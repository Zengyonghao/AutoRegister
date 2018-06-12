package com.example.mylibrary.utils;

import android.os.Environment;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public class FileUtils {
    /**
     * 读取sd卡 xml数据
     *
     * @param
     * @return
     */
    public static String readTxtFile() {
        String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/wx_ui.xml";
        ;
        StringBuilder builder = new StringBuilder();
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                instream.close();
            } catch (java.io.FileNotFoundException e) {
                Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("TestFile", e.getMessage());
            }
        }
        Logger.t("xml").d(builder.toString());
        return builder.toString();
    }
}
