package com.zplh.yk.autoregister.bean;

import com.example.mylibrary.utils.DecoedUtils;

/**
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public class PhoneBean {

    /**
     * status : 1
     * info : success
     * data : {"phone":"1112909013","nick":"test_nick"}
     */

    private int status;
    private String info;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * phone : 1112909013
         * nick : test_nick
         */

        private String phone;
        private String nick;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNick() {
            return DecoedUtils.decode(nick);
        }

        public void setNick(String nick) {
            this.nick = nick;
        }
    }
}
