package gnu.kawa.util;

public class IdentityHashTable<K, V> extends GeneralHashTable<K, V> {
    public IdentityHashTable() {
    }

    public IdentityHashTable(int capacity) {
        super(capacity);
    }

    public int hash(Object key) {
        return System.identityHashCode(key);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object, code=K, for r3v0, types: [java.lang.Object, K] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean matches(K r2, K r3) {
        /*
            r1 = this;
            if (r2 != r3) goto L_0x0004
            r0 = 1
        L_0x0003:
            return r0
        L_0x0004:
            r0 = 0
            goto L_0x0003
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.util.IdentityHashTable.matches(java.lang.Object, java.lang.Object):boolean");
    }
}
