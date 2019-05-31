package com.fmy.server.config.dynamicDataSource.config;

import com.fmy.server.config.dynamicDataSource.dataSource.DataSourceContextHolder;
import com.fmy.server.config.dynamicDataSource.dataSource.DynamicDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Aspect
@Configuration
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Pointcut("execution(* com.fmy.server.controller..*.*(..))")
    public void daoAspect() {
    }

    @Before("daoAspect()")
    public void switchDataSource(JoinPoint point) {
        Map dataSources = DynamicDataSource.getInstance().getDataSourceMap();
        if ( dataSources.get("dynamicDataSource-slave") != null ) {
            DataSourceContextHolder.setDBType("dynamicDataSource-slave");
        }
    }

}
