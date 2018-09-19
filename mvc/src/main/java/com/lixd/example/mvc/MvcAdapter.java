package com.lixd.example.mvc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lixd.example.base.data.DetailBean;

import java.util.ArrayList;
import java.util.List;

public class MvcAdapter extends RecyclerView.Adapter<MvcAdapter.MvcViewHolder> {
    private List<DetailBean> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MvcAdapter(Context context, List<DetailBean> data) {
        mContext = context;
        mData = data;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MvcViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.rv_item_mvc, viewGroup, false);
        return new MvcViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MvcViewHolder mvcViewHolder, int i) {
        DetailBean detailBean = mData.get(i);
        Glide.with(mContext)
                .load(detailBean.url)
                .into(mvcViewHolder.imgPic);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setNewData(List<DetailBean> newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    public void addData(List<DetailBean> moreData) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(moreData);
        notifyDataSetChanged();
    }


    static class MvcViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPic;

        public MvcViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPic = itemView.findViewById(R.id.img_pic);
        }
    }
}
