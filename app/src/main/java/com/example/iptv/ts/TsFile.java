package com.example.iptv.ts;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TsFile {

    public TsFile() { }

    public List getFileList() {
        List fileList = new ArrayList();
        File file = Environment.getExternalStorageDirectory();
        String[] list = file.list();
        for (int i = 0; i < list.length; i++) {
            if (list[i].contains(".ts")) {
                fileList.add((list[i]));
            }
        }
        return fileList;
    }
}
