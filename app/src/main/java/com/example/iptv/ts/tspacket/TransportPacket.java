package com.example.iptv.ts.tspacket;

public class TransportPacket {
    private int mSync_byte = 0; //8
    private int mTranport_error_indictor = 0;//1
    private int mPayload_unit_start_indicator = 0;//1
    private int mTransport_priority = 0;//1
    private int mPid = 0;//13
    private int mTransport_scrambling_control = 0;//2
    private int mAdaptation_field_control = 0;//2
    private int mContinuity_counter = 0;//4


    public TransportPacket(byte[] datas) {

        int bytes = 0;
        mSync_byte = datas[bytes];
        bytes++;
        mTranport_error_indictor = datas[bytes] >> 7;
        mPayload_unit_start_indicator = (datas[bytes] >> 6) & 0x1;
        mTransport_priority = (datas[bytes] >> 5) & 0x1;
        mPid = ((datas[bytes] & 0x1F) << 8 | datas[bytes + 1] & 0xFF);
        bytes += 2;
        mTransport_scrambling_control = datas[bytes] >> 6;
        mAdaptation_field_control = (datas[bytes] >> 4) & 0x3;
        mContinuity_counter = datas[bytes] & 0xf;

    }

    public void setmSync_byte(int mSync_byte) {
        this.mSync_byte = mSync_byte;
    }

    public int getmSync_byte() {
        return mSync_byte;
    }

    public void setmTranport_error_indictor(int mTranport_error_indictor) {
        this.mTranport_error_indictor = mTranport_error_indictor;
    }

    public void setmPayload_unit_start_indicator(int mPayload_unit_start_indicator) {
        this.mPayload_unit_start_indicator = mPayload_unit_start_indicator;
    }

    public void setmTransport_priority(int mTransport_priority) {
        this.mTransport_priority = mTransport_priority;
    }

    public void setmPid(int mPid) {
        this.mPid = mPid;
    }

    public void setmTransport_scrambling_control(int mTransport_scrambling_control) {
        this.mTransport_scrambling_control = mTransport_scrambling_control;
    }

    public void setmAdaptation_field_control(int mAdaptation_field_control) {
        this.mAdaptation_field_control = mAdaptation_field_control;
    }

    public void setmContinuity_counter(int mContinuity_counter) {
        this.mContinuity_counter = mContinuity_counter;
    }


    public int getmTranport_error_indictor() {
        return mTranport_error_indictor;
    }

    public int getmPayload_unit_start_indicator() {
        return mPayload_unit_start_indicator;
    }

    public int getmTransport_priority() {
        return mTransport_priority;
    }

    public int getmPid() {
        return mPid;
    }

    public int getmTransport_scrambling_control() {
        return mTransport_scrambling_control;
    }

    public int getmAdaptation_field_control() {
        return mAdaptation_field_control;
    }

    public int getmContinuity_counter() {
        return mContinuity_counter;
    }


}
