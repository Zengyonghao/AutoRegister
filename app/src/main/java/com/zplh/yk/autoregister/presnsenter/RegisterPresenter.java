package com.zplh.yk.autoregister.presnsenter;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.mylibrary.utils.AdbUtils;
import com.example.mylibrary.utils.NodeUtils;
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

    public RegisterCallback getRegisterCallback() {
        return registerCallback;
    }

    /**
     * 执行注册任务
     * @param task
     */
    public void execute(RegisterTask task) throws Exception{


        //允许读取联系人
        WxAdbUtils.closeWx();//先关一下微信
        WxAdbUtils.cleanData();
            Thread.sleep(2000 );
        //打开微信
        if (!WxAdbUtils.openWx()) {
            registerCallback.onError(task,"打开微信失败");
            return;
        }
        registerCallback.onProgress(task,"打开了微信");
            Thread.sleep(5000 );


        if (!WxAdbUtils.clickRegister()) {
            registerCallback.onError(task,"点击注册失败");
            return;
        }


        registerCallback.onProgress(task,"进入了注册");
            Thread.sleep(5000);


        //打开地区选择
        if (!AdbUtils.dumpXml2String().contains("com.tencent.mm:id/qa")) {
            registerCallback.onError(task,"未进入注册");
            return;
        }

        //进入国家地区
        NodeUtils.clickNode("com.tencent.mm:id/qa");
        registerCallback.onProgress(task,"进入国家地区");

            Thread.sleep(5000);
        //打开地区选择
        if (!AdbUtils.dumpXml2String().contains("com.tencent.mm:id/at")) {
            registerCallback.onError(task,"未进入国家地区");
            return;
        }
        //点击搜索
        NodeUtils.clickNode("com.tencent.mm:id/at");
            Thread.sleep(3000);
        AdbUtils.putText("Malaysia");
        registerCallback.onProgress(task,"筛选了马来西亚");
            Thread.sleep(5000);

        AdbUtils.click(356,215);
        registerCallback.onProgress(task,"成功选择了马来西亚");

            Thread.sleep(3000);


        AdbUtils.click(233,194);
        Thread.sleep(1000);
        AdbUtils.putText(task.getNick());

        registerCallback.onProgress(task,"输入了昵称");
            Thread.sleep(3000);

        //点击手机号码
        AdbUtils.click(306,294);
            Thread.sleep(1000);

        //因为不知原因的bug 将电话号码分成两部分输入

        AdbUtils.putText(task.getPhone().substring(0,5));


        Thread.sleep(1000);
        AdbUtils.putText(task.getPhone().substring(5,task.getPhone().length()));
        Thread.sleep(1000);
        registerCallback.onProgress(task,"输入了手机号");

        AdbUtils.click(301,368);

        Thread.sleep(1000);

        AdbUtils.putText(task.getPwd().substring(0,5));

        Thread.sleep(1000);

        AdbUtils.putText(task.getPwd().substring(5,task.getPwd().length()));
        registerCallback.onProgress(task,"输入了密码");
        AdbUtils.back();

        Thread.sleep(1000);
        NodeUtils.clickNode("com.tencent.mm:id/cbt");
        Thread.sleep(20000);



            if (!AdbUtils.dumpXml2String().contains("Confirmation")) {
            registerCallback.onError(task,"手机号码错误");
            return;
        }

        if (AdbUtils.dumpXml2String().contains("check")){
                registerCallback.onError(task,"需要验证");
            return;
            }



        AdbUtils.click(356,549);//点击确定
        registerCallback.onProgress(task,"发送验证码");
        Thread.sleep(40000);
        if (AdbUtils.dumpXml2String().contains("later")){
            registerCallback.onError(task,"频繁操作 稍后重试");
            return;
        }



            if (!AdbUtils.dumpXml2String().contains("Verification Code")) {
            registerCallback.onProgress(task,"未进入验证码界面,重试");
            Thread.sleep(10000);
        }
        if (!AdbUtils.dumpXml2String().contains("Verification Code")) {
            registerCallback.onError(task,"未进入验证码界面");
            return;
        }

        registerCallback.onProgress(task,"进入填写验证码界面");
       //拉取验证码 每5秒拉取一次 一共拉取10次
        String code = "";
             code = getCode(task.getPhone(),0);
        if (TextUtils.isEmpty(code)){
            registerCallback.onError(task,"获取验证码失败");
            return;
        }
        registerCallback.onProgress(task,"获取了验证码");
        AdbUtils.click(266,317);//点击输入验证码
        AdbUtils.putText(code);
            Thread.sleep(1000);
        AdbUtils.click(262,491);//下一步

            Thread.sleep(15000);






        //号如果已经注册过 欢迎回来
        if (AdbUtils.dumpXml2String().contains("Yes, log in now")) {
            registerCallback.onError(task,"手机号被注册过");
            return;
        }

      if (AdbUtils.dumpXml2String().contains("Friend")){
                registerCallback.onProgress(task, "注册成功");
                registerCallback.onSuccess(task);
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
