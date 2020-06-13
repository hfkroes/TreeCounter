package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;
import java.util.Vector;

public class SyntaxPattern extends Pattern implements Externalizable {
    static final int MATCH_ANY = 3;
    static final int MATCH_ANY_CAR = 7;
    static final int MATCH_EQUALS = 2;
    static final int MATCH_IGNORE = 24;
    static final int MATCH_LENGTH = 6;
    static final int MATCH_LREPEAT = 5;
    static final int MATCH_MISC = 0;
    static final int MATCH_NIL = 8;
    static final int MATCH_PAIR = 4;
    static final int MATCH_VECTOR = 16;
    static final int MATCH_WIDE = 1;
    Object[] literals;
    String program;
    int varCount;

    public int varCount() {
        return this.varCount;
    }

    public boolean match(Object obj, Object[] vars, int start_vars) {
        return match(obj, vars, start_vars, 0, null);
    }

    public SyntaxPattern(String program2, Object[] literals2, int varCount2) {
        this.program = program2;
        this.literals = literals2;
        this.varCount = varCount2;
    }

    public SyntaxPattern(Object pattern, Object[] literal_identifiers, Translator tr) {
        this(new StringBuffer(), pattern, null, literal_identifiers, tr);
    }

    SyntaxPattern(StringBuffer programbuf, Object pattern, SyntaxForm syntax, Object[] literal_identifiers, Translator tr) {
        Vector literalsbuf = new Vector();
        translate(pattern, programbuf, literal_identifiers, 0, literalsbuf, null, 0, tr);
        this.program = programbuf.toString();
        this.literals = new Object[literalsbuf.size()];
        literalsbuf.copyInto(this.literals);
        this.varCount = tr.patternScope.pattern_names.size();
    }

    public void disassemble() {
        disassemble(OutPort.errDefault(), (Translator) Compilation.getCurrent(), 0, this.program.length());
    }

    public void disassemble(PrintWriter ps, Translator tr) {
        disassemble(ps, tr, 0, this.program.length());
    }

