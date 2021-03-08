package com.example.iptv.ui.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.example.iptv.utils.Constant;


public class RobotoRegularTextView extends androidx.appcompat.widget.AppCompatTextView {

    public RobotoRegularTextView(Context context) {
        super(context);
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),Constant.FONT_ROBOTO_REGULAR);
        setTypeface(tf);
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
