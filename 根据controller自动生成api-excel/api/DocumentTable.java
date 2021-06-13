package com.yinse.datacenter.serviceutils.documentUtils.api;

import java.util.List;

public class DocumentTable {

    //文档名
    String  docName="";

    //api接口列表
    List<Apitable> apitablelist;

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public List<Apitable> getApitablelist() {
        return apitablelist;
    }

    public void setApitablelist(List<Apitable> apitablelist) {
        this.apitablelist = apitablelist;
    }
}
