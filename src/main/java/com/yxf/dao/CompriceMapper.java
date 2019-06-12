package com.yxf.dao;

import com.yxf.model.Comprice;

import java.util.List;

public interface CompriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comprice record);

    int insertSelective(Comprice record);

    Comprice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comprice record);

    int updateByPrimaryKey(Comprice record);

    int deleteBycomId(Comprice record);
    List<Comprice> selectBycomname(String comname);
    Comprice selectBynamesid(String comname,String supplierid);
    List<Comprice> selectBysupid(String supplierid);
    List<Comprice> selectAllinfo();
    List<Comprice> selectBycomna(String comna);
    List<Comprice> selectBycomnamesuid(String comna,String supid);
    List<Comprice> comnameinfo(String comna);
    List<Comprice> supinfo(String supna);
    List<Comprice> comnamesupinfo(String supna,String comna);
    int updateComprice(Comprice record);
    Comprice selectBynamesna(String comna,String supnae);
    int deletecompriceid(String comid);
}