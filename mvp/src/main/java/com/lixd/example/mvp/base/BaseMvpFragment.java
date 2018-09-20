package com.lixd.example.mvp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseMvpFragment<P extends BasePresenter> extends Fragment implements BaseView<P> {

    protected P mPresenter;
    protected ProgressDialog mProgressDialog;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Object layout = getLayout();
        if (layout instanceof Integer) {
            int layoutId = (int) layout;
            View view = inflater.inflate(layoutId, container, false);
            return view;
        } else if (layout instanceof View) {
            View layoutView = (View) layout;
            return layoutView;
        } else {
            throw new ClassCastException("layout只能是Integer,View类型");
        }
    }

    protected abstract Object getLayout();

    protected abstract void initView();

    protected void initData(Bundle savedInstanceState) {

    }

    protected void initEvent() {

    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        mProgressDialog.setMessage("加载中.......");
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }
}
