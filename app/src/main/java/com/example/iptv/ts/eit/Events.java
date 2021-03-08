package com.example.iptv.ts.eit;


import android.util.Log;

import com.example.iptv.ts.epginfo.ProgramInfoDisplay;
import com.example.iptv.utils.Constant;

import java.nio.charset.Charset;
import java.util.Arrays;

public class Events {
    private int mEventId = 0;
    private String mStartTime = "";
    private int mDuration = 0;
    private int mRunningStatus = 0;
    private int mFreeCaMode = 0;
    private int mDescriptorsLoopLength = 0;

    private ShortEventDescriptor mShortEventDescriptor = null;
    private ExtendedEventDescriptor mExtendedEventDescriptor = null;


    public Events(byte[] datasOfEit) {
        int bytePosition = 0;
        mEventId = ((datasOfEit[bytePosition] & 0xFF) << 8) | (datasOfEit[bytePosition + 1] & 0xFF);
        bytePosition += 2;

        int date = (datasOfEit[bytePosition] & 0xFF) << 8 | (datasOfEit[bytePosition + 1] & 0xFF);
        bytePosition += 2;
        int time = (datasOfEit[bytePosition] & 0x0000FF) << 16 | (datasOfEit[bytePosition + 1] & 0x0000FF) << 8 | (datasOfEit[bytePosition + 2] & 0x0000FF);

        mStartTime = String.format("%04x", Integer.parseInt(Integer.toHexString(date), 16)) + "" + String.format("%06x", Integer.parseInt(Integer.toHexString(time), 16));

        bytePosition += 3;
        mDuration = (datasOfEit[bytePosition] & 0x0000FF) << 16 |
                (datasOfEit[bytePosition + 1] & 0x0000FF) << 8 |
                (datasOfEit[bytePosition + 2] & 0x0000FF);

        bytePosition += 3;
        mRunningStatus = datasOfEit[bytePosition] >> 5 & 0x7;
        mFreeCaMode = datasOfEit[bytePosition] >> 4 & 0x1;
        mDescriptorsLoopLength = (datasOfEit[bytePosition] & 0xF) << 4 | (datasOfEit[bytePosition + 1] & 0xFF);
        bytePosition += 2;

        for (; bytePosition < datasOfEit.length - 4; ) {
            int descriptorTag = datasOfEit[bytePosition] & 0xFF;
            bytePosition += 1;
            int descriptorLength = datasOfEit[bytePosition] & 0xFF;
            bytePosition += 1;
            byte[] newDatasOfEit = Arrays.copyOfRange(datasOfEit, bytePosition, datasOfEit.length);
            if (descriptorTag == Constant.SHORT_EVENT_DESCRIPTOR_TAG) {
                mShortEventDescriptor = new ShortEventDescriptor(descriptorTag, descriptorLength, newDatasOfEit);
                bytePosition += descriptorLength;
            }
            if (descriptorTag == Constant.EXTENDED_EVENT_DESCRIPTOR_TAG) {
                mExtendedEventDescriptor = new ExtendedEventDescriptor(descriptorTag, descriptorLength, newDatasOfEit);
                bytePosition += descriptorLength;
            }
        }
    }

    public ProgramInfoDisplay event() {
//        startTime
        int start = Integer.parseInt(mStartTime.substring(0, 4), 16);
        int year = (int) ((start - 15078.2) / 365.25);
        int month = (int) ((start - 14956.1 - (year * 365.25)) / 30.6001);
        int day = start - 14956 - (int) (year * 365.25) - (int) (month * 30.6001);
        int k = 0;
        if (month == 14 || month == 15) {
            k = 1;
        } else {
            k = 0;
        }
        year = year + k;
        if (year > 100) {
            year -= 100;
        }
        month = month - 1 - k * 12;

        String date = year + "-" + month + "-" + day;
//       Duration
        int hour = Integer.parseInt(mStartTime.substring(4, 6));
        int minute = Integer.parseInt(mStartTime.substring(6, 8));
        int seconds = Integer.parseInt(mStartTime.substring(8, 10));
        String time = String.format("%02d",hour) + ":" + String.format("%02d",minute);
        String startTime = date + "     " + time;



        String duration = String.format("%06x", Integer.parseInt(Integer.toHexString(getmDuration()), 16));

        int h = Integer.parseInt(duration.substring(0, 2));
        int m = Integer.parseInt(duration.substring(2, 4));
        seconds = Integer.parseInt(duration.substring(4, 6));
        int lastTime = h * 60 + m ;
        Log.d("TAG", "event: "+lastTime);

        int hh = lastTime/60;
        int mm = lastTime%60;
        hour+=hh;
        minute+=mm;
        Log.d("TAG", "hour: "+hour);
        Log.d("TAG", "minute: "+minute);
        if(minute>=60){
            minute-=60;
            hour+=1;
        }
        if(hour>=24){
            hour-=24;
        }
        String tt = String.format("%02d",hour)+":"+String.format("%02d",minute);

        time = time+"-"+tt;

        String languageCode = getmShortEventDescriptor().getmIos639LanguageCode();
        String eventName = getmShortEventDescriptor().getmEventName();
        StringBuilder eventNameStr = new StringBuilder();
        String text = getmShortEventDescriptor().getmText();
        StringBuilder textStr = new StringBuilder();
        for (int i = 0; i < eventName.length(); i++) {
            if (eventName.charAt(i) > 'a' && eventName.charAt(i) < 'z' || eventName.charAt(i) > 'A' && eventName.charAt(i) < 'Z' || eventName.charAt(i) == 32) {
                eventNameStr.append(eventName.charAt(i));
            }
        }
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) > 'a' && text.charAt(i) < 'z' || text.charAt(i) > 'A' && text.charAt(i) < 'Z' || text.charAt(i) == 32) {
                textStr.append(text.charAt(i));
            }
        }
        Charset.defaultCharset();

//        String str = "startTime:" + startTime + "\nduration:" + lastTime + "分钟\nlanguageCode:" + languageCode + "\neventName:" + eventNameStr + "\ntext:" + textStr;

        return new ProgramInfoDisplay(time,eventNameStr);
    }

    public int getmEventId() {
        return mEventId;
    }

    public void setmEventId(int mEventId) {
        this.mEventId = mEventId;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public int getmDuration() {
        return mDuration;
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public int getmRunningStatus() {
        return mRunningStatus;
    }

    public void setmRunningStatus(int mRunningStatus) {
        this.mRunningStatus = mRunningStatus;
    }

    public int getmFreeCaMode() {
        return mFreeCaMode;
    }

    public void setmFreeCaMode(int mFreeCaMode) {
        this.mFreeCaMode = mFreeCaMode;
    }

    public int getmDescriptorsLoopLength() {
        return mDescriptorsLoopLength;
    }

    public void setmDescriptorsLoopLength(int mDescriptorsLoopLength) {
        this.mDescriptorsLoopLength = mDescriptorsLoopLength;
    }

    public ShortEventDescriptor getmShortEventDescriptor() {
        return mShortEventDescriptor;
    }

    public void setmShortEventDescriptor(ShortEventDescriptor mShortEventDescriptor) {
        this.mShortEventDescriptor = mShortEventDescriptor;
    }

    public ExtendedEventDescriptor getmExtendedEventDescriptor() {
        return mExtendedEventDescriptor;
    }

    public void setmExtendedEventDescriptor(ExtendedEventDescriptor mExtendedEventDescriptor) {
        this.mExtendedEventDescriptor = mExtendedEventDescriptor;
    }
}
