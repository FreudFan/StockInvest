package com.fmy.server.dao.entity;

public class Position {
    private Long objid;
    private String name;
    private int deptId;
    private int enabled;
    private String clazzType = "Position";

    public Position() {
    }

    public Long getObjid() {
        return objid;
    }

    public void setObjid(Long objid) {
        this.objid = objid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getClazzType() {
        return clazzType;
    }

    public void setClazzType(String clazzType) {
        this.clazzType = clazzType;
    }

}
