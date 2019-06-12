package com.yxf.controller;

import com.yxf.model.Commodity;
import com.yxf.model.Employee;
import com.yxf.model.Order;
import com.yxf.model.SerachView;
import com.yxf.service.ComService;
import com.yxf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ComService comService;

    @RequestMapping(value = "/order/insert", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> orderinsert(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        Order order=new Order();
        String orderid = request.getParameter("orderid");
        String details = request.getParameter("details");
        String  money = request.getParameter("money");
        order.setOrderid(orderid);
        order.setDetails(details);
        order.setMoney(Float.valueOf(money));
        order.setTime(new Date());
        int num = orderService.insertSelective(order);
        if(num>0){
            resp.put("msg","插入成功");
            resp.put("code","00000");

        }else {
            resp.put("msg","插入失败");
            resp.put("code","99999");
        }
        return resp;
    }


//    final Long[] boxIds
    @RequestMapping(value = "/com/numjian", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> comnumjian(final String[] comidArray,final String[] comnumArray){
        Map<String,Object> resp=new HashMap<String, Object>();
//        System.out.println("cccccccc:"+comidArray+","+comnumArray);
        int flag=0;
        System.out.println("length:"+comidArray.length);
        for (int i = 0 ;i<comidArray.length;i++){
            Commodity commodity=new Commodity();
            commodity.setComnum(Integer.valueOf(comnumArray[i]));
            commodity.setComid(comidArray[i]);
            System.out.println("comid:"+comidArray[i]+",comnum:"+comnumArray[i]);
            int num = comService.updateByNum(commodity);
            Commodity commodity1 = comService.selectByPrimaryKey(comidArray[i]);
            if(commodity1.getComnum()==0){
                commodity1.setIsup("已下架");
                int num1 = comService.updateByPrimaryKeySelective(commodity1);
            }
            flag+=num;
        }
        if(flag==comidArray.length){
            resp.put("code","00000");
            resp.put("msg","成功");
        }else {
            resp.put("code","99999");
            resp.put("msg","失败");
        }
        return resp;
    }



    @RequestMapping("/order/selectAll")
    @ResponseBody
    public List<Order> selectorder(SerachView serachView){
        Map<String,Object> resp=new HashMap<String, Object>();
        Order order=new Order();
        List<Order> orders;
        if(serachView.getStartdate()==null){
            orders = orderService.selectorder();
        }else{
            orders = orderService.selectorderTime(serachView);
        }
        return orders;
    }

}
