package com.example.iptv.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;

import com.example.iptv.ts.pidpackage.packageFilter;

import java.io.File;
import java.lang.reflect.Method;

public class PlayVideoUtil  {

    public static void play(Activity activity ,int programNum, String name){
        packageFilter packageFilter = new packageFilter(TsDataManager.getInstance().getmFileName());
        packageFilter.getPackageDatas(programNum, name);

        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        intent.setClassName(Constant.VLC_PACKAGE_NAME, Constant.VLC_CLASS_NAME);
        intent.setAction(Intent.ACTION_VIEW);


        File file = new File(Constant.SDCARD_FILE, name + ".mp4");

        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "video/*");
        }
        activity.startActivity(intent);
    }
}
