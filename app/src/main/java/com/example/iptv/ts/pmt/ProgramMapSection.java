package com.example.iptv.ts.pmt;

import android.util.Log;

import com.example.iptv.ts.section.SectionHead;
import com.example.iptv.utils.Constant;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ProgramMapSection {

    private SectionHead mSectionHead;
    private int mPcrPid = 0;
    private int mProgramInfoLength = 0;
    private int mCrc32 = 0;
    ArrayList<PMTStream> pmtStreamList = new ArrayList();

    public ProgramMapSection(SectionHead sectionHead) {
        this.mSectionHead = sectionHead;
    }

    public ArrayList parsePmtSection(byte[] datasOfPmt) {
        int bytePosition = Constant.SECTION_HEAD_LENGTH;
//        mProgramNumber =
        mPcrPid = ((datasOfPmt[bytePosition] << 8) | datasOfPmt[bytePosition + 1]) & 0x1FFF;
        bytePosition += 2; //10
        mProgramInfoLength = (datasOfPmt[bytePosition] & 0x0F) << 8 | (datasOfPmt[bytePosition + 1] & 0xFF);
        bytePosition += 2; //12

        if (mProgramInfoLength != 0) {
            bytePosition += mProgramInfoLength;
        }
        int streamType = 0;
        int elementaryPid = 0;
        int esInfoLength = 0;
        ArrayList<EsInfoDescriptors> esDescriptorsList = new ArrayList();

        for (; bytePosition < (mSectionHead.getmSectionLength() + 3) - 4; bytePosition += 5) {
            streamType = datasOfPmt[bytePosition];
            elementaryPid = (datasOfPmt[bytePosition + 1] & 0x1F) << 8 | datasOfPmt[bytePosition + 2] & 0xFF;
            esInfoLength = ((datasOfPmt[bytePosition + 3] & 0x0F) << 8) | datasOfPmt[bytePosition + 4];
            if (esInfoLength != 0) {
                int descriptorTag = 0;
                int descriptorLength = 0;
                for (int i = 1; i <= esInfoLength; ) {
                    descriptorTag = (datasOfPmt[bytePosition + 4 + i]);
                    descriptorLength = (datasOfPmt[bytePosition + 5 + i]);
                    StringBuilder descriptorDatas = new StringBuilder();
                    for (int j = 1; j <= descriptorLength; j++) {
                        int datas = (datasOfPmt[bytePosition + 5 + i + j] & 0xFF);
                        descriptorDatas.append(datas + " ");
                    }
                    i = i + 2 + descriptorLength;
                    esDescriptorsList.add(new EsInfoDescriptors(descriptorTag, descriptorLength, descriptorDatas));
                }
            }
            bytePosition += esInfoLength;
            pmtStreamList.add(new PMTStream(streamType, elementaryPid, esInfoLength, esDescriptorsList));
        }

        mCrc32 = (datasOfPmt[datasOfPmt.length - 4] & 0x000000FF) << 24 |
                (datasOfPmt[datasOfPmt.length - 3] & 0x000000FF) << 16 |
                (datasOfPmt[datasOfPmt.length - 2] & 0x000000FF) << 8 |
                (datasOfPmt[datasOfPmt.length - 1]) & 0x000000FF;

        return pmtStreamList;

    }

    public void printPmtDatas() {
        Log.e(TAG, "TableId: " + Integer.toHexString(mSectionHead.getmTableId()));
        Log.e(TAG, "SectionSyntaxIndicator: " + Integer.toHexString(mSectionHead.getmSectionSyntaxIndicator()));
        Log.e(TAG, "SectionLength: " + Integer.toHexString(mSectionHead.getmSectionLength()));
        Log.e(TAG, "ProgramNumber: " + Integer.toHexString(mSectionHead.getmPublicFields()));
        Log.e(TAG, "VersionNumber: " + Integer.toHexString(mSectionHead.getmVersionNumber()));
        Log.e(TAG, "CurrentNextIndicator: " + Integer.toHexString(mSectionHead.getmCurrentNextIndicator()));
        Log.e(TAG, "SectionNumber: " + Integer.toHexString(mSectionHead.getmSectionNumber()));
        Log.e(TAG, "LastSectionNumber: " + Integer.toHexString(mSectionHead.getmLastSectionNumber()));
        Log.e(TAG, "PcrPid: " + Integer.toHexString(getmPcrPid()));
        Log.e(TAG, "ProgramInfoLength: " + Integer.toHexString(getmProgramInfoLength()));

        for (int i = 0; i < pmtStreamList.size(); i++) {
            Log.e(TAG, "streamType: " + Integer.toHexString(pmtStreamList.get(i).getmStreamType()));
            Log.e(TAG, "elementaryPid: " + Integer.toHexString(pmtStreamList.get(i).getmElementaryPid()));
            Log.e(TAG, "esIndorLength: " + Integer.toHexString(pmtStreamList.get(i).getmEsInfoLength()));
            ArrayList<EsInfoDescriptors> EsInfoDescriptorsList = pmtStreamList.get(i).getmEsDescriptors();
            for (int j = 0; j < EsInfoDescriptorsList.size(); j++) {
                Log.e(TAG, "getmDescriptor_tag: " + Integer.toHexString(EsInfoDescriptorsList.get(j).getmDescriptor_tag()));
                Log.e(TAG, "getmDescriptor_length: " + Integer.toHexString(EsInfoDescriptorsList.get(j).getmDescriptor_length()));
                Log.e(TAG, "getmDescriptor_data: " + EsInfoDescriptorsList.get(j).getmDescriptor_data());
            }
        }
        Log.e(TAG, "mCRC_32: " + Integer.toHexString(getmCrc32()));
        Log.e(TAG, "----------------------------------------------------------------------");
    }

    public int getmPcrPid() {
        return mPcrPid;
    }

    public int getmProgramInfoLength() {
        return mProgramInfoLength;
    }

    public int getmCrc32() {
        return mCrc32;
    }


}
