package com.fmy.server.controller;

import com.fmy.server.dao.entity.Menu;
import com.fmy.server.service.IOrgService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    private IOrgService orgService;

    /***
     * 组织结构用户树
     * @return
     */
    @RequestMapping(value = "/getEmpByUserName", method = RequestMethod.POST)
    public List<Map<String, Object>> getAllOrgAndEmployee() {
        return orgService.getOrgTree();
    }


    @RequestMapping(value = "/getMenuByOrg", method = RequestMethod.POST)
    public List getMenuByOrg(@RequestBody Map<String,Object> param, HttpSession httpSession) throws Exception {
        return orgService.getMenuTreeByOrg(param);
    }

    @RequestMapping(value = "/setMenu", method = RequestMethod.POST)
    public List setMenu(@RequestBody Map<String,Object> param, HttpSession httpSession) throws Exception {
        return orgService.setMenu(param);
    }

    @RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    public List getMenu(@RequestBody Map<String,Object> param, HttpSession httpSession) throws Exception {
        return orgService.getMenuTreeByOrg(param);
    }

    @RequestMapping(value = "/getChildMenu", method = RequestMethod.POST)
    public List getChildMenu(@RequestBody Integer menuId, HttpSession httpSession) throws Exception {
        return orgService.getChildMenu(menuId);
    }

}
