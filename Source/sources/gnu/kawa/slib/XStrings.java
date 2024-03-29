package gnu.kawa.slib;

import android.support.p000v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.math.IntNum;

/* compiled from: XStrings.scm */
public class XStrings extends ModuleBody {
    public static final XStrings $instance = new XStrings();
    static final IntNum Lit0 = IntNum.make((int) ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("substring").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("string-length").readResolve());
    public static final ModuleMethod string$Mnlength;
    public static final ModuleMethod substring;

    static {
        XStrings xStrings = $instance;
        substring = new ModuleMethod(xStrings, 1, Lit1, 12290);
        string$Mnlength = new ModuleMethod(xStrings, 3, Lit2, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public XStrings() {
        ModuleInfo.register(this);
    }

    public static Object substring(Object obj, Object obj2) {
        return substring(obj, obj2, Lit0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        if (r8 != false) goto L_0x000c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object substring(java.lang.Object r13, java.lang.Object r14, java.lang.Object r15) {
        /*
            r9 = 1
            r10 = 0
            r12 = -2
            gnu.mapping.Values r11 = gnu.mapping.Values.empty
            if (r13 != r11) goto L_0x000f
            r8 = r9
        L_0x0008:
            if (r8 == 0) goto L_0x0011
            if (r8 == 0) goto L_0x001a
        L_0x000c:
            gnu.mapping.Values r9 = gnu.mapping.Values.empty
        L_0x000e:
            return r9
        L_0x000f:
            r8 = r10
            goto L_0x0008
        L_0x0011:
            gnu.mapping.Values r11 = gnu.mapping.Values.empty
            if (r14 != r11) goto L_0x0040
            r8 = r9
        L_0x0016:
            if (r8 == 0) goto L_0x0042
            if (r8 != 0) goto L_0x000c
        L_0x001a:
            r0 = r13
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ ClassCastException -> 0x0049 }
            r5 = r0
            int r7 = r5.length()
            r0 = r14
            java.lang.Number r0 = (java.lang.Number) r0     // Catch:{ ClassCastException -> 0x0052 }
            r9 = r0
            int r6 = r9.intValue()     // Catch:{ ClassCastException -> 0x0052 }
            int r2 = r6 + -1
            r0 = r15
            java.lang.Number r0 = (java.lang.Number) r0     // Catch:{ ClassCastException -> 0x005b }
            r9 = r0
            int r3 = r9.intValue()     // Catch:{ ClassCastException -> 0x005b }
            int r1 = r7 - r2
            if (r3 <= r1) goto L_0x0047
            r4 = r1
        L_0x0039:
            int r9 = r2 + r4
            java.lang.String r9 = r5.substring(r2, r9)
            goto L_0x000e
        L_0x0040:
            r8 = r10
            goto L_0x0016
        L_0x0042:
            gnu.mapping.Values r9 = gnu.mapping.Values.empty
            if (r15 != r9) goto L_0x001a
            goto L_0x000c
        L_0x0047:
            r4 = r3
            goto L_0x0039
        L_0x0049:
            r9 = move-exception
            gnu.mapping.WrongType r10 = new gnu.mapping.WrongType
            java.lang.String r11 = "s"
            r10.<init>(r9, r11, r12, r13)
            throw r10
        L_0x0052:
            r9 = move-exception
            gnu.mapping.WrongType r10 = new gnu.mapping.WrongType
            java.lang.String r11 = "sindex"
            r10.<init>(r9, r11, r12, r14)
            throw r10
        L_0x005b:
            r9 = move-exception
            gnu.mapping.WrongType r10 = new gnu.mapping.WrongType
            java.lang.String r11 = "len"
            r10.<init>(r9, r11, r12, r15)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.XStrings.substring(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        return moduleMethod.selector == 1 ? substring(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        return moduleMethod.selector == 1 ? substring(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        if (moduleMethod.selector != 1) {
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.proc = moduleMethod;
        callContext.f232pc = 2;
        return 0;
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        if (moduleMethod.selector != 1) {
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.value3 = obj3;
        callContext.proc = moduleMethod;
        callContext.f232pc = 3;
        return 0;
    }

    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    public static Object stringLength(Object string) {
        if (string == Values.empty) {
            return Values.empty;
        }
        return Integer.valueOf(((String) string).length());
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        return moduleMethod.selector == 3 ? stringLength(obj) : super.apply1(moduleMethod, obj);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        if (moduleMethod.selector != 3) {
            return super.match1(moduleMethod, obj, callContext);
        }
        callContext.value1 = obj;
        callContext.proc = moduleMethod;
        callContext.f232pc = 1;
        return 0;
    }
}
