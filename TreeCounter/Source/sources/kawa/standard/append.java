package kawa.standard;

import gnu.mapping.ProcedureN;

public class append extends ProcedureN {
    public static final append append = new append();

    static {
        append.setName("append");
    }

    public Object applyN(Object[] args) {
        return append$V(args);
    }

    /* JADX WARNING: type inference failed for: r13v0, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r7v1 */
    /* JADX WARNING: type inference failed for: r8v1, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r3v1, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r7v2 */
    /* JADX WARNING: type inference failed for: r11v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r7v3 */
    /* JADX WARNING: type inference failed for: r7v4 */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r6v0, types: [gnu.lists.Pair, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r4v2, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r7v5 */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r7v6 */
    /* JADX WARNING: type inference failed for: r7v7 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object[], code=null, for r13v0, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r7v4
      assigns: []
      uses: []
      mth insns count: 46
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
    /* JADX WARNING: Unknown variable types count: 16 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object append$V(java.lang.Object[] r13) {
        /*
            int r1 = r13.length
            if (r1 != 0) goto L_0x0006
            gnu.lists.LList r8 = gnu.lists.LList.Empty
        L_0x0005:
            return r8
        L_0x0006:
            int r8 = r1 + -1
            r7 = r13[r8]
            int r2 = r1 + -1
            r8 = r7
        L_0x000d:
            int r2 = r2 + -1
            if (r2 < 0) goto L_0x0005
            r4 = r13[r2]
            r0 = 0
            r3 = 0
            r7 = r0
        L_0x0016:
            boolean r9 = r4 instanceof gnu.lists.Pair
            if (r9 == 0) goto L_0x0036
            r5 = r4
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            gnu.lists.Pair r6 = new gnu.lists.Pair
            java.lang.Object r9 = r5.getCar()
            r10 = 0
            r6.<init>(r9, r10)
            if (r3 != 0) goto L_0x0031
            r0 = r6
        L_0x002a:
            r3 = r6
            java.lang.Object r4 = r5.getCdr()
            r7 = r0
            goto L_0x0016
        L_0x0031:
            r3.setCdr(r6)
            r0 = r7
            goto L_0x002a
        L_0x0036:
            gnu.lists.LList r9 = gnu.lists.LList.Empty
            if (r4 == r9) goto L_0x0048
            gnu.mapping.WrongType r8 = new gnu.mapping.WrongType
            kawa.standard.append r9 = append
            int r10 = r2 + 1
            r11 = r13[r2]
            java.lang.String r12 = "list"
            r8.<init>(r9, r10, r11, r12)
            throw r8
        L_0x0048:
            if (r3 == 0) goto L_0x004f
            r3.setCdr(r8)
        L_0x004d:
            r8 = r7
            goto L_0x000d
        L_0x004f:
            r7 = r8
            goto L_0x004d
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.append.append$V(java.lang.Object[]):java.lang.Object");
    }
}
