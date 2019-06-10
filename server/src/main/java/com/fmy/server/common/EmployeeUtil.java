package com.fmy.server.common;

import com.fmy.server.dao.entity.Employee;
import com.fmy.server.dao.mapper.EmployeeMapper;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public class EmployeeUtil {

    @Resource
    private HttpSession session;
    @Resource
    private EmployeeMapper employeeMapper;
    @Resource
    private HttpServletRequest request;

    /***
     * 通过session获取当前用户
     * @return
     */
    public Employee getCurrentEmployee() {
        Integer empId = (Integer)session.getAttribute("loginUser");
        return employeeMapper.getEmployeeById(empId);
    }

    /***
     * 设置用户session
     * @param employee
     * @return
     */
    public int setEmployeeSession( Employee employee ) {
        session.setAttribute("loginUser", employee.getObjid());
        return employee.getObjid();
    }

    /***
     * 得到当前用户id
     * @return
     */
    public Object getCurrentEmployeeId() {
        request.getSession().getAttribute("loginUser");
        Object empId = session.getAttribute("loginUser");
        return empId;
    }

    /***
     * 清除当前用户session
     */
    public void invalidEmployeeSession() {
        try {
            session.removeAttribute("loginUser");
        } catch (Exception ignored) {

        }
    }

    public void invalidAllSession() {
        session.invalidate();
    }

    /***
     * 转换map的性别
     * @param map
     * @return
     */
    public static Map<String,Object> getGenderByMap( Map map ) {
        int gender = MapUtils.getIntValue(map, "gender", 0);
        if ( gender == 0 ) {
            map.put("gender", "男");
        } else {
            map.put("gender", "女");
        }
        return map;
    }

}
