package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.CheckedTarget;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.lists.FString;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure3;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import kawa.standard.Scheme;

public class SlotSet extends Procedure3 implements Inlineable {
    public static final SlotSet set$Mnfield$Ex = new SlotSet("set-field!", false);
    public static final SlotSet set$Mnstatic$Mnfield$Ex = new SlotSet("set-static-field!", true);
    public static final SlotSet setFieldReturnObject = new SlotSet("set-field-return-object!", false);
    static final Type[] type1Array = new Type[1];
    boolean isStatic;
    boolean returnSelf;

    static {
        setFieldReturnObject.returnSelf = true;
    }

    public SlotSet(String name, boolean isStatic2) {
        super(name);
        this.isStatic = isStatic2;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplySlotSet");
    }

    public static void setField(Object obj, String name, Object value) {
        apply(false, obj, name, value);
    }

    public static void setStaticField(Object obj, String name, Object value) {
        apply(true, obj, name, value);
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0145  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void apply(boolean r21, java.lang.Object r22, java.lang.Object r23, java.lang.Object r24) {
        /*
            gnu.expr.Language r13 = gnu.expr.Language.getDefaultLanguage()
            r12 = 0
            r0 = r23
            boolean r0 = r0 instanceof java.lang.String
            r18 = r0
            if (r18 != 0) goto L_0x001d
            r0 = r23
            boolean r0 = r0 instanceof gnu.lists.FString
            r18 = r0
            if (r18 != 0) goto L_0x001d
            r0 = r23
            boolean r0 = r0 instanceof gnu.mapping.Symbol
            r18 = r0
            if (r18 == 0) goto L_0x0054
        L_0x001d:
            java.lang.String r14 = r23.toString()
            java.lang.String r6 = gnu.expr.Compilation.mangleNameIfNeeded(r14)
            if (r21 == 0) goto L_0x004f
            java.lang.Class r3 = gnu.kawa.reflect.SlotGet.coerceToClass(r22)
        L_0x002b:
            r0 = r23
            boolean r0 = r0 instanceof gnu.bytecode.Field     // Catch:{ NoSuchFieldException -> 0x0170, IllegalAccessException -> 0x0064 }
            r18 = r0
            if (r18 == 0) goto L_0x005f
            r0 = r23
            gnu.bytecode.Field r0 = (gnu.bytecode.Field) r0     // Catch:{ NoSuchFieldException -> 0x0170, IllegalAccessException -> 0x0064 }
            r18 = r0
            java.lang.reflect.Field r5 = r18.getReflectField()     // Catch:{ NoSuchFieldException -> 0x0170, IllegalAccessException -> 0x0064 }
        L_0x003d:
            java.lang.Class r7 = r5.getType()     // Catch:{ NoSuchFieldException -> 0x0170, IllegalAccessException -> 0x0064 }
            r0 = r24
            java.lang.Object r18 = r13.coerceFromObject(r7, r0)     // Catch:{ NoSuchFieldException -> 0x0170, IllegalAccessException -> 0x0064 }
            r0 = r22
            r1 = r18
            r5.set(r0, r1)     // Catch:{ NoSuchFieldException -> 0x0170, IllegalAccessException -> 0x0064 }
        L_0x004e:
            return
        L_0x004f:
            java.lang.Class r3 = r22.getClass()
            goto L_0x002b
        L_0x0054:
            r18 = r23
            gnu.bytecode.Member r18 = (gnu.bytecode.Member) r18
            java.lang.String r14 = r18.getName()
            r6 = r14
            r3 = 0
            goto L_0x002b
        L_0x005f:
            java.lang.reflect.Field r5 = r3.getField(r6)     // Catch:{ NoSuchFieldException -> 0x0170, IllegalAccessException -> 0x0064 }
            goto L_0x003d
        L_0x0064:
            r4 = move-exception
            r12 = 1
        L_0x0066:
            r10 = 0
            r0 = r23
            boolean r11 = r0 instanceof gnu.bytecode.Method     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            if (r11 == 0) goto L_0x00e2
            r16 = r6
        L_0x006f:
            if (r11 == 0) goto L_0x007e
            java.lang.String r18 = "set"
            r0 = r16
            r1 = r18
            boolean r18 = r0.startsWith(r1)     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            if (r18 != 0) goto L_0x007e
            r11 = 0
        L_0x007e:
            if (r11 == 0) goto L_0x00eb
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f4 }
            r18.<init>()     // Catch:{ Exception -> 0x00f4 }
            java.lang.String r19 = "get"
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Exception -> 0x00f4 }
            r19 = 3
            r0 = r16
            r1 = r19
            java.lang.String r19 = r0.substring(r1)     // Catch:{ Exception -> 0x00f4 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ Exception -> 0x00f4 }
            java.lang.String r9 = r18.toString()     // Catch:{ Exception -> 0x00f4 }
        L_0x009d:
            java.lang.Class[] r18 = gnu.kawa.reflect.SlotGet.noClasses     // Catch:{ Exception -> 0x00f4 }
            r0 = r18
            java.lang.reflect.Method r10 = r3.getMethod(r9, r0)     // Catch:{ Exception -> 0x00f4 }
        L_0x00a5:
            r18 = 1
            r0 = r18
            java.lang.Class[] r15 = new java.lang.Class[r0]     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            r18 = 0
            java.lang.Class r19 = r10.getReturnType()     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            r15[r18] = r19     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            r0 = r16
            java.lang.reflect.Method r17 = r3.getMethod(r0, r15)     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            r18 = 1
            r0 = r18
            java.lang.Object[] r2 = new java.lang.Object[r0]     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            r18 = 0
            r19 = 0
            r19 = r15[r19]     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            r0 = r19
            r1 = r24
            java.lang.Object r19 = r13.coerceFromObject(r0, r1)     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            r2[r18] = r19     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            r0 = r17
            r1 = r22
            r0.invoke(r1, r2)     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            goto L_0x004e
        L_0x00d8:
            r4 = move-exception
            java.lang.Throwable r18 = r4.getTargetException()
            java.lang.RuntimeException r18 = gnu.mapping.WrappedException.wrapIfNeeded(r18)
            throw r18
        L_0x00e2:
            java.lang.String r18 = "set"
            r0 = r18
            java.lang.String r16 = gnu.expr.ClassExp.slotToMethodName(r0, r14)     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            goto L_0x006f
        L_0x00eb:
            java.lang.String r18 = "get"
            r0 = r18
            java.lang.String r9 = gnu.expr.ClassExp.slotToMethodName(r0, r14)     // Catch:{ Exception -> 0x00f4 }
            goto L_0x009d
        L_0x00f4:
            r8 = move-exception
            if (r11 == 0) goto L_0x011d
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            r18.<init>()     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            java.lang.String r19 = "is"
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            r19 = 3
            r0 = r16
            r1 = r19
            java.lang.String r19 = r0.substring(r1)     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            java.lang.String r9 = r18.toString()     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
        L_0x0114:
            java.lang.Class[] r18 = gnu.kawa.reflect.SlotGet.noClasses     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            r0 = r18
            java.lang.reflect.Method r10 = r3.getMethod(r9, r0)     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            goto L_0x00a5
        L_0x011d:
            java.lang.String r18 = "is"
            r0 = r18
            java.lang.String r9 = gnu.expr.ClassExp.slotToMethodName(r0, r14)     // Catch:{ InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e }
            goto L_0x0114
        L_0x0126:
            r4 = move-exception
            r12 = 1
        L_0x0128:
            if (r12 == 0) goto L_0x0145
            java.lang.RuntimeException r18 = new java.lang.RuntimeException
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            r19.<init>()
            java.lang.String r20 = "illegal access for field "
            java.lang.StringBuilder r19 = r19.append(r20)
            r0 = r19
            java.lang.StringBuilder r19 = r0.append(r14)
            java.lang.String r19 = r19.toString()
            r18.<init>(r19)
            throw r18
        L_0x0145:
            java.lang.RuntimeException r18 = new java.lang.RuntimeException
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            r19.<init>()
            java.lang.String r20 = "no such field "
            java.lang.StringBuilder r19 = r19.append(r20)
            r0 = r19
            java.lang.StringBuilder r19 = r0.append(r14)
            java.lang.String r20 = " in "
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r20 = r3.getName()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            r18.<init>(r19)
            throw r18
        L_0x016e:
            r18 = move-exception
            goto L_0x0128
        L_0x0170:
            r18 = move-exception
            goto L_0x0066
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.SlotSet.apply(boolean, java.lang.Object, java.lang.Object, java.lang.Object):void");
    }

    public Object apply3(Object obj, Object fname, Object value) {
        apply(this.isStatic, obj, fname, value);
        return this.returnSelf ? obj : Values.empty;
    }

    public static Member lookupMember(ObjectType clas, String name, ClassType caller) {
        Field field = clas.getField(Compilation.mangleNameIfNeeded(name), -1);
        if (field != null) {
            if (caller == null) {
                caller = Type.pointer_type;
            }
            if (caller.isAccessible(field, clas)) {
                return field;
            }
        }
        Method method = clas.getMethod(ClassExp.slotToMethodName("set", name), type1Array);
        if (method != null) {
            return method;
        }
        return field;
    }

    static void compileSet(Procedure thisProc, ObjectType ctype, Expression valArg, Object part, Compilation comp) {
        CodeAttr code = comp.getCode();
        Language language = comp.getLanguage();
        boolean isStatic2 = (thisProc instanceof SlotSet) && ((SlotSet) thisProc).isStatic;
        if (part instanceof Field) {
            Field field = (Field) part;
            boolean isStaticField = field.getStaticFlag();
            Type ftype = language.getLangTypeFor(field.getType());
            if (isStatic2 && !isStaticField) {
                comp.error('e', "cannot access non-static field `" + field.getName() + "' using `" + thisProc.getName() + '\'');
            }
            valArg.compile(comp, CheckedTarget.getInstance(ftype));
            if (isStaticField) {
                code.emitPutStatic(field);
            } else {
                code.emitPutField(field);
            }
        } else if (part instanceof Method) {
            Method method = (Method) part;
            boolean isStaticMethod = method.getStaticFlag();
            if (isStatic2 && !isStaticMethod) {
                comp.error('e', "cannot call non-static getter method `" + method.getName() + "' using `" + thisProc.getName() + '\'');
            }
            valArg.compile(comp, CheckedTarget.getInstance(language.getLangTypeFor(method.getParameterTypes()[0])));
            if (isStaticMethod) {
                code.emitInvokeStatic(method);
            } else {
                code.emitInvoke(method);
            }
            if (!method.getReturnType().isVoid()) {
                code.emitPop(1);
            }
        }
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        ClassType caller;
        String name;
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        if (nargs != 3) {
            comp.error('e', (nargs < 3 ? "too few" : "too many") + " arguments to `" + getName() + '\'');
            comp.compileConstant(null, target);
            return;
        }
        Expression arg0 = args[0];
        Expression arg1 = args[1];
        Expression expression = args[2];
        Type type = this.isStatic ? Scheme.exp2Type(arg0) : arg0.getType();
        Member part = null;
        if ((type instanceof ObjectType) && (arg1 instanceof QuoteExp)) {
            Object val1 = ((QuoteExp) arg1).getValue();
            ObjectType ctype = (ObjectType) type;
            if (comp.curClass != null) {
                caller = comp.curClass;
            } else {
                caller = comp.mainClass;
            }
            if ((val1 instanceof String) || (val1 instanceof FString) || (val1 instanceof Symbol)) {
                name = val1.toString();
                part = lookupMember(ctype, name, caller);
                if (part == null && type != Type.pointer_type && comp.warnUnknownMember()) {
                    comp.error('w', "no slot `" + name + "' in " + ctype.getName());
                }
            } else if (val1 instanceof Member) {
                part = (Member) val1;
                name = part.getName();
            } else {
                name = null;
            }
            if (part != null) {
                boolean isStaticField = (part.getModifiers() & 8) != 0;
                if (caller != null && !caller.isAccessible(part, ctype)) {
                    comp.error('e', "slot '" + name + "' in " + part.getDeclaringClass().getName() + " not accessible here");
                }
                args[0].compile(comp, isStaticField ? Target.Ignore : Target.pushValue(ctype));
                if (this.returnSelf) {
                    comp.getCode().emitDup(ctype.getImplementationType());
                }
                compileSet(this, ctype, args[2], part, comp);
                if (this.returnSelf) {
                    target.compileFromStack(comp, ctype);
                    return;
                } else {
                    comp.compileConstant(Values.empty, target);
                    return;
                }
            }
        }
        ApplyExp.compile(exp, comp, target);
    }
}
