package com.fmy.server.service;

import com.fmy.server.dao.entity.Menu;
import com.fmy.server.dao.entity.Position;

import java.util.List;
import java.util.Map;

public interface IOrgService {

    /***
     * 获取所有职位
     * @return
     */
    public List<Position> getAllPositions();

    /***
     * 组织结构用户树
     * @return
     */
    public List<Map<String, Object>> getOrgTree();

    /***
     * 根据组织结构树查询目录树
     * @param param
     * @return
     */
    public List getMenuTreeByOrg( Map<String,Object> param );

    /***
     * 设置菜单权限
     * type: {0:保存, 1:继承权限(清空), 2:继承权限(保留)}
     * @param param
     * @return
     */
    public List setMenu( Map<String,Object> param );


    /**
     * 获取所有一级子菜单
     * @param menuId
     * @return
     */
    public List getChildMenu(Integer menuId);

}
