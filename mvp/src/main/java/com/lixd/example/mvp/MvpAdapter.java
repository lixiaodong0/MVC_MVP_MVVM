package com.lixd.example.mvp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixd.example.base.data.DetailBean;

import java.util.ArrayList;
import java.util.List;

public class MvpAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<DetailBean> mDatas;
    private LayoutInflater mLayoutInfalter;

    public MvpAdapter(Context context, List<DetailBean> datas) {
        mContext = context;
        mDatas = datas;
        mLayoutInfalter = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View inflate = mLayoutInfalter.inflate(R.layout.mvp_recycle_item, viewGroup, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setNewData(List<DetailBean> newData) {
        mDatas = newData;
        notifyDataSetChanged();
    }

    public void addData(List<DetailBean> moreData) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.addAll(moreData);
        notifyDataSetChanged();
    }

    static class MvpViewHolder extends RecyclerView.ViewHolder {

        public MvpViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
