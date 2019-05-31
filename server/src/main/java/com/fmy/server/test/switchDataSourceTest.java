package com.fmy.server.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.fmy.server.config.dynamicDataSource.dataSource.DataSourceContextHolder;
import com.fmy.server.config.dynamicDataSource.dataSource.DynamicDataSource;
import com.fmy.server.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class switchDataSourceTest {

    @Autowired
    private IEmpService empService;

    private void test() throws Exception {
        //测试原有数据源
        DataSourceContextHolder.setDBType("default");
        List<Map<String, Object>> userInfoList = empService.getEmployeeInfoByPage(new HashMap());
        userInfoList.stream().forEach(userInfo -> System.out.println("name is : " + userInfo.get("name") + " "));

        //创建新数据源
        DruidDataSource dynamicDataSource = new DruidDataSource();
        dynamicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dynamicDataSource.setUrl("jdbc:mysql://106.14.120.166:3306/bs_support?characterEncoding=UTF-8&useUnicode=true&useSSL=false&serverTimezone=Hongkong");
        dynamicDataSource.setUsername("root");
        dynamicDataSource.setPassword("d123456");

        //创建动态数据源
        Map<Object, Object> dataSourceMap = DynamicDataSource.getInstance().getDataSourceMap();
        dataSourceMap.put("dynamic-slave", dynamicDataSource);
        DynamicDataSource.getInstance().setTargetDataSources(dataSourceMap);

        //测试新数据源
        System.out.println("开始切换数据源");
        DataSourceContextHolder.setDBType("dynamicDataSource-slave");
        List<Map<String, Object>> userList = empService.getEmployeeInfoByPage(new HashMap());
        userList.stream().forEach(userInfo -> System.out.println("name is : " + userInfo.get("name") + " "));
    }
}
