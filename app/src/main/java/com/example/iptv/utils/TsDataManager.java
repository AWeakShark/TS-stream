package com.example.iptv.utils;

import com.example.iptv.ts.epginfo.ProgramInfoDisplay;

import java.util.ArrayList;
import java.util.List;

public class TsDataManager {

    private String mFileName = "";
    private List<String> mHistorList = new ArrayList<>();
    private List<ProgramInfoDisplay> mProgramList = new ArrayList<>();
    private List<ProgramInfoDisplay> mFavList = new ArrayList<>();
    private static volatile TsDataManager tsDataManager;

    public static TsDataManager getInstance() {
        if (tsDataManager == null) {
            synchronized (TsDataManager.class) {
                if (tsDataManager == null) {
                    tsDataManager = new TsDataManager();
                }
            }
        }
        return tsDataManager;
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }
    public List<String> getmHistorList() {
        return mHistorList;
    }

    public void setmHistorList(List<String> mHistorList) {
        this.mHistorList = mHistorList;
    }

    public List<ProgramInfoDisplay> getmProgramList() {
        return mProgramList;
    }

    public void setmProgramList(List<ProgramInfoDisplay> mProgramList) {
        this.mProgramList = mProgramList;
    }

    public List<ProgramInfoDisplay> getmFavList() {
        return mFavList;
    }

    public void setmFavList(List<ProgramInfoDisplay> mFavList) {
        this.mFavList = mFavList;
    }
}
