package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.mapping.OutPort;
import java.util.Hashtable;
import java.util.Vector;

public class ClassExp extends LambdaExp {
    public static final int CLASS_SPECIFIED = 65536;
    public static final int HAS_SUBCLASS = 131072;
    public static final int INTERFACE_SPECIFIED = 32768;
    public static final int IS_ABSTRACT = 16384;
    public String classNameSpecifier;
    public LambdaExp clinitMethod;
    boolean explicitInit;
    public LambdaExp initMethod;
    ClassType instanceType;
    boolean partsDeclared;
    boolean simple;
    public int superClassIndex = -1;
    public Expression[] supers;

    public boolean isSimple() {
        return this.simple;
    }

    public void setSimple(boolean value) {
        this.simple = value;
    }

    public final boolean isAbstract() {
        return getFlag(16384);
    }

    public boolean isMakingClassPair() {
        return this.type != this.instanceType;
    }

    public Type getType() {
        return this.simple ? Compilation.typeClass : Compilation.typeClassType;
    }

    public ClassType getClassType() {
        return this.type;
    }

    public ClassExp() {
    }

    public ClassExp(boolean simple2) {
        this.simple = simple2;
        ClassType classType = new ClassType();
        this.type = classType;
        this.instanceType = classType;
    }

    /* access modifiers changed from: protected */
    public boolean mustCompile() {
        return true;
    }

    public void compile(Compilation comp, Target target) {
        if (!(target instanceof IgnoreTarget)) {
            compileMembers(comp);
            compilePushClass(comp, target);
        }
    }

