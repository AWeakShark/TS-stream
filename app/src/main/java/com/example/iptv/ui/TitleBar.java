package com.example.iptv.ui;

import android.app.Activity;

public class TitleBar {
    private int mTitleName;
    private int mTitleImageStatus;
    private Activity mActivity;
    public TitleBar(Activity activity, int titleName, int titleImageStatus){
        this.mActivity = activity;
        this.mTitleName = titleName;
        this.mTitleImageStatus = titleImageStatus;
    }

    public int getmTitleName() {
        return mTitleName;
    }

    public void setmTitleName(int mTitleName) {
        this.mTitleName = mTitleName;
    }

    public int getmTitleImageStatus() {
        return mTitleImageStatus;
    }

    public void setmTitleImageStatus(int mTitleImageStatus) {
        this.mTitleImageStatus = mTitleImageStatus;
    }

    public void back(){
        mActivity.finish();
    }


}
