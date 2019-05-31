package com.fmy.server.common;

import com.fmy.server.dao.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MenuUtil {

    public MenuUtil() {
    }

    public List<Map<String,Object>> getMenuTree (List<Menu> menuList, List<Map<String,Object>> parentMap, Menu menu ) {
        long parentId = menu.getObjid();
        for ( Menu _menu: menuList ) {
            if ( _menu.getParentId() == parentId ) {
                if ( this.haschild(menuList, menu)) {
                    Map<String,Object> map = MapUtil.objectToMap(_menu);
                    List<Map<String,Object>> childMap = new ArrayList<>();
                    childMap = this.getMenuTree(menuList, childMap, _menu);
                    map.put("children", childMap);
                    parentMap.add(map);
                } else {
                    Map<String,Object> map = MapUtil.objectToMap(_menu);
                    parentMap.add(map);
                }
            }
        }
        return parentMap;
    }

    private boolean haschild ( List<Menu> menuList, Menu menu ) {
        for ( Menu _menu: menuList ) {
            if (_menu.getParentId().equals(menu.getObjid())) {
                return true;
            }
        }
        return false;
    }

}
