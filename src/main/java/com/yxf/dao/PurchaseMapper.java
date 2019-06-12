package com.yxf.dao;

import com.yxf.model.Purchase;
import com.yxf.model.SerachView;

import java.util.List;

public interface PurchaseMapper {
    int deleteByPrimaryKey(String purchaseid);

    int insert(Purchase record);

    int insertSelective(Purchase record);

    Purchase selectByPrimaryKey(String purchaseid);

    int updateByPrimaryKeySelective(Purchase record);

    int updateByPrimaryKey(Purchase record);

    List<Purchase> selectAllInfo();

    List<Purchase> selectAllInfoComname(String comna);

    List<Purchase> selectAllInfoSupname(String supna);

    List<Purchase> selectAllInfoBothna(String comna,String supna);
}