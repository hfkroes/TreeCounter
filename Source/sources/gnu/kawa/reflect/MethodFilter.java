package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Filter;
import gnu.bytecode.ObjectType;

/* compiled from: ClassMethods */
class MethodFilter implements Filter {
    ClassType caller;
    int modifiers;
    int modmask;
    String name;
    int nlen;
    ObjectType receiver;

    public MethodFilter(String name2, int modifiers2, int modmask2, ClassType caller2, ObjectType receiver2) {
        this.name = name2;
        this.nlen = name2.length();
        this.modifiers = modifiers2;
        this.modmask = modmask2;
        this.caller = caller2;
        this.receiver = receiver2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0046, code lost:
        if (r0 != 'X') goto L_0x0048;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean select(java.lang.Object r11) {
        /*
            r10 = this;
            r7 = 0
            r2 = r11
            gnu.bytecode.Method r2 = (gnu.bytecode.Method) r2
            java.lang.String r5 = r2.getName()
            int r4 = r2.getModifiers()
            int r6 = r10.modmask
            r6 = r6 & r4
            int r8 = r10.modifiers
            if (r6 != r8) goto L_0x001f
            r6 = r4 & 4096(0x1000, float:5.74E-42)
            if (r6 != 0) goto L_0x001f
            java.lang.String r6 = r10.name
            boolean r6 = r5.startsWith(r6)
            if (r6 != 0) goto L_0x0020
        L_0x001f:
            return r7
        L_0x0020:
            int r3 = r5.length()
            int r6 = r10.nlen
            if (r3 == r6) goto L_0x0056
            int r6 = r10.nlen
            int r6 = r6 + 2
            if (r3 != r6) goto L_0x0048
            int r6 = r10.nlen
            char r6 = r5.charAt(r6)
            r8 = 36
            if (r6 != r8) goto L_0x0048
            int r6 = r10.nlen
            int r6 = r6 + 1
            char r0 = r5.charAt(r6)
            r6 = 86
            if (r0 == r6) goto L_0x0056
            r6 = 88
            if (r0 == r6) goto L_0x0056
        L_0x0048:
            int r6 = r10.nlen
            int r6 = r6 + 4
            if (r3 != r6) goto L_0x001f
            java.lang.String r6 = "$V$X"
            boolean r6 = r5.endsWith(r6)
            if (r6 == 0) goto L_0x001f
        L_0x0056:
            gnu.bytecode.ObjectType r6 = r10.receiver
            boolean r6 = r6 instanceof gnu.bytecode.ClassType
            if (r6 == 0) goto L_0x0076
            gnu.bytecode.ObjectType r6 = r10.receiver
            gnu.bytecode.ClassType r6 = (gnu.bytecode.ClassType) r6
            r1 = r6
        L_0x0061:
            gnu.bytecode.ClassType r6 = r10.caller
            if (r6 == 0) goto L_0x0073
            gnu.bytecode.ClassType r6 = r10.caller
            gnu.bytecode.ObjectType r8 = r10.receiver
            int r9 = r2.getModifiers()
            boolean r6 = r6.isAccessible(r1, r8, r9)
            if (r6 == 0) goto L_0x007b
        L_0x0073:
            r6 = 1
        L_0x0074:
            r7 = r6
            goto L_0x001f
        L_0x0076:
            gnu.bytecode.ClassType r1 = r2.getDeclaringClass()
            goto L_0x0061
        L_0x007b:
            r6 = r7
            goto L_0x0074
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.MethodFilter.select(java.lang.Object):boolean");
    }
}
