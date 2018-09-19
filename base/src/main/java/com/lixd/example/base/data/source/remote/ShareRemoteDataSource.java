package com.lixd.example.base.data.source.remote;

import com.lixd.example.base.data.DetailBean;
import com.lixd.example.base.data.ShareBean;
import com.lixd.example.base.data.source.ShareDataSource;
import com.lixd.example.base.net.ApiService;
import com.lixd.example.base.net.RetrofitHelper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ShareRemoteDataSource implements ShareDataSource {

    private final ApiService mService;

    public ShareRemoteDataSource() {
        mService = RetrofitHelper.getInstance().getService(ApiService.class);
    }

    @Override
    public Observable<ShareBean<String>> getTodayShareData() {
        return mService.getTodayShare();
    }

    @Override
    public Observable<List<DetailBean>> getCategoryData(int page, int size) {
        return mService.getCategory(page, size)
                .flatMap(new Function<ShareBean<List<DetailBean>>, ObservableSource<List<DetailBean>>>() {
                    @Override
                    public ObservableSource<List<DetailBean>> apply(ShareBean<List<DetailBean>> shareBean) throws Exception {
                        if (shareBean.error) {
                            return Observable.error(new RuntimeException("业务异常"));
                        }
                        return Observable.just(shareBean.results);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
