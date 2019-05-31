package com.fmy.server.service;

import java.util.List;
import java.util.Map;

public interface IMenuService {

    /***
     * 获取当前用户菜单列表
     * @return
     */
    public List<Map<String, Object>> getMenusByCurrentUserId();

}
