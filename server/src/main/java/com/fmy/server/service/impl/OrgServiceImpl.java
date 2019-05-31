package com.fmy.server.service.impl;

import com.fmy.server.common.EmployeeUtil;
import com.fmy.server.common.MapUtil;
import com.fmy.server.common.MenuUtil;
import com.fmy.server.dao.entity.Department;
import com.fmy.server.dao.entity.Employee;
import com.fmy.server.dao.entity.Menu;
import com.fmy.server.dao.entity.Position;
import com.fmy.server.dao.mapper.DepartmentMapper;
import com.fmy.server.dao.mapper.EmployeeMapper;
import com.fmy.server.dao.mapper.MenuMapper;
import com.fmy.server.dao.mapper.PositionMapper;
import com.fmy.server.service.IOrgService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class OrgServiceImpl implements IOrgService {

    @Resource
    private PositionMapper positionMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private EmployeeMapper employeeMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private MenuServiceImpl menuService;
    @Resource
    private MenuUtil menuUtil;

    private List<Position> positionList = new ArrayList<>();
    private List<Department> departmentList = new ArrayList<>();
    private List<Employee> employeeList = new ArrayList<>();

    private void initGlobalValue() {
        this.positionList = positionMapper.getAllPos();
        this.departmentList = departmentMapper.getAllDepartment();
        this.employeeList = employeeMapper.getAllEmployee();
    }

    @Override
    public List<Position> getAllPositions() {
        return positionMapper.getAllPos();
    }

    @Override
    public List<Map<String, Object>> getOrgTree() {
        this.initGlobalValue();
        List<Map<String,Object>> orgTree = this.praseDepartmentTree();
        this.addIcon(orgTree);
        return orgTree;
    }

    /***
     * 得到部门树
     * @return
     */
    private List<Map<String,Object>> praseDepartmentTree () {
        List<Map<String,Object>> mapList = new ArrayList<>();
        for ( Department department: this.departmentList ) {
            if ( department.getParentId() == 0 ) {
                Map<String,Object> map = MapUtil.objectToMap(department);
                List<Map<String,Object>> childMap = new ArrayList<>();
                this.getDeptTree(childMap, department);
                childMap.addAll(this.getPositionByDept(map));
                this.addIcon(childMap);
                map.put("children", childMap);
                mapList.add(map);
            }
        }
        return mapList;
    }

    /***
     * 得到部门、职位、人员树
     * @param parentMap
     * @param department
     * @return
     */
    private List<Map<String,Object>> getDeptTree ( List<Map<String,Object>> parentMap, Department department ) {
        long parentId = department.getObjid();
        for ( Department _dept: this.departmentList ) {
            if ( _dept.getParentId() == parentId ) {
                if ( this.haschild( _dept )) {
                    Map<String,Object> map = MapUtil.objectToMap(_dept);
                    List<Map<String,Object>> childMap = new ArrayList<>();
                    this.getDeptTree(childMap, _dept);
                    childMap.addAll(this.getPositionByDept(map));
                    this.addIcon(childMap);
                    map.put("children", childMap);
                    parentMap.add(map);
                } else {
                    Map<String,Object> map = MapUtil.objectToMap(_dept);
                    map.put("children", this.getPositionByDept(map));
                    parentMap.add(map);
                }
            }
        }
        return parentMap;
    }

    private boolean haschild ( Department department ) {
        for ( Department _dept: this.departmentList ) {
            if ( _dept.getParentId() == department.getObjid() ) {
                return true;
            }
        }
        return false;
    }

    private List<Map<String,Object>> getPositionByDept( Map<String,Object> department ) {
        List<Map<String,Object>> positionsList = new ArrayList<>();
        int deptId = MapUtils.getInteger(department, "objid", 0);
        for ( Position position: this.positionList ) {
            if ( position.getDeptId() == deptId ) {
                Map<String,Object> pos = MapUtil.objectToMap(position);
                pos.put("children", this.getEmployeeByDept(pos));
                positionsList.add(pos);
            }
        }
        this.addIcon(positionsList);
        return positionsList;
    }

    private List<Map<String,Object>> getEmployeeByDept( Map<String,Object> position ) {
        List<Map<String,Object>> employeesList = new ArrayList<>();
        int posId = MapUtils.getInteger(position, "objid", 0);
        for ( Employee employee: employeeList ) {
            if ( employee.getPosId() == posId ) {
                Map<String,Object> emp = MapUtil.objectToMap(employee);
                employeesList.add(emp);
            }
        }
        this.addIcon(employeesList);
        return employeesList;
    }

    private List<Map<String,Object>> addIcon(List<Map<String,Object>> mapList ) {
        for ( Map<String,Object> _map: mapList ) {
            String clazzType = MapUtils.getString(_map, "clazzType", "");
            if ( clazzType.equals("Department") ) {
                _map.put("icon", "iconfont icon-bumen");
                int parentId = MapUtils.getInteger(_map, "parentId", 0);
                if ( parentId == 0 ) {
                    _map.put("icon", "iconfont icon-gongsimingcheng");
                }
            } else if ( clazzType.equals("Employee") ) {
                _map.put("icon", "iconfont icon-geren");
            } else if ( clazzType.equals("Position") ) {
                _map.put("icon", "iconfont icon-gangweitubiao");
            }
        }
        return mapList;
    }

    @Override
    public List getMenuTreeByOrg(Map<String, Object> param) {
        int objid = MapUtils.getInteger(param, "objid", 0);
        if ( MapUtils.isEmpty(param) ) {
            List<Menu> menuList = menuMapper.getAllMenu();
            return this.praseOrgMenuTree(menuList);
        } else {
            List<Map<String,Object>> menuList = menuMapper.getMenusByOrgId(param);
            return this.returnAllChildNode(menuList);
        }
    }

    /***
     * 初始化菜单设置、菜单权限设置菜单树
     * @param menuList
     * @return
     */
    private List<Map<String,Object>> praseOrgMenuTree(List<Menu> menuList) {
        List<Map<String,Object>> mapList = new ArrayList<>();
        for ( Menu menu: menuList ) {
            if ( menu.getParentId() == 0 ) {
                Map<String,Object> map = MapUtil.objectToMap(menu);
                List<Map<String,Object>> childMap = new ArrayList<>();
                childMap = menuUtil.getMenuTree(menuList, childMap, menu);
                map.put("children", childMap);
                mapList.add(map);
            }
        }
        return mapList;
    }

    /***
     * 获取所有子节点(不获取中间节点)
     * 前端element-ui树形控件传父节点进去会默认选中所有子节点
     * 需清除所有父节点，只传子节点
     * @param menus
     * @return
     */
    private List<Map<String,Object>> returnAllChildNode( List<Map<String,Object>> menus ) {
        List<Map<String,Object>> childNode = new ArrayList<>();
        List<Menu> menuList = menuMapper.getAllMenu();
        for ( Map<String,Object> node: menus ) {
            int nId = MapUtils.getInteger(node, "objid", 0);
            boolean flag = true;
            for ( Menu menu: menuList ) {
                if ( menu.getParentId() == nId ) {
                    flag = false;
                    break;
                }
            }
            if ( flag ) {
                childNode.add(node);
            }
        }
        return childNode;
    }

    @Override
    public List setMenu(Map<String, Object> param) {
        int type = MapUtils.getInteger(param, "type", 0);
        List<Integer> nodeIds = (List<Integer>) MapUtils.getObject(param, "node");
        Map role = MapUtils.getMap(param, "role");
        clazzTypeToInt(role);
        Set<Integer> menuTree = this.getAllMenuNode(nodeIds);
        this.setMenuAuthByUserId( type, menuTree, role );
        return this.getMenuTreeByOrg(role);
    }

    /***
     * 保存用户菜单权限
     * 先删除后保存
     * @param type
     * @param menuTree
     */
    private void setMenuAuthByUserId(Integer type, Set<Integer> menuTree, Map role ) {
        menuMapper.deleteMenuRoleByOrg(role);
        if ( menuTree.size() > 0 ) {
            if (type == 0) {  //保存
                menuMapper.saveBatchMenuRoleByOrg(role, menuTree);
            } else if (type == 1) {   //继承成权限(清空)

            } else if (type == 2) {   //继承权限(保留)

            }
        }
    }

    /***
     * 通过前端查询到的子节点，获取所有父级节点
     * @param nodeIds
     * @return
     */
    private Set<Integer> getAllMenuNode( List<Integer> nodeIds ) {
        List<Menu> menuList = menuMapper.getAllMenu();
        Set<Integer> nodes = new HashSet<>();

        for ( Integer id: nodeIds ) {
            List<Integer> treeId = new ArrayList<>();
            treeId.add(id);
            treeId = this.findMenuParentId(menuList, id, treeId);
            nodes.addAll(treeId);
        }
        return nodes;
    }

    /***
     * 递归遍历所有树id
     * @param menuList
     * @param id
     * @param treeId
     * @return
     */
    private List<Integer> findMenuParentId(List<Menu> menuList, int id , List<Integer> treeId ) {
        if ( id == 1 ) {
            return treeId;
        } else {
            for (Menu menu : menuList) {
                if (menu.getObjid() == id) {
                    int pid = menu.getParentId();
                    treeId.add(pid);
                    findMenuParentId( menuList, pid, treeId );
                }
            }
        }
        return treeId;
    }

    private Map clazzTypeToInt( Map param ) {
        String clazzType = MapUtils.getString(param, "clazzType", "");
        if ( clazzType.equals("Employee") ) {
            param.put("type", 1);
        } else if ( clazzType.equals("Department") ) {
            param.put("type", 2);
        } else if ( clazzType.equals("Position") ) {
            param.put("type", 3);
        }
        return param;
    }

    @Override
    public List getChildMenu(Integer menuId) {
        return menuMapper.getChildMenu(menuId);
    }

}
