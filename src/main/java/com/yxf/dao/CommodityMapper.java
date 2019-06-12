package com.yxf.dao;

import com.yxf.model.Commodity;

import java.util.List;

public interface CommodityMapper {
    int deleteByPrimaryKey(String comid);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(String comid);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);

    List<Commodity> serachComForComname(String comname);

    List<Commodity> selectComInfo();

    List<Commodity> selectComInfoNum(int firstNum,int lastNum);

    Commodity selectComAllForname(String comname);

    int updateByNum(Commodity record);
}