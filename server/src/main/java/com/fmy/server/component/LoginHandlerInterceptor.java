package com.fmy.server.component;

import com.fmy.server.common.EmployeeUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 登录检查
 */
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Resource
    private EmployeeUtil employeeUtil;

    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = employeeUtil.getCurrentEmployeeId();
//        Object user = request.getSession().getAttribute("loginUser");
        if (user == null ) {
            response.sendError(401);
            return false;
        } else {
            return true;
        }
    }

}
