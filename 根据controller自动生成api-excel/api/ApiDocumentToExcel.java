package com.yinse.datacenter.serviceutils.documentUtils.api;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//创建Excel文件
public class ApiDocumentToExcel {
    int rowNum;
    HSSFWorkbook workbook;
    HSSFSheet sheet;

    HSSFFont fontRed; //红色字体
    HSSFFont fontBlack; //黑色字体
    HSSFFont fontHeard; //粗体

    HSSFCellStyle cellStylefontRed;
    HSSFCellStyle cellStylefontBlack;
    HSSFCellStyle cellStylefontHeard;
    HSSFCellStyle cellStylefontApiHeard;
    HSSFCellStyle cellStylefontHeardNoColor;
    HSSFCellStyle cellStylefontHttpNoColor;

    //解析整个接口文档分类
    public void parseDocumentTable(DocumentTable documentTable,HSSFWorkbook workbook){
        try {
            this.workbook=workbook;
            this.fontBlack=this.workbook.createFont();
            this.fontRed=this.workbook.createFont();
            this.fontHeard=this.workbook.createFont();
            this.cellStylefontRed=this.workbook.createCellStyle();
            this.cellStylefontBlack=this.workbook.createCellStyle();
            this.cellStylefontHeard=this.workbook.createCellStyle();
            this.cellStylefontApiHeard=this.workbook.createCellStyle();
            this.cellStylefontHeardNoColor=this.workbook.createCellStyle();
            this.cellStylefontHttpNoColor=this.workbook.createCellStyle();
            this.fontBlack.setColor(HSSFFont.COLOR_NORMAL);

            this.fontRed.setColor(HSSFFont.COLOR_RED);

            this.fontHeard.setBold(true);


            this.cellStylefontRed.setAlignment(HorizontalAlignment.LEFT); // 水平方向上向左对齐
            // 垂直方向上居中对齐
            this.cellStylefontRed.setVerticalAlignment(VerticalAlignment.CENTER);
            this.cellStylefontRed.setFont(this.fontRed); // 设置字体


            this.cellStylefontBlack.setAlignment(HorizontalAlignment.LEFT); // 水平方向上向左对齐
            // 垂直方向上居中对齐
            this.cellStylefontBlack.setVerticalAlignment(VerticalAlignment.CENTER);
            //mainType.setFillPattern(FillPatternType.NO_FILL);  //填充单元格
            this.cellStylefontBlack.setFont(this.fontBlack); // 设置字体


            this.cellStylefontHeard.setAlignment(HorizontalAlignment.LEFT); // 水平方向上向左对齐
            // 垂直方向上居中对齐
            this.cellStylefontHeard.setVerticalAlignment(VerticalAlignment.CENTER);
            this.cellStylefontHeard.setFillPattern(FillPatternType.SOLID_FOREGROUND);  //填充单元格
            this.cellStylefontHeard.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            this.cellStylefontHeard.setFont(this.fontHeard); // 设置字体

            //黑色粗体 无颜色
            this.cellStylefontHeardNoColor.setFont(this.fontHeard);


            //API接口字段样式
            this.cellStylefontApiHeard.setFont(fontHeard);
            this.cellStylefontApiHeard.setAlignment(HorizontalAlignment.LEFT); // 水平方向上向左对齐
            // 垂直方向上居中对齐
            this.cellStylefontApiHeard.setVerticalAlignment(VerticalAlignment.CENTER);
            this.cellStylefontApiHeard.setFillPattern(FillPatternType.SOLID_FOREGROUND);  //填充单元格
            this.cellStylefontApiHeard.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());

            //API接口字段样式
            this.cellStylefontHttpNoColor.setFont(fontHeard);
            this.cellStylefontHttpNoColor.setAlignment(HorizontalAlignment.LEFT); // 水平方向上向左对齐
            // 垂直方向上居中对齐
            this.cellStylefontHttpNoColor.setVerticalAlignment(VerticalAlignment.CENTER);
            this.cellStylefontHttpNoColor.setFillPattern(FillPatternType.SOLID_FOREGROUND);  //填充单元格
            this.cellStylefontHttpNoColor.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());

