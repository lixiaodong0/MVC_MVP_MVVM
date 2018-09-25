package com.lixd.example.mvp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixd.example.base.data.DetailBean;

import java.util.ArrayList;
import java.util.List;

public class MvpAdapter extends RecyclerView.Adapter<MvpAdapter.MvpViewHolder> {
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
    public MvpAdapter.MvpViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mLayoutInfalter.inflate(R.layout.mvp_recycle_item, viewGroup, false);
        return new MvpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MvpAdapter.MvpViewHolder holder, int position) {
        holder.refresh(mDatas.get(position), position);
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

    class MvpViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImgUserIcon;
        private final TextView mTvUserName;
        private final TextView mTvShareContent;
        private final TextView mTvPublishTime;
        private final TextView mTvTag;
        private final RecyclerView mRvImageList;

        public MvpViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgUserIcon = itemView.findViewById(R.id.img_user_icon);
            mTvUserName = itemView.findViewById(R.id.tv_user_name);
            mTvShareContent = itemView.findViewById(R.id.tv_share_contnet);
            mRvImageList = itemView.findViewById(R.id.rv_img_list);
            mTvPublishTime = itemView.findViewById(R.id.tv_publish_time);
            mTvTag = itemView.findViewById(R.id.tv_tag);
        }

        private void refresh(DetailBean data, int position) {
            mTvUserName.setText(data.who);
            mTvShareContent.setText(data.desc);
            mTvPublishTime.setText(data.publishedAt);
            mTvTag.setText(data.type);
            if (data.images != null && data.images.size() > 0) {
                mRvImageList.setVisibility(View.VISIBLE);
                if (data.images.size() == 1) {
                    mRvImageList.setLayoutManager(new LinearLayoutManager(mContext));
                } else if (data.images.size() % 2 == 0) {
                    mRvImageList.setLayoutManager(new GridLayoutManager(mContext, 2));
                } else if (data.images.size() % 3 == 0) {
                    mRvImageList.setLayoutManager(new GridLayoutManager(mContext, 3));
                }
                mRvImageList.setHasFixedSize(true);
                mRvImageList.setAdapter(new ImageListAdapter(mContext, data.images));
            } else {
                mRvImageList.setVisibility(View.GONE);
            }
        }
    }
}
