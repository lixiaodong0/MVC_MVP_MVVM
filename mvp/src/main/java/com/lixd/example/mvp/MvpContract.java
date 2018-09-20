package com.lixd.example.mvp;

import com.lixd.example.base.data.DetailBean;
import com.lixd.example.mvp.base.BasePresenter;
import com.lixd.example.mvp.base.BaseView;

import java.util.List;

public interface MvpContract {

    interface Presenter extends BasePresenter {
        void getShareData();
    }

    interface View extends BaseView<Presenter> {
        void showShareData(List<DetailBean> data);

        void showEmptyLayout();

        void showNetError();
    }
}
