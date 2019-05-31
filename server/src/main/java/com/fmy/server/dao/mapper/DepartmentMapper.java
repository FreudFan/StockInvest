package com.fmy.server.dao.mapper;

import com.fmy.server.dao.entity.Department;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DepartmentMapper {

    /***
     * 获取所有职位
     * @return
     */
    @Select(" SELECT * FROM DEPARTMENT ORDER BY objid; ")
    public List<Department> getAllDepartment();

}
