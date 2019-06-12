package com.yxf.service;

import com.yxf.dao.CompriceMapper;
import com.yxf.dao.EmployeeMapper;
import com.yxf.model.Comprice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompriceServiceImp implements CompriceService{
    @Autowired
    private CompriceMapper compriceMapper;

    @Override
    public int insertSelective(Comprice record) {
        return compriceMapper.insertSelective(record);
    }

    @Override
    public List<Comprice> selectBycomname(String comname) {
        return compriceMapper.selectBycomname(comname);
    }

    @Override
    public Comprice selectBynamesid(String comname, String supplierid) {
        return compriceMapper.selectBynamesid(comname,supplierid);
    }

    @Override
    public List<Comprice> selectBysupid(String supplierid) {
        return compriceMapper.selectBysupid(supplierid);
    }

    @Override
    public List<Comprice> selectAllinfo() {
        return compriceMapper.selectAllinfo();
    }

    @Override
    public List<Comprice> selectBycomna(String comna) {
        return compriceMapper.selectBycomna(comna);
    }

    @Override
    public int deletecompriceid(String comid) {
        return compriceMapper.deletecompriceid(comid);
    }

    @Override
    public List<Comprice> comnameinfo(String comna) {
        return compriceMapper.comnameinfo(comna);
    }

    @Override
    public List<Comprice> supinfo(String supna) {
        return compriceMapper.supinfo(supna);
    }

    @Override
    public List<Comprice> comnamesupinfo(String supna, String comna) {
        return compriceMapper.comnamesupinfo(supna,comna);
    }

    @Override
    public int updateComprice(Comprice record) {
        return compriceMapper.updateComprice(record);
    }

    @Override
    public int deleteBycomId(Comprice record) {
        return compriceMapper.deleteBycomId(record);
    }

    @Override
    public Comprice selectBynamesna(String comna, String supnae) {
        return compriceMapper.selectBynamesna(comna,supnae);
    }


}
