package com.example.iptv.ui.selectfile;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.iptv.adapter.FileAdaptor;
import com.example.iptv.base.BaseViewModel;
import com.example.iptv.base.Status;
import com.example.iptv.interfaces.OnRecyclerItemClickListener;
import com.example.iptv.ts.TsFile;
import com.example.iptv.ts.epginfo.ProgramInfo;
import com.example.iptv.ts.epginfo.ProgramInfoDisplay;
import com.example.iptv.utils.Constant;
import com.example.iptv.utils.TsDataManager;

import java.util.List;

public class MainViewModel extends BaseViewModel {
    private FileAdaptor mFileAdapter;
    private List mSdFileList;
    private Status mStatus = getStatus();

    class MyAsyncTask extends AsyncTask<Void, Integer, List<ProgramInfoDisplay>> {
        List<ProgramInfoDisplay> list;
        @Override
        protected List<ProgramInfoDisplay> doInBackground(Void... voids) {
            list = new ProgramInfo().getProgramInfo();
            mStatus.setType(Constant.SHOW_DIALOG_STATUS);
            TsDataManager.getInstance().setmProgramList(list);
            return list;
        }

        @Override
        protected void onPostExecute(List<ProgramInfoDisplay> programInfoDisplays) {
            super.onPostExecute(programInfoDisplays);
            mStatus.setType(Constant.SHOW_FILE_LIST_STATUS);
            getCurrentStatus().setValue(mStatus);
            if (!TsDataManager.getInstance().getmProgramList().isEmpty()) {
                mStatus.setType(Constant.TO_PROGRAM_ACTIVITY);
                getCurrentStatus().setValue(mStatus);
            }
        }
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        mSdFileList = new TsFile().getFileList();
        mFileAdapter = new FileAdaptor(getApplication().getApplicationContext(), mSdFileList);
        mStatus.setType(Constant.SHOW_FILE_LIST_STATUS);
        getCurrentStatus().setValue(mStatus);

        mFileAdapter.setRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int Position) {
                mStatus.setType(Constant.SHOW_DIALOG_STATUS);
                TsDataManager.getInstance().setmFileName((String) mSdFileList.get(Position));
                MyAsyncTask asyncTask = new MyAsyncTask();
                asyncTask.execute();
            }
        });
    }

    public FileAdaptor getmFileAdapter() {
        return mFileAdapter;
    }

}
