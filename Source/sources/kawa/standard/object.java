package kawa.standard;

import gnu.bytecode.Type;
import gnu.expr.BeginExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.ObjectExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.expr.ThisExp;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class object extends Syntax {
    public static final Keyword accessKeyword = Keyword.make("access");
    public static final Keyword allocationKeyword = Keyword.make("allocation");
    public static final Keyword classNameKeyword = Keyword.make("class-name");
    static final Symbol coloncolon = Namespace.EmptyNamespace.getSymbol("::");
    static final Keyword initKeyword = Keyword.make("init");
    static final Keyword init_formKeyword = Keyword.make("init-form");
    static final Keyword init_keywordKeyword = Keyword.make("init-keyword");
    static final Keyword init_valueKeyword = Keyword.make("init-value");
    static final Keyword initformKeyword = Keyword.make("initform");
    public static final Keyword interfaceKeyword = Keyword.make("interface");
    public static final object objectSyntax = new object(SchemeCompilation.lambda);
    public static final Keyword throwsKeyword = Keyword.make("throws");
    static final Keyword typeKeyword = Keyword.make("type");
    Lambda lambda;

    static {
        objectSyntax.setName("object");
    }

    public object(Lambda lambda2) {
        this.lambda = lambda2;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        if (!(form.getCdr() instanceof Pair)) {
            return tr.syntaxError("missing superclass specification in object");
        }
        Pair pair = (Pair) form.getCdr();
        ObjectExp oexp = new ObjectExp();
        if (pair.getCar() instanceof FString) {
            if (!(pair.getCdr() instanceof Pair)) {
                return tr.syntaxError("missing superclass specification after object class name");
            }
            pair = (Pair) pair.getCdr();
        }
        Object[] saved = scanClassDef(pair, oexp, tr);
        if (saved == null) {
            return oexp;
        }
        rewriteClassDef(saved, tr);
        return oexp;
    }

    /* JADX WARNING: type inference failed for: r43v4, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r19v0, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v17, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v18, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v19, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r16v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r19v1 */
    /* JADX WARNING: type inference failed for: r16v1 */
    /* JADX WARNING: type inference failed for: r16v2 */
    /* JADX WARNING: type inference failed for: r0v20 */
    /* JADX WARNING: type inference failed for: r16v3, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v21 */
    /* JADX WARNING: type inference failed for: r0v24, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v25, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v26, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v27, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r0v29, types: [gnu.expr.Declaration] */
    /* JADX WARNING: type inference failed for: r5v40, types: [java.lang.Boolean] */
    /* JADX WARNING: type inference failed for: r5v41, types: [java.lang.Boolean] */
    /* JADX WARNING: type inference failed for: r5v42, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r5v43 */
    /* JADX WARNING: type inference failed for: r16v4 */
    /* JADX WARNING: type inference failed for: r5v44 */
    /* JADX WARNING: type inference failed for: r43v5 */
    /* JADX WARNING: type inference failed for: r16v5, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v36 */
    /* JADX WARNING: type inference failed for: r0v37 */
    /* JADX WARNING: type inference failed for: r16v6 */
    /* JADX WARNING: type inference failed for: r43v7 */
    /* JADX WARNING: type inference failed for: r16v7, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r16v8 */
    /* JADX WARNING: type inference failed for: r0v39 */
    /* JADX WARNING: type inference failed for: r43v9 */
    /* JADX WARNING: type inference failed for: r16v9, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r16v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r19v2 */
    /* JADX WARNING: type inference failed for: r16v11 */
    /* JADX WARNING: type inference failed for: r19v4 */
    /* JADX WARNING: type inference failed for: r16v12 */
    /* JADX WARNING: type inference failed for: r16v13 */
    /* JADX WARNING: type inference failed for: r5v98 */
    /* JADX WARNING: type inference failed for: r5v99 */
    /* JADX WARNING: type inference failed for: r16v14 */
    /* JADX WARNING: type inference failed for: r16v15 */
    /* JADX WARNING: type inference failed for: r16v16 */
    /* JADX WARNING: type inference failed for: r16v17 */
    /* JADX WARNING: type inference failed for: r16v18 */
    /* JADX WARNING: type inference failed for: r16v19 */
    /* JADX WARNING: type inference failed for: r16v20 */
    /* JADX WARNING: type inference failed for: r16v21 */
    /* JADX WARNING: type inference failed for: r16v22 */
    /* JADX WARNING: type inference failed for: r16v23 */
    /* JADX WARNING: type inference failed for: r16v24 */
    /* JADX WARNING: type inference failed for: r16v25 */
    /* JADX WARNING: type inference failed for: r16v26 */
    /* JADX WARNING: type inference failed for: r16v27 */
    /* JADX WARNING: type inference failed for: r16v28 */
    /* JADX WARNING: type inference failed for: r16v29 */
    /* JADX WARNING: type inference failed for: r19v5 */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x0327, code lost:
        r16 = 0;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r16v1
      assigns: []
      uses: []
      mth insns count: 436
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
    /* JADX WARNING: Unknown variable types count: 33 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object[] scanClassDef(gnu.lists.Pair r43, gnu.expr.ClassExp r44, kawa.lang.Translator r45) {
        /*
            r42 = this;
            r45.mustCompileHere()
            java.lang.Object r39 = r43.getCar()
            java.lang.Object r18 = r43.getCdr()
            r17 = 0
            r27 = 0
            r25 = 0
            r6 = 0
            java.util.Vector r21 = new java.util.Vector
            r5 = 20
            r0 = r21
            r0.<init>(r5)
            r31 = r18
        L_0x001e:
            gnu.lists.LList r5 = gnu.lists.LList.Empty
            r0 = r31
            if (r0 == r5) goto L_0x042c
        L_0x0024:
            r0 = r31
            boolean r5 = r0 instanceof kawa.lang.SyntaxForm
            if (r5 == 0) goto L_0x0031
            kawa.lang.SyntaxForm r31 = (kawa.lang.SyntaxForm) r31
            java.lang.Object r31 = r31.getDatum()
            goto L_0x0024
        L_0x0031:
            r0 = r31
            boolean r5 = r0 instanceof gnu.lists.Pair
            if (r5 != 0) goto L_0x0043
            r5 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "object member not a list"
            r0 = r45
            r0.error(r5, r8)
            r33 = 0
        L_0x0042:
            return r33
        L_0x0043:
            r43 = r31
            gnu.lists.Pair r43 = (gnu.lists.Pair) r43
            java.lang.Object r32 = r43.getCar()
        L_0x004b:
            r0 = r32
            boolean r5 = r0 instanceof kawa.lang.SyntaxForm
            if (r5 == 0) goto L_0x0058
            kawa.lang.SyntaxForm r32 = (kawa.lang.SyntaxForm) r32
            java.lang.Object r32 = r32.getDatum()
            goto L_0x004b
        L_0x0058:
            java.lang.Object r31 = r43.getCdr()
            r0 = r45
            r1 = r43
            java.lang.Object r34 = r0.pushPositionOf(r1)
            r0 = r32
            boolean r5 = r0 instanceof gnu.expr.Keyword
            if (r5 == 0) goto L_0x011a
        L_0x006a:
            r0 = r31
            boolean r5 = r0 instanceof kawa.lang.SyntaxForm
            if (r5 == 0) goto L_0x0077
            kawa.lang.SyntaxForm r31 = (kawa.lang.SyntaxForm) r31
            java.lang.Object r31 = r31.getDatum()
            goto L_0x006a
        L_0x0077:
            r0 = r31
            boolean r5 = r0 instanceof gnu.lists.Pair
            if (r5 == 0) goto L_0x011a
            gnu.expr.Keyword r5 = interfaceKeyword
            r0 = r32
            if (r0 != r5) goto L_0x00b0
            r5 = r31
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r41 = r5.getCar()
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            r0 = r41
            if (r0 != r5) goto L_0x00a7
            r5 = 65536(0x10000, float:9.18355E-41)
            r0 = r44
            r0.setFlag(r5)
        L_0x0098:
            gnu.lists.Pair r31 = (gnu.lists.Pair) r31
            java.lang.Object r31 = r31.getCdr()
            r0 = r45
            r1 = r34
            r0.popPositionOf(r1)
            goto L_0x001e
        L_0x00a7:
            r5 = 32768(0x8000, float:4.5918E-41)
            r0 = r44
            r0.setFlag(r5)
            goto L_0x0098
        L_0x00b0:
            gnu.expr.Keyword r5 = classNameKeyword
            r0 = r32
            if (r0 != r5) goto L_0x00d2
            if (r17 == 0) goto L_0x00c1
            r5 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "duplicate class-name specifiers"
            r0 = r45
            r0.error(r5, r8)
        L_0x00c1:
            r17 = r31
            gnu.lists.Pair r31 = (gnu.lists.Pair) r31
            java.lang.Object r31 = r31.getCdr()
            r0 = r45
            r1 = r34
            r0.popPositionOf(r1)
            goto L_0x001e
        L_0x00d2:
            gnu.expr.Keyword r5 = accessKeyword
            r0 = r32
            if (r0 != r5) goto L_0x011a
            r0 = r45
            r1 = r31
            java.lang.Object r35 = r0.pushPositionOf(r1)
            r5 = r31
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r5 = r5.getCar()
            r8 = 25820135424(0x603000000, double:1.2756841884E-313)
            java.lang.String r10 = "class"
            r11 = r45
            long r6 = addAccessFlags(r5, r6, r8, r10, r11)
            r0 = r44
            gnu.expr.Declaration r5 = r0.nameDecl
            if (r5 != 0) goto L_0x0104
            r5 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "access specifier for anonymous class"
            r0 = r45
            r0.error(r5, r8)
        L_0x0104:
            r0 = r45
            r1 = r35
            r0.popPositionOf(r1)
            gnu.lists.Pair r31 = (gnu.lists.Pair) r31
            java.lang.Object r31 = r31.getCdr()
            r0 = r45
            r1 = r34
            r0.popPositionOf(r1)
            goto L_0x001e
        L_0x011a:
            r0 = r32
            boolean r5 = r0 instanceof gnu.lists.Pair
            if (r5 != 0) goto L_0x012d
            r5 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "object member not a list"
            r0 = r45
            r0.error(r5, r8)
            r33 = 0
            goto L_0x0042
        L_0x012d:
            r43 = r32
            gnu.lists.Pair r43 = (gnu.lists.Pair) r43
            java.lang.Object r32 = r43.getCar()
        L_0x0135:
            r0 = r32
            boolean r5 = r0 instanceof kawa.lang.SyntaxForm
            if (r5 == 0) goto L_0x0142
            kawa.lang.SyntaxForm r32 = (kawa.lang.SyntaxForm) r32
            java.lang.Object r32 = r32.getDatum()
            goto L_0x0135
        L_0x0142:
            r0 = r32
            boolean r5 = r0 instanceof java.lang.String
            if (r5 != 0) goto L_0x0154
            r0 = r32
            boolean r5 = r0 instanceof gnu.mapping.Symbol
            if (r5 != 0) goto L_0x0154
            r0 = r32
            boolean r5 = r0 instanceof gnu.expr.Keyword
            if (r5 == 0) goto L_0x03d7
        L_0x0154:
            r40 = 0
            r38 = r32
            r4 = 0
            r10 = 0
            r0 = r38
            boolean r5 = r0 instanceof gnu.expr.Keyword
            if (r5 == 0) goto L_0x0180
            r19 = 0
            r16 = r43
        L_0x0165:
            r30 = 0
            r37 = 0
            r20 = 0
        L_0x016b:
            gnu.lists.LList r5 = gnu.lists.LList.Empty
            r0 = r16
            if (r0 == r5) goto L_0x0329
            r5 = r16
        L_0x0173:
            boolean r8 = r5 instanceof kawa.lang.SyntaxForm
            if (r8 == 0) goto L_0x01a2
            kawa.lang.SyntaxForm r5 = (kawa.lang.SyntaxForm) r5
            java.lang.Object r16 = r5.getDatum()
            r5 = r16
            goto L_0x0173
        L_0x0180:
            r0 = r44
            r1 = r38
            gnu.expr.Declaration r19 = r0.addDeclaration(r1)
            r5 = 0
            r0 = r19
            r0.setSimple(r5)
            r12 = 1048576(0x100000, double:5.180654E-318)
            r0 = r19
            r0.setFlag(r12)
            r0 = r19
            r1 = r43
            kawa.lang.Translator.setLine(r0, r1)
            java.lang.Object r16 = r43.getCdr()
            goto L_0x0165
        L_0x01a2:
            r43 = r5
            gnu.lists.Pair r43 = (gnu.lists.Pair) r43
            r24 = r43
            java.lang.Object r23 = r43.getCar()
        L_0x01ac:
            r0 = r23
            boolean r5 = r0 instanceof kawa.lang.SyntaxForm
            if (r5 == 0) goto L_0x01b9
            kawa.lang.SyntaxForm r23 = (kawa.lang.SyntaxForm) r23
            java.lang.Object r23 = r23.getDatum()
            goto L_0x01ac
        L_0x01b9:
            r0 = r45
            r1 = r43
            java.lang.Object r35 = r0.pushPositionOf(r1)
            java.lang.Object r16 = r43.getCdr()
            gnu.mapping.Symbol r5 = coloncolon
            r0 = r23
            if (r0 == r5) goto L_0x01d1
            r0 = r23
            boolean r5 = r0 instanceof gnu.expr.Keyword
            if (r5 == 0) goto L_0x02f5
        L_0x01d1:
            r0 = r16
            boolean r5 = r0 instanceof gnu.lists.Pair
            if (r5 == 0) goto L_0x02f5
            int r30 = r30 + 1
            r43 = r16
            gnu.lists.Pair r43 = (gnu.lists.Pair) r43
            java.lang.Object r9 = r43.getCar()
            java.lang.Object r16 = r43.getCdr()
            gnu.mapping.Symbol r5 = coloncolon
            r0 = r23
            if (r0 == r5) goto L_0x01f1
            gnu.expr.Keyword r5 = typeKeyword
            r0 = r23
            if (r0 != r5) goto L_0x01fc
        L_0x01f1:
            r40 = r43
        L_0x01f3:
            r0 = r45
            r1 = r35
            r0.popPositionOf(r1)
            goto L_0x016b
        L_0x01fc:
            gnu.expr.Keyword r5 = allocationKeyword
            r0 = r23
            if (r0 != r5) goto L_0x0252
            if (r4 == 0) goto L_0x020d
            r5 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "duplicate allocation: specification"
            r0 = r45
            r0.error(r5, r8)
        L_0x020d:
            java.lang.String r5 = "class"
            r0 = r45
            boolean r5 = matches(r9, r5, r0)
            if (r5 != 0) goto L_0x0221
            java.lang.String r5 = "static"
            r0 = r45
            boolean r5 = matches(r9, r5, r0)
            if (r5 == 0) goto L_0x0224
        L_0x0221:
            r4 = 2048(0x800, float:2.87E-42)
            goto L_0x01f3
        L_0x0224:
            java.lang.String r5 = "instance"
            r0 = r45
            boolean r5 = matches(r9, r5, r0)
            if (r5 == 0) goto L_0x0231
            r4 = 4096(0x1000, float:5.74E-42)
            goto L_0x01f3
        L_0x0231:
            r5 = 101(0x65, float:1.42E-43)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r12 = "unknown allocation kind '"
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r12 = "'"
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.String r8 = r8.toString()
            r0 = r45
            r0.error(r5, r8)
            goto L_0x01f3
        L_0x0252:
            gnu.expr.Keyword r5 = initKeyword
            r0 = r23
            if (r0 == r5) goto L_0x026a
            gnu.expr.Keyword r5 = initformKeyword
            r0 = r23
            if (r0 == r5) goto L_0x026a
            gnu.expr.Keyword r5 = init_formKeyword
            r0 = r23
            if (r0 == r5) goto L_0x026a
            gnu.expr.Keyword r5 = init_valueKeyword
            r0 = r23
            if (r0 != r5) goto L_0x0281
        L_0x026a:
            if (r37 == 0) goto L_0x0275
            r5 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "duplicate initialization"
            r0 = r45
            r0.error(r5, r8)
        L_0x0275:
            r37 = 1
            gnu.expr.Keyword r5 = initKeyword
            r0 = r23
            if (r0 == r5) goto L_0x01f3
            r20 = r43
            goto L_0x01f3
        L_0x0281:
            gnu.expr.Keyword r5 = init_keywordKeyword
            r0 = r23
            if (r0 != r5) goto L_0x02ad
            boolean r5 = r9 instanceof gnu.expr.Keyword
            if (r5 != 0) goto L_0x0296
            r5 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "invalid 'init-keyword' - not a keyword"
            r0 = r45
            r0.error(r5, r8)
            goto L_0x01f3
        L_0x0296:
            gnu.expr.Keyword r9 = (gnu.expr.Keyword) r9
            java.lang.String r5 = r9.getName()
            java.lang.String r8 = r38.toString()
            if (r5 == r8) goto L_0x01f3
            r5 = 119(0x77, float:1.67E-43)
            java.lang.String r8 = "init-keyword option ignored"
            r0 = r45
            r0.error(r5, r8)
            goto L_0x01f3
        L_0x02ad:
            gnu.expr.Keyword r5 = accessKeyword
            r0 = r23
            if (r0 != r5) goto L_0x02d1
            r0 = r45
            r1 = r43
            java.lang.Object r36 = r0.pushPositionOf(r1)
            r12 = 32463912960(0x78f000000, double:1.6039304123E-313)
            java.lang.String r14 = "field"
            r15 = r45
            long r10 = addAccessFlags(r9, r10, r12, r14, r15)
            r0 = r45
            r1 = r36
            r0.popPositionOf(r1)
            goto L_0x01f3
        L_0x02d1:
            r5 = 119(0x77, float:1.67E-43)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r12 = "unknown slot keyword '"
            java.lang.StringBuilder r8 = r8.append(r12)
            r0 = r23
            java.lang.StringBuilder r8 = r8.append(r0)
            java.lang.String r12 = "'"
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.String r8 = r8.toString()
            r0 = r45
            r0.error(r5, r8)
            goto L_0x01f3
        L_0x02f5:
            gnu.lists.LList r5 = gnu.lists.LList.Empty
            r0 = r16
            if (r0 != r5) goto L_0x0303
            if (r37 != 0) goto L_0x0303
            r20 = r24
            r37 = 1
            goto L_0x01f3
        L_0x0303:
            r0 = r16
            boolean r5 = r0 instanceof gnu.lists.Pair
            if (r5 == 0) goto L_0x0327
            if (r30 != 0) goto L_0x0327
            if (r37 != 0) goto L_0x0327
            if (r40 != 0) goto L_0x0327
            r43 = r16
            gnu.lists.Pair r43 = (gnu.lists.Pair) r43
            java.lang.Object r5 = r43.getCdr()
            gnu.lists.LList r8 = gnu.lists.LList.Empty
            if (r5 != r8) goto L_0x0327
            r40 = r24
            r20 = r43
            java.lang.Object r16 = r43.getCdr()
            r37 = 1
            goto L_0x01f3
        L_0x0327:
            r16 = 0
        L_0x0329:
            gnu.lists.LList r5 = gnu.lists.LList.Empty
            r0 = r16
            if (r0 == r5) goto L_0x036c
            r8 = 101(0x65, float:1.42E-43)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r12 = "invalid argument list for slot '"
            java.lang.StringBuilder r5 = r5.append(r12)
            r0 = r38
            java.lang.StringBuilder r5 = r5.append(r0)
            r12 = 39
            java.lang.StringBuilder r5 = r5.append(r12)
            java.lang.String r12 = " args:"
            java.lang.StringBuilder r12 = r5.append(r12)
            if (r16 != 0) goto L_0x0363
            java.lang.String r5 = "null"
        L_0x0352:
            java.lang.StringBuilder r5 = r12.append(r5)
            java.lang.String r5 = r5.toString()
            r0 = r45
            r0.error(r8, r5)
            r33 = 0
            goto L_0x0042
        L_0x0363:
            java.lang.Class r5 = r16.getClass()
            java.lang.String r5 = r5.getName()
            goto L_0x0352
        L_0x036c:
            if (r37 == 0) goto L_0x0384
            r5 = 2048(0x800, float:2.87E-42)
            if (r4 != r5) goto L_0x0395
            r22 = 1
        L_0x0374:
            if (r19 == 0) goto L_0x0398
            r5 = r19
        L_0x0378:
            r0 = r21
            r0.addElement(r5)
            r0 = r21
            r1 = r20
            r0.addElement(r1)
        L_0x0384:
            if (r19 != 0) goto L_0x03a0
            if (r37 != 0) goto L_0x03ce
            r5 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "missing field name"
            r0 = r45
            r0.error(r5, r8)
            r33 = 0
            goto L_0x0042
        L_0x0395:
            r22 = 0
            goto L_0x0374
        L_0x0398:
            if (r22 == 0) goto L_0x039d
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            goto L_0x0378
        L_0x039d:
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            goto L_0x0378
        L_0x03a0:
            if (r40 == 0) goto L_0x03af
            r0 = r45
            r1 = r40
            gnu.bytecode.Type r5 = r0.exp2Type(r1)
            r0 = r19
            r0.setType(r5)
        L_0x03af:
            if (r4 == 0) goto L_0x03b7
            long r12 = (long) r4
            r0 = r19
            r0.setFlag(r12)
        L_0x03b7:
            r12 = 0
            int r5 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r5 == 0) goto L_0x03c2
            r0 = r19
            r0.setFlag(r10)
        L_0x03c2:
            r5 = 1
            r0 = r19
            r0.setCanRead(r5)
            r5 = 1
            r0 = r19
            r0.setCanWrite(r5)
        L_0x03ce:
            r0 = r45
            r1 = r34
            r0.popPositionOf(r1)
            goto L_0x001e
        L_0x03d7:
            r0 = r32
            boolean r5 = r0 instanceof gnu.lists.Pair
            if (r5 == 0) goto L_0x0422
            r29 = r32
            gnu.lists.Pair r29 = (gnu.lists.Pair) r29
            java.lang.Object r28 = r29.getCar()
            r0 = r28
            boolean r5 = r0 instanceof java.lang.String
            if (r5 != 0) goto L_0x03fe
            r0 = r28
            boolean r5 = r0 instanceof gnu.mapping.Symbol
            if (r5 != 0) goto L_0x03fe
            r5 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "missing method name"
            r0 = r45
            r0.error(r5, r8)
            r33 = 0
            goto L_0x0042
        L_0x03fe:
            gnu.expr.LambdaExp r26 = new gnu.expr.LambdaExp
            r26.<init>()
            r0 = r44
            r1 = r26
            r2 = r28
            gnu.expr.Declaration r19 = r0.addMethod(r1, r2)
            r0 = r19
            r1 = r29
            kawa.lang.Translator.setLine(r0, r1)
            if (r25 != 0) goto L_0x041b
            r27 = r26
        L_0x0418:
            r25 = r26
            goto L_0x03ce
        L_0x041b:
            r0 = r26
            r1 = r25
            r1.nextSibling = r0
            goto L_0x0418
        L_0x0422:
            r5 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "invalid field/method definition"
            r0 = r45
            r0.error(r5, r8)
            goto L_0x03ce
        L_0x042c:
            r12 = 0
            int r5 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r5 == 0) goto L_0x0439
            r0 = r44
            gnu.expr.Declaration r5 = r0.nameDecl
            r5.setFlag(r6)
        L_0x0439:
            r5 = 6
            java.lang.Object[] r0 = new java.lang.Object[r5]
            r33 = r0
            r5 = 0
            r33[r5] = r44
            r5 = 1
            r33[r5] = r18
            r5 = 2
            r33[r5] = r21
            r5 = 3
            r33[r5] = r27
            r5 = 4
            r33[r5] = r39
            r5 = 5
            r33[r5] = r17
            goto L_0x0042
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.object.scanClassDef(gnu.lists.Pair, gnu.expr.ClassExp, kawa.lang.Translator):java.lang.Object[]");
    }

    /* JADX WARNING: type inference failed for: r0v23, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r39v2, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r12v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r12v1 */
    /* JADX WARNING: type inference failed for: r12v2 */
    /* JADX WARNING: type inference failed for: r12v3 */
    /* JADX WARNING: type inference failed for: r0v35 */
    /* JADX WARNING: type inference failed for: r12v4, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v40 */
    /* JADX WARNING: type inference failed for: r12v6, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r12v7 */
    /* JADX WARNING: type inference failed for: r0v43 */
    /* JADX WARNING: type inference failed for: r12v8, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v52 */
    /* JADX WARNING: type inference failed for: r12v9, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r12v10 */
    /* JADX WARNING: type inference failed for: r0v58, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r12v11 */
    /* JADX WARNING: type inference failed for: r12v12 */
    /* JADX WARNING: type inference failed for: r12v13 */
    /* JADX WARNING: type inference failed for: r12v14 */
    /* JADX WARNING: type inference failed for: r12v15 */
    /* JADX WARNING: type inference failed for: r12v16 */
    /* JADX WARNING: type inference failed for: r12v17 */
    /* JADX WARNING: type inference failed for: r12v18 */
    /* JADX WARNING: type inference failed for: r12v19 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r12v1
      assigns: []
      uses: []
      mth insns count: 374
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
    /* JADX WARNING: Unknown variable types count: 15 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rewriteClassDef(java.lang.Object[] r53, kawa.lang.Translator r54) {
        /*
            r52 = this;
            r6 = 0
            r38 = r53[r6]
            gnu.expr.ClassExp r38 = (gnu.expr.ClassExp) r38
            r6 = 1
            r17 = r53[r6]
            r6 = 2
            r27 = r53[r6]
            java.util.Vector r27 = (java.util.Vector) r27
            r6 = 3
            r34 = r53[r6]
            gnu.expr.LambdaExp r34 = (gnu.expr.LambdaExp) r34
            r6 = 4
            r46 = r53[r6]
            r6 = 5
            r14 = r53[r6]
            r0 = r34
            r1 = r38
            r1.firstChild = r0
            int r36 = kawa.lang.Translator.listLength(r46)
            if (r36 >= 0) goto L_0x002f
            r6 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "object superclass specification not a list"
            r0 = r54
            r0.error(r6, r8)
            r36 = 0
        L_0x002f:
            r0 = r36
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r48 = r0
            r21 = 0
        L_0x0037:
            r0 = r21
            r1 = r36
            if (r0 >= r1) goto L_0x0087
        L_0x003d:
            r0 = r46
            boolean r6 = r0 instanceof kawa.lang.SyntaxForm
            if (r6 == 0) goto L_0x004a
            kawa.lang.SyntaxForm r46 = (kawa.lang.SyntaxForm) r46
            java.lang.Object r46 = r46.getDatum()
            goto L_0x003d
        L_0x004a:
            r47 = r46
            gnu.lists.Pair r47 = (gnu.lists.Pair) r47
            r6 = 0
            r0 = r54
            r1 = r47
            gnu.expr.Expression r6 = r0.rewrite_car(r1, r6)
            r48[r21] = r6
            r6 = r48[r21]
            boolean r6 = r6 instanceof gnu.expr.ReferenceExp
            if (r6 == 0) goto L_0x0080
            r6 = r48[r21]
            gnu.expr.ReferenceExp r6 = (gnu.expr.ReferenceExp) r6
            gnu.expr.Declaration r6 = r6.getBinding()
            gnu.expr.Declaration r20 = gnu.expr.Declaration.followAliases(r6)
            if (r20 == 0) goto L_0x0080
            gnu.expr.Expression r49 = r20.getValue()
            r0 = r49
            boolean r6 = r0 instanceof gnu.expr.ClassExp
            if (r6 == 0) goto L_0x0080
            gnu.expr.ClassExp r49 = (gnu.expr.ClassExp) r49
            r6 = 131072(0x20000, float:1.83671E-40)
            r0 = r49
            r0.setFlag(r6)
        L_0x0080:
            java.lang.Object r46 = r47.getCdr()
            int r21 = r21 + 1
            goto L_0x0037
        L_0x0087:
            if (r14 == 0) goto L_0x00ad
            r6 = r14
            gnu.lists.Pair r6 = (gnu.lists.Pair) r6
            r8 = 0
            r0 = r54
            gnu.expr.Expression r13 = r0.rewrite_car(r6, r8)
            java.lang.Object r16 = r13.valueIfConstant()
            r0 = r16
            boolean r0 = r0 instanceof java.lang.CharSequence
            r29 = r0
            if (r29 == 0) goto L_0x00e7
            java.lang.String r15 = r16.toString()
            int r6 = r15.length()
            if (r6 <= 0) goto L_0x00e7
            r0 = r38
            r0.classNameSpecifier = r15
        L_0x00ad:
            r0 = r48
            r1 = r38
            r1.supers = r0
            r0 = r38
            r1 = r54
            r0.setTypes(r1)
            int r31 = r27.size()
            r21 = 0
        L_0x00c0:
            r0 = r21
            r1 = r31
            if (r0 >= r1) goto L_0x00fe
            int r6 = r21 + 1
            r0 = r27
            java.lang.Object r22 = r0.elementAt(r6)
            if (r22 == 0) goto L_0x00e4
            r0 = r27
            r1 = r21
            java.lang.Object r6 = r0.elementAt(r1)
            gnu.lists.Pair r22 = (gnu.lists.Pair) r22
            r8 = 0
            r0 = r38
            r1 = r22
            r2 = r54
            rewriteInit(r6, r0, r1, r2, r8)
        L_0x00e4:
            int r21 = r21 + 2
            goto L_0x00c0
        L_0x00e7:
            r0 = r54
            java.lang.Object r43 = r0.pushPositionOf(r14)
            r6 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "class-name specifier must be a non-empty string literal"
            r0 = r54
            r0.error(r6, r8)
            r0 = r54
            r1 = r43
            r0.popPositionOf(r1)
            goto L_0x00ad
        L_0x00fe:
            r0 = r54
            r1 = r38
            r0.push(r1)
            r7 = r34
            r25 = 0
            r18 = 0
            r37 = r17
        L_0x010d:
            gnu.lists.LList r6 = gnu.lists.LList.Empty
            r0 = r37
            if (r0 == r6) goto L_0x032b
        L_0x0113:
            r0 = r37
            boolean r6 = r0 instanceof kawa.lang.SyntaxForm
            if (r6 == 0) goto L_0x0122
            r18 = r37
            kawa.lang.SyntaxForm r18 = (kawa.lang.SyntaxForm) r18
            java.lang.Object r37 = r18.getDatum()
            goto L_0x0113
        L_0x0122:
            r39 = r37
            gnu.lists.Pair r39 = (gnu.lists.Pair) r39
            r0 = r54
            r1 = r39
            java.lang.Object r44 = r0.pushPositionOf(r1)
            java.lang.Object r40 = r39.getCar()
            r33 = r18
        L_0x0134:
            r0 = r40
            boolean r6 = r0 instanceof kawa.lang.SyntaxForm
            if (r6 == 0) goto L_0x0143
            r33 = r40
            kawa.lang.SyntaxForm r33 = (kawa.lang.SyntaxForm) r33
            java.lang.Object r40 = r33.getDatum()
            goto L_0x0134
        L_0x0143:
            java.lang.Object r37 = r39.getCdr()     // Catch:{ all -> 0x0211 }
            r0 = r40
            boolean r6 = r0 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x0164
            r0 = r37
            boolean r6 = r0 instanceof gnu.lists.Pair     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x0164
            r0 = r37
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0     // Catch:{ all -> 0x0211 }
            r6 = r0
            java.lang.Object r37 = r6.getCdr()     // Catch:{ all -> 0x0211 }
            r0 = r54
            r1 = r44
            r0.popPositionOf(r1)
            goto L_0x010d
        L_0x0164:
            r0 = r40
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0     // Catch:{ all -> 0x0211 }
            r39 = r0
            java.lang.Object r40 = r39.getCar()     // Catch:{ all -> 0x0211 }
            r32 = r33
        L_0x0170:
            r0 = r40
            boolean r6 = r0 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x0181
            r0 = r40
            kawa.lang.SyntaxForm r0 = (kawa.lang.SyntaxForm) r0     // Catch:{ all -> 0x0211 }
            r32 = r0
            java.lang.Object r40 = r32.getDatum()     // Catch:{ all -> 0x0211 }
            goto L_0x0170
        L_0x0181:
            r0 = r40
            boolean r6 = r0 instanceof java.lang.String     // Catch:{ all -> 0x0211 }
            if (r6 != 0) goto L_0x0193
            r0 = r40
            boolean r6 = r0 instanceof gnu.mapping.Symbol     // Catch:{ all -> 0x0211 }
            if (r6 != 0) goto L_0x0193
            r0 = r40
            boolean r6 = r0 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x02b0
        L_0x0193:
            r50 = 0
            r35 = 0
            r0 = r40
            boolean r6 = r0 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x01b5
            r12 = r39
        L_0x019f:
            r23 = 0
            r24 = 0
        L_0x01a3:
            gnu.lists.LList r6 = gnu.lists.LList.Empty     // Catch:{ all -> 0x0211 }
            if (r12 == r6) goto L_0x0265
        L_0x01a7:
            boolean r6 = r12 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x01ba
            r0 = r12
            kawa.lang.SyntaxForm r0 = (kawa.lang.SyntaxForm) r0     // Catch:{ all -> 0x0211 }
            r33 = r0
            java.lang.Object r12 = r33.getDatum()     // Catch:{ all -> 0x0211 }
            goto L_0x01a7
        L_0x01b5:
            java.lang.Object r12 = r39.getCdr()     // Catch:{ all -> 0x0211 }
            goto L_0x019f
        L_0x01ba:
            r0 = r12
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0     // Catch:{ all -> 0x0211 }
            r39 = r0
            java.lang.Object r30 = r39.getCar()     // Catch:{ all -> 0x0211 }
        L_0x01c3:
            r0 = r30
            boolean r6 = r0 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x01d0
            kawa.lang.SyntaxForm r30 = (kawa.lang.SyntaxForm) r30     // Catch:{ all -> 0x0211 }
            java.lang.Object r30 = r30.getDatum()     // Catch:{ all -> 0x0211 }
            goto L_0x01c3
        L_0x01d0:
            r0 = r54
            r1 = r39
            java.lang.Object r45 = r0.pushPositionOf(r1)     // Catch:{ all -> 0x0211 }
            java.lang.Object r12 = r39.getCdr()     // Catch:{ all -> 0x0211 }
            gnu.mapping.Symbol r6 = coloncolon     // Catch:{ all -> 0x0211 }
            r0 = r30
            if (r0 == r6) goto L_0x01e8
            r0 = r30
            boolean r6 = r0 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x0237
        L_0x01e8:
            boolean r6 = r12 instanceof gnu.lists.Pair     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x0237
            int r35 = r35 + 1
            r0 = r12
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0     // Catch:{ all -> 0x0211 }
            r39 = r0
            java.lang.Object r51 = r39.getCar()     // Catch:{ all -> 0x0211 }
            java.lang.Object r12 = r39.getCdr()     // Catch:{ all -> 0x0211 }
            gnu.mapping.Symbol r6 = coloncolon     // Catch:{ all -> 0x0211 }
            r0 = r30
            if (r0 == r6) goto L_0x0207
            gnu.expr.Keyword r6 = typeKeyword     // Catch:{ all -> 0x0211 }
            r0 = r30
            if (r0 != r6) goto L_0x021a
        L_0x0207:
            r50 = r51
        L_0x0209:
            r0 = r54
            r1 = r45
            r0.popPositionOf(r1)     // Catch:{ all -> 0x0211 }
            goto L_0x01a3
        L_0x0211:
            r6 = move-exception
        L_0x0212:
            r0 = r54
            r1 = r44
            r0.popPositionOf(r1)
            throw r6
        L_0x021a:
            gnu.expr.Keyword r6 = initKeyword     // Catch:{ all -> 0x0211 }
            r0 = r30
            if (r0 == r6) goto L_0x0232
            gnu.expr.Keyword r6 = initformKeyword     // Catch:{ all -> 0x0211 }
            r0 = r30
            if (r0 == r6) goto L_0x0232
            gnu.expr.Keyword r6 = init_formKeyword     // Catch:{ all -> 0x0211 }
            r0 = r30
            if (r0 == r6) goto L_0x0232
            gnu.expr.Keyword r6 = init_valueKeyword     // Catch:{ all -> 0x0211 }
            r0 = r30
            if (r0 != r6) goto L_0x0209
        L_0x0232:
            r23 = r39
            r24 = r33
            goto L_0x0209
        L_0x0237:
            gnu.lists.LList r6 = gnu.lists.LList.Empty     // Catch:{ all -> 0x0211 }
            if (r12 != r6) goto L_0x0242
            if (r23 != 0) goto L_0x0242
            r23 = r39
            r24 = r33
            goto L_0x0209
        L_0x0242:
            boolean r6 = r12 instanceof gnu.lists.Pair     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x0264
            if (r35 != 0) goto L_0x0264
            if (r23 != 0) goto L_0x0264
            if (r50 != 0) goto L_0x0264
            r0 = r12
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0     // Catch:{ all -> 0x0211 }
            r39 = r0
            java.lang.Object r6 = r39.getCdr()     // Catch:{ all -> 0x0211 }
            gnu.lists.LList r8 = gnu.lists.LList.Empty     // Catch:{ all -> 0x0211 }
            if (r6 != r8) goto L_0x0264
            r50 = r30
            r23 = r39
            r24 = r33
            java.lang.Object r12 = r39.getCdr()     // Catch:{ all -> 0x0211 }
            goto L_0x0209
        L_0x0264:
            r12 = 0
        L_0x0265:
            if (r23 == 0) goto L_0x029b
            int r26 = r25 + 1
            r0 = r27
            r1 = r25
            java.lang.Object r19 = r0.elementAt(r1)     // Catch:{ all -> 0x0356 }
            r0 = r19
            boolean r6 = r0 instanceof gnu.expr.Declaration     // Catch:{ all -> 0x0356 }
            if (r6 == 0) goto L_0x02a4
            r0 = r19
            gnu.expr.Declaration r0 = (gnu.expr.Declaration) r0     // Catch:{ all -> 0x0356 }
            r6 = r0
            r8 = 2048(0x800, double:1.0118E-320)
            boolean r28 = r6.getFlag(r8)     // Catch:{ all -> 0x0356 }
        L_0x0282:
            int r25 = r26 + 1
            r0 = r27
            r1 = r26
            java.lang.Object r6 = r0.elementAt(r1)     // Catch:{ all -> 0x0211 }
            if (r6 != 0) goto L_0x029b
            r0 = r19
            r1 = r38
            r2 = r23
            r3 = r54
            r4 = r24
            rewriteInit(r0, r1, r2, r3, r4)     // Catch:{ all -> 0x0211 }
        L_0x029b:
            r0 = r54
            r1 = r44
            r0.popPositionOf(r1)
            goto L_0x010d
        L_0x02a4:
            java.lang.Boolean r6 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x0356 }
            r0 = r19
            if (r0 != r6) goto L_0x02ad
            r28 = 1
            goto L_0x0282
        L_0x02ad:
            r28 = 0
            goto L_0x0282
        L_0x02b0:
            r0 = r40
            boolean r6 = r0 instanceof gnu.lists.Pair     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x0322
            gnu.expr.ScopeExp r42 = r54.currentScope()     // Catch:{ all -> 0x0211 }
            if (r33 == 0) goto L_0x02c5
            kawa.lang.TemplateScope r6 = r33.getScope()     // Catch:{ all -> 0x0211 }
            r0 = r54
            r0.setCurrentScope(r6)     // Catch:{ all -> 0x0211 }
        L_0x02c5:
            java.lang.String r6 = "*init*"
            java.lang.String r8 = r7.getName()     // Catch:{ all -> 0x0211 }
            boolean r6 = r6.equals(r8)     // Catch:{ all -> 0x0211 }
            if (r6 == 0) goto L_0x02d6
            gnu.bytecode.PrimType r6 = gnu.bytecode.Type.voidType     // Catch:{ all -> 0x0211 }
            r7.setReturnType(r6)     // Catch:{ all -> 0x0211 }
        L_0x02d6:
            r0 = r39
            kawa.lang.Translator.setLine(r7, r0)     // Catch:{ all -> 0x0211 }
            r0 = r54
            gnu.expr.LambdaExp r0 = r0.curMethodLambda     // Catch:{ all -> 0x0211 }
            r41 = r0
            r0 = r54
            r0.curMethodLambda = r7     // Catch:{ all -> 0x0211 }
            r0 = r52
            kawa.lang.Lambda r6 = r0.lambda     // Catch:{ all -> 0x0211 }
            r0 = r40
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0     // Catch:{ all -> 0x0211 }
            r8 = r0
            java.lang.Object r8 = r8.getCdr()     // Catch:{ all -> 0x0211 }
            java.lang.Object r9 = r39.getCdr()     // Catch:{ all -> 0x0211 }
            if (r32 == 0) goto L_0x0320
            if (r33 == 0) goto L_0x0304
            kawa.lang.TemplateScope r10 = r32.getScope()     // Catch:{ all -> 0x0211 }
            kawa.lang.TemplateScope r11 = r33.getScope()     // Catch:{ all -> 0x0211 }
            if (r10 == r11) goto L_0x0320
        L_0x0304:
            kawa.lang.TemplateScope r11 = r32.getScope()     // Catch:{ all -> 0x0211 }
        L_0x0308:
            r10 = r54
            r6.rewrite(r7, r8, r9, r10, r11)     // Catch:{ all -> 0x0211 }
            r0 = r41
            r1 = r54
            r1.curMethodLambda = r0     // Catch:{ all -> 0x0211 }
            if (r33 == 0) goto L_0x031c
            r0 = r54
            r1 = r42
            r0.setCurrentScope(r1)     // Catch:{ all -> 0x0211 }
        L_0x031c:
            gnu.expr.LambdaExp r7 = r7.nextSibling     // Catch:{ all -> 0x0211 }
            goto L_0x029b
        L_0x0320:
            r11 = 0
            goto L_0x0308
        L_0x0322:
            java.lang.String r6 = "invalid field/method definition"
            r0 = r54
            r0.syntaxError(r6)     // Catch:{ all -> 0x0211 }
            goto L_0x029b
        L_0x032b:
            r0 = r38
            gnu.expr.LambdaExp r6 = r0.initMethod
            if (r6 == 0) goto L_0x0339
            r0 = r38
            gnu.expr.LambdaExp r6 = r0.initMethod
            r0 = r38
            r6.outer = r0
        L_0x0339:
            r0 = r38
            gnu.expr.LambdaExp r6 = r0.clinitMethod
            if (r6 == 0) goto L_0x0347
            r0 = r38
            gnu.expr.LambdaExp r6 = r0.clinitMethod
            r0 = r38
            r6.outer = r0
        L_0x0347:
            r0 = r54
            r1 = r38
            r0.pop(r1)
            r0 = r38
            r1 = r54
            r0.declareParts(r1)
            return
        L_0x0356:
            r6 = move-exception
            r25 = r26
            goto L_0x0212
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.object.rewriteClassDef(java.lang.Object[], kawa.lang.Translator):void");
    }

    private static void rewriteInit(Object d, ClassExp oexp, Pair initPair, Translator tr, SyntaxForm initSyntax) {
        Expression initValue;
        boolean isStatic = d instanceof Declaration ? ((Declaration) d).getFlag(2048) : d == Boolean.TRUE;
        LambdaExp initMethod = isStatic ? oexp.clinitMethod : oexp.initMethod;
        if (initMethod == null) {
            initMethod = new LambdaExp((Expression) new BeginExp());
            initMethod.setClassMethod(true);
            initMethod.setReturnType(Type.voidType);
            if (isStatic) {
                initMethod.setName("$clinit$");
                oexp.clinitMethod = initMethod;
            } else {
                initMethod.setName("$finit$");
                oexp.initMethod = initMethod;
                initMethod.add(null, new Declaration((Object) ThisExp.THIS_NAME));
            }
            initMethod.nextSibling = oexp.firstChild;
            oexp.firstChild = initMethod;
        }
        tr.push((ScopeExp) initMethod);
        LambdaExp saveLambda = tr.curMethodLambda;
        tr.curMethodLambda = initMethod;
        Expression initValue2 = tr.rewrite_car(initPair, initSyntax);
        if (d instanceof Declaration) {
            Declaration decl = (Declaration) d;
            SetExp sexp = new SetExp(decl, initValue2);
            sexp.setLocation(decl);
            decl.noteValue(null);
            initValue = sexp;
        } else {
            initValue = Compilation.makeCoercion(initValue2, (Expression) new QuoteExp(Type.voidType));
        }
        ((BeginExp) initMethod.body).add(initValue);
        tr.curMethodLambda = saveLambda;
        tr.pop(initMethod);
    }

    static boolean matches(Object exp, String tag, Translator tr) {
        String value;
        if (exp instanceof Keyword) {
            value = ((Keyword) exp).getName();
        } else if (exp instanceof FString) {
            value = ((FString) exp).toString();
        } else if (!(exp instanceof Pair)) {
            return false;
        } else {
            Object qvalue = tr.matchQuoted((Pair) exp);
            if (!(qvalue instanceof SimpleSymbol)) {
                return false;
            }
            value = qvalue.toString();
        }
        if (tag == null || tag.equals(value)) {
            return true;
        }
        return false;
    }

    static long addAccessFlags(Object value, long previous, long allowed, String kind, Translator tr) {
        long flags = matchAccess(value, tr);
        if (flags == 0) {
            tr.error('e', "unknown access specifier " + value);
        } else if (((-1 ^ allowed) & flags) != 0) {
            tr.error('e', "invalid " + kind + " access specifier " + value);
        } else if ((previous & flags) != 0) {
            tr.error('w', "duplicate " + kind + " access specifiers " + value);
        }
        return previous | flags;
    }

    static long matchAccess(Object value, Translator tr) {
        while (value instanceof SyntaxForm) {
            value = ((SyntaxForm) value).getDatum();
        }
        if (value instanceof Pair) {
            Pair pair = (Pair) value;
            value = tr.matchQuoted((Pair) value);
            if (value instanceof Pair) {
                return matchAccess2((Pair) value, tr);
            }
        }
        return matchAccess1(value, tr);
    }

    private static long matchAccess2(Pair pair, Translator tr) {
        long icar = matchAccess1(pair.getCar(), tr);
        Object cdr = pair.getCdr();
        if (cdr == LList.Empty || icar == 0) {
            return icar;
        }
        if (cdr instanceof Pair) {
            long icdr = matchAccess2((Pair) cdr, tr);
            if (icdr != 0) {
                return icar | icdr;
            }
        }
        return 0;
    }

    private static long matchAccess1(Object value, Translator tr) {
        if (value instanceof Keyword) {
            value = ((Keyword) value).getName();
        } else if (value instanceof FString) {
            value = ((FString) value).toString();
        } else if (value instanceof SimpleSymbol) {
            value = value.toString();
        }
        if ("private".equals(value)) {
            return 16777216;
        }
        if ("protected".equals(value)) {
            return 33554432;
        }
        if ("public".equals(value)) {
            return 67108864;
        }
        if ("package".equals(value)) {
            return 134217728;
        }
        if ("volatile".equals(value)) {
            return Declaration.VOLATILE_ACCESS;
        }
        if ("transient".equals(value)) {
            return Declaration.TRANSIENT_ACCESS;
        }
        if ("enum".equals(value)) {
            return Declaration.ENUM_ACCESS;
        }
        if ("final".equals(value)) {
            return Declaration.FINAL_ACCESS;
        }
        return 0;
    }
}
