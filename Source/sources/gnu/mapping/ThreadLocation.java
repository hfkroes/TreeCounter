package gnu.mapping;

public class ThreadLocation extends NamedLocation implements Named {
    public static final String ANONYMOUS = new String("(dynamic)");
    static int counter;
    static SimpleEnvironment env;
    SharedLocation global;
    private int hash;
    private ThreadLocal<NamedLocation> thLocal;

    public class InheritingLocation extends InheritableThreadLocal<NamedLocation> {
        public InheritingLocation() {
        }

        /* JADX WARNING: type inference failed for: r7v1 */
        /* JADX WARNING: type inference failed for: r0v0, types: [gnu.mapping.NamedLocation] */
        /* JADX WARNING: type inference failed for: r0v1, types: [gnu.mapping.Location] */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r7v3, types: [gnu.mapping.SharedLocation] */
        /* JADX WARNING: type inference failed for: r7v4 */
        /* access modifiers changed from: protected */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Unknown variable types count: 2 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public gnu.mapping.SharedLocation childValue(gnu.mapping.NamedLocation r7) {
            /*
                r6 = this;
                r5 = 0
                r4 = 0
                gnu.mapping.ThreadLocation r2 = gnu.mapping.ThreadLocation.this
                java.lang.Object r2 = r2.property
                java.lang.String r3 = gnu.mapping.ThreadLocation.ANONYMOUS
                if (r2 == r3) goto L_0x0010
                java.lang.Error r2 = new java.lang.Error
                r2.<init>()
                throw r2
            L_0x0010:
                if (r7 != 0) goto L_0x001a
                gnu.mapping.ThreadLocation r2 = gnu.mapping.ThreadLocation.this
                gnu.mapping.NamedLocation r7 = r2.getLocation()
                gnu.mapping.SharedLocation r7 = (gnu.mapping.SharedLocation) r7
            L_0x001a:
                r0 = r7
                gnu.mapping.Location r2 = r0.base
                if (r2 != 0) goto L_0x0035
                gnu.mapping.SharedLocation r1 = new gnu.mapping.SharedLocation
                gnu.mapping.ThreadLocation r2 = gnu.mapping.ThreadLocation.this
                gnu.mapping.Symbol r2 = r2.name
                gnu.mapping.ThreadLocation r3 = gnu.mapping.ThreadLocation.this
                java.lang.Object r3 = r3.property
                r1.<init>(r2, r3, r4)
                java.lang.Object r2 = r0.value
                r1.value = r2
                r0.base = r1
                r0.value = r5
                r0 = r1
            L_0x0035:
                gnu.mapping.SharedLocation r1 = new gnu.mapping.SharedLocation
                gnu.mapping.ThreadLocation r2 = gnu.mapping.ThreadLocation.this
                gnu.mapping.Symbol r2 = r2.name
                gnu.mapping.ThreadLocation r3 = gnu.mapping.ThreadLocation.this
                java.lang.Object r3 = r3.property
                r1.<init>(r2, r3, r4)
                r1.value = r5
                r1.base = r0
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.mapping.ThreadLocation.InheritingLocation.childValue(gnu.mapping.NamedLocation):gnu.mapping.SharedLocation");
        }
    }

    private static synchronized int nextCounter() {
        int i;
        synchronized (ThreadLocation.class) {
            i = counter + 1;
            counter = i;
        }
        return i;
    }

    public ThreadLocation() {
        this("param#" + nextCounter());
    }

    public ThreadLocation(String name) {
        super(Symbol.makeUninterned(name), ANONYMOUS);
        this.thLocal = new InheritingLocation();
        this.global = new SharedLocation(this.name, null, 0);
    }

    private ThreadLocation(Symbol name) {
        super(name, ANONYMOUS);
        this.thLocal = new InheritingLocation();
        this.global = new SharedLocation(Symbol.makeUninterned(name == null ? null : name.toString()), null, 0);
    }

    public ThreadLocation(Symbol name, Object property, SharedLocation global2) {
        super(name, property);
        this.hash = name.hashCode() ^ System.identityHashCode(property);
        this.global = global2;
    }

    public static ThreadLocation makeAnonymous(String name) {
        return new ThreadLocation(name);
    }

    public static ThreadLocation makeAnonymous(Symbol name) {
        return new ThreadLocation(name);
    }

    public void setGlobal(Object value) {
        synchronized (this) {
            if (this.global == null) {
                this.global = new SharedLocation(this.name, null, 0);
            }
            this.global.set(value);
        }
    }

    public NamedLocation getLocation() {
        if (this.property != ANONYMOUS) {
            return Environment.getCurrent().getLocation(this.name, this.property, this.hash, true);
        }
        NamedLocation entry = (NamedLocation) this.thLocal.get();
        if (entry != null) {
            return entry;
        }
        NamedLocation entry2 = new SharedLocation(this.name, this.property, 0);
        if (this.global != null) {
            entry2.setBase(this.global);
        }
        this.thLocal.set(entry2);
        return entry2;
    }

    public Object get(Object defaultValue) {
        return getLocation().get(defaultValue);
    }

    public void set(Object value) {
        getLocation().set(value);
    }

    public Object setWithSave(Object newValue) {
        return getLocation().setWithSave(newValue);
    }

    public void setRestore(Object oldValue) {
        getLocation().setRestore(oldValue);
    }

    public String getName() {
        if (this.name == null) {
            return null;
        }
        return this.name.toString();
    }

    public Object getSymbol() {
        if (this.name != null && this.property == ANONYMOUS && this.global.getKeySymbol() == this.name) {
            return this.name.toString();
        }
        return this.name;
    }

    public void setName(String name) {
        throw new RuntimeException("setName not allowed");
    }

    public static synchronized ThreadLocation getInstance(Symbol name, Object property) {
        ThreadLocation threadLocation;
        synchronized (ThreadLocation.class) {
            if (env == null) {
                env = new SimpleEnvironment("[thread-locations]");
            }
            IndirectableLocation loc = (IndirectableLocation) env.getLocation(name, property);
            if (loc.base != null) {
                threadLocation = (ThreadLocation) loc.base;
            } else {
                ThreadLocation tloc = new ThreadLocation(name, property, null);
                loc.base = tloc;
                threadLocation = tloc;
            }
        }
        return threadLocation;
    }
}
