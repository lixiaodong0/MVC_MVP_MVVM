package com.lixd.example.base.data.source;

import com.lixd.example.base.data.DetailBean;

import java.util.List;

import io.reactivex.Observable;

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
    public Observable<List<DetailBean>> getTodayShareData() {
        return mRemote.getTodayShareData();
    }

    @Override
    public Observable<List<DetailBean>> getCategoryData(int page, int size) {
        return mRemote.getCategoryData(page, size);
    }
}
