package com.zplh.yk.autoregister.base;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public class MyApplication extends Application{
   public static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());//初始化log
        application = this;
    }
}
