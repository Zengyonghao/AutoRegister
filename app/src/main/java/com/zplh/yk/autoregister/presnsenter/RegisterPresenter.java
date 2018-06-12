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
            Thread.sleep(3000 );



        if (!WxAdbUtils.goRegister()) {
            registerCallback.onError(task,"点击注册失败");
            return;
        }

        Thread.sleep(3000);




        registerCallback.onProgress(task,"进入了注册");

        String s = AdbUtils.dumpXml2String();
        if (s.contains("安全警告")) {

            NodeUtils.clickNode("com.mediatek.security:id/checkbox");


            NodeUtils.clickNode("android:id/button1");
        }



        AdbUtils.putText(task.getNick());

        registerCallback.onProgress(task,"输入了昵称");
        Thread.sleep(1000);





        //进入国家地区
        NodeUtils.clickNode("com.tencent.mm:id/vk");
        registerCallback.onProgress(task,"进入国家地区");

            Thread.sleep(3000);


        //点击搜索
        AdbUtils.click(525,87);
            Thread.sleep(1000);
        AdbUtils.putText("malaixiya");
        registerCallback.onProgress(task,"筛选了马来西亚");
            Thread.sleep(1000);

        NodeUtils.clickNode("com.tencent.mm:id/a9s");
        registerCallback.onProgress(task,"成功选择了马来西亚");

            Thread.sleep(1000);


        //点击手机号码
        NodeUtils.clickNode("com.tencent.mm:id/bun");
         //点击一下清除
        AdbUtils.click(485,339);

        //因为不知原因的bug 将电话号码分成两部分输入

        AdbUtils.putText(task.getPhone().substring(0,5));


        Thread.sleep(1000);
        AdbUtils.putText(task.getPhone().substring(5,task.getPhone().length()));
        Thread.sleep(1000);
        registerCallback.onProgress(task,"输入了手机号");

        //输入密码
        AdbUtils.click(279,406);


        AdbUtils.putText(task.getPwd().substring(0,5));

        Thread.sleep(1000);

        AdbUtils.putText(task.getPwd().substring(5,task.getPwd().length()));
        registerCallback.onProgress(task,"输入了密码");

        Thread.sleep(2000);
        NodeUtils.clickNode("com.tencent.mm:id/cbt");
        Thread.sleep(20000);


//        if (AdbUtils.dumpXml2String().contains("check")){
//            registerCallback.onError(task,"需要验证");
//            return;
//        }
            if (!AdbUtils.dumpXml2String().contains("Confirmation")) {
            registerCallback.onError(task,"手机号码错误");
            return;
        }




        NodeUtils.clickNode("com.tencent.mm:id/ad8");
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
        NodeUtils.clickNode("com.tencent.mm:id/gz");
        AdbUtils.putText(code);
            Thread.sleep(1000);
            NodeUtils.clickNode("com.tencent.mm:id/ac6");
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
