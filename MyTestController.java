package com.yinse.datacenter.TestController;


import com.yinse.datacenter.serviceutils.documentUtils.api.*;
import com.yinse.datacenter.configcenter.entity.AthResponse;
import com.yinse.datacenter.mysqldata.entity.YinseChatroomManage;
import com.yinse.datacenter.mysqldata.entity.YinseChatroomSetting;
import com.yinse.datacenter.mysqldata.entity.YinsePhoneCode;
import com.yinse.datacenter.mysqldata.entity.YinseUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "测试接口")
@RestController
@ApiDocumnet(docName = "用户测试接口1")
public class MyTestController {

    @ApiOperation(value = "1号Api123123")
    @PostMapping("/geeetName")
    @ApiDocumentOperation(apiName = "1号Api")
    @RequestFileds(name ="requestData" ,value = {
            @RequestFiled(name = "pageSize",referenceName = "data",description = "每页条数",required = true,typeClass = Integer.class),
            @RequestFiled(name = "currentPage",referenceName = "data",description = "当前页",required = true,typeClass = String.class),
            @RequestFiled(name = "data",description = "请求对象",required = true,typeClass = YinseUser.class,mustInputsNames = {"yuIdCard","yuPassword","yuName"})
    })
    @ResponseFields(name = "responseData",value = {
            @ResponseField(name = "email",description = "邮箱",required = true,typeClass = String.class),
            @ResponseField(name = "adrress",description = "地址",required = true,typeClass = String.class),
            @ResponseField(name = "data",description = "返回对象",required = true,typeClass = YinseChatroomManage.class)
    })
    public Object getName(@RequestBody Map<String, Object> reqJSON){
        return  AthResponse.ok().setMessage("success");
    }

    @ApiOperation(value = "2号Api2342343")
    @PostMapping("/geeetaName")
    @ApiDocumentOperation(apiName = "2号Api")
    @RequestFileds(name ="requestData" ,value = {
            @RequestFiled(name = "pageSize",referenceName = "data",description = "每页条数",required = true,typeClass = Integer.class),
            @RequestFiled(name = "currentPage",referenceName = "data",description = "当前页",required = true,typeClass = String.class),
            @RequestFiled(name = "data",description = "请求对象",required = true,typeClass = YinsePhoneCode.class,mustInputsNames = {"ypcBussinesName","ypcPhone","ypcPhoneCode"})
    })
    @ResponseFields(name = "responseData",value = {
            @ResponseField(name = "nation",description = "国籍",required = true,typeClass = String.class),
            @ResponseField(name = "faceBookName",description = "faceBook名称",required = true,typeClass = String.class),
            @ResponseField(name = "YoutubeAccount",description = "Youtube账号",required = true,typeClass = YinseChatroomSetting.class)
    })
    public Object setName(@RequestBody Map<String, Object> reqJSON){
        return  AthResponse.ok().setMessage("success");
    }
}
