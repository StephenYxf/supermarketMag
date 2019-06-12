package com.yxf.service;

import com.yxf.dao.EmployeeMapper;
import com.yxf.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class EmpServiceImp implements EmpService {
    @Autowired
    private EmployeeMapper employeeMapper;


    //员工基本信息查询接口
    @Override
    public List<Employee> selectEmpInfo() {
        return employeeMapper.selectEmpInfo();
    }
    public List<Employee> serachEmpForUname(String uname) {
        return employeeMapper.serachEmpForUname(uname);
    }


    //员工登录接口
    @Override
    public Employee loginselect(String uname, String pwd) {
        Employee emp = employeeMapper.loginselect(uname,pwd);
        return emp;
    }

    //员工id和uname转换接口
    @Override
    public Employee empidforuname(String empid) {
        Employee emp = employeeMapper.empidforuname(empid);
        return emp;
    }

    //根据员工id删除员工信息接口
    @Override
    public int deleteByPrimaryKey(String empid) {
        int num = employeeMapper.deleteByPrimaryKey(empid);
        return num;
    }

    @Override
    public Employee unamefortype(String uname) {
        Employee emp = employeeMapper.unamefortype(uname);
        return emp;
    }

    @Override
    public Employee idSerach(String empid) {
        Employee emp = employeeMapper.idSerach(empid);
        return emp;
    }

    @Override
    public Employee findByName(String uname) {
        Employee emp = employeeMapper.unamefortype(uname);
        return emp;
    }

    @Override
    public String send_mail(String email_name, String nickname) {
        int rang=(int)(Math.random()*9000)+1000;
        //创建连接对象 连接到邮件服务器
        Properties properties = new Properties();
        //设置发送邮件的基本参数
        //发送邮件服务器
        properties.put("mail.smtp.host", "smtp.qq.com");
        //发送端口
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        //设置发送邮件的账号和密码
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //两个参数分别是发送邮件的账户和密码
                return new PasswordAuthentication("1056257539@qq.com","hvajzxweeyzibeig");
            }
        });
        try {
            //创建邮件对象
            System.out.println("创建邮件对象");
            Message message = new MimeMessage(session);
            //设置发件人
            System.out.println("设置发件人");
            message.setFrom(new InternetAddress("1056257539@qq.com"));
            //设置收件人
            System.out.println("设置收件人");
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(email_name));
            //设置主题
            System.out.println("设置主题");
            message.setSubject("这是一份测试邮件");
            //设置邮件正文  第二个参数是邮件发送的类型
            System.out.println("设置邮件正文");
            message.setContent(nickname+",您好！这是你的随机验证码:"+rang,"text/html;charset=UTF-8");
            //发送一封邮件
            System.out.println("发送一封邮件");
            Transport.send(message);
            System.out.println("发送一封邮件之后");
            return "true"+rang;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "false";
        }
    }

    @Override
    public int insertUser(Employee employee) {
        return employeeMapper.insert(employee);
    }

    @Override
    public int updateByPrimaryKeySelective(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public Employee selectPhone(String phone) {
        return employeeMapper.selectPhone(phone);
    }


}
