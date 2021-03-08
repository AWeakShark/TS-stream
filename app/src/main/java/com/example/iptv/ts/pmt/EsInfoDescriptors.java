package com.example.iptv.ts.pmt;

public class EsInfoDescriptors {

    private int mDescriptor_tag;
    private int mDescriptor_length;
    private StringBuilder mDescriptor_data;

    public EsInfoDescriptors(int mDescriptor_tag, int mDescriptor_length, StringBuilder mDescriptor_data) {
        this.mDescriptor_tag = mDescriptor_tag;
        this.mDescriptor_length = mDescriptor_length;
        this.mDescriptor_data = mDescriptor_data;
    }

    public int getmDescriptor_tag() {
        return mDescriptor_tag;
    }

    public int getmDescriptor_length() {
        return mDescriptor_length;
    }

    public StringBuilder getmDescriptor_data() {
        return mDescriptor_data;
    }
}
