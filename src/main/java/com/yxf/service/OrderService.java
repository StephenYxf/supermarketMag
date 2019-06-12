package com.yxf.service;

import com.yxf.model.Order;
import com.yxf.model.SerachView;

import java.util.List;

public interface OrderService {
    int insert(Order record);
    int insertSelective(Order record);
    List<Order> selectorder();
    List<Order> selectorderTime(SerachView serachView);
}
