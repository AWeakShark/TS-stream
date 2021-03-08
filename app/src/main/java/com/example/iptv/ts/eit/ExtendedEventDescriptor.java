package com.example.iptv.ts.eit;

public class ExtendedEventDescriptor {
   private int mDescriptorTag = 0;
   private int mDescriptorLength = 0;
    private int mDescriptorNumber = 0;
    private int mLastDescriptorNumber = 0;
    private String mIos639LanguageCode = "";
    private int mLengthOfItems = 0;
    private int mTextLength = 0;
    private String mTextChar = "";

    public ExtendedEventDescriptor(int descriptorTag, int descriptorLength, byte[] datasOfEit) {
        mDescriptorTag = descriptorTag;
        mDescriptorLength = descriptorLength;
        int bytePosition = 0;
        mDescriptorNumber = datasOfEit[bytePosition] >> 4 & 0x0F;
        mLastDescriptorNumber = datasOfEit[bytePosition] & 0x0F;
        bytePosition += 1;
        mIos639LanguageCode = new String(datasOfEit, bytePosition, 3);
        bytePosition += 3;
        mLengthOfItems = datasOfEit[bytePosition] & 0xFF;
        bytePosition += 1;
        bytePosition += mLengthOfItems;
        mTextLength = datasOfEit[bytePosition] & 0xFF;
        bytePosition += 1;

        mTextChar = new String(datasOfEit, bytePosition, mTextLength);

    }

    public int getmDescriptorNumber() {
        return mDescriptorNumber;
    }

    public int getmLastDescriptorNumber() {
        return mLastDescriptorNumber;
    }

    public String getmIos639LanguageCode() {
        return mIos639LanguageCode;
    }

    public int getmLengthOfItems() {
        return mLengthOfItems;
    }

    public int getmTextLength() {
        return mTextLength;
    }

    public String getmTextChar() {
        return mTextChar;
    }

    public int getmDescriptorLength() {
        return mDescriptorLength;
    }

    public int getmDescriptorTag() {
        return mDescriptorTag;
    }
}
