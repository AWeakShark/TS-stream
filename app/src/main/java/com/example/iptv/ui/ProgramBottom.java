package com.example.iptv.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.iptv.R;
import com.example.iptv.ui.about.AboutActivity;

public class ProgramBottom {
    private Activity activity;
    private Drawable ivBottom;
    private int tvBottom;

    public ProgramBottom(Activity activity, Drawable ivBottom, int tvBottom) {
        this.activity = activity;
        this.ivBottom = ivBottom;
        this.tvBottom = tvBottom;
    }

    public void run(View view) {
        if(view.getParent() ==  activity.findViewById(R.id.bottom2)){
            Intent intent = new Intent(activity, AboutActivity.class);
            activity.startActivity(intent);
        }
    }

    public Drawable getIvBottom() {
        return ivBottom;
    }

    public void setIvBottom(Drawable ivBottom) {
        this.ivBottom = ivBottom;
    }

    public int getTvBottom() {
        return tvBottom;
    }

    public void setTvBottom(int tvBottom) {
        this.tvBottom = tvBottom;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
