package kawa.lang;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.NameLookup;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.Special;
import gnu.kawa.functions.AppendValues;
import gnu.kawa.functions.CompileNamedPart;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.xml.MakeAttribute;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import gnu.xml.NamespaceBinding;
import java.util.Stack;
import java.util.Vector;
import kawa.standard.begin;
import kawa.standard.require;

public class Translator extends Compilation {
    private static Expression errorExp = new ErrorExp("unknown syntax error");
    public static final Declaration getNamedPartDecl = Declaration.getDeclarationFromStatic("gnu.kawa.functions.GetNamedPart", "getNamedPart");
    public LambdaExp curMethodLambda;
    public Macro currentMacroDefinition;
    Syntax currentSyntax;
    private Environment env = Environment.getCurrent();
    public int firstForm;
    public Stack formStack = new Stack();
    Declaration macroContext;
    public Declaration matchArray;
    Vector notedAccess;
    public PatternScope patternScope;
    public Object pendingForm;
    PairWithPosition positionPair;
    Stack renamedAliasStack;
    public Declaration templateScopeDecl;
    public NamespaceBinding xmlElementNamespaces = NamespaceBinding.predefinedXML;

    static {
        LispLanguage.getNamedPartLocation.setDeclaration(getNamedPartDecl);
    }

    public Translator(Language language, SourceMessages messages, NameLookup lexical) {
        super(language, messages, lexical);
    }

    public final Environment getGlobalEnvironment() {
        return this.env;
    }

    public Expression parse(Object input) {
        return rewrite(input);
    }

    public final Expression rewrite_car(Pair pair, SyntaxForm syntax) {
        if (syntax == null || syntax.getScope() == this.current_scope || (pair.getCar() instanceof SyntaxForm)) {
            return rewrite_car(pair, false);
        }
        ScopeExp save_scope = this.current_scope;
        try {
            setCurrentScope(syntax.getScope());
            return rewrite_car(pair, false);
        } finally {
            setCurrentScope(save_scope);
        }
    }

    public final Expression rewrite_car(Pair pair, boolean function) {
        Object car = pair.getCar();
        if (pair instanceof PairWithPosition) {
            return rewrite_with_position(car, function, (PairWithPosition) pair);
        }
        return rewrite(car, function);
    }

    public Syntax getCurrentSyntax() {
        return this.currentSyntax;
    }

    /* access modifiers changed from: 0000 */
    public Expression apply_rewrite(Syntax syntax, Pair form) {
        Expression expression = errorExp;
        Syntax saveSyntax = this.currentSyntax;
        this.currentSyntax = syntax;
        try {
            return syntax.rewriteForm(form, this);
        } finally {
            this.currentSyntax = saveSyntax;
        }
    }

    static ReferenceExp getOriginalRef(Declaration decl) {
        if (decl != null && decl.isAlias() && !decl.isIndirectBinding()) {
            Expression value = decl.getValue();
            if (value instanceof ReferenceExp) {
                return (ReferenceExp) value;
            }
        }
        return null;
    }

    public final boolean selfEvaluatingSymbol(Object obj) {
        return ((LispLanguage) getLanguage()).selfEvaluatingSymbol(obj);
    }

    public final boolean matches(Object form, String literal) {
        return matches(form, (SyntaxForm) null, literal);
    }

