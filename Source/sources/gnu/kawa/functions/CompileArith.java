package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.mapping.Procedure;
import gnu.math.IntNum;

public class CompileArith implements Inlineable {
    public static CompileArith $Mn = new CompileArith(AddOp.$Mn, 2);
    public static CompileArith $Pl = new CompileArith(AddOp.$Pl, 1);

    /* renamed from: op */
    int f55op;
    Procedure proc;

    CompileArith(Object proc2, int op) {
        this.proc = (Procedure) proc2;
        this.f55op = op;
    }

    public static CompileArith forMul(Object proc2) {
        return new CompileArith(proc2, 3);
    }

    public static CompileArith forDiv(Object proc2) {
        return new CompileArith(proc2, ((DivideOp) proc2).f54op);
    }

    public static CompileArith forBitwise(Object proc2) {
        return new CompileArith(proc2, ((BitwiseOp) proc2).f54op);
    }

    public static boolean appropriateIntConstant(Expression[] args, int iarg, InlineCalls visitor) {
        Expression exp = visitor.fixIntValue(args[iarg]);
        if (exp == null) {
            return false;
        }
        args[iarg] = exp;
        return true;
    }

    public static boolean appropriateLongConstant(Expression[] args, int iarg, InlineCalls visitor) {
        Expression exp = visitor.fixLongValue(args[iarg]);
        if (exp == null) {
            return false;
        }
        args[iarg] = exp;
        return true;
    }

