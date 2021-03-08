package com.example.iptv.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.iptv.R;
import com.example.iptv.utils.LoadingAnimation;

public class MyDialog extends Dialog {
    private Context mContext;

    public MyDialog(@NonNull Context context) {
        super(context,R.style.MyDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        ImageView imageView = findViewById(R.id.iv_pic_loading);
        LoadingAnimation.load(mContext, imageView);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);

    }
}
