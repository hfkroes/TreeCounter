package gnu.xquery.util;

import gnu.bytecode.ClassType;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.xml.Document;
import gnu.kawa.xml.KDocument;
import gnu.kawa.xml.KElement;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.Nodes;
import gnu.kawa.xml.SortedNodes;
import gnu.kawa.xml.UntypedAtomic;
import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.Path;
import gnu.xml.NamespaceBinding;
import gnu.xml.NodeTree;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import gnu.xquery.lang.XQuery;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Stack;

public class NodeUtils {
    static String collectionNamespace = "http://gnu.org/kawa/cached-collections";
    public static final Symbol collectionResolverSymbol = Symbol.make(XQuery.LOCAL_NAMESPACE, "collection-resolver", "qexo");

    public static Object nodeName(Object node) {
        if (node == Values.empty || node == null) {
            return node;
        }
        if (!(node instanceof KNode)) {
            throw new WrongType("node-name", 1, node, "node()?");
        }
        Symbol sym = ((KNode) node).getNodeSymbol();
        if (sym == null) {
            return Values.empty;
        }
        return sym;
    }

    public static String name(Object node) {
        if (node == Values.empty || node == null) {
            return "";
        }
        Object name = ((KNode) node).getNodeNameObject();
        if (name == null || name == Values.empty) {
            return "";
        }
        return name.toString();
    }

    public static String localName(Object node) {
        if (node == Values.empty || node == null) {
            return "";
        }
        if (!(node instanceof KNode)) {
            throw new WrongType("local-name", 1, node, "node()?");
        }
        Object name = ((KNode) node).getNodeNameObject();
        if (name == null || name == Values.empty) {
            return "";
        }
        if (name instanceof Symbol) {
            return ((Symbol) name).getName();
        }
        return name.toString();
    }

    public static Object namespaceURI(Object node) {
        if (!(node == Values.empty || node == null)) {
            if (!(node instanceof KNode)) {
                throw new WrongType("namespace-uri", 1, node, "node()?");
            }
            Object name = ((KNode) node).getNodeNameObject();
            if (name instanceof Symbol) {
                return QNameUtils.namespaceURIFromQName(name);
            }
        }
        return "";
    }

    public static void prefixesFromNodetype(XName name, Consumer out) {
        NamespaceBinding bindings = name.getNamespaceNodes();
        for (NamespaceBinding ns = bindings; ns != null; ns = ns.getNext()) {
            if (ns.getUri() != null) {
                String prefix = ns.getPrefix();
                NamespaceBinding ns2 = bindings;
                while (true) {
                    if (ns2 != ns) {
                        if (ns2.getPrefix() == prefix) {
                            break;
                        }
                        ns2 = ns2.getNext();
                    } else {
                        if (prefix == null) {
                            prefix = "";
                        }
                        out.writeObject(prefix);
                    }
                }
            }
        }
    }

    public static void inScopePrefixes$X(Object node, CallContext ctx) {
        Object type = ((KElement) node).getNodeNameObject();
        if (type instanceof XName) {
            prefixesFromNodetype((XName) type, ctx.consumer);
        } else {
            ctx.consumer.writeObject("xml");
        }
    }

    public static void data$X(Object arg, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (arg instanceof Values) {
            Values vals = (Values) arg;
            int ipos = vals.startPos();
            while (true) {
                ipos = vals.nextPos(ipos);
                if (ipos != 0) {
                    out.writeObject(KNode.atomicValue(vals.getPosPrevious(ipos)));
                } else {
                    return;
                }
            }
        } else {
            out.writeObject(KNode.atomicValue(arg));
        }
    }

    public static Object root(Object arg) {
        if (arg == null || arg == Values.empty) {
            return arg;
        }
        if (!(arg instanceof KNode)) {
            throw new WrongType("root", 1, arg, "node()?");
        }
        KNode node = (KNode) arg;
        return Nodes.root((NodeTree) node.sequence, node.getPos());
    }

