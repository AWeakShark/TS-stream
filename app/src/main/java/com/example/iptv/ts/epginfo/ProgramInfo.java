package com.example.iptv.ts.epginfo;

import com.example.iptv.ts.tspacket.ProgramParsing;
import com.example.iptv.utils.Constant;
import com.example.iptv.utils.TsDataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramInfo {


    public List<ProgramInfoDisplay> getProgramInfo() {

        ProgramParsing programParsing = new ProgramParsing(TsDataManager.getInstance().getmFileName());
        List<ProgramInfoDisplay> mProgramList = programParsing.getProgramList();
        ArrayList<HashMap> eitEventList = programParsing.getEitEvents();
        List<ProgramInfoDisplay> programList = new ArrayList();
        for (int i = 0; i < mProgramList.size(); i++) {
            String programNum = mProgramList.get(i).getmProgramNumber();
            String serviceName = mProgramList.get(i).getmServiceName();
            String time = "";
            StringBuilder eventText = null;
            for (int j = 0; j < eitEventList.size(); j++) {
                ProgramInfoDisplay info = (ProgramInfoDisplay) eitEventList.get(j).get(programNum);
                if (info != null) {
                    time = info.getTime();
                    eventText = info.getEventText();
                    break;
                } else {
                    time = Constant.DEFAULT_TIME;
                    eventText = new StringBuilder(Constant.NO_EVENT_TEXT);
                }
            }
            programList.add(new ProgramInfoDisplay(programNum, serviceName, time, eventText));
        }

        return programList;
    }
}
