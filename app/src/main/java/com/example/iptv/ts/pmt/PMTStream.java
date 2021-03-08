package com.example.iptv.ts.pmt;
import java.util.ArrayList;

public class PMTStream {
    private int mStreamType = 0;
    private int mElementaryPid = 0;
    private int mEsInfoLength = 0;
    ArrayList<EsInfoDescriptors> mEsDescriptors = null;

    public PMTStream(int mStreamType, int mElementaryPid, int mEsInfoLength, ArrayList mEsDescriptors) {
        this.mStreamType = mStreamType;
        this.mElementaryPid = mElementaryPid;
        this.mEsInfoLength = mEsInfoLength;
        this.mEsDescriptors = mEsDescriptors;
    }

    public int getmStreamType() {
        return mStreamType;
    }

    public int getmElementaryPid() {
        return mElementaryPid;
    }

    public int getmEsInfoLength() {
        return mEsInfoLength;
    }

    public ArrayList getmEsDescriptors() {
        return mEsDescriptors;
    }
}