    /* access modifiers changed from: 0000 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void disassemble(java.io.PrintWriter r10, kawa.lang.Translator r11, int r12, int r13) {
        /*
            r9 = this;
            r8 = 8
            r4 = 0
            if (r11 == 0) goto L_0x000d
            kawa.lang.PatternScope r6 = r11.patternScope
            if (r6 == 0) goto L_0x000d
            kawa.lang.PatternScope r6 = r11.patternScope
            java.util.Vector r4 = r6.pattern_names
        L_0x000d:
            r5 = 0
            r1 = r12
        L_0x000f:
            if (r1 >= r13) goto L_0x01ee
            java.lang.String r6 = r9.program
            char r0 = r6.charAt(r1)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = " "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = ": "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r6 = r6.toString()
            r10.print(r6)
            int r1 = r1 + 1
            r3 = r0 & 7
            int r6 = r5 << 13
            int r7 = r0 >> 3
            r5 = r6 | r7
            switch(r3) {
                case 0: goto L_0x01a9;
                case 1: goto L_0x0066;
                case 2: goto L_0x007d;
                case 3: goto L_0x00af;
                case 4: goto L_0x00e6;
                case 5: goto L_0x0104;
                case 6: goto L_0x017c;
                case 7: goto L_0x00af;
                default: goto L_0x0044;
            }
        L_0x0044:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = " - "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r3)
            r7 = 47
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r5)
            java.lang.String r6 = r6.toString()
            r10.println(r6)
        L_0x0064:
            r5 = 0
            goto L_0x000f
        L_0x0066:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = " - WIDE "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r5)
            java.lang.String r6 = r6.toString()
            r10.println(r6)
            goto L_0x000f
        L_0x007d:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = " - EQUALS["
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r5)
            java.lang.String r7 = "]"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r10.print(r6)
            java.lang.Object[] r6 = r9.literals
            if (r6 == 0) goto L_0x00ab
            if (r5 < 0) goto L_0x00ab
            java.lang.Object[] r6 = r9.literals
            int r6 = r6.length
            if (r5 >= r6) goto L_0x00ab
            java.lang.Object[] r6 = r9.literals
            r6 = r6[r5]
            r10.print(r6)
        L_0x00ab:
            r10.println()
            goto L_0x0064
        L_0x00af:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r6 = 3
            if (r3 != r6) goto L_0x00e3
            java.lang.String r6 = " - ANY["
        L_0x00b9:
            java.lang.StringBuilder r6 = r7.append(r6)
            java.lang.StringBuilder r6 = r6.append(r5)
            java.lang.String r7 = "]"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r10.print(r6)
            if (r4 == 0) goto L_0x00df
            if (r5 < 0) goto L_0x00df
            int r6 = r4.size()
            if (r5 >= r6) goto L_0x00df
            java.lang.Object r6 = r4.elementAt(r5)
            r10.print(r6)
        L_0x00df:
            r10.println()
            goto L_0x0064
        L_0x00e3:
            java.lang.String r6 = " - ANY_CAR["
            goto L_0x00b9
        L_0x00e6:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = " - PAIR["
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r5)
            java.lang.String r7 = "]"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r10.println(r6)
            goto L_0x0064
        L_0x0104:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = " - LREPEAT["
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r5)
            java.lang.String r7 = "]"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r10.println(r6)
            int r6 = r1 + r5
            r9.disassemble(r10, r11, r1, r6)
            int r1 = r1 + r5
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = " "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = ": - repeat first var:"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = r9.program
            int r2 = r1 + 1
            char r7 = r7.charAt(r1)
            int r7 = r7 >> 3
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r10.println(r6)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = " "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.String r7 = ": - repeast nested vars:"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = r9.program
            int r1 = r2 + 1
            char r7 = r7.charAt(r2)
            int r7 = r7 >> 3
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r10.println(r6)
            goto L_0x0064
        L_0x017c:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = " - LENGTH "
            java.lang.StringBuilder r6 = r6.append(r7)
            int r7 = r5 >> 1
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = " pairs. "
            java.lang.StringBuilder r7 = r6.append(r7)
            r6 = r5 & 1
            if (r6 != 0) goto L_0x01a6
            java.lang.String r6 = "pure list"
        L_0x0199:
            java.lang.StringBuilder r6 = r7.append(r6)
            java.lang.String r6 = r6.toString()
            r10.println(r6)
            goto L_0x0064
        L_0x01a6:
            java.lang.String r6 = "impure list"
            goto L_0x0199
        L_0x01a9:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "[misc ch:"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r7 = " n:"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r7 = "]"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r10.print(r6)
            if (r0 != r8) goto L_0x01d8
            java.lang.String r6 = " - NIL"
            r10.println(r6)
            goto L_0x0064
        L_0x01d8:
            r6 = 16
            if (r0 != r6) goto L_0x01e3
            java.lang.String r6 = " - VECTOR"
            r10.println(r6)
            goto L_0x0064
        L_0x01e3:
            r6 = 24
            if (r0 != r6) goto L_0x0044
            java.lang.String r6 = " - IGNORE"
            r10.println(r6)
            goto L_0x0064
        L_0x01ee:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.SyntaxPattern.disassemble(java.io.PrintWriter, kawa.lang.Translator, int, int):void");
    }

    /* access modifiers changed from: 0000 */
    public void translate(Object pattern, StringBuffer program2, Object[] literal_identifiers, int nesting, Vector literals2, SyntaxForm syntax, char context, Translator tr) {
        int i;
        ScopeExp scope1;
        Object literal;
        ScopeExp scope2;
        Object next;
        int i2;
        int restLength;
        PatternScope patternScope = tr.patternScope;
        Vector patternNames = patternScope.pattern_names;
        while (true) {
            if (pattern instanceof SyntaxForm) {
                syntax = (SyntaxForm) pattern;
                pattern = syntax.getDatum();
            } else if (pattern instanceof Pair) {
                Object savePos = tr.pushPositionOf(pattern);
                try {
                    int start_pc = program2.length();
                    program2.append(4);
                    Pair pair = (Pair) pattern;
                    SyntaxForm car_syntax = syntax;
                    Object next2 = pair.getCdr();
                    while (next instanceof SyntaxForm) {
                        syntax = (SyntaxForm) next;
                        next2 = syntax.getDatum();
                    }
                    boolean repeat = false;
                    if (next instanceof Pair) {
                        if (tr.matches(((Pair) next).getCar(), "...")) {
                            repeat = true;
                            next = ((Pair) next).getCdr();
                            while (next instanceof SyntaxForm) {
                                syntax = (SyntaxForm) next;
                                next = syntax.getDatum();
                            }
                        }
                    }
                    int subvar0 = patternNames.size();
                    if (context == 'P') {
                        context = 0;
                    }
                    Object car = pair.getCar();
                    if (repeat) {
                        i2 = nesting + 1;
                    } else {
                        i2 = nesting;
                    }
                    translate(car, program2, literal_identifiers, i2, literals2, car_syntax, context == 'V' ? 0 : 'P', tr);
                    int subvarN = patternNames.size() - subvar0;
                    int width = (((program2.length() - start_pc) - 1) << 3) | (repeat ? 5 : 4);
                    if (width > 65535) {
                        start_pc += insertInt(start_pc, program2, (width >> 13) + 1);
                    }
                    program2.setCharAt(start_pc, (char) width);
                    int restLength2 = Translator.listLength(next);
                    if (restLength2 == Integer.MIN_VALUE) {
                        tr.syntaxError("cyclic pattern list");
                        tr.popPositionOf(savePos);
                        return;
                    }
                    if (repeat) {
                        addInt(program2, subvar0 << 3);
                        addInt(program2, subvarN << 3);
                        if (next == LList.Empty) {
                            program2.append(8);
                            tr.popPositionOf(savePos);
                            return;
                        }
                        if (restLength2 >= 0) {
                            restLength = restLength2 << 1;
                        } else {
                            restLength = ((-restLength2) << 1) - 1;
                        }
                        addInt(program2, (restLength << 3) | 6);
                    }
                    pattern = next;
                } finally {
                    tr.popPositionOf(savePos);
                }
            } else if (pattern instanceof Symbol) {
                int j = literal_identifiers.length;
                do {
                    j--;
                    if (j >= 0) {
                        ScopeExp current = tr.currentScope();
                        scope1 = syntax == null ? current : syntax.getScope();
                        literal = literal_identifiers[j];
                        if (literal instanceof SyntaxForm) {
                            SyntaxForm syntax2 = (SyntaxForm) literal;
                            literal = syntax2.getDatum();
                            scope2 = syntax2.getScope();
                        } else if (tr.currentMacroDefinition != null) {
                            scope2 = tr.currentMacroDefinition.getCapturedScope();
                        } else {
                            scope2 = current;
                        }
                    } else {
                        if (patternNames.contains(pattern)) {
                            tr.syntaxError("duplicated pattern variable " + pattern);
                        }
                        int i3 = patternNames.size();
                        patternNames.addElement(pattern);
                        boolean matchCar = context == 'P';
                        patternScope.patternNesting.append((char) ((nesting << 1) + (matchCar ? 1 : 0)));
                        Declaration decl = patternScope.addDeclaration(pattern);
                        decl.setLocation(tr);
                        tr.push(decl);
                        int i4 = i3 << 3;
                        if (matchCar) {
                            i = 7;
                        } else {
                            i = 3;
                        }
                        addInt(program2, i | i4);
                        return;
                    }
                } while (!literalIdentifierEq(pattern, scope1, literal, scope2));
                int i5 = SyntaxTemplate.indexOf(literals2, pattern);
                if (i5 < 0) {
                    i5 = literals2.size();
                    literals2.addElement(pattern);
                }
                addInt(program2, (i5 << 3) | 2);
                return;
            } else if (pattern == LList.Empty) {
                program2.append(8);
                return;
            } else if (pattern instanceof FVector) {
                program2.append(16);
                pattern = LList.makeList((FVector) pattern);
                context = 'V';
            } else {
                int i6 = SyntaxTemplate.indexOf(literals2, pattern);
                if (i6 < 0) {
                    i6 = literals2.size();
                    literals2.addElement(pattern);
                }
                addInt(program2, (i6 << 3) | 2);
                return;
            }
        }
    }

