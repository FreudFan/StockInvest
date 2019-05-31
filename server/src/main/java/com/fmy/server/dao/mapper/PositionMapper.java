package com.fmy.server.dao.mapper;

import com.fmy.server.dao.entity.Position;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PositionMapper {

    /***
     * 获取所有职位
     * @return
     */
    @Select(" SELECT * FROM POSITION WHERE enabled = true ORDER BY objid; ")
    public List<Position> getAllPos();

}
