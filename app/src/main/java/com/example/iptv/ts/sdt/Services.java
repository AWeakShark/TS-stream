package com.example.iptv.ts.sdt;

public class Services {
    private static final String TAG = "ServiceTAG";
    private int mServiceId;
    private int mEitSchedulFlag = 0;
    private int mEitPresentFollowingFlag = 0;
    private int mRunningStatus = 0;
    private int mFreeCaMode = 0;
    private int mDescriptorsLoopLength = 0;
    private ServiceDescriptors mServiceDescriptors = null;

    public Services(byte[] datasOfSdt) {
        int bytePosition = 0;
        mServiceId = (datasOfSdt[bytePosition] & 0xFF) << 8 | (datasOfSdt[bytePosition + 1] & 0xFF);
        bytePosition += 2;

        mEitSchedulFlag = (datasOfSdt[bytePosition] >> 1) & 0x1;
        mEitPresentFollowingFlag = datasOfSdt[bytePosition] & 0x1;
        bytePosition += 1;
        mRunningStatus = datasOfSdt[bytePosition] >> 5 & 0x7;
        mFreeCaMode = datasOfSdt[bytePosition] >> 4 & 0x1;
        mDescriptorsLoopLength = datasOfSdt[bytePosition] << 8 & 0x0F | datasOfSdt[bytePosition + 1] & 0xFF;
        bytePosition += 2;
        mServiceDescriptors = new ServiceDescriptors(datasOfSdt, bytePosition);
    }

    public int getmServiceId() {
        return mServiceId;
    }

    public int getmEitSchedulFlag() {
        return mEitSchedulFlag;
    }

    public int getmEitPresentFollowingFlag() {
        return mEitPresentFollowingFlag;
    }

    public int getmRunningStatus() {
        return mRunningStatus;
    }

    public int getmFreeCaMode() {
        return mFreeCaMode;
    }

    public int getmDescriptorsLoopLength() {
        return mDescriptorsLoopLength;
    }

    public ServiceDescriptors getmServiceDescriptors() {
        return mServiceDescriptors;
    }
}
