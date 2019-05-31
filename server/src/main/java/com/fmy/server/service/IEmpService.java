package com.fmy.server.service;

import com.fmy.server.dao.entity.Employee;

import java.util.List;
import java.util.Map;

public interface IEmpService {

    /***
     * 保存注册信息
     * @param employee
     * @return
     * @throws Exception
     */
    public String save(Employee employee) throws Exception;

    /***
     * 用户登录
     * @param param
     * @return
     * @throws Exception
     */
    public Employee login(Map<String,String> param) throws Exception;


    /***
     * 页面上查询所有用户信息(分页)
     * @param param
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> getEmployeeInfoByPage(Map param) throws Exception;

    /***
     * 页面上查询用户信息 总数
     * @param param
     * @return
     * @throws Exception
     */
    public Long getEmpCountByKeywords(Map param) throws Exception;

    /***
     * 通过用户名查询用户
     * @param param
     * @return
     * @throws Exception
     */
    public Employee getEmpByUserName(Map param) throws Exception;

    /***
     * 通过用户名重置密码
     * @param param
     * @return
     * @throws Exception
     */
    public String resetPasswordByUserName(Map param) throws Exception;

    /***
     * 实时模糊查询用户（姓名、用户名）
     * @param param
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> querySearchEmp(Map param) throws Exception;

}
