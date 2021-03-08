package com.example.iptv.ts.tspacket;


import com.example.iptv.utils.Constant;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ParsePacketLengthAction {
    private int mPosition0x47 = 0;
    private int mPacketValidStartPosition = 0;
    private boolean mIsValid = true;
    private int mConsecutive0x47 = 0;
    private int mReadPosition = 0;
    private int mReadCount = 0;

    /**
     * get TsPacket's length
     *
     * @return the length of TsPacket
     */
    public int getPacketLength(String fileName) {
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File(Constant.SDCARD_FILE, fileName)));
            byte[] bytes = new byte[Constant.BYTE_ARRAY_LENGTH_BIG];
            while ((mReadCount = inputStream.read(bytes, 0, Constant.BYTE_ARRAY_LENGTH_BIG)) != -1) {
                for (mReadPosition = 0; mReadPosition < mReadCount; mReadPosition++) {
                    if (bytes[mReadPosition] == Constant.SYNC_BYTE) {
                        if (mIsValid) {
                            mPacketValidStartPosition = mReadPosition;
                            mIsValid = false;
                        }
                        mPosition0x47 = mReadPosition;
                        inputStream.mark(Constant.PACKET_LENTH_BIG * Constant.JUDGE_NUMBER);

                        int packetLength = judgePacketLength(inputStream, bytes, Constant.PACKET_LENTH_NORMAL);
                        if (packetLength == Constant.PACKET_LENTH_NORMAL) {
                            getPacketValidPosition();
                            return packetLength;
                        }
                        inputStream.reset();
                        inputStream.mark(Constant.PACKET_LENTH_BIG * Constant.JUDGE_NUMBER);

                        packetLength = judgePacketLength(inputStream, bytes, Constant.PACKET_LENTH_BIG);
                        if (packetLength == Constant.PACKET_LENTH_BIG) {
                            getPacketValidPosition();
                            return packetLength;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private int judgePacketLength(BufferedInputStream inputStream, byte[] bytes, int packetLength) {
        while (mConsecutive0x47 <= Constant.JUDGE_NUMBER) {
            mReadPosition = mReadPosition + packetLength;

            if (mReadPosition >= mReadCount - 1) {
                try {
                    inputStream.read(bytes, 0, Constant.BYTE_ARRAY_LENGTH_BIG);
                    mReadPosition = mReadPosition + packetLength - mReadCount;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (mConsecutive0x47 == Constant.JUDGE_NUMBER) {
                return packetLength;
            }

            if (bytes[mReadPosition] == Constant.SYNC_BYTE) {
                mConsecutive0x47++;
            } else {
                mConsecutive0x47 = 0;
                mReadPosition = mPosition0x47;
                mIsValid = true;
                return 0;
            }
        }
        return 0;
    }

    public int getPacketValidPosition() {
        return mPacketValidStartPosition;
    }
}
