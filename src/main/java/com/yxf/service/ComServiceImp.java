package com.yxf.service;

import com.yxf.dao.CommodityMapper;
import com.yxf.dao.EmployeeMapper;
import com.yxf.model.Commodity;
import com.yxf.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class ComServiceImp implements ComService {
    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public List<Commodity> selectComInfo() {
        return commodityMapper.selectComInfo();
    }

    @Override
    public List<Commodity> serachComForComname(String comname) {
        return commodityMapper.serachComForComname(comname);
    }

    @Override
    public int deleteByPrimaryKey(String comid) {
        return commodityMapper.deleteByPrimaryKey(comid);
    }

    @Override
    public Commodity selectByPrimaryKey(String comid) {
        return commodityMapper.selectByPrimaryKey(comid);
    }

    @Override
    public int updateByPrimaryKeySelective(Commodity commodity) {
        return commodityMapper.updateByPrimaryKeySelective(commodity);
    }


    @Override
    public int insert(Commodity commodity) {
        return commodityMapper.insert(commodity);
    }

    @Override
    public Commodity selectComAllForname(String comname) {
        return commodityMapper.selectComAllForname(comname);
    }

    @Override
    public List<Commodity> selectComInfoNum(int firstNum, int lastNum) {
        return commodityMapper.selectComInfoNum(firstNum,lastNum);
    }

    @Override
    public int updateByNum(Commodity record) {
        return commodityMapper.updateByNum(record);
    }
}
