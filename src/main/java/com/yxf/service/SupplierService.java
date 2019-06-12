package com.yxf.service;


import com.yxf.model.Supplier;

import java.util.List;

public interface SupplierService {
    int insertSelective(Supplier record);
    Supplier selectByPrimaryKey(String supplierid);
    Supplier selectinfoForsname(String suppliername);
    int updateByPrimaryKeySelective(Supplier record);
    int deleteByPrimaryKey(String supplierid);
    List<Supplier> selectSupInfo();
}
