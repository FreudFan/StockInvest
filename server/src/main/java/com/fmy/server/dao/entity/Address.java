package com.fmy.server.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address {
    @Column(name = "emp_id", columnDefinition = " int COMMENT '用户表id' ")
    private Integer empId;
    @Column(name = "province", length = 60, columnDefinition = " varchar(60) COMMENT '省' ")
    private String province;
    @Column(name = "city", length = 60, columnDefinition = " varchar(60) COMMENT '市' ")
    private String city;
    @Column(name = "district", length = 60, columnDefinition = " varchar(60) COMMENT '区/县' ")
    private String district;
    @Column(name = "area", length = 90, columnDefinition = " varchar(90) COMMENT '具体地址' ")
    private String area;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OBJID", unique = true, nullable = false)
    private Integer objid;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "empId=" + empId +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}