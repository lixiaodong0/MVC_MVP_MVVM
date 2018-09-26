package com.lixd.example.base.data.source.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lixd.example.base.data.DetailBean;
import com.lixd.example.base.data.ShareBean;
import com.lixd.example.base.data.source.ShareDataSource;
import com.lixd.example.base.net.ApiService;
import com.lixd.example.base.net.RetrofitHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ShareRemoteDataSource implements ShareDataSource {

    private final ApiService mService;

    public ShareRemoteDataSource() {
        mService = RetrofitHelper.getInstance().getService(ApiService.class);
    }

    @Override
    public Observable<List<DetailBean>> getTodayShareData() {
        return mService.getTodayShare()
                .flatMap(new Function<ResponseBody, ObservableSource<List<DetailBean>>>() {
                    @Override
                    public ObservableSource<List<DetailBean>> apply(ResponseBody responseBody) throws Exception {
                        List<DetailBean> datas = new ArrayList<>();
                        Gson gson = new Gson();
                        if (responseBody != null) {
                            String json = responseBody.string();
                            JSONObject jsonObject = new JSONObject(json);
                            if (jsonObject.has("error") && jsonObject.optBoolean("error")) {
                                return Observable.error(new RuntimeException("业务异常"));
                            }
                            JSONArray category = jsonObject.optJSONArray("category");
                            JSONObject results = jsonObject.optJSONObject("results");
                            for (int count = category.length(), i = 0; i < count; i++) {
                                String key = category.getString(i);
                                String value = results.optJSONArray(key).toString();
                                List<DetailBean> bean = gson.fromJson(value, new TypeToken<List<DetailBean>>() {
                                }.getType());
                                datas.addAll(bean);
                            }
                        }
                        return Observable.just(datas);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
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
