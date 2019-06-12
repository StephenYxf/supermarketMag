package com.yxf.dao;

import com.yxf.model.Supplier;

import java.util.List;

public interface SupplierMapper {
    int deleteByPrimaryKey(String supplierid);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    Supplier selectByPrimaryKey(String supplierid);

    int updateByPrimaryKeySelective(Supplier record);

    int updateByPrimaryKey(Supplier record);

    Supplier selectinfoForsname(String suppliername);

    List<Supplier> selectSupInfo();

}