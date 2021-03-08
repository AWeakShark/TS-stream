package com.example.iptv.utils;

import android.os.Environment;

import java.io.File;

public class Constant {

    public static final File SDCARD_FILE = Environment.getExternalStorageDirectory();
    public static final int SYNC_BYTE = 0x47;
    public static final int PACKET_LENTH_NORMAL = 188;
    public static final int PACKET_LENTH_BIG = 204;
    public static final int JUDGE_NUMBER = 5;
    public static final int BYTE_ARRAY_LENGTH_BIG = 1024;
    public static final int SECTION_NUMBER_MAX_VALUE = 256;
    public static final int SECTION_HEAD_LENGTH = 8;
    public static final int EIT_PID = 0x12;
    public static final int EIT_TABLE_ID = 0x4e;
    public static final int PMT_TABLE_ID = 0x02;
    public static final int PAT_PID = 0x00;
    public static final int PAT_TABLE_ID = 0x00;
    public static final int SHORT_EVENT_DESCRIPTOR_TAG = 0x4d;
    public static final int EXTENDED_EVENT_DESCRIPTOR_TAG = 0x4e;
    public static final int PAT_HEAD_SECTION_LENGTH = 8;
    public static final int SDT_DESCRIPTOR_TAG = 0x48;
    public static final int STD_PID = 0x0011;
    public static final int STD_TABLE_ID = 0x42;
    public static final String VLC_PACKAGE_NAME = "org.videolan.vlc";
    public static final String VLC_CLASS_NAME = "org.videolan.vlc.StartActivity";
    public static final String  FONT_ROBOTO_REGULAR = "Roboto-Regular.ttf";
    public static final String  FONT_ROBOTO_MEDIUM = "Roboto-Medium.ttf";
    public static final String  DEFAULT_TIME = "00:00";
    public static final String  NO_EVENT_TEXT = "No EPG";

    public static final int SHOW_FILE_LIST_STATUS = 3;
    public static final int SHOW_DIALOG_STATUS = 0;
    public static final int SHOW_DIALOG_STATUS1 = 10;
    public static final int DISMISS_DIALOG_STATUS = 100;
    public static final int  FARVORITE_STATUS = 2;
    public static final int  ALREADLY_FARVORITE_STATUS = 4;
    public static final int BACK_PROGRAM_LIST_STATUS = 5;
    public static final int SHOW_FRAGMENT_STATUS = 7;
    public static final int SHOW_PROGRAM_LIST_STATUS = 8;
    public static final int DELETE_HISTORY_STATUS = 9;
    public static final int HIDE_KEY_BOARD = 11;
    public static final int SHOW_FAV_LIST = 1;
    public static final int TO_PROGRAM_ACTIVITY = 12;
    public static final int BACK = 13;
    public static final int CHANGE = 14;


}
