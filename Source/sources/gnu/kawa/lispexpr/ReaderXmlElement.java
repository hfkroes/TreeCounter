package gnu.kawa.lispexpr;

import gnu.expr.Compilation;
import gnu.expr.PrimProcedure;
import gnu.expr.Special;
import gnu.kawa.xml.CommentConstructor;
import gnu.kawa.xml.MakeCDATA;
import gnu.kawa.xml.MakeProcInst;
import gnu.kawa.xml.MakeText;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Namespace;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import gnu.xml.XName;
import java.io.IOException;

public class ReaderXmlElement extends ReadTableEntry {
    static final String DEFAULT_ELEMENT_NAMESPACE = "[default-element-namespace]";

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        LispReader reader = (LispReader) in;
        return readXMLConstructor(reader, reader.readUnicodeChar(), false);
    }

    public static Pair quote(Object obj) {
        return LList.list2(Namespace.EmptyNamespace.getSymbol(LispLanguage.quote_sym), obj);
    }

    public static Object readQNameExpression(LispReader reader, int ch, boolean forElement) throws IOException, SyntaxException {
        String prefix;
        String name = reader.getName();
        int line = reader.getLineNumber() + 1;
        int column = reader.getColumnNumber();
        reader.tokenBufferLength = 0;
        if (XName.isNameStart(ch)) {
            int colon = -1;
            while (true) {
                reader.tokenBufferAppend(ch);
                ch = reader.readUnicodeChar();
                if (ch == 58 && colon < 0) {
                    colon = reader.tokenBufferLength;
                } else if (!XName.isNamePart(ch)) {
                    break;
                }
            }
            reader.unread(ch);
            if (colon < 0 && !forElement) {
                return quote(Namespace.getDefaultSymbol(reader.tokenBufferString().intern()));
            }
            String local = new String(reader.tokenBuffer, colon + 1, (reader.tokenBufferLength - colon) - 1).intern();
            if (colon < 0) {
                prefix = DEFAULT_ELEMENT_NAMESPACE;
            } else {
                prefix = new String(reader.tokenBuffer, 0, colon).intern();
            }
            return new Pair(ResolveNamespace.resolveQName, PairWithPosition.make(Namespace.EmptyNamespace.getSymbol(prefix), new Pair(local, LList.Empty), reader.getName(), line, column));
        } else if (ch == 123 || ch == 40) {
            return readEscapedExpression(reader, ch);
        } else {
            reader.error("missing element name");
            return null;
        }
    }

    static Object readEscapedExpression(LispReader reader, int ch) throws IOException, SyntaxException {
        if (ch == 40) {
            reader.unread(ch);
            return reader.readObject();
        }
        LineBufferedReader port = reader.getPort();
        char saveReadState = reader.pushNesting('{');
        int startLine = port.getLineNumber();
        int startColumn = port.getColumnNumber();
        try {
            Pair list = reader.makePair(new PrimProcedure(Compilation.typeValues.getDeclaredMethod("values", 1)), startLine, startColumn);
            Pair last = list;
            ReadTable readTable = ReadTable.getCurrent();
            while (true) {
                int line = port.getLineNumber();
                int column = port.getColumnNumber();
                int ch2 = port.read();
                if (ch2 == 125) {
                    break;
                }
                if (ch2 < 0) {
                    reader.eofError("unexpected EOF in list starting here", startLine + 1, startColumn);
                }
                Object value = reader.readValues(ch2, readTable.lookup(ch2), readTable);
                if (value != Values.empty) {
                    Pair pair = reader.makePair(reader.handlePostfix(value, readTable, line, column), line, column);
                    reader.setCdr(last, pair);
                    last = pair;
                }
            }
            reader.tokenBufferLength = 0;
            if (last == list.getCdr()) {
                return last.getCar();
            }
            reader.popNesting(saveReadState);
            return list;
        } finally {
            reader.popNesting(saveReadState);
        }
    }

    static Object readXMLConstructor(LispReader reader, int next, boolean inElementContent) throws IOException, SyntaxException {
        int startLine = reader.getLineNumber() + 1;
        int startColumn = reader.getColumnNumber() - 2;
        if (next == 33) {
            int next2 = reader.read();
            if (next2 == 45) {
                next2 = reader.peek();
                if (next2 == 45) {
                    reader.skip();
                    if (!reader.readDelimited("-->")) {
                        reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file in XML comment starting here - expected \"-->\"");
                    }
                    return LList.list2(CommentConstructor.commentConstructor, reader.tokenBufferString());
                }
            }
            if (next2 == 91) {
                next2 = reader.read();
                if (next2 == 67) {
                    next2 = reader.read();
                    if (next2 == 68) {
                        next2 = reader.read();
                        if (next2 == 65) {
                            next2 = reader.read();
                            if (next2 == 84) {
                                next2 = reader.read();
                                if (next2 == 65) {
                                    next2 = reader.read();
                                    if (next2 == 91) {
                                        if (!reader.readDelimited("]]>")) {
                                            reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file in CDATA starting here - expected \"]]>\"");
                                        }
                                        return LList.list2(MakeCDATA.makeCDATA, reader.tokenBufferString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            reader.error('f', reader.getName(), startLine, startColumn, "'<!' must be followed by '--' or '[CDATA['");
            while (next2 >= 0 && next2 != 62 && next2 != 10 && next2 != 13) {
                next2 = reader.read();
            }
            return null;
        } else if (next != 63) {
            return readElementConstructor(reader, next);
        } else {
            int next3 = reader.readUnicodeChar();
            if (next3 < 0 || !XName.isNameStart(next3)) {
                reader.error("missing target after '<?'");
            }
            do {
                reader.tokenBufferAppend(next3);
                next3 = reader.readUnicodeChar();
            } while (XName.isNamePart(next3));
            String target = reader.tokenBufferString();
            int nspaces = 0;
            while (next3 >= 0 && Character.isWhitespace(next3)) {
                nspaces++;
                next3 = reader.read();
            }
            reader.unread(next3);
            char saveReadState = reader.pushNesting('?');
            try {
                if (!reader.readDelimited("?>")) {
                    reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file looking for \"?>\"");
                }
                if (nspaces == 0 && reader.tokenBufferLength > 0) {
                    reader.error("target must be followed by space or '?>'");
                }
                return LList.list3(MakeProcInst.makeProcInst, target, reader.tokenBufferString());
            } finally {
                reader.popNesting(saveReadState);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r18v0, types: [gnu.lists.LList] */
    /* JADX WARNING: type inference failed for: r18v1, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r18v3 */
    /* JADX WARNING: type inference failed for: r0v34, types: [gnu.lists.PairWithPosition] */
    /* JADX WARNING: type inference failed for: r1v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r18v4 */
    /* JADX WARNING: type inference failed for: r18v5 */
    /* JADX WARNING: type inference failed for: r18v6 */
    /* JADX WARNING: type inference failed for: r18v7 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r18v3
      assigns: []
      uses: []
      mth insns count: 211
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object readElementConstructor(gnu.kawa.lispexpr.LispReader r29, int r30) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            int r3 = r29.getLineNumber()
            int r25 = r3 + 1
            int r3 = r29.getColumnNumber()
            int r24 = r3 + -2
            r3 = 1
            r0 = r29
            r1 = r30
            java.lang.Object r27 = readQNameExpression(r0, r1, r3)
            r0 = r29
            int r3 = r0.tokenBufferLength
            if (r3 != 0) goto L_0x0048
            r26 = 0
        L_0x001d:
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            java.lang.String r4 = r29.getName()
            r0 = r27
            r1 = r25
            r2 = r24
            gnu.lists.PairWithPosition r28 = gnu.lists.PairWithPosition.make(r0, r3, r4, r1, r2)
            r22 = r28
            gnu.lists.LList r18 = gnu.lists.LList.Empty
            r20 = 0
        L_0x0033:
            r23 = 0
            int r30 = r29.readUnicodeChar()
        L_0x0039:
            if (r30 < 0) goto L_0x004d
            boolean r3 = java.lang.Character.isWhitespace(r30)
            if (r3 == 0) goto L_0x004d
            int r30 = r29.read()
            r23 = 1
            goto L_0x0039
        L_0x0048:
            java.lang.String r26 = r29.tokenBufferString()
            goto L_0x001d
        L_0x004d:
            if (r30 < 0) goto L_0x005b
            r3 = 62
            r0 = r30
            if (r0 == r3) goto L_0x005b
            r3 = 47
            r0 = r30
            if (r0 != r3) goto L_0x007f
        L_0x005b:
            r15 = 0
            r3 = 47
            r0 = r30
            if (r0 != r3) goto L_0x006d
            int r30 = r29.read()
            r3 = 62
            r0 = r30
            if (r0 != r3) goto L_0x017f
            r15 = 1
        L_0x006d:
            if (r15 != 0) goto L_0x020e
            r3 = 62
            r0 = r30
            if (r0 == r3) goto L_0x0184
            java.lang.String r3 = "missing '>' after start element"
            r0 = r29
            r0.error(r3)
            java.lang.Boolean r3 = java.lang.Boolean.FALSE
        L_0x007e:
            return r3
        L_0x007f:
            if (r23 != 0) goto L_0x0088
            java.lang.String r3 = "missing space before attribute"
            r0 = r29
            r0.error(r3)
        L_0x0088:
            r3 = 0
            r0 = r29
            r1 = r30
            java.lang.Object r10 = readQNameExpression(r0, r1, r3)
            int r3 = r29.getLineNumber()
            int r17 = r3 + 1
            int r3 = r29.getColumnNumber()
            int r3 = r3 + 1
            r0 = r29
            int r4 = r0.tokenBufferLength
            int r13 = r3 - r4
            r14 = 0
            r0 = r29
            int r3 = r0.tokenBufferLength
            r4 = 5
            if (r3 < r4) goto L_0x00eb
            r0 = r29
            char[] r3 = r0.tokenBuffer
            r4 = 0
            char r3 = r3[r4]
            r4 = 120(0x78, float:1.68E-43)
            if (r3 != r4) goto L_0x00eb
            r0 = r29
            char[] r3 = r0.tokenBuffer
            r4 = 1
            char r3 = r3[r4]
            r4 = 109(0x6d, float:1.53E-43)
            if (r3 != r4) goto L_0x00eb
            r0 = r29
            char[] r3 = r0.tokenBuffer
            r4 = 2
            char r3 = r3[r4]
            r4 = 108(0x6c, float:1.51E-43)
            if (r3 != r4) goto L_0x00eb
            r0 = r29
            char[] r3 = r0.tokenBuffer
            r4 = 3
            char r3 = r3[r4]
            r4 = 110(0x6e, float:1.54E-43)
            if (r3 != r4) goto L_0x00eb
            r0 = r29
            char[] r3 = r0.tokenBuffer
            r4 = 4
            char r3 = r3[r4]
            r4 = 115(0x73, float:1.61E-43)
            if (r3 != r4) goto L_0x00eb
            r0 = r29
            int r3 = r0.tokenBufferLength
            r4 = 5
            if (r3 != r4) goto L_0x014d
            java.lang.String r14 = ""
        L_0x00eb:
            r3 = 32
            r0 = r29
            int r30 = skipSpace(r0, r3)
            r3 = 61
            r0 = r30
            if (r0 == r3) goto L_0x0100
            java.lang.String r3 = "missing '=' after attribute"
            r0 = r29
            r0.error(r3)
        L_0x0100:
            r3 = 32
            r0 = r29
            int r30 = skipSpace(r0, r3)
            gnu.kawa.xml.MakeAttribute r3 = gnu.kawa.xml.MakeAttribute.makeAttribute
            gnu.lists.LList r4 = gnu.lists.LList.Empty
            java.lang.String r5 = r29.getName()
            r0 = r25
            r1 = r24
            gnu.lists.PairWithPosition r9 = gnu.lists.PairWithPosition.make(r3, r4, r5, r0, r1)
            r12 = r9
            gnu.lists.LList r3 = gnu.lists.LList.Empty
            java.lang.String r4 = r29.getName()
            r0 = r25
            r1 = r24
            gnu.lists.PairWithPosition r11 = gnu.lists.PairWithPosition.make(r10, r3, r4, r0, r1)
            r0 = r29
            r0.setCdr(r12, r11)
            r12 = r11
            r0 = r30
            char r3 = (char) r0
            r0 = r29
            gnu.lists.Pair r12 = readContent(r0, r3, r12)
            if (r14 == 0) goto L_0x0169
            gnu.lists.PairWithPosition r19 = new gnu.lists.PairWithPosition
            java.lang.Object r3 = r11.getCdr()
            gnu.lists.Pair r3 = gnu.lists.Pair.make(r14, r3)
            r0 = r19
            r1 = r18
            r0.<init>(r11, r3, r1)
            r18 = r19
            goto L_0x0033
        L_0x014d:
            r0 = r29
            char[] r3 = r0.tokenBuffer
            r4 = 5
            char r3 = r3[r4]
            r4 = 58
            if (r3 != r4) goto L_0x00eb
            java.lang.String r14 = new java.lang.String
            r0 = r29
            char[] r3 = r0.tokenBuffer
            r4 = 6
            r0 = r29
            int r5 = r0.tokenBufferLength
            int r5 = r5 + -6
            r14.<init>(r3, r4, r5)
            goto L_0x00eb
        L_0x0169:
            java.lang.Object r3 = r29.makeNil()
            r4 = 0
            r5 = -1
            r6 = -1
            gnu.lists.PairWithPosition r21 = gnu.lists.PairWithPosition.make(r9, r3, r4, r5, r6)
            r0 = r22
            r1 = r21
            r0.setCdrBackdoor(r1)
            r22 = r21
            goto L_0x0033
        L_0x017f:
            r29.unread(r30)
            goto L_0x006d
        L_0x0184:
            r3 = 60
            r0 = r29
            r1 = r22
            gnu.lists.Pair r22 = readContent(r0, r3, r1)
            int r30 = r29.readUnicodeChar()
            boolean r3 = gnu.xml.XName.isNameStart(r30)
            if (r3 == 0) goto L_0x01fd
            r3 = 0
            r0 = r29
            r0.tokenBufferLength = r3
        L_0x019d:
            r29.tokenBufferAppend(r30)
            int r30 = r29.readUnicodeChar()
            boolean r3 = gnu.xml.XName.isNamePart(r30)
            if (r3 != 0) goto L_0x019d
            r3 = 58
            r0 = r30
            if (r0 == r3) goto L_0x019d
            java.lang.String r16 = r29.tokenBufferString()
            if (r26 == 0) goto L_0x01c0
            r0 = r16
            r1 = r26
            boolean r3 = r0.equals(r1)
            if (r3 != 0) goto L_0x01f8
        L_0x01c0:
            r4 = 101(0x65, float:1.42E-43)
            java.lang.String r5 = r29.getName()
            int r3 = r29.getLineNumber()
            int r6 = r3 + 1
            int r3 = r29.getColumnNumber()
            r0 = r29
            int r7 = r0.tokenBufferLength
            int r7 = r3 - r7
            if (r26 != 0) goto L_0x022a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r8 = "computed start tag closed by '</"
            java.lang.StringBuilder r3 = r3.append(r8)
            r0 = r16
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r8 = ">'"
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.String r8 = r3.toString()
        L_0x01f3:
            r3 = r29
            r3.error(r4, r5, r6, r7, r8)
        L_0x01f8:
            r3 = 0
            r0 = r29
            r0.tokenBufferLength = r3
        L_0x01fd:
            int r30 = skipSpace(r29, r30)
            r3 = 62
            r0 = r30
            if (r0 == r3) goto L_0x020e
            java.lang.String r3 = "missing '>' after end element"
            r0 = r29
            r0.error(r3)
        L_0x020e:
            gnu.lists.LList r18 = gnu.lists.LList.reverseInPlace(r18)
            gnu.kawa.lispexpr.MakeXmlElement r3 = gnu.kawa.lispexpr.MakeXmlElement.makeXml
            r0 = r18
            r1 = r28
            gnu.lists.Pair r4 = gnu.lists.Pair.make(r0, r1)
            java.lang.String r5 = r29.getName()
            r0 = r25
            r1 = r24
            gnu.lists.PairWithPosition r3 = gnu.lists.PairWithPosition.make(r3, r4, r5, r0, r1)
            goto L_0x007e
        L_0x022a:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r8 = "'<"
            java.lang.StringBuilder r3 = r3.append(r8)
            r0 = r26
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r8 = ">' closed by '</"
            java.lang.StringBuilder r3 = r3.append(r8)
            r0 = r16
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r8 = ">'"
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.String r8 = r3.toString()
            goto L_0x01f3
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReaderXmlElement.readElementConstructor(gnu.kawa.lispexpr.LispReader, int):java.lang.Object");
    }

    public static Pair readContent(LispReader reader, char delimiter, Pair resultTail) throws IOException, SyntaxException {
        reader.tokenBufferLength = 0;
        boolean prevWasEnclosed = false;
        while (true) {
            Object item = null;
            String text = null;
            int line = reader.getLineNumber() + 1;
            int column = reader.getColumnNumber();
            int next = reader.read();
            if (next < 0) {
                reader.error("unexpected end-of-file");
                item = Special.eof;
            } else if (next == delimiter) {
                if (delimiter == '<') {
                    if (reader.tokenBufferLength > 0) {
                        text = reader.tokenBufferString();
                        reader.tokenBufferLength = 0;
                    }
                    int next2 = reader.read();
                    if (next2 == 47) {
                        item = Special.eof;
                    } else {
                        item = readXMLConstructor(reader, next2, true);
                    }
                } else if (reader.checkNext(delimiter)) {
                    reader.tokenBufferAppend(delimiter);
                } else {
                    item = Special.eof;
                }
                prevWasEnclosed = false;
            } else if (next == 38) {
                int next3 = reader.read();
                if (next3 == 35) {
                    readCharRef(reader);
                } else if (next3 == 40 || next3 == 123) {
                    if (reader.tokenBufferLength > 0 || prevWasEnclosed) {
                        text = reader.tokenBufferString();
                    }
                    reader.tokenBufferLength = 0;
                    item = readEscapedExpression(reader, next3);
                } else {
                    item = readEntity(reader, next3);
                    if (prevWasEnclosed && reader.tokenBufferLength == 0) {
                        text = "";
                    }
                }
                prevWasEnclosed = true;
            } else {
                if (delimiter != '<' && (next == 9 || next == 10 || next == 13)) {
                    next = 32;
                }
                if (next == 60) {
                    reader.error('e', "'<' must be quoted in a direct attribute value");
                }
                reader.tokenBufferAppend((char) next);
            }
            if (item != null && reader.tokenBufferLength > 0) {
                text = reader.tokenBufferString();
                reader.tokenBufferLength = 0;
            }
            if (text != null) {
                Pair pair = PairWithPosition.make(Pair.list2(MakeText.makeText, text), reader.makeNil(), null, -1, -1);
                resultTail.setCdrBackdoor(pair);
                resultTail = pair;
            }
            if (item == Special.eof) {
                return resultTail;
            }
            if (item != null) {
                Pair pair2 = PairWithPosition.make(item, reader.makeNil(), null, line, column);
                resultTail.setCdrBackdoor(pair2);
                resultTail = pair2;
            }
        }
    }

    static void readCharRef(LispReader reader) throws IOException, SyntaxException {
        int base;
        int next = reader.read();
        if (next == 120) {
            base = 16;
            next = reader.read();
        } else {
            base = 10;
        }
        int value = 0;
        while (next >= 0) {
            int digit = Character.digit((char) next, base);
            if (digit < 0 || value >= 134217728) {
                break;
            }
            value = (value * base) + digit;
            next = reader.read();
        }
        if (next != 59) {
            reader.unread(next);
            reader.error("invalid character reference");
        } else if ((value <= 0 || value > 55295) && ((value < 57344 || value > 65533) && (value < 65536 || value > 1114111))) {
            reader.error("invalid character value " + value);
        } else {
            reader.tokenBufferAppend(value);
        }
    }

    static Object readEntity(LispReader reader, int next) throws IOException, SyntaxException {
        String result = "?";
        int saveLength = reader.tokenBufferLength;
        while (next >= 0) {
            char ch = (char) next;
            if (!XName.isNamePart(ch)) {
                break;
            }
            reader.tokenBufferAppend(ch);
            next = reader.read();
        }
        if (next != 59) {
            reader.unread(next);
            reader.error("invalid entity reference");
            return result;
        }
        String ref = new String(reader.tokenBuffer, saveLength, reader.tokenBufferLength - saveLength);
        reader.tokenBufferLength = saveLength;
        namedEntity(reader, ref);
        return null;
    }

    public static void namedEntity(LispReader reader, String name) {
        String name2 = name.intern();
        char ch = '?';
        if (name2 == "lt") {
            ch = '<';
        } else if (name2 == "gt") {
            ch = '>';
        } else if (name2 == "amp") {
            ch = '&';
        } else if (name2 == "quot") {
            ch = '\"';
        } else if (name2 == "apos") {
            ch = '\'';
        } else {
            reader.error("unknown enity reference: '" + name2 + "'");
        }
        reader.tokenBufferAppend(ch);
    }

    static int skipSpace(LispReader reader, int ch) throws IOException, SyntaxException {
        while (ch >= 0 && Character.isWhitespace(ch)) {
            ch = reader.readUnicodeChar();
        }
        return ch;
    }
}