    public void compilePushClass(Compilation comp, Target target) {
        ClassType typeType;
        int nargs;
        ClassType new_class = this.type;
        CodeAttr code = comp.getCode();
        comp.loadClassRef(new_class);
        boolean needsLink = getNeedsClosureEnv();
        if (!isSimple() || needsLink) {
            if (isMakingClassPair() || needsLink) {
                if (new_class == this.instanceType) {
                    code.emitDup((Type) this.instanceType);
                } else {
                    comp.loadClassRef(this.instanceType);
                }
                typeType = ClassType.make("gnu.expr.PairClassType");
                nargs = needsLink ? 3 : 2;
            } else {
                typeType = ClassType.make("gnu.bytecode.Type");
                nargs = 1;
            }
            Type[] argsClass = new Type[nargs];
            if (needsLink) {
                getOwningLambda().loadHeapFrame(comp);
                nargs--;
                argsClass[nargs] = Type.pointer_type;
            }
            ClassType typeClass = ClassType.make("java.lang.Class");
            while (true) {
                nargs--;
                if (nargs >= 0) {
                    argsClass[nargs] = typeClass;
                } else {
                    code.emitInvokeStatic(typeType.addMethod("make", argsClass, (Type) typeType, 9));
                    target.compileFromStack(comp, typeType);
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public ClassType getCompiledClassType(Compilation comp) {
        return this.type;
    }

    public void setTypes(Compilation comp) {
        ClassType[] interfaces;
        String name;
        String name2;
        int modifiers;
        int j;
        int nsupers = this.supers == null ? 0 : this.supers.length;
        ClassType[] superTypes = new ClassType[nsupers];
        ClassType superType = null;
        int i = 0;
        int j2 = 0;
        while (i < nsupers) {
            Type st = Language.getDefaultLanguage().getTypeFor(this.supers[i]);
            if (!(st instanceof ClassType)) {
                comp.setLine(this.supers[i]);
                comp.error('e', "invalid super type");
                j = j2;
            } else {
                ClassType t = (ClassType) st;
                try {
                    modifiers = t.getModifiers();
                } catch (RuntimeException e) {
                    modifiers = 0;
                    if (comp != null) {
                        comp.error('e', "unknown super-type " + t.getName());
                    }
                }
                if ((modifiers & 512) == 0) {
                    if (j2 < i) {
                        comp.error('e', "duplicate superclass for " + this);
                    }
                    superType = t;
                    this.superClassIndex = i;
                    j = j2;
                } else {
                    j = j2 + 1;
                    superTypes[j2] = t;
                }
            }
            i++;
            j2 = j;
        }
        if (!(superType == null || (this.flags & 32768) == 0)) {
            comp.error('e', "cannot be interface since has superclass");
        }
        if (!this.simple && superType == null && (this.flags & 65536) == 0 && (getFlag(131072) || (this.nameDecl != null && this.nameDecl.isPublic()))) {
            PairClassType ptype = new PairClassType();
            this.type = ptype;
            ptype.setInterface(true);
            ptype.instanceType = this.instanceType;
            ClassType[] interfaces2 = {this.type};
            this.instanceType.setSuper(Type.pointer_type);
            this.instanceType.setInterfaces(interfaces2);
        } else if (getFlag(32768)) {
            this.instanceType.setInterface(true);
        }
        ClassType classType = this.type;
        if (superType == null) {
            superType = Type.pointer_type;
        }
        classType.setSuper(superType);
        if (j2 == nsupers) {
            interfaces = superTypes;
        } else {
            interfaces = new ClassType[j2];
            System.arraycopy(superTypes, 0, interfaces, 0, j2);
        }
        this.type.setInterfaces(interfaces);
        if (this.type.getName() == null) {
            if (this.classNameSpecifier != null) {
                name = this.classNameSpecifier;
            } else {
                name = getName();
                if (name != null) {
                    int nlen = name.length();
                    if (nlen > 2 && name.charAt(0) == '<' && name.charAt(nlen - 1) == '>') {
                        name = name.substring(1, nlen - 1);
                    }
                }
            }
            if (name == null) {
                StringBuffer nbuf = new StringBuffer(100);
                comp.getModule().classFor(comp);
                nbuf.append(comp.mainClass.getName());
                nbuf.append('$');
                int len = nbuf.length();
                int i2 = 0;
                while (true) {
                    nbuf.append(i2);
                    name2 = nbuf.toString();
                    if (comp.findNamedClass(name2) == null) {
                        break;
                    }
                    nbuf.setLength(len);
                    i2++;
                }
            } else if (!isSimple() || (this instanceof ObjectExp)) {
                name2 = comp.generateClassName(name);
            } else {
                int start = 0;
                StringBuffer nbuf2 = new StringBuffer(100);
                while (true) {
                    int dot = name.indexOf(46, start);
                    if (dot < 0) {
                        break;
                    }
                    nbuf2.append(Compilation.mangleNameIfNeeded(name.substring(start, dot)));
                    start = dot + 1;
                    if (start < name.length()) {
                        nbuf2.append('.');
                    }
                }
                if (start == 0) {
                    String mainName = comp.mainClass == null ? null : comp.mainClass.getName();
                    int dot2 = mainName == null ? -1 : mainName.lastIndexOf(46);
                    if (dot2 > 0) {
                        nbuf2.append(mainName.substring(0, dot2 + 1));
                    } else if (comp.classPrefix != null) {
                        nbuf2.append(comp.classPrefix);
                    }
                } else if (start == 1 && start < name.length()) {
                    nbuf2.setLength(0);
                    nbuf2.append(comp.mainClass.getName());
                    nbuf2.append('$');
                }
                if (start < name.length()) {
                    nbuf2.append(Compilation.mangleNameIfNeeded(name.substring(start)));
                }
                name2 = nbuf2.toString();
            }
            this.type.setName(name2);
            comp.addClass(this.type);
            if (isMakingClassPair()) {
                this.instanceType.setName(this.type.getName() + "$class");
                comp.addClass(this.instanceType);
            }
        }
    }

    public void declareParts(Compilation comp) {
        if (!this.partsDeclared) {
            this.partsDeclared = true;
            Hashtable<String, Declaration> seenFields = new Hashtable<>();
            for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
                if (decl.getCanRead()) {
                    int flags = decl.getAccessFlags(1);
                    if (decl.getFlag(2048)) {
                        flags |= 8;
                    }
                    if (isMakingClassPair()) {
                        int flags2 = flags | 1024;
                        Type ftype = decl.getType().getImplementationType();
                        this.type.addMethod(slotToMethodName("get", decl.getName()), flags2, Type.typeArray0, ftype);
                        this.type.addMethod(slotToMethodName("set", decl.getName()), flags2, new Type[]{ftype}, (Type) Type.voidType);
                    } else {
                        String fname = Compilation.mangleNameIfNeeded(decl.getName());
                        decl.field = this.instanceType.addField(fname, decl.getType(), flags);
                        decl.setSimple(false);
                        Declaration old = (Declaration) seenFields.get(fname);
                        if (old != null) {
                            duplicateDeclarationError(old, decl, comp);
                        }
                        seenFields.put(fname, decl);
                    }
                }
            }
            for (LambdaExp child = this.firstChild; child != null; child = child.nextSibling) {
                if (child.isAbstract()) {
                    setFlag(16384);
                }
                if ("*init*".equals(child.getName())) {
                    this.explicitInit = true;
                    if (child.isAbstract()) {
                        comp.error('e', "*init* method cannot be abstract", child);
                    }
                    if (this.type instanceof PairClassType) {
                        comp.error('e', "'*init*' methods only supported for simple classes");
                    }
                }
                child.outer = this;
                if (!(child == this.initMethod || child == this.clinitMethod || child.nameDecl == null || child.nameDecl.getFlag(2048)) || !isMakingClassPair()) {
                    child.addMethodFor(this.type, comp, null);
                }
                if (isMakingClassPair()) {
                    child.addMethodFor(this.instanceType, comp, this.type);
                }
            }
            if (!this.explicitInit && !this.instanceType.isInterface()) {
                Compilation.getConstructor(this.instanceType, this);
            }
            if (isAbstract()) {
                this.instanceType.setModifiers(this.instanceType.getModifiers() | 1024);
            }
            if (this.nameDecl != null) {
                this.instanceType.setModifiers((this.instanceType.getModifiers() & -2) | this.nameDecl.getAccessFlags(1));
            }
        }
    }

    static void getImplMethods(ClassType interfaceType, String mname, Type[] paramTypes, Vector vec) {
        ClassType implType;
        if (interfaceType instanceof PairClassType) {
            implType = ((PairClassType) interfaceType).instanceType;
        } else if (interfaceType.isInterface()) {
            try {
                Class reflectClass = interfaceType.getReflectClass();
                if (reflectClass != null) {
                    implType = (ClassType) Type.make(Class.forName(interfaceType.getName() + "$class", false, reflectClass.getClassLoader()));
                } else {
                    return;
                }
            } catch (Throwable th) {
                return;
            }
        } else {
            return;
        }
        Type[] itypes = new Type[(paramTypes.length + 1)];
        itypes[0] = interfaceType;
        System.arraycopy(paramTypes, 0, itypes, 1, paramTypes.length);
        Method implMethod = implType.getDeclaredMethod(mname, itypes);
        if (implMethod != null) {
            int count = vec.size();
            if (count != 0) {
                if (vec.elementAt(count - 1).equals(implMethod)) {
                    return;
                }
            }
            vec.addElement(implMethod);
            return;
        }
        ClassType[] superInterfaces = interfaceType.getInterfaces();
        for (ClassType implMethods : superInterfaces) {
            getImplMethods(implMethods, mname, paramTypes, vec);
        }
    }

    private static void usedSuperClasses(ClassType clas, Compilation comp) {
        comp.usedClass(clas.getSuperclass());
        ClassType[] interfaces = clas.getInterfaces();
        if (interfaces != null) {
            int i = interfaces.length;
            while (true) {
                i--;
                if (i >= 0) {
                    comp.usedClass(interfaces[i]);
                } else {
                    return;
                }
            }
        }
    }

    /* JADX WARNING: type inference failed for: r0v3, types: [gnu.bytecode.Method] */
    /* JADX WARNING: type inference failed for: r38v0 */
    /* JADX WARNING: type inference failed for: r13v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.bytecode.ClassType compileMembers(gnu.expr.Compilation r49) {
        /*
            r48 = this;
            r0 = r49
            gnu.bytecode.ClassType r0 = r0.curClass
            r33 = r0
            r0 = r49
            gnu.bytecode.Method r0 = r0.method
            r38 = r0
            gnu.bytecode.ClassType r27 = r48.getCompiledClassType(r49)     // Catch:{ all -> 0x0272 }
            r0 = r27
            r1 = r49
            r1.curClass = r0     // Catch:{ all -> 0x0272 }
            gnu.expr.LambdaExp r29 = r48.outerLambda()     // Catch:{ all -> 0x0272 }
            r13 = 0
            r0 = r29
            boolean r0 = r0 instanceof gnu.expr.ClassExp     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 == 0) goto L_0x00ba
            r0 = r29
            gnu.bytecode.ClassType r13 = r0.type     // Catch:{ all -> 0x0272 }
        L_0x0027:
            if (r13 == 0) goto L_0x003b
            r0 = r27
            r0.setEnclosingMember(r13)     // Catch:{ all -> 0x0272 }
            boolean r0 = r13 instanceof gnu.bytecode.ClassType     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 == 0) goto L_0x003b
            gnu.bytecode.ClassType r13 = (gnu.bytecode.ClassType) r13     // Catch:{ all -> 0x0272 }
            r0 = r27
            r13.addMemberClass(r0)     // Catch:{ all -> 0x0272 }
        L_0x003b:
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            r1 = r27
            if (r0 == r1) goto L_0x0065
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.type     // Catch:{ all -> 0x0272 }
            r46 = r0
            r45.setEnclosingMember(r46)     // Catch:{ all -> 0x0272 }
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.type     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r46 = r0
            r45.addMemberClass(r46)     // Catch:{ all -> 0x0272 }
        L_0x0065:
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.type     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            r1 = r49
            usedSuperClasses(r0, r1)     // Catch:{ all -> 0x0272 }
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.type     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r46 = r0
            r0 = r45
            r1 = r46
            if (r0 == r1) goto L_0x0091
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            r1 = r49
            usedSuperClasses(r0, r1)     // Catch:{ all -> 0x0272 }
        L_0x0091:
            java.lang.String r15 = r48.getFileName()     // Catch:{ all -> 0x0272 }
            if (r15 == 0) goto L_0x009c
            r0 = r27
            r0.setSourceFile(r15)     // Catch:{ all -> 0x0272 }
        L_0x009c:
            r0 = r49
            gnu.expr.LambdaExp r0 = r0.curLambda     // Catch:{ all -> 0x0272 }
            r36 = r0
            r0 = r48
            r1 = r49
            r1.curLambda = r0     // Catch:{ all -> 0x0272 }
            r48.allocFrame(r49)     // Catch:{ all -> 0x0272 }
            r0 = r48
            gnu.expr.LambdaExp r10 = r0.firstChild     // Catch:{ all -> 0x0272 }
        L_0x00af:
            if (r10 == 0) goto L_0x029d
            boolean r45 = r10.isAbstract()     // Catch:{ all -> 0x0272 }
            if (r45 == 0) goto L_0x00e8
        L_0x00b7:
            gnu.expr.LambdaExp r10 = r10.nextSibling     // Catch:{ all -> 0x0272 }
            goto L_0x00af
        L_0x00ba:
            if (r29 == 0) goto L_0x00c8
            r0 = r29
            boolean r0 = r0 instanceof gnu.expr.ModuleExp     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 != 0) goto L_0x00c8
            r13 = r38
            goto L_0x0027
        L_0x00c8:
            r0 = r29
            boolean r0 = r0 instanceof gnu.expr.ModuleExp     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 == 0) goto L_0x0027
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.type     // Catch:{ all -> 0x0272 }
            r45 = r0
            java.lang.String r45 = r45.getName()     // Catch:{ all -> 0x0272 }
            r46 = 36
            int r45 = r45.indexOf(r46)     // Catch:{ all -> 0x0272 }
            if (r45 <= 0) goto L_0x0027
            r0 = r29
            gnu.bytecode.ClassType r13 = r0.type     // Catch:{ all -> 0x0272 }
            goto L_0x0027
        L_0x00e8:
            r0 = r49
            gnu.bytecode.Method r0 = r0.method     // Catch:{ all -> 0x0272 }
            r40 = r0
            r0 = r49
            gnu.expr.LambdaExp r0 = r0.curLambda     // Catch:{ all -> 0x0272 }
            r39 = r0
            java.lang.String r35 = r49.getFileName()     // Catch:{ all -> 0x0272 }
            int r37 = r49.getLineNumber()     // Catch:{ all -> 0x0272 }
            int r34 = r49.getColumnNumber()     // Catch:{ all -> 0x0272 }
            r0 = r49
            r0.setLine(r10)     // Catch:{ all -> 0x0272 }
            gnu.bytecode.Method r45 = r10.getMainMethod()     // Catch:{ all -> 0x0272 }
            r0 = r45
            r1 = r49
            r1.method = r0     // Catch:{ all -> 0x0272 }
            gnu.expr.Declaration r11 = r10.nameDecl     // Catch:{ all -> 0x0272 }
            if (r11 == 0) goto L_0x011d
            r46 = 2048(0x800, double:1.0118E-320)
            r0 = r46
            boolean r45 = r11.getFlag(r0)     // Catch:{ all -> 0x0272 }
            if (r45 != 0) goto L_0x0128
        L_0x011d:
            r0 = r49
            gnu.bytecode.ClassType r0 = r0.curClass     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            r10.declareThis(r0)     // Catch:{ all -> 0x0272 }
        L_0x0128:
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            r1 = r49
            r1.curClass = r0     // Catch:{ all -> 0x0272 }
            r0 = r49
            r0.curLambda = r10     // Catch:{ all -> 0x0272 }
            r0 = r49
            gnu.bytecode.Method r0 = r0.method     // Catch:{ all -> 0x0272 }
            r45 = r0
            r45.initCode()     // Catch:{ all -> 0x0272 }
            r0 = r49
            r10.allocChildClasses(r0)     // Catch:{ all -> 0x0272 }
            r0 = r49
            r10.allocParameters(r0)     // Catch:{ all -> 0x0272 }
            java.lang.String r45 = "*init*"
            java.lang.String r46 = r10.getName()     // Catch:{ all -> 0x0272 }
            boolean r45 = r45.equals(r46)     // Catch:{ all -> 0x0272 }
            if (r45 == 0) goto L_0x0292
            gnu.bytecode.CodeAttr r12 = r49.getCode()     // Catch:{ all -> 0x0272 }
            r0 = r48
            gnu.bytecode.Field r0 = r0.staticLinkField     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 == 0) goto L_0x0180
            r12.emitPushThis()     // Catch:{ all -> 0x0272 }
            gnu.bytecode.Scope r45 = r12.getCurrentScope()     // Catch:{ all -> 0x0272 }
            r46 = 1
            gnu.bytecode.Variable r45 = r45.getVariable(r46)     // Catch:{ all -> 0x0272 }
            r0 = r45
            r12.emitLoad(r0)     // Catch:{ all -> 0x0272 }
            r0 = r48
            gnu.bytecode.Field r0 = r0.staticLinkField     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            r12.emitPutField(r0)     // Catch:{ all -> 0x0272 }
        L_0x0180:
            gnu.expr.Expression r7 = r10.body     // Catch:{ all -> 0x0272 }
        L_0x0182:
            boolean r0 = r7 instanceof gnu.expr.BeginExp     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 == 0) goto L_0x019d
            r0 = r7
            gnu.expr.BeginExp r0 = (gnu.expr.BeginExp) r0     // Catch:{ all -> 0x0272 }
            r6 = r0
            int r0 = r6.length     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 != 0) goto L_0x0194
            r7 = 0
            goto L_0x0182
        L_0x0194:
            gnu.expr.Expression[] r0 = r6.exps     // Catch:{ all -> 0x0272 }
            r45 = r0
            r46 = 0
            r7 = r45[r46]     // Catch:{ all -> 0x0272 }
            goto L_0x0182
        L_0x019d:
            r8 = 0
            boolean r0 = r7 instanceof gnu.expr.ApplyExp     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 == 0) goto L_0x01e9
            r0 = r7
            gnu.expr.ApplyExp r0 = (gnu.expr.ApplyExp) r0     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            gnu.expr.Expression r14 = r0.func     // Catch:{ all -> 0x0272 }
            boolean r0 = r14 instanceof gnu.expr.QuoteExp     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 == 0) goto L_0x01e9
            gnu.expr.QuoteExp r14 = (gnu.expr.QuoteExp) r14     // Catch:{ all -> 0x0272 }
            java.lang.Object r42 = r14.getValue()     // Catch:{ all -> 0x0272 }
            r0 = r42
            boolean r0 = r0 instanceof gnu.expr.PrimProcedure     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 == 0) goto L_0x01e9
            r0 = r42
            gnu.expr.PrimProcedure r0 = (gnu.expr.PrimProcedure) r0     // Catch:{ all -> 0x0272 }
            r30 = r0
            boolean r45 = r30.isSpecial()     // Catch:{ all -> 0x0272 }
            if (r45 == 0) goto L_0x01e9
            java.lang.String r45 = "<init>"
            r0 = r30
            gnu.bytecode.Method r0 = r0.method     // Catch:{ all -> 0x0272 }
            r46 = r0
            java.lang.String r46 = r46.getName()     // Catch:{ all -> 0x0272 }
            boolean r45 = r45.equals(r46)     // Catch:{ all -> 0x0272 }
            if (r45 == 0) goto L_0x01e9
            r0 = r30
            gnu.bytecode.Method r0 = r0.method     // Catch:{ all -> 0x0272 }
            r45 = r0
            gnu.bytecode.ClassType r8 = r45.getDeclaringClass()     // Catch:{ all -> 0x0272 }
        L_0x01e9:
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            gnu.bytecode.ClassType r41 = r45.getSuperclass()     // Catch:{ all -> 0x0272 }
            if (r8 == 0) goto L_0x0280
            gnu.expr.Target r45 = gnu.expr.Target.Ignore     // Catch:{ all -> 0x0272 }
            r0 = r49
            r1 = r45
            r7.compileWithPosition(r0, r1)     // Catch:{ all -> 0x0272 }
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            if (r8 == r0) goto L_0x0219
            r0 = r41
            if (r8 == r0) goto L_0x0219
            r45 = 101(0x65, float:1.42E-43)
            java.lang.String r46 = "call to <init> for not this or super class"
            r0 = r49
            r1 = r45
            r2 = r46
            r0.error(r1, r2)     // Catch:{ all -> 0x0272 }
        L_0x0219:
            r0 = r49
            r10.enterFunction(r0)     // Catch:{ all -> 0x0272 }
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            if (r8 == r0) goto L_0x023c
            gnu.bytecode.ClassType r45 = r48.getCompiledClassType(r49)     // Catch:{ all -> 0x0272 }
            java.util.Vector r46 = new java.util.Vector     // Catch:{ all -> 0x0272 }
            r47 = 10
            r46.<init>(r47)     // Catch:{ all -> 0x0272 }
            r0 = r49
            r1 = r45
            r2 = r46
            r0.callInitMethods(r1, r2)     // Catch:{ all -> 0x0272 }
        L_0x023c:
            if (r8 == 0) goto L_0x028c
            gnu.expr.Expression r0 = r10.body     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            r1 = r49
            gnu.expr.Expression.compileButFirst(r0, r1)     // Catch:{ all -> 0x0272 }
        L_0x0249:
            r0 = r49
            r10.compileEnd(r0)     // Catch:{ all -> 0x0272 }
            r0 = r49
            r10.generateApplyMethods(r0)     // Catch:{ all -> 0x0272 }
            r0 = r40
            r1 = r49
            r1.method = r0     // Catch:{ all -> 0x0272 }
            r0 = r27
            r1 = r49
            r1.curClass = r0     // Catch:{ all -> 0x0272 }
            r0 = r39
            r1 = r49
            r1.curLambda = r0     // Catch:{ all -> 0x0272 }
            r0 = r49
            r1 = r35
            r2 = r37
            r3 = r34
            r0.setLine(r1, r2, r3)     // Catch:{ all -> 0x0272 }
            goto L_0x00b7
        L_0x0272:
            r45 = move-exception
            r0 = r33
            r1 = r49
            r1.curClass = r0
            r0 = r38
            r1 = r49
            r1.method = r0
            throw r45
        L_0x0280:
            if (r41 == 0) goto L_0x0219
            r0 = r41
            r1 = r49
            r2 = r48
            invokeDefaultSuperConstructor(r0, r1, r2)     // Catch:{ all -> 0x0272 }
            goto L_0x0219
        L_0x028c:
            r0 = r49
            r10.compileBody(r0)     // Catch:{ all -> 0x0272 }
            goto L_0x0249
        L_0x0292:
            r0 = r49
            r10.enterFunction(r0)     // Catch:{ all -> 0x0272 }
            r0 = r49
            r10.compileBody(r0)     // Catch:{ all -> 0x0272 }
            goto L_0x0249
        L_0x029d:
            r0 = r48
            boolean r0 = r0.explicitInit     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 != 0) goto L_0x02fb
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            boolean r45 = r45.isInterface()     // Catch:{ all -> 0x0272 }
            if (r45 != 0) goto L_0x02fb
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r49
            r1 = r45
            r2 = r48
            r0.generateConstructor(r1, r2)     // Catch:{ all -> 0x0272 }
        L_0x02c0:
            boolean r45 = r48.isAbstract()     // Catch:{ all -> 0x0272 }
            if (r45 == 0) goto L_0x0315
            r23 = 0
            r28 = 0
        L_0x02ca:
            r19 = 0
        L_0x02cc:
            r0 = r19
            r1 = r28
            if (r0 >= r1) goto L_0x04b9
            r22 = r23[r19]     // Catch:{ all -> 0x0272 }
            java.lang.String r25 = r22.getName()     // Catch:{ all -> 0x0272 }
            gnu.bytecode.Type[] r31 = r22.getParameterTypes()     // Catch:{ all -> 0x0272 }
            gnu.bytecode.Type r32 = r22.getReturnType()     // Catch:{ all -> 0x0272 }
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            r1 = r25
            r2 = r31
            gnu.bytecode.Method r24 = r0.getMethod(r1, r2)     // Catch:{ all -> 0x0272 }
            if (r24 == 0) goto L_0x0325
            boolean r45 = r24.isAbstract()     // Catch:{ all -> 0x0272 }
            if (r45 != 0) goto L_0x0325
        L_0x02f8:
            int r19 = r19 + 1
            goto L_0x02cc
        L_0x02fb:
            r0 = r48
            gnu.expr.Initializer r0 = r0.initChain     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 == 0) goto L_0x02c0
            r0 = r48
            gnu.expr.Initializer r0 = r0.initChain     // Catch:{ all -> 0x0272 }
            r45 = r0
            java.lang.String r46 = "unimplemented: explicit constructor cannot initialize "
            r0 = r45
            r1 = r46
            r2 = r49
            r0.reportError(r1, r2)     // Catch:{ all -> 0x0272 }
            goto L_0x02c0
        L_0x0315:
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.type     // Catch:{ all -> 0x0272 }
            r45 = r0
            gnu.bytecode.Method[] r23 = r45.getAbstractMethods()     // Catch:{ all -> 0x0272 }
            r0 = r23
            int r0 = r0.length     // Catch:{ all -> 0x0272 }
            r28 = r0
            goto L_0x02ca
        L_0x0325:
            int r45 = r25.length()     // Catch:{ all -> 0x0272 }
            r46 = 3
            r0 = r45
            r1 = r46
            if (r0 <= r1) goto L_0x0426
            r45 = 2
            r0 = r25
            r1 = r45
            char r45 = r0.charAt(r1)     // Catch:{ all -> 0x0272 }
            r46 = 116(0x74, float:1.63E-43)
            r0 = r45
            r1 = r46
            if (r0 != r1) goto L_0x0426
            r45 = 1
            r0 = r25
            r1 = r45
            char r45 = r0.charAt(r1)     // Catch:{ all -> 0x0272 }
            r46 = 101(0x65, float:1.42E-43)
            r0 = r45
            r1 = r46
            if (r0 != r1) goto L_0x0426
            r45 = 0
            r0 = r25
            r1 = r45
            char r9 = r0.charAt(r1)     // Catch:{ all -> 0x0272 }
            r45 = 103(0x67, float:1.44E-43)
            r0 = r45
            if (r9 == r0) goto L_0x036b
            r45 = 115(0x73, float:1.61E-43)
            r0 = r45
            if (r9 != r0) goto L_0x0426
        L_0x036b:
            r45 = 115(0x73, float:1.61E-43)
            r0 = r45
            if (r9 != r0) goto L_0x0402
            boolean r45 = r32.isVoid()     // Catch:{ all -> 0x0272 }
            if (r45 == 0) goto L_0x0402
            r0 = r31
            int r0 = r0.length     // Catch:{ all -> 0x0272 }
            r45 = r0
            r46 = 1
            r0 = r45
            r1 = r46
            if (r0 != r1) goto L_0x0402
            r45 = 0
            r18 = r31[r45]     // Catch:{ all -> 0x0272 }
        L_0x0388:
            java.lang.StringBuilder r45 = new java.lang.StringBuilder     // Catch:{ all -> 0x0272 }
            r45.<init>()     // Catch:{ all -> 0x0272 }
            r46 = 3
            r0 = r25
            r1 = r46
            char r46 = r0.charAt(r1)     // Catch:{ all -> 0x0272 }
            char r46 = java.lang.Character.toLowerCase(r46)     // Catch:{ all -> 0x0272 }
            java.lang.StringBuilder r45 = r45.append(r46)     // Catch:{ all -> 0x0272 }
            r46 = 4
            r0 = r25
            r1 = r46
            java.lang.String r46 = r0.substring(r1)     // Catch:{ all -> 0x0272 }
            java.lang.StringBuilder r45 = r45.append(r46)     // Catch:{ all -> 0x0272 }
            java.lang.String r17 = r45.toString()     // Catch:{ all -> 0x0272 }
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            r1 = r17
            gnu.bytecode.Field r16 = r0.getField(r1)     // Catch:{ all -> 0x0272 }
            if (r16 != 0) goto L_0x03d5
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r46 = 1
            r0 = r45
            r1 = r17
            r2 = r18
            r3 = r46
            gnu.bytecode.Field r16 = r0.addField(r1, r2, r3)     // Catch:{ all -> 0x0272 }
        L_0x03d5:
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r46 = 1
            r0 = r45
            r1 = r25
            r2 = r46
            r3 = r31
            r4 = r32
            gnu.bytecode.Method r21 = r0.addMethod(r1, r2, r3, r4)     // Catch:{ all -> 0x0272 }
            gnu.bytecode.CodeAttr r12 = r21.startCode()     // Catch:{ all -> 0x0272 }
            r12.emitPushThis()     // Catch:{ all -> 0x0272 }
            r45 = 103(0x67, float:1.44E-43)
            r0 = r45
            if (r9 != r0) goto L_0x0413
            r0 = r16
            r12.emitGetField(r0)     // Catch:{ all -> 0x0272 }
        L_0x03fd:
            r12.emitReturn()     // Catch:{ all -> 0x0272 }
            goto L_0x02f8
        L_0x0402:
            r45 = 103(0x67, float:1.44E-43)
            r0 = r45
            if (r9 != r0) goto L_0x02f8
            r0 = r31
            int r0 = r0.length     // Catch:{ all -> 0x0272 }
            r45 = r0
            if (r45 != 0) goto L_0x02f8
            r18 = r32
            goto L_0x0388
        L_0x0413:
            r45 = 1
            r0 = r45
            gnu.bytecode.Variable r45 = r12.getArg(r0)     // Catch:{ all -> 0x0272 }
            r0 = r45
            r12.emitLoad(r0)     // Catch:{ all -> 0x0272 }
            r0 = r16
            r12.emitPutField(r0)     // Catch:{ all -> 0x0272 }
            goto L_0x03fd
        L_0x0426:
            java.util.Vector r44 = new java.util.Vector     // Catch:{ all -> 0x0272 }
            r44.<init>()     // Catch:{ all -> 0x0272 }
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.type     // Catch:{ all -> 0x0272 }
            r45 = r0
            r0 = r45
            r1 = r25
            r2 = r31
            r3 = r44
            getImplMethods(r0, r1, r2, r3)     // Catch:{ all -> 0x0272 }
            int r45 = r44.size()     // Catch:{ all -> 0x0272 }
            r46 = 1
            r0 = r45
            r1 = r46
            if (r0 == r1) goto L_0x0479
            int r45 = r44.size()     // Catch:{ all -> 0x0272 }
            if (r45 != 0) goto L_0x0476
            java.lang.String r26 = "missing implementation for "
        L_0x0450:
            r45 = 101(0x65, float:1.42E-43)
            java.lang.StringBuilder r46 = new java.lang.StringBuilder     // Catch:{ all -> 0x0272 }
            r46.<init>()     // Catch:{ all -> 0x0272 }
            r0 = r46
            r1 = r26
            java.lang.StringBuilder r46 = r0.append(r1)     // Catch:{ all -> 0x0272 }
            r0 = r46
            r1 = r22
            java.lang.StringBuilder r46 = r0.append(r1)     // Catch:{ all -> 0x0272 }
            java.lang.String r46 = r46.toString()     // Catch:{ all -> 0x0272 }
            r0 = r49
            r1 = r45
            r2 = r46
            r0.error(r1, r2)     // Catch:{ all -> 0x0272 }
            goto L_0x02f8
        L_0x0476:
            java.lang.String r26 = "ambiguous implementation for "
            goto L_0x0450
        L_0x0479:
            r0 = r48
            gnu.bytecode.ClassType r0 = r0.instanceType     // Catch:{ all -> 0x0272 }
            r45 = r0
            r46 = 1
            r0 = r45
            r1 = r25
            r2 = r46
            r3 = r31
            r4 = r32
            gnu.bytecode.Method r21 = r0.addMethod(r1, r2, r3, r4)     // Catch:{ all -> 0x0272 }
            gnu.bytecode.CodeAttr r12 = r21.startCode()     // Catch:{ all -> 0x0272 }
            gnu.bytecode.Scope r45 = r12.getCurrentScope()     // Catch:{ all -> 0x0272 }
            gnu.bytecode.Variable r43 = r45.firstVar()     // Catch:{ all -> 0x0272 }
        L_0x049b:
            if (r43 == 0) goto L_0x04a7
            r0 = r43
            r12.emitLoad(r0)     // Catch:{ all -> 0x0272 }
            gnu.bytecode.Variable r43 = r43.nextVar()     // Catch:{ all -> 0x0272 }
            goto L_0x049b
        L_0x04a7:
            r45 = 0
            java.lang.Object r20 = r44.elementAt(r45)     // Catch:{ all -> 0x0272 }
            gnu.bytecode.Method r20 = (gnu.bytecode.Method) r20     // Catch:{ all -> 0x0272 }
            r0 = r20
            r12.emitInvokeStatic(r0)     // Catch:{ all -> 0x0272 }
            r12.emitReturn()     // Catch:{ all -> 0x0272 }
            goto L_0x02f8
        L_0x04b9:
            r48.generateApplyMethods(r49)     // Catch:{ all -> 0x0272 }
            r0 = r36
            r1 = r49
            r1.curLambda = r0     // Catch:{ all -> 0x0272 }
            r0 = r33
            r1 = r49
            r1.curClass = r0
            r0 = r38
            r1 = r49
            r1.method = r0
            return r27
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ClassExp.compileMembers(gnu.expr.Compilation):gnu.bytecode.ClassType");
    }

    /* access modifiers changed from: protected */
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        Compilation comp = visitor.getCompilation();
        if (comp == null) {
            return visitor.visitClassExp(this, d);
        }
        ClassType saveClass = comp.curClass;
        try {
            comp.curClass = this.type;
            return visitor.visitClassExp(this, d);
        } finally {
            comp.curClass = saveClass;
        }
    }

    /* access modifiers changed from: protected */
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        LambdaExp save = visitor.currentLambda;
        visitor.currentLambda = this;
        this.supers = visitor.visitExps(this.supers, this.supers.length, d);
        try {
            for (LambdaExp child = this.firstChild; child != null && visitor.exitValue == null; child = child.nextSibling) {
                if (this.instanceType != null) {
                    Declaration firstParam = child.firstDecl();
                    if (firstParam != null && firstParam.isThisParameter()) {
                        firstParam.setType(this.type);
                    }
                }
                visitor.visitLambdaExp(child, d);
            }
        } finally {
            visitor.currentLambda = save;
        }
    }

    static void loadSuperStaticLink(Expression superExp, ClassType superClass, Compilation comp) {
        CodeAttr code = comp.getCode();
        superExp.compile(comp, Target.pushValue(Compilation.typeClassType));
        code.emitInvokeStatic(ClassType.make("gnu.expr.PairClassType").getDeclaredMethod("extractStaticLink", 1));
        code.emitCheckcast(superClass.getOuterLinkType());
    }

    static void invokeDefaultSuperConstructor(ClassType superClass, Compilation comp, LambdaExp lexp) {
        CodeAttr code = comp.getCode();
        Method superConstructor = superClass.getDeclaredMethod("<init>", 0);
        if (superConstructor == null) {
            comp.error('e', "super class does not have a default constructor");
            return;
        }
        code.emitPushThis();
        if (superClass.hasOuterLink() && (lexp instanceof ClassExp)) {
            ClassExp clExp = (ClassExp) lexp;
            loadSuperStaticLink(clExp.supers[clExp.superClassIndex], superClass, comp);
        }
        code.emitInvokeSpecial(superConstructor);
    }

    public void print(OutPort out) {
        out.startLogicalBlock("(" + getExpClassName() + "/", ")", 2);
        Object name = getSymbol();
        if (name != null) {
            out.print(name);
            out.print('/');
        }
        out.print(this.f53id);
        out.print("/fl:");
        out.print(Integer.toHexString(this.flags));
        if (this.supers.length > 0) {
            out.writeSpaceFill();
            out.startLogicalBlock("supers:", "", 2);
            for (Expression print : this.supers) {
                print.print(out);
                out.writeSpaceFill();
            }
            out.endLogicalBlock("");
        }
        out.print('(');
        int i = 0;
        if (this.keywords != null) {
            int length = this.keywords.length;
        }
        for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (i > 0) {
                out.print(' ');
            }
            decl.printInfo(out);
            i++;
        }
        out.print(") ");
        for (LambdaExp child = this.firstChild; child != null; child = child.nextSibling) {
            out.writeBreakLinear();
            child.print(out);
        }
        if (this.body != null) {
            out.writeBreakLinear();
            this.body.print(out);
        }
        out.endLogicalBlock(")");
    }

    public Field compileSetField(Compilation comp) {
        return new ClassInitializer(this, comp).field;
    }

    public static String slotToMethodName(String prefix, String sname) {
        if (!Compilation.isValidJavaName(sname)) {
            sname = Compilation.mangleName(sname, false);
        }
        int slen = sname.length();
        StringBuffer sbuf = new StringBuffer(slen + 3);
        sbuf.append(prefix);
        if (slen > 0) {
            sbuf.append(Character.toTitleCase(sname.charAt(0)));
            sbuf.append(sname.substring(1));
        }
        return sbuf.toString();
    }

    public Declaration addMethod(LambdaExp lexp, Object mname) {
        Declaration mdecl = addDeclaration(mname, Compilation.typeProcedure);
        lexp.outer = this;
        lexp.setClassMethod(true);
        mdecl.noteValue(lexp);
        mdecl.setFlag(1048576);
        mdecl.setProcedureDecl(true);
        lexp.setSymbol(mname);
        return mdecl;
    }
}
