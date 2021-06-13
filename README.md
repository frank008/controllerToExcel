# controllerToExcel
根据自定义注解，使用在controller 方法上，注解请求参数，响应参数，自动生成 excel 请求-响应文档，注解的使用 参考 My2TestController，MyTestController类，
自动解析 entity或者 Bean 字段，可以设置 Bean 字段是否必输，mustInputsNames 属性 显示控制字段必输


 @ApiOperation(value = "1号Api用户接口")
    @PostMapping("/getUser")
    @ApiDocumentOperation(apiName = "1号Api用户接口")
    @RequestFileds(name ="requestData" ,value = {
            @RequestFiled(name = "pageSize",description = "每页条数",required = true,typeClass = Integer.class),
            @RequestFiled(name = "currentPage",referenceName = "data",description = "当前页",required = true,typeClass = String.class),
            @RequestFiled(name = "data",description = "请求对象",required = true,typeClass = YinseUser.class,mustInputsNames = {"yuName","yuPassword"})
    })
    @ResponseFields(name = "responseData",value = {
            @ResponseField(name = "email",description = "邮箱",required = true,typeClass = String.class),
            @ResponseField(name = "adrress",description = "地址",required = true,typeClass = String.class),
            @ResponseField(name = "data",description = "返回对象",required = true,typeClass = YinseUser.class)
    })


自己添加依赖  
 <!-- 使用 xls 格式 -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>5.0.0</version>
        </dependency>
        <!-- 使用 xlsx 格式 -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.0.0</version>
        </dependency>
