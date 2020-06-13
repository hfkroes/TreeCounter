package gnu.xquery.util;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.Target;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.OccurrenceType;
import gnu.kawa.xml.ChildAxis;
import gnu.kawa.xml.CoerceNodes;
import gnu.kawa.xml.DescendantAxis;
import gnu.kawa.xml.DescendantOrSelfAxis;
import gnu.kawa.xml.NodeSetType;
import gnu.kawa.xml.NodeType;
import gnu.kawa.xml.XDataType;
import gnu.mapping.Procedure;
import gnu.math.IntNum;
import gnu.xquery.lang.XQuery;

public class CompileMisc {
    static final Method castMethod = typeXDataType.getDeclaredMethod("cast", 1);
    static final Method castableMethod = typeXDataType.getDeclaredMethod("castable", 1);
    static final ClassType typeTuples = ClassType.make("gnu.xquery.util.OrderedTuples");
    static final ClassType typeXDataType = ClassType.make("gnu.kawa.xml.XDataType");

    public static Expression validateCompare(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression folded = exp.inlineIfConstant(proc, visitor);
        if (folded != exp) {
            return folded;
        }
        Compare cproc = (Compare) proc;
        if ((cproc.flags & 32) == 0) {
            exp = new ApplyExp(ClassType.make("gnu.xquery.util.Compare").getDeclaredMethod("apply", 4), new QuoteExp(IntNum.make(cproc.flags)), exp.getArg(0), exp.getArg(1), QuoteExp.nullExp);
        }
        if (exp.getTypeRaw() == null) {
            exp.setType(XDataType.booleanType);
        }
        return exp;
    }

