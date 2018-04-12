package com.zplh.yk.autoregister.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;

/**
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public  abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        ButterKnife.bind(this);
        initView();
        initData();
    }




    protected abstract int getLayoutID();

    protected abstract void initView();

    protected abstract void initData();


}
