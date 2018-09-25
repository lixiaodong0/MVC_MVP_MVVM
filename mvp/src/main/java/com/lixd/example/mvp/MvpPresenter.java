package com.lixd.example.mvp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lixd.example.base.data.DetailBean;
import com.lixd.example.base.data.ShareBean;
import com.lixd.example.base.data.source.ShareDataSource;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

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
                .flatMap(new Function<ShareBean<String>, ObservableSource<List<DetailBean>>>() {
                    @Override
                    public ObservableSource<List<DetailBean>> apply(ShareBean<String> data) throws Exception {
                        String results = data.results;
                        Log.e("getShareData", results);
                        JSONObject jsonObject = new JSONObject(results);
                        Gson gson = new Gson();
                        List<DetailBean> detailBeans = new ArrayList<>();
                        List<String> category = data.category;
                        if (category != null && category.size() > 0) {
                            for (int count = category.size(), i = 0; i < count; i++) {
                                String key = category.get(i);
                                String json = jsonObject.getJSONArray(key).toString();
                                List<DetailBean> lists = gson.fromJson(json, new TypeToken<List<DetailBean>>() {
                                }.getType());
                                detailBeans.addAll(lists);
                            }
                        }
                        return Observable.just(detailBeans);
                    }
                })
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
