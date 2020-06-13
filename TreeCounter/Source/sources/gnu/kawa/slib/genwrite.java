package gnu.kawa.slib;

import android.support.p000v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Format;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.C0619lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.standard.Scheme;

/* compiled from: genwrite.scm */
public class genwrite extends ModuleBody {
    public static final genwrite $instance = new genwrite();
    static final Char Lit0 = Char.make(10);
    static final IntNum Lit1 = IntNum.make(0);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("and").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("or").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("let").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("begin").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("do").readResolve());
    static final IntNum Lit15 = IntNum.make(7);
    static final IntNum Lit16 = IntNum.make(8);
    static final IntNum Lit17 = IntNum.make(1);
    static final IntNum Lit18 = IntNum.make(50);
    static final IntNum Lit19 = IntNum.make(2);
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("lambda").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("pp-expr").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("pp-expr-list").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("pp-LAMBDA").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("pp-IF").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("pp-COND").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("pp-CASE").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("pp-AND").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("pp-LET").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("pp-BEGIN").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("pp-DO").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("let*").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.unquote_sym).readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.unquotesplicing_sym).readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("generic-write").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("reverse-string-append").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("letrec").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("define").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("if").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("set!").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("cond").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("case").readResolve());
    public static final ModuleMethod generic$Mnwrite;
    public static final ModuleMethod reverse$Mnstring$Mnappend;

    /* compiled from: genwrite.scm */
    public class frame extends ModuleBody {
        Object display$Qu;
        Object output;
        Object width;

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0040, code lost:
            if (r2 == java.lang.Boolean.FALSE) goto L_0x0042;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:3:0x001a, code lost:
            if (r2 != java.lang.Boolean.FALSE) goto L_0x001c;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static java.lang.Object lambda1isReadMacro(java.lang.Object r5) {
            /*
                gnu.expr.GenericProc r3 = kawa.lib.C0619lists.car
                java.lang.Object r0 = r3.apply1(r5)
                gnu.expr.GenericProc r3 = kawa.lib.C0619lists.cdr
                java.lang.Object r1 = r3.apply1(r5)
                gnu.kawa.functions.IsEqv r3 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r4 = gnu.kawa.slib.genwrite.Lit30
                java.lang.Object r2 = r3.apply2(r0, r4)
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 == r3) goto L_0x0032
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 == r3) goto L_0x0042
            L_0x001c:
                r5 = r1
                boolean r2 = kawa.lib.C0619lists.isPair(r5)
                if (r2 == 0) goto L_0x0066
                gnu.expr.GenericProc r3 = kawa.lib.C0619lists.cdr
                java.lang.Object r3 = r3.apply1(r5)
                boolean r3 = kawa.lib.C0619lists.isNull(r3)
                if (r3 == 0) goto L_0x0063
                java.lang.Boolean r3 = java.lang.Boolean.TRUE
            L_0x0031:
                return r3
            L_0x0032:
                gnu.kawa.functions.IsEqv r3 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r4 = gnu.kawa.slib.genwrite.Lit31
                java.lang.Object r2 = r3.apply2(r0, r4)
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 == r3) goto L_0x0045
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 != r3) goto L_0x001c
            L_0x0042:
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                goto L_0x0031
            L_0x0045:
                gnu.kawa.functions.IsEqv r3 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r4 = gnu.kawa.slib.genwrite.Lit32
                java.lang.Object r2 = r3.apply2(r0, r4)
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 == r3) goto L_0x0056
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                if (r2 == r3) goto L_0x0042
                goto L_0x001c
            L_0x0056:
                gnu.kawa.functions.IsEqv r3 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r4 = gnu.kawa.slib.genwrite.Lit33
                java.lang.Object r3 = r3.apply2(r0, r4)
                java.lang.Boolean r4 = java.lang.Boolean.FALSE
                if (r3 == r4) goto L_0x0042
                goto L_0x001c
            L_0x0063:
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                goto L_0x0031
            L_0x0066:
                if (r2 == 0) goto L_0x006b
                java.lang.Boolean r3 = java.lang.Boolean.TRUE
                goto L_0x0031
            L_0x006b:
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                goto L_0x0031
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.genwrite.frame.lambda1isReadMacro(java.lang.Object):java.lang.Object");
        }

        public static Object lambda2readMacroBody(Object l) {
            return C0619lists.cadr.apply1(l);
        }

        public static Object lambda3readMacroPrefix(Object l) {
            Object head = C0619lists.car.apply1(l);
            C0619lists.cdr.apply1(l);
            if (Scheme.isEqv.apply2(head, genwrite.Lit30) != Boolean.FALSE) {
                return "'";
            }
            if (Scheme.isEqv.apply2(head, genwrite.Lit31) != Boolean.FALSE) {
                return "`";
            }
            if (Scheme.isEqv.apply2(head, genwrite.Lit32) != Boolean.FALSE) {
                return ",";
            }
            return Scheme.isEqv.apply2(head, genwrite.Lit33) != Boolean.FALSE ? ",@" : Values.empty;
        }

        public Object lambda4out(Object str, Object col) {
            if (col == Boolean.FALSE) {
                return col;
            }
            Object x = Scheme.applyToArgs.apply2(this.output, str);
            if (x == Boolean.FALSE) {
                return x;
            }
            try {
                return AddOp.$Pl.apply2(col, Integer.valueOf(strings.stringLength((CharSequence) str)));
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, str);
            }
        }

        public Object lambda5wr(Object obj, Object col) {
            if (C0619lists.isPair(obj)) {
                Object expr = obj;
                if (lambda1isReadMacro(expr) != Boolean.FALSE) {
                    return lambda5wr(lambda2readMacroBody(expr), lambda4out(lambda3readMacroPrefix(expr), col));
                }
                obj = expr;
            } else if (!C0619lists.isNull(obj)) {
                if (vectors.isVector(obj)) {
                    try {
                        Object vector$To$List = vectors.vector$To$List((FVector) obj);
                        col = lambda4out("#", col);
                        obj = vector$To$List;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "vector->list", 1, obj);
                    }
                } else {
                    Object[] objArr = new Object[2];
                    objArr[0] = this.display$Qu != Boolean.FALSE ? "~a" : "~s";
                    objArr[1] = obj;
                    return lambda4out(Format.formatToString(0, objArr), col);
                }
            }
            if (!C0619lists.isPair(obj)) {
                return lambda4out("()", col);
            }
            Object l = C0619lists.cdr.apply1(obj);
            if (col != Boolean.FALSE) {
                col = lambda5wr(C0619lists.car.apply1(obj), lambda4out("(", col));
            }
            while (col != Boolean.FALSE) {
                if (C0619lists.isPair(l)) {
                    Object l2 = C0619lists.cdr.apply1(l);
                    col = lambda5wr(C0619lists.car.apply1(l), lambda4out(" ", col));
                    l = l2;
                } else if (C0619lists.isNull(l)) {
                    return lambda4out(")", col);
                } else {
                    return lambda4out(")", lambda5wr(l, lambda4out(" . ", col)));
                }
            }
            return col;
        }
    }

    /* compiled from: genwrite.scm */
    public class frame0 extends ModuleBody {
        Procedure pp$MnAND = new ModuleMethod(this, 8, genwrite.Lit26, 12291);
        Procedure pp$MnBEGIN = new ModuleMethod(this, 10, genwrite.Lit28, 12291);
        Procedure pp$MnCASE = new ModuleMethod(this, 7, genwrite.Lit25, 12291);
        Procedure pp$MnCOND = new ModuleMethod(this, 6, genwrite.Lit24, 12291);
        Procedure pp$MnDO = new ModuleMethod(this, 11, genwrite.Lit29, 12291);
        Procedure pp$MnIF = new ModuleMethod(this, 5, genwrite.Lit23, 12291);
        Procedure pp$MnLAMBDA = new ModuleMethod(this, 4, genwrite.Lit22, 12291);
        Procedure pp$MnLET = new ModuleMethod(this, 9, genwrite.Lit27, 12291);
        Procedure pp$Mnexpr = new ModuleMethod(this, 2, genwrite.Lit20, 12291);
        Procedure pp$Mnexpr$Mnlist = new ModuleMethod(this, 3, genwrite.Lit21, 12291);
        frame staticLink;

        public Object lambda6indent(Object to, Object col) {
            Object n;
            if (col == Boolean.FALSE) {
                return col;
            }
            if (Scheme.numLss.apply2(to, col) != Boolean.FALSE) {
                Object x = this.staticLink.lambda4out(strings.makeString(1, genwrite.Lit0), col);
                if (x == Boolean.FALSE) {
                    return x;
                }
                col = genwrite.Lit1;
                n = to;
            } else {
                n = AddOp.$Mn.apply2(to, col);
            }
            while (true) {
                Object obj = n;
                if (Scheme.numGrt.apply2(obj, genwrite.Lit1) == Boolean.FALSE) {
                    break;
                } else if (Scheme.numGrt.apply2(obj, genwrite.Lit15) != Boolean.FALSE) {
                    n = AddOp.$Mn.apply2(obj, genwrite.Lit16);
                    col = this.staticLink.lambda4out("        ", col);
                } else {
                    try {
                        col = this.staticLink.lambda4out(strings.substring("        ", 0, ((Number) obj).intValue()), col);
                        break;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "substring", 3, obj);
                    }
                }
            }
            return col;
        }

        public Object lambda7pr(Object obj, Object col, Object extra, Object pp$Mnpair) {
            frame1 frame1 = new frame1();
            frame1.staticLink = this;
            boolean x = C0619lists.isPair(obj);
            if (!x ? !vectors.isVector(obj) : !x) {
                return this.staticLink.lambda5wr(obj, col);
            }
            LList lList = LList.Empty;
            frame1.left = numbers.min(AddOp.$Pl.apply2(AddOp.$Mn.apply2(AddOp.$Mn.apply2(this.staticLink.width, col), extra), genwrite.Lit17), genwrite.Lit18);
            frame1.result = lList;
            genwrite.genericWrite(obj, this.staticLink.display$Qu, Boolean.FALSE, frame1.lambda$Fn1);
            if (Scheme.numGrt.apply2(frame1.left, genwrite.Lit1) != Boolean.FALSE) {
                return this.staticLink.lambda4out(genwrite.reverseStringAppend(frame1.result), col);
            }
            if (C0619lists.isPair(obj)) {
                return Scheme.applyToArgs.apply4(pp$Mnpair, obj, col, extra);
            }
            try {
                return lambda10ppList(vectors.vector$To$List((FVector) obj), this.staticLink.lambda4out("#", col), extra, this.pp$Mnexpr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "vector->list", 1, obj);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0054, code lost:
            if (r10 == java.lang.Boolean.FALSE) goto L_0x0056;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0037, code lost:
            if (r10 != java.lang.Boolean.FALSE) goto L_0x0039;
         */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x003f  */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x0105 A[SYNTHETIC, Splitter:B:53:0x0105] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object lambda8ppExpr(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
            /*
                r11 = this;
                java.lang.Object r0 = gnu.kawa.slib.genwrite.frame.lambda1isReadMacro(r12)
                java.lang.Boolean r1 = java.lang.Boolean.FALSE
                if (r0 == r1) goto L_0x001d
                java.lang.Object r0 = gnu.kawa.slib.genwrite.frame.lambda2readMacroBody(r12)
                gnu.kawa.slib.genwrite$frame r1 = r11.staticLink
                java.lang.Object r2 = gnu.kawa.slib.genwrite.frame.lambda3readMacroPrefix(r12)
                java.lang.Object r1 = r1.lambda4out(r2, r13)
                gnu.mapping.Procedure r2 = r11.pp$Mnexpr
                java.lang.Object r0 = r11.lambda7pr(r0, r1, r14, r2)
            L_0x001c:
                return r0
            L_0x001d:
                gnu.expr.GenericProc r0 = kawa.lib.C0619lists.car
                java.lang.Object r8 = r0.apply1(r12)
                boolean r0 = kawa.lib.misc.isSymbol(r8)
                if (r0 == 0) goto L_0x012c
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit2
                java.lang.Object r10 = r0.apply2(r8, r1)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0046
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0056
            L_0x0039:
                gnu.mapping.Procedure r9 = r11.pp$MnLAMBDA
            L_0x003b:
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r9 == r0) goto L_0x0105
                gnu.kawa.functions.ApplyToArgs r0 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r0 = r0.apply4(r9, r12, r13, r14)
                goto L_0x001c
            L_0x0046:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit3
                java.lang.Object r10 = r0.apply2(r8, r1)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0069
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 != r0) goto L_0x0039
            L_0x0056:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit6
                java.lang.Object r10 = r0.apply2(r8, r1)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0087
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0093
            L_0x0066:
                gnu.mapping.Procedure r9 = r11.pp$MnIF
                goto L_0x003b
            L_0x0069:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit4
                java.lang.Object r10 = r0.apply2(r8, r1)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x007a
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x0056
                goto L_0x0039
            L_0x007a:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit5
                java.lang.Object r0 = r0.apply2(r8, r1)
                java.lang.Boolean r1 = java.lang.Boolean.FALSE
                if (r0 == r1) goto L_0x0056
                goto L_0x0039
            L_0x0087:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit7
                java.lang.Object r0 = r0.apply2(r8, r1)
                java.lang.Boolean r1 = java.lang.Boolean.FALSE
                if (r0 != r1) goto L_0x0066
            L_0x0093:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit8
                java.lang.Object r0 = r0.apply2(r8, r1)
                java.lang.Boolean r1 = java.lang.Boolean.FALSE
                if (r0 == r1) goto L_0x00a2
                gnu.mapping.Procedure r9 = r11.pp$MnCOND
                goto L_0x003b
            L_0x00a2:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit9
                java.lang.Object r0 = r0.apply2(r8, r1)
                java.lang.Boolean r1 = java.lang.Boolean.FALSE
                if (r0 == r1) goto L_0x00b1
                gnu.mapping.Procedure r9 = r11.pp$MnCASE
                goto L_0x003b
            L_0x00b1:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit10
                java.lang.Object r10 = r0.apply2(r8, r1)
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x00c5
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                if (r10 == r0) goto L_0x00d1
            L_0x00c1:
                gnu.mapping.Procedure r9 = r11.pp$MnAND
                goto L_0x003b
            L_0x00c5:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit11
                java.lang.Object r0 = r0.apply2(r8, r1)
                java.lang.Boolean r1 = java.lang.Boolean.FALSE
                if (r0 != r1) goto L_0x00c1
            L_0x00d1:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit12
                java.lang.Object r0 = r0.apply2(r8, r1)
                java.lang.Boolean r1 = java.lang.Boolean.FALSE
                if (r0 == r1) goto L_0x00e1
                gnu.mapping.Procedure r9 = r11.pp$MnLET
                goto L_0x003b
            L_0x00e1:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit13
                java.lang.Object r0 = r0.apply2(r8, r1)
                java.lang.Boolean r1 = java.lang.Boolean.FALSE
                if (r0 == r1) goto L_0x00f1
                gnu.mapping.Procedure r9 = r11.pp$MnBEGIN
                goto L_0x003b
            L_0x00f1:
                gnu.kawa.functions.IsEqv r0 = kawa.standard.Scheme.isEqv
                gnu.mapping.SimpleSymbol r1 = gnu.kawa.slib.genwrite.Lit14
                java.lang.Object r0 = r0.apply2(r8, r1)
                java.lang.Boolean r1 = java.lang.Boolean.FALSE
                if (r0 == r1) goto L_0x0101
                gnu.mapping.Procedure r9 = r11.pp$MnDO
                goto L_0x003b
            L_0x0101:
                java.lang.Boolean r9 = java.lang.Boolean.FALSE
                goto L_0x003b
            L_0x0105:
                gnu.mapping.Symbol r8 = (gnu.mapping.Symbol) r8     // Catch:{ ClassCastException -> 0x0134 }
                java.lang.String r0 = kawa.lib.misc.symbol$To$String(r8)
                int r0 = kawa.lib.strings.stringLength(r0)
                r1 = 5
                if (r0 <= r1) goto L_0x0124
                java.lang.Boolean r4 = java.lang.Boolean.FALSE
                java.lang.Boolean r5 = java.lang.Boolean.FALSE
                java.lang.Boolean r6 = java.lang.Boolean.FALSE
                gnu.mapping.Procedure r7 = r11.pp$Mnexpr
                r0 = r11
                r1 = r12
                r2 = r13
                r3 = r14
                java.lang.Object r0 = r0.lambda12ppGeneral(r1, r2, r3, r4, r5, r6, r7)
                goto L_0x001c
            L_0x0124:
                gnu.mapping.Procedure r0 = r11.pp$Mnexpr
                java.lang.Object r0 = r11.lambda9ppCall(r12, r13, r14, r0)
                goto L_0x001c
            L_0x012c:
                gnu.mapping.Procedure r0 = r11.pp$Mnexpr
                java.lang.Object r0 = r11.lambda10ppList(r12, r13, r14, r0)
                goto L_0x001c
            L_0x0134:
                r0 = move-exception
                gnu.mapping.WrongType r1 = new gnu.mapping.WrongType
                java.lang.String r2 = "symbol->string"
                r3 = 1
                r1.<init>(r0, r2, r3, r8)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.genwrite.frame0.lambda8ppExpr(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 2:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 3;
                    return 0;
                case 3:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 3;
                    return 0;
                case 4:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 3;
                    return 0;
                case 5:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 3;
                    return 0;
                case 6:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 3;
                    return 0;
                case 7:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 3;
                    return 0;
                case 8:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 3;
                    return 0;
                case 9:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 3;
                    return 0;
                case 10:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 3;
                    return 0;
                case 11:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 3;
                    return 0;
                default:
                    return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
        }

        public Object lambda9ppCall(Object expr, Object col, Object extra, Object pp$Mnitem) {
            Object col$St = this.staticLink.lambda5wr(C0619lists.car.apply1(expr), this.staticLink.lambda4out("(", col));
            if (col == Boolean.FALSE) {
                return col;
            }
            return lambda11ppDown(C0619lists.cdr.apply1(expr), col$St, AddOp.$Pl.apply2(col$St, genwrite.Lit17), extra, pp$Mnitem);
        }

        public Object lambda10ppList(Object l, Object col, Object extra, Object pp$Mnitem) {
            Object col2 = this.staticLink.lambda4out("(", col);
            return lambda11ppDown(l, col2, col2, extra, pp$Mnitem);
        }

        public Object lambda11ppDown(Object l, Object obj, Object col2, Object obj2, Object pp$Mnitem) {
            while (true) {
                if (obj == Boolean.FALSE) {
                    return obj;
                }
                if (!C0619lists.isPair(l)) {
                    return C0619lists.isNull(l) ? this.staticLink.lambda4out(")", obj) : this.staticLink.lambda4out(")", lambda7pr(l, lambda6indent(col2, this.staticLink.lambda4out(".", lambda6indent(col2, obj))), AddOp.$Pl.apply2(obj2, genwrite.Lit17), pp$Mnitem));
                }
                Object rest = C0619lists.cdr.apply1(l);
                l = rest;
                obj = lambda7pr(C0619lists.car.apply1(l), lambda6indent(col2, obj), C0619lists.isNull(rest) ? AddOp.$Pl.apply2(obj2, genwrite.Lit17) : genwrite.Lit1, pp$Mnitem);
            }
        }

        public Object lambda12ppGeneral(Object expr, Object col, Object extra, Object named$Qu, Object pp$Mn1, Object pp$Mn2, Object pp$Mn3) {
            Object apply2;
            Object apply22;
            Object col$St$St;
            Object obj;
            Object obj2;
            Object obj3;
            Object extra2;
            Object extra3;
            Object head = C0619lists.car.apply1(expr);
            Object rest = C0619lists.cdr.apply1(expr);
            Object col$St = this.staticLink.lambda5wr(head, this.staticLink.lambda4out("(", col));
            if (named$Qu == Boolean.FALSE ? named$Qu != Boolean.FALSE : C0619lists.isPair(rest)) {
                Object name = C0619lists.car.apply1(rest);
                rest = C0619lists.cdr.apply1(rest);
                col$St$St = this.staticLink.lambda5wr(name, this.staticLink.lambda4out(" ", col$St));
                apply2 = AddOp.$Pl.apply2(col, genwrite.Lit19);
                apply22 = AddOp.$Pl.apply2(col$St$St, genwrite.Lit17);
            } else {
                apply2 = AddOp.$Pl.apply2(col, genwrite.Lit19);
                apply22 = AddOp.$Pl.apply2(col$St, genwrite.Lit17);
                col$St$St = col$St;
            }
            if (pp$Mn1 == Boolean.FALSE ? pp$Mn1 != Boolean.FALSE : C0619lists.isPair(rest)) {
                Object val1 = C0619lists.car.apply1(rest);
                rest = C0619lists.cdr.apply1(rest);
                if (C0619lists.isNull(rest)) {
                    extra3 = AddOp.$Pl.apply2(extra, genwrite.Lit17);
                } else {
                    extra3 = genwrite.Lit1;
                }
                col$St$St = lambda7pr(val1, lambda6indent(apply22, col$St$St), extra3, pp$Mn1);
            }
            if (pp$Mn2 == Boolean.FALSE ? pp$Mn2 != Boolean.FALSE : C0619lists.isPair(rest)) {
                Object val12 = C0619lists.car.apply1(rest);
                Object rest2 = C0619lists.cdr.apply1(rest);
                if (C0619lists.isNull(rest2)) {
                    extra2 = AddOp.$Pl.apply2(extra, genwrite.Lit17);
                } else {
                    extra2 = genwrite.Lit1;
                }
                obj = lambda7pr(val12, lambda6indent(apply22, col$St$St), extra2, pp$Mn2);
                obj2 = apply2;
                obj3 = rest2;
            } else {
                obj = col$St$St;
                obj2 = apply2;
                obj3 = rest;
            }
            return lambda11ppDown(obj3, obj, obj2, extra, pp$Mn3);
        }

        public Object lambda13ppExprList(Object l, Object col, Object extra) {
            return lambda10ppList(l, col, extra, this.pp$Mnexpr);
        }

        public Object lambda14pp$MnLAMBDA(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr$Mnlist, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda15pp$MnIF(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda16pp$MnCOND(Object expr, Object col, Object extra) {
            return lambda9ppCall(expr, col, extra, this.pp$Mnexpr$Mnlist);
        }

        public Object lambda17pp$MnCASE(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr, Boolean.FALSE, this.pp$Mnexpr$Mnlist);
        }

        public Object lambda18pp$MnAND(Object expr, Object col, Object extra) {
            return lambda9ppCall(expr, col, extra, this.pp$Mnexpr);
        }

        public Object lambda19pp$MnLET(Object expr, Object col, Object extra) {
            boolean named$Qu;
            Object rest = C0619lists.cdr.apply1(expr);
            boolean x = C0619lists.isPair(rest);
            if (x) {
                named$Qu = misc.isSymbol(C0619lists.car.apply1(rest));
            } else {
                named$Qu = x;
            }
            return lambda12ppGeneral(expr, col, extra, named$Qu ? Boolean.TRUE : Boolean.FALSE, this.pp$Mnexpr$Mnlist, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda20pp$MnBEGIN(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            switch (moduleMethod.selector) {
                case 2:
                    return lambda8ppExpr(obj, obj2, obj3);
                case 3:
                    return lambda13ppExprList(obj, obj2, obj3);
                case 4:
                    return lambda14pp$MnLAMBDA(obj, obj2, obj3);
                case 5:
                    return lambda15pp$MnIF(obj, obj2, obj3);
                case 6:
                    return lambda16pp$MnCOND(obj, obj2, obj3);
                case 7:
                    return lambda17pp$MnCASE(obj, obj2, obj3);
                case 8:
                    return lambda18pp$MnAND(obj, obj2, obj3);
                case 9:
                    return lambda19pp$MnLET(obj, obj2, obj3);
                case 10:
                    return lambda20pp$MnBEGIN(obj, obj2, obj3);
                case 11:
                    return lambda21pp$MnDO(obj, obj2, obj3);
                default:
                    return super.apply3(moduleMethod, obj, obj2, obj3);
            }
        }

        public Object lambda21pp$MnDO(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr$Mnlist, this.pp$Mnexpr$Mnlist, this.pp$Mnexpr);
        }
    }

    /* compiled from: genwrite.scm */
    public class frame1 extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        Object left;
        Object result;
        frame0 staticLink;

        public frame1() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/genwrite.scm:72");
            this.lambda$Fn1 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 1) {
                return lambda22(obj) ? Boolean.TRUE : Boolean.FALSE;
            }
            return super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: 0000 */
        public boolean lambda22(Object str) {
            this.result = C0619lists.cons(str, this.result);
            try {
                this.left = AddOp.$Mn.apply2(this.left, Integer.valueOf(strings.stringLength((CharSequence) str)));
                return ((Boolean) Scheme.numGrt.apply2(this.left, genwrite.Lit1)).booleanValue();
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, str);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.f232pc = 1;
            return 0;
        }
    }

    static {
        genwrite genwrite = $instance;
        generic$Mnwrite = new ModuleMethod(genwrite, 12, Lit34, 16388);
        reverse$Mnstring$Mnappend = new ModuleMethod(genwrite, 13, Lit35, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public genwrite() {
        ModuleInfo.register(this);
    }

    public static Object genericWrite(Object obj, Object isDisplay, Object width, Object output) {
        frame closureEnv = new frame();
        closureEnv.display$Qu = isDisplay;
        closureEnv.width = width;
        closureEnv.output = output;
        if (closureEnv.width == Boolean.FALSE) {
            return closureEnv.lambda5wr(obj, Lit1);
        }
        CharSequence makeString = strings.makeString(1, Lit0);
        IntNum intNum = Lit1;
        frame0 frame02 = new frame0();
        frame02.staticLink = closureEnv;
        Procedure procedure = frame02.pp$Mnexpr;
        Procedure procedure2 = frame02.pp$Mnexpr$Mnlist;
        Procedure procedure3 = frame02.pp$MnLAMBDA;
        Procedure procedure4 = frame02.pp$MnIF;
        Procedure procedure5 = frame02.pp$MnCOND;
        Procedure procedure6 = frame02.pp$MnCASE;
        Procedure procedure7 = frame02.pp$MnAND;
        Procedure procedure8 = frame02.pp$MnLET;
        Procedure procedure9 = frame02.pp$MnBEGIN;
        frame02.pp$MnDO = frame02.pp$MnDO;
        frame02.pp$MnBEGIN = procedure9;
        frame02.pp$MnLET = procedure8;
        frame02.pp$MnAND = procedure7;
        frame02.pp$MnCASE = procedure6;
        frame02.pp$MnCOND = procedure5;
        frame02.pp$MnIF = procedure4;
        frame02.pp$MnLAMBDA = procedure3;
        frame02.pp$Mnexpr$Mnlist = procedure2;
        frame02.pp$Mnexpr = procedure;
        return closureEnv.lambda4out(makeString, frame02.lambda7pr(obj, intNum, Lit1, frame02.pp$Mnexpr));
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        return moduleMethod.selector == 12 ? genericWrite(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        if (moduleMethod.selector != 12) {
            return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.value3 = obj3;
        callContext.value4 = obj4;
        callContext.proc = moduleMethod;
        callContext.f232pc = 4;
        return 0;
    }

    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    public static Object reverseStringAppend(Object l) {
        return lambda23revStringAppend(l, Lit1);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        return moduleMethod.selector == 13 ? reverseStringAppend(obj) : super.apply1(moduleMethod, obj);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        if (moduleMethod.selector != 13) {
            return super.match1(moduleMethod, obj, callContext);
        }
        callContext.value1 = obj;
        callContext.proc = moduleMethod;
        callContext.f232pc = 1;
        return 0;
    }

    public static Object lambda23revStringAppend(Object l, Object i) {
        if (C0619lists.isPair(l)) {
            Object str = C0619lists.car.apply1(l);
            try {
                int len = strings.stringLength((CharSequence) str);
                Object result = lambda23revStringAppend(C0619lists.cdr.apply1(l), AddOp.$Pl.apply2(i, Integer.valueOf(len)));
                Object obj = Lit1;
                try {
                    Object apply2 = AddOp.$Mn.apply2(AddOp.$Mn.apply2(Integer.valueOf(strings.stringLength((CharSequence) result)), i), Integer.valueOf(len));
                    while (Scheme.numLss.apply2(obj, Integer.valueOf(len)) != Boolean.FALSE) {
                        try {
                            try {
                                try {
                                    try {
                                        strings.stringSet$Ex((CharSeq) result, ((Number) apply2).intValue(), strings.stringRef((CharSequence) str, ((Number) obj).intValue()));
                                        obj = AddOp.$Pl.apply2(obj, Lit17);
                                        apply2 = AddOp.$Pl.apply2(apply2, Lit17);
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "string-ref", 2, obj);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "string-ref", 1, str);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "string-set!", 2, apply2);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "string-set!", 1, result);
                        }
                    }
                    return result;
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-length", 1, result);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-length", 1, str);
            }
        } else {
            try {
                return strings.makeString(((Number) i).intValue());
            } catch (ClassCastException e7) {
                throw new WrongType(e7, "make-string", 1, i);
            }
        }
    }
}
