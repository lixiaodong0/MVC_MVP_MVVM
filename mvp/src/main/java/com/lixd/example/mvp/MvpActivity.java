package com.lixd.example.mvp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.lixd.example.base.activity.BaseActivity;
import com.lixd.example.base.data.source.ShareRepository;
import com.lixd.example.base.data.source.remote.ShareRemoteDataSource;

public class MvpActivity extends BaseActivity {

    private MvpFragment mvpFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.mvp_activity;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            mvpFragment = (MvpFragment) savedInstanceState.get(MvpFragment.TAG);
        } else {
            mvpFragment = MvpFragment.newInstance();
        }
        FragmentManager manager = getSupportFragmentManager();
        if (!mvpFragment.isAdded()) {
            manager.beginTransaction()
                    .add(R.id.fl_container, mvpFragment, MvpFragment.TAG)
                    .commit();
        }
        //绑定V & P 层
        new MvpPresenter(mvpFragment, ShareRepository.getInstance(new ShareRemoteDataSource()));
        manager.beginTransaction().show(mvpFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mvpFragment.isAdded()) {
            getSupportFragmentManager()
                    .putFragment(outState, MvpFragment.TAG, mvpFragment);
        }
    }
}
