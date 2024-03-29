package com.yxf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PageController {

    //页面跳转接口
    @RequestMapping(value = "/page/reload", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> turnPage(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();

        if(request.getSession().getAttribute("uname")==null){
            resp.put("isEmp","true");//空
            return resp;
        }else {
            resp.put("isEmp","false");
            return resp;
        }
    }
}
