package com.yinse.datacenter.serviceutils.documentUtils.api;

import java.util.HashMap;

public class Apitable {

    //文档名
    String  docName="";
    //api名称
    String  apiname="";
    //请求报文字段
    HashMap<String, Object> requestMap;
    //返回报文字段
    HashMap<String, Object> responsetMap;

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getApiname() {
        return apiname;
    }

    public void setApiname(String apiname) {
        this.apiname = apiname;
    }

    public HashMap<String, Object> getRequestMap() {
        return requestMap;
    }

    public void setRequestMap(HashMap<String, Object> requestMap) {
        this.requestMap = requestMap;
    }

    public HashMap<String, Object> getResponsetMap() {
        return responsetMap;
    }

    public void setResponsetMap(HashMap<String, Object> responsetMap) {
        this.responsetMap = responsetMap;
    }
}
