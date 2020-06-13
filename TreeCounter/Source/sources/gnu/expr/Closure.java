package gnu.expr;

import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;

/* compiled from: LambdaExp */
class Closure extends MethodProc {
    Object[][] evalFrames;
    LambdaExp lambda;

    public int numArgs() {
        return this.lambda.min_args | (this.lambda.max_args << 12);
    }

    public Closure(LambdaExp lexp, CallContext ctx) {
        this.lambda = lexp;
        Object[][] oldFrames = ctx.evalFrames;
        if (oldFrames != null) {
            int n = oldFrames.length;
            while (n > 0 && oldFrames[n - 1] == null) {
                n--;
            }
            this.evalFrames = new Object[n][];
            System.arraycopy(oldFrames, 0, this.evalFrames, 0, n);
        }
        setSymbol(this.lambda.getSymbol());
    }

    public int match0(CallContext ctx) {
        return matchN(new Object[0], ctx);
    }

    public int match1(Object arg1, CallContext ctx) {
        return matchN(new Object[]{arg1}, ctx);
    }

    public int match2(Object arg1, Object arg2, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2}, ctx);
    }

    public int match3(Object arg1, Object arg2, Object arg3, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3}, ctx);
    }

    public int match4(Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        return matchN(new Object[]{arg1, arg2, arg3, arg4}, ctx);
    }

    /* JADX WARNING: type inference failed for: r32v0, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r8v0, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r0v50, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r28v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v51 */
    /* JADX WARNING: type inference failed for: r28v1 */
    /* JADX WARNING: type inference failed for: r28v2, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v59, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r28v3, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r28v4, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r29v32 */
    /* JADX WARNING: type inference failed for: r0v64, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v65, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v5 */
    /* JADX WARNING: type inference failed for: r0v67, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r27v0 */
    /* JADX WARNING: type inference failed for: r0v68, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v8, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v6 */
    /* JADX WARNING: type inference failed for: r28v7, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v8 */
    /* JADX WARNING: type inference failed for: r28v9 */
    /* JADX WARNING: type inference failed for: r28v10 */
    /* JADX WARNING: type inference failed for: r28v11 */
    /* JADX WARNING: type inference failed for: r28v12 */
    /* JADX WARNING: type inference failed for: r18v0, types: [gnu.mapping.Location] */
    /* JADX WARNING: type inference failed for: r0v75, types: [gnu.mapping.Location] */
    /* JADX WARNING: type inference failed for: r1v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v13 */
    /* JADX WARNING: type inference failed for: r1v11, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v14, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r28v15 */
    /* JADX WARNING: type inference failed for: r28v16 */
    /* JADX WARNING: type inference failed for: r28v17 */
    /* JADX WARNING: type inference failed for: r28v18 */
    /* JADX WARNING: type inference failed for: r28v19 */
    /* JADX WARNING: type inference failed for: r28v20 */
    /* JADX WARNING: type inference failed for: r28v21 */
    /* JADX WARNING: type inference failed for: r28v22 */
    /* JADX WARNING: type inference failed for: r28v23 */
    /* JADX WARNING: type inference failed for: r28v24 */
    /* JADX WARNING: type inference failed for: r28v25 */
    /* JADX WARNING: type inference failed for: r28v26 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object[], code=null, for r32v0, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r28v1
      assigns: []
      uses: []
      mth insns count: 223
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
    /* JADX WARNING: Unknown variable types count: 28 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int matchN(java.lang.Object[] r32, gnu.mapping.CallContext r33) {
        /*
            r31 = this;
            int r23 = r31.numArgs()
            r0 = r32
            int r0 = r0.length
            r22 = r0
            r0 = r23
            r0 = r0 & 4095(0xfff, float:5.738E-42)
            r20 = r0
            r0 = r22
            r1 = r20
            if (r0 >= r1) goto L_0x001a
            r29 = -983040(0xfffffffffff10000, float:NaN)
            r29 = r29 | r20
        L_0x0019:
            return r29
        L_0x001a:
            int r19 = r23 >> 12
            r0 = r22
            r1 = r19
            if (r0 <= r1) goto L_0x0029
            if (r19 < 0) goto L_0x0029
            r29 = -917504(0xfffffffffff20000, float:NaN)
            r29 = r29 | r19
            goto L_0x0019
        L_0x0029:
            r0 = r31
            gnu.expr.LambdaExp r0 = r0.lambda
            r29 = r0
            r0 = r29
            int r0 = r0.frameSize
            r29 = r0
            r0 = r29
            java.lang.Object[] r8 = new java.lang.Object[r0]
            r0 = r31
            gnu.expr.LambdaExp r0 = r0.lambda
            r29 = r0
            r0 = r29
            gnu.expr.Keyword[] r0 = r0.keywords
            r29 = r0
            if (r29 != 0) goto L_0x00b1
            r13 = 0
        L_0x0048:
            r0 = r31
            gnu.expr.LambdaExp r0 = r0.lambda
            r29 = r0
            r0 = r29
            gnu.expr.Expression[] r0 = r0.defaultArgs
            r29 = r0
            if (r29 != 0) goto L_0x00c1
            r24 = 0
        L_0x0058:
            r10 = 0
            r25 = 0
            r14 = 0
            r0 = r31
            gnu.expr.LambdaExp r0 = r0.lambda
            r29 = r0
            r0 = r29
            int r0 = r0.min_args
            r21 = r0
            r0 = r31
            gnu.expr.LambdaExp r0 = r0.lambda
            r29 = r0
            gnu.expr.Declaration r4 = r29.firstDecl()
            r15 = r14
            r11 = r10
        L_0x0074:
            if (r4 == 0) goto L_0x01b8
            r0 = r21
            if (r11 >= r0) goto L_0x00d5
            int r10 = r11 + 1
            r28 = r32[r11]
            r14 = r15
        L_0x007f:
            gnu.bytecode.Type r0 = r4.type
            r29 = r0
            if (r29 == 0) goto L_0x0091
            gnu.bytecode.Type r0 = r4.type     // Catch:{ ClassCastException -> 0x01b1 }
            r29 = r0
            r0 = r29
            r1 = r28
            java.lang.Object r28 = r0.coerceFromObject(r1)     // Catch:{ ClassCastException -> 0x01b1 }
        L_0x0091:
            boolean r29 = r4.isIndirectBinding()
            if (r29 == 0) goto L_0x00a4
            gnu.mapping.Location r18 = r4.makeIndirectLocationFor()
            r0 = r18
            r1 = r28
            r0.set(r1)
            r28 = r18
        L_0x00a4:
            int r0 = r4.evalIndex
            r29 = r0
            r8[r29] = r28
            gnu.expr.Declaration r4 = r4.nextDecl()
            r15 = r14
            r11 = r10
            goto L_0x0074
        L_0x00b1:
            r0 = r31
            gnu.expr.LambdaExp r0 = r0.lambda
            r29 = r0
            r0 = r29
            gnu.expr.Keyword[] r0 = r0.keywords
            r29 = r0
            r0 = r29
            int r13 = r0.length
            goto L_0x0048
        L_0x00c1:
            r0 = r31
            gnu.expr.LambdaExp r0 = r0.lambda
            r29 = r0
            r0 = r29
            gnu.expr.Expression[] r0 = r0.defaultArgs
            r29 = r0
            r0 = r29
            int r0 = r0.length
            r29 = r0
            int r24 = r29 - r13
            goto L_0x0058
        L_0x00d5:
            int r29 = r21 + r24
            r0 = r29
            if (r11 >= r0) goto L_0x00f9
            r0 = r22
            if (r11 >= r0) goto L_0x00e7
            int r10 = r11 + 1
            r28 = r32[r11]
        L_0x00e3:
            int r25 = r25 + 1
            r14 = r15
            goto L_0x007f
        L_0x00e7:
            r0 = r31
            gnu.expr.LambdaExp r0 = r0.lambda
            r29 = r0
            r0 = r29
            r1 = r25
            r2 = r33
            java.lang.Object r28 = r0.evalDefaultArg(r1, r2)
            r10 = r11
            goto L_0x00e3
        L_0x00f9:
            r0 = r31
            gnu.expr.LambdaExp r0 = r0.lambda
            r29 = r0
            r0 = r29
            int r0 = r0.max_args
            r29 = r0
            if (r29 >= 0) goto L_0x0178
            int r29 = r21 + r24
            r0 = r29
            if (r11 != r0) goto L_0x0178
            gnu.bytecode.Type r0 = r4.type
            r29 = r0
            r0 = r29
            boolean r0 = r0 instanceof gnu.bytecode.ArrayType
            r29 = r0
            if (r29 == 0) goto L_0x016e
            int r26 = r22 - r11
            gnu.bytecode.Type r0 = r4.type
            r29 = r0
            gnu.bytecode.ArrayType r29 = (gnu.bytecode.ArrayType) r29
            gnu.bytecode.Type r7 = r29.getComponentType()
            gnu.bytecode.ClassType r29 = gnu.bytecode.Type.objectType
            r0 = r29
            if (r7 != r0) goto L_0x0144
            r0 = r26
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r27 = r0
            r29 = 0
            r0 = r32
            r1 = r27
            r2 = r29
            r3 = r26
            java.lang.System.arraycopy(r0, r11, r1, r2, r3)
            r28 = r27
        L_0x0140:
            r14 = r15
            r10 = r11
            goto L_0x007f
        L_0x0144:
            java.lang.Class r6 = r7.getReflectClass()
            r0 = r26
            java.lang.Object r28 = java.lang.reflect.Array.newInstance(r6, r0)
            r12 = 0
        L_0x014f:
            r0 = r26
            if (r12 >= r0) goto L_0x0140
            int r29 = r11 + r12
            r29 = r32[r29]     // Catch:{ ClassCastException -> 0x0165 }
            r0 = r29
            java.lang.Object r5 = r7.coerceFromObject(r0)     // Catch:{ ClassCastException -> 0x0165 }
            r0 = r28
            java.lang.reflect.Array.set(r0, r12, r5)
            int r12 = r12 + 1
            goto L_0x014f
        L_0x0165:
            r9 = move-exception
            r29 = -786432(0xfffffffffff40000, float:NaN)
            int r30 = r11 + r12
            r29 = r29 | r30
            goto L_0x0019
        L_0x016e:
            r0 = r32
            gnu.lists.LList r28 = gnu.lists.LList.makeList(r0, r11)
            r14 = r15
            r10 = r11
            goto L_0x007f
        L_0x0178:
            r0 = r31
            gnu.expr.LambdaExp r0 = r0.lambda
            r29 = r0
            r0 = r29
            gnu.expr.Keyword[] r0 = r0.keywords
            r29 = r0
            int r14 = r15 + 1
            r17 = r29[r15]
            int r16 = r21 + r24
            r0 = r32
            r1 = r16
            r2 = r17
            java.lang.Object r28 = gnu.expr.Keyword.searchForKeyword(r0, r1, r2)
            gnu.expr.Special r29 = gnu.expr.Special.dfault
            r0 = r28
            r1 = r29
            if (r0 != r1) goto L_0x01ac
            r0 = r31
            gnu.expr.LambdaExp r0 = r0.lambda
            r29 = r0
            r0 = r29
            r1 = r25
            r2 = r33
            java.lang.Object r28 = r0.evalDefaultArg(r1, r2)
        L_0x01ac:
            int r25 = r25 + 1
            r10 = r11
            goto L_0x007f
        L_0x01b1:
            r9 = move-exception
            r29 = -786432(0xfffffffffff40000, float:NaN)
            r29 = r29 | r10
            goto L_0x0019
        L_0x01b8:
            r0 = r33
            r0.values = r8
            r29 = 0
            r0 = r29
            r1 = r33
            r1.where = r0
            r29 = 0
            r0 = r29
            r1 = r33
            r1.next = r0
            r0 = r31
            r1 = r33
            r1.proc = r0
            r29 = 0
            goto L_0x0019
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.Closure.matchN(java.lang.Object[], gnu.mapping.CallContext):int");
    }

    public void apply(CallContext ctx) throws Throwable {
        int numFrames;
        int level = ScopeExp.nesting(this.lambda);
        Object[] evalFrame = ctx.values;
        Object[][] saveFrames = ctx.evalFrames;
        if (this.evalFrames == null) {
            numFrames = 0;
        } else {
            numFrames = this.evalFrames.length;
        }
        if (level >= numFrames) {
            numFrames = level;
        }
        Object[][] newFrames = new Object[(numFrames + 10)][];
        if (this.evalFrames != null) {
            System.arraycopy(this.evalFrames, 0, newFrames, 0, this.evalFrames.length);
        }
        newFrames[level] = evalFrame;
        ctx.evalFrames = newFrames;
        try {
            if (this.lambda.body == null) {
                StringBuffer sbuf = new StringBuffer("procedure ");
                String name = this.lambda.getName();
                if (name == null) {
                    name = "<anonymous>";
                }
                sbuf.append(name);
                int line = this.lambda.getLineNumber();
                if (line > 0) {
                    sbuf.append(" at line ");
                    sbuf.append(line);
                }
                sbuf.append(" was called before it was expanded");
                throw new RuntimeException(sbuf.toString());
            }
            this.lambda.body.apply(ctx);
        } finally {
            ctx.evalFrames = saveFrames;
        }
    }

    public Object getProperty(Object key, Object defaultValue) {
        Object value = super.getProperty(key, defaultValue);
        if (value == null) {
            return this.lambda.getProperty(key, defaultValue);
        }
        return value;
    }
}
