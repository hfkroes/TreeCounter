package gnu.kawa.functions;

import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.text.BreakIterator;

public class UnicodeUtils {

    /* renamed from: Cc */
    static final Symbol f60Cc;

    /* renamed from: Cf */
    static final Symbol f61Cf;

    /* renamed from: Cn */
    static final Symbol f62Cn;

    /* renamed from: Co */
    static final Symbol f63Co;

    /* renamed from: Cs */
    static final Symbol f64Cs;

    /* renamed from: Ll */
    static final Symbol f65Ll;

    /* renamed from: Lm */
    static final Symbol f66Lm;

    /* renamed from: Lo */
    static final Symbol f67Lo;

    /* renamed from: Lt */
    static final Symbol f68Lt;

    /* renamed from: Lu */
    static final Symbol f69Lu;

    /* renamed from: Mc */
    static final Symbol f70Mc;

    /* renamed from: Me */
    static final Symbol f71Me;

    /* renamed from: Mn */
    static final Symbol f72Mn;

    /* renamed from: Nd */
    static final Symbol f73Nd;

    /* renamed from: Nl */
    static final Symbol f74Nl;

    /* renamed from: No */
    static final Symbol f75No;

    /* renamed from: Pc */
    static final Symbol f76Pc;

    /* renamed from: Pd */
    static final Symbol f77Pd;

    /* renamed from: Pe */
    static final Symbol f78Pe;

    /* renamed from: Pf */
    static final Symbol f79Pf;

    /* renamed from: Pi */
    static final Symbol f80Pi;

    /* renamed from: Po */
    static final Symbol f81Po;

    /* renamed from: Ps */
    static final Symbol f82Ps;

    /* renamed from: Sc */
    static final Symbol f83Sc;

    /* renamed from: Sk */
    static final Symbol f84Sk;

    /* renamed from: Sm */
    static final Symbol f85Sm;

    /* renamed from: So */
    static final Symbol f86So;

    /* renamed from: Zl */
    static final Symbol f87Zl;

    /* renamed from: Zp */
    static final Symbol f88Zp;

    /* renamed from: Zs */
    static final Symbol f89Zs;

    public static boolean isWhitespace(int ch) {
        if (ch == 32 || (ch >= 9 && ch <= 13)) {
            return true;
        }
        if (ch < 133) {
            return false;
        }
        if (ch == 133 || ch == 160 || ch == 5760 || ch == 6158) {
            return true;
        }
        if (ch < 8192 || ch > 12288) {
            return false;
        }
        if (ch <= 8202 || ch == 8232 || ch == 8233 || ch == 8239 || ch == 8287 || ch == 12288) {
            return true;
        }
        return false;
    }

    public static String capitalize(String str) {
        StringBuilder sbuf = new StringBuilder();
        BreakIterator wb = BreakIterator.getWordInstance();
        wb.setText(str);
        int start = wb.first();
        for (int end = wb.next(); end != -1; end = wb.next()) {
            boolean isWord = false;
            int p = start;
            while (true) {
                if (p >= end) {
                    break;
                } else if (Character.isLetter(str.codePointAt(p))) {
                    isWord = true;
                    break;
                } else {
                    p++;
                }
            }
            if (!isWord) {
                sbuf.append(str, start, end);
            } else {
                sbuf.append(Character.toTitleCase(str.charAt(start)));
                sbuf.append(str.substring(start + 1, end).toLowerCase());
            }
            start = end;
        }
        return sbuf.toString();
    }

    public static String foldCase(CharSequence str) {
        int len = str.length();
        if (len == 0) {
            return "";
        }
        StringBuilder sbuf = null;
        int start = 0;
        int i = 0;
        while (true) {
            int ch = i == len ? -1 : str.charAt(i);
            boolean sigma = ch == 931 || ch == 963 || ch == 962;
            if (ch < 0 || ch == 304 || ch == 305 || sigma) {
                if (sbuf == null && ch >= 0) {
                    sbuf = new StringBuilder();
                }
                if (i > start) {
                    String converted = str.subSequence(start, i).toString().toUpperCase().toLowerCase();
                    if (sbuf == null) {
                        return converted;
                    }
                    sbuf.append(converted);
                }
                if (ch < 0) {
                    return sbuf.toString();
                }
                if (sigma) {
                    ch = 963;
                }
                sbuf.append((char) ch);
                start = i + 1;
            }
            i++;
        }
    }

    public static Symbol generalCategory(int ch) {
        switch (Character.getType(ch)) {
            case 1:
                return f69Lu;
            case 2:
                return f65Ll;
            case 3:
                return f68Lt;
            case 4:
                return f66Lm;
            case 5:
                return f67Lo;
            case 6:
                return f72Mn;
            case 7:
                return f71Me;
            case 8:
                return f70Mc;
            case 9:
                return f73Nd;
            case 10:
                return f74Nl;
            case 11:
                return f75No;
            case 12:
                return f89Zs;
            case 13:
                return f87Zl;
            case 14:
                return f88Zp;
            case 15:
                return f60Cc;
            case 16:
                return f61Cf;
            case 18:
                return f63Co;
            case 19:
                return f64Cs;
            case 20:
                return f77Pd;
            case 21:
                return f82Ps;
            case 22:
                return f78Pe;
            case 23:
                return f76Pc;
            case 24:
                return f81Po;
            case 25:
                return f85Sm;
            case 26:
                return f83Sc;
            case 27:
                return f84Sk;
            case 28:
                return f86So;
            case 29:
                return f80Pi;
            case 30:
                return f79Pf;
            default:
                return f62Cn;
        }
    }

    static {
        Namespace empty = Namespace.EmptyNamespace;
        f70Mc = empty.getSymbol("Mc");
        f76Pc = empty.getSymbol("Pc");
        f60Cc = empty.getSymbol("Cc");
        f83Sc = empty.getSymbol("Sc");
        f77Pd = empty.getSymbol("Pd");
        f73Nd = empty.getSymbol("Nd");
        f71Me = empty.getSymbol("Me");
        f78Pe = empty.getSymbol("Pe");
        f79Pf = empty.getSymbol("Pf");
        f61Cf = empty.getSymbol("Cf");
        f80Pi = empty.getSymbol("Pi");
        f74Nl = empty.getSymbol("Nl");
        f87Zl = empty.getSymbol("Zl");
        f65Ll = empty.getSymbol("Ll");
        f85Sm = empty.getSymbol("Sm");
        f66Lm = empty.getSymbol("Lm");
        f84Sk = empty.getSymbol("Sk");
        f72Mn = empty.getSymbol("Mn");
        f67Lo = empty.getSymbol("Lo");
        f75No = empty.getSymbol("No");
        f81Po = empty.getSymbol("Po");
        f86So = empty.getSymbol("So");
        f88Zp = empty.getSymbol("Zp");
        f63Co = empty.getSymbol("Co");
        f89Zs = empty.getSymbol("Zs");
        f82Ps = empty.getSymbol("Ps");
        f64Cs = empty.getSymbol("Cs");
        f68Lt = empty.getSymbol("Lt");
        f62Cn = empty.getSymbol("Cn");
        f69Lu = empty.getSymbol("Lu");
    }
}
