package com.fmy.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fmy.server.dao.entity.SYS_AREA;
import com.fmy.server.dao.mapper.AreaMapper;
import com.fmy.server.service.ISystemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SystemServiceImpl implements ISystemService {

    @Resource
    private AreaMapper areaMapper;

    @Override
    public List<JSONObject> getAllJsonArea() {
        List<SYS_AREA> areaList = areaMapper.getAllArea();
        List<JSONObject> allArea = new ArrayList<JSONObject>();
        for ( SYS_AREA area: areaList ) {
            if ( area.getParentCode().equals("") ) {    //是顶层节点
                JSONObject provinceJson = new JSONObject();
                provinceJson.put("value", area.getName());
                provinceJson.put("label", area.getName());

                List<JSONObject> childJsonObject = new ArrayList<JSONObject>();
                childJsonObject = this.getJson(area, childJsonObject, areaList);

                provinceJson.put("children", childJsonObject);
                allArea.add(provinceJson);
            }
        }
        return allArea;
    }

    /***
     * 递归处理json
     * @param parentArea
     * @param parentJson
     * @return
     */
    private List<JSONObject> getJson( SYS_AREA parentArea, List<JSONObject> parentJson, List<SYS_AREA> areaList ) {
        String parentCode = parentArea.getCode().toString();

        for ( SYS_AREA area: areaList ) {
            if (area.getParentCode().equals(parentCode)) {
                if ( this.hasChild(area, areaList) ) {
                    JSONObject childJson = new JSONObject();
                    childJson.put("value", area.getName());
                    childJson.put("label", area.getName());

                    List<JSONObject> childJsonList = new ArrayList<JSONObject>();
                    childJsonList = this.getJson(area, childJsonList, areaList);

                    childJson.put("children", childJsonList);
                    parentJson.add(childJson);
                } else {
                    JSONObject childJson = new JSONObject();
                    childJson.put("value", area.getName());
                    childJson.put("label", area.getName());
                    parentJson.add(childJson);
                }
            }
        }
        return parentJson;
    }

    /***
     * 是否有子节点
     * @param parentArea
     * @return
     */
    private boolean hasChild( SYS_AREA parentArea, List<SYS_AREA> areaList ) {
        String parentCode = parentArea.getCode().toString();
        for ( SYS_AREA area: areaList ) {
            if (area.getParentCode().equals(parentCode)) {
                return true;
            }
        }
        return false;
    }

}
