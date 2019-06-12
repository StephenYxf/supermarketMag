package com.yxf.controller;

import com.yxf.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yxf.service.EmpService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EmpController {
    @Autowired
    private EmpService empService;

    //员工登录接口
    @RequestMapping(value = "/emp/login", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> userLogin(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        Map<String,Object> resp = new HashMap<String,Object>();
        String uname=request.getParameter("uname");
        String pwd=request.getParameter("pwd");
        Employee emp =empService.loginselect(uname,pwd);
        if(emp!=null){
            if(emp.getEmpid()!=null && emp.getPassword()!=null){
                resp.put("code","00000");
                resp.put("msg",emp);
                session.setAttribute("uname",uname);
                //退出登录要删除这个session
            }
        }else {
            resp.put("msg","登录失败");
            resp.put("code","99999");
        }
        return resp;
    }

    //员工基本信息查询接口
    @RequestMapping("/emp/info")
    @ResponseBody
    public List<Employee> empInfoSelect(String uname){
        //这里的uname指的是真实姓名  name字段
        List<Employee> empList;
        if(uname.isEmpty()){
            System.out.println("空属性");
            empList = empService.selectEmpInfo();
        }else {
            System.out.println("有属性:"+uname);
            empList = empService.serachEmpForUname(uname);
        }
        return empList;
    }

    @RequestMapping(value = "/emp/idForUname", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> empidforuname(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String empid = request.getParameter("empid");
        Employee empList = empService.empidforuname(empid);
        if(empList.getUname()!=null){
            resp.put("msg",empList);
            resp.put("code","00000");
        }else {
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }
        return resp;
    }

    //员工删除接口
    @RequestMapping(value = "/emp/del", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> deleteByPrimaryKey(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String empid = request.getParameter("empid");
        int num = empService.deleteByPrimaryKey(empid);
        if(num!=0){
            resp.put("msg","删除成功");
            resp.put("code","00000");
        }else {
            resp.put("msg","删除失败");
            resp.put("code","99999");
        }
        return resp;
    }

    //网页用户名显示接口 session
    @RequestMapping(value = "/emp/showName", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> showUname(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("uname");
        if(obj==null){
            resp.put("msg","查找失败");
            resp.put("code","99999");
        }else {
            String uname = obj.toString();
            resp.put("msg",uname);
            resp.put("code","00000");
        }

        return resp;
    }

    //删除session接口  退出登录
    @RequestMapping(value = "/emp/delSession", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> unload(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("uname");//删除uname这个session属性
            resp.put("msg","session删除成功");
            resp.put("code","00000");
        }catch (Exception e){
            resp.put("msg","session删除失败");
            resp.put("code","99999");
        }
        return resp;
    }

    //根据手机查询信息 会员信息查询
    @RequestMapping(value = "/emp/selectPhone", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> selectPhone(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String phone = request.getParameter("phone");
        Employee empList = empService.selectPhone(phone);
        if(empList!=null){
            resp.put("msg",empList);
            resp.put("code","00000");
        }else {
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }

        return resp;
    }

    //用户名查询对应type
    @RequestMapping(value = "/emp/unameFortype", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> unamefortype(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String uname = request.getParameter("uname");
        Employee empList = empService.unamefortype(uname);
        if(!empList.getUname().isEmpty()){
            resp.put("msg",empList);
            resp.put("code","00000");
        }else {
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }

        return resp;
    }

    @RequestMapping(value = "/emp/idSerach", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> idSerach(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String empid = request.getParameter("empid");
        System.out.println("传入的empid:"+empid);
        Employee empList = empService.idSerach(empid);
        if(empList.getUname()!=null){
            resp.put("msg",empList);
            resp.put("code","00000");
        }else {
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }
        return resp;
    }

    @RequestMapping(value = "/email/regist", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> emailRegist(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> resp = new HashMap<String,Object>();
        String nickname = request.getParameter("uname");//昵称
        String email=request.getParameter("email");//邮箱
        System.out.println(email+",eamil");
        String isFlag = empService.send_mail(email,nickname);
        if(isFlag!="false"){
            String CheckMade=isFlag.substring(4);
            resp.put("msg",CheckMade);
            resp.put("code","发送成功");
        }else {
            resp.put("code","发送失败");
        }
        return resp;
    }

    @RequestMapping(value = "/emp/check", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> userCheck(HttpServletRequest request,HttpServletResponse response){
        String uname=request.getParameter("uname");
        Map<String,Object> resp = new HashMap<String,Object>();
        Employee employee;
        employee =empService.findByName(uname);
        if(employee==null){
            resp.put("msg","可以使用");
            resp.put("code","00000");
        }else {
            resp.put("msg","用户已存在");
            resp.put("code","99999");
        }
        return resp;
    }

    /**
     * 用户注册
     * @param request
     * @return
     */
    @RequestMapping(value = "/emp/register", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> userRegister(HttpServletRequest request,HttpServletResponse response) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String dateString = df.format(new Date());// new Date()为获取当前系统时间
        Date date=null;
        DateFormat df1 =new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = df1.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String,Object> resp = new HashMap<String,Object>();
        //判断是否已存在用户--获取前端传递的信息
        String name=request.getParameter("name");
        String pwd =request.getParameter("pwd");
        String email=request.getParameter("email");
        String uname=request.getParameter("uname");
        String sex=request.getParameter("sex");
        String address=request.getParameter("address");
        String serviceNumber=request.getParameter("serviceNumber");
        String birthday=request.getParameter("birthday");
        Employee e = empService.findByName(uname);
        if(e==null){
            //注册内容填充
            Employee employee=new Employee();
            employee.setUname(uname);
            employee.setName(name);
            employee.setPassword(pwd);
            employee.setEmail(email);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date date2=sdf.parse(birthday);
            employee.setBirthday(date2);
            employee.setAddress(address);
            employee.setServiceNumber(serviceNumber);
            Calendar cale = null;
            cale = Calendar.getInstance();
            int year = cale.get(Calendar.YEAR);
            employee.setEmpid(String.valueOf(year)+serviceNumber);//年份加上手机号成为id
            employee.setSex(sex);
            employee.setDateofentry(new Date());
            employee.setType(1);
            int num = empService.insertUser(employee);
            if(num>0){
                resp.put("msg",employee);
                resp.put("code","注册成功");
            }else {
                resp.put("code","注册失败");
            }
        }else {
            resp.put("code","注册失败");
        }
        return resp;
    }

    @RequestMapping(value = "/emp/update", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> empupdate(HttpServletRequest request,HttpServletResponse response){
        String empid=request.getParameter("empid");
        String name=request.getParameter("name");
        String password=request.getParameter("password");
        String sex=request.getParameter("sex");
        String servicebumber=request.getParameter("servicebumber");
        String address=request.getParameter("address");
        Map<String,Object> resp = new HashMap<String,Object>();
        Employee employee=new Employee();
        employee.setEmpid(empid);
        employee.setName(name);
        employee.setPassword(password);
        employee.setSex(sex);
        employee.setServiceNumber(servicebumber);
        employee.setAddress(address);
        int num = empService.updateByPrimaryKeySelective(employee);
        if(num>0){
            resp.put("msg","更新成功");
            resp.put("code","00000");
        }else {
            resp.put("msg","更新失败");
            resp.put("code","99999");
        }
        return resp;
    }

}
