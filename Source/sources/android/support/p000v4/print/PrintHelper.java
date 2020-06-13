package android.support.p000v4.print;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignal.OnCancelListener;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Margins;
import android.print.PrintAttributes.MediaSize;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentAdapter.LayoutResultCallback;
import android.print.PrintDocumentAdapter.WriteResultCallback;
import android.print.PrintDocumentInfo;
import android.print.PrintDocumentInfo.Builder;
import android.print.PrintManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: android.support.v4.print.PrintHelper */
public final class PrintHelper {
    @SuppressLint({"InlinedApi"})
    public static final int COLOR_MODE_COLOR = 2;
    @SuppressLint({"InlinedApi"})
    public static final int COLOR_MODE_MONOCHROME = 1;
    static final boolean IS_MIN_MARGINS_HANDLING_CORRECT;
    private static final String LOG_TAG = "PrintHelper";
    private static final int MAX_PRINT_SIZE = 3500;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    static final boolean PRINT_ACTIVITY_RESPECTS_ORIENTATION = (VERSION.SDK_INT < 20 || VERSION.SDK_INT > 23);
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    int mColorMode = 2;
    final Context mContext;
    Options mDecodeOptions = null;
    final Object mLock = new Object();
    int mOrientation = 1;
    int mScaleMode = 2;

    /* renamed from: android.support.v4.print.PrintHelper$OnPrintFinishCallback */
    public interface OnPrintFinishCallback {
        void onFinish();
    }

    @RequiresApi(19)
    /* renamed from: android.support.v4.print.PrintHelper$PrintBitmapAdapter */
    private class PrintBitmapAdapter extends PrintDocumentAdapter {
        private PrintAttributes mAttributes;
        private final Bitmap mBitmap;
        private final OnPrintFinishCallback mCallback;
        private final int mFittingMode;
        private final String mJobName;

        PrintBitmapAdapter(String jobName, int fittingMode, Bitmap bitmap, OnPrintFinishCallback callback) {
            this.mJobName = jobName;
            this.mFittingMode = fittingMode;
            this.mBitmap = bitmap;
            this.mCallback = callback;
        }

        public void onLayout(PrintAttributes oldPrintAttributes, PrintAttributes newPrintAttributes, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
            boolean changed = true;
            this.mAttributes = newPrintAttributes;
            PrintDocumentInfo info = new Builder(this.mJobName).setContentType(1).setPageCount(1).build();
            if (newPrintAttributes.equals(oldPrintAttributes)) {
                changed = false;
            }
            layoutResultCallback.onLayoutFinished(info, changed);
        }

        public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor fileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
            PrintHelper.this.writeBitmap(this.mAttributes, this.mFittingMode, this.mBitmap, fileDescriptor, cancellationSignal, writeResultCallback);
        }

