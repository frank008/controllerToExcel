# controllerToExcel
根据自定义注解，使用在controller 方法上，注解请求参数，响应参数，自动生成 excel 请求-响应文档，注解的使用 参考 My2TestController，MyTestController类，
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
