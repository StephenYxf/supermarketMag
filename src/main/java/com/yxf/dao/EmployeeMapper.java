package com.yxf.dao;

import com.yxf.model.Employee;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(String empid);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(String empid);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    Employee loginselect(String uname, String pwd);

    List<Employee> selectEmpInfo();

    List<Employee> serachEmpForUname(String uname);

    Employee empidforuname(String empid);

    Employee unamefortype(String uname);

    Employee idSerach(String empid);

    Employee selectPhone(String phone);
}