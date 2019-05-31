package com.fmy.server.service.impl;

import com.fmy.server.common.EmployeeUtil;
import com.fmy.server.common.MapUtil;
import com.fmy.server.common.MenuUtil;
import com.fmy.server.dao.entity.Employee;
import com.fmy.server.dao.entity.Menu;
import com.fmy.server.dao.mapper.MenuMapper;
import com.fmy.server.service.IMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuServiceImpl implements IMenuService {

    @Resource
    MenuMapper menuMapper;
    @Resource
    private EmployeeUtil employeeUtil;
    @Resource
    private MenuUtil menuUtil;

    @Override
    public List<Map<String, Object>> getMenusByCurrentUserId() {
        Employee currentEmp = employeeUtil.getCurrentEmployee();
        if ( currentEmp == null ) {
            return null;
        } else {
            List<Menu> menuList = menuMapper.getMenusByEmpId(currentEmp.getObjid());
            return this.praseMenuTree(menuList);
//            return menuMapper.getMenusByEmpId(currentEmp.getObjid());
        }
    }

    /***
     * 初始化路由菜单
     * @param menuList
     * @return
     */
    private List<Map<String,Object>> praseMenuTree(List<Menu> menuList) {
        List<Map<String,Object>> mapList = new ArrayList<>();
        for ( Menu menu: menuList ) {
            if ( menu.getParentId() == 1 ) {
                Map<String,Object> map = MapUtil.objectToMap(menu);
                List<Map<String,Object>> childMap = new ArrayList<>();
                childMap = menuUtil.getMenuTree(menuList, childMap, menu);
                map.put("children", childMap);
                mapList.add(map);
            }
        }
        return mapList;
    }


}
