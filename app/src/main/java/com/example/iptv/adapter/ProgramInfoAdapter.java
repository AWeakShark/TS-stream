package com.example.iptv.adapter;

import android.annotation.SuppressLint;
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
import com.example.iptv.interfaces.OnRecyclerItemLongClickListener;
import com.example.iptv.ts.epginfo.ProgramInfoDisplay;

import java.util.List;


public class ProgramInfoAdapter extends RecyclerView.Adapter<ProgramInfoAdapter.MyViewHolder> {
    private Context mContext;
    private List<ProgramInfoDisplay> mList;
    private View mInflator;
    private OnRecyclerItemLongClickListener mLongListener;
    private OnRecyclerItemClickListener mListener;

    public ProgramInfoAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setRecyclerItemClickLongListener(OnRecyclerItemLongClickListener listener) {
        mLongListener = listener;
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

    @SuppressLint("DefaultLocale")
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mLongListener != null) {
                    mLongListener.onRecyclerItemLongClickListener(position);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void update(List<ProgramInfoDisplay> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LayoutProgramItemBinding binding;

        public MyViewHolder(LayoutProgramItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
