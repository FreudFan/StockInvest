package com.fmy.server.config;

import com.fmy.server.component.LoginHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@Component
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private LoginHandlerInterceptor loginHandlerInterceptor;

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login/**","/config/**","/employee/**");
    }

    // 用来允许接受CORS的跨域请求
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 限制了路径和域名的访问
        /*registry.addMapping("/api*").allowedOrigins("http://localhost:8081");*/
        registry.addMapping("/**")
                .allowedOrigins(ALL)
                .allowedMethods(ALL)
                .allowedHeaders(ALL)
                .allowCredentials(true);
    }

}
