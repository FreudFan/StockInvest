package com.fmy.server.config.dynamicDataSource.dataSource;

/**
 * 通过 ThreadLocal 获取和设置线程安全的数据源 key
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static synchronized void setDBType(String dbType){
        contextHolder.set(dbType);
    }

    public static String getDBType(){
        return contextHolder.get();
    }

    public static void clearDBType(){
        contextHolder.remove();
    }

}