    public static KDocument rootDocument(Object arg) {
        if (!(arg instanceof KNode)) {
            throw new WrongType("root-document", 1, arg, "node()?");
        }
        KNode node = (KNode) arg;
        KNode node2 = Nodes.root((NodeTree) node.sequence, node.getPos());
        if (node2 instanceof KDocument) {
            return (KDocument) node2;
        }
        throw new WrongType("root-document", 1, arg, "document()");
    }

    public static String getLang(KNode node) {
        NodeTree seq = (NodeTree) node.sequence;
        int attr = seq.ancestorAttribute(node.ipos, NamespaceBinding.XML_NAMESPACE, "lang");
        if (attr == 0) {
            return null;
        }
        return KNode.getNodeValue(seq, attr);
    }

    public static boolean lang(Object testlang, Object node) {
        String teststr;
        if (testlang == null || testlang == Values.empty) {
            teststr = "";
        } else {
            teststr = TextUtils.stringValue(testlang);
        }
        String lang = getLang((KNode) node);
        if (lang == null) {
            return false;
        }
        int langlen = lang.length();
        int testlen = teststr.length();
        if (langlen > testlen && lang.charAt(testlen) == '-') {
            lang = lang.substring(0, testlen);
        }
        return lang.equalsIgnoreCase(teststr);
    }

    public static Object documentUri(Object arg) {
        if (arg == null || arg == Values.empty) {
            return arg;
        }
        if (!(arg instanceof KNode)) {
            throw new WrongType("xs:document-uri", 1, arg, "node()?");
        }
        KNode node = (KNode) arg;
        Object uri = ((NodeTree) node.sequence).documentUriOfPos(node.ipos);
        return uri == null ? Values.empty : uri;
    }

    public static Object nilled(Object arg) {
        if (arg == null || arg == Values.empty) {
            return arg;
        }
        if (!(arg instanceof KNode)) {
            throw new WrongType("nilled", 1, arg, "node()?");
        } else if (!(arg instanceof KElement)) {
            return Values.empty;
        } else {
            return Boolean.FALSE;
        }
    }

    public static Object baseUri(Object arg) {
        if (arg == null || arg == Values.empty) {
            return arg;
        }
        if (!(arg instanceof KNode)) {
            throw new WrongType("base-uri", 1, arg, "node()?");
        }
        Path uri = ((KNode) arg).baseURI();
        if (uri == null) {
            return Values.empty;
        }
        return uri;
    }

