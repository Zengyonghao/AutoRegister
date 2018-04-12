package com.zplh.yk.autoregister.bean;

/**
 * 注册数据bean
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public class RegisterTask  {
    public static final int STATE_SUCCESS = 0;//状态正常;
    public static final int STATE_UN_REGISTER_TIME = 1;//无法注册 一个月之类无法注册

    String phone;//手机号
    String pwd;//密码
    String nick;//昵称
    String isRegister;//是否注册成功
    String state;//账号状态

    public RegisterTask(String phone, String pwd, String nick) {
        this.phone = phone;
        this.pwd = pwd;
        this.nick = nick;
    }

    public static int getStateSuccess() {
        return STATE_SUCCESS;
    }

    public static int getStateUnRegisterTime() {
        return STATE_UN_REGISTER_TIME;
    }

    public String getPhone() {
        return phone;
    }

    public RegisterTask setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPwd() {
        return pwd;
    }

    public RegisterTask setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public String getNick() {
        return nick;
    }

    public RegisterTask setNick(String nick) {
        this.nick = nick;
        return this;
    }

    public String getIsRegister() {
        return isRegister;
    }

    public RegisterTask setIsRegister(String isRegister) {
        this.isRegister = isRegister;
        return this;
    }

    public String getState() {
        return state;
    }

    public RegisterTask setState(String state) {
        this.state = state;
        return this;
    }
}
