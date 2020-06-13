package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.IgnoreTarget;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.expr.TryExp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.reflect.CompileReflect;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.WrongArguments;
import kawa.standard.Scheme;

public class CompileMisc implements Inlineable {
    static final int CONVERT = 2;
    static final int NOT = 3;
    static Method coerceMethod;
    public static final ClassType typeContinuation = ClassType.make("kawa.lang.Continuation");
    static ClassType typeType;
    int code;
    Procedure proc;

    static class ExitThroughFinallyChecker extends ExpVisitor<Expression, TryExp> {
        Declaration decl;

        ExitThroughFinallyChecker() {
        }

        public static boolean check(Declaration decl2, Expression body) {
            ExitThroughFinallyChecker visitor = new ExitThroughFinallyChecker();
            visitor.decl = decl2;
            visitor.visit(body, null);
            return visitor.exitValue != null;
        }

        /* access modifiers changed from: protected */
        public Expression defaultValue(Expression r, TryExp d) {
            return r;
        }

        /* access modifiers changed from: protected */
        public Expression visitReferenceExp(ReferenceExp exp, TryExp currentTry) {
            if (this.decl == exp.getBinding() && currentTry != null) {
                this.exitValue = Boolean.TRUE;
            }
            return exp;
        }

        /* access modifiers changed from: protected */
        public Expression visitTryExp(TryExp exp, TryExp currentTry) {
            if (exp.getFinallyClause() != null) {
                currentTry = exp;
            }
            visitExpression(exp, currentTry);
            return exp;
        }
    }

    public CompileMisc(Procedure proc2, int code2) {
        this.proc = proc2;
        this.code = code2;
    }

    public static CompileMisc forConvert(Object proc2) {
        return new CompileMisc((Procedure) proc2, 2);
    }

    public static CompileMisc forNot(Object proc2) {
        return new CompileMisc((Procedure) proc2, 3);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        switch (this.code) {
            case 2:
                compileConvert((Convert) this.proc, exp, comp, target);
                return;
            case 3:
                compileNot((Not) this.proc, exp, comp, target);
                return;
            default:
                throw new Error();
        }
    }

    public static Expression validateApplyConstantFunction0(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        int nargs = exp.getArgCount();
        if (nargs == 0 || visitor == null) {
            return ((ConstantFunction0) proc2).constant;
        }
        return visitor.noteError(WrongArguments.checkArgCount(proc2, nargs));
    }

