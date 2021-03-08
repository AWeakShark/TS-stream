package com.example.iptv.ts.section;

import android.util.Log;

import com.example.iptv.ts.tspacket.ParsePacketLengthAction;
import com.example.iptv.ts.tspacket.TransportPacket;
import com.example.iptv.utils.Constant;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


//    get complete section
public class GetSectionTableAction {


    private File mVideoFile ;
    private int mSectionPosition = 0;
    private int[] mSectionNumberRecord = null;
    private String mFileName;

    public GetSectionTableAction(String mFileName) {
        this.mFileName = mFileName;
    }

    /**
     * Get the section based on the pid and table_id
     *
     * @param targetPID
     * @param targetTableId
     * @return section arraylist
     */
    public ArrayList getSection(int targetPID, int targetTableId) {

        mVideoFile = new File(Constant.SDCARD_FILE,mFileName);
        mSectionPosition = 0;
        BufferedInputStream inputStream = null;
        ArrayList<Section> sectionList = new ArrayList<>();
        int packetLength = 0;
        int packetValidStartPosition = 0;
        mSectionNumberRecord = new int[Constant.SECTION_NUMBER_MAX_VALUE + 1];
//    get TsPacket's length and packet's valid start position
        ParsePacketLengthAction parsePacketLengthAction = new ParsePacketLengthAction();
        packetLength = parsePacketLengthAction.getPacketLength(mFileName);
        packetValidStartPosition = parsePacketLengthAction.getPacketValidPosition();
        if (packetLength == 0) {
            try {
                throw new Exception();
            } catch (Exception e) {
                Log.e("TAG", "parseTsPacket: error packet!");
                e.printStackTrace();
            }
        }
//    get a complete section
        try {
            int sectionNumber = 0;
            int lastSectionNumber = 0;
            int sectionLength = 0;
            SectionHead sectionHead = null;
            int sectionStart = 0;
            int continuityCounter = 0;
            int nextContinuityCounter = 0;
            boolean isSectionCombineOver = false;

            inputStream = new BufferedInputStream(new FileInputStream(mVideoFile));
            inputStream.skip(packetValidStartPosition);
            byte[] tempDatas = new byte[packetLength];
            boolean isHeadSection = true;
            byte[] datasOfSection = null;

            while (inputStream.read(tempDatas) != -1) {
                TransportPacket transportPacket = new TransportPacket(tempDatas);
                if (transportPacket.getmPid() == targetPID) {
                    if (transportPacket.getmPayload_unit_start_indicator() == 1 && isHeadSection) {
//                        can get section head
                        sectionStart = getSectionStart(transportPacket,tempDatas);
                        sectionHead = new SectionHead(tempDatas, sectionStart);
                        sectionLength = sectionHead.getmSectionLength();
                        sectionNumber = sectionHead.getmSectionNumber();
                        lastSectionNumber = sectionHead.getmLastSectionNumber();

                        if (sectionHead.getmTableId() == targetTableId) {
                            datasOfSection = new byte[sectionLength + 3];
                            continuityCounter = transportPacket.getmContinuity_counter();
                            isHeadSection = false;

//                            determine if the sectionNumber has been read?
                            if (mSectionNumberRecord[sectionNumber] == 1) {
                                continue;
                            }
//                            begin to combine section
                            isSectionCombineOver = combineSection(packetLength, sectionStart, sectionLength, tempDatas, datasOfSection);

//                            a section has combined
                            if (isSectionCombineOver) {
//                                begin to parse section
                                sectionList.add(new Section(sectionHead, datasOfSection));

                                mSectionNumberRecord[sectionNumber]++;
                                mSectionNumberRecord[Constant.SECTION_NUMBER_MAX_VALUE]++;
                                if (mSectionNumberRecord[Constant.SECTION_NUMBER_MAX_VALUE] == lastSectionNumber + 1) {
                                    return sectionList;
                                } else {
                                    isHeadSection = true;
                                    mSectionPosition = 0;
                                }
                            }
                        }
                    } else if (!isHeadSection) {
                        //no the first of the section
                        sectionStart = getSectionStart(transportPacket,tempDatas);
                        nextContinuityCounter = transportPacket.getmContinuity_counter();
                        if ((nextContinuityCounter - continuityCounter == 1) || ((nextContinuityCounter == 0) && (continuityCounter == 0xf))) {
                            continuityCounter = nextContinuityCounter;
                            if (mSectionNumberRecord[sectionNumber] == 1) {
                                continue;
                            }
                            isSectionCombineOver = combineSection(packetLength, sectionStart, sectionLength, tempDatas, datasOfSection);
                            if (isSectionCombineOver) {
//                                begin to parse section
                                sectionList.add(new Section(sectionHead, datasOfSection));

                                mSectionNumberRecord[sectionNumber]++;
                                mSectionNumberRecord[Constant.SECTION_NUMBER_MAX_VALUE]++;
                                if (mSectionNumberRecord[Constant.SECTION_NUMBER_MAX_VALUE] == lastSectionNumber + 1) {
                                    return sectionList;
                                } else {
                                    isHeadSection = true;
                                    mSectionPosition = 0;
                                }
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sectionList;
    }

    private boolean combineSection(int packetLength, int sectionStart, int sectionLength, byte[] mDatas, byte[] datasOfSection) {

        boolean isSectionCombineOver = false;
        int copySize = 0;
        copySize = packetLength - sectionStart;
//        if packet's length is 204
        if (packetLength == Constant.PACKET_LENTH_BIG) {
            copySize = copySize - 16;
        }
        if (copySize > (sectionLength + 3 - mSectionPosition)) {
            copySize = sectionLength + 3 - mSectionPosition;
            isSectionCombineOver = true;
        }
//        write the datas of mDatas to datasOfPat

        for (int i = 0; i < copySize; i++) {
            datasOfSection[mSectionPosition + i] = mDatas[i + sectionStart];
        }

        mSectionPosition = mSectionPosition + copySize;
        return isSectionCombineOver;
    }

    private int getSectionStart(TransportPacket transportPacket, byte[] datas) {

        int adaptationFieldControl = transportPacket.getmAdaptation_field_control();
        int sectionStart = 0;
        switch (adaptationFieldControl) {
            case 0b00:
                break;
            case 0b01:
                sectionStart = 4;
                break;
            case 0b10:
                break;
            case 0b11:
                sectionStart = 5 + datas[4];
                break;
        }
        if (transportPacket.getmPayload_unit_start_indicator() != 0) {
            sectionStart = sectionStart + datas[sectionStart] + 1;
        }
        return sectionStart;
    }

}
