package com.example.iptv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iptv.R;
import com.example.iptv.databinding.LayoutSearchHistoryItemBinding;
import com.example.iptv.interfaces.OnRecyclerItemClickListener;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mList;
    private OnRecyclerItemClickListener mListener;

    public HistoryAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSearchHistoryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.layout_search_history_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if (mList.size() > 0) {
            holder.mBinding.tvHistory.setText(mList.get(position));
        }
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
        if (mList.size() >= 3) {
            mList = mList.subList(mList.size()-3,mList.size());
        }
        return mList.size();
    }


    public void update(List<String> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private LayoutSearchHistoryItemBinding mBinding;

        public MyViewHolder( LayoutSearchHistoryItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

    }
}
