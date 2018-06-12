package com.example.mylibrary.utils;

import android.text.TextUtils;

import com.example.mylibrary.bean.NodeXmlBean;

import java.util.List;

/**
 * Created by yong hao zeng on 2018/4/28/028.
 */
public class NodeUtils {

    /**
     * 点击某个节点
     * 获取Bounds[] 坐标 完成点击
     */

    public static void clickNode(String text, String id) {
        if (TextUtils.isEmpty(text)){
            clickNode(id);
            return;
        }
        List<String> nodeList = AdbUtils.getNodeList(AdbUtils.dumpXml2String());
        for (int i = 0; i < nodeList.size(); i++) {
            NodeXmlBean.NodeBean node = AdbUtils.getNodeXmlBean(nodeList.get(i)).getNode();
            if (node != null && node.getResourceid() != null && node.getResourceid().equals(id)) {
                if (!TextUtils.isEmpty(node.getText()) && !TextUtils.isEmpty(text) && node.getText().contains(text)) {
                    List<Integer> xy = AdbUtils.getXY(node.getBounds());
                    AdbUtils.click4xy(xy.get(0), xy.get(1), xy.get(2), xy.get(3));
                    break;
                }
            }
        }
    }




    /**
     * id 点击Node
     * @param id
     */
    public static void clickNode(String id) {
        List<String> nodeList = AdbUtils.getNodeList(AdbUtils.dumpXml2String());
        for (int i = 0; i < nodeList.size(); i++) {
            NodeXmlBean.NodeBean node = AdbUtils.getNodeXmlBean(nodeList.get(i)).getNode();
            if (node != null && !TextUtils.isEmpty(node.getResourceid()) && TextUtils.equals(node.getResourceid(), id)) {
                List<Integer> xy = AdbUtils.getXY(node.getBounds());
                AdbUtils.click4xy(xy.get(0), xy.get(1), xy.get(2), xy.get(3));
                break;
            }
        }
    }


    /**
     * text 点击Node
     */
    public static void clickNode4Text(String text) {
        List<String> nodeList = AdbUtils.getNodeList(AdbUtils.dumpXml2String());
        for (int i = 0; i < nodeList.size(); i++) {
            NodeXmlBean.NodeBean node = AdbUtils.getNodeXmlBean(nodeList.get(i)).getNode();
            if (TextUtils.equals(text,node.getText())) {
                List<Integer> xy = AdbUtils.getXY(node.getBounds());
                AdbUtils.click4xy(xy.get(0), xy.get(1), xy.get(2), xy.get(3));
                break;
            }
        }
    }
    /**
     * 根据索引点击0
     */
    public static void clickIndex(String id, int dex) {
        List<String> nodeList = AdbUtils.getNodeList(AdbUtils.dumpXml2String());
        for (int i = 0; i < nodeList.size(); i++) {
            NodeXmlBean.NodeBean node = AdbUtils.getNodeXmlBean(nodeList.get(i)).getNode();
            if (node != null && node.getResourceid() != null && node.getResourceid().equals(id)) {
                if (dex == node.getIndex()) {
                    List<Integer> xy = AdbUtils.getXY(node.getBounds());
                    AdbUtils.click4xy(xy.get(0), xy.get(1), xy.get(2), xy.get(3));
                    break;
                }

            }
        }

    }

    /**
     * 根据Text点击
     */
    public static void clickText(String text, int dex) {
        List<String> nodeList = AdbUtils.getNodeList(AdbUtils.dumpXml2String());
        for (int i = 0; i < nodeList.size(); i++) {
            NodeXmlBean.NodeBean node = AdbUtils.getNodeXmlBean(nodeList.get(i)).getNode();
            if (node != null && node.getText() != null && node.getText().equals(text)) {
                if (dex == node.getIndex()) {
                    List<Integer> xy = AdbUtils.getXY(node.getBounds());
                    AdbUtils.click4xy(xy.get(0), xy.get(1), xy.get(2), xy.get(3));
                    break;
                }

            }
        }
    }

//    public static void clickLong(String text, String id) {
//        if (TextUtils.isEmpty(text)){
//            clickLong(id,4000);
//            return;
//        }
//        List<String> nodeList = AdbUtils.getNodeList(AdbUtils.dumpXml2String());
//        for (int i = 0; i < nodeList.size(); i++) {
//            NodeXmlBean.NodeBean node = AdbUtils.getNodeXmlBean(nodeList.get(i)).getNode();
//            if (node != null && node.getResourceid() != null && node.getResourceid().equals(id)) {
//                if (!TextUtils.isEmpty(node.getText()) && !TextUtils.isEmpty(text) && node.getText().contains(text)) {
//                    List<Integer> xy = AdbUtils.getXY(node.getBounds());
//                    AdbUtils.clickLong(xy.get(0), xy.get(1), xy.get(2), xy.get(3));
//                    break;
//                }
//            }
//        }
//
//    }

    public static void clickLong(String id) {
        List<String> nodeList = AdbUtils.getNodeList(AdbUtils.dumpXml2String());
        for (int i = 0; i < nodeList.size(); i++) {
            NodeXmlBean.NodeBean node = AdbUtils.getNodeXmlBean(nodeList.get(i)).getNode();
            if (node != null && node.getResourceid() != null && node.getResourceid().equals(id)) {
                    List<Integer> xy = AdbUtils.getXY(node.getBounds());
                    AdbUtils.clickLong(xy.get(0), xy.get(1), xy.get(2), xy.get(3));
                    break;
                }
            }
        }

//    public static void clickLong(String text, String id, long time) {
//    if (TextUtils.isEmpty(text)){
//        clickLong(id,time);
//        return;
//    }
//        List<String> nodeList = AdbUtils.getNodeList(AdbUtils.dumpXml2String());
//        for (int i = 0; i < nodeList.size(); i++) {
//            NodeXmlBean.NodeBean node = AdbUtils.getNodeXmlBean(nodeList.get(i)).getNode();
//            if (node != null && node.getResourceid() != null && node.getResourceid().equals(id)) {
//                if (!TextUtils.isEmpty(node.getText()) && !TextUtils.isEmpty(text) && node.getText().contains(text)) {
//                    List<Integer> xy = AdbUtils.getXY(node.getBounds());
//                    AdbUtils.clickLong(xy.get(0), xy.get(1), xy.get(2), xy.get(3),time);
//                    break;
//                }
//            }
//        }
//
//    }

    public static void clickNode4Des(String des) {
        List<String> nodeList = AdbUtils.getNodeList(AdbUtils.dumpXml2String());
        for (int i = 0; i < nodeList.size(); i++) {
            NodeXmlBean.NodeBean node = AdbUtils.getNodeXmlBean(nodeList.get(i)).getNode();
                if (!TextUtils.isEmpty(node.getContentdesc()) && !TextUtils.isEmpty(des) && node.getText().contains(des)) {
                    List<Integer> xy = AdbUtils.getXY(node.getBounds());
                    AdbUtils.click4xy(xy.get(0), xy.get(1), xy.get(2), xy.get(3));
                    break;
                }
        }
    }
}
