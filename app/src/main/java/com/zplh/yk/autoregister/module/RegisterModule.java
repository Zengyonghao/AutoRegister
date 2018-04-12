package com.zplh.yk.autoregister.module;

import com.zplh.yk.autoregister.bean.RegisterTask;
import com.zplh.yk.autoregister.presnsenter.RegisterPresenter;

/**
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public class RegisterModule extends BaseModule<RegisterTask> {


    private final RegisterPresenter registerPresenter;

    public RegisterModule(RegisterTask task, RegisterPresenter presenter) {
        super(task);
        registerPresenter = presenter;
    }

    //执行注册流程
    @Override
    public void run() {
        registerPresenter.execute(task);
    }
}