    /* JADX WARNING: type inference failed for: r13v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r9v3 */
    /* JADX WARNING: type inference failed for: r9v4, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r13v1, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r9v5 */
    /* JADX WARNING: type inference failed for: r9v6 */
    /* JADX WARNING: type inference failed for: r5v0, types: [java.lang.Object, java.lang.String] */
    /* JADX WARNING: type inference failed for: r6v0, types: [java.util.Stack] */
    /* JADX WARNING: type inference failed for: r13v2 */
    /* JADX WARNING: type inference failed for: r9v7 */
    /* JADX WARNING: type inference failed for: r9v8 */
    /* JADX WARNING: type inference failed for: r6v1, types: [java.util.Stack] */
    /* JADX WARNING: type inference failed for: r6v2 */
    /* JADX WARNING: type inference failed for: r6v3, types: [java.util.Stack] */
    /* JADX WARNING: type inference failed for: r13v3 */
    /* JADX WARNING: type inference failed for: r9v9 */
    /* JADX WARNING: type inference failed for: r13v4, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r13v5, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r13v6 */
    /* JADX WARNING: type inference failed for: r9v11 */
    /* JADX WARNING: type inference failed for: r9v12 */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r9v13 */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: type inference failed for: r13v7 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Object, code=null, for r13v0, types: [java.lang.Object] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r9v5
      assigns: []
      uses: []
      mth insns count: 63
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 13 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.Object getIDs(java.lang.Object r12, java.lang.Object r13) {
        /*
            boolean r9 = r12 instanceof gnu.kawa.xml.KNode
            if (r9 == 0) goto L_0x0008
            java.lang.Object r12 = gnu.kawa.xml.KNode.atomicValue(r12)
        L_0x0008:
            boolean r9 = r12 instanceof gnu.mapping.Values
            if (r9 == 0) goto L_0x001e
            gnu.mapping.Values r12 = (gnu.mapping.Values) r12
            java.lang.Object[] r0 = r12.getValues()
            int r2 = r0.length
        L_0x0013:
            int r2 = r2 + -1
            if (r2 < 0) goto L_0x0085
            r9 = r0[r2]
            java.lang.Object r13 = getIDs(r9, r13)
            goto L_0x0013
        L_0x001e:
            java.lang.String r9 = "fn:id"
            r10 = 1
            java.lang.String r11 = ""
            java.lang.String r8 = gnu.xquery.util.StringUtils.coerceToString(r12, r9, r10, r11)
            int r4 = r8.length()
            r2 = 0
            r3 = r2
            r9 = r13
        L_0x002e:
            if (r3 >= r4) goto L_0x0084
            int r2 = r3 + 1
            char r1 = r8.charAt(r3)
            boolean r10 = java.lang.Character.isWhitespace(r1)
            if (r10 == 0) goto L_0x003e
            r3 = r2
            goto L_0x002e
        L_0x003e:
            boolean r10 = gnu.xml.XName.isNameStart(r1)
            if (r10 == 0) goto L_0x0060
            int r7 = r2 + -1
        L_0x0046:
            if (r2 >= r4) goto L_0x0052
            char r1 = r8.charAt(r2)
            boolean r10 = java.lang.Character.isWhitespace(r1)
            if (r10 == 0) goto L_0x0062
        L_0x0052:
            if (r7 >= r4) goto L_0x005c
            java.lang.String r5 = r8.substring(r7, r2)
            if (r9 != 0) goto L_0x006e
            r13 = r5
            r9 = r13
        L_0x005c:
            int r2 = r2 + 1
            r3 = r2
            goto L_0x002e
        L_0x0060:
            r7 = r4
            goto L_0x0046
        L_0x0062:
            int r2 = r2 + 1
            if (r7 >= r4) goto L_0x0046
            boolean r10 = gnu.xml.XName.isNamePart(r1)
            if (r10 != 0) goto L_0x0046
            r7 = r4
            goto L_0x0046
        L_0x006e:
            boolean r10 = r9 instanceof java.util.Stack
            if (r10 == 0) goto L_0x0079
            r6 = r9
            java.util.Stack r6 = (java.util.Stack) r6
        L_0x0075:
            r6.push(r5)
            goto L_0x005c
        L_0x0079:
            java.util.Stack r6 = new java.util.Stack
            r6.<init>()
            r6.push(r9)
            r13 = r6
            r9 = r13
            goto L_0x0075
        L_0x0084:
            r13 = r9
        L_0x0085:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.NodeUtils.getIDs(java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static void id$X(Object arg1, Object arg2, CallContext ctx) {
        KNode node = (KNode) arg2;
        NodeTree ntree = (NodeTree) node.sequence;
        KDocument kDocument = (KDocument) Nodes.root(ntree, node.ipos);
        Consumer out = ctx.consumer;
        Object idrefs = getIDs(arg1, null);
        if (idrefs != null) {
            ntree.makeIDtableIfNeeded();
            if ((out instanceof PositionConsumer) && ((idrefs instanceof String) || (out instanceof SortedNodes))) {
                idScan(idrefs, ntree, (PositionConsumer) out);
            } else if (idrefs instanceof String) {
                int pos = ntree.lookupID((String) idrefs);
                if (pos != -1) {
                    out.writeObject(KNode.make(ntree, pos));
                }
            } else {
                SortedNodes nodes = new SortedNodes();
                idScan(idrefs, ntree, nodes);
                Values.writeValues(nodes, out);
            }
        }
    }

    private static void idScan(Object ids, NodeTree seq, PositionConsumer out) {
        if (ids instanceof String) {
            int pos = seq.lookupID((String) ids);
            if (pos != -1) {
                out.writePosition(seq, pos);
            }
        } else if (ids instanceof Stack) {
            Stack st = (Stack) ids;
            int n = st.size();
            for (int i = 0; i < n; i++) {
                idScan(st.elementAt(i), seq, out);
            }
        }
    }

    public static Object idref(Object arg1, Object arg2) {
        KNode node = (KNode) arg2;
        KDocument kDocument = (KDocument) Nodes.root((NodeTree) node.sequence, node.getPos());
        return Values.empty;
    }

    public static void setSavedCollection(Object uri, Object value, Environment env) {
        if (uri == null) {
            uri = "#default";
        }
        env.put(Symbol.make(collectionNamespace, uri.toString()), null, value);
    }

    public static void setSavedCollection(Object uri, Object value) {
        setSavedCollection(uri, value, Environment.getCurrent());
    }

    public static Object getSavedCollection(Object uri, Environment env) {
        if (uri == null) {
            uri = "#default";
        }
        Object coll = env.get(Symbol.make(collectionNamespace, uri.toString()), null, null);
        if (coll != null) {
            return coll;
        }
        throw new RuntimeException("collection '" + uri + "' not found");
    }

    public static Object getSavedCollection(Object uri) {
        return getSavedCollection(uri, Environment.getCurrent());
    }

    public static Object collection(Object uri, Object base) throws Throwable {
        Object uri2 = resolve(uri, base, "collection");
        Environment env = Environment.getCurrent();
        Symbol rsym = collectionResolverSymbol;
        Object rvalue = env.get(rsym, null, null);
        if (rvalue == null) {
            rvalue = env.get(Symbol.makeWithUnknownNamespace(rsym.getLocalName(), rsym.getPrefix()), null, null);
        }
        if (rvalue == null) {
            return getSavedCollection(uri2);
        }
        if ((rvalue instanceof String) || (rvalue instanceof UntypedAtomic)) {
            String str = rvalue.toString();
            int colon = str.indexOf(58);
            if (colon > 0) {
                String cname = str.substring(0, colon);
                String mname = str.substring(colon + 1);
                try {
                    rvalue = ClassMethods.apply((ClassType) ClassType.make(Class.forName(cname)), mname, 0, XQuery.instance);
                    if (rvalue == null) {
                        throw new RuntimeException("invalid collection-resolver: no method " + mname + " in " + cname);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("invalid collection-resolver: class " + cname + " not found");
                } catch (Throwable ex) {
                    throw new RuntimeException("invalid collection-resolver: " + ex);
                }
            }
        }
        if (rvalue instanceof Procedure) {
            return ((Procedure) rvalue).apply1(uri2);
        }
        throw new RuntimeException("invalid collection-resolver: " + rvalue);
    }

    static Object resolve(Object uri, Object base, String fname) throws Throwable {
        if (!(uri instanceof File) && !(uri instanceof Path) && !(uri instanceof URI) && !(uri instanceof URL)) {
            uri = StringUtils.coerceToString(uri, fname, 1, null);
        }
        if (uri == Values.empty || uri == null) {
            return null;
        }
        return Path.currentPath().resolve(Path.valueOf(uri));
    }

    public static Object docCached(Object uri, Object base) throws Throwable {
        Object uri2 = resolve(uri, base, "doc");
        if (uri2 == null) {
            return Values.empty;
        }
        return Document.parseCached(uri2);
    }

    public static boolean availableCached(Object uri, Object base) throws Throwable {
        Object uri2 = resolve(uri, base, "doc-available");
        if (uri2 == null) {
            return false;
        }
        try {
            Document.parseCached(uri2);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }
}
