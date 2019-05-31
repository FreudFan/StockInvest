package com.fmy.server.service.impl;

import com.fmy.server.common.EmployeeUtil;
import com.fmy.server.common.MapUtil;
import com.fmy.server.dao.entity.Employee;
import com.fmy.server.dao.mapper.EmployeeMapper;
import com.fmy.server.service.IEmpService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EmpServiceImpl implements IEmpService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public String save(Employee employee) throws Exception {
        if ( employee.getObjid() == null ) {    //注册用户
            String userName = employee.getUserName();
            if ( employeeMapper.getEmployeeByUserName(userName) == null ) {
                int id = employeeMapper.insertEmp(employee);
                if ( employee.getPassword() == null ) {
                    this.resetPasswordByUserName(MapUtil.objectToMap(employee));
                }
                int empId = employee.getObjid();
                return "ok";
            } else {
                return "hasName";
            }
        } else {    //编辑用户信息
            if ( employeeMapper.selectUserNameCount(employee) > 1 ) {
                return "hasName";
            }
            int id = employeeMapper.updateEmployeeInfo(employee);
            return "ok";
        }
    }

    @Override
    public Employee login(Map<String, String> param) throws Exception {
        String userName = MapUtils.getString(param, "userName", "");
        if ( !userName.equals("") ) {
            Employee employee = employeeMapper.loginByUserNameAndPassword(param);
            if ( employee == null ) {
                return null;
            } else {
                return employee;
            }
        }
        return null;
    }

    @Override
    public List<Map<String,Object>> getEmployeeInfoByPage(Map param) throws Exception {
        List<Map<String,Object>> maps = employeeMapper.getEmployeeByPage(param);
        for ( Map<String,Object> map: maps ) {
            EmployeeUtil.getGenderByMap(map);
            String birthday = MapUtils.getString(map, "birthday").substring(0, 10);
            map.put("birthday", birthday);
        }
        return maps;
    }

    @Override
    public Long getEmpCountByKeywords(Map param) throws Exception {
        return employeeMapper.getEmployeeCountByPage(param);
    }

    @Override
    public Employee getEmpByUserName(Map param) throws Exception {
        String userName = MapUtils.getString(param, "userName", "");
        Employee employee = employeeMapper.getEmployeeByUserName(userName);
        employee.addAddress();
        employee.setPassword("");
        return employee;
    }

    @Override
    public String resetPasswordByUserName(Map param) throws Exception {
        String defaultPassword = "1";
        param.put("defaultPassword", defaultPassword);
        employeeMapper.resetPasswordEmpByUserName(param);
        return defaultPassword;
    }

    @Override
    public List<Map<String,Object>> querySearchEmp(Map param) throws Exception {
        List<Map<String,Object>> maps = employeeMapper.querySearchByNameOrUserName(param);
        for ( Map<String,Object> map: maps ) {
            String name = MapUtils.getString(map, "name", "");
            String userName = MapUtils.getString(map, "userName", "");
            map.put( "value", name + "(" + userName + ")" );
//            map.put( "value", name );
        }
        return maps;
    }

}
