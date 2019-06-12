package com.yxf.model;

public class Comprice {
    private Integer id;

    private String comid;

    private String comname;

    private Float inprice;

    private String supplierid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComid() {
        return comid;
    }

    public void setComid(String comid) {
        this.comid = comid;
    }

    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname;
    }

    public Float getInprice() {
        return inprice;
    }

    public void setInprice(Float inprice) {
        this.inprice = inprice;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }
}