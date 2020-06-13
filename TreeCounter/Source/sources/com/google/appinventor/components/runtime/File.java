package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.errors.PermissionException;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

@SimpleObject
@DesignerComponent(category = ComponentCategory.STORAGE, description = "Non-visible component for storing and retrieving files. Use this component to write or read files on your device. The default behaviour is to write files to the private data directory associated with your App. The Companion is special cased to write files to /sdcard/AppInventor/data to facilitate debugging. If the file path starts with a slash (/), then the file is created relative to /sdcard. For example writing a file to /myFile.txt will write the file in /sdcard/myFile.txt.", iconName = "images/file.png", nonVisible = true, version = 2)
@UsesPermissions(permissionNames = "android.permission.WRITE_EXTERNAL_STORAGE, android.permission.READ_EXTERNAL_STORAGE")
public class File extends AndroidNonvisibleComponent implements Component {
    private static final String LOG_TAG = "FileComponent";
    public static final String NO_ASSETS = "No_Assets";
    private final int BUFFER_LENGTH = 4096;
    /* access modifiers changed from: private */
    public final Activity activity;
    private boolean isRepl = false;

    public File(ComponentContainer container) {
        super(container.$form());
        if (this.form instanceof ReplForm) {
            this.isRepl = true;
        }
        this.activity = container.$context();
    }

    @SimpleFunction(description = "Saves text to a file. If the filename begins with a slash (/) the file is written to the sdcard. For example writing to /myFile.txt will write the file to /sdcard/myFile.txt. If the filename does not start with a slash, it will be written in the programs private data directory where it will not be accessible to other programs on the phone. There is a special exception for the AI Companion where these files are written to /sdcard/AppInventor/data to facilitate debugging. Note that this block will overwrite a file if it already exists.\n\nIf you want to add content to a file use the append block.")
    public void SaveFile(String text, String fileName) {
        if (fileName.startsWith("/")) {
            FileUtil.checkExternalStorageWriteable();
        }
        Write(fileName, text, false);
    }

    @SimpleFunction(description = "Appends text to the end of a file storage, creating the file if it does not exist. See the help text under SaveFile for information about where files are written.")
    public void AppendToFile(String text, String fileName) {
        if (fileName.startsWith("/")) {
            FileUtil.checkExternalStorageWriteable();
        }
        Write(fileName, text, true);
    }

    @SimpleFunction(description = "Reads text from a file in storage. Prefix the filename with / to read from a specific file on the SD card. for instance /myFile.txt will read the file /sdcard/myFile.txt. To read assets packaged with an application (also works for the Companion) start the filename with // (two slashes). If a filename does not start with a slash, it will be read from the applications private storage (for packaged apps) and from /sdcard/AppInventor/data for the Companion.")
    public void ReadFrom(final String fileName) {
        this.form.askPermission("android.permission.READ_EXTERNAL_STORAGE", new PermissionResultHandler() {
            public void HandlePermissionResponse(String permission, boolean granted) {
                InputStream inputStream;
                if (granted) {
                    try {
                        if (fileName.startsWith("//")) {
                            inputStream = File.this.form.openAsset(fileName.substring(2));
                        } else {
                            String filepath = File.this.AbsoluteFileName(fileName);
                            Log.d(File.LOG_TAG, "filepath = " + filepath);
                            inputStream = FileUtil.openFile(filepath);
                        }
                        final InputStream asyncInputStream = inputStream;
                        AsynchUtil.runAsynchronously(new Runnable() {
                            public void run() {
                                File.this.AsyncRead(asyncInputStream, fileName);
                            }
                        });
                    } catch (PermissionException e) {
                        File.this.form.dispatchPermissionDeniedEvent((Component) File.this, "ReadFrom", e);
                    } catch (FileNotFoundException e2) {
                        Log.e(File.LOG_TAG, "FileNotFoundException", e2);
                        File.this.form.dispatchErrorOccurredEvent(File.this, "ReadFrom", ErrorMessages.ERROR_CANNOT_FIND_FILE, fileName);
                    } catch (IOException e3) {
                        Log.e(File.LOG_TAG, "IOException", e3);
                        File.this.form.dispatchErrorOccurredEvent(File.this, "ReadFrom", ErrorMessages.ERROR_CANNOT_FIND_FILE, fileName);
                    }
                } else {
                    File.this.form.dispatchPermissionDeniedEvent((Component) File.this, "ReadFrom", permission);
                }
            }
        });
    }

