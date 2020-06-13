package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.widget.TextViewCompat;
import android.support.p003v7.appcompat.C0205R;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.ActionMode.Callback;
import android.widget.TextView;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.DialogTitle */
public class DialogTitle extends TextView {
    public DialogTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DialogTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogTitle(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        if (layout != null) {
            int lineCount = layout.getLineCount();
            if (lineCount > 0 && layout.getEllipsisCount(lineCount - 1) > 0) {
                setSingleLine(false);
                setMaxLines(2);
                TypedArray a = getContext().obtainStyledAttributes(null, C0205R.styleable.TextAppearance, 16842817, 16973892);
                int textSize = a.getDimensionPixelSize(C0205R.styleable.TextAppearance_android_textSize, 0);
                if (textSize != 0) {
                    setTextSize(0, (float) textSize);
                }
                a.recycle();
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    public void setCustomSelectionActionModeCallback(Callback actionModeCallback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, actionModeCallback));
    }
}
