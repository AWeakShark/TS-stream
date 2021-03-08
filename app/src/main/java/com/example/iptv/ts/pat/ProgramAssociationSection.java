package com.example.iptv.ts.pat;

import android.util.Log;

import com.example.iptv.ts.section.SectionHead;
import com.example.iptv.utils.Constant;

import java.util.ArrayList;


public class ProgramAssociationSection {

    private static final String TAG = "tag";

    private SectionHead mPatHead;
    private int mNetWorkPID;
    private ArrayList<Program> mProgramList = new ArrayList<>();
    private int mCRC_32;

    public ProgramAssociationSection(SectionHead patHead) {
        this.mPatHead = patHead;
    }

    public void getPatDatas(byte[] datasOfPat) {

        int programNumber;
        int programMapPID;

        for (int i = Constant.PAT_HEAD_SECTION_LENGTH; i < datasOfPat.length - 4; i += 4) {
            programNumber = (datasOfPat[i] & 0xFF) << 8 | datasOfPat[1 + i] & 0xFF;
            int pid = (datasOfPat[2 + i] & 0x1F) << 8 | datasOfPat[3 + i] & 0xFF;
            if (programNumber == 0x00) {
                mNetWorkPID = pid;
            } else {
                programMapPID = pid;
                mProgramList.add(new Program(programNumber, programMapPID));
            }
        }
        mCRC_32 = (datasOfPat[datasOfPat.length - 4] & 0x000000FF) << 24 |
                (datasOfPat[datasOfPat.length - 3] & 0x000000FF) << 16 |
                (datasOfPat[datasOfPat.length - 2] & 0x000000FF) << 8 |
                (datasOfPat[datasOfPat.length - 1]) & 0x000000FF;

    }

    public void printPatDatas() {
        Log.e(TAG, "Table_id: " + Integer.toHexString(mPatHead.getmTableId()));
        Log.e(TAG, "SectionSyntaxIndicator: " + Integer.toHexString(mPatHead.getmSectionSyntaxIndicator()));
        Log.e(TAG, "SectionLength: " + Integer.toHexString(mPatHead.getmSectionLength()));
        Log.e(TAG, "TransportStreamId: " + Integer.toHexString(mPatHead.getmPublicFields()));
        Log.e(TAG, "VersionNumber: " + Integer.toHexString(mPatHead.getmVersionNumber()));
        Log.e(TAG, "CurrentNextIndicator: " + Integer.toHexString(mPatHead.getmCurrentNextIndicator()));
        Log.e(TAG, "SectionNumber: " + Integer.toHexString(mPatHead.getmSectionNumber()));
        Log.e(TAG, "LastSectionNumber: " + Integer.toHexString(mPatHead.getmLastSectionNumber()));
        Log.e(TAG, "NetWorkPID: " + Integer.toHexString(mNetWorkPID));
        for (int i = 0; i < mProgramList.size(); i++) {
            Log.e(TAG, "program_number:" + Integer.toHexString(mProgramList.get(i).getmProgramNumber()) + "=>pid: " + Integer.toHexString(mProgramList.get(i).getmProgramMapPid()));
        }
        Log.e(TAG, "mCRC_32: " + Integer.toHexString(mCRC_32));
    }


    public SectionHead getmPatHead() {
        return mPatHead;
    }

    public int getmNetWorkPID() {
        return mNetWorkPID;
    }

    public ArrayList<Program> getmProgramList() {
        return mProgramList;
    }

    public int getmCRC_32() {
        return mCRC_32;
    }
}