    public static Expression validateApplyArithOp(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        int rkind;
        int op = ((ArithOp) proc2).f54op;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length > 2) {
            return pairwise(proc2, exp.getFunction(), args, visitor);
        }
        Expression folded = exp.inlineIfConstant(proc2, visitor);
        if (folded != exp) {
            return folded;
        }
        int rkind2 = 0;
        if (args.length == 2 || args.length == 1) {
            int kind1 = Arithmetic.classifyType(args[0].getType());
            if (args.length != 2 || (op >= 9 && op <= 12)) {
                rkind = kind1;
            } else {
                int kind2 = Arithmetic.classifyType(args[1].getType());
                rkind = getReturnKind(kind1, kind2, op);
                if (rkind == 4) {
                    if (kind1 == 1 && appropriateIntConstant(args, 1, visitor)) {
                        rkind = 1;
                    } else if (kind2 == 1 && appropriateIntConstant(args, 0, visitor)) {
                        rkind = 1;
                    } else if (kind1 == 2 && appropriateLongConstant(args, 1, visitor)) {
                        rkind = 2;
                    } else if (kind2 == 2 && appropriateLongConstant(args, 0, visitor)) {
                        rkind = 2;
                    }
                }
            }
            rkind2 = adjustReturnKind(rkind, op);
            exp.setType(Arithmetic.kindType(rkind2));
        }
        if (!visitor.getCompilation().mustCompile) {
            return exp;
        }
        switch (op) {
            case 1:
            case 2:
                return validateApplyAdd((AddOp) proc2, exp, visitor);
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return validateApplyDiv((DivideOp) proc2, exp, visitor);
            case 16:
                if (rkind2 > 0) {
                    return validateApplyNot(exp, rkind2, visitor);
                }
                return exp;
            default:
                return exp;
        }
    }

    /* JADX WARNING: type inference failed for: r21v0, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r23v0, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v1 */
    /* JADX WARNING: type inference failed for: r23v2, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v3, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v4, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v5, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r23v6 */
    /* JADX WARNING: type inference failed for: r2v0, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r23v7, types: [gnu.bytecode.Type] */
    /* JADX WARNING: type inference failed for: r23v8 */
    /* JADX WARNING: type inference failed for: r23v9, types: [gnu.kawa.lispexpr.LangObjType] */
    /* JADX WARNING: type inference failed for: r23v10, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v11, types: [gnu.bytecode.PrimType] */
    /* JADX WARNING: type inference failed for: r23v12 */
    /* JADX WARNING: type inference failed for: r23v13 */
    /* JADX WARNING: type inference failed for: r23v14 */
    /* JADX WARNING: type inference failed for: r23v15 */
    /* JADX WARNING: type inference failed for: r23v16 */
    /* JADX WARNING: type inference failed for: r23v17 */
    /* JADX WARNING: type inference failed for: r23v18 */
    /* JADX WARNING: type inference failed for: r23v19 */
    /* JADX WARNING: type inference failed for: r23v20 */
    /* JADX WARNING: type inference failed for: r23v21 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 12 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void compile(gnu.expr.ApplyExp r25, gnu.expr.Compilation r26, gnu.expr.Target r27) {
        /*
            r24 = this;
            gnu.expr.Expression[] r9 = r25.getArgs()
            int r15 = r9.length
            if (r15 != 0) goto L_0x0019
            r0 = r24
            gnu.mapping.Procedure r3 = r0.proc
            gnu.kawa.functions.ArithOp r3 = (gnu.kawa.functions.ArithOp) r3
            java.lang.Object r3 = r3.defaultResult()
            r0 = r26
            r1 = r27
            r0.compileConstant(r3, r1)
        L_0x0018:
            return
        L_0x0019:
            r3 = 1
            if (r15 == r3) goto L_0x0022
            r0 = r27
            boolean r3 = r0 instanceof gnu.expr.IgnoreTarget
            if (r3 == 0) goto L_0x0026
        L_0x0022:
            gnu.expr.ApplyExp.compile(r25, r26, r27)
            goto L_0x0018
        L_0x0026:
            r3 = 0
            r3 = r9[r3]
            gnu.bytecode.Type r3 = r3.getType()
            int r6 = gnu.kawa.functions.Arithmetic.classifyType(r3)
            r3 = 1
            r3 = r9[r3]
            gnu.bytecode.Type r3 = r3.getType()
            int r7 = gnu.kawa.functions.Arithmetic.classifyType(r3)
            r0 = r24
            int r3 = r0.f55op
            int r14 = getReturnKind(r6, r7, r3)
            gnu.bytecode.Type r21 = gnu.kawa.functions.Arithmetic.kindType(r14)
            if (r14 == 0) goto L_0x004d
            r3 = 2
            if (r15 == r3) goto L_0x0051
        L_0x004d:
            gnu.expr.ApplyExp.compile(r25, r26, r27)
            goto L_0x0018
        L_0x0051:
            gnu.bytecode.Type r19 = r27.getType()
            int r20 = gnu.kawa.functions.Arithmetic.classifyType(r19)
            r3 = 1
            r0 = r20
            if (r0 == r3) goto L_0x0063
            r3 = 2
            r0 = r20
            if (r0 != r3) goto L_0x00eb
        L_0x0063:
            r3 = 1
            if (r14 < r3) goto L_0x00eb
            r3 = 4
            if (r14 > r3) goto L_0x00eb
            r14 = r20
            r3 = 1
            r0 = r20
            if (r0 != r3) goto L_0x00e8
            gnu.bytecode.PrimType r23 = gnu.kawa.lispexpr.LangPrimType.intType
        L_0x0072:
            r0 = r24
            int r3 = r0.f55op
            r4 = 4
            if (r3 < r4) goto L_0x0096
            r0 = r24
            int r3 = r0.f55op
            r4 = 8
            if (r3 > r4) goto L_0x0096
            r0 = r24
            gnu.mapping.Procedure r12 = r0.proc
            gnu.kawa.functions.DivideOp r12 = (gnu.kawa.functions.DivideOp) r12
            int r3 = r12.f54op
            r4 = 4
            if (r3 != r4) goto L_0x0124
            r3 = 4
            if (r14 <= r3) goto L_0x0096
            r3 = 6
            if (r14 >= r3) goto L_0x0096
            r3 = 9
            if (r14 > r3) goto L_0x0124
        L_0x0096:
            r0 = r24
            int r3 = r0.f55op
            r4 = 4
            if (r3 != r4) goto L_0x0181
            r3 = 10
            if (r14 > r3) goto L_0x0181
            r3 = 8
            if (r14 == r3) goto L_0x0181
            r3 = 7
            if (r14 == r3) goto L_0x0181
            r3 = 6
            if (r14 == r3) goto L_0x00ae
            r3 = 4
            if (r14 <= r3) goto L_0x0174
        L_0x00ae:
            r3 = 6
            if (r14 != r3) goto L_0x0170
            gnu.kawa.lispexpr.LangObjType r11 = gnu.kawa.functions.Arithmetic.typeRatNum
        L_0x00b3:
            r23 = r11
            java.lang.String r3 = "divide"
            r4 = 2
            gnu.bytecode.Method r17 = r11.getDeclaredMethod(r3, r4)
        L_0x00bc:
            gnu.expr.Target r22 = gnu.expr.StackTarget.getInstance(r23)
            r3 = 0
            r3 = r9[r3]
            r0 = r26
            r1 = r22
            r3.compile(r0, r1)
            r3 = 1
            r3 = r9[r3]
            r0 = r26
            r1 = r22
            r3.compile(r0, r1)
            gnu.bytecode.CodeAttr r3 = r26.getCode()
            r0 = r17
            r3.emitInvokeStatic(r0)
        L_0x00dd:
            r0 = r27
            r1 = r26
            r2 = r23
            r0.compileFromStack(r1, r2)
            goto L_0x0018
        L_0x00e8:
            gnu.bytecode.PrimType r23 = gnu.kawa.lispexpr.LangPrimType.longType
            goto L_0x0072
        L_0x00eb:
            r3 = 8
            r0 = r20
            if (r0 == r3) goto L_0x00f6
            r3 = 7
            r0 = r20
            if (r0 != r3) goto L_0x010b
        L_0x00f6:
            r3 = 2
            if (r14 <= r3) goto L_0x010b
            r3 = 10
            if (r14 > r3) goto L_0x010b
            r14 = r20
            r3 = 7
            r0 = r20
            if (r0 != r3) goto L_0x0108
            gnu.bytecode.PrimType r23 = gnu.kawa.lispexpr.LangPrimType.floatType
        L_0x0106:
            goto L_0x0072
        L_0x0108:
            gnu.bytecode.PrimType r23 = gnu.kawa.lispexpr.LangPrimType.doubleType
            goto L_0x0106
        L_0x010b:
            r3 = 7
            if (r14 != r3) goto L_0x0112
            gnu.bytecode.PrimType r23 = gnu.kawa.lispexpr.LangPrimType.floatType
            goto L_0x0072
        L_0x0112:
            r3 = 8
            if (r14 == r3) goto L_0x011a
            r3 = 9
            if (r14 != r3) goto L_0x0120
        L_0x011a:
            r14 = 8
            gnu.bytecode.PrimType r23 = gnu.kawa.lispexpr.LangPrimType.doubleType
            goto L_0x0072
        L_0x0120:
            r23 = r21
            goto L_0x0072
        L_0x0124:
            int r3 = r12.f54op
            r4 = 5
            if (r3 != r4) goto L_0x0130
            r3 = 10
            if (r14 > r3) goto L_0x0130
            r3 = 7
            if (r14 != r3) goto L_0x0139
        L_0x0130:
            int r3 = r12.f54op
            r4 = 4
            if (r3 != r4) goto L_0x013d
            r3 = 10
            if (r14 != r3) goto L_0x013d
        L_0x0139:
            r14 = 8
            goto L_0x0096
        L_0x013d:
            int r3 = r12.f54op
            r4 = 7
            if (r3 == r4) goto L_0x014a
            int r3 = r12.f54op
            r4 = 6
            if (r3 != r4) goto L_0x015b
            r3 = 4
            if (r14 > r3) goto L_0x015b
        L_0x014a:
            int r3 = r12.getRoundingMode()
            r4 = 3
            if (r3 == r4) goto L_0x0096
            r3 = 4
            if (r14 == r3) goto L_0x0096
            r3 = 7
            if (r14 == r3) goto L_0x0096
            r3 = 8
            if (r14 == r3) goto L_0x0096
        L_0x015b:
            int r3 = r12.f54op
            r4 = 8
            if (r3 != r4) goto L_0x016b
            int r3 = r12.getRoundingMode()
            r4 = 3
            if (r3 == r4) goto L_0x0096
            r3 = 4
            if (r14 == r3) goto L_0x0096
        L_0x016b:
            gnu.expr.ApplyExp.compile(r25, r26, r27)
            goto L_0x0018
        L_0x0170:
            gnu.kawa.lispexpr.LangObjType r11 = gnu.kawa.functions.Arithmetic.typeRealNum
            goto L_0x00b3
        L_0x0174:
            gnu.kawa.lispexpr.LangObjType r23 = gnu.kawa.functions.Arithmetic.typeIntNum
            gnu.kawa.lispexpr.LangObjType r3 = gnu.kawa.functions.Arithmetic.typeRatNum
            java.lang.String r4 = "make"
            r5 = 2
            gnu.bytecode.Method r17 = r3.getDeclaredMethod(r4, r5)
            goto L_0x00bc
        L_0x0181:
            r3 = 4
            if (r14 != r3) goto L_0x01df
            r0 = r24
            int r3 = r0.f55op
            r4 = 1
            if (r3 == r4) goto L_0x01d0
            r0 = r24
            int r3 = r0.f55op
            r4 = 3
            if (r3 == r4) goto L_0x01d0
            r0 = r24
            int r3 = r0.f55op
            r4 = 2
            if (r3 == r4) goto L_0x01d0
            r0 = r24
            int r3 = r0.f55op
            r4 = 13
            if (r3 == r4) goto L_0x01d0
            r0 = r24
            int r3 = r0.f55op
            r4 = 14
            if (r3 == r4) goto L_0x01d0
            r0 = r24
            int r3 = r0.f55op
            r4 = 15
            if (r3 == r4) goto L_0x01d0
            r0 = r24
            int r3 = r0.f55op
            r4 = 7
            if (r3 == r4) goto L_0x01d0
            r0 = r24
            int r3 = r0.f55op
            r4 = 8
            if (r3 == r4) goto L_0x01d0
            r0 = r24
            int r3 = r0.f55op
            r4 = 9
            if (r3 < r4) goto L_0x01df
            r0 = r24
            int r3 = r0.f55op
            r4 = 11
            if (r3 > r4) goto L_0x01df
        L_0x01d0:
            r3 = 0
            r4 = r9[r3]
            r3 = 1
            r5 = r9[r3]
            r3 = r24
            r8 = r26
            r3.compileIntNum(r4, r5, r6, r7, r8)
            goto L_0x00dd
        L_0x01df:
            r3 = 1
            if (r14 == r3) goto L_0x01fc
            r3 = 2
            if (r14 == r3) goto L_0x01fc
            r3 = 7
            if (r14 == r3) goto L_0x01ec
            r3 = 8
            if (r14 != r3) goto L_0x0269
        L_0x01ec:
            r0 = r24
            int r3 = r0.f55op
            r4 = 8
            if (r3 <= r4) goto L_0x01fc
            r0 = r24
            int r3 = r0.f55op
            r4 = 13
            if (r3 < r4) goto L_0x0269
        L_0x01fc:
            gnu.expr.Target r22 = gnu.expr.StackTarget.getInstance(r23)
            gnu.bytecode.CodeAttr r10 = r26.getCode()
            r13 = 0
        L_0x0205:
            if (r13 >= r15) goto L_0x00dd
            r3 = 1
            if (r13 != r3) goto L_0x0220
            r0 = r24
            int r3 = r0.f55op
            r4 = 9
            if (r3 < r4) goto L_0x0220
            r0 = r24
            int r3 = r0.f55op
            r4 = 12
            if (r3 > r4) goto L_0x0220
            gnu.bytecode.PrimType r3 = gnu.bytecode.Type.intType
            gnu.expr.Target r22 = gnu.expr.StackTarget.getInstance(r3)
        L_0x0220:
            r3 = r9[r13]
            r0 = r26
            r1 = r22
            r3.compile(r0, r1)
            if (r13 != 0) goto L_0x022e
        L_0x022b:
            int r13 = r13 + 1
            goto L_0x0205
        L_0x022e:
            switch(r14) {
                case 1: goto L_0x0232;
                case 2: goto L_0x0232;
                case 3: goto L_0x0231;
                case 4: goto L_0x0231;
                case 5: goto L_0x0231;
                case 6: goto L_0x0231;
                case 7: goto L_0x0232;
                case 8: goto L_0x0232;
                default: goto L_0x0231;
            }
        L_0x0231:
            goto L_0x022b
        L_0x0232:
            r0 = r24
            int r3 = r0.f55op
            r4 = 9
            if (r3 != r4) goto L_0x025b
            r3 = 2
            gnu.bytecode.Type[] r0 = new gnu.bytecode.Type[r3]
            r16 = r0
            r3 = 0
            r16[r3] = r23
            r3 = 1
            gnu.bytecode.PrimType r4 = gnu.bytecode.Type.intType
            r16[r3] = r4
            java.lang.String r3 = "gnu.math.IntNum"
            gnu.bytecode.ClassType r3 = gnu.bytecode.ClassType.make(r3)
            java.lang.String r4 = "shift"
            r0 = r16
            gnu.bytecode.Method r18 = r3.getDeclaredMethod(r4, r0)
            r0 = r18
            r10.emitInvokeStatic(r0)
            goto L_0x022b
        L_0x025b:
            int r4 = r24.primitiveOpcode()
            gnu.bytecode.Type r3 = r23.getImplementationType()
            gnu.bytecode.PrimType r3 = (gnu.bytecode.PrimType) r3
            r10.emitBinop(r4, r3)
            goto L_0x022b
        L_0x0269:
            gnu.expr.ApplyExp.compile(r25, r26, r27)
            goto L_0x0018
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.CompileArith.compile(gnu.expr.ApplyExp, gnu.expr.Compilation, gnu.expr.Target):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:65:0x011f, code lost:
        if (r11 != null) goto L_0x012a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0121, code lost:
        r11 = new gnu.bytecode.Type[]{r22, r23};
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x012a, code lost:
        r12.emitInvokeStatic(r15.getMethod(r19, r11));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0140, code lost:
        if (r19 != null) goto L_0x0144;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0142, code lost:
        r19 = "ior";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0144, code lost:
        if (r19 != null) goto L_0x0148;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0146, code lost:
        r19 = "xor";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0148, code lost:
        r15 = gnu.bytecode.ClassType.make("gnu.math.BitOps");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean compileIntNum(gnu.expr.Expression r26, gnu.expr.Expression r27, int r28, int r29, gnu.expr.Compilation r30) {
        /*
            r25 = this;
            r0 = r25
            int r4 = r0.f55op
            r5 = 2
            if (r4 != r5) goto L_0x0069
            r0 = r27
            boolean r4 = r0 instanceof gnu.expr.QuoteExp
            if (r4 == 0) goto L_0x0069
            java.lang.Object r24 = r27.valueIfConstant()
            r4 = 2
            r0 = r29
            if (r0 > r4) goto L_0x004b
            java.lang.Number r24 = (java.lang.Number) r24
            long r16 = r24.longValue()
            r4 = -2147483648(0xffffffff80000000, double:NaN)
            int r4 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x0048
            r4 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r4 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1))
            if (r4 > 0) goto L_0x0048
            r20 = 1
        L_0x002c:
            if (r20 == 0) goto L_0x0069
            gnu.kawa.functions.CompileArith r4 = $Pl
            r0 = r16
            long r6 = -r0
            int r5 = (int) r6
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            gnu.expr.QuoteExp r6 = gnu.expr.QuoteExp.getInstance(r5)
            r8 = 1
            r5 = r26
            r7 = r28
            r9 = r30
            boolean r4 = r4.compileIntNum(r5, r6, r7, r8, r9)
        L_0x0047:
            return r4
        L_0x0048:
            r20 = 0
            goto L_0x002c
        L_0x004b:
            r0 = r24
            boolean r4 = r0 instanceof gnu.math.IntNum
            if (r4 == 0) goto L_0x0064
            r14 = r24
            gnu.math.IntNum r14 = (gnu.math.IntNum) r14
            long r16 = r14.longValue()
            r4 = -2147483647(0xffffffff80000001, double:NaN)
            r6 = 2147483647(0x7fffffff, double:1.060997895E-314)
            boolean r20 = r14.inRange(r4, r6)
            goto L_0x002c
        L_0x0064:
            r20 = 0
            r16 = 0
            goto L_0x002c
        L_0x0069:
            r0 = r25
            int r4 = r0.f55op
            r5 = 1
            if (r4 == r5) goto L_0x0077
            r0 = r25
            int r4 = r0.f55op
            r5 = 3
            if (r4 != r5) goto L_0x00b5
        L_0x0077:
            r10 = 1
        L_0x0078:
            if (r10 == 0) goto L_0x00ff
            java.lang.Integer r4 = gnu.expr.InlineCalls.checkIntValue(r26)
            if (r4 == 0) goto L_0x0082
            r28 = 1
        L_0x0082:
            java.lang.Integer r4 = gnu.expr.InlineCalls.checkIntValue(r27)
            if (r4 == 0) goto L_0x008a
            r29 = 1
        L_0x008a:
            r4 = 1
            r0 = r28
            if (r0 != r4) goto L_0x00b7
            r4 = 1
            r0 = r29
            if (r0 == r4) goto L_0x00b7
            r21 = 1
        L_0x0096:
            if (r21 == 0) goto L_0x00ba
            boolean r4 = r26.side_effects()
            if (r4 == 0) goto L_0x00a4
            boolean r4 = r27.side_effects()
            if (r4 != 0) goto L_0x00ba
        L_0x00a4:
            r4 = r25
            r5 = r27
            r6 = r26
            r7 = r29
            r8 = r28
            r9 = r30
            boolean r4 = r4.compileIntNum(r5, r6, r7, r8, r9)
            goto L_0x0047
        L_0x00b5:
            r10 = 0
            goto L_0x0078
        L_0x00b7:
            r21 = 0
            goto L_0x0096
        L_0x00ba:
            r4 = 1
            r0 = r28
            if (r0 != r4) goto L_0x00f9
            gnu.bytecode.PrimType r22 = gnu.bytecode.Type.intType
        L_0x00c1:
            r4 = 1
            r0 = r29
            if (r0 != r4) goto L_0x00fc
            gnu.bytecode.PrimType r23 = gnu.bytecode.Type.intType
        L_0x00c8:
            r0 = r26
            r1 = r30
            r2 = r22
            r0.compile(r1, r2)
            r0 = r27
            r1 = r30
            r2 = r23
            r0.compile(r1, r2)
            gnu.bytecode.CodeAttr r12 = r30.getCode()
            if (r21 == 0) goto L_0x00e7
            r12.emitSwap()
            gnu.kawa.lispexpr.LangObjType r22 = gnu.kawa.functions.Arithmetic.typeIntNum
            gnu.bytecode.PrimType r23 = gnu.kawa.lispexpr.LangPrimType.intType
        L_0x00e7:
            r19 = 0
            r11 = 0
            gnu.kawa.lispexpr.LangObjType r15 = gnu.kawa.functions.Arithmetic.typeIntNum
            r0 = r25
            int r4 = r0.f55op
            switch(r4) {
                case 1: goto L_0x011d;
                case 2: goto L_0x0138;
                case 3: goto L_0x013b;
                case 4: goto L_0x014f;
                case 5: goto L_0x014f;
                case 6: goto L_0x014f;
                case 7: goto L_0x014f;
                case 8: goto L_0x014f;
                case 9: goto L_0x019f;
                case 10: goto L_0x018b;
                case 11: goto L_0x018b;
                case 12: goto L_0x00f3;
                case 13: goto L_0x013e;
                case 14: goto L_0x0140;
                case 15: goto L_0x0144;
                default: goto L_0x00f3;
            }
        L_0x00f3:
            java.lang.Error r4 = new java.lang.Error
            r4.<init>()
            throw r4
        L_0x00f9:
            gnu.kawa.lispexpr.LangObjType r22 = gnu.kawa.functions.Arithmetic.typeIntNum
            goto L_0x00c1
        L_0x00fc:
            gnu.kawa.lispexpr.LangObjType r23 = gnu.kawa.functions.Arithmetic.typeIntNum
            goto L_0x00c8
        L_0x00ff:
            r0 = r25
            int r4 = r0.f55op
            r5 = 9
            if (r4 < r5) goto L_0x0116
            r0 = r25
            int r4 = r0.f55op
            r5 = 12
            if (r4 > r5) goto L_0x0116
            gnu.kawa.lispexpr.LangObjType r22 = gnu.kawa.functions.Arithmetic.typeIntNum
            gnu.bytecode.PrimType r23 = gnu.bytecode.Type.intType
            r21 = 0
            goto L_0x00c8
        L_0x0116:
            gnu.kawa.lispexpr.LangObjType r23 = gnu.kawa.functions.Arithmetic.typeIntNum
            r22 = r23
            r21 = 0
            goto L_0x00c8
        L_0x011d:
            java.lang.String r19 = "add"
        L_0x011f:
            if (r11 != 0) goto L_0x012a
            r4 = 2
            gnu.bytecode.Type[] r11 = new gnu.bytecode.Type[r4]
            r4 = 0
            r11[r4] = r22
            r4 = 1
            r11[r4] = r23
        L_0x012a:
            r0 = r19
            gnu.bytecode.Method r18 = r15.getMethod(r0, r11)
            r0 = r18
            r12.emitInvokeStatic(r0)
            r4 = 1
            goto L_0x0047
        L_0x0138:
            java.lang.String r19 = "sub"
            goto L_0x011f
        L_0x013b:
            java.lang.String r19 = "times"
            goto L_0x011f
        L_0x013e:
            java.lang.String r19 = "and"
        L_0x0140:
            if (r19 != 0) goto L_0x0144
            java.lang.String r19 = "ior"
        L_0x0144:
            if (r19 != 0) goto L_0x0148
            java.lang.String r19 = "xor"
        L_0x0148:
            java.lang.String r4 = "gnu.math.BitOps"
            gnu.bytecode.ClassType r15 = gnu.bytecode.ClassType.make(r4)
            goto L_0x011f
        L_0x014f:
            r0 = r25
            int r4 = r0.f55op
            r5 = 8
            if (r4 != r5) goto L_0x016f
            java.lang.String r19 = "remainder"
        L_0x0159:
            r0 = r25
            gnu.mapping.Procedure r13 = r0.proc
            gnu.kawa.functions.DivideOp r13 = (gnu.kawa.functions.DivideOp) r13
            r0 = r25
            int r4 = r0.f55op
            r5 = 8
            if (r4 != r5) goto L_0x0172
            int r4 = r13.rounding_mode
            r5 = 1
            if (r4 != r5) goto L_0x0172
            java.lang.String r19 = "modulo"
            goto L_0x011f
        L_0x016f:
            java.lang.String r19 = "quotient"
            goto L_0x0159
        L_0x0172:
            int r4 = r13.rounding_mode
            r5 = 3
            if (r4 == r5) goto L_0x011f
            int r4 = r13.rounding_mode
            r12.emitPushInt(r4)
            r4 = 3
            gnu.bytecode.Type[] r11 = new gnu.bytecode.Type[r4]
            r4 = 0
            r11[r4] = r22
            r4 = 1
            r11[r4] = r23
            r4 = 2
            gnu.bytecode.PrimType r5 = gnu.bytecode.Type.intType
            r11[r4] = r5
            goto L_0x011f
        L_0x018b:
            r0 = r25
            int r4 = r0.f55op
            r5 = 10
            if (r4 != r5) goto L_0x019c
            java.lang.String r19 = "shiftLeft"
        L_0x0195:
            java.lang.String r4 = "gnu.kawa.functions.BitwiseOp"
            gnu.bytecode.ClassType r15 = gnu.bytecode.ClassType.make(r4)
            goto L_0x011f
        L_0x019c:
            java.lang.String r19 = "shiftRight"
            goto L_0x0195
        L_0x019f:
            java.lang.String r19 = "shift"
            goto L_0x011f
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.CompileArith.compileIntNum(gnu.expr.Expression, gnu.expr.Expression, int, int, gnu.expr.Compilation):boolean");
    }

    public static int getReturnKind(int kind1, int kind2, int op) {
        if (op >= 9 && op <= 12) {
            return kind1;
        }
        if (kind1 <= 0 || (kind1 > kind2 && kind2 > 0)) {
            kind2 = kind1;
        }
        return kind2;
    }

    public int getReturnKind(Expression[] args) {
        int len = args.length;
        if (len == 0) {
            return 4;
        }
        ClassType classType = Type.pointer_type;
        int kindr = 0;
        for (int i = 0; i < len; i++) {
            int kind = Arithmetic.classifyType(args[i].getType());
            if (i == 0 || kind == 0 || kind > kindr) {
                kindr = kind;
            }
        }
        return kindr;
    }

    public Type getReturnType(Expression[] args) {
        return Arithmetic.kindType(adjustReturnKind(getReturnKind(args), this.f55op));
    }

    static int adjustReturnKind(int rkind, int op) {
        if (op < 4 || op > 7 || rkind <= 0) {
            return rkind;
        }
        switch (op) {
            case 4:
                if (rkind <= 4) {
                    return 6;
                }
                return rkind;
            case 5:
                if (rkind > 10 || rkind == 7) {
                    return rkind;
                }
                return 8;
            case 7:
                if (rkind <= 10) {
                    return 4;
                }
                return rkind;
            default:
                return rkind;
        }
    }

    public static Expression validateApplyAdd(AddOp proc2, ApplyExp exp, InlineCalls visitor) {
        Expression[] args = exp.getArgs();
        if (args.length != 1 || proc2.plusOrMinus >= 0) {
            return exp;
        }
        Type type0 = args[0].getType();
        if (!(type0 instanceof PrimType)) {
            return exp;
        }
        char sig0 = type0.getSignature().charAt(0);
        Type type = null;
        int opcode = 0;
        if (!(sig0 == 'V' || sig0 == 'Z' || sig0 == 'C')) {
            if (sig0 == 'D') {
                opcode = 119;
                type = LangPrimType.doubleType;
            } else if (sig0 == 'F') {
                opcode = 118;
                type = LangPrimType.floatType;
            } else if (sig0 == 'J') {
                opcode = 117;
                type = LangPrimType.longType;
            } else {
                opcode = 116;
                type = LangPrimType.intType;
            }
        }
        if (type != null) {
            return new ApplyExp((Procedure) PrimProcedure.makeBuiltinUnary(opcode, type), args);
        }
        return exp;
    }

    public static Expression validateApplyDiv(DivideOp proc2, ApplyExp exp, InlineCalls visitor) {
        Expression[] args = exp.getArgs();
        if (args.length != 1) {
            return exp;
        }
        Expression[] args2 = {QuoteExp.getInstance(IntNum.one()), args[0]};
        Expression[] expressionArr = args2;
        return new ApplyExp(exp.getFunction(), args2);
    }

    public static Expression validateApplyNot(ApplyExp exp, int kind, InlineCalls visitor) {
        String cname;
        if (exp.getArgCount() != 1) {
            return exp;
        }
        Expression arg = exp.getArg(0);
        if (kind == 1 || kind == 2) {
            return visitor.visitApplyOnly(new ApplyExp((Procedure) BitwiseOp.xor, arg, QuoteExp.getInstance(IntNum.minusOne())), null);
        }
        if (kind == 4) {
            cname = "gnu.math.BitOps";
        } else if (kind == 3) {
            cname = "java.meth.BigInteger";
        } else {
            cname = null;
        }
        if (cname != null) {
            return new ApplyExp(ClassType.make(cname).getDeclaredMethod("not", 1), exp.getArgs());
        }
        return exp;
    }

    public static Expression validateApplyNumberCompare(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        Expression folded = exp.inlineIfConstant(proc2, visitor);
        return folded != exp ? folded : exp;
    }

    public int primitiveOpcode() {
        switch (this.f55op) {
            case 1:
                return 96;
            case 2:
                return 100;
            case 3:
                return 104;
            case 4:
            case 5:
            case 6:
            case 7:
                return 108;
            case 8:
                return 112;
            case 10:
                return 120;
            case 11:
                return 122;
            case 12:
                return 124;
            case 13:
                return 126;
            case 14:
                return 128;
            case 15:
                return 130;
            default:
                return -1;
        }
    }

    /* JADX WARNING: type inference failed for: r11v0, types: [gnu.expr.Expression[]] */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v1, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r0v0, types: [gnu.expr.Expression[]] */
    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r4v0, types: [gnu.expr.ApplyExp] */
    /* JADX WARNING: type inference failed for: r2v0, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=gnu.expr.Expression[], code=null, for r11v0, types: [gnu.expr.Expression[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v3
      assigns: []
      uses: []
      mth insns count: 17
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 8 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.expr.Expression pairwise(gnu.mapping.Procedure r9, gnu.expr.Expression r10, gnu.expr.Expression[] r11, gnu.expr.InlineCalls r12) {
        /*
            r8 = 0
            int r3 = r11.length
            r5 = r11[r8]
            r1 = 1
        L_0x0005:
            if (r1 >= r3) goto L_0x0023
            r6 = 2
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r6]
            r0[r8] = r5
            r6 = 1
            r7 = r11[r1]
            r0[r6] = r7
            gnu.expr.ApplyExp r4 = new gnu.expr.ApplyExp
            r4.<init>(r10, r0)
            r6 = 0
            gnu.expr.Expression r2 = r12.maybeInline(r4, r6, r9)
            if (r2 == 0) goto L_0x0021
            r5 = r2
        L_0x001e:
            int r1 = r1 + 1
            goto L_0x0005
        L_0x0021:
            r5 = r4
            goto L_0x001e
        L_0x0023:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.CompileArith.pairwise(gnu.mapping.Procedure, gnu.expr.Expression, gnu.expr.Expression[], gnu.expr.InlineCalls):gnu.expr.Expression");
    }

    public static Expression validateApplyNumberPredicate(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        int i = ((NumberPredicate) proc2).f59op;
        Expression[] args = exp.getArgs();
        args[0] = visitor.visit(args[0], (Type) LangObjType.integerType);
        exp.setType(Type.booleanType);
        return exp;
    }
}
