package com.zplh.yk.autoregister.ui;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.mylibrary.utils.WxAdbUtils;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zplh.yk.autoregister.R;
import com.zplh.yk.autoregister.base.BaseActivity;
import com.zplh.yk.autoregister.bean.PhoneBean;
import com.zplh.yk.autoregister.bean.RegisterTask;
import com.zplh.yk.autoregister.callback.RegisterCallback;
import com.zplh.yk.autoregister.module.RegisterModule;
import com.zplh.yk.autoregister.presnsenter.RegisterPresenter;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.zplh.yk.autoregister.config.Constant.DEFAULT_PWD;

public class MainActivity extends BaseActivity implements RegisterCallback {


    @BindView(R.id.tv_go_register)
    TextView tvGoRegister;
    @SuppressLint("NewApi")
    private final Deque<RegisterModule> readyTasks = new ArrayDeque<>();
    private ExecutorService pool = Executors.newSingleThreadExecutor() ;
    private RegisterPresenter registerPresenter;
    private Future<?> currentFuture;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

        registerPresenter = new RegisterPresenter(this,this);



    }

    @OnClick(R.id.tv_go_register)
    public void onStartClick(){

        applyTask();


    }

    private void applyTask(){

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //开始获取手机号
        OkHttpUtils.get().url(getString(R.string.host_address).concat(getString(R.string.get_phone))).build().execute(new StringCallback() {


            @Override
            public void onError(Call call, Exception e, int i) {
                Logger.t("获取电话号码失败").d("错误原因=" + e.getMessage());
            }

            @Override
            public void onResponse(String s, int i) {

                Logger.t("获取电话号码").d(s);
                try {
                    PhoneBean phoneBean = JSON.parseObject(s,PhoneBean.class);
                    if (phoneBean==null||phoneBean.getStatus() != 1 || TextUtils.isEmpty(phoneBean.getData().getNick())) {
                        Logger.t("获取电话号码失败").d("state=" + phoneBean.getInfo());
                        return;
                    }
                    Logger.t("电话号码").d(phoneBean.getData().getPhone());
                    RegisterTask registerTask = new RegisterTask(phoneBean.getData().getPhone(), DEFAULT_PWD, phoneBean.getData().getNick());
                    RegisterModule registerModule = new RegisterModule(registerTask, registerPresenter);
                    currentFuture = pool.submit(registerModule);
                }catch(IllegalStateException e) {

                        return;
            }
        }
        });


    }



    @Override
    public void onStart(RegisterTask task) {
        Logger.t(task.getPhone()).d("开始执行注册");
    }

    @Override
    public void onProgress(RegisterTask task, String progressMessage) {
        Logger.t(task.getPhone()).d(progressMessage);

    }

    @Override
    public void onSuccess(RegisterTask task) {
        Logger.t(task.getPhone()).d("注册成功");
        try {
            Response phone = OkHttpUtils.get().url(getString(R.string.host_address).concat(getString(R.string.confirm)))
                    .addParams("phone", task.getPhone()).build().execute();
            String string = phone.body().string();
            runOnUiThread(this::applyTask);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onError(RegisterTask task, String errorMsg) {
        Logger.t(task.getPhone()).d("注册失败"+"  原因:"+errorMsg);
        WxAdbUtils.closeWx();
        currentFuture.cancel(true);
        while (!currentFuture.isCancelled()){
        }
        applyTask();
    }
}
