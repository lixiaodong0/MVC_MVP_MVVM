package com.lixd.example.base.data.source;

import com.lixd.example.base.data.DetailBean;
import com.lixd.example.base.data.ShareBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ShareRepository implements ShareDataSource {
    private static ShareRepository INSTANCE;
    private final ShareDataSource mRemote;

    private ShareRepository(ShareDataSource remote) {
        mRemote = remote;
    }

    public static final ShareRepository getInstance(ShareDataSource remote) {
        if (INSTANCE == null) {
            INSTANCE = new ShareRepository(remote);
        }
        return INSTANCE;
    }

    @Override
    public Observable<ShareBean<String>> getTodayShareData() {
        return mRemote.getTodayShareData()
                .flatMap(new Function<ShareBean<String>, ObservableSource<ShareBean<String>>>() {
                    @Override
                    public ObservableSource<ShareBean<String>> apply(ShareBean<String> stringShareBean) throws Exception {
                        if (stringShareBean.error) {
                            return Observable.error(new RuntimeException("业务异常"));
                        }
                        return Observable.just(stringShareBean);
                    }
                });
    }

    @Override
    public Observable<List<DetailBean>> getCategoryData(int page, int size) {
        return mRemote.getCategoryData(page, size);
    }
}
