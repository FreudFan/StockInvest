package com.fmy.server.dao.mapper;

import com.fmy.server.dao.entity.Menu;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.annotations.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MenuMapper {

    /***
     * 通过用户id查询其主菜单
     * @param id
     * @return
     */
    @Select(" SELECT distinct(m.objid), m.url, m.path, m.component, m.name, m.iconCls, m.keepAlive, m.requireAuth, m.parentId, m.enabled" +
            " FROM MENU m, MENU_ROLE r " +
            " WHERE 1=1 AND r.roleId = #{id} AND r.roleType = 1 AND r.menuId = m.objid ")
    List<Menu> getMenusByEmpId(Integer id);

    @Select(" SELECT * FROM MENU ORDER BY objid ")
    List<Menu> getAllMenu();

    @Delete(" DELETE from MENU_ROLE WHERE 1=1 AND roleId = #{objid} AND roleType = #{type} ")
    int deleteMenuRoleByOrg( Map<String,Object> param );

    @Select(" SELECT * FROM MENU WHERE parentId = #{id} ORDER BY objid ")
    List<Menu> getChildMenu(Integer id);

    @InsertProvider(type = MenuMapperSql.class, method="saveBatchMenuRoleByOrg")
    int saveBatchMenuRoleByOrg( Map<String,Object> role, Set<Integer> menuTree );

    /***
     * 根据组织用户树查询菜单
     * @return
     */
    @SelectProvider(type = MenuMapperSql.class, method="selectMenusByOrgId")
    List<Map<String,Object>> getMenusByOrgId( Map<String,Object> param );

    class MenuMapperSql {

        public String saveBatchMenuRoleByOrg ( Map<String,Object> role, Set<Integer> menuTree ) {
            List<Integer> menus = new ArrayList<>(menuTree);
            Integer roleId = MapUtils.getInteger(role, "objid", 0);
            Integer roleType = MapUtils.getInteger(role, "type", 0);

            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO MENU_ROLE ( menuId, roleId, roleType) VALUES ");

            for ( int i = 0; i < menus.size(); i++ ) {
                int mId = menus.get(i);
                sql.append(" ( " + mId + " , " + roleId + " , " +  roleType + " ) ");

                if( i != menus.size() - 1 ){
                    sql.append(" , ");
                }else
                    sql.append(";");
            }
            return sql.toString();
        }

        public String selectMenusByOrgId( Map<String,Object> param ) {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT distinct(m.objid), m.name FROM MENU m, MENU_ROLE r WHERE 1=1 AND r.menuId = m.objid ");

            Integer roleId = MapUtils.getInteger(param, "objid", 0);
            if ( roleId != 0 ) {
                sql.append(" AND r.roleId = " + roleId + " ");
            }

            String clazzType = MapUtils.getString(param, "clazzType", "");
            if ( clazzType.equals("Employee") ) {
                sql.append(" AND r.roleType = 1 ");
            } else if ( clazzType.equals("Department") ) {
                sql.append(" AND r.roleType = 2 ");
            } else if ( clazzType.equals("Position") ) {
                sql.append(" AND r.roleType = 3 ");
            }

            return sql.toString();
        }

    }

}
