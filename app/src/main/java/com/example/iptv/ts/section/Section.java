package com.example.iptv.ts.section;


public class Section {
    SectionHead mSectionHead = null;
    byte[] mSectionDatas = null;

    public Section(SectionHead sectionHead, byte[] sectionDatas){
        this.mSectionHead = sectionHead;
        this.mSectionDatas = sectionDatas;
    }

    public Section(){}

    public SectionHead getmSectionHead() {
        return mSectionHead;
    }

    public byte[] getmSectionDatas() {
        return mSectionDatas;
    }

    public void setmSectionHead(SectionHead mSectionHead) {
        this.mSectionHead = mSectionHead;
    }

    public void setmSectionDatas(byte[] mSectionDatas) {
        this.mSectionDatas = mSectionDatas;
    }
}
