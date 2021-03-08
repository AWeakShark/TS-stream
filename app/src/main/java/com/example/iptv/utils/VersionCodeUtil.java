package com.example.iptv.utils;

import android.content.Context;

public class VersionCodeUtil {

    public static String getVersionCodeUtil(Context context){

        String versionName = "";
        try {
            versionName = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),0).versionName;

        }catch (Exception e){
            e.printStackTrace();
        }
        //return versionCode
        return versionName;
    }


}
