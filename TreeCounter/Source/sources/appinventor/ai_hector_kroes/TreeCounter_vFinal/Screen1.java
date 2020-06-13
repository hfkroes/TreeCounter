package appinventor.ai_hector_kroes.TreeCounter_vFinal;

import android.os.Bundle;
import android.support.p000v4.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Clock;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Spinner;
import com.google.appinventor.components.runtime.TableArrangement;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.TinyDB;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.errors.PermissionException;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.RetValManager;
import com.google.appinventor.components.runtime.util.RuntimeErrorAlert;
import com.google.youngandroid.C0607runtime;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lang.Promise;
import kawa.lib.C0619lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.require;
import org.acra.ACRAConstants;

/* compiled from: Screen1.yail */
public class Screen1 extends Form implements Runnable {
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Screen1").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("g$Day").readResolve());
    static final FString Lit100 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit101 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve());
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final IntNum Lit103 = IntNum.make(-1001);
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit105 = IntNum.make(-1005);
    static final FString Lit106 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit107 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final SimpleSymbol Lit108 = ((SimpleSymbol) new SimpleSymbol("TableArrangement3").readResolve());
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol("Columns").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("g$Month").readResolve());
    static final IntNum Lit110 = IntNum.make(-1010);
    static final IntNum Lit111 = IntNum.make(-1090);
    static final FString Lit112 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final FString Lit113 = new FString("com.google.appinventor.components.runtime.Spinner");
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("Column").readResolve());
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("ElementsFromString").readResolve());
    static final SimpleSymbol Lit116 = ((SimpleSymbol) new SimpleSymbol("Row").readResolve());
    static final IntNum Lit117 = IntNum.make(-1030);
    static final FString Lit118 = new FString("com.google.appinventor.components.runtime.Spinner");
    static final FString Lit119 = new FString("com.google.appinventor.components.runtime.Spinner");
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("g$Others").readResolve());
    static final IntNum Lit120 = IntNum.make(-1020);
    static final FString Lit121 = new FString("com.google.appinventor.components.runtime.Spinner");
    static final FString Lit122 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("Label7").readResolve());
    static final SimpleSymbol Lit124 = ((SimpleSymbol) new SimpleSymbol("FontBold").readResolve());
    static final SimpleSymbol Lit125 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final FString Lit126 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit127 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit128 = ((SimpleSymbol) new SimpleSymbol("Label8").readResolve());
    static final IntNum Lit129 = IntNum.make(-1015);
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("g$TTN").readResolve());
    static final FString Lit130 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit131 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit132 = ((SimpleSymbol) new SimpleSymbol("Label9").readResolve());
    static final FString Lit133 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit134 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("Label10").readResolve());
    static final FString Lit136 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit137 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit138 = ((SimpleSymbol) new SimpleSymbol("Label20").readResolve());
    static final IntNum Lit139 = IntNum.make(-1015);
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("g$Cel").readResolve());
    static final FString Lit140 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit141 = new FString("com.google.appinventor.components.runtime.Spinner");
    static final IntNum Lit142 = IntNum.make(-1020);
    static final FString Lit143 = new FString("com.google.appinventor.components.runtime.Spinner");
    static final FString Lit144 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final SimpleSymbol Lit145 = ((SimpleSymbol) new SimpleSymbol("TableArrangement4").readResolve());
    static final IntNum Lit146 = IntNum.make(-1011);
    static final IntNum Lit147 = IntNum.make(-1090);
    static final FString Lit148 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final FString Lit149 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("g$Comp").readResolve());
    static final SimpleSymbol Lit150 = ((SimpleSymbol) new SimpleSymbol("Label11").readResolve());
    static final FString Lit151 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit152 = new FString("com.google.appinventor.components.runtime.Spinner");
    static final IntNum Lit153 = IntNum.make(-1044);
    static final FString Lit154 = new FString("com.google.appinventor.components.runtime.Spinner");
    static final PairWithPosition Lit155 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 876661), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 876656);
    static final PairWithPosition Lit156 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 876849), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 876844);
    static final IntNum Lit157 = IntNum.make(2400);
    static final SimpleSymbol Lit158 = ((SimpleSymbol) new SimpleSymbol("StoreValue").readResolve());
    static final PairWithPosition Lit159 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877038), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877032);
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("g$Total").readResolve());
    static final PairWithPosition Lit160 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877158), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877153);
    static final IntNum Lit161 = IntNum.make(16100);
    static final PairWithPosition Lit162 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877348), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877342);
    static final PairWithPosition Lit163 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877472), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877467);
    static final IntNum Lit164 = IntNum.make(9100);
    static final PairWithPosition Lit165 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877658), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877652);
    static final PairWithPosition Lit166 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877781), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877776);
    static final IntNum Lit167 = IntNum.make((int) ACRAConstants.DEFAULT_SOCKET_TIMEOUT);
    static final PairWithPosition Lit168 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877970), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 877964);
    static final PairWithPosition Lit169 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 878092), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 878087);
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("g$Tree").readResolve());
    static final PairWithPosition Lit170 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 878314), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 878308);
    static final SimpleSymbol Lit171 = ((SimpleSymbol) new SimpleSymbol("Spinner4$AfterSelecting").readResolve());
    static final SimpleSymbol Lit172 = ((SimpleSymbol) new SimpleSymbol("AfterSelecting").readResolve());
    static final FString Lit173 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit174 = ((SimpleSymbol) new SimpleSymbol("Label12").readResolve());
    static final FString Lit175 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit176 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit177 = ((SimpleSymbol) new SimpleSymbol("Label13").readResolve());
    static final SimpleSymbol Lit178 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final FString Lit179 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("g$TPD").readResolve());
    static final FString Lit180 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit181 = IntNum.make(-1005);
    static final SimpleSymbol Lit182 = ((SimpleSymbol) new SimpleSymbol("Hint").readResolve());
    static final IntNum Lit183 = IntNum.make(-1038);
    static final FString Lit184 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit185 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final SimpleSymbol Lit186 = ((SimpleSymbol) new SimpleSymbol("TableArrangement1").readResolve());
    static final IntNum Lit187 = IntNum.make(-1010);
    static final IntNum Lit188 = IntNum.make(-1090);
    static final FString Lit189 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("g$Country").readResolve());
    static final FString Lit190 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit191 = IntNum.make(-1005);
    static final SimpleSymbol Lit192 = ((SimpleSymbol) new SimpleSymbol("NumbersOnly").readResolve());
    static final IntNum Lit193 = IntNum.make(-1029);
    static final FString Lit194 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit195 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit196 = ((SimpleSymbol) new SimpleSymbol("Label1").readResolve());
    static final FString Lit197 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit198 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit199 = IntNum.make(-1005);
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final IntNum Lit200 = IntNum.make(-1029);
    static final FString Lit201 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit202 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit203 = ((SimpleSymbol) new SimpleSymbol("Label2").readResolve());
    static final FString Lit204 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit205 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit206 = ((SimpleSymbol) new SimpleSymbol("Label6").readResolve());
    static final FString Lit207 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit208 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit209 = IntNum.make(-1005);
    static final IntNum Lit21 = IntNum.make(3);
    static final IntNum Lit210 = IntNum.make(-1029);
    static final FString Lit211 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit212 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final SimpleSymbol Lit213 = ((SimpleSymbol) new SimpleSymbol("TableArrangement2").readResolve());
    static final IntNum Lit214 = IntNum.make(-1010);
    static final IntNum Lit215 = IntNum.make(-1090);
    static final FString Lit216 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final FString Lit217 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit218 = IntNum.make(-1005);
    static final IntNum Lit219 = IntNum.make(-1029);
    static final SimpleSymbol Lit22;
    static final FString Lit220 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit221 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit222 = ((SimpleSymbol) new SimpleSymbol("Label3").readResolve());
    static final FString Lit223 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit224 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit225 = ((SimpleSymbol) new SimpleSymbol("Label5").readResolve());
    static final FString Lit226 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit227 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit228 = ((SimpleSymbol) new SimpleSymbol("Label16").readResolve());
    static final FString Lit229 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final FString Lit230 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit231 = IntNum.make(-1005);
    static final IntNum Lit232 = IntNum.make(-1029);
    static final FString Lit233 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit234 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit235 = IntNum.make(-1005);
    static final IntNum Lit236 = IntNum.make(-1029);
    static final FString Lit237 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit238 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final SimpleSymbol Lit239 = ((SimpleSymbol) new SimpleSymbol("TableArrangement5").readResolve());
    static final SimpleSymbol Lit24;
    static final IntNum Lit240 = IntNum.make(-1010);
    static final IntNum Lit241 = IntNum.make(-1090);
    static final FString Lit242 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final FString Lit243 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit244 = IntNum.make(-1005);
    static final IntNum Lit245 = IntNum.make(-1044);
    static final FString Lit246 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit247 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit248 = ((SimpleSymbol) new SimpleSymbol("Label4").readResolve());
    static final FString Lit249 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final FString Lit250 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit251 = IntNum.make(-1005);
    static final IntNum Lit252 = IntNum.make(-1044);
    static final FString Lit253 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit254 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit255 = ((SimpleSymbol) new SimpleSymbol("Label17").readResolve());
    static final FString Lit256 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit257 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final SimpleSymbol Lit258 = ((SimpleSymbol) new SimpleSymbol("TableArrangement6").readResolve());
    static final IntNum Lit259 = IntNum.make(-1010);
    static final IntNum Lit26;
    static final IntNum Lit260 = IntNum.make(-1090);
    static final FString Lit261 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final FString Lit262 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit263 = IntNum.make(-1005);
    static final IntNum Lit264 = IntNum.make(-1044);
    static final FString Lit265 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit266 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit267 = IntNum.make(-1005);
    static final IntNum Lit268 = IntNum.make(-1044);
    static final FString Lit269 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("Icon").readResolve());
    static final FString Lit270 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit271 = ((SimpleSymbol) new SimpleSymbol("Label18").readResolve());
    static final FString Lit272 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit273 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit274 = ((SimpleSymbol) new SimpleSymbol("Label19").readResolve());
    static final FString Lit275 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit276 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit277 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement3").readResolve());
    static final IntNum Lit278 = IntNum.make(-1002);
    static final IntNum Lit279 = IntNum.make(-1005);
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final FString Lit280 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit281 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit282 = ((SimpleSymbol) new SimpleSymbol("Button1").readResolve());
    static final SimpleSymbol Lit283 = ((SimpleSymbol) new SimpleSymbol("Shape").readResolve());
    static final IntNum Lit284 = IntNum.make(-1090);
    static final FString Lit285 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit286 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2707737), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2707730), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2707722);
    static final IntNum Lit287 = IntNum.make(45);
    static final PairWithPosition Lit288 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2707904), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2707896);
    static final PairWithPosition Lit289 = PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2707929);
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve());
    static final PairWithPosition Lit290 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708144), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708136);
    static final PairWithPosition Lit291 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708168), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708160);
    static final PairWithPosition Lit292 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708191), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708183);
    static final PairWithPosition Lit293 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708510), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708504);
    static final PairWithPosition Lit294 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708816), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708810);
    static final PairWithPosition Lit295 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708927), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2708921);
    static final PairWithPosition Lit296 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709038), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709032);
    static final PairWithPosition Lit297 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709149), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709143);
    static final PairWithPosition Lit298 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709260), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709254);
    static final PairWithPosition Lit299 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709371), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709365);
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$Other").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final PairWithPosition Lit300 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709469), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709463);
    static final PairWithPosition Lit301 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709566), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709560);
    static final PairWithPosition Lit302 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709663), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2709657);
    static final SimpleSymbol Lit303 = ((SimpleSymbol) new SimpleSymbol("Clock1").readResolve());
    static final SimpleSymbol Lit304 = ((SimpleSymbol) new SimpleSymbol("TimerInterval").readResolve());
    static final SimpleSymbol Lit305 = ((SimpleSymbol) new SimpleSymbol("DurationToDays").readResolve());
    static final SimpleSymbol Lit306 = ((SimpleSymbol) new SimpleSymbol("Duration").readResolve());
    static final SimpleSymbol Lit307 = ((SimpleSymbol) new SimpleSymbol("MakeInstant").readResolve());
    static final SimpleSymbol Lit308 = ((SimpleSymbol) new SimpleSymbol("FormatDate").readResolve());
    static final SimpleSymbol Lit309 = ((SimpleSymbol) new SimpleSymbol("MakeDate").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol(TinyDB.DEFAULT_NAMESPACE).readResolve());
    static final PairWithPosition Lit310 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710103), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710096), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710088);
    static final PairWithPosition Lit311 = PairWithPosition.make(Lit362, PairWithPosition.make(Lit24, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710143), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710128);
    static final PairWithPosition Lit312 = PairWithPosition.make(Lit24, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710152);
    static final SimpleSymbol Lit313 = ((SimpleSymbol) new SimpleSymbol("Year").readResolve());
    static final SimpleSymbol Lit314 = ((SimpleSymbol) new SimpleSymbol("Now").readResolve());
    static final PairWithPosition Lit315 = PairWithPosition.make(Lit362, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710470);
    static final SimpleSymbol Lit316 = ((SimpleSymbol) new SimpleSymbol("Month").readResolve());
    static final PairWithPosition Lit317 = PairWithPosition.make(Lit362, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710610);
    static final SimpleSymbol Lit318 = ((SimpleSymbol) new SimpleSymbol("DayOfMonth").readResolve());
    static final PairWithPosition Lit319 = PairWithPosition.make(Lit362, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710755);
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("GetValue").readResolve());
    static final PairWithPosition Lit320 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710789), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710782), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710774);
    static final PairWithPosition Lit321 = PairWithPosition.make(Lit362, PairWithPosition.make(Lit24, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710829), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710814);
    static final PairWithPosition Lit322 = PairWithPosition.make(Lit24, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710838);
    static final PairWithPosition Lit323 = PairWithPosition.make(Lit362, PairWithPosition.make(Lit362, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710863), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710848);
    static final PairWithPosition Lit324 = PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2710881);
    static final IntNum Lit325 = IntNum.make(365);
    static final PairWithPosition Lit326 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711022), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711014);
    static final PairWithPosition Lit327 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711224), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711216);
    static final PairWithPosition Lit328 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711259), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711251);
    static final PairWithPosition Lit329 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711466), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711458);
    static final IntNum Lit33 = IntNum.make(1);
    static final PairWithPosition Lit330 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711501), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711493);
    static final PairWithPosition Lit331 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711742), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711734);
    static final PairWithPosition Lit332 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711813), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711805);
    static final PairWithPosition Lit333 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711848), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2711840);
    static final PairWithPosition Lit334 = PairWithPosition.make(Lit22, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712071), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712063);
    static final PairWithPosition Lit335;
    static final PairWithPosition Lit336 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712509), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712503);
    static final PairWithPosition Lit337 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712622), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712616);
    static final PairWithPosition Lit338 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712735), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712729);
    static final PairWithPosition Lit339;
    static final PairWithPosition Lit34 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143456), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143450);
    static final SimpleSymbol Lit340 = ((SimpleSymbol) new SimpleSymbol("Button1$Click").readResolve());
    static final SimpleSymbol Lit341 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final FString Lit342 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final SimpleSymbol Lit343 = ((SimpleSymbol) new SimpleSymbol("Namespace").readResolve());
    static final FString Lit344 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final FString Lit345 = new FString("com.google.appinventor.components.runtime.Clock");
    static final FString Lit346 = new FString("com.google.appinventor.components.runtime.Clock");
    static final SimpleSymbol Lit347 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit348 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit349 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final IntNum Lit35 = IntNum.make(2);
    static final SimpleSymbol Lit350 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit351 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit352 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit353 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit354 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit355 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit356 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit357 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit358 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit359 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final PairWithPosition Lit36 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143554), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143548);
    static final SimpleSymbol Lit360 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit361 = ((SimpleSymbol) new SimpleSymbol("any").readResolve());
    static final SimpleSymbol Lit362 = ((SimpleSymbol) new SimpleSymbol("InstantInTime").readResolve());
    static final PairWithPosition Lit37 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143653), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143647);
    static final IntNum Lit38 = IntNum.make(4);
    static final PairWithPosition Lit39 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143751), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143745);
    static final IntNum Lit4 = IntNum.make(0);
    static final IntNum Lit40 = IntNum.make(5);
    static final PairWithPosition Lit41 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143848), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143842);
    static final IntNum Lit42 = IntNum.make(6);
    static final PairWithPosition Lit43 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143948), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 143942);
    static final IntNum Lit44 = IntNum.make(8);
    static final PairWithPosition Lit45 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 144047), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 144041);
    static final IntNum Lit46 = IntNum.make(9);
    static final PairWithPosition Lit47 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 144145), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 144139);
    static final IntNum Lit48 = IntNum.make(10);
    static final PairWithPosition Lit49 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 144243), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 144237);
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("g$SPD").readResolve());
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("TextBox1").readResolve());
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("TextBox2").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("TextBox3").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("TextBox4").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("TextBox5").readResolve());
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("TextBox6").readResolve());
    static final PairWithPosition Lit57 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 144728), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 144723);
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("Spinner3").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("Selection").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("g$RelSeq").readResolve());
    static final PairWithPosition Lit60 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 144907), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 144902);
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("Spinner1").readResolve());
    static final PairWithPosition Lit62 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145084), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145079);
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("Spinner2").readResolve());
    static final IntNum Lit64 = IntNum.make(7);
    static final PairWithPosition Lit65 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145312), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145306);
    static final PairWithPosition Lit66 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145327), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145322);
    static final PairWithPosition Lit67 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145479), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145473);
    static final PairWithPosition Lit68 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145494), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145489);
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("Spinner4").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("g$TotSeq").readResolve());
    static final IntNum Lit70 = IntNum.make(2400);
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("TextBox7").readResolve());
    static final PairWithPosition Lit72 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145810), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145804);
    static final PairWithPosition Lit73 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145825), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 145820);
    static final IntNum Lit74 = IntNum.make(16100);
    static final PairWithPosition Lit75 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146139), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146133);
    static final PairWithPosition Lit76 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146154), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146149);
    static final IntNum Lit77 = IntNum.make(9100);
    static final PairWithPosition Lit78 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146471), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146465);
    static final PairWithPosition Lit79 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146486), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146481);
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("g$Inter").readResolve());
    static final IntNum Lit80 = IntNum.make((int) ACRAConstants.DEFAULT_SOCKET_TIMEOUT);
    static final PairWithPosition Lit81 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146802), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146796);
    static final PairWithPosition Lit82 = PairWithPosition.make(Lit361, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146817), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 146812);
    static final IntNum Lit83 = IntNum.make(15);
    static final PairWithPosition Lit84 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 147007), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 147001);
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol("ReadOnly").readResolve());
    static final IntNum Lit86 = IntNum.make(11);
    static final PairWithPosition Lit87 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 147319), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 147313);
    static final IntNum Lit88 = IntNum.make(12);
    static final PairWithPosition Lit89 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 147417), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 147411);
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("g$Year").readResolve());
    static final IntNum Lit90 = IntNum.make(13);
    static final PairWithPosition Lit91 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 147518), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 147512);
    static final IntNum Lit92 = IntNum.make(14);
    static final PairWithPosition Lit93 = PairWithPosition.make(Lit24, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 147619), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 147613);
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("TextBox10").readResolve());
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol("TextBox11").readResolve());
    static final SimpleSymbol Lit96 = ((SimpleSymbol) new SimpleSymbol("TextBox12").readResolve());
    static final SimpleSymbol Lit97 = ((SimpleSymbol) new SimpleSymbol("TextBox13").readResolve());
    static final SimpleSymbol Lit98 = ((SimpleSymbol) new SimpleSymbol("Screen1$Initialize").readResolve());
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    public static Screen1 Screen1;
    static final ModuleMethod lambda$Fn1 = null;
    static final ModuleMethod lambda$Fn10 = null;
    static final ModuleMethod lambda$Fn100 = null;
    static final ModuleMethod lambda$Fn101 = null;
    static final ModuleMethod lambda$Fn102 = null;
    static final ModuleMethod lambda$Fn103 = null;
    static final ModuleMethod lambda$Fn104 = null;
    static final ModuleMethod lambda$Fn11 = null;
    static final ModuleMethod lambda$Fn12 = null;
    static final ModuleMethod lambda$Fn13 = null;
    static final ModuleMethod lambda$Fn14 = null;
    static final ModuleMethod lambda$Fn15 = null;
    static final ModuleMethod lambda$Fn16 = null;
    static final ModuleMethod lambda$Fn17 = null;
    static final ModuleMethod lambda$Fn18 = null;
    static final ModuleMethod lambda$Fn19 = null;
    static final ModuleMethod lambda$Fn2 = null;
    static final ModuleMethod lambda$Fn20 = null;
    static final ModuleMethod lambda$Fn21 = null;
    static final ModuleMethod lambda$Fn22 = null;
    static final ModuleMethod lambda$Fn23 = null;
    static final ModuleMethod lambda$Fn24 = null;
    static final ModuleMethod lambda$Fn25 = null;
    static final ModuleMethod lambda$Fn26 = null;
    static final ModuleMethod lambda$Fn27 = null;
    static final ModuleMethod lambda$Fn28 = null;
    static final ModuleMethod lambda$Fn29 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn30 = null;
    static final ModuleMethod lambda$Fn31 = null;
    static final ModuleMethod lambda$Fn32 = null;
    static final ModuleMethod lambda$Fn33 = null;
    static final ModuleMethod lambda$Fn34 = null;
    static final ModuleMethod lambda$Fn35 = null;
    static final ModuleMethod lambda$Fn36 = null;
    static final ModuleMethod lambda$Fn37 = null;
    static final ModuleMethod lambda$Fn38 = null;
    static final ModuleMethod lambda$Fn39 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn40 = null;
    static final ModuleMethod lambda$Fn41 = null;
    static final ModuleMethod lambda$Fn42 = null;
    static final ModuleMethod lambda$Fn43 = null;
    static final ModuleMethod lambda$Fn44 = null;
    static final ModuleMethod lambda$Fn45 = null;
    static final ModuleMethod lambda$Fn46 = null;
    static final ModuleMethod lambda$Fn47 = null;
    static final ModuleMethod lambda$Fn48 = null;
    static final ModuleMethod lambda$Fn49 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn50 = null;
    static final ModuleMethod lambda$Fn51 = null;
    static final ModuleMethod lambda$Fn52 = null;
    static final ModuleMethod lambda$Fn53 = null;
    static final ModuleMethod lambda$Fn54 = null;
    static final ModuleMethod lambda$Fn55 = null;
    static final ModuleMethod lambda$Fn56 = null;
    static final ModuleMethod lambda$Fn57 = null;
    static final ModuleMethod lambda$Fn58 = null;
    static final ModuleMethod lambda$Fn59 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn60 = null;
    static final ModuleMethod lambda$Fn61 = null;
    static final ModuleMethod lambda$Fn62 = null;
    static final ModuleMethod lambda$Fn63 = null;
    static final ModuleMethod lambda$Fn64 = null;
    static final ModuleMethod lambda$Fn65 = null;
    static final ModuleMethod lambda$Fn66 = null;
    static final ModuleMethod lambda$Fn67 = null;
    static final ModuleMethod lambda$Fn68 = null;
    static final ModuleMethod lambda$Fn69 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn70 = null;
    static final ModuleMethod lambda$Fn71 = null;
    static final ModuleMethod lambda$Fn72 = null;
    static final ModuleMethod lambda$Fn73 = null;
    static final ModuleMethod lambda$Fn74 = null;
    static final ModuleMethod lambda$Fn75 = null;
    static final ModuleMethod lambda$Fn76 = null;
    static final ModuleMethod lambda$Fn77 = null;
    static final ModuleMethod lambda$Fn78 = null;
    static final ModuleMethod lambda$Fn79 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn80 = null;
    static final ModuleMethod lambda$Fn81 = null;
    static final ModuleMethod lambda$Fn82 = null;
    static final ModuleMethod lambda$Fn83 = null;
    static final ModuleMethod lambda$Fn84 = null;
    static final ModuleMethod lambda$Fn85 = null;
    static final ModuleMethod lambda$Fn86 = null;
    static final ModuleMethod lambda$Fn87 = null;
    static final ModuleMethod lambda$Fn88 = null;
    static final ModuleMethod lambda$Fn89 = null;
    static final ModuleMethod lambda$Fn9 = null;
    static final ModuleMethod lambda$Fn90 = null;
    static final ModuleMethod lambda$Fn91 = null;
    static final ModuleMethod lambda$Fn92 = null;
    static final ModuleMethod lambda$Fn93 = null;
    static final ModuleMethod lambda$Fn94 = null;
    static final ModuleMethod lambda$Fn95 = null;
    static final ModuleMethod lambda$Fn96 = null;
    static final ModuleMethod lambda$Fn97 = null;
    static final ModuleMethod lambda$Fn98 = null;
    static final ModuleMethod lambda$Fn99 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public Button Button1;
    public final ModuleMethod Button1$Click;
    public Clock Clock1;
    public Label Label1;
    public Label Label10;
    public Label Label11;
    public Label Label12;
    public Label Label13;
    public Label Label16;
    public Label Label17;
    public Label Label18;
    public Label Label19;
    public Label Label2;
    public Label Label20;
    public Label Label3;
    public Label Label4;
    public Label Label5;
    public Label Label6;
    public Label Label7;
    public Label Label8;
    public Label Label9;
    public final ModuleMethod Screen1$Initialize;
    public Spinner Spinner1;
    public Spinner Spinner2;
    public Spinner Spinner3;
    public Spinner Spinner4;
    public final ModuleMethod Spinner4$AfterSelecting;
    public TableArrangement TableArrangement1;
    public TableArrangement TableArrangement2;
    public TableArrangement TableArrangement3;
    public TableArrangement TableArrangement4;
    public TableArrangement TableArrangement5;
    public TableArrangement TableArrangement6;
    public TextBox TextBox1;
    public TextBox TextBox10;
    public TextBox TextBox11;
    public TextBox TextBox12;
    public TextBox TextBox13;
    public TextBox TextBox2;
    public TextBox TextBox3;
    public TextBox TextBox4;
    public TextBox TextBox5;
    public TextBox TextBox6;
    public TextBox TextBox7;
    public TinyDB TinyDB1;
    public VerticalArrangement VerticalArrangement1;
    public VerticalArrangement VerticalArrangement3;
    public final ModuleMethod add$Mnto$Mncomponents;
    public final ModuleMethod add$Mnto$Mnevents;
    public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
    public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
    public final ModuleMethod android$Mnlog$Mnform;
    public LList components$Mnto$Mncreate;
    public final ModuleMethod dispatchEvent;
    public final ModuleMethod dispatchGenericEvent;
    public LList events$Mnto$Mnregister;
    public LList form$Mndo$Mnafter$Mncreation;
    public Environment form$Mnenvironment;
    public Symbol form$Mnname$Mnsymbol;
    public final ModuleMethod get$Mnsimple$Mnname;
    public Environment global$Mnvar$Mnenvironment;
    public LList global$Mnvars$Mnto$Mncreate;
    public final ModuleMethod is$Mnbound$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod lookup$Mnhandler;
    public final ModuleMethod lookup$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod onCreate;
    public final ModuleMethod process$Mnexception;
    public final ModuleMethod send$Mnerror;

    /* compiled from: Screen1.yail */
    public class frame extends ModuleBody {
        Screen1 $main = this;

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 1;
                    return 0;
                case 2:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 1;
                    return 0;
                case 3:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 1;
                    return 0;
                case 5:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 1;
                    return 0;
                case 7:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 1;
                    return 0;
                case 12:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 1;
                    return 0;
                case 13:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 1;
                    return 0;
                case 14:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 1;
                    return 0;
                case 64:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 4:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 2;
                    return 0;
                case 5:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 2;
                    return 0;
                case 8:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 2;
                    return 0;
                case 9:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 2;
                    return 0;
                case 11:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 2;
                    return 0;
                case 17:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }

        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 10:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 4;
                    return 0;
                case 15:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    if (!(obj2 instanceof Component)) {
                        return -786430;
                    }
                    callContext.value2 = obj2;
                    if (!(obj3 instanceof String)) {
                        return -786429;
                    }
                    callContext.value3 = obj3;
                    if (!(obj4 instanceof String)) {
                        return -786428;
                    }
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 4;
                    return 0;
                case 16:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    if (!(obj2 instanceof Component)) {
                        return -786430;
                    }
                    callContext.value2 = obj2;
                    if (!(obj3 instanceof String)) {
                        return -786429;
                    }
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 4;
                    return 0;
                default:
                    return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 1:
                    return this.$main.getSimpleName(obj);
                case 2:
                    try {
                        this.$main.onCreate((Bundle) obj);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "onCreate", 1, obj);
                    }
                case 3:
                    this.$main.androidLogForm(obj);
                    return Values.empty;
                case 5:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case 7:
                    try {
                        return this.$main.isBoundInFormEnvironment((Symbol) obj) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "is-bound-in-form-environment", 1, obj);
                    }
                case 12:
                    this.$main.addToFormDoAfterCreation(obj);
                    return Values.empty;
                case 13:
                    this.$main.sendError(obj);
                    return Values.empty;
                case 14:
                    this.$main.processException(obj);
                    return Values.empty;
                case 64:
                    return this.$main.Spinner4$AfterSelecting(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            boolean z = true;
            switch (moduleMethod.selector) {
                case 10:
                    this.$main.addToComponents(obj, obj2, obj3, obj4);
                    return Values.empty;
                case 15:
                    try {
                        try {
                            try {
                                try {
                                    return this.$main.dispatchEvent((Component) obj, (String) obj2, (String) obj3, (Object[]) obj4) ? Boolean.TRUE : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "dispatchEvent", 4, obj4);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "dispatchEvent", 3, obj3);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "dispatchEvent", 2, obj2);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "dispatchEvent", 1, obj);
                    }
                case 16:
                    Screen1 screen1 = this.$main;
                    try {
                        Component component = (Component) obj;
                        try {
                            String str = (String) obj2;
                            try {
                                if (obj3 == Boolean.FALSE) {
                                    z = false;
                                }
                                try {
                                    screen1.dispatchGenericEvent(component, str, z, (Object[]) obj4);
                                    return Values.empty;
                                } catch (ClassCastException e5) {
                                    throw new WrongType(e5, "dispatchGenericEvent", 4, obj4);
                                }
                            } catch (ClassCastException e6) {
                                throw new WrongType(e6, "dispatchGenericEvent", 3, obj3);
                            }
                        } catch (ClassCastException e7) {
                            throw new WrongType(e7, "dispatchGenericEvent", 2, obj2);
                        }
                    } catch (ClassCastException e8) {
                        throw new WrongType(e8, "dispatchGenericEvent", 1, obj);
                    }
                default:
                    return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
            }
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 4:
                    try {
                        this.$main.addToFormEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "add-to-form-environment", 1, obj);
                    }
                case 5:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj, obj2);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case 8:
                    try {
                        this.$main.addToGlobalVarEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "add-to-global-var-environment", 1, obj);
                    }
                case 9:
                    this.$main.addToEvents(obj, obj2);
                    return Values.empty;
                case 11:
                    this.$main.addToGlobalVars(obj, obj2);
                    return Values.empty;
                case 17:
                    return this.$main.lookupHandler(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 18:
                    return Screen1.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return Screen1.lambda3();
                case 21:
                    return Screen1.lambda4();
                case 22:
                    return Screen1.lambda5();
                case 23:
                    return Screen1.lambda6();
                case 24:
                    return Screen1.lambda7();
                case 25:
                    return Screen1.lambda8();
                case 26:
                    return Screen1.lambda9();
                case 27:
                    return Screen1.lambda10();
                case 28:
                    return Screen1.lambda11();
                case 29:
                    return Screen1.lambda12();
                case 30:
                    return Screen1.lambda13();
                case 31:
                    return Screen1.lambda14();
                case 32:
                    return Screen1.lambda15();
                case 33:
                    return Screen1.lambda16();
                case 34:
                    return Screen1.lambda17();
                case 35:
                    return Screen1.lambda18();
                case 36:
                    return Screen1.lambda19();
                case 37:
                    return this.$main.Screen1$Initialize();
                case 38:
                    return Screen1.lambda20();
                case 39:
                    return Screen1.lambda21();
                case 40:
                    return Screen1.lambda22();
                case 41:
                    return Screen1.lambda23();
                case 42:
                    return Screen1.lambda24();
                case 43:
                    return Screen1.lambda25();
                case 44:
                    return Screen1.lambda26();
                case 45:
                    return Screen1.lambda27();
                case 46:
                    return Screen1.lambda28();
                case 47:
                    return Screen1.lambda29();
                case 48:
                    return Screen1.lambda30();
                case 49:
                    return Screen1.lambda31();
                case 50:
                    return Screen1.lambda32();
                case 51:
                    return Screen1.lambda33();
                case 52:
                    return Screen1.lambda34();
                case 53:
                    return Screen1.lambda35();
                case 54:
                    return Screen1.lambda36();
                case 55:
                    return Screen1.lambda37();
                case 56:
                    return Screen1.lambda38();
                case 57:
                    return Screen1.lambda39();
                case 58:
                    return Screen1.lambda40();
                case 59:
                    return Screen1.lambda41();
                case 60:
                    return Screen1.lambda42();
                case 61:
                    return Screen1.lambda43();
                case 62:
                    return Screen1.lambda44();
                case 63:
                    return Screen1.lambda45();
                case 65:
                    return Screen1.lambda46();
                case 66:
                    return Screen1.lambda47();
                case 67:
                    return Screen1.lambda48();
                case 68:
                    return Screen1.lambda49();
                case 69:
                    return Screen1.lambda50();
                case 70:
                    return Screen1.lambda51();
                case 71:
                    return Screen1.lambda52();
                case 72:
                    return Screen1.lambda53();
                case 73:
                    return Screen1.lambda54();
                case 74:
                    return Screen1.lambda55();
                case 75:
                    return Screen1.lambda56();
                case 76:
                    return Screen1.lambda57();
                case 77:
                    return Screen1.lambda58();
                case 78:
                    return Screen1.lambda59();
                case 79:
                    return Screen1.lambda60();
                case 80:
                    return Screen1.lambda61();
                case 81:
                    return Screen1.lambda62();
                case 82:
                    return Screen1.lambda63();
                case 83:
                    return Screen1.lambda64();
                case 84:
                    return Screen1.lambda65();
                case 85:
                    return Screen1.lambda66();
                case 86:
                    return Screen1.lambda67();
                case 87:
                    return Screen1.lambda68();
                case 88:
                    return Screen1.lambda69();
                case 89:
                    return Screen1.lambda70();
                case 90:
                    return Screen1.lambda71();
                case 91:
                    return Screen1.lambda72();
                case 92:
                    return Screen1.lambda73();
                case 93:
                    return Screen1.lambda74();
                case 94:
                    return Screen1.lambda75();
                case 95:
                    return Screen1.lambda76();
                case 96:
                    return Screen1.lambda77();
                case 97:
                    return Screen1.lambda78();
                case 98:
                    return Screen1.lambda79();
                case 99:
                    return Screen1.lambda80();
                case 100:
                    return Screen1.lambda81();
                case 101:
                    return Screen1.lambda82();
                case 102:
                    return Screen1.lambda83();
                case 103:
                    return Screen1.lambda84();
                case 104:
                    return Screen1.lambda85();
                case 105:
                    return Screen1.lambda86();
                case 106:
                    return Screen1.lambda87();
                case 107:
                    return Screen1.lambda88();
                case 108:
                    return Screen1.lambda89();
                case 109:
                    return Screen1.lambda90();
                case 110:
                    return Screen1.lambda91();
                case 111:
                    return Screen1.lambda92();
                case 112:
                    return Screen1.lambda93();
                case 113:
                    return Screen1.lambda94();
                case 114:
                    return Screen1.lambda95();
                case 115:
                    return Screen1.lambda96();
                case 116:
                    return Screen1.lambda97();
                case 117:
                    return Screen1.lambda98();
                case 118:
                    return Screen1.lambda99();
                case 119:
                    return Screen1.lambda100();
                case 120:
                    return Screen1.lambda101();
                case 121:
                    return Screen1.lambda102();
                case 122:
                    return Screen1.lambda103();
                case 123:
                    return this.$main.Button1$Click();
                case 124:
                    return Screen1.lambda104();
                case 125:
                    return Screen1.lambda105();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 18:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 19:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 20:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 21:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 22:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 23:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 24:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 25:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 26:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 27:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 28:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 29:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 30:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 31:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 32:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 33:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 34:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 35:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 36:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 37:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 38:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 39:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 40:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 41:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 42:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 43:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 44:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 45:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 46:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 47:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 48:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 49:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 50:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 51:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 52:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 53:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 54:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 55:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 56:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 57:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 58:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 59:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 60:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 61:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 62:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 63:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 65:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 66:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 67:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 68:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 69:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 70:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 71:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 72:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 73:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 74:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 75:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 76:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 77:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 78:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 79:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 80:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 81:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 82:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 83:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 84:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 85:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 86:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 87:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 88:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 89:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 90:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 91:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 92:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 93:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 94:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 95:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 96:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 97:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 98:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 99:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 100:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 101:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 102:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 103:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 104:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 105:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 106:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 107:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 108:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 109:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 110:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 111:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 112:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 113:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 114:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 115:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 116:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 117:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 118:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 119:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 120:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 121:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 122:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 123:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 124:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                case 125:
                    callContext.proc = moduleMethod;
                    callContext.f232pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
    }

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit24 = simpleSymbol;
        Lit339 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(Lit361, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712848), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712842);
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit22 = simpleSymbol2;
        Lit335 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(Lit22, LList.Empty, "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712106), "/tmp/1589802993594_0.7819105971651724-0/youngandroidproject/../src/appinventor/ai_hector_kroes/TreeCounter_vFinal/Screen1.yail", 2712098);
        int[] iArr = new int[2];
        iArr[0] = -1399671655;
        Lit26 = IntNum.make(iArr);
    }

    public Screen1() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit347, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit348, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit349, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit350, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit351, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit352, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit353, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit354, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit355, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit356, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit357, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit358, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit359, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit360, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime7057964200056720403.scm:622");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, null, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 21, null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 22, null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 23, null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 24, null, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 25, null, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 26, null, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 27, null, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 28, null, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 29, null, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 30, null, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 31, null, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 32, null, 0);
        lambda$Fn15 = new ModuleMethod(frame2, 33, null, 0);
        lambda$Fn16 = new ModuleMethod(frame2, 34, null, 0);
        lambda$Fn17 = new ModuleMethod(frame2, 35, null, 0);
        lambda$Fn18 = new ModuleMethod(frame2, 36, null, 0);
        this.Screen1$Initialize = new ModuleMethod(frame2, 37, Lit98, 0);
        lambda$Fn19 = new ModuleMethod(frame2, 38, null, 0);
        lambda$Fn20 = new ModuleMethod(frame2, 39, null, 0);
        lambda$Fn21 = new ModuleMethod(frame2, 40, null, 0);
        lambda$Fn22 = new ModuleMethod(frame2, 41, null, 0);
        lambda$Fn23 = new ModuleMethod(frame2, 42, null, 0);
        lambda$Fn24 = new ModuleMethod(frame2, 43, null, 0);
        lambda$Fn25 = new ModuleMethod(frame2, 44, null, 0);
        lambda$Fn26 = new ModuleMethod(frame2, 45, null, 0);
        lambda$Fn27 = new ModuleMethod(frame2, 46, null, 0);
        lambda$Fn28 = new ModuleMethod(frame2, 47, null, 0);
        lambda$Fn29 = new ModuleMethod(frame2, 48, null, 0);
        lambda$Fn30 = new ModuleMethod(frame2, 49, null, 0);
        lambda$Fn31 = new ModuleMethod(frame2, 50, null, 0);
        lambda$Fn32 = new ModuleMethod(frame2, 51, null, 0);
        lambda$Fn33 = new ModuleMethod(frame2, 52, null, 0);
        lambda$Fn34 = new ModuleMethod(frame2, 53, null, 0);
        lambda$Fn35 = new ModuleMethod(frame2, 54, null, 0);
        lambda$Fn36 = new ModuleMethod(frame2, 55, null, 0);
        lambda$Fn37 = new ModuleMethod(frame2, 56, null, 0);
        lambda$Fn38 = new ModuleMethod(frame2, 57, null, 0);
        lambda$Fn39 = new ModuleMethod(frame2, 58, null, 0);
        lambda$Fn40 = new ModuleMethod(frame2, 59, null, 0);
        lambda$Fn41 = new ModuleMethod(frame2, 60, null, 0);
        lambda$Fn42 = new ModuleMethod(frame2, 61, null, 0);
        lambda$Fn43 = new ModuleMethod(frame2, 62, null, 0);
        lambda$Fn44 = new ModuleMethod(frame2, 63, null, 0);
        this.Spinner4$AfterSelecting = new ModuleMethod(frame2, 64, Lit171, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn45 = new ModuleMethod(frame2, 65, null, 0);
        lambda$Fn46 = new ModuleMethod(frame2, 66, null, 0);
        lambda$Fn47 = new ModuleMethod(frame2, 67, null, 0);
        lambda$Fn48 = new ModuleMethod(frame2, 68, null, 0);
        lambda$Fn49 = new ModuleMethod(frame2, 69, null, 0);
        lambda$Fn50 = new ModuleMethod(frame2, 70, null, 0);
        lambda$Fn51 = new ModuleMethod(frame2, 71, null, 0);
        lambda$Fn52 = new ModuleMethod(frame2, 72, null, 0);
        lambda$Fn53 = new ModuleMethod(frame2, 73, null, 0);
        lambda$Fn54 = new ModuleMethod(frame2, 74, null, 0);
        lambda$Fn55 = new ModuleMethod(frame2, 75, null, 0);
        lambda$Fn56 = new ModuleMethod(frame2, 76, null, 0);
        lambda$Fn57 = new ModuleMethod(frame2, 77, null, 0);
        lambda$Fn58 = new ModuleMethod(frame2, 78, null, 0);
        lambda$Fn59 = new ModuleMethod(frame2, 79, null, 0);
        lambda$Fn60 = new ModuleMethod(frame2, 80, null, 0);
        lambda$Fn61 = new ModuleMethod(frame2, 81, null, 0);
        lambda$Fn62 = new ModuleMethod(frame2, 82, null, 0);
        lambda$Fn63 = new ModuleMethod(frame2, 83, null, 0);
        lambda$Fn64 = new ModuleMethod(frame2, 84, null, 0);
        lambda$Fn65 = new ModuleMethod(frame2, 85, null, 0);
        lambda$Fn66 = new ModuleMethod(frame2, 86, null, 0);
        lambda$Fn67 = new ModuleMethod(frame2, 87, null, 0);
        lambda$Fn68 = new ModuleMethod(frame2, 88, null, 0);
        lambda$Fn69 = new ModuleMethod(frame2, 89, null, 0);
        lambda$Fn70 = new ModuleMethod(frame2, 90, null, 0);
        lambda$Fn71 = new ModuleMethod(frame2, 91, null, 0);
        lambda$Fn72 = new ModuleMethod(frame2, 92, null, 0);
        lambda$Fn73 = new ModuleMethod(frame2, 93, null, 0);
        lambda$Fn74 = new ModuleMethod(frame2, 94, null, 0);
        lambda$Fn75 = new ModuleMethod(frame2, 95, null, 0);
        lambda$Fn76 = new ModuleMethod(frame2, 96, null, 0);
        lambda$Fn77 = new ModuleMethod(frame2, 97, null, 0);
        lambda$Fn78 = new ModuleMethod(frame2, 98, null, 0);
        lambda$Fn79 = new ModuleMethod(frame2, 99, null, 0);
        lambda$Fn80 = new ModuleMethod(frame2, 100, null, 0);
        lambda$Fn81 = new ModuleMethod(frame2, 101, null, 0);
        lambda$Fn82 = new ModuleMethod(frame2, 102, null, 0);
        lambda$Fn83 = new ModuleMethod(frame2, 103, null, 0);
        lambda$Fn84 = new ModuleMethod(frame2, 104, null, 0);
        lambda$Fn85 = new ModuleMethod(frame2, 105, null, 0);
        lambda$Fn86 = new ModuleMethod(frame2, 106, null, 0);
        lambda$Fn87 = new ModuleMethod(frame2, 107, null, 0);
        lambda$Fn88 = new ModuleMethod(frame2, 108, null, 0);
        lambda$Fn89 = new ModuleMethod(frame2, 109, null, 0);
        lambda$Fn90 = new ModuleMethod(frame2, 110, null, 0);
        lambda$Fn91 = new ModuleMethod(frame2, 111, null, 0);
        lambda$Fn92 = new ModuleMethod(frame2, 112, null, 0);
        lambda$Fn93 = new ModuleMethod(frame2, 113, null, 0);
        lambda$Fn94 = new ModuleMethod(frame2, 114, null, 0);
        lambda$Fn95 = new ModuleMethod(frame2, 115, null, 0);
        lambda$Fn96 = new ModuleMethod(frame2, 116, null, 0);
        lambda$Fn97 = new ModuleMethod(frame2, 117, null, 0);
        lambda$Fn98 = new ModuleMethod(frame2, 118, null, 0);
        lambda$Fn99 = new ModuleMethod(frame2, 119, null, 0);
        lambda$Fn100 = new ModuleMethod(frame2, 120, null, 0);
        lambda$Fn101 = new ModuleMethod(frame2, 121, null, 0);
        lambda$Fn102 = new ModuleMethod(frame2, 122, null, 0);
        this.Button1$Click = new ModuleMethod(frame2, 123, Lit340, 0);
        lambda$Fn103 = new ModuleMethod(frame2, 124, null, 0);
        lambda$Fn104 = new ModuleMethod(frame2, 125, null, 0);
    }

    public Object lookupInFormEnvironment(Symbol symbol) {
        return lookupInFormEnvironment(symbol, Boolean.FALSE);
    }

    public void run() {
        CallContext instance = CallContext.getInstance();
        Consumer consumer = instance.consumer;
        instance.consumer = VoidConsumer.instance;
        try {
            run(instance);
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        ModuleBody.runCleanup(instance, th, consumer);
    }

    public final void run(CallContext $ctx) {
        String obj;
        Consumer $result = $ctx.consumer;
        Object find = require.find("com.google.youngandroid.runtime");
        try {
            ((Runnable) find).run();
            this.$Stdebug$Mnform$St = Boolean.FALSE;
            this.form$Mnenvironment = Environment.make(misc.symbol$To$String(Lit0));
            FString stringAppend = strings.stringAppend(misc.symbol$To$String(Lit0), "-global-vars");
            if (stringAppend == null) {
                obj = null;
            } else {
                obj = stringAppend.toString();
            }
            this.global$Mnvar$Mnenvironment = Environment.make(obj);
            Screen1 = null;
            this.form$Mnname$Mnsymbol = Lit0;
            this.events$Mnto$Mnregister = LList.Empty;
            this.components$Mnto$Mncreate = LList.Empty;
            this.global$Mnvars$Mnto$Mncreate = LList.Empty;
            this.form$Mndo$Mnafter$Mncreation = LList.Empty;
            Object find2 = require.find("com.google.youngandroid.runtime");
            try {
                ((Runnable) find2).run();
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit3, Lit4), $result);
                } else {
                    addToGlobalVars(Lit3, lambda$Fn2);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit5, Lit4), $result);
                } else {
                    addToGlobalVars(Lit5, lambda$Fn3);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit6, Lit4), $result);
                } else {
                    addToGlobalVars(Lit6, lambda$Fn4);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit7, Lit4), $result);
                } else {
                    addToGlobalVars(Lit7, lambda$Fn5);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit8, Lit4), $result);
                } else {
                    addToGlobalVars(Lit8, lambda$Fn6);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit9, Lit4), $result);
                } else {
                    addToGlobalVars(Lit9, lambda$Fn7);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit10, Lit4), $result);
                } else {
                    addToGlobalVars(Lit10, lambda$Fn8);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit11, Lit4), $result);
                } else {
                    addToGlobalVars(Lit11, lambda$Fn9);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit12, Lit4), $result);
                } else {
                    addToGlobalVars(Lit12, lambda$Fn10);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit13, Lit4), $result);
                } else {
                    addToGlobalVars(Lit13, lambda$Fn11);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit14, Lit4), $result);
                } else {
                    addToGlobalVars(Lit14, lambda$Fn12);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit15, Lit4), $result);
                } else {
                    addToGlobalVars(Lit15, lambda$Fn13);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit16, Lit4), $result);
                } else {
                    addToGlobalVars(Lit16, lambda$Fn14);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit17, Lit4), $result);
                } else {
                    addToGlobalVars(Lit17, lambda$Fn15);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit18, Lit4), $result);
                } else {
                    addToGlobalVars(Lit18, lambda$Fn16);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit19, Lit4), $result);
                } else {
                    addToGlobalVars(Lit19, lambda$Fn17);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit20, Lit21, Lit22);
                    C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit23, "Counter", Lit24);
                    C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit25, Lit26, Lit22);
                    C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit27, "20200403_215919_0000.png", Lit24);
                    C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit28, Boolean.TRUE, Lit29);
                    Values.writeValues(C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit30, "Responsive", Lit24), $result);
                } else {
                    addToFormDoAfterCreation(new Promise(lambda$Fn18));
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    C0607runtime.addToCurrentFormEnvironment(Lit98, this.Screen1$Initialize);
                } else {
                    addToFormEnvironment(Lit98, this.Screen1$Initialize);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) C0607runtime.$Stthis$Mnform$St, "Screen1", "Initialize");
                } else {
                    addToEvents(Lit0, Lit99);
                }
                this.VerticalArrangement1 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit0, Lit100, Lit101, lambda$Fn19), $result);
                } else {
                    addToComponents(Lit0, Lit106, Lit101, lambda$Fn20);
                }
                this.TableArrangement3 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit0, Lit107, Lit108, lambda$Fn21), $result);
                } else {
                    addToComponents(Lit0, Lit112, Lit108, lambda$Fn22);
                }
                this.Spinner1 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit108, Lit113, Lit61, lambda$Fn23), $result);
                } else {
                    addToComponents(Lit108, Lit118, Lit61, lambda$Fn24);
                }
                this.Spinner3 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit108, Lit119, Lit58, lambda$Fn25), $result);
                } else {
                    addToComponents(Lit108, Lit121, Lit58, lambda$Fn26);
                }
                this.Label7 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit108, Lit122, Lit123, lambda$Fn27), $result);
                } else {
                    addToComponents(Lit108, Lit126, Lit123, lambda$Fn28);
                }
                this.Label8 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit108, Lit127, Lit128, lambda$Fn29), $result);
                } else {
                    addToComponents(Lit108, Lit130, Lit128, lambda$Fn30);
                }
                this.Label9 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit108, Lit131, Lit132, lambda$Fn31), $result);
                } else {
                    addToComponents(Lit108, Lit133, Lit132, lambda$Fn32);
                }
                this.Label10 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit108, Lit134, Lit135, lambda$Fn33), $result);
                } else {
                    addToComponents(Lit108, Lit136, Lit135, lambda$Fn34);
                }
                this.Label20 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit108, Lit137, Lit138, lambda$Fn35), $result);
                } else {
                    addToComponents(Lit108, Lit140, Lit138, lambda$Fn36);
                }
                this.Spinner2 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit108, Lit141, Lit63, lambda$Fn37), $result);
                } else {
                    addToComponents(Lit108, Lit143, Lit63, lambda$Fn38);
                }
                this.TableArrangement4 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit0, Lit144, Lit145, lambda$Fn39), $result);
                } else {
                    addToComponents(Lit0, Lit148, Lit145, lambda$Fn40);
                }
                this.Label11 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit145, Lit149, Lit150, lambda$Fn41), $result);
                } else {
                    addToComponents(Lit145, Lit151, Lit150, lambda$Fn42);
                }
                this.Spinner4 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit145, Lit152, Lit69, lambda$Fn43), $result);
                } else {
                    addToComponents(Lit145, Lit154, Lit69, lambda$Fn44);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    C0607runtime.addToCurrentFormEnvironment(Lit171, this.Spinner4$AfterSelecting);
                } else {
                    addToFormEnvironment(Lit171, this.Spinner4$AfterSelecting);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) C0607runtime.$Stthis$Mnform$St, "Spinner4", "AfterSelecting");
                } else {
                    addToEvents(Lit69, Lit172);
                }
                this.Label12 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit145, Lit173, Lit174, lambda$Fn45), $result);
                } else {
                    addToComponents(Lit145, Lit175, Lit174, lambda$Fn46);
                }
                this.Label13 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit145, Lit176, Lit177, lambda$Fn47), $result);
                } else {
                    addToComponents(Lit145, Lit179, Lit177, lambda$Fn48);
                }
                this.TextBox7 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit145, Lit180, Lit71, lambda$Fn49), $result);
                } else {
                    addToComponents(Lit145, Lit184, Lit71, lambda$Fn50);
                }
                this.TableArrangement1 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit0, Lit185, Lit186, lambda$Fn51), $result);
                } else {
                    addToComponents(Lit0, Lit189, Lit186, lambda$Fn52);
                }
                this.TextBox2 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit186, Lit190, Lit52, lambda$Fn53), $result);
                } else {
                    addToComponents(Lit186, Lit194, Lit52, lambda$Fn54);
                }
                this.Label1 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit186, Lit195, Lit196, lambda$Fn55), $result);
                } else {
                    addToComponents(Lit186, Lit197, Lit196, lambda$Fn56);
                }
                this.TextBox1 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit186, Lit198, Lit50, lambda$Fn57), $result);
                } else {
                    addToComponents(Lit186, Lit201, Lit50, lambda$Fn58);
                }
                this.Label2 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit186, Lit202, Lit203, lambda$Fn59), $result);
                } else {
                    addToComponents(Lit186, Lit204, Lit203, lambda$Fn60);
                }
                this.Label6 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit186, Lit205, Lit206, lambda$Fn61), $result);
                } else {
                    addToComponents(Lit186, Lit207, Lit206, lambda$Fn62);
                }
                this.TextBox6 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit186, Lit208, Lit56, lambda$Fn63), $result);
                } else {
                    addToComponents(Lit186, Lit211, Lit56, lambda$Fn64);
                }
                this.TableArrangement2 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit0, Lit212, Lit213, lambda$Fn65), $result);
                } else {
                    addToComponents(Lit0, Lit216, Lit213, lambda$Fn66);
                }
                this.TextBox3 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit213, Lit217, Lit53, lambda$Fn67), $result);
                } else {
                    addToComponents(Lit213, Lit220, Lit53, lambda$Fn68);
                }
                this.Label3 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit213, Lit221, Lit222, lambda$Fn69), $result);
                } else {
                    addToComponents(Lit213, Lit223, Lit222, lambda$Fn70);
                }
                this.Label5 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit213, Lit224, Lit225, lambda$Fn71), $result);
                } else {
                    addToComponents(Lit213, Lit226, Lit225, lambda$Fn72);
                }
                this.Label16 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit213, Lit227, Lit228, lambda$Fn73), $result);
                } else {
                    addToComponents(Lit213, Lit229, Lit228, lambda$Fn74);
                }
                this.TextBox5 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit213, Lit230, Lit55, lambda$Fn75), $result);
                } else {
                    addToComponents(Lit213, Lit233, Lit55, lambda$Fn76);
                }
                this.TextBox10 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit213, Lit234, Lit94, lambda$Fn77), $result);
                } else {
                    addToComponents(Lit213, Lit237, Lit94, lambda$Fn78);
                }
                this.TableArrangement5 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit0, Lit238, Lit239, lambda$Fn79), $result);
                } else {
                    addToComponents(Lit0, Lit242, Lit239, lambda$Fn80);
                }
                this.TextBox4 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit239, Lit243, Lit54, lambda$Fn81), $result);
                } else {
                    addToComponents(Lit239, Lit246, Lit54, lambda$Fn82);
                }
                this.Label4 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit239, Lit247, Lit248, lambda$Fn83), $result);
                } else {
                    addToComponents(Lit239, Lit249, Lit248, lambda$Fn84);
                }
                this.TextBox11 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit239, Lit250, Lit95, lambda$Fn85), $result);
                } else {
                    addToComponents(Lit239, Lit253, Lit95, lambda$Fn86);
                }
                this.Label17 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit239, Lit254, Lit255, lambda$Fn87), $result);
                } else {
                    addToComponents(Lit239, Lit256, Lit255, lambda$Fn88);
                }
                this.TableArrangement6 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit0, Lit257, Lit258, lambda$Fn89), $result);
                } else {
                    addToComponents(Lit0, Lit261, Lit258, lambda$Fn90);
                }
                this.TextBox12 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit258, Lit262, Lit96, lambda$Fn91), $result);
                } else {
                    addToComponents(Lit258, Lit265, Lit96, lambda$Fn92);
                }
                this.TextBox13 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit258, Lit266, Lit97, lambda$Fn93), $result);
                } else {
                    addToComponents(Lit258, Lit269, Lit97, lambda$Fn94);
                }
                this.Label18 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit258, Lit270, Lit271, lambda$Fn95), $result);
                } else {
                    addToComponents(Lit258, Lit272, Lit271, lambda$Fn96);
                }
                this.Label19 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit258, Lit273, Lit274, lambda$Fn97), $result);
                } else {
                    addToComponents(Lit258, Lit275, Lit274, lambda$Fn98);
                }
                this.VerticalArrangement3 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit0, Lit276, Lit277, lambda$Fn99), $result);
                } else {
                    addToComponents(Lit0, Lit280, Lit277, lambda$Fn100);
                }
                this.Button1 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit0, Lit281, Lit282, lambda$Fn101), $result);
                } else {
                    addToComponents(Lit0, Lit285, Lit282, lambda$Fn102);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    C0607runtime.addToCurrentFormEnvironment(Lit340, this.Button1$Click);
                } else {
                    addToFormEnvironment(Lit340, this.Button1$Click);
                }
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) C0607runtime.$Stthis$Mnform$St, "Button1", "Click");
                } else {
                    addToEvents(Lit282, Lit341);
                }
                this.TinyDB1 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit0, Lit342, Lit31, lambda$Fn103), $result);
                } else {
                    addToComponents(Lit0, Lit344, Lit31, lambda$Fn104);
                }
                this.Clock1 = null;
                if (C0607runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(C0607runtime.addComponentWithinRepl(Lit0, Lit345, Lit303, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit346, Lit303, Boolean.FALSE);
                }
                C0607runtime.initRuntime();
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Runnable.run()", 1, find2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "java.lang.Runnable.run()", 1, find);
        }
    }

    static IntNum lambda3() {
        return Lit4;
    }

    static IntNum lambda4() {
        return Lit4;
    }

    static IntNum lambda5() {
        return Lit4;
    }

    static IntNum lambda6() {
        return Lit4;
    }

    static IntNum lambda7() {
        return Lit4;
    }

    static IntNum lambda8() {
        return Lit4;
    }

    static IntNum lambda9() {
        return Lit4;
    }

    static IntNum lambda10() {
        return Lit4;
    }

    static IntNum lambda11() {
        return Lit4;
    }

    static IntNum lambda12() {
        return Lit4;
    }

    static IntNum lambda13() {
        return Lit4;
    }

    static IntNum lambda14() {
        return Lit4;
    }

    static IntNum lambda15() {
        return Lit4;
    }

    static IntNum lambda16() {
        return Lit4;
    }

    static IntNum lambda17() {
        return Lit4;
    }

    static IntNum lambda18() {
        return Lit4;
    }

    static Object lambda19() {
        C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit20, Lit21, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit23, "Counter", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit25, Lit26, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit27, "20200403_215919_0000.png", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit28, Boolean.TRUE, Lit29);
        return C0607runtime.setAndCoerceProperty$Ex(Lit0, Lit30, "Responsive", Lit24);
    }

    public Object Screen1$Initialize() {
        C0607runtime.setThisForm();
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit14, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit33, Lit4), Lit34));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit15, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit35, Lit4), Lit36));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit16, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit21, Lit4), Lit37));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit17, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit38, Lit4), Lit39));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit13, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit40, Lit4), Lit41));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit12, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit42, Lit4), Lit43));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit11, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit44, Lit4), Lit45));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit9, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit46, Lit4), Lit47));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit10, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit48, Lit4), Lit49));
        C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit14, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit15, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit17, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnnot$Mnequal$Qu, LList.list2(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit11, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit4), Lit57, "=") != Boolean.FALSE) {
            C0607runtime.setAndCoerceProperty$Ex(Lit58, Lit59, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit11, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        }
        if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnnot$Mnequal$Qu, LList.list2(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit4), Lit60, "=") != Boolean.FALSE) {
            C0607runtime.setAndCoerceProperty$Ex(Lit61, Lit59, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        }
        if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnnot$Mnequal$Qu, LList.list2(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit4), Lit62, "=") != Boolean.FALSE) {
            C0607runtime.setAndCoerceProperty$Ex(Lit63, Lit59, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        }
        if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnnot$Mnequal$Qu, LList.list2(C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit64, "0"), Lit65), Lit4), Lit66, "=") == Boolean.FALSE) {
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit51, "select country", Lit24);
        } else if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnequal$Qu, LList.list2(C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit64, ""), Lit67), Lit33), Lit68, "=") != Boolean.FALSE) {
            C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit59, "Brazil", Lit24);
            C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit19, Lit70);
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        } else if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnequal$Qu, LList.list2(C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit64, ""), Lit72), Lit35), Lit73, "=") != Boolean.FALSE) {
            C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit59, "USA", Lit24);
            C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit19, Lit74);
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        } else if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnequal$Qu, LList.list2(C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit64, ""), Lit75), Lit21), Lit76, "=") != Boolean.FALSE) {
            C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit59, "Germany", Lit24);
            C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit19, Lit77);
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        } else if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnequal$Qu, LList.list2(C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit64, ""), Lit78), Lit38), Lit79, "=") != Boolean.FALSE) {
            C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit59, "France", Lit24);
            C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit19, Lit80);
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        } else if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnequal$Qu, LList.list2(C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit64, ""), Lit81), Lit40), Lit82, "=") != Boolean.FALSE) {
            C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit59, "Other", Lit24);
            C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit19, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit83, "write it here"), Lit84));
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit85, Boolean.FALSE, Lit29);
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        }
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit5, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit86, Lit4), Lit87));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit18, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit88, Lit4), Lit89));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit7, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit90, Lit4), Lit91));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit6, C0607runtime.callComponentMethod(Lit31, Lit32, LList.list2(Lit92, Lit4), Lit93));
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit18, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit6, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
    }

    static Object lambda20() {
        C0607runtime.setAndCoerceProperty$Ex(Lit101, Lit102, Lit103, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit101, Lit104, Lit105, Lit22);
    }

    static Object lambda21() {
        C0607runtime.setAndCoerceProperty$Ex(Lit101, Lit102, Lit103, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit101, Lit104, Lit105, Lit22);
    }

    static Object lambda22() {
        C0607runtime.setAndCoerceProperty$Ex(Lit108, Lit109, Lit38, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit108, Lit102, Lit110, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit108, Lit104, Lit111, Lit22);
    }

    static Object lambda23() {
        C0607runtime.setAndCoerceProperty$Ex(Lit108, Lit109, Lit38, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit108, Lit102, Lit110, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit108, Lit104, Lit111, Lit22);
    }

    static Object lambda24() {
        C0607runtime.setAndCoerceProperty$Ex(Lit61, Lit114, Lit21, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit61, Lit115, "2021, 2020, 2019, 2018, 2017, 2016, 2015, 2014, 2013, 2012, 2011, 2010, 2009, 2008", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit61, Lit116, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit61, Lit104, Lit117, Lit22);
    }

    static Object lambda25() {
        C0607runtime.setAndCoerceProperty$Ex(Lit61, Lit114, Lit21, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit61, Lit115, "2021, 2020, 2019, 2018, 2017, 2016, 2015, 2014, 2013, 2012, 2011, 2010, 2009, 2008", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit61, Lit116, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit61, Lit104, Lit117, Lit22);
    }

    static Object lambda26() {
        C0607runtime.setAndCoerceProperty$Ex(Lit58, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit58, Lit115, "1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit58, Lit116, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit58, Lit104, Lit120, Lit22);
    }

    static Object lambda27() {
        C0607runtime.setAndCoerceProperty$Ex(Lit58, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit58, Lit115, "1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit58, Lit116, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit58, Lit104, Lit120, Lit22);
    }

    static Object lambda28() {
        C0607runtime.setAndCoerceProperty$Ex(Lit123, Lit114, Lit21, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit123, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit123, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit123, Lit51, "Year", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit123, Lit125, Lit33, Lit22);
    }

    static Object lambda29() {
        C0607runtime.setAndCoerceProperty$Ex(Lit123, Lit114, Lit21, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit123, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit123, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit123, Lit51, "Year", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit123, Lit125, Lit33, Lit22);
    }

    static Object lambda30() {
        C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit51, "using:", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit104, Lit129, Lit22);
    }

    static Object lambda31() {
        C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit51, "using:", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit128, Lit104, Lit129, Lit22);
    }

    static Object lambda32() {
        C0607runtime.setAndCoerceProperty$Ex(Lit132, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit132, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit132, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit132, Lit51, "Day", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit132, Lit125, Lit33, Lit22);
    }

    static Object lambda33() {
        C0607runtime.setAndCoerceProperty$Ex(Lit132, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit132, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit132, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit132, Lit51, "Day", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit132, Lit125, Lit33, Lit22);
    }

    static Object lambda34() {
        C0607runtime.setAndCoerceProperty$Ex(Lit135, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit135, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit135, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit135, Lit51, "Month", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit135, Lit125, Lit33, Lit22);
    }

    static Object lambda35() {
        C0607runtime.setAndCoerceProperty$Ex(Lit135, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit135, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit135, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit135, Lit51, "Month", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit135, Lit125, Lit33, Lit22);
    }

    static Object lambda36() {
        C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit51, "Started", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit104, Lit139, Lit22);
    }

    static Object lambda37() {
        C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit51, "Started", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit138, Lit104, Lit139, Lit22);
    }

    static Object lambda38() {
        C0607runtime.setAndCoerceProperty$Ex(Lit63, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit63, Lit115, "1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit63, Lit116, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit63, Lit104, Lit142, Lit22);
    }

    static Object lambda39() {
        C0607runtime.setAndCoerceProperty$Ex(Lit63, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit63, Lit115, "1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit63, Lit116, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit63, Lit104, Lit142, Lit22);
    }

    static Object lambda40() {
        C0607runtime.setAndCoerceProperty$Ex(Lit145, Lit109, Lit21, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit145, Lit102, Lit146, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit145, Lit104, Lit147, Lit22);
    }

    static Object lambda41() {
        C0607runtime.setAndCoerceProperty$Ex(Lit145, Lit109, Lit21, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit145, Lit102, Lit146, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit145, Lit104, Lit147, Lit22);
    }

    static Object lambda42() {
        C0607runtime.setAndCoerceProperty$Ex(Lit150, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit150, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit150, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit150, Lit51, "Country:", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit150, Lit125, Lit33, Lit22);
    }

    static Object lambda43() {
        C0607runtime.setAndCoerceProperty$Ex(Lit150, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit150, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit150, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit150, Lit51, "Country:", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit150, Lit125, Lit33, Lit22);
    }

    static Object lambda44() {
        C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit115, "Brazil, USA, Germany, France, Other", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit116, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit104, Lit153, Lit22);
    }

    static Object lambda45() {
        C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit115, "Brazil, USA, Germany, France, Other", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit116, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit69, Lit104, Lit153, Lit22);
    }

    public Object Spinner4$AfterSelecting(Object $selection) {
        C0607runtime.sanitizeComponentData($selection);
        C0607runtime.setThisForm();
        if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnnot$Mnequal$Qu, LList.list2(C0607runtime.getProperty$1(Lit69, Lit59), "Other"), Lit155, "=") != Boolean.FALSE) {
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit85, Boolean.TRUE, Lit29);
        }
        if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnequal$Qu, LList.list2(C0607runtime.getProperty$1(Lit69, Lit59), "Brazil"), Lit156, "=") != Boolean.FALSE) {
            C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit19, Lit157);
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
            return C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit64, Lit33), Lit159);
        } else if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnequal$Qu, LList.list2(C0607runtime.getProperty$1(Lit69, Lit59), "USA"), Lit160, "=") != Boolean.FALSE) {
            C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit19, Lit161);
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
            return C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit64, Lit35), Lit162);
        } else if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnequal$Qu, LList.list2(C0607runtime.getProperty$1(Lit69, Lit59), "Germany"), Lit163, "=") != Boolean.FALSE) {
            C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit19, Lit164);
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit15, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
            return C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit64, Lit21), Lit165);
        } else if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnequal$Qu, LList.list2(C0607runtime.getProperty$1(Lit69, Lit59), "France"), Lit166, "=") != Boolean.FALSE) {
            C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit19, Lit167);
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
            return C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit64, Lit38), Lit168);
        } else if (C0607runtime.callYailPrimitive(C0607runtime.yail$Mnequal$Qu, LList.list2(C0607runtime.getProperty$1(Lit69, Lit59), "Other"), Lit169, "=") == Boolean.FALSE) {
            return Values.empty;
        } else {
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit85, Boolean.FALSE, Lit29);
            C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
            return C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit64, Lit40), Lit170);
        }
    }

    static Object lambda46() {
        C0607runtime.setAndCoerceProperty$Ex(Lit174, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit174, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit174, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit174, Lit51, "Carbon Footprint:", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit174, Lit125, Lit33, Lit22);
    }

    static Object lambda47() {
        C0607runtime.setAndCoerceProperty$Ex(Lit174, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit174, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit174, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit174, Lit51, "Carbon Footprint:", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit174, Lit125, Lit33, Lit22);
    }

    static Object lambda48() {
        C0607runtime.setAndCoerceProperty$Ex(Lit177, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit177, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit177, Lit178, Lit92, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit177, Lit116, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit177, Lit51, "Kg", Lit24);
    }

    static Object lambda49() {
        C0607runtime.setAndCoerceProperty$Ex(Lit177, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit177, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit177, Lit178, Lit92, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit177, Lit116, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit177, Lit51, "Kg", Lit24);
    }

    static Object lambda50() {
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit102, Lit181, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit182, "write it here", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit104, Lit183, Lit22);
    }

    static Object lambda51() {
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit102, Lit181, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit182, "write it here", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit71, Lit104, Lit183, Lit22);
    }

    static Object lambda52() {
        C0607runtime.setAndCoerceProperty$Ex(Lit186, Lit109, Lit21, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit186, Lit102, Lit187, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit186, Lit104, Lit188, Lit22);
    }

    static Object lambda53() {
        C0607runtime.setAndCoerceProperty$Ex(Lit186, Lit109, Lit21, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit186, Lit102, Lit187, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit186, Lit104, Lit188, Lit22);
    }

    static Object lambda54() {
        C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit102, Lit191, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit104, Lit193, Lit22);
    }

    static Object lambda55() {
        C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit102, Lit191, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit52, Lit104, Lit193, Lit22);
    }

    static Object lambda56() {
        C0607runtime.setAndCoerceProperty$Ex(Lit196, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit196, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit196, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit196, Lit51, "Phone", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit196, Lit125, Lit33, Lit22);
    }

    static Object lambda57() {
        C0607runtime.setAndCoerceProperty$Ex(Lit196, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit196, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit196, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit196, Lit51, "Phone", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit196, Lit125, Lit33, Lit22);
    }

    static Object lambda58() {
        C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit102, Lit199, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit104, Lit200, Lit22);
    }

    static Object lambda59() {
        C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit102, Lit199, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit50, Lit104, Lit200, Lit22);
    }

    static Object lambda60() {
        C0607runtime.setAndCoerceProperty$Ex(Lit203, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit203, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit203, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit203, Lit51, "Computer", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit203, Lit125, Lit33, Lit22);
    }

    static Object lambda61() {
        C0607runtime.setAndCoerceProperty$Ex(Lit203, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit203, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit203, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit203, Lit51, "Computer", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit203, Lit125, Lit33, Lit22);
    }

    static Object lambda62() {
        C0607runtime.setAndCoerceProperty$Ex(Lit206, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit206, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit206, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit206, Lit51, "Others", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit206, Lit125, Lit33, Lit22);
    }

    static Object lambda63() {
        C0607runtime.setAndCoerceProperty$Ex(Lit206, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit206, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit206, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit206, Lit51, "Others", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit206, Lit125, Lit33, Lit22);
    }

    static Object lambda64() {
        C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit102, Lit209, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit104, Lit210, Lit22);
    }

    static Object lambda65() {
        C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit102, Lit209, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit56, Lit104, Lit210, Lit22);
    }

    static Object lambda66() {
        C0607runtime.setAndCoerceProperty$Ex(Lit213, Lit109, Lit21, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit213, Lit102, Lit214, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit213, Lit104, Lit215, Lit22);
    }

    static Object lambda67() {
        C0607runtime.setAndCoerceProperty$Ex(Lit213, Lit109, Lit21, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit213, Lit102, Lit214, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit213, Lit104, Lit215, Lit22);
    }

    static Object lambda68() {
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit102, Lit218, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit104, Lit219, Lit22);
    }

    static Object lambda69() {
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit102, Lit218, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit104, Lit219, Lit22);
    }

    static Object lambda70() {
        C0607runtime.setAndCoerceProperty$Ex(Lit222, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit222, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit222, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit222, Lit51, "Total Searches", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit222, Lit125, Lit33, Lit22);
    }

    static Object lambda71() {
        C0607runtime.setAndCoerceProperty$Ex(Lit222, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit222, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit222, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit222, Lit51, "Total Searches", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit222, Lit125, Lit33, Lit22);
    }

    static Object lambda72() {
        C0607runtime.setAndCoerceProperty$Ex(Lit225, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit225, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit225, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit225, Lit51, "To The Next", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit225, Lit125, Lit33, Lit22);
    }

    static Object lambda73() {
        C0607runtime.setAndCoerceProperty$Ex(Lit225, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit225, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit225, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit225, Lit51, "To The Next", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit225, Lit125, Lit33, Lit22);
    }

    static Object lambda74() {
        C0607runtime.setAndCoerceProperty$Ex(Lit228, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit228, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit228, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit228, Lit51, "Searches/day", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit228, Lit125, Lit33, Lit22);
    }

    static Object lambda75() {
        C0607runtime.setAndCoerceProperty$Ex(Lit228, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit228, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit228, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit228, Lit51, "Searches/day", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit228, Lit125, Lit33, Lit22);
    }

    static Object lambda76() {
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit102, Lit231, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit104, Lit232, Lit22);
    }

    static Object lambda77() {
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit102, Lit231, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit104, Lit232, Lit22);
    }

    static Object lambda78() {
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit102, Lit235, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit182, "Hint for TextBox10", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit104, Lit236, Lit22);
    }

    static Object lambda79() {
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit114, Lit35, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit102, Lit235, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit182, "Hint for TextBox10", Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit104, Lit236, Lit22);
    }

    static Object lambda80() {
        C0607runtime.setAndCoerceProperty$Ex(Lit239, Lit102, Lit240, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit239, Lit104, Lit241, Lit22);
    }

    static Object lambda81() {
        C0607runtime.setAndCoerceProperty$Ex(Lit239, Lit102, Lit240, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit239, Lit104, Lit241, Lit22);
    }

    static Object lambda82() {
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit102, Lit244, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit104, Lit245, Lit22);
    }

    static Object lambda83() {
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit102, Lit244, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit104, Lit245, Lit22);
    }

    static Object lambda84() {
        C0607runtime.setAndCoerceProperty$Ex(Lit248, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit248, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit248, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit248, Lit51, "Planted Trees", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit248, Lit125, Lit33, Lit22);
    }

    static Object lambda85() {
        C0607runtime.setAndCoerceProperty$Ex(Lit248, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit248, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit248, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit248, Lit51, "Planted Trees", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit248, Lit125, Lit33, Lit22);
    }

    static Object lambda86() {
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit102, Lit251, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit104, Lit252, Lit22);
    }

    static Object lambda87() {
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit102, Lit251, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit104, Lit252, Lit22);
    }

    static Object lambda88() {
        C0607runtime.setAndCoerceProperty$Ex(Lit255, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit255, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit255, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit255, Lit51, "Trees/day", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit255, Lit125, Lit33, Lit22);
    }

    static Object lambda89() {
        C0607runtime.setAndCoerceProperty$Ex(Lit255, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit255, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit255, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit255, Lit51, "Trees/day", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit255, Lit125, Lit33, Lit22);
    }

    static Object lambda90() {
        C0607runtime.setAndCoerceProperty$Ex(Lit258, Lit102, Lit259, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit258, Lit104, Lit260, Lit22);
    }

    static Object lambda91() {
        C0607runtime.setAndCoerceProperty$Ex(Lit258, Lit102, Lit259, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit258, Lit104, Lit260, Lit22);
    }

    static Object lambda92() {
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit102, Lit263, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit104, Lit264, Lit22);
    }

    static Object lambda93() {
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit102, Lit263, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit104, Lit264, Lit22);
    }

    static Object lambda94() {
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit102, Lit267, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit104, Lit268, Lit22);
    }

    static Object lambda95() {
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit102, Lit267, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit192, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit85, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit116, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit125, Lit33, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit104, Lit268, Lit22);
    }

    static Object lambda96() {
        C0607runtime.setAndCoerceProperty$Ex(Lit271, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit271, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit271, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit271, Lit51, "Yearly Footprints", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit271, Lit125, Lit33, Lit22);
    }

    static Object lambda97() {
        C0607runtime.setAndCoerceProperty$Ex(Lit271, Lit114, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit271, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit271, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit271, Lit51, "Yearly Footprints", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit271, Lit125, Lit33, Lit22);
    }

    static Object lambda98() {
        C0607runtime.setAndCoerceProperty$Ex(Lit274, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit274, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit274, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit274, Lit51, "Relative Footprints", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit274, Lit125, Lit33, Lit22);
    }

    static Object lambda99() {
        C0607runtime.setAndCoerceProperty$Ex(Lit274, Lit114, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit274, Lit124, Boolean.TRUE, Lit29);
        C0607runtime.setAndCoerceProperty$Ex(Lit274, Lit116, Lit4, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit274, Lit51, "Relative Footprints", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit274, Lit125, Lit33, Lit22);
    }

    static Object lambda100() {
        C0607runtime.setAndCoerceProperty$Ex(Lit277, Lit102, Lit278, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit277, Lit104, Lit279, Lit22);
    }

    static Object lambda101() {
        C0607runtime.setAndCoerceProperty$Ex(Lit277, Lit102, Lit278, Lit22);
        return C0607runtime.setAndCoerceProperty$Ex(Lit277, Lit104, Lit279, Lit22);
    }

    static Object lambda102() {
        C0607runtime.setAndCoerceProperty$Ex(Lit282, Lit283, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit282, Lit51, "CALCULATE", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit282, Lit104, Lit284, Lit22);
    }

    static Object lambda103() {
        C0607runtime.setAndCoerceProperty$Ex(Lit282, Lit283, Lit33, Lit22);
        C0607runtime.setAndCoerceProperty$Ex(Lit282, Lit51, "CALCULATE", Lit24);
        return C0607runtime.setAndCoerceProperty$Ex(Lit282, Lit104, Lit284, Lit22);
    }

    public Object Button1$Click() {
        C0607runtime.setThisForm();
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit14, C0607runtime.getProperty$1(Lit50, Lit51));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit15, C0607runtime.getProperty$1(Lit52, Lit51));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit12, C0607runtime.getProperty$1(Lit56, Lit51));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit16, C0607runtime.callYailPrimitive(AddOp.$Pl, LList.list3(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit14, C0607runtime.$Stthe$Mnnull$Mnvalue$St), C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit15, C0607runtime.$Stthe$Mnnull$Mnvalue$St), C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, C0607runtime.$Stthe$Mnnull$Mnvalue$St)), Lit286, "+"));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit17, C0607runtime.callYailPrimitive(C0607runtime.yail$Mnfloor, LList.list1(C0607runtime.callYailPrimitive(C0607runtime.yail$Mndivide, LList.list2(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit287), Lit288, "yail-divide")), Lit289, "floor"));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit13, C0607runtime.callYailPrimitive(AddOp.$Mn, LList.list2(Lit287, C0607runtime.callYailPrimitive(AddOp.$Mn, LList.list2(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, C0607runtime.$Stthe$Mnnull$Mnvalue$St), C0607runtime.callYailPrimitive(MultiplyOp.$St, LList.list2(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit17, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit287), Lit290, "*")), Lit291, "-")), Lit292, "-"));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit10, C0607runtime.getProperty$1(Lit63, Lit59));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit11, C0607runtime.getProperty$1(Lit58, Lit59));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit9, C0607runtime.getProperty$1(Lit61, Lit59));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit19, C0607runtime.getProperty$1(Lit71, Lit51));
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit83, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, C0607runtime.$Stthe$Mnnull$Mnvalue$St)), Lit293);
        C0607runtime.setAndCoerceProperty$Ex(Lit53, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit54, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit17, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit55, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit33, C0607runtime.getProperty$1(Lit50, Lit51)), Lit294);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit35, C0607runtime.getProperty$1(Lit52, Lit51)), Lit295);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit21, C0607runtime.getProperty$1(Lit53, Lit51)), Lit296);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit38, C0607runtime.getProperty$1(Lit54, Lit51)), Lit297);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit40, C0607runtime.getProperty$1(Lit55, Lit51)), Lit298);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit42, C0607runtime.getProperty$1(Lit56, Lit51)), Lit299);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit44, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit11, C0607runtime.$Stthe$Mnnull$Mnvalue$St)), Lit300);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit46, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, C0607runtime.$Stthe$Mnnull$Mnvalue$St)), Lit301);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit48, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, C0607runtime.$Stthe$Mnnull$Mnvalue$St)), Lit302);
        C0607runtime.setAndCoerceProperty$Ex(Lit303, Lit304, C0607runtime.callComponentMethod(Lit303, Lit305, LList.list1(C0607runtime.callComponentMethod(Lit303, Lit306, LList.list2(C0607runtime.callComponentMethod(Lit303, Lit307, LList.list1(C0607runtime.callComponentMethod(Lit303, Lit308, LList.list2(C0607runtime.callComponentMethod(Lit303, Lit309, LList.list3(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, C0607runtime.$Stthe$Mnnull$Mnvalue$St), C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit11, C0607runtime.$Stthe$Mnnull$Mnvalue$St), C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, C0607runtime.$Stthe$Mnnull$Mnvalue$St)), Lit310), "MM/dd/YYYY"), Lit311)), Lit312), C0607runtime.callComponentMethod(Lit303, Lit307, LList.list1(C0607runtime.callComponentMethod(Lit303, Lit308, LList.list2(C0607runtime.callComponentMethod(Lit303, Lit309, LList.list3(C0607runtime.callComponentMethod(Lit303, Lit313, LList.list1(C0607runtime.callComponentMethod(Lit303, Lit314, LList.Empty, LList.Empty)), Lit315), C0607runtime.callComponentMethod(Lit303, Lit316, LList.list1(C0607runtime.callComponentMethod(Lit303, Lit314, LList.Empty, LList.Empty)), Lit317), C0607runtime.callComponentMethod(Lit303, Lit318, LList.list1(C0607runtime.callComponentMethod(Lit303, Lit314, LList.Empty, LList.Empty)), Lit319)), Lit320), "MM/dd/YYYY"), Lit321)), Lit322)), Lit323)), Lit324), Lit22);
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit8, C0607runtime.callYailPrimitive(C0607runtime.yail$Mndivide, LList.list2(C0607runtime.getProperty$1(Lit303, Lit304), Lit325), Lit326, "yail-divide"));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit7, C0607runtime.callYailPrimitive(C0607runtime.format$Mnas$Mndecimal, LList.list2(C0607runtime.callYailPrimitive(C0607runtime.yail$Mndivide, LList.list2(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, C0607runtime.$Stthe$Mnnull$Mnvalue$St), C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit19, C0607runtime.$Stthe$Mnnull$Mnvalue$St)), Lit327, "yail-divide"), Lit35), Lit328, "format as decimal"));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit6, C0607runtime.callYailPrimitive(C0607runtime.format$Mnas$Mndecimal, LList.list2(C0607runtime.callYailPrimitive(C0607runtime.yail$Mndivide, LList.list2(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, C0607runtime.$Stthe$Mnnull$Mnvalue$St), C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit8, C0607runtime.$Stthe$Mnnull$Mnvalue$St)), Lit329, "yail-divide"), Lit35), Lit330, "format as decimal"));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit18, C0607runtime.callYailPrimitive(C0607runtime.format$Mnas$Mndecimal, LList.list2(C0607runtime.callYailPrimitive(C0607runtime.yail$Mndivide, LList.list2(C0607runtime.callYailPrimitive(C0607runtime.yail$Mndivide, LList.list2(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit287), Lit331, "yail-divide"), C0607runtime.getProperty$1(Lit303, Lit304)), Lit332, "yail-divide"), Lit35), Lit333, "format as decimal"));
        C0607runtime.addGlobalVarToCurrentFormEnvironment(Lit5, C0607runtime.callYailPrimitive(C0607runtime.format$Mnas$Mndecimal, LList.list2(C0607runtime.callYailPrimitive(C0607runtime.yail$Mndivide, LList.list2(C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, C0607runtime.$Stthe$Mnnull$Mnvalue$St), C0607runtime.getProperty$1(Lit303, Lit304)), Lit334, "yail-divide"), Lit35), Lit335, "format as decimal"));
        C0607runtime.setAndCoerceProperty$Ex(Lit94, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit95, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit18, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit96, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.setAndCoerceProperty$Ex(Lit97, Lit51, C0607runtime.lookupGlobalVarInCurrentFormEnvironment(Lit6, C0607runtime.$Stthe$Mnnull$Mnvalue$St), Lit24);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit86, C0607runtime.getProperty$1(Lit94, Lit51)), Lit336);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit88, C0607runtime.getProperty$1(Lit95, Lit51)), Lit337);
        C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit90, C0607runtime.getProperty$1(Lit96, Lit51)), Lit338);
        return C0607runtime.callComponentMethod(Lit31, Lit158, LList.list2(Lit92, C0607runtime.getProperty$1(Lit97, Lit51)), Lit339);
    }

    static Object lambda104() {
        return C0607runtime.setAndCoerceProperty$Ex(Lit31, Lit343, "TCel", Lit24);
    }

    static Object lambda105() {
        return C0607runtime.setAndCoerceProperty$Ex(Lit31, Lit343, "TCel", Lit24);
    }

    public String getSimpleName(Object object) {
        return object.getClass().getSimpleName();
    }

    public void onCreate(Bundle icicle) {
        AppInventorCompatActivity.setClassicModeFromYail(true);
        super.onCreate(icicle);
    }

    public void androidLogForm(Object message) {
    }

    public void addToFormEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.form$Mnenvironment, object));
        this.form$Mnenvironment.put(name, object);
    }

    public Object lookupInFormEnvironment(Symbol name, Object default$Mnvalue) {
        boolean x = ((this.form$Mnenvironment == null ? 1 : 0) + 1) & true;
        if (x) {
            if (!this.form$Mnenvironment.isBound(name)) {
                return default$Mnvalue;
            }
        } else if (!x) {
            return default$Mnvalue;
        }
        return this.form$Mnenvironment.get(name);
    }

    public boolean isBoundInFormEnvironment(Symbol name) {
        return this.form$Mnenvironment.isBound(name);
    }

    public void addToGlobalVarEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.global$Mnvar$Mnenvironment, object));
        this.global$Mnvar$Mnenvironment.put(name, object);
    }

    public void addToEvents(Object component$Mnname, Object event$Mnname) {
        this.events$Mnto$Mnregister = C0619lists.cons(C0619lists.cons(component$Mnname, event$Mnname), this.events$Mnto$Mnregister);
    }

    public void addToComponents(Object container$Mnname, Object component$Mntype, Object component$Mnname, Object init$Mnthunk) {
        this.components$Mnto$Mncreate = C0619lists.cons(LList.list4(container$Mnname, component$Mntype, component$Mnname, init$Mnthunk), this.components$Mnto$Mncreate);
    }

    public void addToGlobalVars(Object var, Object val$Mnthunk) {
        this.global$Mnvars$Mnto$Mncreate = C0619lists.cons(LList.list2(var, val$Mnthunk), this.global$Mnvars$Mnto$Mncreate);
    }

    public void addToFormDoAfterCreation(Object thunk) {
        this.form$Mndo$Mnafter$Mncreation = C0619lists.cons(thunk, this.form$Mndo$Mnafter$Mncreation);
    }

    public void sendError(Object error) {
        RetValManager.sendError(error == null ? null : error.toString());
    }

    public void processException(Object ex) {
        Object apply1 = Scheme.applyToArgs.apply1(GetNamedPart.getNamedPart.apply2(ex, Lit1));
        RuntimeErrorAlert.alert(this, apply1 == null ? null : apply1.toString(), ex instanceof YailRuntimeError ? ((YailRuntimeError) ex).getErrorType() : "Runtime Error", "End Application");
    }

    public boolean dispatchEvent(Component componentObject, String registeredComponentName, String eventName, Object[] args) {
        boolean x;
        SimpleSymbol registeredObject = misc.string$To$Symbol(registeredComponentName);
        if (!isBoundInFormEnvironment(registeredObject)) {
            EventDispatcher.unregisterEventForDelegation(this, registeredComponentName, eventName);
            return false;
        } else if (lookupInFormEnvironment(registeredObject) != componentObject) {
            return false;
        } else {
            try {
                Scheme.apply.apply2(lookupHandler(registeredComponentName, eventName), LList.makeList(args, 0));
                return true;
            } catch (PermissionException exception) {
                exception.printStackTrace();
                if (this == componentObject) {
                    x = true;
                } else {
                    x = false;
                }
                if (!x ? x : IsEqual.apply(eventName, "PermissionNeeded")) {
                    processException(exception);
                } else {
                    PermissionDenied(componentObject, eventName, exception.getPermissionNeeded());
                }
                return false;
            } catch (Throwable exception2) {
                androidLogForm(exception2.getMessage());
                exception2.printStackTrace();
                processException(exception2);
                return false;
            }
        }
    }

    public void dispatchGenericEvent(Component componentObject, String eventName, boolean notAlreadyHandled, Object[] args) {
        Boolean bool;
        boolean x = true;
        Object handler = lookupInFormEnvironment(misc.string$To$Symbol(strings.stringAppend("any$", getSimpleName(componentObject), "$", eventName)));
        if (handler != Boolean.FALSE) {
            try {
                Apply apply = Scheme.apply;
                if (notAlreadyHandled) {
                    bool = Boolean.TRUE;
                } else {
                    bool = Boolean.FALSE;
                }
                apply.apply2(handler, C0619lists.cons(componentObject, C0619lists.cons(bool, LList.makeList(args, 0))));
            } catch (PermissionException exception) {
                exception.printStackTrace();
                if (this != componentObject) {
                    x = false;
                }
                if (!x ? x : IsEqual.apply(eventName, "PermissionNeeded")) {
                    processException(exception);
                } else {
                    PermissionDenied(componentObject, eventName, exception.getPermissionNeeded());
                }
            } catch (Throwable exception2) {
                androidLogForm(exception2.getMessage());
                exception2.printStackTrace();
                processException(exception2);
            }
        }
    }

    public Object lookupHandler(Object componentName, Object eventName) {
        String str = null;
        String obj = componentName == null ? null : componentName.toString();
        if (eventName != null) {
            str = eventName.toString();
        }
        return lookupInFormEnvironment(misc.string$To$Symbol(EventDispatcher.makeFullEventName(obj, str)));
    }

    public void $define() {
        Object reverse;
        Object obj;
        Object reverse2;
        Object obj2;
        Object obj3;
        Object var;
        Object component$Mnname;
        Object obj4;
        Language.setDefaults(Scheme.getInstance());
        try {
            run();
        } catch (Exception exception) {
            androidLogForm(exception.getMessage());
            processException(exception);
        }
        Screen1 = this;
        addToFormEnvironment(Lit0, this);
        Object obj5 = this.events$Mnto$Mnregister;
        while (obj5 != LList.Empty) {
            try {
                Pair arg0 = (Pair) obj5;
                Object event$Mninfo = arg0.getCar();
                Object apply1 = C0619lists.car.apply1(event$Mninfo);
                String obj6 = apply1 == null ? null : apply1.toString();
                Object apply12 = C0619lists.cdr.apply1(event$Mninfo);
                EventDispatcher.registerEventForDelegation(this, obj6, apply12 == null ? null : apply12.toString());
                obj5 = arg0.getCdr();
            } catch (ClassCastException e) {
                WrongType wrongType = new WrongType(e, "arg0", -2, obj5);
                throw wrongType;
            }
        }
        try {
            LList components = C0619lists.reverse(this.components$Mnto$Mncreate);
            addToGlobalVars(Lit2, lambda$Fn1);
            reverse = C0619lists.reverse(this.form$Mndo$Mnafter$Mncreation);
            while (reverse != LList.Empty) {
                Pair arg02 = (Pair) reverse;
                misc.force(arg02.getCar());
                reverse = arg02.getCdr();
            }
            obj = components;
            while (obj != LList.Empty) {
                Pair arg03 = (Pair) obj;
                Object component$Mninfo = arg03.getCar();
                component$Mnname = C0619lists.caddr.apply1(component$Mninfo);
                C0619lists.cadddr.apply1(component$Mninfo);
                Object component$Mnobject = Invoke.make.apply2(C0619lists.cadr.apply1(component$Mninfo), lookupInFormEnvironment((Symbol) C0619lists.car.apply1(component$Mninfo)));
                SlotSet.set$Mnfield$Ex.apply3(this, component$Mnname, component$Mnobject);
                addToFormEnvironment((Symbol) component$Mnname, component$Mnobject);
                obj = arg03.getCdr();
            }
            reverse2 = C0619lists.reverse(this.global$Mnvars$Mnto$Mncreate);
            while (reverse2 != LList.Empty) {
                Pair arg04 = (Pair) reverse2;
                Object var$Mnval = arg04.getCar();
                var = C0619lists.car.apply1(var$Mnval);
                addToGlobalVarEnvironment((Symbol) var, Scheme.applyToArgs.apply1(C0619lists.cadr.apply1(var$Mnval)));
                reverse2 = arg04.getCdr();
            }
            Object obj7 = components;
            obj2 = obj7;
            while (obj2 != LList.Empty) {
                Pair arg05 = (Pair) obj2;
                Object component$Mninfo2 = arg05.getCar();
                C0619lists.caddr.apply1(component$Mninfo2);
                Object init$Mnthunk = C0619lists.cadddr.apply1(component$Mninfo2);
                if (init$Mnthunk != Boolean.FALSE) {
                    Scheme.applyToArgs.apply1(init$Mnthunk);
                }
                obj2 = arg05.getCdr();
            }
            obj3 = obj7;
            while (obj3 != LList.Empty) {
                Pair arg06 = (Pair) obj3;
                Object component$Mninfo3 = arg06.getCar();
                Object component$Mnname2 = C0619lists.caddr.apply1(component$Mninfo3);
                C0619lists.cadddr.apply1(component$Mninfo3);
                callInitialize(SlotGet.field.apply2(this, component$Mnname2));
                obj3 = arg06.getCdr();
            }
        } catch (ClassCastException e2) {
            WrongType wrongType2 = new WrongType(e2, "arg0", -2, obj3);
            throw wrongType2;
        } catch (ClassCastException e3) {
            WrongType wrongType3 = new WrongType(e3, "arg0", -2, obj2);
            throw wrongType3;
        } catch (ClassCastException e4) {
            WrongType wrongType4 = new WrongType(e4, "add-to-global-var-environment", 0, var);
            throw wrongType4;
        } catch (ClassCastException e5) {
            WrongType wrongType5 = new WrongType(e5, "arg0", -2, reverse2);
            throw wrongType5;
        } catch (ClassCastException e6) {
            WrongType wrongType6 = new WrongType(e6, "add-to-form-environment", 0, component$Mnname);
            throw wrongType6;
        } catch (ClassCastException e7) {
            WrongType wrongType7 = new WrongType(e7, "lookup-in-form-environment", 0, obj4);
            throw wrongType7;
        } catch (ClassCastException e8) {
            WrongType wrongType8 = new WrongType(e8, "arg0", -2, obj);
            throw wrongType8;
        } catch (ClassCastException e9) {
            WrongType wrongType9 = new WrongType(e9, "arg0", -2, reverse);
            throw wrongType9;
        } catch (YailRuntimeError exception2) {
            processException(exception2);
        }
    }

    public static SimpleSymbol lambda1symbolAppend$V(Object[] argsArray) {
        LList symbols = LList.makeList(argsArray, 0);
        Apply apply = Scheme.apply;
        ModuleMethod moduleMethod = strings.string$Mnappend;
        Object obj = LList.Empty;
        Object obj2 = symbols;
        while (obj2 != LList.Empty) {
            try {
                Pair arg0 = (Pair) obj2;
                Object arg02 = arg0.getCdr();
                Object car = arg0.getCar();
                try {
                    obj = Pair.make(misc.symbol$To$String((Symbol) car), obj);
                    obj2 = arg02;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "symbol->string", 1, car);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, obj2);
            }
        }
        Object apply2 = apply.apply2(moduleMethod, LList.reverseInPlace(obj));
        try {
            return misc.string$To$Symbol((CharSequence) apply2);
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "string->symbol", 1, apply2);
        }
    }

    static Object lambda2() {
        return null;
    }
}
