package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class define extends Syntax {
    public static final define defineRaw = new define(SchemeCompilation.lambda);
    Lambda lambda;

    /* access modifiers changed from: 0000 */
    public String getName(int options) {
        if ((options & 4) != 0) {
            return "define-private";
        }
        if ((options & 8) != 0) {
            return "define-constant";
        }
        return "define";
    }

    public define(Lambda lambda2) {
        this.lambda = lambda2;
    }

    public void scanForm(Pair st, ScopeExp defs, Translator tr) {
        Pair p1 = (Pair) st.getCdr();
        Pair p2 = (Pair) p1.getCdr();
        Pair p3 = (Pair) p2.getCdr();
        Pair p4 = (Pair) p3.getCdr();
        SyntaxForm nameSyntax = null;
        Object name = p1.getCar();
        while (name instanceof SyntaxForm) {
            nameSyntax = (SyntaxForm) name;
            name = nameSyntax.getDatum();
        }
        int options = ((Number) Translator.stripSyntax(p2.getCar())).intValue();
        boolean makePrivate = (options & 4) != 0;
        boolean makeConstant = (options & 8) != 0;
        ScopeExp currentScope = tr.currentScope();
        Object name2 = tr.namespaceResolve(name);
        if (!(name2 instanceof Symbol)) {
            tr.error('e', "'" + name2 + "' is not a valid identifier");
            name2 = null;
        }
        Object savePos = tr.pushPositionOf(p1);
        Declaration decl = tr.define(name2, nameSyntax, defs);
        tr.popPositionOf(savePos);
        Object name3 = decl.getSymbol();
        if (makePrivate) {
            decl.setFlag(16777216);
            decl.setPrivate(true);
        }
        if (makeConstant) {
            decl.setFlag(16384);
        }
        decl.setFlag(262144);
        if ((options & 2) != 0) {
            LambdaExp lexp = new LambdaExp();
            lexp.setSymbol(name3);
            if (Compilation.inlineOk) {
                decl.setProcedureDecl(true);
                decl.setType(Compilation.typeProcedure);
                lexp.nameDecl = decl;
            }
            Object formals = p4.getCar();
            Object body = p4.getCdr();
            Translator.setLine((Expression) lexp, (Object) p1);
            this.lambda.rewriteFormals(lexp, formals, tr, null);
            Object realBody = this.lambda.rewriteAttrs(lexp, body, tr);
            if (realBody != body) {
                Object car = p2.getCar();
                Object car2 = p3.getCar();
                Pair pair = new Pair(formals, realBody);
                p2 = new Pair(car, new Pair(car2, pair));
            }
            decl.noteValue(lexp);
        }
        if ((defs instanceof ModuleExp) && !makePrivate && (!Compilation.inlineOk || tr.sharedModuleDefs())) {
            decl.setCanWrite(true);
        }
        if ((options & 1) != 0) {
            LangExp langExp = new LangExp(p3);
            decl.setTypeExp(langExp);
            decl.setFlag(8192);
        }
        Pair st2 = Translator.makePair(st, this, Translator.makePair(p1, decl, p2));
        Translator.setLine(decl, (Object) p1);
        tr.formStack.addElement(st2);
    }

    /* JADX WARNING: type inference failed for: r17v0, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r18v12 */
    /* JADX WARNING: type inference failed for: r18v13 */
    /* JADX WARNING: type inference failed for: r0v6, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r18v15 */
    /* JADX WARNING: type inference failed for: r17v1 */
    /* JADX WARNING: type inference failed for: r0v7, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r17v2 */
    /* JADX WARNING: type inference failed for: r17v3 */
    /* JADX WARNING: type inference failed for: r18v29 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewriteForm(gnu.lists.Pair r22, kawa.lang.Translator r23) {
        /*
            r21 = this;
            java.lang.Object r10 = r22.getCdr()
            gnu.lists.Pair r10 = (gnu.lists.Pair) r10
            java.lang.Object r11 = r10.getCdr()
            gnu.lists.Pair r11 = (gnu.lists.Pair) r11
            java.lang.Object r12 = r11.getCdr()
            gnu.lists.Pair r12 = (gnu.lists.Pair) r12
            java.lang.Object r13 = r12.getCdr()
            gnu.lists.Pair r13 = (gnu.lists.Pair) r13
            java.lang.Object r18 = r10.getCar()
            java.lang.Object r8 = kawa.lang.Translator.stripSyntax(r18)
            java.lang.Object r18 = r11.getCar()
            java.lang.Object r18 = kawa.lang.Translator.stripSyntax(r18)
            java.lang.Number r18 = (java.lang.Number) r18
            int r9 = r18.intValue()
            r18 = r9 & 4
            if (r18 == 0) goto L_0x005b
            r7 = 1
        L_0x0033:
            boolean r0 = r8 instanceof gnu.expr.Declaration
            r18 = r0
            if (r18 != 0) goto L_0x005d
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            r0 = r21
            java.lang.String r19 = r0.getName(r9)
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r19 = " is only allowed in a <body>"
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            r0 = r23
            r1 = r18
            gnu.expr.Expression r14 = r0.syntaxError(r1)
        L_0x005a:
            return r14
        L_0x005b:
            r7 = 0
            goto L_0x0033
        L_0x005d:
            r5 = r8
            gnu.expr.Declaration r5 = (gnu.expr.Declaration) r5
            r18 = 8192(0x2000, double:4.0474E-320)
            r0 = r18
            boolean r18 = r5.getFlag(r0)
            if (r18 == 0) goto L_0x0089
            gnu.expr.Expression r15 = r5.getTypeExp()
            boolean r0 = r15 instanceof gnu.expr.LangExp
            r18 = r0
            if (r18 == 0) goto L_0x0089
            gnu.expr.LangExp r15 = (gnu.expr.LangExp) r15
            java.lang.Object r16 = r15.getLangValue()
            gnu.lists.Pair r16 = (gnu.lists.Pair) r16
            r0 = r23
            r1 = r16
            gnu.bytecode.Type r18 = r0.exp2Type(r1)
            r0 = r18
            r5.setType(r0)
        L_0x0089:
            r18 = r9 & 2
            if (r18 == 0) goto L_0x00f1
            gnu.expr.Expression r6 = r5.getValue()
            gnu.expr.LambdaExp r6 = (gnu.expr.LambdaExp) r6
            java.lang.Object r4 = r13.getCdr()
            r0 = r21
            kawa.lang.Lambda r0 = r0.lambda
            r18 = r0
            r0 = r18
            r1 = r23
            r0.rewriteBody(r6, r4, r1)
            r17 = r6
            boolean r18 = gnu.expr.Compilation.inlineOk
            if (r18 != 0) goto L_0x00b1
            r18 = 0
            r0 = r18
            r5.noteValue(r0)
        L_0x00b1:
            gnu.expr.SetExp r14 = new gnu.expr.SetExp
            r0 = r17
            r14.<init>(r5, r0)
            r18 = 1
            r0 = r18
            r14.setDefining(r0)
            if (r7 == 0) goto L_0x005a
            gnu.expr.ScopeExp r18 = r23.currentScope()
            r0 = r18
            boolean r0 = r0 instanceof gnu.expr.ModuleExp
            r18 = r0
            if (r18 != 0) goto L_0x005a
            r18 = 119(0x77, float:1.67E-43)
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            r19.<init>()
            java.lang.String r20 = "define-private not at top level "
            java.lang.StringBuilder r19 = r19.append(r20)
            gnu.expr.ScopeExp r20 = r23.currentScope()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r0 = r23
            r1 = r18
            r2 = r19
            r0.error(r1, r2)
            goto L_0x005a
        L_0x00f1:
            java.lang.Object r18 = r13.getCar()
            r0 = r23
            r1 = r18
            gnu.expr.Expression r17 = r0.rewrite(r1)
            gnu.expr.ScopeExp r0 = r5.context
            r18 = r0
            r0 = r18
            boolean r0 = r0 instanceof gnu.expr.ModuleExp
            r18 = r0
            if (r18 == 0) goto L_0x0119
            if (r7 != 0) goto L_0x0119
            boolean r18 = r5.getCanWrite()
            if (r18 == 0) goto L_0x0119
            r18 = 0
        L_0x0113:
            r0 = r18
            r5.noteValue(r0)
            goto L_0x00b1
        L_0x0119:
            r18 = r17
            goto L_0x0113
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.define.rewriteForm(gnu.lists.Pair, kawa.lang.Translator):gnu.expr.Expression");
    }
}
