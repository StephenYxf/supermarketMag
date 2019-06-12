package com.yxf.service;

import com.yxf.model.Commodity;
import com.yxf.model.Employee;

import java.util.List;

public interface ComService {
    //员工基本信息查询接口
    List<Commodity> selectComInfo();
    List<Commodity> serachComForComname(String comname);
    int deleteByPrimaryKey(String comid);
    Commodity selectByPrimaryKey(String comid);
    int updateByPrimaryKeySelective(Commodity commodity);
    int insert(Commodity commodity);
    Commodity selectComAllForname(String comname);
    List<Commodity> selectComInfoNum(int firstNum,int lastNum);
    int updateByNum(Commodity record);
}
