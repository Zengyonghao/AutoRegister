package com.example.mylibrary.utils;

import android.text.TextUtils;

/**
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public class WxAdbUtils {
    public static boolean openWx() {


        ShellUtils.CommandResult commandResult2 = ShellUtils.execCommand("am start com.tencent.mm/com.tencent.mm.ui.LauncherUI", true);

        return commandResult2.result == 0;

    }





    public static boolean closeWx() {


        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("am force-stop com.tencent.mm", true);


        return commandResult.result == 0;
    }


    public static boolean clickRegister() throws InterruptedException {


        String xmlData = AdbUtils.dumpXml2String();
        if (TextUtils.isEmpty(xmlData))
            return false;
        if (!xmlData.contains("com.tencent.mm:id/cgv")) {
            ShellUtils.CommandResult commandResult = ShellUtils.execCommand("am start com.tencent.mm/.ui.account.RegByMobileRegAIOUI", true);
        }

        Thread.sleep(2000);
        if (!AdbUtils.dumpXml2String().contains("com.tencent.mm:id/cgv"))
            return false;

        NodeUtils.clickNode("com.tencent.mm:id/cgv" );
        if (AdbUtils.dumpXml2String().contains("com.mediatek.security:id/checkbox")){
            NodeUtils.clickNode("com.mediatek.security:id/checkbox");
            NodeUtils.clickNode("android:id/button1");
        }

            return true;
    }

    public static void cleanData() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("pm clear com.tencent.mm", true);
    }
}