    private static void addInt(StringBuffer sbuf, int val) {
        if (val > 65535) {
            addInt(sbuf, (val << 13) + 1);
        }
        sbuf.append((char) val);
    }

    private static int insertInt(int offset, StringBuffer sbuf, int val) {
        if (val > 65535) {
            offset += insertInt(offset, sbuf, (val << 13) + 1);
        }
        sbuf.insert(offset, (char) val);
        return offset + 1;
    }

    /* access modifiers changed from: 0000 */
    public boolean match_car(Pair p, Object[] vars, int start_vars, int pc, SyntaxForm syntax) {
        int pc_start = pc;
        int pc2 = pc + 1;
        char ch = this.program.charAt(pc);
        int value = ch >> 3;
        while ((ch & 7) == 1) {
            int i = value << 13;
            int pc3 = pc2 + 1;
            ch = this.program.charAt(pc2);
            value = i | (ch >> 3);
            pc2 = pc3;
        }
        if ((ch & 7) == 7) {
            if (syntax != null && !(p.getCar() instanceof SyntaxForm)) {
                p = Translator.makePair(p, SyntaxForms.fromDatum(p.getCar(), syntax), p.getCdr());
            }
            vars[start_vars + value] = p;
            return true;
        }
        return match(p.getCar(), vars, start_vars, pc_start, syntax);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:171:?, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean match(java.lang.Object r39, java.lang.Object[] r40, int r41, int r42, kawa.lang.SyntaxForm r43) {
        /*
            r38 = this;
            r37 = 0
        L_0x0002:
            r0 = r39
            boolean r4 = r0 instanceof kawa.lang.SyntaxForm
            if (r4 == 0) goto L_0x0011
            r43 = r39
            kawa.lang.SyntaxForm r43 = (kawa.lang.SyntaxForm) r43
            java.lang.Object r39 = r43.getDatum()
            goto L_0x0002
        L_0x0011:
            r0 = r38
            java.lang.String r4 = r0.program
            int r8 = r42 + 1
            r0 = r42
            char r16 = r4.charAt(r0)
            r27 = r16 & 7
            int r4 = r37 << 13
            int r6 = r16 >> 3
            r37 = r4 | r6
            switch(r27) {
                case 0: goto L_0x0047;
                case 1: goto L_0x0044;
                case 2: goto L_0x022f;
                case 3: goto L_0x0293;
                case 4: goto L_0x00e5;
                case 5: goto L_0x0111;
                case 6: goto L_0x009e;
                case 7: goto L_0x0028;
                case 8: goto L_0x0092;
                default: goto L_0x0028;
            }
        L_0x0028:
            r38.disassemble()
            java.lang.Error r4 = new java.lang.Error
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "unrecognized pattern opcode @pc:"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r6 = r6.toString()
            r4.<init>(r6)
            throw r4
        L_0x0044:
            r42 = r8
            goto L_0x0002
        L_0x0047:
            r4 = 8
            r0 = r16
            if (r0 != r4) goto L_0x0059
            gnu.lists.LList r4 = gnu.lists.LList.Empty
            r0 = r39
            if (r0 != r4) goto L_0x0057
            r4 = 1
        L_0x0054:
            r42 = r8
        L_0x0056:
            return r4
        L_0x0057:
            r4 = 0
            goto L_0x0054
        L_0x0059:
            r4 = 16
            r0 = r16
            if (r0 != r4) goto L_0x0080
            r0 = r39
            boolean r4 = r0 instanceof gnu.lists.FVector
            if (r4 != 0) goto L_0x0069
            r4 = 0
            r42 = r8
            goto L_0x0056
        L_0x0069:
            r4 = r39
            gnu.lists.FVector r4 = (gnu.lists.FVector) r4
            gnu.lists.LList r5 = gnu.lists.LList.makeList(r4)
            r4 = r38
            r6 = r40
            r7 = r41
            r9 = r43
            boolean r4 = r4.match(r5, r6, r7, r8, r9)
            r42 = r8
            goto L_0x0056
        L_0x0080:
            r4 = 24
            r0 = r16
            if (r0 != r4) goto L_0x008a
            r4 = 1
            r42 = r8
            goto L_0x0056
        L_0x008a:
            java.lang.Error r4 = new java.lang.Error
            java.lang.String r6 = "unknwon pattern opcode"
            r4.<init>(r6)
            throw r4
        L_0x0092:
            gnu.lists.LList r4 = gnu.lists.LList.Empty
            r0 = r39
            if (r0 != r4) goto L_0x009c
            r4 = 1
        L_0x0099:
            r42 = r8
            goto L_0x0056
        L_0x009c:
            r4 = 0
            goto L_0x0099
        L_0x009e:
            int r25 = r37 >> 1
            r26 = r39
            r18 = 0
        L_0x00a4:
            r0 = r26
            boolean r4 = r0 instanceof kawa.lang.SyntaxForm
            if (r4 == 0) goto L_0x00b1
            kawa.lang.SyntaxForm r26 = (kawa.lang.SyntaxForm) r26
            java.lang.Object r26 = r26.getDatum()
            goto L_0x00a4
        L_0x00b1:
            r0 = r18
            r1 = r25
            if (r0 != r1) goto L_0x00d1
            r4 = r37 & 1
            if (r4 != 0) goto L_0x00c5
            gnu.lists.LList r4 = gnu.lists.LList.Empty
            r0 = r26
            if (r0 == r4) goto L_0x00cb
        L_0x00c1:
            r4 = 0
            r42 = r8
            goto L_0x0056
        L_0x00c5:
            r0 = r26
            boolean r4 = r0 instanceof gnu.lists.Pair
            if (r4 != 0) goto L_0x00c1
        L_0x00cb:
            r37 = 0
            r42 = r8
            goto L_0x0002
        L_0x00d1:
            r0 = r26
            boolean r4 = r0 instanceof gnu.lists.Pair
            if (r4 == 0) goto L_0x00e0
            gnu.lists.Pair r26 = (gnu.lists.Pair) r26
            java.lang.Object r26 = r26.getCdr()
            int r18 = r18 + 1
            goto L_0x00a4
        L_0x00e0:
            r4 = 0
            r42 = r8
            goto L_0x0056
        L_0x00e5:
            r0 = r39
            boolean r4 = r0 instanceof gnu.lists.Pair
            if (r4 != 0) goto L_0x00f0
            r4 = 0
            r42 = r8
            goto L_0x0056
        L_0x00f0:
            r5 = r39
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            r4 = r38
            r6 = r40
            r7 = r41
            r9 = r43
            boolean r4 = r4.match_car(r5, r6, r7, r8, r9)
            if (r4 != 0) goto L_0x0107
            r4 = 0
            r42 = r8
            goto L_0x0056
        L_0x0107:
            int r42 = r8 + r37
            r37 = 0
            java.lang.Object r39 = r5.getCdr()
            goto L_0x0002
        L_0x0111:
            r13 = r8
            int r42 = r8 + r37
            r0 = r38
            java.lang.String r4 = r0.program
            int r8 = r42 + 1
            r0 = r42
            char r16 = r4.charAt(r0)
            int r34 = r16 >> 3
        L_0x0122:
            r4 = r16 & 7
            r6 = 1
            if (r4 != r6) goto L_0x013a
            int r4 = r34 << 13
            r0 = r38
            java.lang.String r6 = r0.program
            int r42 = r8 + 1
            char r16 = r6.charAt(r8)
            int r6 = r16 >> 3
            r34 = r4 | r6
            r8 = r42
            goto L_0x0122
        L_0x013a:
            int r34 = r34 + r41
            r0 = r38
            java.lang.String r4 = r0.program
            int r42 = r8 + 1
            char r4 = r4.charAt(r8)
            int r35 = r4 >> 3
            r8 = r42
        L_0x014a:
            r4 = r16 & 7
            r6 = 1
            if (r4 != r6) goto L_0x0162
            int r4 = r35 << 13
            r0 = r38
            java.lang.String r6 = r0.program
            int r42 = r8 + 1
            char r16 = r6.charAt(r8)
            int r6 = r16 >> 3
            r35 = r4 | r6
            r8 = r42
            goto L_0x014a
        L_0x0162:
            r0 = r38
            java.lang.String r4 = r0.program
            int r42 = r8 + 1
            char r16 = r4.charAt(r8)
            r22 = 1
            r4 = 8
            r0 = r16
            if (r0 != r4) goto L_0x018b
            r28 = 0
        L_0x0176:
            int r29 = kawa.lang.Translator.listLength(r39)
            if (r29 < 0) goto L_0x01b2
            r23 = 1
        L_0x017e:
            r0 = r29
            r1 = r28
            if (r0 < r1) goto L_0x0188
            if (r22 == 0) goto L_0x01b7
            if (r23 != 0) goto L_0x01b7
        L_0x0188:
            r4 = 0
            goto L_0x0056
        L_0x018b:
            int r37 = r16 >> 3
            r8 = r42
        L_0x018f:
            r4 = r16 & 7
            r6 = 1
            if (r4 != r6) goto L_0x01a7
            int r4 = r37 << 13
            r0 = r38
            java.lang.String r6 = r0.program
            int r42 = r8 + 1
            char r16 = r6.charAt(r8)
            int r6 = r16 >> 3
            r37 = r4 | r6
            r8 = r42
            goto L_0x018f
        L_0x01a7:
            r4 = r37 & 1
            if (r4 == 0) goto L_0x01ad
            r22 = 0
        L_0x01ad:
            int r28 = r37 >> 1
            r42 = r8
            goto L_0x0176
        L_0x01b2:
            r23 = 0
            int r29 = -1 - r29
            goto L_0x017e
        L_0x01b7:
            int r30 = r29 - r28
            r0 = r35
            java.lang.Object[][] r15 = new java.lang.Object[r0][]
            r21 = 0
        L_0x01bf:
            r0 = r21
            r1 = r35
            if (r0 >= r1) goto L_0x01ce
            r0 = r30
            java.lang.Object[] r4 = new java.lang.Object[r0]
            r15[r21] = r4
            int r21 = r21 + 1
            goto L_0x01bf
        L_0x01ce:
            r18 = 0
        L_0x01d0:
            r0 = r18
            r1 = r30
            if (r0 >= r1) goto L_0x0215
        L_0x01d6:
            r0 = r39
            boolean r4 = r0 instanceof kawa.lang.SyntaxForm
            if (r4 == 0) goto L_0x01e5
            r43 = r39
            kawa.lang.SyntaxForm r43 = (kawa.lang.SyntaxForm) r43
            java.lang.Object r39 = r43.getDatum()
            goto L_0x01d6
        L_0x01e5:
            r5 = r39
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            r9 = r38
            r10 = r5
            r11 = r40
            r12 = r41
            r14 = r43
            boolean r4 = r9.match_car(r10, r11, r12, r13, r14)
            if (r4 != 0) goto L_0x01fb
            r4 = 0
            goto L_0x0056
        L_0x01fb:
            java.lang.Object r39 = r5.getCdr()
            r21 = 0
        L_0x0201:
            r0 = r21
            r1 = r35
            if (r0 >= r1) goto L_0x0212
            r4 = r15[r21]
            int r6 = r34 + r21
            r6 = r40[r6]
            r4[r18] = r6
            int r21 = r21 + 1
            goto L_0x0201
        L_0x0212:
            int r18 = r18 + 1
            goto L_0x01d0
        L_0x0215:
            r21 = 0
        L_0x0217:
            r0 = r21
            r1 = r35
            if (r0 >= r1) goto L_0x0226
            int r4 = r34 + r21
            r6 = r15[r21]
            r40[r4] = r6
            int r21 = r21 + 1
            goto L_0x0217
        L_0x0226:
            r37 = 0
            if (r28 != 0) goto L_0x0002
            if (r22 == 0) goto L_0x0002
            r4 = 1
            goto L_0x0056
        L_0x022f:
            r0 = r38
            java.lang.Object[] r4 = r0.literals
            r24 = r4[r37]
            gnu.expr.Compilation r36 = gnu.expr.Compilation.getCurrent()
            kawa.lang.Translator r36 = (kawa.lang.Translator) r36
            r0 = r24
            boolean r4 = r0 instanceof kawa.lang.SyntaxForm
            if (r4 == 0) goto L_0x026f
            r33 = r24
            kawa.lang.SyntaxForm r33 = (kawa.lang.SyntaxForm) r33
            java.lang.Object r19 = r33.getDatum()
            kawa.lang.TemplateScope r31 = r33.getScope()
        L_0x024d:
            r0 = r39
            boolean r4 = r0 instanceof kawa.lang.SyntaxForm
            if (r4 == 0) goto L_0x0285
            r33 = r39
            kawa.lang.SyntaxForm r33 = (kawa.lang.SyntaxForm) r33
            java.lang.Object r20 = r33.getDatum()
            kawa.lang.TemplateScope r32 = r33.getScope()
        L_0x025f:
            r0 = r19
            r1 = r31
            r2 = r20
            r3 = r32
            boolean r4 = literalIdentifierEq(r0, r1, r2, r3)
            r42 = r8
            goto L_0x0056
        L_0x026f:
            r19 = r24
            kawa.lang.Syntax r17 = r36.getCurrentSyntax()
            r0 = r17
            boolean r4 = r0 instanceof kawa.lang.Macro
            if (r4 == 0) goto L_0x0282
            kawa.lang.Macro r17 = (kawa.lang.Macro) r17
            gnu.expr.ScopeExp r31 = r17.getCapturedScope()
        L_0x0281:
            goto L_0x024d
        L_0x0282:
            r31 = 0
            goto L_0x0281
        L_0x0285:
            r20 = r39
            if (r43 != 0) goto L_0x028e
            gnu.expr.ScopeExp r32 = r36.currentScope()
        L_0x028d:
            goto L_0x025f
        L_0x028e:
            kawa.lang.TemplateScope r32 = r43.getScope()
            goto L_0x028d
        L_0x0293:
            if (r43 == 0) goto L_0x029d
            r0 = r39
            r1 = r43
            java.lang.Object r39 = kawa.lang.SyntaxForms.fromDatum(r0, r1)
        L_0x029d:
            int r4 = r41 + r37
            r40[r4] = r39
            r4 = 1
            r42 = r8
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.SyntaxPattern.match(java.lang.Object, java.lang.Object[], int, int, kawa.lang.SyntaxForm):boolean");
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.program);
        out.writeObject(this.literals);
        out.writeInt(this.varCount);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.literals = (Object[]) in.readObject();
        this.program = (String) in.readObject();
        this.varCount = in.readInt();
    }

    public static Object[] allocVars(int varCount2, Object[] outer) {
        Object[] vars = new Object[varCount2];
        if (outer != null) {
            System.arraycopy(outer, 0, vars, 0, outer.length);
        }
        return vars;
    }

    public static boolean literalIdentifierEq(Object id1, ScopeExp sc1, Object id2, ScopeExp sc2) {
        if (id1 != id2 && (id1 == null || id2 == null || !id1.equals(id2))) {
            return false;
        }
        if (sc1 == sc2) {
            return true;
        }
        Declaration d1 = null;
        Declaration d2 = null;
        while (sc1 != null && !(sc1 instanceof ModuleExp)) {
            d1 = sc1.lookup(id1);
            if (d1 != null) {
                break;
            }
            sc1 = sc1.outer;
        }
        while (sc2 != null && !(sc2 instanceof ModuleExp)) {
            d2 = sc2.lookup(id2);
            if (d2 != null) {
                break;
            }
            sc2 = sc2.outer;
        }
        if (d1 != d2) {
            return false;
        }
        return true;
    }

    public static Object[] getLiteralsList(Object list, SyntaxForm syntax, Translator tr) {
        Object wrapped;
        Object savePos = tr.pushPositionOf(list);
        int count = Translator.listLength(list);
        if (count < 0) {
            tr.error('e', "missing or malformed literals list");
            count = 0;
        }
        Object[] literals2 = new Object[(count + 1)];
        for (int i = 1; i <= count; i++) {
            while (list instanceof SyntaxForm) {
                list = ((SyntaxForm) list).getDatum();
            }
            Pair pair = (Pair) list;
            tr.pushPositionOf(pair);
            Object literal = pair.getCar();
            if (literal instanceof SyntaxForm) {
                wrapped = literal;
                literal = ((SyntaxForm) literal).getDatum();
            } else {
                wrapped = literal;
            }
            if (!(literal instanceof Symbol)) {
                tr.error('e', "non-symbol '" + literal + "' in literals list");
            }
            literals2[i] = wrapped;
            list = pair.getCdr();
        }
        tr.popPositionOf(savePos);
        return literals2;
    }

    public void print(Consumer out) {
        out.write("#<syntax-pattern>");
    }
}
