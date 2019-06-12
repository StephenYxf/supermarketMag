package com.yxf.service;

import com.yxf.dao.SupplierMapper;
import com.yxf.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImp implements SupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public int insertSelective(Supplier record) {
        return supplierMapper.insertSelective(record);
    }

    @Override
    public Supplier selectByPrimaryKey(String supplierid) {
        return supplierMapper.selectByPrimaryKey(supplierid);
    }

    @Override
    public Supplier selectinfoForsname(String suppliername) {
        return supplierMapper.selectinfoForsname(suppliername);
    }

    @Override
    public int updateByPrimaryKeySelective(Supplier record) {
        return supplierMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(String supplierid) {
        return supplierMapper.deleteByPrimaryKey(supplierid);
    }

    @Override
    public List<Supplier> selectSupInfo() {
        return supplierMapper.selectSupInfo();
    }
}
