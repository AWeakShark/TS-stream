package com.example.iptv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iptv.R;
import com.example.iptv.databinding.LayoutProgramItemBinding;
import com.example.iptv.interfaces.OnRecyclerItemClickListener;
import com.example.iptv.ts.epginfo.ProgramInfoDisplay;

import java.util.List;

public class FavoriteProgramAdapter extends RecyclerView.Adapter<FavoriteProgramAdapter.MyViewHolder> {
    private Context mContext;
    private List<ProgramInfoDisplay> mList;
    private  OnRecyclerItemClickListener mListener;

    public FavoriteProgramAdapter(Context mContext, List<ProgramInfoDisplay> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        mListener = listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutProgramItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.layout_program_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.binding.setProgram(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.textViewProgramNumber.setText(String.format("%03d", position + 1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRecyclerItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LayoutProgramItemBinding binding;

        public MyViewHolder(LayoutProgramItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
