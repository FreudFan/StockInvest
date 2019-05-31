package com.fmy.server.dao.mapper;

import com.fmy.server.dao.entity.SYS_AREA;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AreaMapper {

    @Select("select * from SYS_AREA")
    public List<SYS_AREA> getAllArea();

}
