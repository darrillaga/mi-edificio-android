package com.miedificio.miedificio;

import android.os.Bundle;

import com.miedificio.miedificio.commons.BaseActivityWithToolbar;

public class HomeActivity extends BaseActivityWithToolbar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.content_home;
    }

    @Override
    public boolean isShowBackButton() {
        return false;
    }
}
