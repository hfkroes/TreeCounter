package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.text.TextUtils;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;
import com.google.appinventor.components.runtime.util.Ev3Constants.SystemCommand;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

@SimpleObject
@DesignerComponent(category = ComponentCategory.MEDIA, description = "Use this component to translate words and sentences between different languages. This component needs Internet access, as it will request translations to the Yandex.Translate service. Specify the source and target language in the form source-target using two letter language codes. So\"en-es\" will translate from English to Spanish while \"es-ru\" will translate from Spanish to Russian. If you leave out the source language, the service will attempt to detect the source language. So providing just \"es\" will attempt to detect the source language and translate it to Spanish.<p /> This component is powered by the Yandex translation service.  See http://api.yandex.com/translate/ for more information, including the list of available languages and the meanings of the language codes and status codes. <p />Note: Translation happens asynchronously in the background. When the translation is complete, the \"GotTranslation\" event is triggered.", iconName = "images/yandex.png", nonVisible = true, version = 2)
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public final class YandexTranslate extends AndroidNonvisibleComponent {
    public static final String YANDEX_TRANSLATE_SERVICE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";
    private final Activity activity;
    private final byte[] key1 = {-127, Opcode.OUTPUT_READ, Opcode.CP_EQF, Opcode.CP_NEQ8, Opcode.JR_FALSE, Opcode.JR_NEQ8, Opcode.OUTPUT_STEP_SYNC, Opcode.CP_LTEQF, Opcode.ARRAY_WRITE, Opcode.MEMORY_WRITE, Opcode.UI_BUTTON, -25, -31, Opcode.MOVE16_F, Opcode.JR_GTF, Opcode.COM_REMOVE, Opcode.ARRAY, Opcode.ARRAY_WRITE, Opcode.OR16, Opcode.TIMER_READY, 1, Opcode.CP_GTEQ16, -33, 23, -19, 18, Opcode.OUTPUT_TIME_SPEED, Opcode.AND16, -67, Opcode.JR_NEQ32, Opcode.SELECT8, Opcode.ARRAY_APPEND, Opcode.OUTPUT_PRG_STOP, Opcode.WRITE32, Opcode.MEMORY_USAGE, Opcode.WRITEF, Opcode.RANDOM, Opcode.FILE, -96, -75, Opcode.JR_LTEQ16, Opcode.BP_SET, Opcode.MOVE16_16, -8, Opcode.RL8, Opcode.JR_EQF, Opcode.JR_GTEQ8, Opcode.MOVE8_8, Opcode.XOR16, 30, Opcode.CP_LTEQ16, Opcode.BP_SET, -31, 17, Opcode.CP_LTEQF, Opcode.OUTPUT_POLARITY, Opcode.WRITEF, Opcode.WRITE16, Opcode.INIT_BYTES, Opcode.SELECT8, Opcode.JR_GTEQ16, Opcode.FILENAME, Opcode.OUTPUT_STEP_SYNC, -25, Opcode.CP_LTEQ32, Opcode.JR_GTEQF, Opcode.MAILBOX_READY, -9, Opcode.JR_LT16, Opcode.KEEP_ALIVE, -22, -28, -29, -14, Opcode.UI_BUTTON, Opcode.RL32, -103, Opcode.MAILBOX_READY, Opcode.STRINGS, Opcode.JR_NEQ32, 35, -31, 1, Opcode.JR_GTEQF};
    private final byte[] key2 = {-11, Opcode.MAILBOX_READ, Opcode.OR16, 35, Opcode.RL16, Opcode.SELECT32, -127, Opcode.JR_GTEQ16, -13, Opcode.CP_NEQ8, Opcode.OUTPUT_TIME_SYNC, Opcode.COM_WRITEFILE, Opcode.COM_READY, 3, Opcode.CP_GTEQF, -29, -15, -9, Opcode.JR_LTEQ16, -74, Opcode.MOVE8_16, Opcode.JR_GT16, -26, Opcode.OR32, Opcode.MAILBOX_CLOSE, Opcode.CP_GT8, -127, Opcode.f40JR, Opcode.BP_SET, Opcode.CP_LT16, Opcode.JR_EQF, -12, Opcode.COM_READY, Opcode.OUTPUT_TIME_SPEED, -11, Opcode.OUTPUT_TIME_POWER, -69, -12, -108, Opcode.COM_REMOVE, Opcode.JR_FALSE, -72, Opcode.CP_LTEQ32, Opcode.COM_REMOVE, 27, 12, 26, 2, 28, Opcode.JR_GTEQ32, Opcode.MOVE8_F, -24, Opcode.COM_GET, Opcode.AND8, Opcode.MOVE16_32, -106, Opcode.OUTPUT_TEST, -3, 27, Opcode.MOVEF_32, Opcode.JR_FALSE, -16, Opcode.UI_WRITE, Opcode.COM_REMOVE, Opcode.NOTE_TO_FREQ, Opcode.CP_EQ16, -70, Opcode.WRITEF, Opcode.CP_NEQF, -12, Opcode.RANDOM, Opcode.MAILBOX_CLOSE, Opcode.COM_SET, SystemCommand.CONTINUE_DOWNLOAD, Opcode.OUTPUT_GET_COUNT, 28, Opcode.OUTPUT_STEP_POWER, -66, Opcode.CP_GT8, 22, 18, Opcode.UI_WRITE, Opcode.MOVE8_32, Opcode.CP_EQ32};
    private String userYandexKey = "DEFAULT";
    private final String yandexKey;

    public YandexTranslate(ComponentContainer container) {
        super(container.$form());
        this.form.setYandexTranslateTagline();
        this.yandexKey = m20gk();
        this.activity = container.$context();
    }

    @SimpleFunction(description = "By providing a target language to translate to (for instance, 'es' for Spanish, 'en' for English, or 'ru' for Russian), and a word or sentence to translate, this method will request a translation to the Yandex.Translate service.\nOnce the text is translated by the external service, the event GotTranslation will be executed.\nNote: Yandex.Translate will attempt to detect the source language. You can also specify prepending it to the language translation. I.e., es-ru will specify Spanish to Russian translation.")
    public void RequestTranslation(final String languageToTranslateTo, final String textToTranslate) {
        if (!TextUtils.isEmpty(this.yandexKey) || (!TextUtils.isEmpty(this.userYandexKey) && !TextUtils.equals(this.userYandexKey, "DEFAULT"))) {
            AsynchUtil.runAsynchronously(new Runnable() {
                public void run() {
                    try {
                        YandexTranslate.this.performRequest(languageToTranslateTo, textToTranslate);
                    } catch (IOException e) {
                        YandexTranslate.this.form.dispatchErrorOccurredEvent(YandexTranslate.this, "RequestTranslation", ErrorMessages.ERROR_TRANSLATE_SERVICE_NOT_AVAILABLE, new Object[0]);
                    } catch (JSONException e2) {
                        YandexTranslate.this.form.dispatchErrorOccurredEvent(YandexTranslate.this, "RequestTranslation", ErrorMessages.ERROR_TRANSLATE_JSON_RESPONSE, new Object[0]);
                    }
                }
            });
        } else {
            this.form.dispatchErrorOccurredEvent(this, "RequestTranslation", ErrorMessages.ERROR_TRANSLATE_NO_KEY_FOUND, new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void performRequest(String languageToTranslateTo, String textToTranslate) throws IOException, JSONException {
        HttpURLConnection connection = (HttpURLConnection) new URL(YANDEX_TRANSLATE_SERVICE_URL + ((TextUtils.equals(this.userYandexKey, "DEFAULT") || TextUtils.isEmpty(this.userYandexKey)) ? this.yandexKey : this.userYandexKey) + "&lang=" + languageToTranslateTo + "&text=" + URLEncoder.encode(textToTranslate, "UTF-8")).openConnection();
        if (connection != null) {
            try {
                JSONObject jsonResponse = new JSONObject(getResponseContent(connection));
                final String responseCode = jsonResponse.getString("code");
                final String translation = (String) jsonResponse.getJSONArray(PropertyTypeConstants.PROPERTY_TYPE_TEXT).get(0);
                this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        YandexTranslate.this.GotTranslation(responseCode, translation);
                    }
                });
            } finally {
                connection.disconnect();
            }
        }
    }

    private static String getResponseContent(HttpURLConnection connection) throws IOException {
        String encoding = connection.getContentEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        InputStreamReader reader = new InputStreamReader(connection.getInputStream(), encoding);
        try {
            int contentLength = connection.getContentLength();
            StringBuilder sb = contentLength != -1 ? new StringBuilder(contentLength) : new StringBuilder();
            char[] buf = new char[1024];
            while (true) {
                int read = reader.read(buf);
                if (read == -1) {
                    return sb.toString();
                }
                sb.append(buf, 0, read);
            }
        } finally {
            reader.close();
        }
    }

    @SimpleEvent(description = "Event triggered when the Yandex.Translate service returns the translated text. This event also provides a response code for error handling. If the responseCode is not 200, then something went wrong with the call, and the translation will not be available.")
    public void GotTranslation(String responseCode, String translation) {
        EventDispatcher.dispatchEvent(this, "GotTranslation", responseCode, translation);
    }

    @DesignerProperty(defaultValue = "DEFAULT", editorType = "string")
    @SimpleProperty(description = "Set the API Key to use with Yandex. You do not need to set this if you are using the MIT system because MIT has its own key builtin. If set, the key provided here will be used instead")
    public void ApiKey(String apiKey) {
        this.userYandexKey = apiKey;
    }

    /* renamed from: gk */
    private String m20gk() {
        byte[] retval = new byte[this.key1.length];
        for (int i = 0; i < this.key1.length; i++) {
            retval[i] = (byte) (this.key1[i] ^ this.key2[i]);
        }
        return new String(retval);
    }
}
