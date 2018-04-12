package com.example.mylibrary.bean;


import com.google.gson.annotations.SerializedName;

/**
 * Created by lichun on 2017/6/15.
 * Description:
 */

public class NodeXmlBean {

    @Override
    public String toString() {
        return "NodeXmlBean{" +
                "node=" + node +
                '}';
    }

    /**
     * node : {"password":false,"checkable":false,"long-clickable":false,"selected":false,"scrollable":false,"enabled":true,"clickable":false,"checked":false,"focused":false,"class":"android.widget.TextView","resource-id":"com.tencent.mm:id/bq0","package":"com.tencent.mm","text":"我","bounds":"[412,829][427,851]","index":1,"focusable":false}
     */

    private NodeBean node;

    public NodeBean getNode() {
        return node;
    }

    public void setNode(NodeBean node) {
        this.node = node;
    }

    public static class NodeBean {

        /**
         * password : false
         * checkable : false
         * long-clickable : false
         * selected : false
         * scrollable : false
         * enabled : true
         * clickable : false
         * checked : false
         * focused : false
         * class : android.widget.TextView
         * resource-id : com.tencent.mm:id/bq0
         * package : com.tencent.mm
         * text : 我
         * bounds : [412,829][427,851]
         * index : 1
         * focusable : false
         */

        private boolean password;
        private boolean checkable;
        private boolean longclickable;
        private boolean selected;
        private boolean scrollable;
        private boolean enabled;
        private boolean clickable;
        private boolean checked;
        private boolean focused;
        private String classX;
        @SerializedName("resource-id")
        private String resourceid;
        @SerializedName("package")
        private String packageX;
        private String text;
        private String bounds;
        private int index;
        private boolean focusable;
        @SerializedName("content-desc")
        private String contentdesc;

        public String getContentdesc() {
            return contentdesc;
        }

        public void setContentdesc(String contentdesc) {
            this.contentdesc = contentdesc;
        }

        public boolean isPassword() {
            return password;
        }

        public void setPassword(boolean password) {
            this.password = password;
        }

        public boolean isCheckable() {
            return checkable;
        }

        public void setCheckable(boolean checkable) {
            this.checkable = checkable;
        }

        public boolean isLongclickable() {
            return longclickable;
        }

        public void setLongclickable(boolean longclickable) {
            this.longclickable = longclickable;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isScrollable() {
            return scrollable;
        }

        public void setScrollable(boolean scrollable) {
            this.scrollable = scrollable;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isClickable() {
            return clickable;
        }

        public void setClickable(boolean clickable) {
            this.clickable = clickable;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean isFocused() {
            return focused;
        }

        public void setFocused(boolean focused) {
            this.focused = focused;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        public String getResourceid() {
            return resourceid;
        }

        public void setResourceid(String resourceid) {
            this.resourceid = resourceid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getBounds() {
            return bounds;
        }

        public void setBounds(String bounds) {
            this.bounds = bounds;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public String toString() {
            return "NodeBean{" +
                    "password=" + password +
                    ", checkable=" + checkable +
                    ", longclickable=" + longclickable +
                    ", selected=" + selected +
                    ", scrollable=" + scrollable +
                    ", enabled=" + enabled +
                    ", clickable=" + clickable +
                    ", checked=" + checked +
                    ", focused=" + focused +
                    ", classX='" + classX + '\'' +
                    ", resourceid='" + resourceid + '\'' +
                    ", packageX='" + packageX + '\'' +
                    ", text='" + text + '\'' +
                    ", bounds='" + bounds + '\'' +
                    ", index=" + index +
                    ", focusable=" + focusable +
                    ", contentdesc='" + contentdesc + '\'' +
                    '}';
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public boolean isFocusable() {
            return focusable;
        }

        public void setFocusable(boolean focusable) {
            this.focusable = focusable;
        }
    }
}
