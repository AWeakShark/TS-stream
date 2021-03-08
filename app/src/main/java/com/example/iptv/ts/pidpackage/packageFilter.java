package com.example.iptv.ts.pidpackage;

import android.util.Log;

import com.example.iptv.ts.pat.Program;
import com.example.iptv.ts.pat.ProgramAssociationSection;
import com.example.iptv.ts.pmt.PMTStream;
import com.example.iptv.ts.pmt.ProgramMapSection;
import com.example.iptv.ts.section.GetSectionTableAction;
import com.example.iptv.ts.section.Section;
import com.example.iptv.ts.tspacket.ParsePacketLengthAction;
import com.example.iptv.utils.Constant;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.iptv.utils.WriteToTextUtil.writeToText;

public class packageFilter {
    private String mFileName;

    public packageFilter(String mFileName) {
        this.mFileName = mFileName;
    }

    public void getPackageDatas(int targetPid, String ServiceName) {
        Log.e("TAG", "getPackageDatas: "+targetPid+" -"+ServiceName );

        GetSectionTableAction getSectionTableAction = new GetSectionTableAction(mFileName);
        Log.e("TAG", "getPackageDatas: "+mFileName );
        List<Section> patSectionList = getSectionTableAction.getSection(Constant.PAT_PID, Constant.PAT_TABLE_ID);

        ArrayList<Program> program = new ArrayList<>();
        ArrayList<Integer> programPidList = new ArrayList<>();

        for (int i = 0; i < patSectionList.size(); i++) {
            ProgramAssociationSection pat = new ProgramAssociationSection(patSectionList.get(i).getmSectionHead());
            pat.getPatDatas(patSectionList.get(i).getmSectionDatas());
            program = pat.getmProgramList();
        }

        for (int i = 0; i < program.size(); i++) {
            if (program.get(i).getmProgramNumber() == targetPid) {
                int pp = program.get(i).getmProgramMapPid();
                ArrayList<Section> list = getSectionTableAction.getSection(pp, Constant.PMT_TABLE_ID);
                ArrayList<PMTStream> pmtStreamsList = new ArrayList<>();
                programPidList = new ArrayList<>();
                for (int j = 0; j < list.size(); j++) {
                    ProgramMapSection pmt = new ProgramMapSection(list.get(j).getmSectionHead());
                    pmtStreamsList = pmt.parsePmtSection(list.get(j).getmSectionDatas());
                    for (int k = 0; k < pmtStreamsList.size(); k++) {
                        programPidList.add(pmtStreamsList.get(k).getmElementaryPid());
                    }
                }
            }
        }

        File file = new File(Constant.SDCARD_FILE, mFileName);
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ParsePacketLengthAction parsePacketLengthAction = new ParsePacketLengthAction();
        int len = parsePacketLengthAction.getPacketLength(mFileName);

        byte[] packetDatas = new byte[len];
        try {
            while (bis.read(packetDatas) != -1) {
                int pid = (packetDatas[1] & 0x1F) << 8 | packetDatas[2] & 0xFF;
                for (int i = 0; i < programPidList.size(); i++) {
                    if (pid == programPidList.get(i)) {
                        writeToText(packetDatas, ServiceName);
                    }
                }
            }
        } catch (IOException e) {

        }
    }
}
