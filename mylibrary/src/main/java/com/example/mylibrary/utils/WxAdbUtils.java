package com.example.mylibrary.utils;

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


    public static boolean goRegister() throws InterruptedException {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("am start com.tencent.mm/.ui.account.RegByMobileRegAIOUI", true);
        return commandResult.result == 0;
    }
    public static void cleanData() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("pm clear com.tencent.mm", true);
    }
}
