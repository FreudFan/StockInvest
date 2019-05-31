package com.fmy.server.controller;

import com.alibaba.fastjson.JSONArray;
import com.fmy.server.common.MapUtil;
import com.fmy.server.dao.entity.Employee;
import com.fmy.server.service.IEmpService;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/employee")
public class EmpController {

    @Autowired
    private IEmpService empService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /***
     * 用户注册
     * @param employee
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody Employee employee, HttpSession httpSession) throws Exception {
        if ( employee.getObjid() == null ) {
            logger.info(" 新用户注册 用户名: " + employee.getUserName() + " 姓名: " + employee.getName() );
        }
        String msg = empService.save(employee);
        return JSONArray.toJSONString(msg);
    }

    /***
     * 页面查询用户
     * @param param
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getEmp", method = RequestMethod.GET)
    public Map<String, Object> getEmployeeByPage(@RequestParam Map<String,String> param, HttpSession httpSession) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<Map<String,Object>> employeeList = empService.getEmployeeInfoByPage(param);

        long totalCount = empService.getEmpCountByKeywords(param);
        map.put("Employee", employeeList);
        map.put("totalCount", totalCount);
        return map;
    }

    /***
     * 根据用户名获取用户信息
     * @param param
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getEmpByUserName", method = RequestMethod.GET)
    public Map<String,Object> getEmployeeByPageUserName(@RequestParam Map<String,String> param, HttpSession httpSession) throws Exception {
        return MapUtil.objectToMap(empService.getEmpByUserName(param));
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public Map<String,Object> resetPasswordByPageUserName(@RequestParam Map<String,String> param, HttpSession httpSession) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String defaultPassword = empService.resetPasswordByUserName(param);
        map.put("pass", defaultPassword);
        return map;
    }

    /***
     * 实时模糊搜索用户姓名
     * @param param
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/querySearchEmp", method = RequestMethod.POST)
    public Map<String,Object> querySearchEmpByPage(@RequestBody Map<String,String> param, HttpSession httpSession) throws Exception {
        String value = MapUtils.getString(param, "queryName", "");
        if ( value != null ) {
            Map<String, Object> map = new HashMap<>();
            List<Map<String,Object>> emps = empService.querySearchEmp(param);
            map.put("emps", emps);
            return map;
        } else {
            return null;
        }

    }

}
