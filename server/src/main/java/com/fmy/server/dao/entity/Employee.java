package com.fmy.server.dao.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    @Column(name = "name", length = 60, columnDefinition = " varchar(60) COMMENT '姓名' ")
    private String name;
    @Column(name = "user_name", length = 60, columnDefinition = " varchar(60) COMMENT '昵称' ")
    private String userName;
    @Column(name = "birthday", columnDefinition = " datetime COMMENT '生日' ")
    private Date birthday;
    @Column(name = "email", length = 60, columnDefinition = " varchar(60) COMMENT '邮件' ")
    private String email;
    @Column(name = "phone", columnDefinition = " int COMMENT '电话号码' ")
    private BigInteger phone;
    @Column(name = "gender", columnDefinition = " int(1) COMMENT '性别' ")
    private Integer gender;
    @Column(name = "password", columnDefinition = " varchar(60) COMMENT '密码' ")
    private String password;

    @Column(name = "province", length = 60, columnDefinition = " varchar(60) COMMENT '省' ")
    private String province;
    @Column(name = "city", length = 60, columnDefinition = " varchar(60) COMMENT '市' ")
    private String city;
    @Column(name = "district", length = 60, columnDefinition = " varchar(60) COMMENT '区/县' ")
    private String district;
    @Column(name = "area", length = 90, columnDefinition = " varchar(90) COMMENT '具体地址' ")
    private String area;
    private Long posId;
    private Integer enabled;
    private String clazzType = "Employee";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OBJID", unique = true, nullable = false)
    private Integer objid;

    @Transient  //代表忽略这个字段的映射
    private String[] address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getPhone() {
        return phone;
    }

    public void setPhone(BigInteger phone) {
        this.phone = phone;
    }

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
        this.setProvince(address[0]);
        this.setCity(address[1]);
        this.setDistrict(address[2]);
    }

    public void addAddress() {
        String[] _address = new String[3];
        _address[0] = this.getProvince();
        _address[1] = this.getCity();
        _address[2] = this.getDistrict();
        this.address = _address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        if ( address == null ) {
            this.province = province;
        } else {
            this.province = this.address[0];
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if ( address == null ) {
            this.city = city;
        } else {
            this.city = this.address[1];
        }
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        if ( address == null ) {
            this.district = district;
        } else {
            this.district = this.address[2];
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Employee() {
    }
    
    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getClazzType() {
        return clazzType;
    }

    public void setClazzType(String clazzType) {
        this.clazzType = clazzType;
    }

}
