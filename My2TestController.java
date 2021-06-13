package com.yinse.datacenter.TestController;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinse.datacenter.mysqldata.controller.PermissionController;
import com.yinse.datacenter.mysqldata.entity.*;
import com.yinse.datacenter.mysqldata.service.IPermissionService;
import com.yinse.datacenter.serviceutils.documentUtils.api.*;
import com.yinse.datacenter.configcenter.entity.AthResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户接口")
@RestController
@ApiDocumnet(docName = "用户接口")

public class My2TestController {
    private final Logger logger= LoggerFactory.getLogger(My2TestController.class);

    //获取数据库字段名
    private static final HashMap<String, Object> dataFieldMap=new HashMap<>();
    static {
        Field[]fields= YinseUser.class.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            String keyName=field.getName();
            try{
                String annName=null;
                if(keyName.toLowerCase().contains("id")){
                    try{
                        annName=field.getDeclaredAnnotation(TableId.class).value();
                    }catch(Exception e){
                        annName=field.getDeclaredAnnotation(TableField.class).value();
                    }
                }else{
                    annName=field.getDeclaredAnnotation(TableField.class).value();
                }
                dataFieldMap.put(keyName,annName);
            }catch(Exception e){
            }
        }
    }

    @Autowired
    public IPermissionService iPermissionService;


    @ApiOperation(value = "1号Api用户接口")
    @PostMapping("/getUser")
    @ApiDocumentOperation(apiName = "1号Api用户接口")
    @RequestFileds(name ="requestData" ,value = {
            @RequestFiled(name = "pageSize",description = "每页条数",required = true,typeClass = Integer.class),
            @RequestFiled(name = "currentPage",referenceName = "data",description = "当前页",required = true,typeClass = String.class),
            @RequestFiled(name = "data",description = "请求对象",required = true,typeClass = YinseUser.class)
    })
    @ResponseFields(name = "responseData",value = {
            @ResponseField(name = "email",description = "邮箱",required = true,typeClass = String.class),
            @ResponseField(name = "adrress",description = "地址",required = true,typeClass = String.class),
            @ResponseField(name = "data",description = "返回对象",required = true,typeClass = YinseUser.class)
    })
    public AthResponse getName(@RequestBody Map<String, Object> reqJSON){
        try{
            HashMap<String, Object> dataJson = null;
            String pageSize="";
            String currentPage="";
            try {
                dataJson=(HashMap<String, Object>) reqJSON.get("data");
                pageSize=(String)reqJSON.get("pageSize");
                currentPage=(String)reqJSON.get("currentPage");
            }catch (Exception e){
                dataJson= (HashMap<String, Object>) reqJSON;
                pageSize=(String)dataJson.get("pageSize");
                currentPage=(String)dataJson.get("currentPage");
            }
            QueryWrapper queryWrapper=getQueryWrapper(dataJson);
            //创建翻页条件
            Page<Permission> objectPage=new Page<>(Long.parseLong(currentPage),Long.parseLong(pageSize));
            Page<Permission> page=iPermissionService.page(objectPage,queryWrapper);

            AthResponse athResponse=AthResponse.ok();
            athResponse.setMessage("查询列表成功!");
            athResponse.setPageSize(page.getSize());
            athResponse.setCurrentPage(page.getCurrent());
            athResponse.setCount(page.getTotal());
            athResponse.setData(page.getRecords());
            return athResponse;
        }catch(Exception e){
            logger.error("查询列表异常："+e.toString());
            return AthResponse.error().setMessage("查询列表失败！");
        }
    }

    @ApiOperation(value = "2号Api用户接口")
    @PostMapping("/setName")
    @ApiDocumentOperation(apiName = "2号Api用户接口")
    @RequestFileds(name ="requestData" ,value = {
            @RequestFiled(name = "pageSize",referenceName = "data",description = "每页条数",required = true,typeClass = Integer.class),
            @RequestFiled(name = "currentPage",referenceName = "data",description = "当前页",required = true,typeClass = String.class),
            @RequestFiled(name = "data",description = "请求对象",required = true,typeClass = YinseUser.class)
    })
    @ResponseFields(name = "responseData",value = {
            @ResponseField(name = "nation",description = "国籍",required = true,typeClass = String.class),
            @ResponseField(name = "faceBookName",description = "faceBook名称",required = true,typeClass = String.class),
            @ResponseField(name = "code",description = "状态码",required = true,typeClass = String.class),
            @ResponseField(name = "message",description = "错误信息",required = false,typeClass = String.class),
            @ResponseField(name = "YoutubeAccount",description = "Youtube账号",required = true,typeClass = YinseUser.class)

    })
    public Object setName(@RequestBody Map<String, Object> reqJSON){
        return  AthResponse.ok().setMessage("success");
    }


    /*根据Bean 数据表 拼接SQL查询条件*/
    public QueryWrapper getQueryWrapper(Map<String, Object> dataJson){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        for(Map.Entry<String, Object> entry:dataJson.entrySet()){
            String key=entry.getKey();
            String value=entry.getValue().toString();
            String annName=(String)dataFieldMap.get(key);
            if(annName!=null&& StringUtils.isNoneBlank(value)){
                if(key.toLowerCase().contains("id")){
                    queryWrapper.eq(annName,value);
                }else{
                    if(key.equalsIgnoreCase("yuPassword")){
                        queryWrapper.eq(annName,value);
                    }else{
                        queryWrapper.like(annName,value);
                    }
                }
            }
        }
        return queryWrapper;
    }
}
