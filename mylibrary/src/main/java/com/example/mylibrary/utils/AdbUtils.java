package com.example.mylibrary.utils;


import com.example.mylibrary.XmlToJson.XmlToJson;
import com.example.mylibrary.bean.NodeXmlBean;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *   adb 执行工具
 * Created by yong hao zeng on 2018/4/10 0010.
 */

public class AdbUtils {
    private static List<String> commnandList;
    //单击某按钮 根据坐标
    public static boolean click4xy(int a, int b, int c, int d){
        if (commnandList != null) {
            commnandList.clear();
        } else {
            commnandList = new ArrayList<>();
        }
        commnandList.add("input tap " + (a + c) / 2 + " " + (b + d) / 2);
        ShellUtils.CommandResult result = ShellUtils.execCommand(commnandList, true);
        Logger.t("adb click").d("x"+(a + c) / 2+"y"+(b + d) / 2);
        return result.result == 0;
    }
    public static String dumpXml2String(){

        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("uiautomator dump /sdcard/wx_ui.xml", true);
        if (commandResult.result!=0) return "";
        return FileUtils.readTxtFile();
    }


    /**
     * 从字符串中提取数字
     *
     * @param s
     * @return
     */
    public static List<Integer> getXY(String s) {

            ArrayList<Integer> listXY = new ArrayList<>();

        for (String sss : s.replaceAll("[^0-9]", ",").split(",")) {
            if (sss.length() > 0)
                try {
                    Integer a = Integer.parseInt(sss);
                    listXY.add(a);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

        }
        return listXY;
    }

    /**
     * xml转json
     *
     * @param xml
     * @return
     */
    public static String xml2JSON(String xml) {
        try {
            XmlToJson xmlToJson = new XmlToJson.Builder(xml).build();

            String newJson = xmlToJson.toJson().toString().replaceAll("\"node\":\\[", "\"node_list\":[");
            return newJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static NodeXmlBean getNodeXmlBean(String str){
        Gson gson = new Gson();
        return gson.fromJson(xml2JSON(str), NodeXmlBean.class);
    }

    //返回node节点数据
    public static List<String> getNodeList(String node){
        List<String> ls=new ArrayList<String>();
        ls.clear();
        Pattern pattern = Pattern.compile("<node.*?text=\"(.*?)\".*?resource-id=\"(.*?)\" class=\"(.*?)\" package=\"(.*?)\".*?content-desc=\"(.*?)\".*?checked=\"(.*?)\".*?enabled=\"(.*?)\".*?selected=\"(.*?)\".*?bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"");
        Matcher matcher = pattern.matcher(node);
        while(matcher.find()){
            ls.add(matcher.group()+"/>");
        }
        return  ls;
    }

    public static void putText(String str) {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("input text "+str, true);


    }

    //返回键
    public static void back() {
        ShellUtils.execCommand("input keyevent 4", true);
    }

    public static boolean boardisShow() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("\"dumpsys input_method |grep mInputShown=true\"", true);
        return commandResult.result == 0;
    }

    public static void clickLong(int a, int b,int c,int d) {
        if (commnandList != null) {
            commnandList.clear();
        } else {
            commnandList = new ArrayList<>();
        }

        commnandList.add("input swipe " + (a + c) / 2 + " " + (b + d) / 2+" "+(a + c) / 2 + " " + (b + d) / 2+" "+4000);
        ShellUtils.CommandResult result = ShellUtils.execCommand(commnandList, true);
        Logger.t("adb click").d("长按");

    }

    public static void click(int i, int i1) {
        if (commnandList != null) {
            commnandList.clear();
        } else {
            commnandList = new ArrayList<>();
        }
        commnandList.add("input tap " + i + " " + i1);
        ShellUtils.CommandResult result = ShellUtils.execCommand(commnandList, true);
    }
}
