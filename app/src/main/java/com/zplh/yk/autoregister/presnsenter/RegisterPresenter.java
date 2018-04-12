package com.zplh.yk.autoregister.presnsenter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.mylibrary.utils.AdbUtils;
import com.example.mylibrary.utils.WxAdbUtils;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zplh.yk.autoregister.bean.CodeBean;
import com.zplh.yk.autoregister.bean.RegisterTask;
import com.zplh.yk.autoregister.callback.RegisterCallback;
import com.zplh.yk.autoregister.utils.NetAddressUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public class RegisterPresenter  {
    private  Activity activity;
    protected RegisterCallback registerCallback;

    public RegisterPresenter(RegisterCallback registerCallback, Activity activity) {
        this.registerCallback = registerCallback;
        this.activity = activity;
    }

    /**
     * 执行注册任务
     * @param task
     */
    public void execute(RegisterTask task){
        try {
            Thread.sleep(3000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WxAdbUtils.closeWx();//先关一下微信
        try {
            Thread.sleep(3000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WxAdbUtils.cleanData();
        try {
            Thread.sleep(2000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打开微信
        if (!WxAdbUtils.openWx()) {
            registerCallback.onError(task,"打开微信失败");
            return;
        }
        registerCallback.onProgress(task,"打开了微信");
        try {
            Thread.sleep(5000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (!WxAdbUtils.clickRegister()) {
            registerCallback.onError(task,"点击注册失败");

            return;
        }


        registerCallback.onProgress(task,"进入了注册");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //打开地区选择
        if (!AdbUtils.dumpXml2String().contains("国家/地区")) {
            registerCallback.onError(task,"未进入注册");
            return;
        }

        //进入国家地区
        AdbUtils.click4xy(402,241,450,274);
        registerCallback.onProgress(task,"进入国家地区");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打开地区选择
        if (!AdbUtils.dumpXml2String().contains("选择国家和地区代码")) {
            registerCallback.onError(task,"未进入国家地区");
            return;
        }
        //点击搜索
        AdbUtils.click4xy(408,36,480,108);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AdbUtils.putText("malai");
        registerCallback.onProgress(task,"筛选了马来西亚");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AdbUtils.click4xy(12,166,468,238);
        registerCallback.onProgress(task,"成功选择了马来西亚");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        //将文本数据复制到剪贴板
        ClipData clipData = ClipData.newPlainText(null, task.getNick());
        clipboard.setPrimaryClip(clipData);
        AdbUtils.clickLong(162,156,339,216);//长按

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AdbUtils.click(164,118);//点击粘贴

        registerCallback.onProgress(task,"输入了昵称");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //点击手机号码
        AdbUtils.click4xy(123,294,462,366);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //因为不知原因的bug 将电话号码分成两部分输入

        AdbUtils.putText(task.getPhone().substring(0,5));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AdbUtils.putText(task.getPhone().substring(5,task.getPhone().length()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        registerCallback.onProgress(task,"输入了手机号");
        AdbUtils.click4xy(162,372,450,432);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AdbUtils.putText(task.getPwd().substring(0,5));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AdbUtils.putText(task.getPwd().substring(5,task.getPwd().length()));
        registerCallback.onProgress(task,"输入了密码");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AdbUtils.click(259,460);//点击注册
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!AdbUtils.dumpXml2String().contains("确认手机号码")) {
            registerCallback.onError(task,"手机号码错误");
            return;
        }
        AdbUtils.click(356,549);//点击确定
        registerCallback.onProgress(task,"发送验证码");
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!AdbUtils.dumpXml2String().contains("填写验证码")) {
            registerCallback.onProgress(task,"未进入验证码界面,重试");

        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!AdbUtils.dumpXml2String().contains("填写验证码")) {
            registerCallback.onError(task,"未进入验证码界面");
            return;
        }

        registerCallback.onProgress(task,"进入填写验证码界面");
       //拉取验证码 每5秒拉取一次 一共拉取10次
        String code = "";
        try {
             code = getCode(task.getPhone(),0);

        } catch (Exception e) {
            e.printStackTrace();
            registerCallback.onError(task,"获取验证码错误");
            return;
        }

        if (TextUtils.isEmpty(code)){
            registerCallback.onError(task,"获取验证码失败");
            return;
        }
        registerCallback.onProgress(task,"获取了验证码");
        AdbUtils.click(266,317);//点击输入验证码
        AdbUtils.putText(code);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AdbUtils.click(262,491);//下一步

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean flag = true;
        while (flag) {
            String s = AdbUtils.dumpXml2String();
            if (!s.contains("注册中") && !s.contains("找朋友")) {
                registerCallback.onError(task,"注册失败");
                return;
            }else if (s.contains("注册中")){
                registerCallback.onProgress(task, "注册dialog");
            }else if (s.contains("找朋友")){
                registerCallback.onProgress(task, "注册成功");
                registerCallback.onSuccess(task);
                flag = false;
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private void goRegister() { //前往注册

    }

    //递归获取验证码
    private String getCode(String phone,int number) throws IOException, InterruptedException {
        number += 1;
        if (number>=10) return "";



        Response code = OkHttpUtils.get().addParams("phone", phone).url(NetAddressUtils.getCodeString())
                .build().execute();
        String string = code.body().string();
        Logger.t("验证码数据").d(string);
        if (TextUtils.isEmpty(string)){
        return  "";
        }
        CodeBean codeBean = JSON.parseObject(string,CodeBean.class);
        if (codeBean.getStatus()!=1||TextUtils.isEmpty(codeBean.getData())){
            Thread.sleep(10000);
            getCode(phone,number);
        }
        return codeBean.getData();

    }

}
