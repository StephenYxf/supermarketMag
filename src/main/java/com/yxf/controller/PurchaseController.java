package com.yxf.controller;

import com.yxf.PurchaseInfo;
import com.yxf.model.Purchase;
import com.yxf.model.SerachView;
import com.yxf.service.PurchaseService;
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
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "/pur/info", method = RequestMethod.POST)
    @ResponseBody
    public List<PurchaseInfo> userCheck(SerachView serachView){
        String comna = serachView.getComna();
        String supna = serachView.getSupna();
        Map<String,Object> resp = new HashMap<String,Object>();
        List<Purchase> purchases=new ArrayList<Purchase>();
        System.out.println("("+comna+")"+"("+supna+")");
        if(comna!="" && supna==""){
            comna = "%商品名称:%"+comna+"%";
            System.out.println("comna!=null && supna==null");
            purchases=purchaseService.selectAllInfoComname(comna);
        }else if(comna=="" && supna!=""){
            supna = "%供货商名称:%"+supna+"%";
            System.out.println("comna==null && supna!=null");
            purchases=purchaseService.selectAllInfoSupname(supna);
        }else if(comna!="" && supna!=""){
            comna = "%商品名称:%"+comna+"%";
            supna = "%供货商名称:%"+supna+"%";
            System.out.println("comna!=null && supna!=null");
            purchases=purchaseService.selectAllInfoBothna(comna, supna);
        }else {
            System.out.println("else");
            purchases=purchaseService.selectAllInfo();
        }

        System.out.println("长度:"+purchases.size());
//        if(comna!=null){
//            System.out.println("comna：有属性");
//            comna="商品名称:%"+comna;
//            purchases=purchaseService.selectAllInfoComname(comna);
//        }else{
//            System.out.println("comna：空属性");
//            purchases = purchaseService.selectAllInfo();
//        }
        String supname,comid,comname,num,price,allprice,info;
        //供货商名称:毛毛水果,商品id:2019002,商品名称:点心,单价:13,数量:3,总价:39.0
        List<PurchaseInfo> purchaseInfos = new ArrayList<PurchaseInfo>();
        for (int i =0;i<purchases.size();i++){
            System.out.println("aaa"+purchases.get(i));
            System.out.println("bbb"+purchases.get(i).getDetails());
            info=purchases.get(i).getDetails();
            String[] arr = info.split(",");
            supname = arr[0].substring(6);
            comid = arr[1].substring(5);
            comname = arr[2].substring(5);
            price = arr[3].substring(3);
            num = arr[4].substring(3);
            allprice = arr[5].substring(3);
            PurchaseInfo purchaseInfo=new PurchaseInfo();
            purchaseInfo.setPurchaseid(purchases.get(i).getPurchaseid());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            String dateStr = sdf.format(purchases.get(i).getTime());
            try {
                Date mydate = sdf.parse(dateStr);
                purchaseInfo.setTime(mydate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            purchaseInfo.setSuppliername(supname);
            purchaseInfo.setComid(comid);
            purchaseInfo.setComname(comname);
            purchaseInfo.setInprice(price);
            purchaseInfo.setAllprice(allprice);
            purchaseInfo.setComnum(num);
            purchaseInfos.add(purchaseInfo);
        }
        return purchaseInfos;
    }
}
