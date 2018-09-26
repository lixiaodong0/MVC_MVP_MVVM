package com.lixd.example.base.data.source;

import com.lixd.example.base.data.DetailBean;

import java.util.List;

import io.reactivex.Observable;

public interface ShareDataSource {

    Observable<List<DetailBean>> getTodayShareData();

    Observable<List<DetailBean>> getCategoryData(int page, int size);
}
