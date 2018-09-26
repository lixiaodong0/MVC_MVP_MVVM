package com.lixd.example.mvp;

import com.lixd.example.base.data.DetailBean;
import com.lixd.example.base.data.source.ShareDataSource;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MvpPresenter implements MvpContract.Presenter {
    private MvpContract.View mView;
    private ShareDataSource mDataSource;
    private final CompositeDisposable mCompositeDisposable;

    public MvpPresenter(MvpContract.View view, ShareDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void getShareData() {
        mDataSource.getTodayShareData()
                .subscribe(new Observer<List<DetailBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(List<DetailBean> detailBeans) {
                        if (mView.isActive()) {
                            if (detailBeans != null && detailBeans.size() > 0) {
                                mView.showShareData(detailBeans);
                            } else {
                                mView.showEmptyLayout();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
