package com.lixd.example.mvp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.lixd.example.base.data.DetailBean;
import com.lixd.example.mvp.base.BaseMvpFragment;

import java.util.List;

public class MvpFragment extends BaseMvpFragment<MvpContract.Presenter> implements MvpContract.View {
    public static final String TAG = MvpFragment.class.getSimpleName();
    private RecyclerView rvShare;
    private ViewStub mVsLayoutEmpty;
    private ViewStub mVsLayoutError;
    private LinearLayout mLlErrorLayout, mLlEmptyLayout;
    private MvpAdapter mAdapter;

    public static final MvpFragment newInstance() {
        MvpFragment fragment = new MvpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPresenter.getShareData();
    }

    @Override
    public void showShareData(List<DetailBean> data) {
        mAdapter.setNewData(data);
        hideLayout(mLlErrorLayout, mLlEmptyLayout);
    }

    @Override
    public void showEmptyLayout() {
        if (mVsLayoutEmpty != null && mVsLayoutEmpty.getParent() != null) {
            mVsLayoutEmpty.inflate();
            mLlEmptyLayout = mRootView.findViewById(R.id.ll_empty_layout);
        }
        hideLayout(mLlErrorLayout);
    }

    @Override
    public void showNetError() {
        if (mVsLayoutError != null && mVsLayoutError.getParent() != null) {
            mVsLayoutError.inflate();
            mLlErrorLayout = mRootView.findViewById(R.id.ll_error_layout);
            mRootView.findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //重新发起请求
                }
            });
        }
        mLlErrorLayout.setVisibility(View.VISIBLE);
    }

    private void hideLayout(View... args) {
        for (View view : args) {
            if (view != null && view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected Object getLayout() {
        return R.layout.mvp_fragment;
    }

    @Override
    protected void initView(View rootView) {
        rvShare = rootView.findViewById(R.id.rv_share);
        rvShare.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MvpAdapter(mContext, null);
        rvShare.setAdapter(mAdapter);
        mVsLayoutEmpty = rootView.findViewById(R.id.layout_empty);
        mVsLayoutError = rootView.findViewById(R.id.layout_error);
    }
}
