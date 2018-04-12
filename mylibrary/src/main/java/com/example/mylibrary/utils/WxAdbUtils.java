package com.example.mylibrary.utils;

import android.text.TextUtils;

/**
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public class WxAdbUtils {
    public static boolean openWx() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("am start com.tencent.mm/com.tencent.mm.ui.LauncherUI", true);

        return commandResult.result == 0;

    }

    public static boolean closeWx() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("am force-stop com.tencent.mm", true);
        return commandResult.result == 0;
    }


    public static boolean clickRegister() {
        String xmlData = AdbUtils.dumpXml2String();
        if (TextUtils.isEmpty(xmlData))
            return false;
        if (!xmlData.contains("注册"))
            return false;
        if (AdbUtils.click4xy(266,752,450,824)) {
            return true;
        }
        return false;
    }

    public static void cleanData() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("pm clear com.tencent.mm", true);
    }
}
