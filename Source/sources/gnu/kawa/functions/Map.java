package gnu.kawa.functions;

import gnu.expr.Declaration;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Values;

public class Map extends ProcedureN {
    final Declaration applyFieldDecl;
    final ApplyToArgs applyToArgs;
    boolean collect;
    final IsEq isEq;

    public Map(boolean collect2, ApplyToArgs applyToArgs2, Declaration applyFieldDecl2, IsEq isEq2) {
        super(collect2 ? "map" : "for-each");
        this.collect = collect2;
        this.applyToArgs = applyToArgs2;
        this.applyFieldDecl = applyFieldDecl2;
        this.isEq = isEq2;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyMap");
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v1, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r1v0, types: [gnu.lists.Pair, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v2
      assigns: []
      uses: []
      mth insns count: 20
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
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object map1(gnu.mapping.Procedure r6, java.lang.Object r7) throws java.lang.Throwable {
        /*
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            r0 = 0
        L_0x0003:
            gnu.lists.LList r4 = gnu.lists.LList.Empty
            if (r7 == r4) goto L_0x0026
            r2 = r7
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            gnu.lists.Pair r1 = new gnu.lists.Pair
            java.lang.Object r4 = r2.getCar()
            java.lang.Object r4 = r6.apply1(r4)
            gnu.lists.LList r5 = gnu.lists.LList.Empty
            r1.<init>(r4, r5)
            if (r0 != 0) goto L_0x0022
            r3 = r1
        L_0x001c:
            r0 = r1
            java.lang.Object r7 = r2.getCdr()
            goto L_0x0003
        L_0x0022:
            r0.setCdr(r1)
            goto L_0x001c
        L_0x0026:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.Map.map1(gnu.mapping.Procedure, java.lang.Object):java.lang.Object");
    }

    public static void forEach1(Procedure proc, Object list) throws Throwable {
        while (list != LList.Empty) {
            Pair pair = (Pair) list;
            proc.apply1(pair.getCar());
            list = pair.getCdr();
        }
    }

    public Object apply2(Object arg1, Object arg2) throws Throwable {
        if (arg1 instanceof Procedure) {
            Procedure proc = (Procedure) arg1;
            if (this.collect) {
                return map1(proc, arg2);
            }
            forEach1(proc, arg2);
            return Values.empty;
        }
        return applyN(new Object[]{arg1, arg2});
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r10v0, types: [gnu.mapping.Values] */
    /* JADX WARNING: type inference failed for: r10v1 */
    /* JADX WARNING: type inference failed for: r10v2, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r3v1, types: [gnu.lists.Pair] */
    /* JADX WARNING: type inference failed for: r10v3 */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r6v0, types: [gnu.lists.Pair, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r10v4 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r10v5 */
    /* JADX WARNING: type inference failed for: r10v6, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r10v9 */
    /* JADX WARNING: type inference failed for: r10v10 */
    /* JADX WARNING: type inference failed for: r10v11 */
    /* JADX WARNING: type inference failed for: r10v12 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r10v13 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r10v14 */
    /* JADX WARNING: type inference failed for: r10v15 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r10v1
      assigns: []
      uses: []
      mth insns count: 72
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
    /* JADX WARNING: Unknown variable types count: 10 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object applyN(java.lang.Object[] r15) throws java.lang.Throwable {
        /*
            r14 = this;
            int r12 = r15.length
            int r0 = r12 + -1
            r12 = 1
            if (r0 != r12) goto L_0x002a
            r12 = 0
            r12 = r15[r12]
            boolean r12 = r12 instanceof gnu.mapping.Procedure
            if (r12 == 0) goto L_0x002a
            r12 = 0
            r12 = r15[r12]
            gnu.mapping.Procedure r12 = (gnu.mapping.Procedure) r12
            r8 = r12
            gnu.mapping.Procedure r8 = (gnu.mapping.Procedure) r8
            boolean r12 = r14.collect
            if (r12 == 0) goto L_0x0021
            r12 = 1
            r12 = r15[r12]
            java.lang.Object r10 = map1(r8, r12)
        L_0x0020:
            return r10
        L_0x0021:
            r12 = 1
            r12 = r15[r12]
            forEach1(r8, r12)
            gnu.mapping.Values r10 = gnu.mapping.Values.empty
            goto L_0x0020
        L_0x002a:
            r3 = 0
            boolean r12 = r14.collect
            if (r12 == 0) goto L_0x0064
            gnu.lists.LList r10 = gnu.lists.LList.Empty
        L_0x0031:
            java.lang.Object[] r9 = new java.lang.Object[r0]
            r12 = 1
            r13 = 0
            java.lang.System.arraycopy(r15, r12, r9, r13, r0)
            r12 = 0
            r12 = r15[r12]
            boolean r12 = r12 instanceof gnu.mapping.Procedure
            if (r12 == 0) goto L_0x0067
            r5 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r12 = 0
            r8 = r15[r12]
            gnu.mapping.Procedure r8 = (gnu.mapping.Procedure) r8
        L_0x0047:
            r2 = 0
        L_0x0048:
            if (r2 >= r0) goto L_0x0075
            r4 = r9[r2]
            gnu.lists.LList r12 = gnu.lists.LList.Empty
            if (r4 == r12) goto L_0x0020
            r7 = r4
            gnu.lists.Pair r7 = (gnu.lists.Pair) r7
            int r12 = r5 + r2
            java.lang.Object r13 = r7.getCar()
            r1[r12] = r13
            java.lang.Object r12 = r7.getCdr()
            r9[r2] = r12
            int r2 = r2 + 1
            goto L_0x0048
        L_0x0064:
            gnu.mapping.Values r10 = gnu.mapping.Values.empty
            goto L_0x0031
        L_0x0067:
            r5 = 1
            int r12 = r0 + 1
            java.lang.Object[] r1 = new java.lang.Object[r12]
            r12 = 0
            r13 = 0
            r13 = r15[r13]
            r1[r12] = r13
            gnu.kawa.functions.ApplyToArgs r8 = r14.applyToArgs
            goto L_0x0047
        L_0x0075:
            java.lang.Object r11 = r8.applyN(r1)
            boolean r12 = r14.collect
            if (r12 == 0) goto L_0x0047
            gnu.lists.Pair r6 = new gnu.lists.Pair
            gnu.lists.LList r12 = gnu.lists.LList.Empty
            r6.<init>(r11, r12)
            if (r3 != 0) goto L_0x0089
            r10 = r6
        L_0x0087:
            r3 = r6
            goto L_0x0047
        L_0x0089:
            r3.setCdr(r6)
            goto L_0x0087
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.Map.applyN(java.lang.Object[]):java.lang.Object");
    }
}
