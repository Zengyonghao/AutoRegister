package com.zplh.yk.autoregister.module;

/**
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public abstract class   BaseModule<T> implements Runnable {

    protected final T task;

    public BaseModule(T task) {
        this.task = task;

    }


    public T getTask() {
        return task;
    }
}
