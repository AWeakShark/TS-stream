package com.example.iptv.ui.about;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.iptv.base.BaseViewModel;


public class AboutViewModel extends BaseViewModel {

    public String version ;

    public AboutViewModel(@NonNull Application application) {
        super(application);
    }

}
