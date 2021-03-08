package com.example.iptv.utils;

import android.content.Context;

import com.example.iptv.ui.MyDialog;

public class DialogUtil {

    private static volatile DialogUtil dialogUtil;
    private MyDialog myDialog;

    public static DialogUtil getInstance() {
        if (dialogUtil == null) {
            synchronized (TsDataManager.class) {
                if (dialogUtil == null) {
                    dialogUtil = new DialogUtil();
                }
            }
        }
        return dialogUtil;
    }

    public void show(Context context) {
        myDialog = new MyDialog(context);
        myDialog.show();
    }

    public void dismiss() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }
}
