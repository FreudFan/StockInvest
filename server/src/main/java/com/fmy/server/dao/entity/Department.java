package com.fmy.server.dao.entity;

public class Department {

    private int objid;
    private String name;
    private int parentId;
    private int sort;
    private String clazzType = "Department";

    public Department() {
    }

    public int getObjid() {
        return objid;
    }

    public void setObjid(int objid) {
        this.objid = objid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getClazzType() {
        return clazzType;
    }

    public void setClazzType(String clazzType) {
        this.clazzType = clazzType;
    }
}
