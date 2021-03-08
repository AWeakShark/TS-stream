package com.example.iptv.ui.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.example.iptv.utils.Constant;


public class RobotoMediumTextView extends androidx.appcompat.widget.AppCompatTextView {

    public RobotoMediumTextView(Context context) {
        super(context);
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),Constant.FONT_ROBOTO_MEDIUM);
        setTypeface(tf);
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
