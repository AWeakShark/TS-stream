package com.example.iptv.ts.tspacket;

import com.example.iptv.ts.eit.EventInformationSection;
import com.example.iptv.ts.eit.Events;
import com.example.iptv.ts.epginfo.ProgramInfoDisplay;
import com.example.iptv.ts.pat.Program;
import com.example.iptv.ts.pat.ProgramAssociationSection;
import com.example.iptv.ts.sdt.ServiceDescriptorSection;
import com.example.iptv.ts.sdt.Services;
import com.example.iptv.ts.section.GetSectionTableAction;
import com.example.iptv.ts.section.Section;
import com.example.iptv.utils.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ProgramParsing {

    private String mFileName;


    GetSectionTableAction getSectionTableAction;

    public ProgramParsing(String mFileName) {
        this.mFileName = mFileName;
    }

    public ArrayList getProgramList() {


        ArrayList<Section> patList = null;
        ArrayList<Section> sdtList = null;

        ArrayList<Program> programList = null;
        ArrayList<Integer> programNumberList = new ArrayList<>();

        ArrayList<ProgramInfoDisplay> pmtList = new ArrayList<>();
         getSectionTableAction = new GetSectionTableAction(mFileName);
        patList = getSectionTableAction.getSection(Constant.PAT_PID, Constant.PAT_TABLE_ID);

        for (int i = 0; i < patList.size(); i++) {
//            parse PAT
            ProgramAssociationSection pat = new ProgramAssociationSection(patList.get(i).getmSectionHead());
            pat.getPatDatas(patList.get(i).getmSectionDatas());

            programList = pat.getmProgramList();

            for (int j = 0; j < programList.size(); j++) {
                programNumberList.add(programList.get(j).getmProgramNumber());
            }
        }

        sdtList = getSectionTableAction.getSection(Constant.STD_PID, Constant.STD_TABLE_ID);
        for (int i = 0; i < sdtList.size(); i++) {
            ServiceDescriptorSection sdt = new ServiceDescriptorSection(sdtList.get(i).getmSectionHead());
            byte[] sectionDatas = sdtList.get(i).getmSectionDatas();
            sdt.parseSdtSection(Arrays.copyOfRange(sectionDatas, Constant.PAT_HEAD_SECTION_LENGTH, sectionDatas.length));
            ArrayList<Services> list = sdt.getmServicesList();
            for (int j = 0; j < list.size(); j++) {
//                        Here is the program number corresponding to the serviceID
                String serviceId = Integer.toString(list.get(j).getmServiceId());
                for (int k = 0; k < programNumberList.size(); k++) {
                    int programNumber = programNumberList.get(k);
                    if (serviceId .equals(Integer.toString(programNumber))) {
                        String serviceName = list.get(j).getmServiceDescriptors().getmServiceName();
                        pmtList.add(new ProgramInfoDisplay(serviceId,serviceName));
                    }
                }
            }
        }
        return pmtList;
    }


    public ArrayList getEitEvents() {
        ArrayList<Section> eitList = null;
        eitList = getSectionTableAction.getSection(Constant.EIT_PID, Constant.EIT_TABLE_ID);

        int serviceId = 0;
        ArrayList<HashMap> eitEventsList = new ArrayList<>();

        for (int i = 0; i < eitList.size(); i++) {
            EventInformationSection eit = new EventInformationSection(eitList.get(i).getmSectionHead());
            byte[] data = eitList.get(i).getmSectionDatas();
            eit.parseEitSection(Arrays.copyOfRange(data, Constant.PAT_HEAD_SECTION_LENGTH, data.length));
            serviceId = eit.getmSectionHead().getmPublicFields();

            ArrayList<Events> eventsList = null;
            eventsList = eit.getmEventsList();

            for (int j = 0; j < eventsList.size(); j++) {
                HashMap hashMap = new HashMap();
                ProgramInfoDisplay event = eventsList.get(j).event();
                hashMap.put(serviceId, event);
                eitEventsList.add(hashMap);
            }
        }
        return eitEventsList;
    }

}
