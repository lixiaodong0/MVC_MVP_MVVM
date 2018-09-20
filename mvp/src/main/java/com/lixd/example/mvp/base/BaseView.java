package com.lixd.example.mvp.base;

public interface BaseView<P> {

    void showLoading();

    void hideLoading();

    void showToast(String msg);

    void setPresenter(P presenter);

    boolean isActive();
}
