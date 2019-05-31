package com.fmy.server.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ISystemService {

    /***
     * 得到国省市三级列表(单表查询)
     * @return
     */
    public List<JSONObject> getAllJsonArea();
}
