package com.lixd.example.mvc;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.lixd.example.base.activity.BaseActivity;
import com.lixd.example.base.data.DetailBean;
import com.lixd.example.base.data.source.ShareRepository;
import com.lixd.example.base.data.source.remote.ShareRemoteDataSource;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MvcActivity extends BaseActivity {
    public static final int MAX_PAGE_SIZE = 50;
    private RecyclerView mRvList;
    private int mCurPage = 1;
    private CompositeDisposable mDisposable;
    private ShareRepository mShareRepository;
    private MvcAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvc;
    }

    @Override
    protected void initView() {
        mRvList = findViewById(R.id.rv_list);
        //mRvList.setLayoutManager(new GridLayoutManager(this, 2));
        mRvList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new MvcAdapter(this, null);
        mRvList.setAdapter(mAdapter);
        mProgressDialog = new ProgressDialog(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mShareRepository = ShareRepository.getInstance(new ShareRemoteDataSource());
        mShareRepository.getCategoryData(mCurPage, MAX_PAGE_SIZE)
                .subscribe(new Observer<List<DetailBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                        showDialog();
                    }

                    @Override
                    public void onNext(List<DetailBean> detailBeans) {
                        mAdapter.setNewData(detailBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MvcActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        hideDialog();
                    }

                    @Override
                    public void onComplete() {
                        hideDialog();
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDisposable != null) {
            mDisposable.clear();
        }
    }
}
