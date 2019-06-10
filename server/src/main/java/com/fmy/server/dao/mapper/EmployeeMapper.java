package com.fmy.server.dao.mapper;

import com.fmy.server.dao.entity.Employee;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

//@Mapper
public interface EmployeeMapper {

    @Select(" SELECT * FROM EMPLOYEE ORDER BY objid ")
    public List<Employee> getAllEmployee();

    /***
     * 通过 objid 查询用户
     * @param id
     * @return
     */
    @Select("select * from EMPLOYEE where objid = #{id}")
    public Employee getEmployeeById(Integer id);

    /***
     * 通过 姓名 查询用户
     * @param name
     * @return
     */
    @Select("select * from EMPLOYEE where name = #{name}")
    public Employee getEmployeeByName(String name);

    /***
     * 用户登录
     * @param param
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "objid")
    @Select("select * from EMPLOYEE where user_name = #{userName} and password = #{password} ")
    public Employee loginByUserNameAndPassword(Map param);

    /***
     * 通过 昵称 查询用户
     * @param userName
     * @return
     */
    @Select("select * from EMPLOYEE where user_name = #{userName}")
    public Employee getEmployeeByUserName(String userName);

    /***
     * 通过 objid 删除用户信息
     * @param id
     * @return
     */
    @Delete("delete from EMPLOYEE where objid = #{id} ")
    public int deleteEmpById(Integer id);

    /***
     * 保存用户
     * @param employee
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "objid")
    @Insert(" insert into EMPLOYEE( " +
            " name, user_name, birthday, phone, gender, password, " +
            " province, city, district, area, posId, email, enabled " +
            " ) values ( " +
            " #{name}, #{userName}, #{birthday}, #{phone}, #{gender}, #{password}, " +
            " #{province}, #{city}, #{district}, #{area}, #{posId}, #{email}, #{enabled} " +
            " ) ")
    public int insertEmp(Employee employee);

    /***
     * 重置密码
     * @param param
     * @return
     */
    @Update("update EMPLOYEE set password = #{defaultPassword} where user_name = #{userName} ")
    public int resetPasswordEmpByUserName(Map param);

    /***
     * 编辑用户校验是否有同名 用户名
     * @param employee
     * @return
     */
    @Select("select count(*) from EMPLOYEE where user_name = #{userName}")
    public int selectUserNameCount(Employee employee);

    @Update(" update EMPLOYEE set name = #{name}, user_name = #{userName}, birthday = #{birthday}, " +
            " phone = #{phone}, gender = #{gender}, province = #{province}, city = #{city}, district = #{district}, " +
            " area = #{area}, posId = #{posId}, email = #{email}, enabled = #{enabled} " +
            " where objid = #{objid} ")
    public int updateEmployeeInfo(Employee employee);

    /***
     * 动态模糊搜索用户
     * @param param
     * @return
     */
    @Select(" select e.objid, e.name as name, e.user_name as userName from EMPLOYEE e where e.user_name like concat('%',#{queryName},'%') or e.name like concat('%',#{queryName},'%') ")
    public List<Map<String,Object>> querySearchByNameOrUserName(Map param);

    /***
     * 页面分页查询用户
     * @param param
     * @return
     */
    @SelectProvider(type = EmployeeMapperSql.class, method="selectEmployeeByPage")
    public List<Map<String,Object>> getEmployeeByPage(Map param);

    /***
     * 页面查询用户 总数
     * @param param
     * @return
     */
    @SelectProvider(type = EmployeeMapperSql.class, method="selectEmployeeCountByPage")
    public long getEmployeeCountByPage(Map param);

    /***
     * 删除所有用户
     */
    @Delete("delete from EMPLOYEE")
    public void deleteAll();


    class EmployeeMapperSql {

        public String selectEmployeeByPage(Map param) {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT e.name, e.user_name AS userName, e.gender, ");
            sql.append(" e.birthday, p.name AS position, e.email, e.phone, ");
            sql.append(" e.province, e.city, e.district, e.area ");

            sql.append(this.selectEmployeeSql(param));

            Integer currentPage = MapUtils.getInteger(param, "currentPage", 1);
            Integer currentSize = MapUtils.getInteger(param, "currentSize", 20);
            int start = (currentPage-1) * currentSize;
            sql.append(" ORDER BY e.objid LIMIT ").append(start).append(" , ").append(currentSize).append(" ");
            return sql.toString();
        }

        public String selectEmployeeCountByPage(Map param) {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT COUNT(*) ");
            sql.append(this.selectEmployeeSql(param));
            return sql.toString();
        }

        private String selectEmployeeSql(Map param) {
            StringBuilder sql = new StringBuilder();

            sql.append(" FROM EMPLOYEE e, POSITION p WHERE 1=1 AND e.posId = p.objid ");

            String name = MapUtils.getString(param, "queryName", "");
            if ( !name.equals("") ) {
                sql.append(" AND e.name like '%").append(name).append("%'");
            }

            return sql.toString();
        }

    }

}