    @SimpleFunction(description = "Deletes a file from storage. Prefix the filename with / to delete a specific file in the SD card, for instance /myFile.txt. will delete the file /sdcard/myFile.txt. If the file does not begin with a /, then the file located in the programs private storage will be deleted. Starting the file with // is an error because assets files cannot be deleted.")
    public void Delete(final String fileName) {
        this.form.askPermission("android.permission.WRITE_EXTERNAL_STORAGE", new PermissionResultHandler() {
            public void HandlePermissionResponse(String permission, boolean granted) {
                if (!granted) {
                    File.this.form.dispatchPermissionDeniedEvent((Component) File.this, "Delete", permission);
                } else if (fileName.startsWith("//")) {
                    File.this.form.dispatchErrorOccurredEvent(File.this, "DeleteFile", ErrorMessages.ERROR_CANNOT_DELETE_ASSET, fileName);
                } else {
                    String filepath = File.this.AbsoluteFileName(fileName);
                    if (MediaUtil.isExternalFile(fileName) && File.this.form.isDeniedPermission("android.permission.WRITE_EXTERNAL_STORAGE")) {
                        File.this.form.dispatchPermissionDeniedEvent((Component) File.this, "Delete", new PermissionException("android.permission.WRITE_EXTERNAL_STORAGE"));
                    }
                    new java.io.File(filepath).delete();
                }
            }
        });
    }

