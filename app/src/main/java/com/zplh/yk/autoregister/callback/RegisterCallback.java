package com.zplh.yk.autoregister.callback;

import com.zplh.yk.autoregister.bean.RegisterTask;

/**
 * 注册回调
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public interface RegisterCallback {
    void onStart(RegisterTask task);//注册任务开始
    void onProgress(RegisterTask task,String progressMessage);
    void onSuccess(RegisterTask task);
    void onError(RegisterTask task,String errorMsg);
}
