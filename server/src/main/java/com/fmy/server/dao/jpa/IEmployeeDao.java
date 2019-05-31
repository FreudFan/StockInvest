package com.fmy.server.dao.jpa;

import com.fmy.server.dao.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

//继承JpaRepository来完成对数据库的操作
public interface IEmployeeDao extends JpaRepository<Employee,Integer> {

}
