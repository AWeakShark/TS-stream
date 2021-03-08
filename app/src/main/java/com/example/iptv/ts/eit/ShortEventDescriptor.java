package com.example.iptv.ts.eit;

public class ShortEventDescriptor {
    private int mDescriptorTag = 0;
    private int mDescriptorLength = 0;
    private String mIos639LanguageCode = "";
    private int mEventNameLength = 0;
    private String mEventName = "";
    private int mTextLength = 0;
    private String mText = "";

    public ShortEventDescriptor(int descriptorTag, int descriptorLength, byte[] datasOfEit) {
        mDescriptorTag = descriptorTag;
        mDescriptorLength = descriptorLength;
        int bytePosition = 0;
        mIos639LanguageCode = new String(datasOfEit, bytePosition, 3);

        bytePosition += 3;
        mEventNameLength = datasOfEit[bytePosition] & 0xFF;
        bytePosition += 1;
        mEventName = new String(datasOfEit, bytePosition, mEventNameLength);
        bytePosition += mEventNameLength;
        mTextLength = datasOfEit[bytePosition] & 0xFF;
        bytePosition += 1;
        mText = new String(datasOfEit, bytePosition, mTextLength);

    }

    public String getmIos639LanguageCode() {
        return mIos639LanguageCode;
    }

    public int getmEventNameLength() {
        return mEventNameLength;
    }

    public String getmEventName() {
        return mEventName;
    }

    public int getmTextLength() {
        return mTextLength;
    }

    public String getmText() {
        return mText;
    }

    public int getmDescriptorTag() {
        return mDescriptorTag;
    }

    public int getmDescriptorLength() {
        return mDescriptorLength;
    }
}
