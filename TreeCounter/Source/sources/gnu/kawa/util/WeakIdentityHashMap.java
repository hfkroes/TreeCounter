package gnu.kawa.util;

import java.lang.ref.ReferenceQueue;

public class WeakIdentityHashMap<K, V> extends AbstractHashTable<WeakHashNode<K, V>, K, V> {
    ReferenceQueue<K> rqueue = new ReferenceQueue<>();

    public WeakIdentityHashMap() {
        super(64);
    }

    public WeakIdentityHashMap(int capacity) {
        super(capacity);
    }

    /* access modifiers changed from: protected */
    public int getEntryHashCode(WeakHashNode<K, V> entry) {
        return entry.hash;
    }

    /* access modifiers changed from: protected */
    public WeakHashNode<K, V> getEntryNext(WeakHashNode<K, V> entry) {
        return entry.next;
    }

    /* access modifiers changed from: protected */
    public void setEntryNext(WeakHashNode<K, V> entry, WeakHashNode<K, V> next) {
        entry.next = next;
    }

    /* access modifiers changed from: protected */
    public WeakHashNode<K, V>[] allocEntries(int n) {
        return (WeakHashNode[]) new WeakHashNode[n];
    }

    public int hash(Object key) {
        return System.identityHashCode(key);
    }

    /* access modifiers changed from: protected */
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
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.util.WeakIdentityHashMap.matches(java.lang.Object, java.lang.Object):boolean");
    }

    /* access modifiers changed from: protected */
    public WeakHashNode<K, V> makeEntry(K key, int hash, V value) {
        WeakHashNode<K, V> node = new WeakHashNode<>(key, this.rqueue, hash);
        node.value = value;
        return node;
    }

    public V get(Object key, V defaultValue) {
        cleanup();
        return super.get(key, defaultValue);
    }

    public V put(K key, int hash, V value) {
        cleanup();
        return super.put(key, hash, value);
    }

    public V remove(Object key) {
        cleanup();
        return super.remove(key);
    }

    /* access modifiers changed from: 0000 */
    public void cleanup() {
        AbstractWeakHashTable.cleanup(this, this.rqueue);
    }
}