    private void Write(final String filename, final String text, final boolean append) {
        if (!filename.startsWith("//")) {
            final Runnable operation = new Runnable() {
                public void run() {
                    String filepath = File.this.AbsoluteFileName(filename);
                    if (MediaUtil.isExternalFile(filepath)) {
                        File.this.form.assertPermission("android.permission.WRITE_EXTERNAL_STORAGE");
                    }
                    java.io.File file = new java.io.File(filepath);
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            if (append) {
                                File.this.form.dispatchErrorOccurredEvent(File.this, "AppendTo", ErrorMessages.ERROR_CANNOT_CREATE_FILE, filepath);
                                return;
                            }
                            File.this.form.dispatchErrorOccurredEvent(File.this, "SaveFile", ErrorMessages.ERROR_CANNOT_CREATE_FILE, filepath);
                            return;
                        }
                    }
                    try {
                        FileOutputStream fileWriter = new FileOutputStream(file, append);
                        OutputStreamWriter out = new OutputStreamWriter(fileWriter);
                        out.write(text);
                        out.flush();
                        out.close();
                        fileWriter.close();
                        File.this.activity.runOnUiThread(new Runnable() {
                            public void run() {
                                File.this.AfterFileSaved(filename);
                            }
                        });
                    } catch (IOException e2) {
                        if (append) {
                            File.this.form.dispatchErrorOccurredEvent(File.this, "AppendTo", ErrorMessages.ERROR_CANNOT_WRITE_TO_FILE, filepath);
                            return;
                        }
                        File.this.form.dispatchErrorOccurredEvent(File.this, "SaveFile", ErrorMessages.ERROR_CANNOT_WRITE_TO_FILE, filepath);
                    }
                }
            };
            this.form.askPermission("android.permission.WRITE_EXTERNAL_STORAGE", new PermissionResultHandler() {
                public void HandlePermissionResponse(String permission, boolean granted) {
                    if (granted) {
                        AsynchUtil.runAsynchronously(operation);
                    } else {
                        File.this.form.dispatchPermissionDeniedEvent((Component) File.this, append ? "AppendTo" : "SaveFile", permission);
                    }
                }
            });
        } else if (append) {
            this.form.dispatchErrorOccurredEvent(this, "AppendTo", ErrorMessages.ERROR_CANNOT_WRITE_ASSET, filename);
        } else {
            this.form.dispatchErrorOccurredEvent(this, "SaveFile", ErrorMessages.ERROR_CANNOT_WRITE_ASSET, filename);
        }
    }

    private String normalizeNewLines(String s) {
        return s.replaceAll("\r\n", "\n");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0038 A[SYNTHETIC, Splitter:B:14:0x0038] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0071 A[SYNTHETIC, Splitter:B:29:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x007a A[SYNTHETIC, Splitter:B:34:0x007a] */
    /* JADX WARNING: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void AsyncRead(java.io.InputStream r14, java.lang.String r15) {
        /*
            r13 = this;
            r2 = 0
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ FileNotFoundException -> 0x0088, IOException -> 0x0058 }
            r3.<init>(r14)     // Catch:{ FileNotFoundException -> 0x0088, IOException -> 0x0058 }
            java.io.StringWriter r6 = new java.io.StringWriter     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x0085, all -> 0x0082 }
            r6.<init>()     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x0085, all -> 0x0082 }
            r8 = 4096(0x1000, float:5.74E-42)
            char[] r0 = new char[r8]     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x0085, all -> 0x0082 }
            r5 = 0
            r4 = 0
        L_0x0011:
            r8 = 4096(0x1000, float:5.74E-42)
            int r4 = r3.read(r0, r5, r8)     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x0085, all -> 0x0082 }
            if (r4 <= 0) goto L_0x003c
            r8 = 0
            r6.write(r0, r8, r4)     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x0085, all -> 0x0082 }
            goto L_0x0011
        L_0x001e:
            r1 = move-exception
            r2 = r3
        L_0x0020:
            java.lang.String r8 = "FileComponent"
            java.lang.String r9 = "FileNotFoundException"
            android.util.Log.e(r8, r9, r1)     // Catch:{ all -> 0x0077 }
            com.google.appinventor.components.runtime.Form r8 = r13.form     // Catch:{ all -> 0x0077 }
            java.lang.String r9 = "ReadFrom"
            r10 = 2101(0x835, float:2.944E-42)
            r11 = 1
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x0077 }
            r12 = 0
            r11[r12] = r15     // Catch:{ all -> 0x0077 }
            r8.dispatchErrorOccurredEvent(r13, r9, r10, r11)     // Catch:{ all -> 0x0077 }
            if (r2 == 0) goto L_0x003b
            r2.close()     // Catch:{ IOException -> 0x007e }
        L_0x003b:
            return
        L_0x003c:
            java.lang.String r8 = r6.toString()     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x0085, all -> 0x0082 }
            java.lang.String r7 = r13.normalizeNewLines(r8)     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x0085, all -> 0x0082 }
            android.app.Activity r8 = r13.activity     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x0085, all -> 0x0082 }
            com.google.appinventor.components.runtime.File$5 r9 = new com.google.appinventor.components.runtime.File$5     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x0085, all -> 0x0082 }
            r9.<init>(r7)     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x0085, all -> 0x0082 }
            r8.runOnUiThread(r9)     // Catch:{ FileNotFoundException -> 0x001e, IOException -> 0x0085, all -> 0x0082 }
            if (r3 == 0) goto L_0x008a
            r3.close()     // Catch:{ IOException -> 0x0055 }
            r2 = r3
            goto L_0x003b
        L_0x0055:
            r8 = move-exception
            r2 = r3
            goto L_0x003b
        L_0x0058:
            r1 = move-exception
        L_0x0059:
            java.lang.String r8 = "FileComponent"
            java.lang.String r9 = "IOException"
            android.util.Log.e(r8, r9, r1)     // Catch:{ all -> 0x0077 }
            com.google.appinventor.components.runtime.Form r8 = r13.form     // Catch:{ all -> 0x0077 }
            java.lang.String r9 = "ReadFrom"
            r10 = 2102(0x836, float:2.946E-42)
            r11 = 1
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x0077 }
            r12 = 0
            r11[r12] = r15     // Catch:{ all -> 0x0077 }
            r8.dispatchErrorOccurredEvent(r13, r9, r10, r11)     // Catch:{ all -> 0x0077 }
            if (r2 == 0) goto L_0x003b
            r2.close()     // Catch:{ IOException -> 0x0075 }
            goto L_0x003b
        L_0x0075:
            r8 = move-exception
            goto L_0x003b
        L_0x0077:
            r8 = move-exception
        L_0x0078:
            if (r2 == 0) goto L_0x007d
            r2.close()     // Catch:{ IOException -> 0x0080 }
        L_0x007d:
            throw r8
        L_0x007e:
            r8 = move-exception
            goto L_0x003b
        L_0x0080:
            r9 = move-exception
            goto L_0x007d
        L_0x0082:
            r8 = move-exception
            r2 = r3
            goto L_0x0078
        L_0x0085:
            r1 = move-exception
            r2 = r3
            goto L_0x0059
        L_0x0088:
            r1 = move-exception
            goto L_0x0020
        L_0x008a:
            r2 = r3
            goto L_0x003b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.File.AsyncRead(java.io.InputStream, java.lang.String):void");
    }

    @SimpleEvent(description = "Event indicating that the contents from the file have been read.")
    public void GotText(String text) {
        EventDispatcher.dispatchEvent(this, "GotText", text);
    }

    @SimpleEvent(description = "Event indicating that the contents of the file have been written.")
    public void AfterFileSaved(String fileName) {
        EventDispatcher.dispatchEvent(this, "AfterFileSaved", fileName);
    }

    /* access modifiers changed from: private */
    public String AbsoluteFileName(String filename) {
        if (filename.startsWith("/")) {
            return Environment.getExternalStorageDirectory().getPath() + filename;
        }
        java.io.File dirPath = this.activity.getFilesDir();
        if (this.isRepl) {
            dirPath = new java.io.File(Environment.getExternalStorageDirectory().getPath() + "/AppInventor/data/");
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
        }
        return dirPath.getPath() + "/" + filename;
    }
}
