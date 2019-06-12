package com.yxf.model;

public class Commodity {
    private String comid;

    private String comname;

    private Float comprice;

    private Integer comnum;

    private String compicture;

    private String isup;

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

    public Float getComprice() {
        return comprice;
    }

    public void setComprice(Float comprice) {
        this.comprice = comprice;
    }

    public Integer getComnum() {
        return comnum;
    }

    public void setComnum(Integer comnum) {
        this.comnum = comnum;
    }

    public String getCompicture() {
        return compicture;
    }

    public void setCompicture(String compicture) {
        this.compicture = compicture;
    }

    public String getIsup() {
        return isup;
    }

    public void setIsup(String isup) {
        this.isup = isup;
    }
}