    public static Expression validateBooleanValue(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            Expression arg = args[0];
            Type type = arg.getType();
            if (type == XDataType.booleanType) {
                return arg;
            }
            if (type == null) {
                exp.setType(XDataType.booleanType);
            }
            if (arg instanceof QuoteExp) {
                try {
                    return BooleanValue.booleanValue(((QuoteExp) arg).getValue()) ? XQuery.trueExp : XQuery.falseExp;
                } catch (Throwable th) {
                    String message = "cannot convert to a boolean";
                    visitor.getMessages().error('e', message);
                    return new ErrorExp(message);
                }
            }
        }
        return exp;
    }

    public static Expression validateArithOp(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        return exp;
    }

    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r14v0 */
    /* JADX WARNING: type inference failed for: r21v0, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r21v1 */
    /* JADX WARNING: type inference failed for: r21v2 */
    /* JADX WARNING: type inference failed for: r2v1, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r0v19, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r18v0 */
    /* JADX WARNING: type inference failed for: r18v1 */
    /* JADX WARNING: type inference failed for: r18v2 */
    /* JADX WARNING: type inference failed for: r1v8, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r0v56, types: [gnu.expr.Expression] */
    /* JADX WARNING: type inference failed for: r18v3 */
    /* JADX WARNING: type inference failed for: r18v4 */
    /* JADX WARNING: type inference failed for: r21v3 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.expr.Expression validateApplyValuesFilter(gnu.expr.ApplyExp r32, gnu.expr.InlineCalls r33, gnu.bytecode.Type r34, gnu.mapping.Procedure r35) {
        /*
            r26 = r35
            gnu.xquery.util.ValuesFilter r26 = (gnu.xquery.util.ValuesFilter) r26
            r32.visitArgs(r33)
            gnu.expr.Expression[] r4 = r32.getArgs()
            r27 = 1
            r7 = r4[r27]
            boolean r0 = r7 instanceof gnu.expr.LambdaExp
            r27 = r0
            if (r27 == 0) goto L_0x0030
            r14 = r7
            gnu.expr.LambdaExp r14 = (gnu.expr.LambdaExp) r14
            int r0 = r14.min_args
            r27 = r0
            r28 = 3
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x0030
            int r0 = r14.max_args
            r27 = r0
            r28 = 3
            r0 = r27
            r1 = r28
            if (r0 == r1) goto L_0x0031
        L_0x0030:
            return r32
        L_0x0031:
            r27 = 0
            r27 = r4[r27]
            gnu.bytecode.Type r27 = r27.getType()
            r0 = r32
            r1 = r27
            r0.setType(r1)
            gnu.expr.Compilation r15 = r33.getCompilation()
            gnu.expr.Declaration r6 = r14.firstDecl()
            gnu.expr.Declaration r16 = r6.nextDecl()
            gnu.expr.Declaration r10 = r16.nextDecl()
            r27 = 1
            r0 = r27
            r14.setInlineOnly(r0)
            r0 = r32
            r14.returnContinuation = r0
            gnu.expr.LambdaExp r27 = r33.getCurrentLambda()
            r0 = r27
            r14.inlineHome = r0
            r0 = r16
            r14.remove(r0, r10)
            r27 = 2
            r0 = r27
            r14.min_args = r0
            r27 = 2
            r0 = r27
            r14.max_args = r0
            boolean r27 = r10.getCanRead()
            if (r27 != 0) goto L_0x0088
            r0 = r26
            char r0 = r0.kind
            r27 = r0
            r28 = 82
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x0030
        L_0x0088:
            r15.letStart()
            r27 = 0
            r21 = r4[r27]
            r0 = r26
            char r0 = r0.kind
            r27 = r0
            r28 = 80
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x0218
            gnu.bytecode.Type r23 = r21.getType()
            gnu.bytecode.ClassType r27 = gnu.expr.Compilation.typeValues
            java.lang.String r28 = "countValues"
            r29 = 1
            gnu.bytecode.Method r25 = r27.getDeclaredMethod(r28, r29)
        L_0x00ab:
            java.lang.String r27 = "sequence"
            r0 = r27
            r1 = r23
            r2 = r21
            gnu.expr.Declaration r24 = r15.letVariable(r0, r1, r2)
            r15.letEnter()
            gnu.expr.Expression r0 = r14.body
            r18 = r0
            gnu.expr.Expression r0 = r14.body
            r27 = r0
            gnu.bytecode.Type r20 = r27.getType()
            gnu.kawa.xml.XDataType r27 = gnu.kawa.xml.XDataType.booleanType
            r0 = r20
            r1 = r27
            if (r0 == r1) goto L_0x00f6
            gnu.expr.ApplyExp r19 = new gnu.expr.ApplyExp
            gnu.bytecode.Method r27 = gnu.xquery.util.ValuesFilter.matchesMethod
            r28 = 2
            r0 = r28
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r28 = r0
            r29 = 0
            r28[r29] = r18
            r29 = 1
            gnu.expr.ReferenceExp r30 = new gnu.expr.ReferenceExp
            r0 = r30
            r1 = r16
            r0.<init>(r1)
            r28[r29] = r30
            r0 = r19
            r1 = r27
            r2 = r28
            r0.<init>(r1, r2)
            r18 = r19
        L_0x00f6:
            r0 = r26
            char r0 = r0.kind
            r27 = r0
            r28 = 82
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x0185
            gnu.expr.Declaration r17 = new gnu.expr.Declaration
            r27 = 0
            gnu.bytecode.PrimType r28 = gnu.bytecode.Type.intType
            r0 = r17
            r1 = r27
            r2 = r28
            r0.<init>(r1, r2)
            gnu.expr.ApplyExp r8 = new gnu.expr.ApplyExp
            gnu.kawa.functions.AddOp r27 = gnu.kawa.functions.AddOp.$Mn
            r28 = 2
            r0 = r28
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r28 = r0
            r29 = 0
            gnu.expr.ReferenceExp r30 = new gnu.expr.ReferenceExp
            r0 = r30
            r0.<init>(r10)
            r28[r29] = r30
            r29 = 1
            gnu.expr.ReferenceExp r30 = new gnu.expr.ReferenceExp
            r0 = r30
            r1 = r17
            r0.<init>(r1)
            r28[r29] = r30
            r0 = r27
            r1 = r28
            r8.<init>(r0, r1)
            gnu.expr.ApplyExp r9 = new gnu.expr.ApplyExp
            gnu.kawa.functions.AddOp r27 = gnu.kawa.functions.AddOp.$Pl
            r28 = 2
            r0 = r28
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r28 = r0
            r29 = 0
            r28[r29] = r8
            r29 = 1
            gnu.expr.QuoteExp r30 = new gnu.expr.QuoteExp
            gnu.math.IntNum r31 = gnu.math.IntNum.one()
            r30.<init>(r31)
            r28[r29] = r30
            r0 = r27
            r1 = r28
            r9.<init>(r0, r1)
            gnu.expr.LetExp r12 = new gnu.expr.LetExp
            r27 = 1
            r0 = r27
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r27 = r0
            r28 = 0
            r27[r28] = r9
            r0 = r27
            r12.<init>(r0)
            r0 = r17
            r14.replaceFollowing(r6, r0)
            r0 = r16
            r12.add(r0)
            r0 = r18
            r12.body = r0
            r18 = r12
        L_0x0185:
            gnu.expr.IfExp r19 = new gnu.expr.IfExp
            gnu.expr.ReferenceExp r27 = new gnu.expr.ReferenceExp
            r0 = r27
            r0.<init>(r6)
            gnu.expr.QuoteExp r28 = gnu.expr.QuoteExp.voidExp
            r0 = r19
            r1 = r18
            r2 = r27
            r3 = r28
            r0.<init>(r1, r2, r3)
            r0 = r19
            r14.body = r0
            gnu.expr.ApplyExp r5 = new gnu.expr.ApplyExp
            gnu.kawa.functions.ValuesMap r27 = gnu.kawa.functions.ValuesMap.valuesMapWithPos
            r28 = 2
            r0 = r28
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r28 = r0
            r29 = 0
            r28[r29] = r14
            r29 = 1
            gnu.expr.ReferenceExp r30 = new gnu.expr.ReferenceExp
            r0 = r30
            r1 = r24
            r0.<init>(r1)
            r28[r29] = r30
            r0 = r27
            r1 = r28
            r5.<init>(r0, r1)
            gnu.bytecode.Type r27 = r6.getType()
            r0 = r27
            r5.setType(r0)
            r14.returnContinuation = r5
            gnu.expr.ApplyExp r11 = new gnu.expr.ApplyExp
            r27 = 1
            r0 = r27
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r27 = r0
            r28 = 0
            gnu.expr.ReferenceExp r29 = new gnu.expr.ReferenceExp
            r0 = r29
            r1 = r24
            r0.<init>(r1)
            r27[r28] = r29
            r0 = r25
            r1 = r27
            r11.<init>(r0, r1)
            gnu.expr.LetExp r13 = new gnu.expr.LetExp
            r27 = 1
            r0 = r27
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r27 = r0
            r28 = 0
            r27[r28] = r11
            r0 = r27
            r13.<init>(r0)
            r13.add(r10)
            gnu.kawa.functions.ValuesMap r27 = gnu.kawa.functions.ValuesMap.valuesMapWithPos
            r0 = r33
            r1 = r34
            r2 = r27
            gnu.expr.Expression r27 = gnu.kawa.functions.CompileMisc.validateApplyValuesMap(r5, r0, r1, r2)
            r0 = r27
            r13.body = r0
            gnu.expr.LetExp r32 = r15.letDone(r13)
            goto L_0x0030
        L_0x0218:
            gnu.bytecode.ClassType r23 = gnu.kawa.xml.SortNodes.typeSortedNodes
            gnu.expr.ApplyExp r22 = new gnu.expr.ApplyExp
            gnu.kawa.xml.SortNodes r27 = gnu.kawa.xml.SortNodes.sortNodes
            r28 = 1
            r0 = r28
            gnu.expr.Expression[] r0 = new gnu.expr.Expression[r0]
            r28 = r0
            r29 = 0
            r28[r29] = r21
            r0 = r22
            r1 = r27
            r2 = r28
            r0.<init>(r1, r2)
            gnu.bytecode.ClassType r27 = gnu.kawa.xml.CoerceNodes.typeNodes
            java.lang.String r28 = "size"
            r29 = 0
            gnu.bytecode.Method r25 = r27.getDeclaredMethod(r28, r29)
            r21 = r22
            goto L_0x00ab
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.CompileMisc.validateApplyValuesFilter(gnu.expr.ApplyExp, gnu.expr.InlineCalls, gnu.bytecode.Type, gnu.mapping.Procedure):gnu.expr.Expression");
    }

    public static Expression validateApplyRelativeStep(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Type rtype;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        Expression exp1 = args[0];
        Expression exp2 = args[1];
        Compilation comp = visitor.getCompilation();
        if ((exp2 instanceof LambdaExp) && comp.mustCompile) {
            LambdaExp lexp2 = (LambdaExp) exp2;
            if (lexp2.min_args == 3 && lexp2.max_args == 3) {
                lexp2.setInlineOnly(true);
                lexp2.returnContinuation = exp;
                lexp2.inlineHome = visitor.getCurrentLambda();
                Expression exp22 = lexp2.body;
                Declaration posArg = lexp2.firstDecl().nextDecl();
                Declaration lastArg = posArg.nextDecl();
                posArg.setNext(lastArg.nextDecl());
                lastArg.setNext(null);
                lexp2.min_args = 2;
                lexp2.max_args = 2;
                Type type1 = exp1.getType();
                if (type1 == null || NodeType.anyNodeTest.compare(type1) != -3) {
                    Type rtype2 = exp.getTypeRaw();
                    if (rtype2 == null || rtype2 == Type.pointer_type) {
                        Type rtypePrime = OccurrenceType.itemPrimeType(exp22.getType());
                        if (NodeType.anyNodeTest.compare(rtypePrime) >= 0) {
                            rtype = NodeSetType.getInstance(rtypePrime);
                        } else {
                            rtype = OccurrenceType.getInstance(rtypePrime, 0, -1);
                        }
                        exp.setType(rtype);
                    }
                    if (lastArg.getCanRead()) {
                        ClassType typeNodes = CoerceNodes.typeNodes;
                        comp.letStart();
                        Declaration sequence = comp.letVariable(null, typeNodes, new ApplyExp((Procedure) CoerceNodes.coerceNodes, exp1));
                        comp.letEnter();
                        Method sizeMethod = typeNodes.getDeclaredMethod("size", 0);
                        ReferenceExp referenceExp = new ReferenceExp(sequence);
                        LetExp lastLet = new LetExp(new Expression[]{new ApplyExp(sizeMethod, referenceExp)});
                        lastLet.addDeclaration(lastArg);
                        Expression function = exp.getFunction();
                        ReferenceExp referenceExp2 = new ReferenceExp(sequence);
                        lastLet.body = new ApplyExp(function, referenceExp2, lexp2);
                        return comp.letDone(lastLet);
                    }
                    ApplyExp result = exp;
                    if (exp22 instanceof ApplyExp) {
                        ApplyExp aexp2 = (ApplyExp) exp22;
                        if (aexp2.getFunction().valueIfConstant() instanceof ValuesFilter) {
                            Expression vexp2 = aexp2.getArgs()[1];
                            if (vexp2 instanceof LambdaExp) {
                                LambdaExp lvexp2 = (LambdaExp) vexp2;
                                Declaration dot2 = lvexp2.firstDecl();
                                if (dot2 != null) {
                                    Declaration pos2 = dot2.nextDecl();
                                    if (pos2 != null && pos2.nextDecl() == null && !pos2.getCanRead() && ClassType.make("java.lang.Number").compare(lvexp2.body.getType()) == -3) {
                                        exp22 = aexp2.getArg(0);
                                        lexp2.body = exp22;
                                        aexp2.setArg(0, exp);
                                        result = aexp2;
                                    }
                                }
                            }
                        }
                    }
                    if (!(exp1 instanceof ApplyExp) || !(exp22 instanceof ApplyExp)) {
                        return result;
                    }
                    ApplyExp aexp1 = (ApplyExp) exp1;
                    ApplyExp aexp22 = (ApplyExp) exp22;
                    Object p1 = aexp1.getFunction().valueIfConstant();
                    Object p2 = aexp22.getFunction().valueIfConstant();
                    if (p1 != RelativeStep.relativeStep || !(p2 instanceof ChildAxis) || aexp1.getArgCount() != 2) {
                        return result;
                    }
                    Expression exp12 = aexp1.getArg(1);
                    if (!(exp12 instanceof LambdaExp)) {
                        return result;
                    }
                    LambdaExp lexp12 = (LambdaExp) exp12;
                    if (!(lexp12.body instanceof ApplyExp) || ((ApplyExp) lexp12.body).getFunction().valueIfConstant() != DescendantOrSelfAxis.anyNode) {
                        return result;
                    }
                    exp.setArg(0, aexp1.getArg(0));
                    aexp22.setFunction(new QuoteExp(DescendantAxis.make(((ChildAxis) p2).getNodePredicate())));
                    return result;
                }
                String message = "step input is " + visitor.getCompilation().getLanguage().formatType(type1) + " - not a node sequence";
                visitor.getMessages().error('e', message);
                ErrorExp errorExp = new ErrorExp(message);
                return errorExp;
            }
        }
        return exp;
    }

    public static Expression validateApplyOrderedMap(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length <= 2) {
            return exp;
        }
        Expression[] rargs = new Expression[(args.length - 1)];
        System.arraycopy(args, 1, rargs, 0, rargs.length);
        return new ApplyExp(proc, args[0], new ApplyExp(typeTuples.getDeclaredMethod("make$V", 2), rargs));
    }

    public static void compileOrderedMap(ApplyExp exp, Compilation comp, Target target, Procedure proc) {
        Expression[] args = exp.getArgs();
        if (args.length != 2) {
            ApplyExp.compile(exp, comp, target);
            return;
        }
        CodeAttr code = comp.getCode();
        Variable consumer = code.pushScope().addVariable(code, typeTuples, null);
        args[1].compile(comp, Target.pushValue(typeTuples));
        code.emitStore(consumer);
        args[0].compile(comp, (Target) new ConsumerTarget(consumer));
        Method mm = typeTuples.getDeclaredMethod("run$X", 1);
        code.emitLoad(consumer);
        PrimProcedure.compileInvoke(comp, mm, target, exp.isTailCall(), 182, Type.pointer_type);
        code.popScope();
    }

    public static Expression validateApplyCastAs(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        ApplyExp exp2 = CompileReflect.inlineClassName(exp, 0, visitor);
        Expression[] args = exp2.getArgs();
        if (args.length != 2 || !(args[0] instanceof QuoteExp) || !(((QuoteExp) args[0]).getValue() instanceof XDataType)) {
            return exp2;
        }
        return new ApplyExp(castMethod, args);
    }

    public static Expression validateApplyCastableAs(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        ApplyExp exp2 = CompileReflect.inlineClassName(exp, 1, visitor);
        Expression[] args = exp2.getArgs();
        if (args.length != 2 || !(args[1] instanceof QuoteExp) || !(((QuoteExp) args[1]).getValue() instanceof XDataType)) {
            return exp2;
        }
        return new ApplyExp(castableMethod, args[1], args[0]);
    }
}
