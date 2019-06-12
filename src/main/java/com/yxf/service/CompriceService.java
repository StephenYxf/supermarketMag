package com.yxf.service;

import com.yxf.model.Comprice;

import java.util.List;

public interface CompriceService {
    int insertSelective(Comprice record);
    List<Comprice> selectBycomname(String comname);
    Comprice selectBynamesid(String comname,String supplierid);
    List<Comprice> selectBysupid(String supplierid);
    List<Comprice> selectAllinfo();
    List<Comprice> selectBycomna(String comna);
    int deletecompriceid(String comid);
    List<Comprice> comnameinfo(String comna);
    List<Comprice> supinfo(String supna);
    List<Comprice> comnamesupinfo(String supna,String comna);
    int updateComprice(Comprice record);
    int deleteBycomId(Comprice record);
    Comprice selectBynamesna(String comna,String supnae);
}
