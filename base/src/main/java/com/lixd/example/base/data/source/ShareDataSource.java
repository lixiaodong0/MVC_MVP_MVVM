package com.lixd.example.base.data.source;

import com.lixd.example.base.data.DetailBean;
import com.lixd.example.base.data.ShareBean;

import java.util.List;

import io.reactivex.Observable;

public interface ShareDataSource {

    Observable<ShareBean<String>> getTodayShareData();

    Observable<List<DetailBean>> getCategoryData(int page, int size);
}
