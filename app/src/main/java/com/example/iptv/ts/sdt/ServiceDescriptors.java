package com.example.iptv.ts.sdt;

public class ServiceDescriptors {

    int mDescriptorTag = 0;
    int mDescriptorLength = 0;
    int mServiceType = 0;
    int mServiceProviderNameLength = 0;
    String mServiceProvideName = null;
    int mServiceNameLength = 0;
    String mServiceName = null;

    public ServiceDescriptors(byte[] datasOfSdt, int bytePosition) {
        mDescriptorTag = datasOfSdt[bytePosition];
        bytePosition += 1;
        mDescriptorLength = datasOfSdt[bytePosition];
        bytePosition += 1;
        mServiceType = datasOfSdt[bytePosition];
        bytePosition += 1;
        mServiceProviderNameLength = datasOfSdt[bytePosition];
        bytePosition += 1;
        mServiceProvideName = new String(datasOfSdt, bytePosition, mServiceProviderNameLength);
        bytePosition += mServiceProviderNameLength;

        mServiceNameLength = datasOfSdt[bytePosition];
        bytePosition += 1;
        mServiceName = new String(datasOfSdt, bytePosition, mServiceNameLength);
    }


    public int getmDescriptorTag() {
        return mDescriptorTag;
    }

    public int getmServiceType() {
        return mServiceType;
    }

    public int getmServiceProviderNameLength() {
        return mServiceProviderNameLength;
    }

    public String getmServiceProvideName() {
        return mServiceProvideName;
    }

    public int getmServiceNameLength() {
        return mServiceNameLength;
    }

    public String getmServiceName() {
        return mServiceName;
    }

    public int getmDescriptorLength() {
        return mDescriptorLength;
    }

    public void setmDescriptorTag(int mDescriptorTag) {
        this.mDescriptorTag = mDescriptorTag;
    }

    public void setmDescriptorLength(int mDescriptorLength) {
        this.mDescriptorLength = mDescriptorLength;
    }

    public void setmServiceType(int mServiceType) {
        this.mServiceType = mServiceType;
    }

    public void setmServiceProviderNameLength(int mServiceProviderNameLength) {
        this.mServiceProviderNameLength = mServiceProviderNameLength;
    }

    public void setmServiceProvideName(String mServiceProvideName) {
        this.mServiceProvideName = mServiceProvideName;
    }

    public void setmServiceNameLength(int mServiceNameLength) {
        this.mServiceNameLength = mServiceNameLength;
    }

    public void setmServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }
}
