package com.example.iptv.ts.sdt;

import android.util.Log;

import com.example.iptv.ts.section.SectionHead;
import com.example.iptv.utils.Constant;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class ServiceDescriptorSection {

    private SectionHead mSectionHead;
    private int mOriginalNetworkId = 0;
    private ArrayList<Services> mServicesList = new ArrayList<>();
    private int mCrc32 = 0;

    public ServiceDescriptorSection(SectionHead sectionHead) {
        this.mSectionHead = sectionHead;
    }

    public void parseSdtSection(byte[] datasOfSdt) {
        int bytePosition = 0;
        mOriginalNetworkId = (datasOfSdt[bytePosition] & 0xFF) << 8 | (datasOfSdt[bytePosition + 1] & 0xFF);
        bytePosition += 2; //10
        bytePosition += 1;//11
        Services services = null;

        for (int i = bytePosition; i < datasOfSdt.length - 4; i = i + 5 + services.getmDescriptorsLoopLength()) {
            bytePosition = i;
            ;
            services = new Services(Arrays.copyOfRange(datasOfSdt,bytePosition,datasOfSdt.length));
            if (services.getmServiceDescriptors().getmDescriptorTag() == Constant.SDT_DESCRIPTOR_TAG) {
                mServicesList.add(services);
            }
        }

        mCrc32 = (datasOfSdt[datasOfSdt.length - 4] & 0x000000FF) << 24 |
                (datasOfSdt[datasOfSdt.length - 3] & 0x000000FF) << 16 |
                (datasOfSdt[datasOfSdt.length - 2] & 0x000000FF) << 8 |
                (datasOfSdt[datasOfSdt.length - 1]) & 0x000000FF;
    }

    public void printDatas() {
        Log.e(TAG, "TableId: " + Integer.toHexString(mSectionHead.getmTableId()));
        Log.e(TAG, "SectionSyntaxIndicator: " + Integer.toHexString(mSectionHead.getmSectionSyntaxIndicator()));
        Log.e(TAG, "SectionLength: " + Integer.toHexString(mSectionHead.getmSectionLength()));
        Log.e(TAG, "TransportStreamId: " + Integer.toHexString(mSectionHead.getmPublicFields()));
        Log.e(TAG, "VersionNumber: " + Integer.toHexString(mSectionHead.getmVersionNumber()));
        Log.e(TAG, "CurrentNextIndicator: " + Integer.toHexString(mSectionHead.getmCurrentNextIndicator()));
        Log.e(TAG, "SectionNumber: " + Integer.toHexString(mSectionHead.getmSectionNumber()));
        Log.e(TAG, "LastSectionNumber: " + Integer.toHexString(mSectionHead.getmLastSectionNumber()));
        Log.e(TAG, "OriginalNetworkId: " + Integer.toHexString(getmOriginalNetworkId()));

        for (int i = 0; i < mServicesList.size(); i++) {
            Log.e(TAG, "ServiceId: " + Integer.toHexString(mServicesList.get(i).getmServiceId()));
            Log.e(TAG, "EitSchedulFlag: " + Integer.toHexString(mServicesList.get(i).getmEitSchedulFlag()));
            Log.e(TAG, "EitPresentFollowingFlag: " + Integer.toHexString(mServicesList.get(i).getmEitPresentFollowingFlag()));
            Log.e(TAG, "RunningStatus: " + Integer.toHexString(mServicesList.get(i).getmRunningStatus()));
            Log.e(TAG, "FreeCaMode: " + Integer.toHexString(mServicesList.get(i).getmFreeCaMode()));
            Log.e(TAG, "DescriptorsLoopLength: " + Integer.toHexString(mServicesList.get(i).getmDescriptorsLoopLength()));

            Log.e(TAG, "DescriptorTag: " + Integer.toHexString(mServicesList.get(i).getmServiceDescriptors().getmDescriptorTag()));
            Log.e(TAG, "DescriptorLength: " + Integer.toHexString(mServicesList.get(i).getmServiceDescriptors().getmDescriptorLength()));
            Log.e(TAG, "ServiceType: " + Integer.toHexString(mServicesList.get(i).getmServiceDescriptors().getmServiceType()));
            Log.e(TAG, "ServiceProviderNameLength: " + Integer.toHexString(mServicesList.get(i).getmServiceDescriptors().getmServiceProviderNameLength()));
            Log.e(TAG, "ServiceProvideName: " + mServicesList.get(i).getmServiceDescriptors().getmServiceProvideName());
            Log.e(TAG, "ServiceNameLength: " + Integer.toHexString(mServicesList.get(i).getmServiceDescriptors().getmServiceNameLength()));
            Log.e(TAG, "ServiceName: " + mServicesList.get(i).getmServiceDescriptors().getmServiceName());
        }
        Log.e(TAG, "mCrc32: " + Integer.toHexString(getmCrc32()));
    }

    public int getmOriginalNetworkId() {
        return mOriginalNetworkId;
    }

    public int getmCrc32() {
        return mCrc32;
    }

    public SectionHead getmSectionHead() {
        return mSectionHead;
    }

    public ArrayList<Services> getmServicesList() {
        return mServicesList;
    }
}
