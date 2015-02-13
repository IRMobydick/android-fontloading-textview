package net.kianoni.fontloader;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;

/**
 * Created by kilp on 08/02/15.
 */
public class TextView extends android.widget.TextView {

    public TextView(Context context) {
        super(context);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttributes(context, attrs);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttributes(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        readAttributes(context, attrs);
    }

    private void readAttributes(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TextView);

        String fontFile = null;
        String fontFamily = null;
        String fontVariant = null;
        String fontFilePattern = null;

        final int N = styledAttributes.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = styledAttributes.getIndex(i);
            if (R.styleable.TextView_fontFile == attr) {
                fontFile = styledAttributes.getString(attr);
            }
            if (R.styleable.TextView_fontFamily == attr) {
                fontFamily = styledAttributes.getString(attr);
            }
            if (R.styleable.TextView_fontVariant == attr) {
                fontVariant = styledAttributes.getString(attr);
            }
            if (R.styleable.TextView_fontFilePattern == attr) {
                fontFilePattern = styledAttributes.getString(attr);
            }
        }

        styledAttributes.recycle();

        if (fontFamily != null && fontVariant != null && fontFilePattern != null) {
            if (fontFile != null) {
                throw new RuntimeException("Attempting to set fontFile together with fontFilePattern");
            }
            this.setTypeface(FontLoader.getInstance().getTypeFace(context, fontFamily, fontVariant, fontFilePattern));
        }

        if (fontFile != null) {
            this.setTypeface(FontLoader.getInstance().getTypeFace(context, fontFile));
        }

    }

}
