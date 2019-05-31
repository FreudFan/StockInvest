package com.fmy.server.config.dynamicDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.fmy.server.config.dynamicDataSource.dataSource.DynamicDataSource;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class DataSourceEntity {

    private static Logger logger = LoggerFactory.getLogger(DataSourceEntity.class);

    public static void setDynamicDataSource( Map dataSource ) throws Exception {
        String DBName = MapUtils.getString(dataSource, "DBName", "");
        String DBUrl = MapUtils.getString(dataSource, "DBUrl", "");
        String DBUser = MapUtils.getString(dataSource, "DBUser", "");
        String DBPassword = MapUtils.getString(dataSource, "DBPassword", "");

        if ( !DBName.equals("") && !DBUrl.equals("") && !DBUser.equals("") && !DBPassword.equals("") ) {
            logger.info("正在设置自定义数据源" + dataSource.toString());
            DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();

            String url = "jdbc:mysql://" + DBUrl +"/" + DBName +"?characterEncoding=UTF-8&useUnicode=true&useSSL=false&serverTimezone=Hongkong";
//        jdbc:mysql://106.14.120.166:3306/bs_support?characterEncoding=UTF-8&useUnicode=true&useSSL=false&serverTimezone=Hongkong

            DruidDataSource defaultDataSource = new DruidDataSource();
            defaultDataSource.setUrl(url);
            defaultDataSource.setUsername(DBUser);
            defaultDataSource.setPassword(DBPassword);
            defaultDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

            Map<Object,Object> map = new HashMap<>();
            map.put("dynamic-slave", defaultDataSource);
            dynamicDataSource.setTargetDataSources(map);
            dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
        }
    }

    public static String testDataSource( Map dataSource ) throws Exception {
        String DBName = MapUtils.getString(dataSource, "DBName", "");
        String DBUrl = MapUtils.getString(dataSource, "DBUrl", "");
        String DBUser = MapUtils.getString(dataSource, "DBUser", "");
        String DBPassword = MapUtils.getString(dataSource, "DBPassword", "");

        if ( !DBName.equals("") && !DBUrl.equals("") && !DBUser.equals("") && !DBPassword.equals("") ) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动类
                String url = "jdbc:mysql://" + DBUrl +"/" + DBName +"?characterEncoding=UTF-8&useUnicode=true&useSSL=false&serverTimezone=Hongkong";
                String username = DBUser;
                String password = DBPassword;
                Connection conn= DriverManager.getConnection(url,username,password);//用参数得到连接对象
                return "ok";
            } catch (Exception e) {
                logger.error("测试数据库连接失败");
                return "error";
            }
        } else {
            return "error";
        }
    }

}