    public boolean matches(Object form, SyntaxForm syntax, String literal) {
        if (syntax != null) {
        }
        if (form instanceof SyntaxForm) {
            form = ((SyntaxForm) form).getDatum();
        }
        if ((form instanceof SimpleSymbol) && !selfEvaluatingSymbol(form)) {
            ReferenceExp rexp = getOriginalRef(this.lexical.lookup(form, -1));
            if (rexp != null) {
                form = rexp.getSymbol();
            }
        }
        return (form instanceof SimpleSymbol) && ((Symbol) form).getLocalPart() == literal;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=gnu.mapping.Symbol, code=java.lang.Object, for r6v0, types: [gnu.mapping.Symbol, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean matches(java.lang.Object r4, kawa.lang.SyntaxForm r5, java.lang.Object r6) {
        /*
            r3 = this;
            if (r5 == 0) goto L_0x0002
        L_0x0002:
            boolean r1 = r4 instanceof kawa.lang.SyntaxForm
            if (r1 == 0) goto L_0x000c
            kawa.lang.SyntaxForm r4 = (kawa.lang.SyntaxForm) r4
            java.lang.Object r4 = r4.getDatum()
        L_0x000c:
            boolean r1 = r4 instanceof gnu.mapping.SimpleSymbol
            if (r1 == 0) goto L_0x0027
            boolean r1 = r3.selfEvaluatingSymbol(r4)
            if (r1 != 0) goto L_0x0027
            gnu.expr.NameLookup r1 = r3.lexical
            r2 = -1
            gnu.expr.Declaration r1 = r1.lookup(r4, r2)
            gnu.expr.ReferenceExp r0 = getOriginalRef(r1)
            if (r0 == 0) goto L_0x0027
            java.lang.Object r4 = r0.getSymbol()
        L_0x0027:
            if (r4 != r6) goto L_0x002b
            r1 = 1
        L_0x002a:
            return r1
        L_0x002b:
            r1 = 0
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Translator.matches(java.lang.Object, kawa.lang.SyntaxForm, gnu.mapping.Symbol):boolean");
    }

    public Object matchQuoted(Pair pair) {
        if (matches(pair.getCar(), LispLanguage.quote_sym) && (pair.getCdr() instanceof Pair)) {
            Pair pair2 = (Pair) pair.getCdr();
            if (pair2.getCdr() == LList.Empty) {
                return pair2.getCar();
            }
        }
        return null;
    }

    public Declaration lookup(Object name, int namespace) {
        Declaration decl = this.lexical.lookup(name, namespace);
        return (decl == null || !getLanguage().hasNamespace(decl, namespace)) ? currentModule().lookup(name, getLanguage(), namespace) : decl;
    }

    public Declaration lookupGlobal(Object name) {
        return lookupGlobal(name, -1);
    }

    public Declaration lookupGlobal(Object name, int namespace) {
        ModuleExp module = currentModule();
        Declaration decl = module.lookup(name, getLanguage(), namespace);
        if (decl != null) {
            return decl;
        }
        Declaration decl2 = module.getNoDefine(name);
        decl2.setIndirectBinding(true);
        return decl2;
    }

    /* access modifiers changed from: 0000 */
    public Syntax check_if_Syntax(Declaration decl) {
        Declaration d = Declaration.followAliases(decl);
        Object obj = null;
        Expression dval = d.getValue();
        if (dval != null && d.getFlag(32768)) {
            try {
                if (decl.getValue() instanceof ReferenceExp) {
                    Declaration context = ((ReferenceExp) decl.getValue()).contextDecl();
                    if (context != null) {
                        this.macroContext = context;
                    } else if (this.current_scope instanceof TemplateScope) {
                        this.macroContext = ((TemplateScope) this.current_scope).macroContext;
                    }
                } else if (this.current_scope instanceof TemplateScope) {
                    this.macroContext = ((TemplateScope) this.current_scope).macroContext;
                }
                obj = dval.eval(this.env);
            } catch (Throwable ex) {
                ex.printStackTrace();
                error('e', "unable to evaluate macro for " + decl.getSymbol());
            }
        } else if (decl.getFlag(32768) && !decl.needsContext()) {
            obj = StaticFieldLocation.make(decl).get(null);
        }
        if (obj instanceof Syntax) {
            return (Syntax) obj;
        }
        return null;
    }

    public Expression rewrite_pair(Pair p, boolean function) {
        Symbol symbol;
        Expression func = rewrite_car(p, true);
        if (func instanceof QuoteExp) {
            Object proc = func.valueIfConstant();
            if (proc instanceof Syntax) {
                return apply_rewrite((Syntax) proc, p);
            }
        }
        if (func instanceof ReferenceExp) {
            ReferenceExp ref = (ReferenceExp) func;
            Declaration decl = ref.getBinding();
            if (decl == null) {
                Object sym = ref.getSymbol();
                if (!(sym instanceof Symbol) || selfEvaluatingSymbol(sym)) {
                    symbol = this.env.getSymbol(sym.toString());
                } else {
                    symbol = (Symbol) sym;
                    String name = symbol.getName();
                }
                Object proc2 = this.env.get(symbol, getLanguage().hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null, null);
                if (proc2 instanceof Syntax) {
                    return apply_rewrite((Syntax) proc2, p);
                }
                if (proc2 instanceof AutoloadProcedure) {
                    try {
                        Object proc3 = ((AutoloadProcedure) proc2).getLoaded();
                    } catch (RuntimeException e) {
                    }
                }
            } else {
                Declaration saveContext = this.macroContext;
                Syntax syntax = check_if_Syntax(decl);
                if (syntax != null) {
                    Expression apply_rewrite = apply_rewrite(syntax, p);
                    this.macroContext = saveContext;
                    return apply_rewrite;
                }
            }
            ref.setProcedureName(true);
            if (getLanguage().hasSeparateFunctionNamespace()) {
                func.setFlag(8);
            }
        }
        Object cdr = p.getCdr();
        int cdr_length = listLength(cdr);
        if (cdr_length == -1) {
            return syntaxError("circular list is not allowed after " + p.getCar());
        }
        if (cdr_length < 0) {
            return syntaxError("dotted list [" + cdr + "] is not allowed after " + p.getCar());
        }
        boolean mapKeywordsToAttributes = false;
        Stack vec = new Stack();
        ScopeExp save_scope = this.current_scope;
        int i = 0;
        while (i < cdr_length) {
            if (cdr instanceof SyntaxForm) {
                SyntaxForm sf = (SyntaxForm) cdr;
                cdr = sf.getDatum();
                setCurrentScope(sf.getScope());
            }
            Pair cdr_pair = (Pair) cdr;
            Expression arg = rewrite_car(cdr_pair, false);
            i++;
            if (mapKeywordsToAttributes) {
                if ((i & 1) == 0) {
                    arg = new ApplyExp((Procedure) MakeAttribute.makeAttribute, (Expression) vec.pop(), arg);
                } else {
                    if (arg instanceof QuoteExp) {
                        Object value = ((QuoteExp) arg).getValue();
                        if ((value instanceof Keyword) && i < cdr_length) {
                            arg = new QuoteExp(((Keyword) value).asSymbol());
                        }
                    }
                    mapKeywordsToAttributes = false;
                }
            }
            vec.addElement(arg);
            cdr = cdr_pair.getCdr();
        }
        Expression[] args = new Expression[vec.size()];
        vec.copyInto(args);
        if (save_scope != this.current_scope) {
            setCurrentScope(save_scope);
        }
        if (!(func instanceof ReferenceExp) || ((ReferenceExp) func).getBinding() != getNamedPartDecl) {
            return ((LispLanguage) getLanguage()).makeApply(func, args);
        }
        Expression part1 = args[0];
        Expression part2 = args[1];
        Symbol sym2 = namespaceResolve(part1, part2);
        if (sym2 != null) {
            return rewrite(sym2, function);
        }
        return CompileNamedPart.makeExp(part1, part2);
    }

    public Namespace namespaceResolvePrefix(Expression context) {
        Object val;
        if (context instanceof ReferenceExp) {
            ReferenceExp rexp = (ReferenceExp) context;
            Declaration decl = rexp.getBinding();
            if (decl == null || decl.getFlag(65536)) {
                Object rsym = rexp.getSymbol();
                val = this.env.get((EnvironmentKey) rsym instanceof Symbol ? (Symbol) rsym : this.env.getSymbol(rsym.toString()), (Object) null);
            } else if (decl.isNamespaceDecl()) {
                val = decl.getConstantValue();
            } else {
                val = null;
            }
            if (val instanceof Namespace) {
                Namespace ns = (Namespace) val;
                String uri = ns.getName();
                if (uri == null || !uri.startsWith("class:")) {
                    return ns;
                }
                return null;
            }
        }
        return null;
    }

    public Symbol namespaceResolve(Namespace ns, Expression member) {
        if (ns == null || !(member instanceof QuoteExp)) {
            return null;
        }
        return ns.getSymbol(((QuoteExp) member).getValue().toString().intern());
    }

    public Symbol namespaceResolve(Expression context, Expression member) {
        return namespaceResolve(namespaceResolvePrefix(context), member);
    }

    public static Object stripSyntax(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        return obj;
    }

    public static Object safeCar(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        if (!(obj instanceof Pair)) {
            return null;
        }
        return stripSyntax(((Pair) obj).getCar());
    }

    public static Object safeCdr(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        if (!(obj instanceof Pair)) {
            return null;
        }
        return stripSyntax(((Pair) obj).getCdr());
    }

    public static int listLength(Object obj) {
        int n = 0;
        Object slow = obj;
        Object fast = obj;
        while (true) {
            if (fast instanceof SyntaxForm) {
                fast = ((SyntaxForm) fast).getDatum();
            } else {
                while (slow instanceof SyntaxForm) {
                    slow = ((SyntaxForm) slow).getDatum();
                }
                if (fast == LList.Empty) {
                    return n;
                }
                if (!(fast instanceof Pair)) {
                    return -1 - n;
                }
                int n2 = n + 1;
                Object next = ((Pair) fast).getCdr();
                while (next instanceof SyntaxForm) {
                    next = ((SyntaxForm) next).getDatum();
                }
                if (next == LList.Empty) {
                    return n2;
                }
                if (!(next instanceof Pair)) {
                    return -1 - n2;
                }
                slow = ((Pair) slow).getCdr();
                fast = ((Pair) next).getCdr();
                n = n2 + 1;
                if (fast == slow) {
                    return Integer.MIN_VALUE;
                }
            }
        }
    }

    public void rewriteInBody(Object exp) {
        if (exp instanceof SyntaxForm) {
            SyntaxForm sf = (SyntaxForm) exp;
            ScopeExp save_scope = this.current_scope;
            try {
                setCurrentScope(sf.getScope());
                rewriteInBody(sf.getDatum());
            } finally {
                setCurrentScope(save_scope);
            }
        } else if (exp instanceof Values) {
            Object[] vals = ((Values) exp).getValues();
            for (Object rewriteInBody : vals) {
                rewriteInBody(rewriteInBody);
            }
        } else {
            this.formStack.add(rewrite(exp, false));
        }
    }

    public Expression rewrite(Object exp) {
        return rewrite(exp, false);
    }

    public Object namespaceResolve(Object name) {
        if (!(name instanceof SimpleSymbol) && (name instanceof Pair)) {
            Pair p = (Pair) name;
            if (safeCar(p) == LispLanguage.lookup_sym && (p.getCdr() instanceof Pair)) {
                Pair p2 = (Pair) p.getCdr();
                if (p2.getCdr() instanceof Pair) {
                    Expression part1 = rewrite(p2.getCar());
                    Expression part2 = rewrite(((Pair) p2.getCdr()).getCar());
                    Symbol sym = namespaceResolve(part1, part2);
                    if (sym != null) {
                        return sym;
                    }
                    String combinedName = CompileNamedPart.combineName(part1, part2);
                    if (combinedName != null) {
                        return Namespace.EmptyNamespace.getSymbol(combinedName);
                    }
                }
            }
        }
        return name;
    }

    /* JADX WARNING: type inference failed for: r0v82, types: [gnu.expr.ThisExp] */
    /* JADX WARNING: type inference failed for: r23v1 */
    /* JADX WARNING: type inference failed for: r0v83, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r0v87, types: [gnu.expr.ReferenceExp] */
    /* JADX WARNING: type inference failed for: r0v103, types: [gnu.expr.ThisExp] */
    /* JADX WARNING: type inference failed for: r0v104, types: [gnu.expr.ReferenceExp] */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x02c6, code lost:
        if ((r16 instanceof gnu.bytecode.ArrayClassLoader) == false) goto L_0x011c;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v103, types: [gnu.expr.ThisExp]
      assigns: [gnu.expr.ThisExp, gnu.expr.ReferenceExp]
      uses: [gnu.expr.ThisExp, ?[OBJECT, ARRAY], gnu.expr.ReferenceExp]
      mth insns count: 388
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x011e  */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewrite(java.lang.Object r36, boolean r37) {
        /*
            r35 = this;
            r0 = r36
            boolean r0 = r0 instanceof kawa.lang.SyntaxForm
            r31 = r0
            if (r31 == 0) goto L_0x003c
            r29 = r36
            kawa.lang.SyntaxForm r29 = (kawa.lang.SyntaxForm) r29
            r0 = r35
            gnu.expr.ScopeExp r0 = r0.current_scope
            r26 = r0
            kawa.lang.TemplateScope r31 = r29.getScope()     // Catch:{ all -> 0x0033 }
            r0 = r35
            r1 = r31
            r0.setCurrentScope(r1)     // Catch:{ all -> 0x0033 }
            java.lang.Object r31 = r29.getDatum()     // Catch:{ all -> 0x0033 }
            r0 = r35
            r1 = r31
            r2 = r37
            gnu.expr.Expression r25 = r0.rewrite(r1, r2)     // Catch:{ all -> 0x0033 }
            r0 = r35
            r1 = r26
            r0.setCurrentScope(r1)
        L_0x0032:
            return r25
        L_0x0033:
            r31 = move-exception
            r0 = r35
            r1 = r26
            r0.setCurrentScope(r1)
            throw r31
        L_0x003c:
            r0 = r36
            boolean r0 = r0 instanceof gnu.lists.PairWithPosition
            r31 = r0
            if (r31 == 0) goto L_0x0055
            r31 = r36
            gnu.lists.PairWithPosition r31 = (gnu.lists.PairWithPosition) r31
            r0 = r35
            r1 = r36
            r2 = r37
            r3 = r31
            gnu.expr.Expression r25 = r0.rewrite_with_position(r1, r2, r3)
            goto L_0x0032
        L_0x0055:
            r0 = r36
            boolean r0 = r0 instanceof gnu.lists.Pair
            r31 = r0
            if (r31 == 0) goto L_0x0064
            gnu.lists.Pair r36 = (gnu.lists.Pair) r36
            gnu.expr.Expression r25 = r35.rewrite_pair(r36, r37)
            goto L_0x0032
        L_0x0064:
            r0 = r36
            boolean r0 = r0 instanceof gnu.mapping.Symbol
            r31 = r0
            if (r31 == 0) goto L_0x0381
            boolean r31 = r35.selfEvaluatingSymbol(r36)
            if (r31 != 0) goto L_0x0381
            r0 = r35
            gnu.expr.NameLookup r0 = r0.lexical
            r31 = r0
            r0 = r31
            r1 = r36
            r2 = r37
            gnu.expr.Declaration r10 = r0.lookup(r1, r2)
            r5 = 0
            r0 = r35
            gnu.expr.ScopeExp r0 = r0.current_scope
            r27 = r0
            if (r10 != 0) goto L_0x0134
            r11 = -1
        L_0x008c:
            r0 = r36
            boolean r0 = r0 instanceof gnu.mapping.Symbol
            r31 = r0
            if (r31 == 0) goto L_0x013e
            r31 = r36
            gnu.mapping.Symbol r31 = (gnu.mapping.Symbol) r31
            boolean r31 = r31.hasEmptyNamespace()
            if (r31 == 0) goto L_0x013e
            java.lang.String r12 = r36.toString()
        L_0x00a2:
            if (r27 == 0) goto L_0x00d2
            r0 = r27
            boolean r0 = r0 instanceof gnu.expr.LambdaExp
            r31 = r0
            if (r31 == 0) goto L_0x018b
            r0 = r27
            gnu.expr.ScopeExp r0 = r0.outer
            r31 = r0
            r0 = r31
            boolean r0 = r0 instanceof gnu.expr.ClassExp
            r31 = r0
            if (r31 == 0) goto L_0x018b
            r31 = r27
            gnu.expr.LambdaExp r31 = (gnu.expr.LambdaExp) r31
            boolean r31 = r31.isClassMethod()
            if (r31 == 0) goto L_0x018b
            r0 = r27
            gnu.expr.ScopeExp r0 = r0.outer
            r31 = r0
            int r31 = gnu.expr.ScopeExp.nesting(r31)
            r0 = r31
            if (r11 < r0) goto L_0x0143
        L_0x00d2:
            if (r10 == 0) goto L_0x01cb
            java.lang.Object r21 = r10.getSymbol()
            r36 = 0
            gnu.expr.ReferenceExp r24 = getOriginalRef(r10)
            if (r24 == 0) goto L_0x00ec
            gnu.expr.Declaration r10 = r24.getBinding()
            if (r10 != 0) goto L_0x00ec
            java.lang.Object r36 = r24.getSymbol()
            r21 = r36
        L_0x00ec:
            r31 = r36
        L_0x00ee:
            r30 = r31
            gnu.mapping.Symbol r30 = (gnu.mapping.Symbol) r30
            gnu.expr.Language r32 = r35.getLanguage()
            boolean r28 = r32.hasSeparateFunctionNamespace()
            if (r10 == 0) goto L_0x0220
            r0 = r35
            gnu.expr.ScopeExp r0 = r0.current_scope
            r31 = r0
            r0 = r31
            boolean r0 = r0 instanceof kawa.lang.TemplateScope
            r31 = r0
            if (r31 == 0) goto L_0x01d1
            boolean r31 = r10.needsContext()
            if (r31 == 0) goto L_0x01d1
            r0 = r35
            gnu.expr.ScopeExp r0 = r0.current_scope
            r31 = r0
            kawa.lang.TemplateScope r31 = (kawa.lang.TemplateScope) r31
            r0 = r31
            gnu.expr.Declaration r5 = r0.macroContext
        L_0x011c:
            if (r10 == 0) goto L_0x035b
            if (r37 != 0) goto L_0x0328
            java.lang.Object r31 = r10.getConstantValue()
            r0 = r31
            boolean r0 = r0 instanceof kawa.standard.object
            r31 = r0
            if (r31 == 0) goto L_0x0328
            java.lang.Class<java.lang.Object> r31 = java.lang.Object.class
            gnu.expr.QuoteExp r25 = gnu.expr.QuoteExp.getInstance(r31)
            goto L_0x0032
        L_0x0134:
            gnu.expr.ScopeExp r0 = r10.context
            r31 = r0
            int r11 = gnu.expr.ScopeExp.nesting(r31)
            goto L_0x008c
        L_0x013e:
            r12 = 0
            r27 = 0
            goto L_0x00a2
        L_0x0143:
            r4 = r27
            gnu.expr.LambdaExp r4 = (gnu.expr.LambdaExp) r4
            r0 = r27
            gnu.expr.ScopeExp r7 = r0.outer
            gnu.expr.ClassExp r7 = (gnu.expr.ClassExp) r7
            gnu.bytecode.ClassType r9 = r7.getClassType()
            gnu.bytecode.Member r22 = gnu.kawa.reflect.SlotGet.lookupMember(r9, r12, r9)
            gnu.expr.LambdaExp r0 = r7.clinitMethod
            r31 = r0
            r0 = r31
            if (r4 == r0) goto L_0x016f
            gnu.expr.LambdaExp r0 = r7.initMethod
            r31 = r0
            r0 = r31
            if (r4 == r0) goto L_0x0193
            gnu.expr.Declaration r0 = r4.nameDecl
            r31 = r0
            boolean r31 = r31.isStatic()
            if (r31 == 0) goto L_0x0193
        L_0x016f:
            r8 = 1
        L_0x0170:
            if (r22 != 0) goto L_0x0198
            if (r8 == 0) goto L_0x0195
            r20 = 83
        L_0x0176:
            r0 = r35
            gnu.expr.Language r0 = r0.language
            r31 = r0
            r0 = r20
            r1 = r31
            gnu.expr.PrimProcedure[] r19 = gnu.kawa.reflect.ClassMethods.getMethods(r9, r12, r0, r9, r1)
            r0 = r19
            int r0 = r0.length
            r31 = r0
            if (r31 != 0) goto L_0x0198
        L_0x018b:
            r0 = r27
            gnu.expr.ScopeExp r0 = r0.outer
            r27 = r0
            goto L_0x00a2
        L_0x0193:
            r8 = 0
            goto L_0x0170
        L_0x0195:
            r20 = 86
            goto L_0x0176
        L_0x0198:
            if (r8 == 0) goto L_0x01bd
            gnu.expr.ReferenceExp r23 = new gnu.expr.ReferenceExp
            gnu.expr.ScopeExp r0 = r4.outer
            r31 = r0
            gnu.expr.ClassExp r31 = (gnu.expr.ClassExp) r31
            r0 = r31
            gnu.expr.Declaration r0 = r0.nameDecl
            r31 = r0
            r0 = r23
            r1 = r31
            r0.<init>(r1)
        L_0x01af:
            gnu.expr.QuoteExp r31 = gnu.expr.QuoteExp.getInstance(r12)
            r0 = r23
            r1 = r31
            gnu.expr.Expression r25 = gnu.kawa.functions.CompileNamedPart.makeExp(r0, r1)
            goto L_0x0032
        L_0x01bd:
            gnu.expr.ThisExp r23 = new gnu.expr.ThisExp
            gnu.expr.Declaration r31 = r4.firstDecl()
            r0 = r23
            r1 = r31
            r0.<init>(r1)
            goto L_0x01af
        L_0x01cb:
            r21 = r36
            r31 = r36
            goto L_0x00ee
        L_0x01d1:
            r32 = 1048576(0x100000, double:5.180654E-318)
            r0 = r32
            boolean r31 = r10.getFlag(r0)
            if (r31 == 0) goto L_0x011c
            boolean r31 = r10.isStatic()
            if (r31 != 0) goto L_0x011c
            gnu.expr.ScopeExp r27 = r35.currentScope()
        L_0x01e6:
            if (r27 != 0) goto L_0x0203
            java.lang.Error r31 = new java.lang.Error
            java.lang.StringBuilder r32 = new java.lang.StringBuilder
            r32.<init>()
            java.lang.String r33 = "internal error: missing "
            java.lang.StringBuilder r32 = r32.append(r33)
            r0 = r32
            java.lang.StringBuilder r32 = r0.append(r10)
            java.lang.String r32 = r32.toString()
            r31.<init>(r32)
            throw r31
        L_0x0203:
            r0 = r27
            gnu.expr.ScopeExp r0 = r0.outer
            r31 = r0
            gnu.expr.ScopeExp r0 = r10.context
            r32 = r0
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x0219
            gnu.expr.Declaration r5 = r27.firstDecl()
            goto L_0x011c
        L_0x0219:
            r0 = r27
            gnu.expr.ScopeExp r0 = r0.outer
            r27 = r0
            goto L_0x01e6
        L_0x0220:
            r0 = r35
            gnu.mapping.Environment r0 = r0.env
            r33 = r0
            if (r37 == 0) goto L_0x027d
            if (r28 == 0) goto L_0x027d
            java.lang.Object r32 = gnu.mapping.EnvironmentKey.FUNCTION
        L_0x022c:
            r0 = r33
            r1 = r30
            r2 = r32
            gnu.mapping.Location r18 = r0.lookup(r1, r2)
            if (r18 == 0) goto L_0x023c
            gnu.mapping.Location r18 = r18.getBase()
        L_0x023c:
            r0 = r18
            boolean r0 = r0 instanceof gnu.kawa.reflect.FieldLocation
            r32 = r0
            if (r32 == 0) goto L_0x030a
            r17 = r18
            gnu.kawa.reflect.FieldLocation r17 = (gnu.kawa.reflect.FieldLocation) r17
            gnu.expr.Declaration r10 = r17.getDeclaration()     // Catch:{ Throwable -> 0x02ce }
            r32 = 0
            r0 = r35
            r1 = r32
            boolean r32 = r0.inlineOk(r1)     // Catch:{ Throwable -> 0x02ce }
            if (r32 != 0) goto L_0x0280
            gnu.expr.Declaration r32 = getNamedPartDecl     // Catch:{ Throwable -> 0x02ce }
            r0 = r32
            if (r10 == r0) goto L_0x0280
            java.lang.String r32 = "objectSyntax"
            java.lang.String r33 = r17.getMemberName()     // Catch:{ Throwable -> 0x02ce }
            boolean r32 = r32.equals(r33)     // Catch:{ Throwable -> 0x02ce }
            if (r32 == 0) goto L_0x027a
            java.lang.String r32 = "kawa.standard.object"
            gnu.bytecode.ClassType r33 = r17.getDeclaringClass()     // Catch:{ Throwable -> 0x02ce }
            java.lang.String r33 = r33.getName()     // Catch:{ Throwable -> 0x02ce }
            boolean r32 = r32.equals(r33)     // Catch:{ Throwable -> 0x02ce }
            if (r32 != 0) goto L_0x0280
        L_0x027a:
            r10 = 0
            goto L_0x011c
        L_0x027d:
            r32 = 0
            goto L_0x022c
        L_0x0280:
            r0 = r35
            boolean r0 = r0.immediate     // Catch:{ Throwable -> 0x02ce }
            r32 = r0
            if (r32 == 0) goto L_0x02a8
            boolean r32 = r10.isStatic()     // Catch:{ Throwable -> 0x02ce }
            if (r32 != 0) goto L_0x011c
            gnu.expr.Declaration r6 = new gnu.expr.Declaration     // Catch:{ Throwable -> 0x02ce }
            java.lang.String r32 = "(module-instance)"
            r0 = r32
            r6.<init>(r0)     // Catch:{ Throwable -> 0x02ce }
            gnu.expr.QuoteExp r32 = new gnu.expr.QuoteExp     // Catch:{ Throwable -> 0x03c7 }
            java.lang.Object r33 = r17.getInstance()     // Catch:{ Throwable -> 0x03c7 }
            r32.<init>(r33)     // Catch:{ Throwable -> 0x03c7 }
            r0 = r32
            r6.setValue(r0)     // Catch:{ Throwable -> 0x03c7 }
            r5 = r6
            goto L_0x011c
        L_0x02a8:
            boolean r32 = r10.isStatic()     // Catch:{ Throwable -> 0x02ce }
            if (r32 == 0) goto L_0x02cb
            java.lang.Class r15 = r17.getRClass()     // Catch:{ Throwable -> 0x02ce }
            if (r15 == 0) goto L_0x02c8
            java.lang.ClassLoader r16 = r15.getClassLoader()     // Catch:{ Throwable -> 0x02ce }
            r0 = r16
            boolean r0 = r0 instanceof gnu.bytecode.ZipLoader     // Catch:{ Throwable -> 0x02ce }
            r32 = r0
            if (r32 != 0) goto L_0x02c8
            r0 = r16
            boolean r0 = r0 instanceof gnu.bytecode.ArrayClassLoader     // Catch:{ Throwable -> 0x02ce }
            r31 = r0
            if (r31 == 0) goto L_0x011c
        L_0x02c8:
            r10 = 0
            goto L_0x011c
        L_0x02cb:
            r10 = 0
            goto L_0x011c
        L_0x02ce:
            r14 = move-exception
        L_0x02cf:
            r32 = 101(0x65, float:1.42E-43)
            java.lang.StringBuilder r33 = new java.lang.StringBuilder
            r33.<init>()
            java.lang.String r34 = "exception loading '"
            java.lang.StringBuilder r33 = r33.append(r34)
            r0 = r33
            r1 = r31
            java.lang.StringBuilder r31 = r0.append(r1)
            java.lang.String r33 = "' - "
            r0 = r31
            r1 = r33
            java.lang.StringBuilder r31 = r0.append(r1)
            java.lang.String r33 = r14.getMessage()
            r0 = r31
            r1 = r33
            java.lang.StringBuilder r31 = r0.append(r1)
            java.lang.String r31 = r31.toString()
            r0 = r35
            r1 = r32
            r2 = r31
            r0.error(r1, r2)
            r10 = 0
            goto L_0x011c
        L_0x030a:
            if (r18 == 0) goto L_0x0312
            boolean r31 = r18.isBound()
            if (r31 != 0) goto L_0x011c
        L_0x0312:
            gnu.expr.Language r31 = r35.getLanguage()
            gnu.kawa.lispexpr.LispLanguage r31 = (gnu.kawa.lispexpr.LispLanguage) r31
            r0 = r31
            r1 = r30
            r2 = r35
            gnu.expr.Expression r13 = r0.checkDefaultBinding(r1, r2)
            if (r13 == 0) goto L_0x011c
            r25 = r13
            goto L_0x0032
        L_0x0328:
            gnu.expr.ScopeExp r31 = r10.getContext()
            r0 = r31
            boolean r0 = r0 instanceof kawa.lang.PatternScope
            r31 = r0
            if (r31 == 0) goto L_0x035b
            java.lang.StringBuilder r31 = new java.lang.StringBuilder
            r31.<init>()
            java.lang.String r32 = "reference to pattern variable "
            java.lang.StringBuilder r31 = r31.append(r32)
            java.lang.String r32 = r10.getName()
            java.lang.StringBuilder r31 = r31.append(r32)
            java.lang.String r32 = " outside syntax template"
            java.lang.StringBuilder r31 = r31.append(r32)
            java.lang.String r31 = r31.toString()
            r0 = r35
            r1 = r31
            gnu.expr.Expression r25 = r0.syntaxError(r1)
            goto L_0x0032
        L_0x035b:
            gnu.expr.ReferenceExp r24 = new gnu.expr.ReferenceExp
            r0 = r24
            r1 = r21
            r0.<init>(r1, r10)
            r0 = r24
            r0.setContextDecl(r5)
            r0 = r24
            r1 = r35
            r0.setLine(r1)
            if (r37 == 0) goto L_0x037d
            if (r28 == 0) goto L_0x037d
            r31 = 8
            r0 = r24
            r1 = r31
            r0.setFlag(r1)
        L_0x037d:
            r25 = r24
            goto L_0x0032
        L_0x0381:
            r0 = r36
            boolean r0 = r0 instanceof gnu.expr.LangExp
            r31 = r0
            if (r31 == 0) goto L_0x039b
            gnu.expr.LangExp r36 = (gnu.expr.LangExp) r36
            java.lang.Object r31 = r36.getLangValue()
            r0 = r35
            r1 = r31
            r2 = r37
            gnu.expr.Expression r25 = r0.rewrite(r1, r2)
            goto L_0x0032
        L_0x039b:
            r0 = r36
            boolean r0 = r0 instanceof gnu.expr.Expression
            r31 = r0
            if (r31 == 0) goto L_0x03a9
            gnu.expr.Expression r36 = (gnu.expr.Expression) r36
            r25 = r36
            goto L_0x0032
        L_0x03a9:
            gnu.expr.Special r31 = gnu.expr.Special.abstractSpecial
            r0 = r36
            r1 = r31
            if (r0 != r1) goto L_0x03b5
            gnu.expr.QuoteExp r25 = gnu.expr.QuoteExp.abstractExp
            goto L_0x0032
        L_0x03b5:
            r0 = r36
            r1 = r35
            java.lang.Object r31 = kawa.lang.Quote.quote(r0, r1)
            r0 = r31
            r1 = r35
            gnu.expr.QuoteExp r25 = gnu.expr.QuoteExp.getInstance(r0, r1)
            goto L_0x0032
        L_0x03c7:
            r14 = move-exception
            r5 = r6
            goto L_0x02cf
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Translator.rewrite(java.lang.Object, boolean):gnu.expr.Expression");
    }

    public static void setLine(Expression exp, Object location) {
        if (location instanceof SourceLocator) {
            exp.setLocation((SourceLocator) location);
        }
    }

    public static void setLine(Declaration decl, Object location) {
        if (location instanceof SourceLocator) {
            decl.setLocation((SourceLocator) location);
        }
    }

    public Object pushPositionOf(Object pair) {
        PairWithPosition saved;
        if (pair instanceof SyntaxForm) {
            pair = ((SyntaxForm) pair).getDatum();
        }
        if (!(pair instanceof PairWithPosition)) {
            return null;
        }
        PairWithPosition ppair = (PairWithPosition) pair;
        if (this.positionPair != null && this.positionPair.getFileName() == getFileName() && this.positionPair.getLineNumber() == getLineNumber() && this.positionPair.getColumnNumber() == getColumnNumber()) {
            saved = this.positionPair;
        } else {
            saved = new PairWithPosition(this, Special.eof, this.positionPair);
        }
        setLine(pair);
        this.positionPair = ppair;
        return saved;
    }

    public void popPositionOf(Object saved) {
        if (saved != null) {
            setLine(saved);
            this.positionPair = (PairWithPosition) saved;
            if (this.positionPair.getCar() == Special.eof) {
                this.positionPair = (PairWithPosition) this.positionPair.getCdr();
            }
        }
    }

    public void setLineOf(Expression exp) {
        if (!(exp instanceof QuoteExp)) {
            exp.setLocation(this);
        }
    }

    public Type exp2Type(Pair typeSpecPair) {
        Object saved = pushPositionOf(typeSpecPair);
        try {
            Expression texp = InlineCalls.inlineCalls(rewrite_car(typeSpecPair, false), this);
            if (texp instanceof ErrorExp) {
                return null;
            }
            Type type = getLanguage().getTypeFor(texp);
            if (type == null) {
                try {
                    Object t = texp.eval(this.env);
                    if (t instanceof Class) {
                        type = Type.make((Class) t);
                    } else if (t instanceof Type) {
                        type = (Type) t;
                    }
                } catch (Throwable th) {
                }
            }
            if (type == null) {
                if (texp instanceof ReferenceExp) {
                    error('e', "unknown type name '" + ((ReferenceExp) texp).getName() + '\'');
                } else {
                    error('e', "invalid type spec (must be \"type\" or 'type or <type>)");
                }
                Type type2 = Type.pointer_type;
                popPositionOf(saved);
                return type2;
            }
            popPositionOf(saved);
            return type;
        } finally {
            popPositionOf(saved);
        }
    }

    public Expression rewrite_with_position(Object exp, boolean function, PairWithPosition pair) {
        Expression result;
        Object saved = pushPositionOf(pair);
        if (exp == pair) {
            try {
                result = rewrite_pair(pair, function);
            } catch (Throwable th) {
                popPositionOf(saved);
                throw th;
            }
        } else {
            result = rewrite(exp, function);
        }
        setLineOf(result);
        popPositionOf(saved);
        return result;
    }

    public static Object wrapSyntax(Object form, SyntaxForm syntax) {
        return (syntax == null || (form instanceof Expression)) ? form : SyntaxForms.fromDatumIfNeeded(form, syntax);
    }

    public Object popForms(int first) {
        Object obj;
        int last = this.formStack.size();
        if (last == first) {
            return Values.empty;
        }
        if (last == first + 1) {
            obj = this.formStack.elementAt(first);
        } else {
            Values vals = new Values();
            for (int i = first; i < last; i++) {
                vals.writeObject(this.formStack.elementAt(i));
            }
            obj = vals;
        }
        this.formStack.setSize(first);
        return obj;
    }

    public void scanForm(Object st, ScopeExp defs) {
        if (st instanceof SyntaxForm) {
            SyntaxForm sf = (SyntaxForm) st;
            ScopeExp save_scope = currentScope();
            try {
                setCurrentScope(sf.getScope());
                int first = this.formStack.size();
                scanForm(sf.getDatum(), defs);
                this.formStack.add(wrapSyntax(popForms(first), sf));
            } finally {
                setCurrentScope(save_scope);
            }
        } else {
            if (st instanceof Values) {
                if (st == Values.empty) {
                    st = QuoteExp.voidExp;
                } else {
                    Object[] vals = ((Values) st).getValues();
                    for (int i = 0; i < vals.length; i++) {
                        scanForm(vals[i], defs);
                    }
                    return;
                }
            }
            if (st instanceof Pair) {
                Pair st_pair = (Pair) st;
                Declaration saveContext = this.macroContext;
                Syntax syntax = null;
                ScopeExp savedScope = this.current_scope;
                Object savedPosition = pushPositionOf(st);
                if ((st instanceof SourceLocator) && defs.getLineNumber() < 0) {
                    defs.setLocation((SourceLocator) st);
                }
                try {
                    Object obj = st_pair.getCar();
                    if (obj instanceof SyntaxForm) {
                        SyntaxForm sf2 = (SyntaxForm) st_pair.getCar();
                        setCurrentScope(sf2.getScope());
                        obj = sf2.getDatum();
                    }
                    if (obj instanceof Pair) {
                        Pair p = (Pair) obj;
                        if (p.getCar() == LispLanguage.lookup_sym && (p.getCdr() instanceof Pair)) {
                            Pair p2 = (Pair) p.getCdr();
                            if (p2.getCdr() instanceof Pair) {
                                Expression part1 = rewrite(p2.getCar());
                                Expression part2 = rewrite(((Pair) p2.getCdr()).getCar());
                                Object value1 = part1.valueIfConstant();
                                Object value2 = part2.valueIfConstant();
                                if (!(value1 instanceof Class) || !(value2 instanceof Symbol)) {
                                    obj = namespaceResolve(part1, part2);
                                } else {
                                    try {
                                        obj = GetNamedPart.getNamedPart(value1, (Symbol) value2);
                                        if (obj instanceof Syntax) {
                                            syntax = (Syntax) obj;
                                        }
                                    } catch (Throwable th) {
                                        obj = null;
                                    }
                                }
                            }
                        }
                    }
                    if ((obj instanceof Symbol) && !selfEvaluatingSymbol(obj)) {
                        Expression func = rewrite(obj, true);
                        if (func instanceof ReferenceExp) {
                            Declaration decl = ((ReferenceExp) func).getBinding();
                            if (decl != null) {
                                syntax = check_if_Syntax(decl);
                            } else {
                                Object obj2 = resolve(obj, true);
                                if (obj2 instanceof Syntax) {
                                    syntax = (Syntax) obj2;
                                }
                            }
                        }
                    } else if (obj == begin.begin) {
                        syntax = (Syntax) obj;
                    }
                    if (syntax != null) {
                        String save_filename = getFileName();
                        int save_line = getLineNumber();
                        int save_column = getColumnNumber();
                        try {
                            setLine((Object) st_pair);
                            syntax.scanForm(st_pair, defs, this);
                            return;
                        } finally {
                            this.macroContext = saveContext;
                            setLine(save_filename, save_line, save_column);
                        }
                    }
                } finally {
                    if (savedScope != this.current_scope) {
                        setCurrentScope(savedScope);
                    }
                    popPositionOf(savedPosition);
                }
            }
            this.formStack.add(st);
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r5v2, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r4v1, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r4v3, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r6v0, types: [gnu.lists.Pair, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r5v9 */
    /* JADX WARNING: type inference failed for: r5v10, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r5v11 */
    /* JADX WARNING: type inference failed for: r5v12 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r5v13 */
    /* JADX WARNING: type inference failed for: r5v14 */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r5v15 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r5v16 */
    /* JADX WARNING: type inference failed for: r5v17 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r5v18 */
    /* JADX WARNING: type inference failed for: r5v19 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v1
      assigns: []
      uses: []
      mth insns count: 94
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 12 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.lists.LList scanBody(java.lang.Object r13, gnu.expr.ScopeExp r14, boolean r15) {
        /*
            r12 = this;
            if (r15 == 0) goto L_0x0036
            gnu.lists.LList r5 = gnu.lists.LList.Empty
        L_0x0004:
            r4 = 0
        L_0x0005:
            gnu.lists.LList r10 = gnu.lists.LList.Empty
            if (r13 == r10) goto L_0x0035
            boolean r10 = r13 instanceof kawa.lang.SyntaxForm
            if (r10 == 0) goto L_0x0056
            r9 = r13
            kawa.lang.SyntaxForm r9 = (kawa.lang.SyntaxForm) r9
            gnu.expr.ScopeExp r8 = r12.current_scope
            kawa.lang.TemplateScope r10 = r9.getScope()     // Catch:{ all -> 0x0051 }
            r12.setCurrentScope(r10)     // Catch:{ all -> 0x0051 }
            java.util.Stack r10 = r12.formStack     // Catch:{ all -> 0x0051 }
            int r1 = r10.size()     // Catch:{ all -> 0x0051 }
            java.lang.Object r10 = r9.getDatum()     // Catch:{ all -> 0x0051 }
            gnu.lists.LList r0 = r12.scanBody(r10, r14, r15)     // Catch:{ all -> 0x0051 }
            if (r15 == 0) goto L_0x003f
            java.lang.Object r0 = kawa.lang.SyntaxForms.fromDatumIfNeeded(r0, r9)     // Catch:{ all -> 0x0051 }
            gnu.lists.LList r0 = (gnu.lists.LList) r0     // Catch:{ all -> 0x0051 }
            if (r4 != 0) goto L_0x0038
            r12.setCurrentScope(r8)
            r5 = r0
        L_0x0035:
            return r5
        L_0x0036:
            r5 = 0
            goto L_0x0004
        L_0x0038:
            r4.setCdrBackdoor(r0)     // Catch:{ all -> 0x0051 }
            r12.setCurrentScope(r8)
            goto L_0x0035
        L_0x003f:
            java.util.Stack r10 = r12.formStack     // Catch:{ all -> 0x0051 }
            java.lang.Object r11 = r12.popForms(r1)     // Catch:{ all -> 0x0051 }
            java.lang.Object r11 = wrapSyntax(r11, r9)     // Catch:{ all -> 0x0051 }
            r10.add(r11)     // Catch:{ all -> 0x0051 }
            r5 = 0
            r12.setCurrentScope(r8)
            goto L_0x0035
        L_0x0051:
            r10 = move-exception
            r12.setCurrentScope(r8)
            throw r10
        L_0x0056:
            boolean r10 = r13 instanceof gnu.lists.Pair
            if (r10 == 0) goto L_0x00bc
            r7 = r13
            gnu.lists.Pair r7 = (gnu.lists.Pair) r7
            java.util.Stack r10 = r12.formStack
            int r1 = r10.size()
            java.lang.Object r10 = r7.getCar()
            r12.scanForm(r10, r14)
            int r10 = r12.getState()
            r11 = 2
            if (r10 != r11) goto L_0x008f
            java.lang.Object r10 = r7.getCar()
            java.lang.Object r11 = r12.pendingForm
            if (r10 == r11) goto L_0x0083
            java.lang.Object r10 = r12.pendingForm
            java.lang.Object r11 = r7.getCdr()
            gnu.lists.Pair r7 = makePair(r7, r10, r11)
        L_0x0083:
            gnu.lists.Pair r10 = new gnu.lists.Pair
            kawa.standard.begin r11 = kawa.standard.begin.begin
            r10.<init>(r11, r7)
            r12.pendingForm = r10
            gnu.lists.LList r5 = gnu.lists.LList.Empty
            goto L_0x0035
        L_0x008f:
            java.util.Stack r10 = r12.formStack
            int r2 = r10.size()
            if (r15 == 0) goto L_0x00b6
            r3 = r1
        L_0x0098:
            if (r3 >= r2) goto L_0x00b1
            java.util.Stack r10 = r12.formStack
            java.lang.Object r10 = r10.elementAt(r3)
            gnu.lists.LList r11 = gnu.lists.LList.Empty
            gnu.lists.Pair r6 = makePair(r7, r10, r11)
            if (r4 != 0) goto L_0x00ad
            r5 = r6
        L_0x00a9:
            r4 = r6
            int r3 = r3 + 1
            goto L_0x0098
        L_0x00ad:
            r4.setCdrBackdoor(r6)
            goto L_0x00a9
        L_0x00b1:
            java.util.Stack r10 = r12.formStack
            r10.setSize(r1)
        L_0x00b6:
            java.lang.Object r13 = r7.getCdr()
            goto L_0x0005
        L_0x00bc:
            java.util.Stack r10 = r12.formStack
            java.lang.String r11 = "body is not a proper list"
            gnu.expr.Expression r11 = r12.syntaxError(r11)
            r10.add(r11)
            goto L_0x0035
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Translator.scanBody(java.lang.Object, gnu.expr.ScopeExp, boolean):gnu.lists.LList");
    }

    public static Pair makePair(Pair pair, Object car, Object cdr) {
        if (pair instanceof PairWithPosition) {
            return new PairWithPosition((PairWithPosition) pair, car, cdr);
        }
        return new Pair(car, cdr);
    }

    public Expression rewrite_body(Object exp) {
        Object saved = pushPositionOf(exp);
        LetExp defs = new LetExp(null);
        int first = this.formStack.size();
        defs.outer = this.current_scope;
        this.current_scope = defs;
        try {
            LList list = scanBody(exp, defs, true);
            if (list.isEmpty()) {
                this.formStack.add(syntaxError("body with no expressions"));
            }
            int ndecls = defs.countNonDynamicDecls();
            if (ndecls != 0) {
                Expression[] inits = new Expression[ndecls];
                int i = ndecls;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    inits[i] = QuoteExp.undefined_exp;
                }
                defs.inits = inits;
            }
            rewriteBody(list);
            Expression body = makeBody(first, null);
            setLineOf(body);
            if (ndecls == 0) {
                return body;
            }
            defs.body = body;
            setLineOf(defs);
            pop(defs);
            popPositionOf(saved);
            return defs;
        } finally {
            pop(defs);
            popPositionOf(saved);
        }
    }

    /* JADX INFO: finally extract failed */
    private void rewriteBody(LList forms) {
        while (forms != LList.Empty) {
            Pair pair = (Pair) forms;
            Object saved = pushPositionOf(pair);
            try {
                rewriteInBody(pair.getCar());
                popPositionOf(saved);
                forms = (LList) pair.getCdr();
            } catch (Throwable th) {
                popPositionOf(saved);
                throw th;
            }
        }
    }

    private Expression makeBody(int first, ScopeExp scope) {
        int nforms = this.formStack.size() - first;
        if (nforms == 0) {
            return QuoteExp.voidExp;
        }
        if (nforms == 1) {
            return (Expression) this.formStack.pop();
        }
        Expression[] exps = new Expression[nforms];
        for (int i = 0; i < nforms; i++) {
            exps[i] = (Expression) this.formStack.elementAt(first + i);
        }
        this.formStack.setSize(first);
        if (scope instanceof ModuleExp) {
            return new ApplyExp((Procedure) AppendValues.appendValues, exps);
        }
        return ((LispLanguage) getLanguage()).makeBody(exps);
    }

    public void noteAccess(Object name, ScopeExp scope) {
        if (this.notedAccess == null) {
            this.notedAccess = new Vector();
        }
        this.notedAccess.addElement(name);
        this.notedAccess.addElement(scope);
    }

    public void processAccesses() {
        if (this.notedAccess != null) {
            int sz = this.notedAccess.size();
            ScopeExp saveScope = this.current_scope;
            for (int i = 0; i < sz; i += 2) {
                Object name = this.notedAccess.elementAt(i);
                ScopeExp scope = (ScopeExp) this.notedAccess.elementAt(i + 1);
                if (this.current_scope != scope) {
                    setCurrentScope(scope);
                }
                Declaration decl = this.lexical.lookup(name, -1);
                if (decl != null && !decl.getFlag(65536)) {
                    decl.getContext().currentLambda().capture(decl);
                    decl.setCanRead(true);
                    decl.setSimple(false);
                    decl.setFlag(524288);
                }
            }
            if (this.current_scope != saveScope) {
                setCurrentScope(saveScope);
            }
        }
    }

    public void finishModule(ModuleExp mexp) {
        boolean moduleStatic = mexp.isStatic();
        for (Declaration decl = mexp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.getFlag(512)) {
                String msg1 = "'";
                String msg2 = decl.getFlag(1024) ? "' exported but never defined" : decl.getFlag(2048) ? "' declared static but never defined" : "' declared but never defined";
                error('e', decl, msg1, msg2);
            }
            if (mexp.getFlag(16384) || (this.generateMain && !this.immediate)) {
                if (!decl.getFlag(1024)) {
                    decl.setPrivate(true);
                } else if (decl.isPrivate()) {
                    if (decl.getFlag(16777216)) {
                        error('e', decl, "'", "' is declared both private and exported");
                    }
                    decl.setPrivate(false);
                }
            }
            if (moduleStatic) {
                decl.setFlag(2048);
            } else if ((mexp.getFlag(65536) && !decl.getFlag(2048)) || Compilation.moduleStatic < 0 || mexp.getFlag(131072)) {
                decl.setFlag(4096);
            }
        }
    }

    static void vectorReverse(Vector vec, int start, int count) {
        int j = count / 2;
        int last = (start + count) - 1;
        for (int i = 0; i < j; i++) {
            Object tmp = vec.elementAt(start + i);
            vec.setElementAt(vec.elementAt(last - i), start + i);
            vec.setElementAt(tmp, last - i);
        }
    }

    public void resolveModule(ModuleExp mexp) {
        int numPending;
        if (this.pendingImports == null) {
            numPending = 0;
        } else {
            numPending = this.pendingImports.size();
        }
        int i = 0;
        while (i < numPending) {
            int i2 = i + 1;
            ModuleInfo info = (ModuleInfo) this.pendingImports.elementAt(i);
            int i3 = i2 + 1;
            ScopeExp defs = (ScopeExp) this.pendingImports.elementAt(i2);
            int i4 = i3 + 1;
            Expression posExp = (Expression) this.pendingImports.elementAt(i3);
            i = i4 + 1;
            Integer savedSize = (Integer) this.pendingImports.elementAt(i4);
            if (mexp == defs) {
                ReferenceExp referenceExp = new ReferenceExp((Object) null);
                referenceExp.setLine((Compilation) this);
                setLine(posExp);
                int beforeSize = this.formStack.size();
                require.importDefinitions(null, info, null, this.formStack, defs, this);
                int desiredPosition = savedSize.intValue();
                if (savedSize.intValue() != beforeSize) {
                    int curSize = this.formStack.size();
                    int count = curSize - desiredPosition;
                    vectorReverse(this.formStack, desiredPosition, beforeSize - desiredPosition);
                    vectorReverse(this.formStack, beforeSize, curSize - beforeSize);
                    vectorReverse(this.formStack, desiredPosition, count);
                }
                setLine((Expression) referenceExp);
            }
        }
        this.pendingImports = null;
        processAccesses();
        setModule(mexp);
        Compilation save_comp = Compilation.setSaveCurrent(this);
        try {
            rewriteInBody(popForms(this.firstForm));
            mexp.body = makeBody(this.firstForm, mexp);
            if (!this.immediate) {
                this.lexical.pop((ScopeExp) mexp);
            }
        } finally {
            Compilation.restoreCurrent(save_comp);
        }
    }

    public Declaration makeRenamedAlias(Declaration decl, ScopeExp templateScope) {
        return templateScope == null ? decl : makeRenamedAlias(decl.getSymbol(), decl, templateScope);
    }

    public Declaration makeRenamedAlias(Object name, Declaration decl, ScopeExp templateScope) {
        Declaration alias = new Declaration(name);
        alias.setAlias(true);
        alias.setPrivate(true);
        alias.context = templateScope;
        ReferenceExp ref = new ReferenceExp(decl);
        ref.setDontDereference(true);
        alias.noteValue(ref);
        return alias;
    }

    public void pushRenamedAlias(Declaration alias) {
        Declaration decl = getOriginalRef(alias).getBinding();
        ScopeExp templateScope = alias.context;
        decl.setSymbol(null);
        Declaration old = templateScope.lookup(decl.getSymbol());
        if (old != null) {
            templateScope.remove(old);
        }
        templateScope.addDeclaration(alias);
        if (this.renamedAliasStack == null) {
            this.renamedAliasStack = new Stack();
        }
        this.renamedAliasStack.push(old);
        this.renamedAliasStack.push(alias);
        this.renamedAliasStack.push(templateScope);
    }

    public void popRenamedAlias(int count) {
        while (true) {
            count--;
            if (count >= 0) {
                ScopeExp templateScope = (ScopeExp) this.renamedAliasStack.pop();
                Declaration alias = (Declaration) this.renamedAliasStack.pop();
                getOriginalRef(alias).getBinding().setSymbol(alias.getSymbol());
                templateScope.remove(alias);
                Object old = this.renamedAliasStack.pop();
                if (old != null) {
                    templateScope.addDeclaration((Declaration) old);
                }
            } else {
                return;
            }
        }
    }

    public Declaration define(Object name, SyntaxForm nameSyntax, ScopeExp defs) {
        Object declName;
        boolean aliasNeeded = (nameSyntax == null || nameSyntax.getScope() == currentScope()) ? false : true;
        if (aliasNeeded) {
            declName = new String(name.toString());
        } else {
            declName = name;
        }
        Declaration decl = defs.getDefine(declName, 'w', this);
        if (aliasNeeded) {
            nameSyntax.getScope().addDeclaration(makeRenamedAlias(name, decl, nameSyntax.getScope()));
        }
        push(decl);
        return decl;
    }
}
