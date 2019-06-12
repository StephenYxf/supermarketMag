package com.yxf.service;

import com.yxf.model.Employee;

import java.util.List;

public interface EmpService {
    //员工基本信息查询接口
    List<Employee> selectEmpInfo();
    List<Employee> serachEmpForUname(String uname);
    Employee loginselect(String uname, String pwd);
    Employee empidforuname(String empid);
    int deleteByPrimaryKey(String empid);
    Employee unamefortype(String uname);
    Employee idSerach(String empid);
    Employee findByName(String uname);
    String send_mail(String email_name,String nickname);
    int insertUser(Employee employee);
    int updateByPrimaryKeySelective(Employee employee);
    Employee selectPhone(String phone);
}
