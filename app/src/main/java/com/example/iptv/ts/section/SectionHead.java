package com.example.iptv.ts.section;


public class SectionHead {

    private int mTableId = 0;
    private int mSectionSyntaxIndicator = 0;
    private int mSectionLength = 0;
    private int mPublicFields = 0;
    private int mVersionNumber = 0;
    private int mCurrentNextIndicator = 0;
    private int mSectionNumber = 0;
    private int mLastSectionNumber = 0;

    public SectionHead() {

    }

    public SectionHead(byte[] datas1, int sectionStart) {
        byte[] datas = datas1;
        int bytes = sectionStart;
        mTableId = datas[bytes] & 0xff;
        bytes += 1;//1
        mSectionSyntaxIndicator = datas[bytes] >> 7 & 0x1;
        mSectionLength = ((datas[bytes] & 0xf) << 8) | datas[bytes + 1] & 0xFF;
        bytes += 2;//3
        mPublicFields = (datas[bytes] & 0xFF) << 8 | datas[bytes + 1] & 0xFF;
        bytes += 2;//5
        mVersionNumber = datas[bytes] >> 1 & 0x1F;
        mCurrentNextIndicator = (datas[bytes] & 0x1 << 7) >> 7;
        bytes += 1;//6
        mSectionNumber = datas[bytes] & 0xFF;
        bytes += 1;//7
        mLastSectionNumber = datas[bytes] & 0xFF;
    }


    public int getmTableId() {
        return mTableId;
    }

    public int getmSectionSyntaxIndicator() {
        return mSectionSyntaxIndicator;
    }

    public int getmSectionLength() {
        return mSectionLength;
    }

    public int getmVersionNumber() {
        return mVersionNumber;
    }

    public int getmCurrentNextIndicator() {
        return mCurrentNextIndicator;
    }

    public int getmSectionNumber() {
        return mSectionNumber;
    }

    public int getmPublicFields() {
        return mPublicFields;
    }

    public int getmLastSectionNumber() {
        return mLastSectionNumber;
    }


    public void setmTableId(int mTableId) {
        this.mTableId = mTableId;
    }

    public void setmSectionSyntaxIndicator(int mSectionSyntaxIndicator) {
        this.mSectionSyntaxIndicator = mSectionSyntaxIndicator;
    }

    public void setmSectionLength(int mSectionLength) {
        this.mSectionLength = mSectionLength;
    }

    public void setmPublicFields(int mPublicFields) {
        this.mPublicFields = mPublicFields;
    }

    public void setmVersionNumber(int mVersionNumber) {
        this.mVersionNumber = mVersionNumber;
    }

    public void setmCurrentNextIndicator(int mCurrentNextIndicator) {
        this.mCurrentNextIndicator = mCurrentNextIndicator;
    }

    public void setmSectionNumber(int mSectionNumber) {
        this.mSectionNumber = mSectionNumber;
    }

    public void setmLastSectionNumber(int mLastSectionNumber) {
        this.mLastSectionNumber = mLastSectionNumber;
    }

}
