package com.yinse.datacenter.serviceutils.documentUtils.api;


/*@根据注解 生成APi接口*/

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiControllerGeneral {


    public static void main(String[] args) throws FileNotFoundException {
        String packageName="com.yinse.datacenter.mysqldata.controller";
        new ApiControllerGeneral().parsePackageClasses(packageName);
    }
    //根据包解析所有controller类
    public void parsePackageClasses(String packageName) throws FileNotFoundException {
        File file = new File("音涩API接口.xls");
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        hssfWorkbook.createSheet("目录");
        List<String> classNames = PackageUtil.getClassName(packageName);
            for (String className : classNames) {
                if(!className.toLowerCase().contains("controller")){
                    continue;
                }
                try {
                    System.out.println(className);
                    Class<?> aclass=Class.forName(className);
                    DocumentTable documentTable = parseApis(aclass);
                    System.out.println(documentTable.getDocName());

                    ApiDocumentToExcel apiDocumentToExcel = ApiDocumentToExcel.class.newInstance();
                    apiDocumentToExcel.parseDocumentTable(documentTable,hssfWorkbook);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("找不到类："+className);
                }
            }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("写入成功："+file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //根据包解析所有controller类
    public Object parsePackageClassesTwo(String packageName) throws FileNotFoundException {
        File file = new File("音涩API接口.xls");
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

            try {
                System.out.println(packageName);
                Class<?> aclass=Class.forName(packageName);
                DocumentTable documentTable = parseApis(aclass);
                if(documentTable==null){
                    return null;
                }
                System.out.println(documentTable.getDocName());

                ApiDocumentToExcel apiDocumentToExcel = ApiDocumentToExcel.class.newInstance();
                apiDocumentToExcel.parseDocumentTable(documentTable,hssfWorkbook);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("找不到类："+packageName);
            }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("写入成功："+file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    //解析Apis
    public  static DocumentTable  parseApis(Class dataClass){
         //文档名称
        DocumentTable documentTable = new DocumentTable();
        List<Apitable> apitableList = new ArrayList<>();
        ApiDocumnet annotation1 = (ApiDocumnet) dataClass.getAnnotation(ApiDocumnet.class);

        String docName = annotation1.docName();
        try {
            //多个方法 多个接口解析
            Method[] declaredMethods = dataClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                //接口对象
                Apitable apitable = new Apitable();
                //接口名称  没有注解的接口 略过
                ApiDocumentOperation annotation2 = declaredMethod.getAnnotation(ApiDocumentOperation.class);
                if(annotation2==null){
                    continue;
                }
                String apiName = annotation2.apiName();

                //请求报文
                HashMap<String, Object> requestMap = new HashMap<>();
                //响应报文
                HashMap<String, Object> responsetMap = new HashMap<>();
                try {
                    //请求报文解析 一个接口的请求报文
                    RequestFileds annotation = declaredMethod.getAnnotation(RequestFileds.class);
                    RequestFiled[] requestFileds = annotation.value();
                    List<Object> fileList=new ArrayList<>();
                    for (RequestFiled requestFiled : requestFileds) {
                        Map<String,Object> parseFiled= parseFileds(requestFiled);
                        fileList.add(parseFiled);
                    }
                    requestMap.put("requestData",fileList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //一个接口的响应报文
                try {
                    //请求报文解析 一个接口的请求报文
                    ResponseFields annotation = declaredMethod.getAnnotation(ResponseFields.class);
                    ResponseField[] responseFileds = annotation.value();
                    List<Object> fileList=new ArrayList<>();
                    for (ResponseField responseFiled : responseFileds) {
                        Map<String, Object> parseFiled= parseFileds(responseFiled);
                        fileList.add(parseFiled);
                    }
                    responsetMap.put("responseData",fileList);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("请求对象字段："+JSON.toJSONString(requestMap));
                System.out.println("响应对象字段："+JSON.toJSONString(responsetMap));

                //接口对象设置
                apitable.setDocName(docName);
                apitable.setApiname(apiName);
                apitable.setRequestMap(requestMap);
                apitable.setResponsetMap(responsetMap);
                //添加到接口列表中
                apitableList.add(apitable);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        //接口文档设置 设置文档名称
        documentTable.setDocName(docName);
        //设置接口列表
        documentTable.setApitablelist(apitableList);
        //返回接口文档
        return documentTable;
    }
    //将一个字段变成数组
    public static Map<String,Object> parseFileds(Object filedDate){
        HashMap<String, Object> hashMap = new HashMap<>();

        if(filedDate instanceof RequestFiled){
            RequestFiled   filed=(RequestFiled)filedDate;

            //必输字段
            String[] mustInputsNames = filed.mustInputsNames();
            List<String> mustInputList = Arrays.asList(mustInputsNames);

            String name=filed.name();
            List<Object> fileList=new ArrayList<>();
            String className = filed.typeClass().getName();
            int length = className.split("\\.").length;
            String reg="string|Integer|Date|LocalDateTime";
            Pattern r = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
            Matcher matches = r.matcher(className);
            if(matches.find( )){
                //常用数据类型
                fileList.add(filed.name());
                fileList.add(filed.description());
                fileList.add(filed.referenceName());
                fileList.add(filed.required());
                fileList.add(className.split("\\.")[length-1]);
                hashMap.put(name, fileList);
            }else {
                //自定义类型 解析对象 records : {[],[],[]}
                // name  description   required   string
                hashMap= parseObject(filed.typeClass(),mustInputList);
            }
        }else {
            ResponseField   filed=(ResponseField)filedDate;
            //必输字段
            String name=filed.name();
            List<Object> fileList=new ArrayList<>();
            String className = filed.typeClass().getName();
            int length = className.split("\\.").length;
            String reg="string|Integer|Date|LocalDateTime";
            Pattern r = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
            Matcher matches = r.matcher(className);
            if(matches.find( )){
                //常用数据类型
                fileList.add(filed.name());
                fileList.add(filed.description());
                fileList.add(filed.referenceName());
                fileList.add(filed.required());
                fileList.add(className.split("\\.")[length-1]);
                hashMap.put(name, fileList);
            }else {
                //自定义类型 解析对象 records : {[],[],[]}
                // name  description   required   string
                hashMap= parseObject(filed.typeClass(),null);
            }
        }
        return hashMap;
    }
    //对象字段解析
    public  static  HashMap<String, Object> parseObject(Class dataClass,List<String> mustInputList){
        //每一个字段 是一个list
        HashMap<String,  Object> objectHash = new HashMap<>();
        List<Object> arrayList = new ArrayList<>();
        Field[] declaredFields = dataClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            ArrayList<Object> filedList = new ArrayList<>();
            try {
                declaredField.setAccessible(true);
                String name = declaredField.getName();
                String description = declaredField.getAnnotation(ApiModelProperty.class).value();
                filedList.add(name); //名称
                filedList.add(description); //描述
                filedList.add("");
                if(null!=mustInputList&&mustInputList.contains(name)){
                    filedList.add(true);
                }else {
                    filedList.add(false);
                }
                filedList.add("String");
                arrayList.add(filedList);
            } catch (Exception e) {
            }
        }
        objectHash.put("recodes",arrayList);
        return objectHash;
    }
}
