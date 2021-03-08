package com.example.iptv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iptv.R;
import com.example.iptv.databinding.LayoutMainFileItemBinding;
import com.example.iptv.interfaces.OnRecyclerItemClickListener;

import java.util.List;

public class FileAdaptor extends RecyclerView.Adapter<FileAdaptor.MyViewHolder> {
    private Context mContext;
    private List<String> mList;
    private OnRecyclerItemClickListener mListener;

    public FileAdaptor(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutMainFileItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.layout_main_file_item,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.binding.setFile(mList.get(position));
        holder.binding.executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onRecyclerItemClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        LayoutMainFileItemBinding binding;

        public MyViewHolder(LayoutMainFileItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
