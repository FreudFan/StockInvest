package com.fmy.server.controller;

import com.alibaba.fastjson.JSONArray;
import com.fmy.server.common.EmployeeUtil;
import com.fmy.server.config.dynamicDataSource.DataSourceEntity;
import com.fmy.server.config.dynamicDataSource.dataSource.DynamicDataSource;
import com.fmy.server.dao.entity.Employee;
import com.fmy.server.service.IEmpService;
import com.fmy.server.service.IMenuService;
import com.fmy.server.service.IOrgService;
import com.fmy.server.service.ISystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private EmployeeUtil employeeUtil;
    @Autowired
    private ISystemService systemService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IOrgService positionService;
    @Autowired
    private IEmpService empService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /***
     * 用户登录
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    public String login(@RequestBody Map<String,String> param, HttpSession httpSession) throws Exception {
        Employee emp = empService.login(param);
        if ( emp != null ) {
            employeeUtil.setEmployeeSession(emp);
            emp.setPassword("");
//            logger.info(" 用户登录 用户名: " + emp.getUserName() + " 姓名: " + emp.getName() );
            return JSONArray.toJSONString(emp);
        } else {
            return JSONArray.toJSONString("error");
        }
    }

    /***
     * 替换数据源
     * @param dataSource
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changeDataSource", method = RequestMethod.POST)
    public String changeDataSource(@RequestBody Map dataSource, HttpSession httpSession) throws Exception {
        try {
            DataSourceEntity.setDynamicDataSource(dataSource);
            return "ok";
        } catch (Exception e) {
            logger.error("配置自定义数据源失败", e);
            DynamicDataSource.getInstance().deleteDataSource("dynamicDataSource-slave");
            return "error";
        }
    }

    /***
     * 测试数据源是否能正常连接
     * @param dataSource
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/testDataSource", method = RequestMethod.POST)
    public String testDataSource(@RequestBody Map dataSource, HttpSession httpSession) throws Exception {
        return DataSourceEntity.testDataSource(dataSource);
    }

    /***
     * 恢复默认数据源
     * @param dataSource
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/restoreDataSource", method = RequestMethod.POST)
    public String restoreDataSource(@RequestBody Map dataSource, HttpSession httpSession) throws Exception {
        logger.info("正在重置默认数据源");
        DynamicDataSource.getInstance().deleteDataSource("dynamicDataSource-slave");
        return "ok";
    }

    /***
     * 用户注销
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void getAllArea(HttpSession httpSession) throws Exception {
        employeeUtil.invalidEmployeeSession();
    }

}
