package com.zplh.yk.autoregister.utils;

import com.zplh.yk.autoregister.R;
import com.zplh.yk.autoregister.base.MyApplication;

/**
 * Created by yong hao zeng on 2018/4/11.
 */

public class NetAddressUtils {
    public static String getCodeString(){
       String hostString =  MyApplication.application.getString(R.string.host_address);
       String url = MyApplication.application.getString(R.string.get_code);
       return hostString.concat(url);
    }

}
