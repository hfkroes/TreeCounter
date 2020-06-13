package android.support.p000v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.graphics.TypefaceCompatUtil */
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    @Nullable
    public static File getTempFile(Context context) {
        String prefix = CACHE_FILE_PREFIX + Process.myPid() + "-" + Process.myTid() + "-";
        int i = 0;
        while (i < 100) {
            File file = new File(context.getCacheDir(), prefix + i);
            try {
                if (file.createNewFile()) {
                    return file;
                }
                i++;
            } catch (IOException e) {
            }
        }
        return null;
    }

    @Nullable
    @RequiresApi(19)
    private static ByteBuffer mmap(File file) {
        Throwable th;
        try {
            FileInputStream fis = new FileInputStream(file);
            Throwable th2 = null;
            try {
                FileChannel channel = fis.getChannel();
                MappedByteBuffer map = channel.map(MapMode.READ_ONLY, 0, channel.size());
                if (fis == null) {
                    return map;
                }
                if (0 != 0) {
                    try {
                        fis.close();
                        return map;
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                        return map;
                    }
                } else {
                    fis.close();
                    return map;
                }
            } catch (Throwable th4) {
                Throwable th5 = th4;
                th = r1;
                th = th5;
            }
            if (fis != null) {
                if (th != null) {
                    try {
                        fis.close();
                    } catch (Throwable th6) {
                        th.addSuppressed(th6);
                    }
                } else {
                    fis.close();
                }
            }
            throw th;
            throw th;
        } catch (IOException e) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0057, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0058, code lost:
        r12 = r2;
        r2 = r1;
        r1 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0067, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0068, code lost:
        r2 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x007c, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x007d, code lost:
        r2.addSuppressed(r3);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0067 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:16:0x0022] */
    @android.support.annotation.Nullable
    @android.support.annotation.RequiresApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.nio.ByteBuffer mmap(android.content.Context r13, android.os.CancellationSignal r14, android.net.Uri r15) {
        /*
            android.content.ContentResolver r9 = r13.getContentResolver()
            java.lang.String r1 = "r"
            android.os.ParcelFileDescriptor r8 = r9.openFileDescriptor(r15, r1, r14)     // Catch:{ IOException -> 0x001b }
            r11 = 0
            if (r8 != 0) goto L_0x0022
            r1 = 0
            if (r8 == 0) goto L_0x0015
            if (r11 == 0) goto L_0x001e
            r8.close()     // Catch:{ Throwable -> 0x0016 }
        L_0x0015:
            return r1
        L_0x0016:
            r2 = move-exception
            r11.addSuppressed(r2)     // Catch:{ IOException -> 0x001b }
            goto L_0x0015
        L_0x001b:
            r6 = move-exception
            r1 = 0
            goto L_0x0015
        L_0x001e:
            r8.close()     // Catch:{ IOException -> 0x001b }
            goto L_0x0015
        L_0x0022:
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0055, all -> 0x0067 }
            java.io.FileDescriptor r1 = r8.getFileDescriptor()     // Catch:{ Throwable -> 0x0055, all -> 0x0067 }
            r7.<init>(r1)     // Catch:{ Throwable -> 0x0055, all -> 0x0067 }
            r10 = 0
            java.nio.channels.FileChannel r0 = r7.getChannel()     // Catch:{ Throwable -> 0x006e, all -> 0x008e }
            long r4 = r0.size()     // Catch:{ Throwable -> 0x006e, all -> 0x008e }
            java.nio.channels.FileChannel$MapMode r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ Throwable -> 0x006e, all -> 0x008e }
            r2 = 0
            java.nio.MappedByteBuffer r1 = r0.map(r1, r2, r4)     // Catch:{ Throwable -> 0x006e, all -> 0x008e }
            if (r7 == 0) goto L_0x0043
            if (r10 == 0) goto L_0x0063
            r7.close()     // Catch:{ Throwable -> 0x0050, all -> 0x0067 }
        L_0x0043:
            if (r8 == 0) goto L_0x0015
            if (r11 == 0) goto L_0x006a
            r8.close()     // Catch:{ Throwable -> 0x004b }
            goto L_0x0015
        L_0x004b:
            r2 = move-exception
            r11.addSuppressed(r2)     // Catch:{ IOException -> 0x001b }
            goto L_0x0015
        L_0x0050:
            r2 = move-exception
            r10.addSuppressed(r2)     // Catch:{ Throwable -> 0x0055, all -> 0x0067 }
            goto L_0x0043
        L_0x0055:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0057 }
        L_0x0057:
            r2 = move-exception
            r12 = r2
            r2 = r1
            r1 = r12
        L_0x005b:
            if (r8 == 0) goto L_0x0062
            if (r2 == 0) goto L_0x008a
            r8.close()     // Catch:{ Throwable -> 0x0085 }
        L_0x0062:
            throw r1     // Catch:{ IOException -> 0x001b }
        L_0x0063:
            r7.close()     // Catch:{ Throwable -> 0x0055, all -> 0x0067 }
            goto L_0x0043
        L_0x0067:
            r1 = move-exception
            r2 = r11
            goto L_0x005b
        L_0x006a:
            r8.close()     // Catch:{ IOException -> 0x001b }
            goto L_0x0015
        L_0x006e:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0070 }
        L_0x0070:
            r2 = move-exception
            r12 = r2
            r2 = r1
            r1 = r12
        L_0x0074:
            if (r7 == 0) goto L_0x007b
            if (r2 == 0) goto L_0x0081
            r7.close()     // Catch:{ Throwable -> 0x007c, all -> 0x0067 }
        L_0x007b:
            throw r1     // Catch:{ Throwable -> 0x0055, all -> 0x0067 }
        L_0x007c:
            r3 = move-exception
            r2.addSuppressed(r3)     // Catch:{ Throwable -> 0x0055, all -> 0x0067 }
            goto L_0x007b
        L_0x0081:
            r7.close()     // Catch:{ Throwable -> 0x0055, all -> 0x0067 }
            goto L_0x007b
        L_0x0085:
            r3 = move-exception
            r2.addSuppressed(r3)     // Catch:{ IOException -> 0x001b }
            goto L_0x0062
        L_0x008a:
            r8.close()     // Catch:{ IOException -> 0x001b }
            goto L_0x0062
        L_0x008e:
            r1 = move-exception
            r2 = r10
            goto L_0x0074
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.graphics.TypefaceCompatUtil.mmap(android.content.Context, android.os.CancellationSignal, android.net.Uri):java.nio.ByteBuffer");
    }

    @Nullable
    @RequiresApi(19)
    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        ByteBuffer byteBuffer = null;
        File tmpFile = getTempFile(context);
        if (tmpFile != null) {
            try {
                if (copyToFile(tmpFile, res, id)) {
                    byteBuffer = mmap(tmpFile);
                    tmpFile.delete();
                }
            } finally {
                tmpFile.delete();
            }
        }
        return byteBuffer;
    }

    public static boolean copyToFile(File file, InputStream is) {
        FileOutputStream os = null;
        ThreadPolicy old = StrictMode.allowThreadDiskWrites();
        try {
            FileOutputStream os2 = new FileOutputStream(file, false);
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    int readLen = is.read(buffer);
                    if (readLen != -1) {
                        os2.write(buffer, 0, readLen);
                    } else {
                        closeQuietly(os2);
                        StrictMode.setThreadPolicy(old);
                        FileOutputStream fileOutputStream = os2;
                        return true;
                    }
                }
            } catch (IOException e) {
                e = e;
                os = os2;
                try {
                    Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
                    closeQuietly(os);
                    StrictMode.setThreadPolicy(old);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    closeQuietly(os);
                    StrictMode.setThreadPolicy(old);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                os = os2;
                closeQuietly(os);
                StrictMode.setThreadPolicy(old);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
            closeQuietly(os);
            StrictMode.setThreadPolicy(old);
            return false;
        }
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            return copyToFile(file, is);
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