        public void onFinish() {
            if (this.mCallback != null) {
                this.mCallback.onFinish();
            }
        }
    }

    @RequiresApi(19)
    /* renamed from: android.support.v4.print.PrintHelper$PrintUriAdapter */
    private class PrintUriAdapter extends PrintDocumentAdapter {
        PrintAttributes mAttributes;
        Bitmap mBitmap = null;
        final OnPrintFinishCallback mCallback;
        final int mFittingMode;
        final Uri mImageFile;
        final String mJobName;
        AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;

        PrintUriAdapter(String jobName, Uri imageFile, OnPrintFinishCallback callback, int fittingMode) {
            this.mJobName = jobName;
            this.mImageFile = imageFile;
            this.mCallback = callback;
            this.mFittingMode = fittingMode;
        }

        public void onLayout(PrintAttributes oldPrintAttributes, PrintAttributes newPrintAttributes, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
            boolean changed = true;
            synchronized (this) {
                this.mAttributes = newPrintAttributes;
            }
            if (cancellationSignal.isCanceled()) {
                layoutResultCallback.onLayoutCancelled();
            } else if (this.mBitmap != null) {
                PrintDocumentInfo info = new Builder(this.mJobName).setContentType(1).setPageCount(1).build();
                if (newPrintAttributes.equals(oldPrintAttributes)) {
                    changed = false;
                }
                layoutResultCallback.onLayoutFinished(info, changed);
            } else {
                final CancellationSignal cancellationSignal2 = cancellationSignal;
                final PrintAttributes printAttributes = newPrintAttributes;
                final PrintAttributes printAttributes2 = oldPrintAttributes;
                final LayoutResultCallback layoutResultCallback2 = layoutResultCallback;
                this.mLoadBitmap = new AsyncTask<Uri, Boolean, Bitmap>() {
                    /* access modifiers changed from: protected */
                    public void onPreExecute() {
                        cancellationSignal2.setOnCancelListener(new OnCancelListener() {
                            public void onCancel() {
                                PrintUriAdapter.this.cancelLoad();
                                C01201.this.cancel(false);
                            }
                        });
                    }

                    /* access modifiers changed from: protected */
                    public Bitmap doInBackground(Uri... uris) {
                        try {
                            return PrintHelper.this.loadConstrainedBitmap(PrintUriAdapter.this.mImageFile);
                        } catch (FileNotFoundException e) {
                            return null;
                        }
                    }

                    /* access modifiers changed from: protected */
                    public void onPostExecute(Bitmap bitmap) {
                        boolean changed;
                        MediaSize mediaSize;
                        super.onPostExecute(bitmap);
                        if (bitmap != null && (!PrintHelper.PRINT_ACTIVITY_RESPECTS_ORIENTATION || PrintHelper.this.mOrientation == 0)) {
                            synchronized (this) {
                                mediaSize = PrintUriAdapter.this.mAttributes.getMediaSize();
                            }
                            if (!(mediaSize == null || mediaSize.isPortrait() == PrintHelper.isPortrait(bitmap))) {
                                Matrix rotation = new Matrix();
                                rotation.postRotate(90.0f);
                                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotation, true);
                            }
                        }
                        PrintUriAdapter.this.mBitmap = bitmap;
                        if (bitmap != null) {
                            PrintDocumentInfo info = new Builder(PrintUriAdapter.this.mJobName).setContentType(1).setPageCount(1).build();
                            if (!printAttributes.equals(printAttributes2)) {
                                changed = true;
                            } else {
                                changed = false;
                            }
                            layoutResultCallback2.onLayoutFinished(info, changed);
                        } else {
                            layoutResultCallback2.onLayoutFailed(null);
                        }
                        PrintUriAdapter.this.mLoadBitmap = null;
                    }

                    /* access modifiers changed from: protected */
                    public void onCancelled(Bitmap result) {
                        layoutResultCallback2.onLayoutCancelled();
                        PrintUriAdapter.this.mLoadBitmap = null;
                    }
                }.execute(new Uri[0]);
            }
        }

        /* access modifiers changed from: 0000 */
        public void cancelLoad() {
            synchronized (PrintHelper.this.mLock) {
                if (PrintHelper.this.mDecodeOptions != null) {
                    if (VERSION.SDK_INT < 24) {
                        PrintHelper.this.mDecodeOptions.requestCancelDecode();
                    }
                    PrintHelper.this.mDecodeOptions = null;
                }
            }
        }

        public void onFinish() {
            super.onFinish();
            cancelLoad();
            if (this.mLoadBitmap != null) {
                this.mLoadBitmap.cancel(true);
            }
            if (this.mCallback != null) {
                this.mCallback.onFinish();
            }
            if (this.mBitmap != null) {
                this.mBitmap.recycle();
                this.mBitmap = null;
            }
        }

        public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor fileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
            PrintHelper.this.writeBitmap(this.mAttributes, this.mFittingMode, this.mBitmap, fileDescriptor, cancellationSignal, writeResultCallback);
        }
    }

    static {
        boolean z = true;
        if (VERSION.SDK_INT == 23) {
            z = false;
        }
        IS_MIN_MARGINS_HANDLING_CORRECT = z;
    }

    public static boolean systemSupportsPrint() {
        return VERSION.SDK_INT >= 19;
    }

    public PrintHelper(@NonNull Context context) {
        this.mContext = context;
    }

    public void setScaleMode(int scaleMode) {
        this.mScaleMode = scaleMode;
    }

    public int getScaleMode() {
        return this.mScaleMode;
    }

    public void setColorMode(int colorMode) {
        this.mColorMode = colorMode;
    }

    public int getColorMode() {
        return this.mColorMode;
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public int getOrientation() {
        if (VERSION.SDK_INT < 19 || this.mOrientation != 0) {
            return this.mOrientation;
        }
        return 1;
    }

    public void printBitmap(@NonNull String jobName, @NonNull Bitmap bitmap) {
        printBitmap(jobName, bitmap, (OnPrintFinishCallback) null);
    }

    public void printBitmap(@NonNull String jobName, @NonNull Bitmap bitmap, @Nullable OnPrintFinishCallback callback) {
        MediaSize mediaSize;
        if (VERSION.SDK_INT >= 19 && bitmap != null) {
            PrintManager printManager = (PrintManager) this.mContext.getSystemService("print");
            if (isPortrait(bitmap)) {
                mediaSize = MediaSize.UNKNOWN_PORTRAIT;
            } else {
                mediaSize = MediaSize.UNKNOWN_LANDSCAPE;
            }
            printManager.print(jobName, new PrintBitmapAdapter(jobName, this.mScaleMode, bitmap, callback), new PrintAttributes.Builder().setMediaSize(mediaSize).setColorMode(this.mColorMode).build());
        }
    }

    public void printBitmap(@NonNull String jobName, @NonNull Uri imageFile) throws FileNotFoundException {
        printBitmap(jobName, imageFile, (OnPrintFinishCallback) null);
    }

    public void printBitmap(@NonNull String jobName, @NonNull Uri imageFile, @Nullable OnPrintFinishCallback callback) throws FileNotFoundException {
        if (VERSION.SDK_INT >= 19) {
            PrintDocumentAdapter printDocumentAdapter = new PrintUriAdapter(jobName, imageFile, callback, this.mScaleMode);
            PrintManager printManager = (PrintManager) this.mContext.getSystemService("print");
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setColorMode(this.mColorMode);
            if (this.mOrientation == 1 || this.mOrientation == 0) {
                builder.setMediaSize(MediaSize.UNKNOWN_LANDSCAPE);
            } else if (this.mOrientation == 2) {
                builder.setMediaSize(MediaSize.UNKNOWN_PORTRAIT);
            }
            printManager.print(jobName, printDocumentAdapter, builder.build());
        }
    }

    static boolean isPortrait(Bitmap bitmap) {
        return bitmap.getWidth() <= bitmap.getHeight();
    }

    @RequiresApi(19)
    private static PrintAttributes.Builder copyAttributes(PrintAttributes other) {
        PrintAttributes.Builder b = new PrintAttributes.Builder().setMediaSize(other.getMediaSize()).setResolution(other.getResolution()).setMinMargins(other.getMinMargins());
        if (other.getColorMode() != 0) {
            b.setColorMode(other.getColorMode());
        }
        if (VERSION.SDK_INT >= 23 && other.getDuplexMode() != 0) {
            b.setDuplexMode(other.getDuplexMode());
        }
        return b;
    }

    static Matrix getMatrix(int imageWidth, int imageHeight, RectF content, int fittingMode) {
        float scale;
        Matrix matrix = new Matrix();
        float scale2 = content.width() / ((float) imageWidth);
        if (fittingMode == 2) {
            scale = Math.max(scale2, content.height() / ((float) imageHeight));
        } else {
            scale = Math.min(scale2, content.height() / ((float) imageHeight));
        }
        matrix.postScale(scale, scale);
        matrix.postTranslate((content.width() - (((float) imageWidth) * scale)) / 2.0f, (content.height() - (((float) imageHeight) * scale)) / 2.0f);
        return matrix;
    }

    /* access modifiers changed from: 0000 */
    @RequiresApi(19)
    public void writeBitmap(PrintAttributes attributes, int fittingMode, Bitmap bitmap, ParcelFileDescriptor fileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
        final PrintAttributes pdfAttributes;
        if (IS_MIN_MARGINS_HANDLING_CORRECT) {
            pdfAttributes = attributes;
        } else {
            pdfAttributes = copyAttributes(attributes).setMinMargins(new Margins(0, 0, 0, 0)).build();
        }
        final CancellationSignal cancellationSignal2 = cancellationSignal;
        final Bitmap bitmap2 = bitmap;
        final PrintAttributes printAttributes = attributes;
        final int i = fittingMode;
        final ParcelFileDescriptor parcelFileDescriptor = fileDescriptor;
        final WriteResultCallback writeResultCallback2 = writeResultCallback;
        new AsyncTask<Void, Void, Throwable>() {
            /* access modifiers changed from: protected */
            /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
            /* JADX WARNING: Unknown top exception splitter block from list: {B:20:0x0070=Splitter:B:20:0x0070, B:33:0x00ab=Splitter:B:33:0x00ab, B:46:0x00dc=Splitter:B:46:0x00dc} */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Throwable doInBackground(java.lang.Void... r12) {
                /*
                    r11 = this;
                    r7 = 0
                    android.os.CancellationSignal r8 = r2     // Catch:{ Throwable -> 0x0078 }
                    boolean r8 = r8.isCanceled()     // Catch:{ Throwable -> 0x0078 }
                    if (r8 == 0) goto L_0x000a
                L_0x0009:
                    return r7
                L_0x000a:
                    android.print.pdf.PrintedPdfDocument r6 = new android.print.pdf.PrintedPdfDocument     // Catch:{ Throwable -> 0x0078 }
                    android.support.v4.print.PrintHelper r8 = android.support.p000v4.print.PrintHelper.this     // Catch:{ Throwable -> 0x0078 }
                    android.content.Context r8 = r8.mContext     // Catch:{ Throwable -> 0x0078 }
                    android.print.PrintAttributes r9 = r3     // Catch:{ Throwable -> 0x0078 }
                    r6.<init>(r8, r9)     // Catch:{ Throwable -> 0x0078 }
                    android.graphics.Bitmap r8 = r4     // Catch:{ Throwable -> 0x0078 }
                    android.print.PrintAttributes r9 = r3     // Catch:{ Throwable -> 0x0078 }
                    int r9 = r9.getColorMode()     // Catch:{ Throwable -> 0x0078 }
                    android.graphics.Bitmap r4 = android.support.p000v4.print.PrintHelper.convertBitmapForColorMode(r8, r9)     // Catch:{ Throwable -> 0x0078 }
                    android.os.CancellationSignal r8 = r2     // Catch:{ Throwable -> 0x0078 }
                    boolean r8 = r8.isCanceled()     // Catch:{ Throwable -> 0x0078 }
                    if (r8 != 0) goto L_0x0009
                    r8 = 1
                    android.graphics.pdf.PdfDocument$Page r5 = r6.startPage(r8)     // Catch:{ all -> 0x009e }
                    boolean r8 = android.support.p000v4.print.PrintHelper.IS_MIN_MARGINS_HANDLING_CORRECT     // Catch:{ all -> 0x009e }
                    if (r8 == 0) goto L_0x007a
                    android.graphics.RectF r0 = new android.graphics.RectF     // Catch:{ all -> 0x009e }
                    android.graphics.pdf.PdfDocument$PageInfo r8 = r5.getInfo()     // Catch:{ all -> 0x009e }
                    android.graphics.Rect r8 = r8.getContentRect()     // Catch:{ all -> 0x009e }
                    r0.<init>(r8)     // Catch:{ all -> 0x009e }
                L_0x003f:
                    int r8 = r4.getWidth()     // Catch:{ all -> 0x009e }
                    int r9 = r4.getHeight()     // Catch:{ all -> 0x009e }
                    int r10 = r6     // Catch:{ all -> 0x009e }
                    android.graphics.Matrix r3 = android.support.p000v4.print.PrintHelper.getMatrix(r8, r9, r0, r10)     // Catch:{ all -> 0x009e }
                    boolean r8 = android.support.p000v4.print.PrintHelper.IS_MIN_MARGINS_HANDLING_CORRECT     // Catch:{ all -> 0x009e }
                    if (r8 == 0) goto L_0x00b3
                L_0x0051:
                    android.graphics.Canvas r8 = r5.getCanvas()     // Catch:{ all -> 0x009e }
                    r9 = 0
                    r8.drawBitmap(r4, r3, r9)     // Catch:{ all -> 0x009e }
                    r6.finishPage(r5)     // Catch:{ all -> 0x009e }
                    android.os.CancellationSignal r8 = r2     // Catch:{ all -> 0x009e }
                    boolean r8 = r8.isCanceled()     // Catch:{ all -> 0x009e }
                    if (r8 == 0) goto L_0x00c2
                    r6.close()     // Catch:{ Throwable -> 0x0078 }
                    android.os.ParcelFileDescriptor r8 = r7     // Catch:{ Throwable -> 0x0078 }
                    if (r8 == 0) goto L_0x0070
                    android.os.ParcelFileDescriptor r8 = r7     // Catch:{ IOException -> 0x00e9 }
                    r8.close()     // Catch:{ IOException -> 0x00e9 }
                L_0x0070:
                    android.graphics.Bitmap r8 = r4     // Catch:{ Throwable -> 0x0078 }
                    if (r4 == r8) goto L_0x0009
                    r4.recycle()     // Catch:{ Throwable -> 0x0078 }
                    goto L_0x0009
                L_0x0078:
                    r7 = move-exception
                    goto L_0x0009
                L_0x007a:
                    android.print.pdf.PrintedPdfDocument r1 = new android.print.pdf.PrintedPdfDocument     // Catch:{ all -> 0x009e }
                    android.support.v4.print.PrintHelper r8 = android.support.p000v4.print.PrintHelper.this     // Catch:{ all -> 0x009e }
                    android.content.Context r8 = r8.mContext     // Catch:{ all -> 0x009e }
                    android.print.PrintAttributes r9 = r5     // Catch:{ all -> 0x009e }
                    r1.<init>(r8, r9)     // Catch:{ all -> 0x009e }
                    r8 = 1
                    android.graphics.pdf.PdfDocument$Page r2 = r1.startPage(r8)     // Catch:{ all -> 0x009e }
                    android.graphics.RectF r0 = new android.graphics.RectF     // Catch:{ all -> 0x009e }
                    android.graphics.pdf.PdfDocument$PageInfo r8 = r2.getInfo()     // Catch:{ all -> 0x009e }
                    android.graphics.Rect r8 = r8.getContentRect()     // Catch:{ all -> 0x009e }
                    r0.<init>(r8)     // Catch:{ all -> 0x009e }
                    r1.finishPage(r2)     // Catch:{ all -> 0x009e }
                    r1.close()     // Catch:{ all -> 0x009e }
                    goto L_0x003f
                L_0x009e:
                    r8 = move-exception
                    r6.close()     // Catch:{ Throwable -> 0x0078 }
                    android.os.ParcelFileDescriptor r9 = r7     // Catch:{ Throwable -> 0x0078 }
                    if (r9 == 0) goto L_0x00ab
                    android.os.ParcelFileDescriptor r9 = r7     // Catch:{ IOException -> 0x00e5 }
                    r9.close()     // Catch:{ IOException -> 0x00e5 }
                L_0x00ab:
                    android.graphics.Bitmap r9 = r4     // Catch:{ Throwable -> 0x0078 }
                    if (r4 == r9) goto L_0x00b2
                    r4.recycle()     // Catch:{ Throwable -> 0x0078 }
                L_0x00b2:
                    throw r8     // Catch:{ Throwable -> 0x0078 }
                L_0x00b3:
                    float r8 = r0.left     // Catch:{ all -> 0x009e }
                    float r9 = r0.top     // Catch:{ all -> 0x009e }
                    r3.postTranslate(r8, r9)     // Catch:{ all -> 0x009e }
                    android.graphics.Canvas r8 = r5.getCanvas()     // Catch:{ all -> 0x009e }
                    r8.clipRect(r0)     // Catch:{ all -> 0x009e }
                    goto L_0x0051
                L_0x00c2:
                    java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ all -> 0x009e }
                    android.os.ParcelFileDescriptor r9 = r7     // Catch:{ all -> 0x009e }
                    java.io.FileDescriptor r9 = r9.getFileDescriptor()     // Catch:{ all -> 0x009e }
                    r8.<init>(r9)     // Catch:{ all -> 0x009e }
                    r6.writeTo(r8)     // Catch:{ all -> 0x009e }
                    r6.close()     // Catch:{ Throwable -> 0x0078 }
                    android.os.ParcelFileDescriptor r8 = r7     // Catch:{ Throwable -> 0x0078 }
                    if (r8 == 0) goto L_0x00dc
                    android.os.ParcelFileDescriptor r8 = r7     // Catch:{ IOException -> 0x00e7 }
                    r8.close()     // Catch:{ IOException -> 0x00e7 }
                L_0x00dc:
                    android.graphics.Bitmap r8 = r4     // Catch:{ Throwable -> 0x0078 }
                    if (r4 == r8) goto L_0x0009
                    r4.recycle()     // Catch:{ Throwable -> 0x0078 }
                    goto L_0x0009
                L_0x00e5:
                    r9 = move-exception
                    goto L_0x00ab
                L_0x00e7:
                    r8 = move-exception
                    goto L_0x00dc
                L_0x00e9:
                    r8 = move-exception
                    goto L_0x0070
                */
                throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.print.PrintHelper.C01191.doInBackground(java.lang.Void[]):java.lang.Throwable");
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Throwable throwable) {
                if (cancellationSignal2.isCanceled()) {
                    writeResultCallback2.onWriteCancelled();
                } else if (throwable == null) {
                    writeResultCallback2.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                } else {
                    Log.e(PrintHelper.LOG_TAG, "Error writing printed content", throwable);
                    writeResultCallback2.onWriteFailed(null);
                }
            }
        }.execute(new Void[0]);
    }

    /* access modifiers changed from: 0000 */
    public Bitmap loadConstrainedBitmap(Uri uri) throws FileNotFoundException {
        Options decodeOptions;
        Bitmap bitmap = null;
        if (uri == null || this.mContext == null) {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        Options opt = new Options();
        opt.inJustDecodeBounds = true;
        loadBitmap(uri, opt);
        int w = opt.outWidth;
        int h = opt.outHeight;
        if (w > 0 && h > 0) {
            int imageSide = Math.max(w, h);
            int sampleSize = 1;
            while (imageSide > MAX_PRINT_SIZE) {
                imageSide >>>= 1;
                sampleSize <<= 1;
            }
            if (sampleSize > 0 && Math.min(w, h) / sampleSize > 0) {
                synchronized (this.mLock) {
                    this.mDecodeOptions = new Options();
                    this.mDecodeOptions.inMutable = true;
                    this.mDecodeOptions.inSampleSize = sampleSize;
                    decodeOptions = this.mDecodeOptions;
                }
                try {
                    bitmap = loadBitmap(uri, decodeOptions);
                    synchronized (this.mLock) {
                        this.mDecodeOptions = null;
                    }
                } catch (Throwable th) {
                    synchronized (this.mLock) {
                        this.mDecodeOptions = null;
                        throw th;
                    }
                }
            }
        }
        return bitmap;
    }

    private Bitmap loadBitmap(Uri uri, Options o) throws FileNotFoundException {
        if (uri == null || this.mContext == null) {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
        InputStream is = null;
        try {
            is = this.mContext.getContentResolver().openInputStream(uri);
            Bitmap decodeStream = BitmapFactory.decodeStream(is, null, o);
            if (is != null) {
                try {
                    is.close();
                } catch (IOException t) {
                    Log.w(LOG_TAG, "close fail ", t);
                }
            }
            return decodeStream;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException t2) {
                    Log.w(LOG_TAG, "close fail ", t2);
                }
            }
        }
    }

    static Bitmap convertBitmapForColorMode(Bitmap original, int colorMode) {
        if (colorMode != 1) {
            return original;
        }
        Bitmap grayscale = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Config.ARGB_8888);
        Canvas c = new Canvas(grayscale);
        Paint p = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0.0f);
        p.setColorFilter(new ColorMatrixColorFilter(cm));
        c.drawBitmap(original, 0.0f, 0.0f, p);
        c.setBitmap(null);
        return grayscale;
    }
}
