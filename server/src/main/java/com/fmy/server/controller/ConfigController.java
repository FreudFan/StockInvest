package com.fmy.server.controller;

import com.fmy.server.common.EmployeeUtil;
import com.fmy.server.service.IEmpService;
import com.fmy.server.service.IMenuService;
import com.fmy.server.service.IOrgService;
import com.fmy.server.service.ISystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

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
     * 查询中国省市三级列表(单表查询)
     * @param httpSession
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllArea", method = RequestMethod.GET)
    public Object getAllArea(HttpSession httpSession) throws Exception {
        return systemService.getAllJsonArea();
    }

    /***
     * 获取当前用户菜单
     * @return
     */
    @RequestMapping("/getSysMenu")
    public List<Map<String, Object>> getSysMenu() {
        return menuService.getMenusByCurrentUserId();
    }

    /***
     * 获取所有职位
     * @return
     */
    @RequestMapping(value = "/getAllPosition", method = RequestMethod.GET)
    public Map<String, Object> getAllPosition() {
        Map<String, Object> map = new HashMap<>();
        map.put("positions", positionService.getAllPositions());
        return map;
    }

}
