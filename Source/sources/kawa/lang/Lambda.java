package kawa.lang;

import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import kawa.standard.object;

public class Lambda extends Syntax {
    public static final Keyword nameKeyword = Keyword.make("name");
    public Expression defaultDefault = QuoteExp.falseExp;
    public Object keyKeyword;
    public Object optionalKeyword;
    public Object restKeyword;

    public void setKeywords(Object optional, Object rest, Object key) {
        this.optionalKeyword = optional;
        this.restKeyword = rest;
        this.keyKeyword = key;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Expression exp = rewrite(form.getCdr(), tr);
        Translator.setLine(exp, (Object) form);
        return exp;
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing formals in lambda");
        }
        int old_errors = tr.getMessages().getErrorCount();
        LambdaExp lexp = new LambdaExp();
        Pair pair = (Pair) obj;
        Translator.setLine((Expression) lexp, (Object) pair);
        rewrite(lexp, pair.getCar(), pair.getCdr(), tr, null);
        if (tr.getMessages().getErrorCount() > old_errors) {
            return new ErrorExp("bad lambda expression");
        }
        return lexp;
    }

    public void rewrite(LambdaExp lexp, Object formals, Object body, Translator tr, TemplateScope templateScopeRest) {
        rewriteFormals(lexp, formals, tr, templateScopeRest);
        if (body instanceof PairWithPosition) {
            lexp.setFile(((PairWithPosition) body).getFileName());
        }
        rewriteBody(lexp, rewriteAttrs(lexp, body, tr), tr);
    }

    public void rewriteFormals(LambdaExp lexp, Object formals, Translator tr, TemplateScope templateScopeRest) {
        if (lexp.getSymbol() == null) {
            String filename = lexp.getFileName();
            int line = lexp.getLineNumber();
            if (filename != null && line > 0) {
                lexp.setSourceLocation(filename, line);
            }
        }
        Object bindings = formals;
        int opt_args = -1;
        int rest_args = -1;
        int key_args = -1;
        while (true) {
            if (bindings instanceof SyntaxForm) {
                bindings = ((SyntaxForm) bindings).getDatum();
            }
            if (!(bindings instanceof Pair)) {
                if (bindings instanceof Symbol) {
                    if (opt_args >= 0 || key_args >= 0 || rest_args >= 0) {
                        tr.syntaxError("dotted rest-arg after " + this.optionalKeyword + ", " + this.restKeyword + ", or " + this.keyKeyword);
                        return;
                    }
                    rest_args = 1;
                } else if (bindings != LList.Empty) {
                    tr.syntaxError("misformed formals in lambda");
                    return;
                }
                if (rest_args > 1) {
                    tr.syntaxError("multiple " + this.restKeyword + " parameters");
                    return;
                }
                if (opt_args < 0) {
                    opt_args = 0;
                }
                if (rest_args < 0) {
                    rest_args = 0;
                }
                if (key_args < 0) {
                    key_args = 0;
                }
                if (rest_args > 0) {
                    lexp.max_args = -1;
                } else {
                    lexp.max_args = lexp.min_args + opt_args + (key_args * 2);
                }
                if (opt_args + key_args > 0) {
                    lexp.defaultArgs = new Expression[(opt_args + key_args)];
                }
                if (key_args > 0) {
                    lexp.keywords = new Keyword[key_args];
                }
                Object bindings2 = formals;
                int opt_args2 = 0;
                int key_args2 = 0;
                Object obj = null;
                while (true) {
                    if (bindings2 instanceof SyntaxForm) {
                        SyntaxForm sf = (SyntaxForm) bindings2;
                        bindings2 = sf.getDatum();
                        templateScopeRest = sf.getScope();
                    }
                    TemplateScope templateScope = templateScopeRest;
                    if (!(bindings2 instanceof Pair)) {
                        if (bindings2 instanceof SyntaxForm) {
                            SyntaxForm sf2 = (SyntaxForm) bindings2;
                            bindings2 = sf2.getDatum();
                            templateScopeRest = sf2.getScope();
                        }
                        if (bindings2 instanceof Symbol) {
                            Declaration decl = new Declaration(bindings2);
                            decl.setType(LangObjType.listType);
                            decl.setFlag(262144);
                            decl.noteValue(null);
                            addParam(decl, templateScopeRest, lexp, tr);
                            return;
                        }
                        return;
                    }
                    Pair pair = (Pair) bindings2;
                    Object pair_car = pair.getCar();
                    if (pair_car instanceof SyntaxForm) {
                        SyntaxForm sf3 = (SyntaxForm) pair_car;
                        pair_car = sf3.getDatum();
                        templateScope = sf3.getScope();
                    }
                    if (pair_car == this.optionalKeyword || pair_car == this.restKeyword || pair_car == this.keyKeyword) {
                        obj = pair_car;
                    } else {
                        Object savePos = tr.pushPositionOf(pair);
                        Object name = null;
                        Object defaultValue = this.defaultDefault;
                        Pair typeSpecPair = null;
                        if (tr.matches(pair_car, "::")) {
                            tr.syntaxError("'::' must follow parameter name");
                            return;
                        }
                        Object pair_car2 = tr.namespaceResolve(pair_car);
                        if (pair_car2 instanceof Symbol) {
                            name = pair_car2;
                            if (pair.getCdr() instanceof Pair) {
                                Pair p = (Pair) pair.getCdr();
                                if (tr.matches(p.getCar(), "::")) {
                                    if (!(pair.getCdr() instanceof Pair)) {
                                        tr.syntaxError("'::' not followed by a type specifier (for parameter '" + name + "')");
                                        return;
                                    }
                                    Pair p2 = (Pair) p.getCdr();
                                    typeSpecPair = p2;
                                    pair = p2;
                                }
                            }
                        } else if (pair_car2 instanceof Pair) {
                            Pair p3 = (Pair) pair_car2;
                            Object pair_car3 = p3.getCar();
                            if (pair_car3 instanceof SyntaxForm) {
                                SyntaxForm sf4 = (SyntaxForm) pair_car3;
                                pair_car3 = sf4.getDatum();
                                templateScope = sf4.getScope();
                            }
                            Object pair_car4 = tr.namespaceResolve(pair_car3);
                            if ((pair_car4 instanceof Symbol) && (p3.getCdr() instanceof Pair)) {
                                name = pair_car4;
                                Pair p4 = (Pair) p3.getCdr();
                                if (tr.matches(p4.getCar(), "::")) {
                                    if (!(p4.getCdr() instanceof Pair)) {
                                        tr.syntaxError("'::' not followed by a type specifier (for parameter '" + name + "')");
                                        return;
                                    }
                                    Pair p5 = (Pair) p4.getCdr();
                                    typeSpecPair = p5;
                                    if (p5.getCdr() instanceof Pair) {
                                        p4 = (Pair) p5.getCdr();
                                    } else {
                                        if (p5.getCdr() == LList.Empty) {
                                            p4 = null;
                                        } else {
                                            tr.syntaxError("improper list in specifier for parameter '" + name + "')");
                                            return;
                                        }
                                    }
                                }
                                if (!(p4 == null || obj == null)) {
                                    defaultValue = p4.getCar();
                                    if (p4.getCdr() instanceof Pair) {
                                        p4 = (Pair) p4.getCdr();
                                    } else {
                                        if (p4.getCdr() == LList.Empty) {
                                            p4 = null;
                                        } else {
                                            tr.syntaxError("improper list in specifier for parameter '" + name + "')");
                                            return;
                                        }
                                    }
                                }
                                if (p4 != null) {
                                    if (typeSpecPair != null) {
                                        tr.syntaxError("duplicate type specifier for parameter '" + name + '\'');
                                        return;
                                    }
                                    typeSpecPair = p4;
                                    if (p4.getCdr() != LList.Empty) {
                                        tr.syntaxError("junk at end of specifier for parameter '" + name + '\'' + " after type " + p4.getCar());
                                        return;
                                    }
                                }
                            }
                        }
                        if (name == null) {
                            tr.syntaxError("parameter is neither name nor (name :: type) nor (name default): " + pair);
                            return;
                        }
                        if (obj == this.optionalKeyword || obj == this.keyKeyword) {
                            Expression[] expressionArr = lexp.defaultArgs;
                            int opt_args3 = opt_args2 + 1;
                            LangExp langExp = new LangExp(defaultValue);
                            expressionArr[opt_args2] = langExp;
                            opt_args2 = opt_args3;
                        }
                        if (obj == this.keyKeyword) {
                            int key_args3 = key_args2 + 1;
                            lexp.keywords[key_args2] = Keyword.make(name instanceof Symbol ? ((Symbol) name).getName() : name.toString());
                            key_args2 = key_args3;
                        }
                        Declaration decl2 = new Declaration(name);
                        Translator.setLine(decl2, bindings2);
                        if (typeSpecPair != null) {
                            LangExp langExp2 = new LangExp(typeSpecPair);
                            decl2.setTypeExp(langExp2);
                            decl2.setFlag(8192);
                        } else if (obj == this.restKeyword) {
                            decl2.setType(LangObjType.listType);
                        }
                        decl2.setFlag(262144);
                        decl2.noteValue(null);
                        addParam(decl2, templateScope, lexp, tr);
                        tr.popPositionOf(savePos);
                    }
                    bindings2 = pair.getCdr();
                }
            } else {
                Pair pair2 = (Pair) bindings;
                Object pair_car5 = pair2.getCar();
                if (pair_car5 instanceof SyntaxForm) {
                    pair_car5 = ((SyntaxForm) pair_car5).getDatum();
                }
                if (pair_car5 != this.optionalKeyword) {
                    if (pair_car5 != this.restKeyword) {
                        if (pair_car5 == this.keyKeyword) {
                            if (key_args >= 0) {
                                tr.syntaxError("multiple " + this.keyKeyword + " in parameter list");
                                return;
                            }
                            key_args = 0;
                        } else if (tr.matches(pair2.getCar(), "::") && (pair2.getCdr() instanceof Pair)) {
                            pair2 = (Pair) pair2.getCdr();
                        } else if (key_args >= 0) {
                            key_args++;
                        } else if (rest_args >= 0) {
                            rest_args++;
                        } else if (opt_args >= 0) {
                            opt_args++;
                        } else {
                            lexp.min_args++;
                        }
                    } else if (rest_args >= 0) {
                        tr.syntaxError("multiple " + this.restKeyword + " in parameter list");
                        return;
                    } else if (key_args >= 0) {
                        tr.syntaxError(this.restKeyword.toString() + " after " + this.keyKeyword);
                        return;
                    } else {
                        rest_args = 0;
                    }
                } else if (opt_args >= 0) {
                    tr.syntaxError("multiple " + this.optionalKeyword + " in parameter list");
                    return;
                } else if (rest_args >= 0 || key_args >= 0) {
                    tr.syntaxError(this.optionalKeyword.toString() + " after " + this.restKeyword + " or " + this.keyKeyword);
                } else {
                    opt_args = 0;
                }
                Object bindings3 = pair2.getCdr();
                bindings = pair2.getCdr();
            }
        }
        tr.syntaxError(this.optionalKeyword.toString() + " after " + this.restKeyword + " or " + this.keyKeyword);
    }

    private static void addParam(Declaration decl, ScopeExp templateScope, LambdaExp lexp, Translator tr) {
        if (templateScope != null) {
            decl = tr.makeRenamedAlias(decl, templateScope);
        }
        lexp.addDeclaration(decl);
        if (templateScope != null) {
            decl.context = templateScope;
        }
    }

    public Object rewriteAttrs(LambdaExp lexp, Object body, Translator tr) {
        String accessFlagName = null;
        String allocationFlagName = null;
        int accessFlag = 0;
        int allocationFlag = 0;
        SyntaxForm syntax0 = null;
        while (true) {
            if (!(body instanceof SyntaxForm)) {
                if (body instanceof Pair) {
                    Pair pair1 = (Pair) body;
                    Object attrName = Translator.stripSyntax(pair1.getCar());
                    if (!tr.matches(attrName, "::")) {
                        if (!(attrName instanceof Keyword)) {
                            break;
                        }
                    } else {
                        attrName = null;
                    }
                    SyntaxForm syntax1 = syntax0;
                    Object pair1_cdr = pair1.getCdr();
                    while (pair1_cdr instanceof SyntaxForm) {
                        syntax1 = (SyntaxForm) pair1_cdr;
                        pair1_cdr = syntax1.getDatum();
                    }
                    if (!(pair1_cdr instanceof Pair)) {
                        break;
                    }
                    Pair pair2 = (Pair) pair1_cdr;
                    if (attrName == null) {
                        if (!lexp.isClassMethod() || !"*init*".equals(lexp.getName())) {
                            lexp.body = new LangExp(new Object[]{pair2, syntax1});
                        } else {
                            tr.error('e', "explicit return type for '*init*' method");
                        }
                    } else if (attrName == object.accessKeyword) {
                        Expression attrExpr = tr.rewrite_car(pair2, syntax1);
                        if (attrExpr instanceof QuoteExp) {
                            Object attrValue = ((QuoteExp) attrExpr).getValue();
                            if ((attrValue instanceof SimpleSymbol) || (attrValue instanceof CharSequence)) {
                                if (lexp.nameDecl == null) {
                                    tr.error('e', "access: not allowed for anonymous function");
                                } else {
                                    String value = attrValue.toString();
                                    if ("private".equals(value)) {
                                        accessFlag = 16777216;
                                    } else if ("protected".equals(value)) {
                                        accessFlag = Declaration.PROTECTED_ACCESS;
                                    } else if ("public".equals(value)) {
                                        accessFlag = Declaration.PUBLIC_ACCESS;
                                    } else if ("package".equals(value)) {
                                        accessFlag = Declaration.PACKAGE_ACCESS;
                                    } else {
                                        tr.error('e', "unknown access specifier");
                                    }
                                    if (!(accessFlagName == null || value == null)) {
                                        tr.error('e', "duplicate access specifiers - " + accessFlagName + " and " + value);
                                    }
                                    accessFlagName = value;
                                }
                            }
                        }
                        tr.error('e', "access: value not a constant symbol or string");
                    } else if (attrName == object.allocationKeyword) {
                        Expression attrExpr2 = tr.rewrite_car(pair2, syntax1);
                        if (attrExpr2 instanceof QuoteExp) {
                            Object attrValue2 = ((QuoteExp) attrExpr2).getValue();
                            if ((attrValue2 instanceof SimpleSymbol) || (attrValue2 instanceof CharSequence)) {
                                if (lexp.nameDecl == null) {
                                    tr.error('e', "allocation: not allowed for anonymous function");
                                } else {
                                    String value2 = attrValue2.toString();
                                    if ("class".equals(value2) || "static".equals(value2)) {
                                        allocationFlag = 2048;
                                    } else if ("instance".equals(value2)) {
                                        allocationFlag = 4096;
                                    } else {
                                        tr.error('e', "unknown allocation specifier");
                                    }
                                    if (!(allocationFlagName == null || value2 == null)) {
                                        tr.error('e', "duplicate allocation specifiers - " + allocationFlagName + " and " + value2);
                                    }
                                    allocationFlagName = value2;
                                }
                            }
                        }
                        tr.error('e', "allocation: value not a constant symbol or string");
                    } else if (attrName == object.throwsKeyword) {
                        Object attrValue3 = pair2.getCar();
                        int count = Translator.listLength(attrValue3);
                        if (count < 0) {
                            tr.error('e', "throws: not followed by a list");
                        } else {
                            Expression[] exps = new Expression[count];
                            SyntaxForm syntax2 = syntax1;
                            for (int i = 0; i < count; i++) {
                                while (attrValue3 instanceof SyntaxForm) {
                                    syntax2 = (SyntaxForm) attrValue3;
                                    attrValue3 = syntax2.getDatum();
                                }
                                Pair pair3 = (Pair) attrValue3;
                                exps[i] = tr.rewrite_car(pair3, syntax2);
                                Translator.setLine(exps[i], (Object) pair3);
                                attrValue3 = pair3.getCdr();
                            }
                            lexp.setExceptions(exps);
                        }
                    } else if (attrName == nameKeyword) {
                        Expression attrExpr3 = tr.rewrite_car(pair2, syntax1);
                        if (attrExpr3 instanceof QuoteExp) {
                            lexp.setName(((QuoteExp) attrExpr3).getValue().toString());
                        }
                    } else {
                        tr.error('w', "unknown procedure property " + attrName);
                    }
                    body = pair2.getCdr();
                } else {
                    break;
                }
            } else {
                syntax0 = (SyntaxForm) body;
                body = syntax0.getDatum();
            }
        }
        int accessFlag2 = accessFlag | allocationFlag;
        if (accessFlag2 != 0) {
            lexp.nameDecl.setFlag((long) accessFlag2);
        }
        if (syntax0 != null) {
            return SyntaxForms.fromDatumIfNeeded(body, syntax0);
        }
        return body;
    }

    public Object skipAttrs(LambdaExp lexp, Object body, Translator tr) {
        while (body instanceof Pair) {
            Pair pair = (Pair) body;
            if (!(pair.getCdr() instanceof Pair)) {
                break;
            }
            Object attrName = pair.getCar();
            if (!tr.matches(attrName, "::")) {
                if (!(attrName instanceof Keyword)) {
                    break;
                }
            }
            body = ((Pair) pair.getCdr()).getCdr();
        }
        return body;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:50:0x01cc, code lost:
        if ((r22 instanceof java.lang.Class) == false) goto L_0x0230;
     */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x020f  */
    /* JADX WARNING: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rewriteBody(gnu.expr.LambdaExp r27, java.lang.Object r28, kawa.lang.Translator r29) {
        /*
            r26 = this;
            r11 = 0
            r0 = r29
            gnu.expr.LambdaExp r0 = r0.curMethodLambda
            r23 = r0
            if (r23 != 0) goto L_0x0023
            r0 = r27
            gnu.expr.Declaration r0 = r0.nameDecl
            r23 = r0
            if (r23 == 0) goto L_0x0023
            gnu.expr.ModuleExp r23 = r29.getModule()
            r24 = 131072(0x20000, float:1.83671E-40)
            boolean r23 = r23.getFlag(r24)
            if (r23 == 0) goto L_0x0023
            r0 = r27
            r1 = r29
            r1.curMethodLambda = r0
        L_0x0023:
            gnu.expr.ScopeExp r6 = r29.currentScope()
            r0 = r29
            r1 = r27
            r0.pushScope(r1)
            r15 = 0
            r0 = r27
            gnu.expr.Keyword[] r0 = r0.keywords
            r23 = r0
            if (r23 != 0) goto L_0x00e1
            r8 = 0
        L_0x0038:
            r0 = r27
            gnu.expr.Expression[] r0 = r0.defaultArgs
            r23 = r0
            if (r23 != 0) goto L_0x00ec
            r12 = 0
        L_0x0041:
            r4 = 0
            r13 = 0
            gnu.expr.Declaration r5 = r27.firstDecl()
        L_0x0047:
            if (r5 == 0) goto L_0x00fb
            boolean r23 = r5.isAlias()
            if (r23 == 0) goto L_0x0068
            gnu.expr.ReferenceExp r23 = kawa.lang.Translator.getOriginalRef(r5)
            gnu.expr.Declaration r14 = r23.getBinding()
            r0 = r27
            r0.replaceFollowing(r15, r14)
            r0 = r27
            r14.context = r0
            r0 = r29
            r0.pushRenamedAlias(r5)
            int r11 = r11 + 1
            r5 = r14
        L_0x0068:
            gnu.expr.Expression r19 = r5.getTypeExp()
            r0 = r19
            boolean r0 = r0 instanceof gnu.expr.LangExp
            r23 = r0
            if (r23 == 0) goto L_0x0089
            gnu.expr.LangExp r19 = (gnu.expr.LangExp) r19
            java.lang.Object r21 = r19.getLangValue()
            gnu.lists.Pair r21 = (gnu.lists.Pair) r21
            r0 = r29
            r1 = r21
            gnu.bytecode.Type r23 = r0.exp2Type(r1)
            r0 = r23
            r5.setType(r0)
        L_0x0089:
            r15 = r5
            r0 = r27
            int r0 = r0.min_args
            r23 = r0
            r0 = r23
            if (r4 < r0) goto L_0x00ce
            r0 = r27
            int r0 = r0.min_args
            r23 = r0
            int r23 = r23 + r12
            r0 = r23
            if (r4 < r0) goto L_0x00b4
            r0 = r27
            int r0 = r0.max_args
            r23 = r0
            if (r23 >= 0) goto L_0x00b4
            r0 = r27
            int r0 = r0.min_args
            r23 = r0
            int r23 = r23 + r12
            r0 = r23
            if (r4 == r0) goto L_0x00ce
        L_0x00b4:
            r0 = r27
            gnu.expr.Expression[] r0 = r0.defaultArgs
            r23 = r0
            r0 = r27
            gnu.expr.Expression[] r0 = r0.defaultArgs
            r24 = r0
            r24 = r24[r13]
            r0 = r29
            r1 = r24
            gnu.expr.Expression r24 = r0.rewrite(r1)
            r23[r13] = r24
            int r13 = r13 + 1
        L_0x00ce:
            int r4 = r4 + 1
            r0 = r29
            gnu.expr.NameLookup r0 = r0.lexical
            r23 = r0
            r0 = r23
            r0.push(r5)
            gnu.expr.Declaration r5 = r5.nextDecl()
            goto L_0x0047
        L_0x00e1:
            r0 = r27
            gnu.expr.Keyword[] r0 = r0.keywords
            r23 = r0
            r0 = r23
            int r8 = r0.length
            goto L_0x0038
        L_0x00ec:
            r0 = r27
            gnu.expr.Expression[] r0 = r0.defaultArgs
            r23 = r0
            r0 = r23
            int r0 = r0.length
            r23 = r0
            int r12 = r23 - r8
            goto L_0x0041
        L_0x00fb:
            boolean r23 = r27.isClassMethod()
            if (r23 == 0) goto L_0x0121
            r0 = r27
            gnu.expr.Declaration r0 = r0.nameDecl
            r23 = r0
            r24 = 2048(0x800, double:1.0118E-320)
            boolean r23 = r23.getFlag(r24)
            if (r23 != 0) goto L_0x0121
            r23 = 0
            gnu.expr.Declaration r24 = new gnu.expr.Declaration
            java.lang.String r25 = gnu.expr.ThisExp.THIS_NAME
            r24.<init>(r25)
            r0 = r27
            r1 = r23
            r2 = r24
            r0.add(r1, r2)
        L_0x0121:
            r0 = r29
            gnu.expr.LambdaExp r0 = r0.curLambda
            r18 = r0
            r0 = r27
            r1 = r29
            r1.curLambda = r0
            r0 = r27
            gnu.bytecode.Type r0 = r0.returnType
            r17 = r0
            r0 = r27
            gnu.expr.Expression r0 = r0.body
            r23 = r0
            r0 = r23
            boolean r0 = r0 instanceof gnu.expr.LangExp
            r23 = r0
            if (r23 == 0) goto L_0x0175
            r0 = r27
            gnu.expr.Expression r0 = r0.body
            r23 = r0
            gnu.expr.LangExp r23 = (gnu.expr.LangExp) r23
            java.lang.Object r23 = r23.getLangValue()
            java.lang.Object[] r23 = (java.lang.Object[]) r23
            r20 = r23
            java.lang.Object[] r20 = (java.lang.Object[]) r20
            r23 = 0
            r23 = r20[r23]
            gnu.lists.Pair r23 = (gnu.lists.Pair) r23
            r24 = 1
            r24 = r20[r24]
            kawa.lang.SyntaxForm r24 = (kawa.lang.SyntaxForm) r24
            r0 = r29
            r1 = r23
            r2 = r24
            gnu.expr.Expression r19 = r0.rewrite_car(r1, r2)
            gnu.expr.Language r23 = r29.getLanguage()
            r0 = r23
            r1 = r19
            gnu.bytecode.Type r17 = r0.getTypeFor(r1)
        L_0x0175:
            r0 = r29
            r1 = r28
            gnu.expr.Expression r23 = r0.rewrite_body(r1)
            r0 = r23
            r1 = r27
            r1.body = r0
            r0 = r18
            r1 = r29
            r1.curLambda = r0
            r0 = r27
            gnu.expr.Expression r0 = r0.body
            r23 = r0
            r0 = r23
            boolean r0 = r0 instanceof gnu.expr.BeginExp
            r23 = r0
            if (r23 == 0) goto L_0x0230
            r0 = r27
            gnu.expr.Expression r0 = r0.body
            r23 = r0
            gnu.expr.BeginExp r23 = (gnu.expr.BeginExp) r23
            gnu.expr.Expression[] r7 = r23.getExpressions()
            int r9 = r7.length
            r23 = 1
            r0 = r23
            if (r9 <= r0) goto L_0x0230
            r23 = 0
            r23 = r7[r23]
            r0 = r23
            boolean r0 = r0 instanceof gnu.expr.ReferenceExp
            r23 = r0
            if (r23 != 0) goto L_0x01ce
            r23 = 0
            r23 = r7[r23]
            java.lang.Object r22 = r23.valueIfConstant()
            r0 = r22
            boolean r0 = r0 instanceof gnu.bytecode.Type
            r23 = r0
            if (r23 != 0) goto L_0x01ce
            r0 = r22
            boolean r0 = r0 instanceof java.lang.Class
            r23 = r0
            if (r23 == 0) goto L_0x0230
        L_0x01ce:
            r23 = 0
            r16 = r7[r23]
            int r9 = r9 + -1
            r23 = 1
            r0 = r23
            if (r9 != r0) goto L_0x0218
            r23 = 1
            r23 = r7[r23]
            r0 = r23
            r1 = r27
            r1.body = r0
        L_0x01e4:
            gnu.expr.Language r23 = r29.getLanguage()
            r0 = r27
            r1 = r16
            r2 = r23
            r0.setCoercedReturnValue(r1, r2)
        L_0x01f1:
            r0 = r29
            r1 = r27
            r0.pop(r1)
            r27.countDecls()
            r0 = r29
            r0.popRenamedAlias(r11)
            r27.countDecls()
            r0 = r29
            gnu.expr.LambdaExp r0 = r0.curMethodLambda
            r23 = r0
            r0 = r23
            r1 = r27
            if (r0 != r1) goto L_0x0217
            r23 = 0
            r0 = r23
            r1 = r29
            r1.curMethodLambda = r0
        L_0x0217:
            return
        L_0x0218:
            gnu.expr.Expression[] r10 = new gnu.expr.Expression[r9]
            r23 = 1
            r24 = 0
            r0 = r23
            r1 = r24
            java.lang.System.arraycopy(r7, r0, r10, r1, r9)
            gnu.expr.Expression r23 = gnu.expr.BeginExp.canonicalize(r10)
            r0 = r23
            r1 = r27
            r1.body = r0
            goto L_0x01e4
        L_0x0230:
            r0 = r27
            r1 = r17
            r0.setCoercedReturnType(r1)
            goto L_0x01f1
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Lambda.rewriteBody(gnu.expr.LambdaExp, java.lang.Object, kawa.lang.Translator):void");
    }

    public void print(Consumer out) {
        out.write("#<builtin lambda>");
    }
}
