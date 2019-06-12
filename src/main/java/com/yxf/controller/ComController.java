package com.yxf.controller;

import com.yxf.PurchaseInfo;
import com.yxf.RomUtils;
import com.yxf.model.*;
import com.yxf.service.ComService;
import com.yxf.service.CompriceService;
import com.yxf.service.PurchaseService;
import com.yxf.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ComController {
    @Autowired
    private ComService comService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CompriceService compriceService;

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping("/com/info")
    @ResponseBody
    public List<Commodity> empInfoSelect(String comname){
        List<Commodity> comList;
        if(comname.isEmpty()){
            System.out.println("空属性");
            comList = comService.selectComInfo();
        }else {
            System.out.println("有属性:"+comname);
            comList = comService.serachComForComname(comname);
        }
        return comList;
    }


    @RequestMapping(value = "/com/infotab", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> cominfotab(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        List<Commodity> comList = comService.selectComInfo();
        if(comList.size()!=0){
            resp.put("msg",comList);
            resp.put("code","00000");
        }else {
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }
        return resp;
    }

    @RequestMapping(value = "/com/infolistname", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> cominfolistname(HttpServletRequest request, HttpServletResponse response){
        String comna = request.getParameter("comna");
        System.out.println("comna:"+comna);
        Map<String,Object> resp=new HashMap<String, Object>();
        List<Commodity> comList = comService.serachComForComname(comna);
        if(comList.size()!=0){
            resp.put("msg",comList);
            resp.put("code","00000");
        }else {
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }
        return resp;
    }



    /**
     * 商品添加库存
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/com/addnum", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> comaddnum(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String innum = request.getParameter("innum");
        String comna = request.getParameter("comname");
        Commodity commodity = comService.selectComAllForname(comna);
        System.out.println("innum:"+innum+",comna:"+comna);
        int comnum = commodity.getComnum();//原有的库存
        comnum+=Integer.valueOf(innum);
        commodity.setComnum(comnum);
        int num = comService.updateByPrimaryKeySelective(commodity);
        if(num>0){
            resp.put("msg","更新成功");
            resp.put("code","00000");
        }else {
            resp.put("msg","更新失败");
            resp.put("code","99999");
        }
        return resp;
    }


    @RequestMapping(value = "/com/getddID", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getddID(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        resp.put("msg","DD"+RomUtils.createID());
        return resp;
    }

    @RequestMapping(value = "/com/comnaSearch", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> comnasearch(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comna = request.getParameter("comna");
        Commodity commodity;
        commodity = comService.selectComAllForname(comna);

        if(commodity!=null){
            resp.put("msg",commodity);
            resp.put("code","00000");
        }else {
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }
        return resp;
    }


    @RequestMapping(value = "/comp/addcomprice", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> addComprice(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comna=request.getParameter("comna");
        String cominprice=request.getParameter("cominprice");
        String checkedvalue=request.getParameter("checkedvalue");
        String suppliername=request.getParameter("suppliername");//自选
        String supnaselect=request.getParameter("supnaselect");//下拉
        String supphone=request.getParameter("supphone");
        System.out.println("comna:"+comna+",cominprice:"+cominprice+",checkedvalue:"+checkedvalue+",suppliername:"+suppliername
        +",supnaselect:"+supnaselect+",supphone:"+supphone);
        Comprice comprice=new Comprice();
        Supplier supplier1=new Supplier();
        if(checkedvalue.equals("1")){//自填框 供货商名字 自填框只需要判断是否在supplier表里存在这个供货商就行了，不存在的话不可能重复
            System.out.println("进来checkedvalue=1");
            supplier1=supplierService.selectinfoForsname(suppliername);
            if(supplier1!=null){
                resp.put("msg","输入的是已存在供应商，请在下拉框选择后提交");
                resp.put("code","99999");
                return resp;
            }
            //根据comna查询comid  新增supna
            String supplierid = RomUtils.createSupID();
            Commodity commodity = comService.selectComAllForname(comna);
            String comid = commodity.getComid();
            //存放数据准备insert
            Comprice comprice1=new Comprice();
            comprice1.setComid(comid);
            comprice1.setSupplierid(supplierid);
            comprice1.setComname(comna);
            comprice1.setInprice(Float.valueOf(cominprice));
            //还需要新增sup信息
            Supplier supplier=new Supplier();
            supplier.setSupplieridnumber(supphone);
            supplier.setSupplierid(supplierid);
            supplier.setSuppliername(suppliername);

            int num1 = compriceService.insertSelective(comprice1);
            int num2 = supplierService.insertSelective(supplier);
            if(num1>0 && num2>0){
                resp.put("code","00000");
                resp.put("msg","插入成功");
            }else {
                System.out.println("--"+num1+","+num2);
                resp.put("code","99999");
                resp.put("msg","插入失败");
            }
        }else if(checkedvalue.equals("2")){//下拉框 供货商名字  下拉框的需要根据supna检索supid 然后根据comna和supid检索comprice表
            System.out.println("进来checkedvalue=2");
            //根据supna查询supid
            Supplier supplier = supplierService.selectinfoForsname(supnaselect);
            String supplierid = supplier.getSupplierid();

            comprice=compriceService.selectBynamesna(comna,supplierid);
            if(comprice!=null){
                resp.put("msg","已存在相应记录");
                resp.put("code","99999");
                return resp;
            }

            //comna查询comid
            Commodity commodity = comService.selectComAllForname(comna);
            String comid = commodity.getComid();
            //存放数据准备insert
            Comprice comprice1=new Comprice();
            comprice1.setComid(comid);
            comprice1.setComname(comna);
            comprice1.setInprice(Float.valueOf(cominprice));
            comprice1.setSupplierid(supplierid);

            int num = compriceService.insertSelective(comprice1);
            if(num>0){
                resp.put("code","00000");
                resp.put("msg","插入成功");
            }else {
                System.out.println("--"+num);
                resp.put("code","99999");
                resp.put("msg","插入失败");
            }
        }
        return resp;
    }



    @RequestMapping(value = "/com/supinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> searchsupinfo(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        List<Supplier> suppliers = supplierService.selectSupInfo();
        if(suppliers!=null){
            resp.put("msg",suppliers);
            resp.put("code","00000");
        }else {
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }
        return resp;
    }

    //根据supname查询supplier信息
    @RequestMapping(value = "/com/supnasearch", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> supnasearch(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String supna = request.getParameter("supna");
        Supplier supplier;
        supplier = supplierService.selectinfoForsname(supna);
        if(supplier!=null){
            resp.put("msg",supplier);
            resp.put("code","00000");
        }else {
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }
        return resp;
    }

    @RequestMapping(value = "/com/comnameinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getcomname(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        List<Commodity> comList;
        comList = comService.selectComInfo();

        if(comList!=null){
            resp.put("msg",comList);
            resp.put("code","00000");
        }else {
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }
        return resp;
    }

    @RequestMapping(value = "/com/idforinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> selectAllcomid(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comid = request.getParameter("comid");
        Commodity commodity = comService.selectByPrimaryKey(comid);
        if(commodity==null){
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }else {
            resp.put("msg",commodity);
            resp.put("code","00000");
        }
        return resp;
    }

    @RequestMapping(value = "/comp/info", method = RequestMethod.POST)
    @ResponseBody
    public List<Comprice> userCheck(String comna,String supna){
        System.out.println("=================进货渠道的接口打印================");
//        String comna = request.getParameter("comna");
//        String supna = request.getParameter("supna");
        Map<String,Object> resp = new HashMap<String,Object>();
        List<Comprice> comprices=new ArrayList<Comprice>();
        System.out.println("("+comna+")"+"("+supna+")");
        if(comna!="" && supna==""){
            comna = "%"+comna+"%";
            System.out.println("comna!=null && supna==null");
            comprices=compriceService.comnameinfo(comna);
        }else if(comna=="" && supna!=""){
            supna = "%"+supna+"%";
            System.out.println("comna==null && supna!=null");
            comprices=compriceService.supinfo(supna);
        }else if(comna!="" && supna!=""){
            comna = "%"+comna+"%";
            supna = "%"+supna+"%";
            System.out.println("comna!=null && supna!=null");
            comprices=compriceService.comnamesupinfo( supna,comna);
        }else {
            System.out.println("else");
            comprices=compriceService.selectAllinfo();
        }

        System.out.println("长度:"+comprices.size());

        return comprices;
    }

    @RequestMapping(value = "/comp/updcomPrice", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> updcomPrice(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comid = request.getParameter("comid");
        String supid = request.getParameter("supid");

        return resp;
    }

    @RequestMapping(value = "/comp/idforname", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> idforname(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String supid = request.getParameter("supid");
        System.out.println("supid:"+supid);
        Supplier supplier = supplierService.selectByPrimaryKey(supid);
        if(supplier!=null){
            resp.put("msg",supplier);
            resp.put("code","00000");
        }else {
            resp.put("msg","失败");
            resp.put("code","99999");
        }

        return resp;
    }


    @RequestMapping(value = "/comp/delCompSup", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> delCompSup(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comid = request.getParameter("comid");
        String supid = request.getParameter("supid");

        Comprice comprice=new Comprice();
        comprice.setSupplierid(supid);
        comprice.setComid(comid);
        int num = compriceService.deleteBycomId(comprice);
        if(num > 0 ){
            //判断是否还有对应的供货商的商品，全部没有了就删除供货商信息supplier表中删除
            List<Comprice> comprices = compriceService.selectBysupid(supid);
            if(comprices.size()==0){
                int num1 = supplierService.deleteByPrimaryKey(supid);
                if(num1>0){
                    resp.put("msg","删除成功");
                    resp.put("code","00000");
                }else {
                    resp.put("msg","删除失败");
                    resp.put("code","99999");
                }
            }
        }else {
            resp.put("msg","删除失败");
            resp.put("code","99999");
        }

        return resp;
    }

    /**
     * 更新供应商接口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/comp/subcomp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> subcomprice(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comna = request.getParameter("comna");
        String comid = request.getParameter("comid");
        String inprice = request.getParameter("inprice");
        String supname = request.getParameter("supname");
        String phone = request.getParameter("phone");
        String supid = request.getParameter("supid");
        //一下是更新comprice表的内容
        Comprice comprice=new Comprice();
        comprice.setInprice(Float.valueOf(inprice));
        comprice.setComname(comna);
        comprice.setComid(comid);
        comprice.setSupplierid(supid);
        int num = compriceService.updateComprice(comprice);

        //以下是更新supplier表的内容
        Supplier supplier=new Supplier();
        supplier.setSupplierid(supid);
        supplier.setSuppliername(supname);
        supplier.setSupplieridnumber(phone);
        int num1 = supplierService.updateByPrimaryKeySelective(supplier);

        if(num>0 && num1>0){
            resp.put("msg","提交成功");
            resp.put("code","00000");
        }else {
            resp.put("msg","提交失败");
            resp.put("code","99999");
        }

        return resp;
    }


    @RequestMapping(value = "/comp/incom", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> incom(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String supname = request.getParameter("supname");
        String comname = request.getParameter("comname");
        String cominprice = request.getParameter("cominprice");
        String innum = request.getParameter("innum");
        if(innum.isEmpty()){
                resp.put("msg","请输入完整的数据");
                resp.put("code","88888");
            return resp;
        }
        Commodity commodity = comService.selectComAllForname(comname);
        String comid = commodity.getComid();
        Float allprice = Float.valueOf(cominprice)*Float.valueOf(innum);

        Purchase purchase = new Purchase();
        String purchaseid = "JH"+RomUtils.createID();
        purchase.setPurchaseid(purchaseid);
        purchase.setDetails("供货商名称:"+supname+",商品id:"+comid+",商品名称:"+comname+",单价:"+cominprice+",数量:"+innum+",总价:"+allprice);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        try {
            Date mydate = sdf.parse(dateStr);
            purchase.setTime(mydate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int num = purchaseService.insert(purchase);
        if(num>0){
            resp.put("msg","提交成功");
            resp.put("code","00000");
        }else {
            resp.put("msg","提交失败");
            resp.put("code","99999");
        }

        return resp;
    }

    @RequestMapping(value = "/comp/getghxorder", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getghxorder(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String supname = request.getParameter("supname");
        String comname = request.getParameter("comname");
        Supplier supplier = supplierService.selectinfoForsname(supname);
        if(supplier==null){
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }else {
            String supid = supplier.getSupplierid();

            Comprice comprice = new Comprice();
            comprice=compriceService.selectBynamesid(comname,supid);
            if(comprice==null){
                resp.put("msg","查询失败");
                resp.put("code","99999");
            }else {
                resp.put("msg",comprice);
                resp.put("code","00000");
            }
        }
        return resp;
    }

    @RequestMapping(value = "/comp/getghx", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getghx(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comname = request.getParameter("comname");
        List<Comprice> comprices = compriceService.selectBycomname(comname);
        if(comprices==null){
            resp.put("msg","查询失败");
            resp.put("code","99999");
        }else {
            List<Supplier> suppliers=new ArrayList<Supplier>();
            Supplier supplier=new Supplier();
            for (int i=0;i<comprices.size();i++){
                System.out.println(comprices.get(i).getSupplierid());
                supplier=supplierService.selectByPrimaryKey(comprices.get(i).getSupplierid());
                System.out.println(supplier.getSuppliername());
                suppliers.add(supplier);
            }
            for (int i =0;i<comprices.size();i++){
                comprices.get(i).setSupplierid(suppliers.get(i).getSuppliername());
            }
            resp.put("msg",comprices);
            resp.put("code","00000");
        }
        return resp;
    }


    //图片分页接口
    @RequestMapping(value = "/com/picturepage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> picturePage(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String pageSize = request.getParameter("pageSize");//每页显示6张
        String pageNumber = request.getParameter("pageNumber");//页码  6的倍数   1-6   7-12  13-18     （页码-1）*6+1，页码*6
        int firstNum = (Integer.valueOf(pageNumber)-1)*6;//不+1 ,因为sql里面的limit5，10  是从6到15  10个数字
        int lastNum = firstNum+6;
        System.out.println("首页："+firstNum+",末页："+lastNum);
        List<Commodity> commodities = comService.selectComInfo();//这个查出来的是总数量 用于获得size
        List<Commodity> pagecom = comService.selectComInfoNum(firstNum,lastNum);//这个查询出来是6条数据
        Commodity[] rows=new Commodity[20];
        for(int i =0;i<pagecom.size();i++){
            System.out.println(pagecom.get(i).getComname());
            rows[i]=pagecom.get(i);
        }
        int pageCount = (int)(commodities.size()/Integer.valueOf(pageSize))+1;
        resp.put("pageCount",pageCount);
        resp.put("rows",rows);
        resp.put("total",commodities.size());
        //{"pageCount":2,"  这个好像不需要
//        total":11,
//        "rows":[{"id":30,"name":"test1",
//                "attachmentPath":"/web/qualcert/seeAttachment?companyID=1&certificateId=30&attachment=logo.png"}]}

        return resp;
    }

    //商品删除接口
    @RequestMapping(value = "/com/del", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> deleteByPrimaryKey(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comid = request.getParameter("comid");
        int num = comService.deleteByPrimaryKey(comid);
        int num1 = compriceService.deletecompriceid(comid);
        if(num!=0 && num1!=0){
            resp.put("msg","删除成功");
            resp.put("code","00000");
        }else {
            resp.put("msg","删除失败");
            resp.put("code","99999");
        }
        return resp;
    }

    //商品上架下架状态判断接口
//    @RequestMapping(value = "/com/isup", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String,Object> isupCheck(HttpServletRequest request, HttpServletResponse response){
//        Map<String,Object> resp=new HashMap<String, Object>();
//        String comid = request.getParameter("comid");
//        Commodity commodity = comService.selectByPrimaryKey(comid);
//        String isup=commodity.getIsup();
//        resp.put("msg",isup);
//        resp.put("code","00000");
//        return resp;
//    }



    //商品信息更新接口
    @RequestMapping(value = "/com/upinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> upinfo(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comname = request.getParameter("comname");
        String comprice = request.getParameter("comprice");
        String comid = request.getParameter("comid");
        Commodity commodity=new Commodity();
        commodity.setComname(comname);
        commodity.setComprice(Float.valueOf(comprice));
        commodity.setComid(comid);
        int num = comService.updateByPrimaryKeySelective(commodity);

        if(num!=0){
            resp.put("msg","更新成功");
            resp.put("code","00000");
        }else {
            resp.put("msg","更新失败");
            resp.put("code","99999");
        }
        return resp;
    }


    //商品上架接口
    @RequestMapping(value = "/com/upcom", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> upCom(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comid = request.getParameter("comid");
        int num=0;
        Commodity comm = comService.selectByPrimaryKey(comid);
        int kucun = comm.getComnum();
        if(kucun>0){
            String isup=comm.getIsup();
            if(isup.equals("已下架")){
                Commodity commodity=new Commodity();
                commodity.setComid(comid);
                commodity.setIsup("已上架");
                num = comService.updateByPrimaryKeySelective(commodity);
            }
        }else {
            resp.put("msg","上架失败,库存不足。");
            resp.put("code","99999");
            return resp;
        }

        if(num!=0){
            resp.put("msg","上架成功");
            resp.put("code","00000");
        }else {
            resp.put("msg","上架失败");
            resp.put("code","99999");
        }
        return resp;
    }

    //商品下架接口
    @RequestMapping(value = "/com/downcom", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> downcom(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comid = request.getParameter("comid");
        int num=0;
        Commodity comm = comService.selectByPrimaryKey(comid);
        String isup=comm.getIsup();
        if(isup.equals("已上架")){
            Commodity commodity=new Commodity();
            commodity.setComid(comid);
            commodity.setIsup("已下架");
            num = comService.updateByPrimaryKeySelective(commodity);
        }
        if(num!=0){
            resp.put("msg","下架成功");
            resp.put("code","00000");
        }else {
            resp.put("msg","下架失败");
            resp.put("code","99999");
        }
        return resp;
    }

    //商品添加接口
    @RequestMapping(value = "/com/add", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> addcom(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resp=new HashMap<String, Object>();
        String comname = request.getParameter("comname");
        Commodity com = comService.selectComAllForname(comname);
        if(com==null) {
            String comprice = request.getParameter("comprice");
            String cominprice = request.getParameter("cominprice");
            String suppliername = request.getParameter("suppliername");
            String suppliernumber = request.getParameter("suppliernumber");
            String supplierid = RomUtils.createSupID();
            String comid = RomUtils.createID();
            String compicture = request.getParameter("compicture");
            Commodity commodity = new Commodity();
            commodity.setComid(comid);
            commodity.setComname(comname);
            commodity.setComprice(Float.valueOf(comprice));
            commodity.setComnum(0);
            commodity.setIsup("已下架");
            commodity.setCompicture(compicture);
            int num1 = comService.insert(commodity);

            Comprice comprice1 = new Comprice();
            comprice1.setComid(comid);
            comprice1.setComname(comname);
            comprice1.setInprice(Float.valueOf(cominprice));
            comprice1.setSupplierid(supplierid);
            int num2 = compriceService.insertSelective(comprice1);

            Supplier supplier = new Supplier();
            supplier.setSupplierid(supplierid);
            supplier.setSuppliername(suppliername);
            supplier.setSupplieridnumber(suppliernumber);
            //先查询一下有没有存在该供应商
            Supplier sup = supplierService.selectByPrimaryKey(supplierid);
            int num3=0;
            if(sup==null){
                num3 = supplierService.insertSelective(supplier);
            }


            if (num1 > 0 && num2 > 0 && num3 > 0) {
                resp.put("msg", "添加成功");
                resp.put("code", "00000");
            } else {
                resp.put("msg", "添加失败");
                resp.put("code", "99999");
            }
        }else {
            resp.put("msg", "无法添加，已经存在同商品");
            resp.put("code", "99999");
        }
        return resp;
    }


    @RequestMapping(value="/picture/upLoad",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> fildUpload(@RequestParam(value="file",required=false) MultipartFile file,
                                          HttpServletRequest request,Model model)throws Exception{
        Map<String, String> map = new HashMap<>();
        System.out.println("上传接口进来了吗");
        //基本表单

        //获得物理路径webapp所在路径
        String pathRoot = request.getSession().getServletContext().getRealPath("/");
        String path="";
        if(!file.isEmpty()){
            //生成uuid作为文件名称
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //获得文件类型（可以判断如果不是图片，禁止上传）
            String contentType=file.getContentType();
            //获得文件后缀名称
            String imageName=contentType.substring(contentType.indexOf("/")+1);
            //地址
            path="img\\"+uuid+"."+imageName;
            file.transferTo(new File(pathRoot+path));
            System.out.println("完整路径："+pathRoot+path);
        }else {
            map.put("code","失败");
        }
        System.out.println(path);
        request.setAttribute("imagesPath", path);
        model.addAttribute("imgPath",path);
        map.put("path",path);
        map.put("code","成功");
        return map;
    }

}
