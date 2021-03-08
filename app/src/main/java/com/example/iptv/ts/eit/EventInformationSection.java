package com.example.iptv.ts.eit;

import android.util.Log;


import com.example.iptv.ts.section.SectionHead;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class EventInformationSection {

    private SectionHead mSectionHead = null;

    private int mTransportStreamId = 0;
    private int mOriginalNetworkId = 0;
    private int mSegmentLastSectionNumber = 0;
    private int mLastTableId = 0;
    ArrayList<Events> mEventsList = new ArrayList<>();
    private int mCrc32 = 0;

    public EventInformationSection(SectionHead mSectionHead) {
        this.mSectionHead = mSectionHead;
    }
    public void parseEitSection(byte[] datasOfEit) {
        int bytePosition = 0;
        mTransportStreamId = (datasOfEit[bytePosition] & 0xFF) << 8 | datasOfEit[bytePosition + 1] & 0xFF;
        bytePosition += 2;
        mOriginalNetworkId = (datasOfEit[bytePosition] & 0xFF) << 8 | datasOfEit[bytePosition + 1] & 0xFF;
        bytePosition += 2;
        mSegmentLastSectionNumber = datasOfEit[bytePosition] & 0xFF;
        bytePosition += 1;
        mLastTableId = datasOfEit[bytePosition] & 0xFF;
        bytePosition += 1;

        Events events = new Events(Arrays.copyOfRange(datasOfEit, bytePosition, datasOfEit.length));
        mEventsList.add(events);

        mCrc32 = (datasOfEit[datasOfEit.length - 4] & 0x000000FF) << 24 |
                (datasOfEit[datasOfEit.length - 3] & 0x000000FF) << 16 |
                (datasOfEit[datasOfEit.length - 2] & 0x000000FF) << 8 |
                (datasOfEit[datasOfEit.length - 1]) & 0x000000FF;
    }

    public void printEitDatas() {
        Log.e(TAG, "TableId: " + Integer.toHexString(mSectionHead.getmTableId()));
        Log.e(TAG, "SectionSyntaxIndicator: " + Integer.toHexString(mSectionHead.getmSectionSyntaxIndicator()));
        Log.e(TAG, "SectionLength: " + Integer.toHexString(mSectionHead.getmSectionLength()));
        Log.e(TAG, "ServiceId: " + Integer.toHexString(mSectionHead.getmPublicFields()));
        Log.e(TAG, "VersionNumber: " + Integer.toHexString(mSectionHead.getmVersionNumber()));
        Log.e(TAG, "CurrentNextIndicator: " + Integer.toHexString(mSectionHead.getmCurrentNextIndicator()));
        Log.e(TAG, "SectionNumber: " + Integer.toHexString(mSectionHead.getmSectionNumber()));
        Log.e(TAG, "LastSectionNumber: " + Integer.toHexString(mSectionHead.getmLastSectionNumber()));
        Log.e(TAG, "TransportStreamId: " + Integer.toHexString(getmTransportStreamId()));
        Log.e(TAG, "OriginalNetworkId: " + Integer.toHexString(getmOriginalNetworkId()));
        Log.e(TAG, "SegmentLastSectionNumber: " + Integer.toHexString(getmSegmentLastSectionNumber()));
        Log.e(TAG, "LastTableId: " + Integer.toHexString(getmLastTableId()));

        for (int i = 0; i < mEventsList.size(); i++) {
            Log.e(TAG, "EventId: " + Integer.toHexString(mEventsList.get(i).getmEventId()));
            Log.e(TAG, "StartTime: " + mEventsList.get(i).getmStartTime());
            Log.e(TAG, "Duration: " + Integer.toHexString(mEventsList.get(i).getmDuration()));
            Log.e(TAG, "RunningStatus: " + Integer.toHexString(mEventsList.get(i).getmRunningStatus()));
            Log.e(TAG, "FreeCaMode: " + Integer.toHexString(mEventsList.get(i).getmFreeCaMode()));
            Log.e(TAG, "DescriptorsLoopLength: " + Integer.toHexString(mEventsList.get(i).getmDescriptorsLoopLength()));
            Log.e(TAG, "LastTableId: " + Integer.toHexString(getmLastTableId()));
            if (mEventsList.get(i).getmShortEventDescriptor() != null) {
                Log.e(TAG, "DescriptorTag: " + Integer.toHexString(mEventsList.get(i).getmShortEventDescriptor().getmDescriptorTag()));
                Log.e(TAG, "DescriptorLength: " + Integer.toHexString(mEventsList.get(i).getmShortEventDescriptor().getmDescriptorLength()));
                Log.e(TAG, "Ios639LanguageCode: " + mEventsList.get(i).getmShortEventDescriptor().getmIos639LanguageCode());
                Log.e(TAG, "EventNameLength: " + Integer.toHexString(mEventsList.get(i).getmShortEventDescriptor().getmEventNameLength()));
                Log.e(TAG, "EventName: " + mEventsList.get(i).getmShortEventDescriptor().getmEventName());
                Log.e(TAG, "TextLength: " + Integer.toHexString(mEventsList.get(i).getmShortEventDescriptor().getmTextLength()));
                Log.e(TAG, "Text: " + mEventsList.get(i).getmShortEventDescriptor().getmText());
            }
            if (mEventsList.get(i).getmExtendedEventDescriptor() != null) {
                Log.e(TAG, "DescriptorTag: " + Integer.toHexString(mEventsList.get(i).getmExtendedEventDescriptor().getmDescriptorTag()));
                Log.e(TAG, "DescriptorLength: " + Integer.toHexString(mEventsList.get(i).getmExtendedEventDescriptor().getmDescriptorLength()));
                Log.e(TAG, "DescriptorNumber: " + Integer.toHexString(mEventsList.get(i).getmExtendedEventDescriptor().getmDescriptorNumber()));
                Log.e(TAG, "LastDescriptorNumber: " + Integer.toHexString(mEventsList.get(i).getmExtendedEventDescriptor().getmLastDescriptorNumber()));
                Log.e(TAG, "Ios639LanguageCode: " + mEventsList.get(i).getmExtendedEventDescriptor().getmIos639LanguageCode());
                Log.e(TAG, "LengthOfItems: " + Integer.toHexString(mEventsList.get(i).getmExtendedEventDescriptor().getmLengthOfItems()));
                Log.e(TAG, "TextLength: " + Integer.toHexString(mEventsList.get(i).getmExtendedEventDescriptor().getmTextLength()));
                Log.e(TAG, "TextChar: " + mEventsList.get(i).getmExtendedEventDescriptor().getmTextChar());
            }
        }

        Log.e(TAG, "Crc32: " + Integer.toHexString(getmCrc32()));
    }

    public SectionHead getmSectionHead() {
        return mSectionHead;
    }

    public int getmTransportStreamId() {
        return mTransportStreamId;
    }

    public int getmOriginalNetworkId() {
        return mOriginalNetworkId;
    }

    public int getmSegmentLastSectionNumber() {
        return mSegmentLastSectionNumber;
    }

    public int getmLastTableId() {
        return mLastTableId;
    }

    public ArrayList<Events> getmEventsList() {
        return mEventsList;
    }

    public int getmCrc32() {
        return mCrc32;
    }
}
