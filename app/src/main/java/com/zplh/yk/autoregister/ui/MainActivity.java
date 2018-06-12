package com.zplh.yk.autoregister.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.utils.NodeUtils;
import com.example.mylibrary.utils.WxAdbUtils;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zplh.yk.autoregister.R;
import com.zplh.yk.autoregister.adapter.ListPhonesAdapter;
import com.zplh.yk.autoregister.base.BaseActivity;
import com.zplh.yk.autoregister.bean.PhoneBean;
import com.zplh.yk.autoregister.bean.RegisterTask;
import com.zplh.yk.autoregister.callback.RegisterCallback;
import com.zplh.yk.autoregister.module.RegisterModule;
import com.zplh.yk.autoregister.presnsenter.RegisterPresenter;
import com.zplh.yk.autoregister.utils.ViewCheckUtils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Response;

import static com.zplh.yk.autoregister.config.Constant.DEFAULT_PWD;

public class MainActivity extends BaseActivity implements RegisterCallback, ListPhonesAdapter.ClickListener {


    @BindView(R.id.tv_go_register)
    TextView tvGoRegister;
    @SuppressLint("NewApi")
    private final Deque<RegisterModule> readyTasks = new ArrayDeque<>();
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_stop_register)
    TextView tvStopRegister;
    private ExecutorService pool = Executors.newSingleThreadExecutor();
    private RegisterPresenter registerPresenter;
    private Future<?> currentFuture;
    private String imei;
    private ListPhonesAdapter listAdapter;
    List<PhoneBean> datas = new ArrayList<>();


    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initView() {
        tvGoRegister.setVisibility(View.GONE);
        listAdapter = new ListPhonesAdapter(this);
        //        list.setAdapter(listAdapter);
        listAdapter.setDatas(datas);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        try {
            Thread.sleep(2000);
            NodeUtils.clickNode("com.android.packageinstaller:id/permission_allow_button");
            TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                imei = tm.getImei();
            }
            tvGoRegister.setVisibility(View.VISIBLE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tvStopRegister.setOnClickListener(v -> {
            finish();
        });
        tvStopRegister.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        registerPresenter = new RegisterPresenter(this, this);
    }

    @OnClick(R.id.tv_go_register)
    public void onStartClick() {
        applyTask();
    }

    private void applyTask() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //        开始获取手机号

        String trim = et.getText().toString().trim();
        RegisterTask registerTask = new RegisterTask(trim, DEFAULT_PWD, "test2");
        RegisterModule registerModule = new RegisterModule(registerTask, registerPresenter);
        currentFuture = pool.submit(registerModule);
        //        OkHttpUtils.get().url(getString(R.string.host_address).concat(getString(R.string.get_phone))).build().execute(new StringCallback() {
        //            @Override
        //            public void onError(Call call, Exception e, int i) {
        //                Logger.t("获取电话号码失败").d("错误原因=" + e.getMessage());
        //            }
        //
        //            @Override
        //            public void onResponse(String s, int i) {
        //
        //                Logger.t("获取电话号码").d(s);
        //                try {
        //                    PhoneBean phoneBean = JSON.parseObject(s, PhoneBean.class);
        //                    if (phoneBean == null || phoneBean.getStatus() != 1 || TextUtils.isEmpty(phoneBean.getData().getNick())) {
        //                        Logger.t("获取电话号码失败").d("state=" + phoneBean.getInfo());
        //                        Toast.makeText(MainActivity.this, "没有电话号码了", Toast.LENGTH_SHORT).show();
        //
        //                    } else {
        //                        phoneBean.getData().setState(2);
        //                        datas.add(phoneBean);
        //                        Logger.t("电话号码").d(phoneBean.getData().getPhone());
        //                        RegisterTask registerTask = new RegisterTask(phoneBean.getData().getPhone(), DEFAULT_PWD, phoneBean.getData().getNick());
        //                        RegisterModule registerModule = new RegisterModule(registerTask, registerPresenter);
        //                        currentFuture = pool.submit(registerModule);
        //                    }
        //                } catch (IllegalStateException e) {
        //                }
        //            }
        //        });
    }


    @Override
    public void onStart(RegisterTask task) {
        Logger.t(task.getPhone()).d("开始执行注册");
        runOnUiThread(() -> listAdapter.notifyDataSetChanged());

    }

    @Override
    public void onProgress(RegisterTask task, String progressMessage) {
        Logger.t(task.getPhone()).d(progressMessage);
        //在这边可以做一下检测 比如权限弹窗
        try {
            ViewCheckUtils.check();
        } catch (Exception e) {
            e.printStackTrace();
            onError(task, "程序错误");
        }
        runOnUiThread(() -> Toast.makeText(MainActivity.this, progressMessage, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onSuccess(RegisterTask task) {
        Logger.t(task.getPhone()).d("注册成功");
        try {
            Response phone = OkHttpUtils.get().url(getString(R.string.host_address).concat(getString(R.string.confirm)))
                    .addParams("phone", task.getPhone()).addParams("imei", imei).addParams("info", "正常").build().execute();
            String string = phone.body().string();
            for (PhoneBean data : datas) {
                if (data.getData().getPhone().equals(task.getPhone())) {
                    data.getData().setState(1);
                    break;
                }
            }
            runOnUiThread(() -> {
                applyTask();
                listAdapter.notifyDataSetChanged();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onError(RegisterTask task, String errorMsg) {
        Logger.t(task.getPhone()).d("注册失败" + "  原因:" + errorMsg);
        WxAdbUtils.closeWx();
        currentFuture.cancel(true);
        while (!currentFuture.isCancelled()) {
        }

        try {
            Response phone = OkHttpUtils.get().url(getString(R.string.host_address).concat(getString(R.string.confirm)))
                    .addParams("phone", task.getPhone()).addParams("imei", imei).addParams("info", errorMsg).build().execute();
            String string = phone.body().string();
            for (PhoneBean data : datas) {
                if (data.getData().getPhone().equals(task.getPhone())) {
                    data.getData().setState(3);
                    data.setMsg(errorMsg);
                    break;
                }
            }
            runOnUiThread(() -> {
                applyTask();
                listAdapter.notifyDataSetChanged();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        runOnUiThread(this::applyTask);
    }



    @Override
    public void onClick(PhoneBean phoneBean, int position) {
        phoneBean.setStatus(2);
        RegisterTask registerTask = new RegisterTask(phoneBean.getData().getPhone(), DEFAULT_PWD, phoneBean.getData().getNick());
        RegisterModule registerModule = new RegisterModule(registerTask, registerPresenter);
        currentFuture = pool.submit(registerModule);
    }
}
