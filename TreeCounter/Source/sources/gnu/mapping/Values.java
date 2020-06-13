package gnu.mapping;

import gnu.lists.Consumer;
import gnu.lists.TreeList;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Values extends TreeList implements Printable, Externalizable {
    public static final Values empty = new Values(noArgs);
    public static final Object[] noArgs = new Object[0];

    public Values() {
    }

    public Values(Object[] values) {
        for (Object writeObject : values) {
            writeObject(writeObject);
        }
    }

    public Object[] getValues() {
        return isEmpty() ? noArgs : toArray();
    }

    public static Object values(Object... vals) {
        return make(vals);
    }

    public static Values make() {
        return new Values();
    }

    public static Object make(Object[] vals) {
        if (vals.length == 1) {
            return vals[0];
        }
        if (vals.length == 0) {
            return empty;
        }
        return new Values(vals);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<java.lang.Object>, for r5v0, types: [java.util.List, java.util.List<java.lang.Object>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object make(java.util.List<java.lang.Object> r5) {
        /*
            r3 = 0
            if (r5 != 0) goto L_0x0009
            r0 = r3
        L_0x0004:
            if (r0 != 0) goto L_0x000e
            gnu.mapping.Values r2 = empty
        L_0x0008:
            return r2
        L_0x0009:
            int r0 = r5.size()
            goto L_0x0004
        L_0x000e:
            r4 = 1
            if (r0 != r4) goto L_0x0016
            java.lang.Object r2 = r5.get(r3)
            goto L_0x0008
        L_0x0016:
            gnu.mapping.Values r2 = new gnu.mapping.Values
            r2.<init>()
            java.util.Iterator r1 = r5.iterator()
        L_0x001f:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0008
            java.lang.Object r3 = r1.next()
            r2.writeObject(r3)
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.mapping.Values.make(java.util.List):java.lang.Object");
    }

    public static Object make(TreeList list) {
        return make(list, 0, list.data.length);
    }

    public static Object make(TreeList list, int startPosition, int endPosition) {
        if (startPosition != endPosition) {
            int next = list.nextDataIndex(startPosition);
            if (next > 0) {
                if (next == endPosition || list.nextDataIndex(next) < 0) {
                    return list.getPosNext(startPosition << 1);
                }
                Values vals = new Values();
                list.consumeIRange(startPosition, endPosition, vals);
                return vals;
            }
        }
        return empty;
    }

    /* Debug info: failed to restart local var, previous not found, register: 3 */
    public final Object canonicalize() {
        if (this.gapEnd != this.data.length) {
            return this;
        }
        if (this.gapStart == 0) {
            return empty;
        }
        if (nextDataIndex(0) == this.gapStart) {
            return getPosNext(0);
        }
        return this;
    }

    public Object call_with(Procedure proc) throws Throwable {
        return proc.applyN(toArray());
    }

    public void print(Consumer out) {
        if (this == empty) {
            out.write("#!void");
            return;
        }
        int length = toArray().length;
        if (1 != 0) {
            out.write("#<values");
        }
        int i = 0;
        while (true) {
            int next = nextDataIndex(i);
            if (next < 0) {
                break;
            }
            out.write(32);
            if (i >= this.gapEnd) {
                i -= this.gapEnd - this.gapStart;
            }
            Object val = getPosNext(i << 1);
            if (val instanceof Printable) {
                ((Printable) val).print(out);
            } else {
                out.writeObject(val);
            }
            i = next;
        }
        if (1 != 0) {
            out.write(62);
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(len);
        for (Object writeObject : toArray()) {
            out.writeObject(writeObject);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int len = in.readInt();
        for (int i = 0; i < len; i++) {
            writeObject(in.readObject());
        }
    }

    public Object readResolve() throws ObjectStreamException {
        return isEmpty() ? empty : this;
    }

    public static int nextIndex(Object values, int curIndex) {
        if (values instanceof Values) {
            return ((Values) values).nextDataIndex(curIndex);
        }
        return curIndex == 0 ? 1 : -1;
    }

    public static Object nextValue(Object values, int curIndex) {
        if (!(values instanceof Values)) {
            return values;
        }
        Values v = (Values) values;
        if (curIndex >= v.gapEnd) {
            curIndex -= v.gapEnd - v.gapStart;
        }
        return ((Values) values).getPosNext(curIndex << 1);
    }

    public static void writeValues(Object value, Consumer out) {
        if (value instanceof Values) {
            ((Values) value).consume(out);
        } else {
            out.writeObject(value);
        }
    }

    public static int countValues(Object value) {
        if (value instanceof Values) {
            return ((Values) value).size();
        }
        return 1;
    }
}