            //获取接口列表
            List<Apitable> apitablelist = documentTable.getApitablelist();
            //遍历解析接口
            for (int i=0;i<apitablelist.size();i++) {
                this.rowNum=0;
                System.out.println("遍历:"+i);
                Apitable apitable =apitablelist.get(i);
                System.out.println("API名称："+apitable.getApiname());
                this.sheet= this.workbook.createSheet(apitable.getApiname()); //创建一个Excel的工作表，可以指定工作表的名字
                // 创建字体，红色、粗体
                HSSFRow row =  this.sheet.createRow(this.rowNum);


                HSSFCell cell = row.createCell((short) 0);
                cell.setCellValue("接口名称");
                cell.setCellStyle(this.cellStylefontApiHeard);

                HSSFCell cell2 = row.createCell((short) 1);
                cell2.setCellStyle(this.cellStylefontApiHeard);
                cell2.setCellValue(apitable.getApiname());

                HSSFCell cell1 = row.createCell((short) 2);
                cell1.setCellValue("接口路径");
                cell1.setCellStyle(this.cellStylefontApiHeard);

                HSSFCell cell3 = row.createCell((short) 3);
                cell3.setCellStyle(this.cellStylefontApiHeard);
                cell3.setCellValue("");
                HSSFCell cell4 = row.createCell((short) 4);
                cell4.setCellStyle(this.cellStylefontApiHeard);
                cell4.setCellValue("");

                this.rowNum=this.rowNum+1;
                parseApitable(apitable,this.sheet);
                //接口之间分开三行
                this.rowNum=this.rowNum+1;

                this.sheet.autoSizeColumn((short) 0);//自动调整第1列宽
                this.sheet.autoSizeColumn((short) 1);//自动调整第2列宽
                this.sheet.autoSizeColumn((short) 2);//自动调整第3列宽
                this.sheet.autoSizeColumn((short) 3);//自动调整第4列宽
                this.sheet.autoSizeColumn((short) 4);//自动调整第5列宽
                this.sheet.autoSizeColumn((short) 5);//自动调整第6列宽
                this.sheet.autoSizeColumn((short) 6);//自动调整第7列宽
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseApitable(Apitable apitable,HSSFSheet sheet){

        HashMap<String, Object> requestMap = apitable.getRequestMap();
        for (Map.Entry<String,Object> entry:requestMap.entrySet()) {
            String key = entry.getKey();
            List<Object> datalist = (List<Object>) entry.getValue();

            HSSFRow row = sheet.createRow(this.rowNum);
            HSSFCell cell = row.createCell((short) 0);
            HSSFCell cell2 = row.createCell((short) 1);
            HSSFCell cell3 = row.createCell((short) 2);
            HSSFCell cell4 = row.createCell((short) 3);
            HSSFCell cell5 = row.createCell((short) 4);


            cell.setCellStyle(this.cellStylefontHeard);
            cell.setCellValue("请求报文");

            cell2.setCellStyle(this.cellStylefontHeard);
            cell3.setCellStyle(this.cellStylefontHeard);
            cell4.setCellStyle(this.cellStylefontHeard);
            cell5.setCellStyle(this.cellStylefontHeard);
            this.rowNum=this.rowNum+1;
            parseFileds(datalist,sheet);

        }


        HashMap<String, Object> responsetMap = apitable.getResponsetMap();
        for (Map.Entry<String,Object> entry:responsetMap.entrySet()) {
            String key = entry.getKey();
            List<Object> datalist = (List<Object>) entry.getValue();

            HSSFRow row = sheet.createRow(this.rowNum);
            HSSFCell cell = row.createCell((short) 0);
            HSSFCell cell2 = row.createCell((short) 1);
            HSSFCell cell3 = row.createCell((short) 2);
            HSSFCell cell4 = row.createCell((short) 3);
            HSSFCell cell5 = row.createCell((short) 4);


            cell.setCellStyle(this.cellStylefontHeard);
            cell.setCellValue("响应报文");

            cell2.setCellStyle(this.cellStylefontHeard);
            cell3.setCellStyle(this.cellStylefontHeard);
            cell4.setCellStyle(this.cellStylefontHeard);
            cell5.setCellStyle(this.cellStylefontHeard);
            this.rowNum=this.rowNum+1;
            parseFileds(datalist,sheet);
        }
    }

    //解析报文
    public  void parseFileds(List<Object> requestList,HSSFSheet sheet){
        HSSFRow rowHeard = sheet.createRow(this.rowNum);



        HSSFCell cell = rowHeard.createCell((short) 0);
        HSSFCell cell1= rowHeard.createCell((short) 1);
        HSSFCell cell2 = rowHeard.createCell((short) 2);
        HSSFCell cell3 = rowHeard.createCell((short) 3);
        HSSFCell cell4 = rowHeard.createCell((short) 4);
        cell.setCellValue("字段名");
        cell1.setCellValue("字段描述");
        cell2.setCellValue("引用");
        cell3.setCellValue("是否必输");
        cell4.setCellValue("字段类型");

        cell.setCellStyle(this.cellStylefontHttpNoColor);
        cell1.setCellStyle(this.cellStylefontHttpNoColor);
        cell2.setCellStyle(this.cellStylefontHttpNoColor);
        cell3.setCellStyle(this.cellStylefontHttpNoColor);
        cell4.setCellStyle(this.cellStylefontHttpNoColor);

        this.rowNum=this.rowNum+1;
        //五列数据
        for (Object dataMap : requestList) {
            Map<String,Object> mydataMap=(Map<String,Object>)dataMap;
            for (Map.Entry<String,Object> entry:mydataMap.entrySet()) {

                String key = entry.getKey();
                List<Object> collist = (List<Object>) entry.getValue();
                Object[] toArray = collist.toArray();
                //对象类型字段
                if(key.equals("recodes")){
                //对象字段
                    HSSFRow row = sheet.createRow(this.rowNum);
                    setCell(row.createCell((short) 0),this.cellStylefontHeardNoColor,"recodes");
                    setCell(row.createCell((short) 1),this.cellStylefontHeardNoColor,"对象字段");
                    setCell(row.createCell((short) 2),this.cellStylefontBlack,"");
                    setCell(row.createCell((short) 3),this.cellStylefontBlack,"");
                    setCell(row.createCell((short) 4),this.cellStylefontBlack,"");
                    this.rowNum=this.rowNum+1;

                    //遍历对象字段
                    for (Object obj : toArray) {
                       List<Object> objlist=(List<Object>)obj;
                        Object[] objects = objlist.toArray();
                        HSSFRow rowTow = sheet.createRow(this.rowNum);
                        for (int i = 0; i < objects.length; i++) {
                            String value=String.valueOf(objects[i]);
                            if(i==0){
                                setCell(rowTow.createCell((short) i),this.cellStylefontBlack,"... "+value);
                            }else {
                                if(i==3&&value.toLowerCase().contains("true")){
                                     setCell(rowTow.createCell((short) i),this.cellStylefontRed,value);
                                }else {
                                     setCell(rowTow.createCell((short) i),this.cellStylefontBlack,value);
                                }
                            }
                        }
                        //行加一
                        this.rowNum=this.rowNum+1;
                    }

                }else{
                    //普通字段
                    HSSFRow rowTow = sheet.createRow(this.rowNum);
                    for (int i = 0; i < toArray.length; i++) {
                        String value = String.valueOf(toArray[i]);
                        if(i==3&&value.toLowerCase().contains("true")){
                            setCell(rowTow.createCell((short) i),this.cellStylefontRed,value);
                        }else {
                            setCell(rowTow.createCell((short) i),this.cellStylefontBlack,value);
                        }
                    }
                }
                //行加一
                this.rowNum=this.rowNum+1;
            }
        }
    }

    public void setCell(HSSFCell cell3,HSSFCellStyle style,String value){
        cell3.setCellStyle(style);
        cell3.setCellValue(value);
    }
}
