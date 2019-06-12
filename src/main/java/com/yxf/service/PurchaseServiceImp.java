package com.yxf.service;

import com.yxf.dao.PurchaseMapper;
import com.yxf.model.Purchase;
import com.yxf.model.SerachView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImp implements PurchaseService{

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public int insert(Purchase record) {
        return purchaseMapper.insert(record);
    }

    @Override
    public List<Purchase> selectAllInfo() {
        return purchaseMapper.selectAllInfo();
    }

    @Override
    public List<Purchase> selectAllInfoComname(String comna) {
        return purchaseMapper.selectAllInfoComname(comna);
    }

    @Override
    public List<Purchase> selectAllInfoSupname(String supna) {
        return purchaseMapper.selectAllInfoSupname(supna);
    }

    @Override
    public List<Purchase> selectAllInfoBothna(String comna, String supna) {
        System.out.println("service:"+comna+","+supna);
        return purchaseMapper.selectAllInfoBothna(comna,supna);
    }


}