    public static Expression validateApplyConvert(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        Compilation comp = visitor.getCompilation();
        Language language = comp.getLanguage();
        Expression[] args = exp.getArgs();
        if (args.length == 2) {
            args[0] = visitor.visit(args[0], (Type) null);
            Type type = language.getTypeFor(args[0]);
            if (type instanceof Type) {
                args[0] = new QuoteExp(type);
                args[1] = visitor.visit(args[1], type);
                CompileReflect.checkKnownClass(type, comp);
                exp.setType(type);
                return exp;
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public static Expression validateApplyNot(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        exp.setType(visitor.getCompilation().getLanguage().getTypeFor(Boolean.TYPE));
        return exp.inlineIfConstant(proc2, visitor);
    }

    public static Expression validateApplyFormat(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        Type retType = Type.objectType;
        Expression[] args = exp.getArgs();
        if (args.length > 0) {
            ClassType typeFormat = ClassType.make("gnu.kawa.functions.Format");
            Object f = args[0].valueIfConstant();
            Type ftype = args[0].getType();
            if (f == Boolean.FALSE || ftype.isSubtype(LangObjType.stringType)) {
                int skip = f == Boolean.FALSE ? 1 : 0;
                Expression[] xargs = new Expression[((args.length + 1) - skip)];
                xargs[0] = new QuoteExp(Integer.valueOf(0), Type.intType);
                System.arraycopy(args, skip, xargs, 1, xargs.length - 1);
                ApplyExp ae = new ApplyExp(typeFormat.getDeclaredMethod("formatToString", 2), xargs);
                ae.setType(Type.javalangStringType);
                return ae;
            } else if (f == Boolean.TRUE || ftype.isSubtype(ClassType.make("java.io.Writer"))) {
                if (f == Boolean.TRUE) {
                    Expression[] xargs2 = new Expression[args.length];
                    xargs2[0] = QuoteExp.nullExp;
                    System.arraycopy(args, 1, xargs2, 1, args.length - 1);
                    args = xargs2;
                }
                ApplyExp ae2 = new ApplyExp(typeFormat.getDeclaredMethod("formatToWriter", 3), args);
                ae2.setType(Type.voidType);
                return ae2;
            } else if (ftype.isSubtype(ClassType.make("java.io.OutputStream"))) {
                retType = Type.voidType;
            }
        }
        exp.setType(retType);
        return null;
    }

    public static Expression validateApplyAppendValues(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            return args[0];
        }
        if (args.length == 0) {
            return QuoteExp.voidExp;
        }
        Expression folded = exp.inlineIfConstant(proc2, visitor);
        if (folded == exp) {
            return exp;
        }
        return folded;
    }

    public static Expression validateApplyMakeProcedure(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        int alen = args.length;
        Expression method = null;
        int countMethods = 0;
        String name = null;
        int i = 0;
        while (i < alen) {
            Expression arg = args[i];
            if (arg instanceof QuoteExp) {
                Object key = ((QuoteExp) arg).getValue();
                if (key instanceof Keyword) {
                    String keyword = ((Keyword) key).getName();
                    i++;
                    Expression next = args[i];
                    if (keyword == "name") {
                        if (next instanceof QuoteExp) {
                            name = ((QuoteExp) next).getValue().toString();
                        }
                    } else if (keyword == "method") {
                        countMethods++;
                        method = next;
                    }
                    i++;
                }
            }
            countMethods++;
            method = arg;
            i++;
        }
        if (countMethods != 1 || !(method instanceof LambdaExp)) {
            return exp;
        }
        LambdaExp lexp = (LambdaExp) method;
        int i2 = 0;
        while (i2 < alen) {
            Expression arg2 = args[i2];
            if (arg2 instanceof QuoteExp) {
                Object key2 = ((QuoteExp) arg2).getValue();
                if (key2 instanceof Keyword) {
                    String keyword2 = ((Keyword) key2).getName();
                    i2++;
                    Expression next2 = args[i2];
                    if (keyword2 == "name") {
                        lexp.setName(name);
                    } else if (keyword2 != "method") {
                        lexp.setProperty(Namespace.EmptyNamespace.getSymbol(keyword2), next2);
                    }
                }
            }
            i2++;
        }
        return method;
    }

    public static Expression validateApplyValuesMap(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        LambdaExp lexp = ValuesMap.canInline(exp, (ValuesMap) proc2);
        if (lexp != null) {
            lexp.setInlineOnly(true);
            lexp.returnContinuation = exp;
            lexp.inlineHome = visitor.getCurrentLambda();
        }
        return exp;
    }

    public static void compileConvert(Convert proc2, ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        if (args.length != 2) {
            throw new Error("wrong number of arguments to " + proc2.getName());
        }
        CodeAttr code2 = comp.getCode();
        Type type = Scheme.getTypeValue(args[0]);
        if (type != null) {
            args[1].compile(comp, Target.pushValue(type));
            if (code2.reachableHere()) {
                target.compileFromStack(comp, type);
                return;
            }
            return;
        }
        if (typeType == null) {
            typeType = ClassType.make("gnu.bytecode.Type");
        }
        if (coerceMethod == null) {
            coerceMethod = typeType.addMethod("coerceFromObject", Compilation.apply1args, (Type) Type.pointer_type, 1);
        }
        args[0].compile(comp, (Type) LangObjType.typeClassType);
        args[1].compile(comp, Target.pushObject);
        code2.emitInvokeVirtual(coerceMethod);
        target.compileFromStack(comp, Type.pointer_type);
    }

    public void compileNot(Not proc2, ApplyExp exp, Compilation comp, Target target) {
        Expression arg = exp.getArgs()[0];
        Language language = proc2.language;
        if (target instanceof ConditionalTarget) {
            ConditionalTarget ctarget = (ConditionalTarget) target;
            ConditionalTarget sub_target = new ConditionalTarget(ctarget.ifFalse, ctarget.ifTrue, language);
            sub_target.trueBranchComesFirst = !ctarget.trueBranchComesFirst;
            arg.compile(comp, (Target) sub_target);
            return;
        }
        CodeAttr code2 = comp.getCode();
        Type type = target.getType();
        if (!(target instanceof StackTarget) || type.getSignature().charAt(0) != 'Z') {
            IfExp.compile(arg, QuoteExp.getInstance(language.booleanObject(false)), QuoteExp.getInstance(language.booleanObject(true)), comp, target);
            return;
        }
        arg.compile(comp, target);
        code2.emitNot(target.getType());
    }

    public static Expression validateApplyCallCC(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        LambdaExp lexp = canInlineCallCC(exp);
        if (lexp != null) {
            lexp.setInlineOnly(true);
            lexp.returnContinuation = exp;
            lexp.inlineHome = visitor.getCurrentLambda();
            Declaration contDecl = lexp.firstDecl();
            if (!contDecl.getFlag(8192)) {
                contDecl.setType(typeContinuation);
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public static void compileCallCC(ApplyExp exp, Compilation comp, Target target, Procedure proc2) {
        LambdaExp lambda = canInlineCallCC(exp);
        if (lambda == null) {
            ApplyExp.compile(exp, comp, target);
            return;
        }
        CodeAttr code2 = comp.getCode();
        Declaration param = lambda.firstDecl();
        if (!param.isSimple() || param.getCanRead() || param.getCanWrite()) {
            Variable contVar = code2.pushScope().addVariable(code2, typeContinuation, null);
            Declaration contDecl = new Declaration(contVar);
            code2.emitNew(typeContinuation);
            code2.emitDup((Type) typeContinuation);
            comp.loadCallContext();
            code2.emitInvokeSpecial(typeContinuation.getDeclaredMethod("<init>", 1));
            code2.emitStore(contVar);
            code2.emitTryStart(false, ((target instanceof IgnoreTarget) || (target instanceof ConsumerTarget)) ? null : Type.objectType);
            ReferenceExp referenceExp = new ReferenceExp(contDecl);
            new ApplyExp((Expression) lambda, referenceExp).compile(comp, target);
            if (code2.reachableHere()) {
                code2.emitLoad(contVar);
                code2.emitPushInt(1);
                code2.emitPutField(typeContinuation.getField("invoked"));
            }
            code2.emitTryEnd();
            code2.emitCatchStart(null);
            code2.emitLoad(contVar);
            if (target instanceof ConsumerTarget) {
                comp.loadCallContext();
                code2.emitInvokeStatic(typeContinuation.getDeclaredMethod("handleException$X", 3));
            } else {
                code2.emitInvokeStatic(typeContinuation.getDeclaredMethod("handleException", 2));
                target.compileFromStack(comp, Type.objectType);
            }
            code2.emitCatchEnd();
            code2.emitTryCatchEnd();
            code2.popScope();
            return;
        }
        param.setCanCall(false);
        CompileTimeContinuation contProxy = new CompileTimeContinuation();
        contProxy.exitableBlock = code2.startExitableBlock(target instanceof StackTarget ? target.getType() : null, ExitThroughFinallyChecker.check(param, lambda.body));
        contProxy.blockTarget = target;
        param.setValue(new QuoteExp(contProxy));
        new ApplyExp((Expression) lambda, QuoteExp.nullExp).compile(comp, target);
        code2.endExitableBlock();
    }

    private static LambdaExp canInlineCallCC(ApplyExp exp) {
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            Expression arg0 = args[0];
            if (arg0 instanceof LambdaExp) {
                LambdaExp lexp = (LambdaExp) arg0;
                if (lexp.min_args == 1 && lexp.max_args == 1 && !lexp.firstDecl().getCanWrite()) {
                    return lexp;
                }
            }
        }
        return null;
    }

    /* JADX WARNING: type inference failed for: r30v0, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r1v13, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r30v1 */
    /* JADX WARNING: type inference failed for: r30v2 */
    /* JADX WARNING: type inference failed for: r0v33, types: [gnu.expr.Expression] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.expr.Expression validateApplyMap(gnu.expr.ApplyExp r38, gnu.expr.InlineCalls r39, gnu.bytecode.Type r40, gnu.mapping.Procedure r41) {
        /*
            r24 = r41
            gnu.kawa.functions.Map r24 = (gnu.kawa.functions.Map) r24
            r0 = r24
            boolean r7 = r0.collect
            r38.visitArgs(r39)
            gnu.expr.Expression[] r5 = r38.getArgs()
            int r0 = r5.length
            r25 = r0
            r35 = 2
            r0 = r25
            r1 = r35
            if (r0 >= r1) goto L_0x001b
        L_0x001a:
            return r38
        L_0x001b:
            int r25 = r25 + -1
            r35 = 0
            r27 = r5[r35]
            boolean r35 = r27.side_effects()
            if (r35 != 0) goto L_0x00e5
            r29 = 1
        L_0x0029:
            r35 = 1
            r0 = r35
            gnu.expr.Expression[] r15 = new gnu.expr.Expression[r0]
            r35 = 0
            r15[r35] = r27
            gnu.expr.LetExp r19 = new gnu.expr.LetExp
            r0 = r19
            r0.<init>(r15)
            java.lang.String r35 = "%proc"
            gnu.bytecode.ClassType r36 = gnu.expr.Compilation.typeProcedure
            r0 = r19
            r1 = r35
            r2 = r36
            gnu.expr.Declaration r28 = r0.addDeclaration(r1, r2)
            r35 = 0
            r35 = r5[r35]
            r0 = r28
            r1 = r35
            r0.noteValue(r1)
            r35 = 1
            r0 = r35
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r16 = r0
            gnu.expr.LetExp r20 = new gnu.expr.LetExp
            r0 = r20
            r1 = r16
            r0.<init>(r1)
            r19.setBody(r20)
            gnu.expr.LambdaExp r22 = new gnu.expr.LambdaExp
            if (r7 == 0) goto L_0x00e9
            int r35 = r25 + 1
        L_0x006d:
            r0 = r22
            r1 = r35
            r0.<init>(r1)
            r35 = 0
            r16[r35] = r22
            java.lang.String r35 = "%loop"
            r0 = r20
            r1 = r35
            gnu.expr.Declaration r23 = r0.addDeclaration(r1)
            r0 = r23
            r1 = r22
            r0.noteValue(r1)
            r0 = r25
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r17 = r0
            gnu.expr.LetExp r21 = new gnu.expr.LetExp
            r0 = r21
            r1 = r17
            r0.<init>(r1)
            r0 = r25
            gnu.expr.Declaration[] r0 = new gnu.expr.Declaration[r0]
            r18 = r0
            r0 = r25
            gnu.expr.Declaration[] r0 = new gnu.expr.Declaration[r0]
            r26 = r0
            r13 = 0
        L_0x00a5:
            r0 = r25
            if (r13 >= r0) goto L_0x00ec
            java.lang.StringBuilder r35 = new java.lang.StringBuilder
            r35.<init>()
            java.lang.String r36 = "arg"
            java.lang.StringBuilder r35 = r35.append(r36)
            r0 = r35
            java.lang.StringBuilder r35 = r0.append(r13)
            java.lang.String r4 = r35.toString()
            r0 = r22
            gnu.expr.Declaration r35 = r0.addDeclaration(r4)
            r18[r13] = r35
            gnu.bytecode.ClassType r35 = gnu.expr.Compilation.typePair
            r0 = r21
            r1 = r35
            gnu.expr.Declaration r35 = r0.addDeclaration(r4, r1)
            r26[r13] = r35
            gnu.expr.ReferenceExp r35 = new gnu.expr.ReferenceExp
            r36 = r18[r13]
            r35.<init>(r36)
            r17[r13] = r35
            r35 = r26[r13]
            r36 = r17[r13]
            r35.noteValue(r36)
            int r13 = r13 + 1
            goto L_0x00a5
        L_0x00e5:
            r29 = 0
            goto L_0x0029
        L_0x00e9:
            r35 = r25
            goto L_0x006d
        L_0x00ec:
            if (r7 == 0) goto L_0x0148
            java.lang.String r35 = "result"
            r0 = r22
            r1 = r35
            gnu.expr.Declaration r33 = r0.addDeclaration(r1)
        L_0x00f8:
            int r35 = r25 + 1
            r0 = r35
            gnu.expr.Expression[] r10 = new gnu.expr.Expression[r0]
            if (r7 == 0) goto L_0x014b
            int r35 = r25 + 1
        L_0x0102:
            r0 = r35
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r31 = r0
            r13 = 0
        L_0x0109:
            r0 = r25
            if (r13 >= r0) goto L_0x014e
            int r35 = r13 + 1
            gnu.expr.ReferenceExp r36 = new gnu.expr.ReferenceExp
            r37 = r26[r13]
            r36.<init>(r37)
            java.lang.String r37 = "car"
            gnu.expr.ApplyExp r36 = gnu.kawa.reflect.SlotGet.makeGetField(r36, r37)
            r37 = 0
            r0 = r39
            r1 = r36
            r2 = r37
            gnu.expr.Expression r36 = r0.visitApplyOnly(r1, r2)
            r10[r35] = r36
            gnu.expr.ReferenceExp r35 = new gnu.expr.ReferenceExp
            r36 = r26[r13]
            r35.<init>(r36)
            java.lang.String r36 = "cdr"
            gnu.expr.ApplyExp r35 = gnu.kawa.reflect.SlotGet.makeGetField(r35, r36)
            r36 = 0
            r0 = r39
            r1 = r35
            r2 = r36
            gnu.expr.Expression r35 = r0.visitApplyOnly(r1, r2)
            r31[r13] = r35
            int r13 = r13 + 1
            goto L_0x0109
        L_0x0148:
            r33 = 0
            goto L_0x00f8
        L_0x014b:
            r35 = r25
            goto L_0x0102
        L_0x014e:
            if (r29 != 0) goto L_0x0155
            gnu.expr.ReferenceExp r27 = new gnu.expr.ReferenceExp
            r27.<init>(r28)
        L_0x0155:
            r35 = 0
            r10[r35] = r27
            gnu.expr.ApplyExp r35 = new gnu.expr.ApplyExp
            gnu.expr.ReferenceExp r36 = new gnu.expr.ReferenceExp
            r0 = r24
            gnu.expr.Declaration r0 = r0.applyFieldDecl
            r37 = r0
            r36.<init>(r37)
            r0 = r35
            r1 = r36
            r0.<init>(r1, r10)
            r36 = 0
            r0 = r39
            r1 = r35
            r2 = r36
            gnu.expr.Expression r11 = r0.visitApplyOnly(r1, r2)
            if (r7 == 0) goto L_0x01a0
            r35 = 2
            r0 = r35
            gnu.expr.Expression[] r9 = new gnu.expr.Expression[r0]
            r35 = 0
            r9[r35] = r11
            r35 = 1
            gnu.expr.ReferenceExp r36 = new gnu.expr.ReferenceExp
            r0 = r36
            r1 = r33
            r0.<init>(r1)
            r9[r35] = r36
            gnu.bytecode.ClassType r35 = gnu.expr.Compilation.typePair
            java.lang.String r36 = "make"
            r0 = r35
            r1 = r36
            gnu.expr.ApplyExp r35 = gnu.kawa.reflect.Invoke.makeInvokeStatic(r0, r1, r9)
            r31[r25] = r35
        L_0x01a0:
            gnu.expr.ApplyExp r35 = new gnu.expr.ApplyExp
            gnu.expr.ReferenceExp r36 = new gnu.expr.ReferenceExp
            r0 = r36
            r1 = r23
            r0.<init>(r1)
            r0 = r35
            r1 = r36
            r2 = r31
            r0.<init>(r1, r2)
            r36 = 0
            r0 = r39
            r1 = r35
            r2 = r36
            gnu.expr.Expression r30 = r0.visitApplyOnly(r1, r2)
            if (r7 == 0) goto L_0x0249
        L_0x01c2:
            r0 = r30
            r1 = r22
            r1.body = r0
            r0 = r22
            gnu.expr.Expression r0 = r0.body
            r35 = r0
            r0 = r21
            r1 = r35
            r0.setBody(r1)
            r0 = r21
            r1 = r22
            r1.body = r0
            if (r7 == 0) goto L_0x0256
            int r35 = r25 + 1
        L_0x01df:
            r0 = r35
            gnu.expr.Expression[] r14 = new gnu.expr.Expression[r0]
            gnu.expr.QuoteExp r12 = new gnu.expr.QuoteExp
            gnu.lists.LList r35 = gnu.lists.LList.Empty
            r0 = r35
            r12.<init>(r0)
            r13 = r25
        L_0x01ee:
            int r13 = r13 + -1
            if (r13 < 0) goto L_0x025c
            r35 = 2
            r0 = r35
            gnu.expr.Expression[] r8 = new gnu.expr.Expression[r0]
            r35 = 0
            gnu.expr.ReferenceExp r36 = new gnu.expr.ReferenceExp
            r37 = r18[r13]
            r36.<init>(r37)
            r8[r35] = r36
            r35 = 1
            r8[r35] = r12
            if (r7 == 0) goto L_0x0259
            gnu.expr.ReferenceExp r32 = new gnu.expr.ReferenceExp
            r32.<init>(r33)
        L_0x020e:
            gnu.expr.IfExp r35 = new gnu.expr.IfExp
            gnu.expr.ApplyExp r36 = new gnu.expr.ApplyExp
            r0 = r24
            gnu.kawa.functions.IsEq r0 = r0.isEq
            r37 = r0
            r0 = r36
            r1 = r37
            r0.<init>(r1, r8)
            r37 = 0
            r0 = r39
            r1 = r36
            r2 = r37
            gnu.expr.Expression r36 = r0.visitApplyOnly(r1, r2)
            r0 = r22
            gnu.expr.Expression r0 = r0.body
            r37 = r0
            r0 = r35
            r1 = r36
            r2 = r32
            r3 = r37
            r0.<init>(r1, r2, r3)
            r0 = r35
            r1 = r22
            r1.body = r0
            int r35 = r13 + 1
            r35 = r5[r35]
            r14[r13] = r35
            goto L_0x01ee
        L_0x0249:
            gnu.expr.BeginExp r35 = new gnu.expr.BeginExp
            r0 = r35
            r1 = r30
            r0.<init>(r11, r1)
            r30 = r35
            goto L_0x01c2
        L_0x0256:
            r35 = r25
            goto L_0x01df
        L_0x0259:
            gnu.expr.QuoteExp r32 = gnu.expr.QuoteExp.voidExp
            goto L_0x020e
        L_0x025c:
            if (r7 == 0) goto L_0x0260
            r14[r25] = r12
        L_0x0260:
            gnu.expr.ApplyExp r35 = new gnu.expr.ApplyExp
            gnu.expr.ReferenceExp r36 = new gnu.expr.ReferenceExp
            r0 = r36
            r1 = r23
            r0.<init>(r1)
            r0 = r35
            r1 = r36
            r0.<init>(r1, r14)
            r36 = 0
            r0 = r39
            r1 = r35
            r2 = r36
            gnu.expr.Expression r6 = r0.visitApplyOnly(r1, r2)
            if (r7 == 0) goto L_0x029a
            r35 = 1
            r0 = r35
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r34 = r0
            r35 = 0
            r34[r35] = r6
            gnu.bytecode.ClassType r35 = gnu.expr.Compilation.scmListType
            java.lang.String r36 = "reverseInPlace"
            r0 = r35
            r1 = r36
            r2 = r34
            gnu.expr.ApplyExp r6 = gnu.kawa.reflect.Invoke.makeInvokeStatic(r0, r1, r2)
        L_0x029a:
            r0 = r20
            r0.setBody(r6)
            if (r29 == 0) goto L_0x02a5
            r38 = r20
            goto L_0x001a
        L_0x02a5:
            r38 = r19
            goto L_0x001a
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.CompileMisc.validateApplyMap(gnu.expr.ApplyExp, gnu.expr.InlineCalls, gnu.bytecode.Type, gnu.mapping.Procedure):gnu.expr.Expression");
    }
}
