package com.yxf.service;

import com.yxf.dao.OrderMapper;
import com.yxf.model.Order;
import com.yxf.model.SerachView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int insert(Order record) {
        return orderMapper.insert(record);
    }

    @Override
    public int insertSelective(Order record) {
        System.out.println("111serviceimp");
        return orderMapper.insertSelective(record);
    }

    @Override
    public List<Order> selectorder() {
        return orderMapper.selectorder();
    }

    @Override
    public List<Order> selectorderTime(SerachView serachView) {
        return orderMapper.selectorderTime(serachView);
    }
}
