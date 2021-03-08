package com.example.iptv.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.iptv.R;

public class LoadingAnimation {

    public static void load(Context context, ImageView imageView){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading);
        animation.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(animation);
    }
}
