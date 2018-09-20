package com.lixd.example.mvp;

import android.os.Bundle;

import com.lixd.example.base.data.DetailBean;
import com.lixd.example.mvp.base.BaseMvpFragment;

import java.util.List;

public class MvpFragment extends BaseMvpFragment<MvpContract.Presenter> implements MvpContract.View {
    public static final String TAG = MvpFragment.class.getSimpleName();

    public static final MvpFragment newInstance() {
        MvpFragment fragment = new MvpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showShareData(List<DetailBean> data) {

    }

    @Override
    public void showEmptyLayout() {

    }

    @Override
    public void showNetError() {

    }

    @Override
    protected Object getLayout() {
        return R.layout.fragment_mvp;
    }

    @Override
    protected void initView() {

    }
}
