package com.zplh.yk.autoregister.utils;

import com.example.mylibrary.utils.AdbUtils;

/**
 * 检查各种突如其来的view 在onprogress回掉 可以避免界面的弹窗
 * Created by yong hao zeng on 2018/4/24/024.
 */
public class ViewCheckUtils {
        public static void check() throws Exception {
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (AdbUtils.dumpXml2String().contains("安全警告")){
                    if (AdbUtils.dumpXml2String().contains("记住")){
                        AdbUtils.click(76,494);
                        AdbUtils.click(382,567);
                    }
                }
            }).start();

        }
}
