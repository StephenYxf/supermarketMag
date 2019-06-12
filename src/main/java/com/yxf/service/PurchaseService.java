package com.yxf.service;

import com.yxf.model.Purchase;
import com.yxf.model.SerachView;

import java.util.List;

public interface PurchaseService {
    int insert(Purchase record);
    List<Purchase> selectAllInfo();
    List<Purchase> selectAllInfoComname(String comna);
    List<Purchase> selectAllInfoSupname(String supna);
    List<Purchase> selectAllInfoBothna(String comna,String supna);
}
