package com.yxf.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SerachView {
    private String comna;
    private String supna;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enddate;

    public String getComna() {
        return comna;
    }

    public void setComna(String comna) {
        this.comna = comna;
    }

    public String getSupna() {
        return supna;
    }

    public void setSupna(String supna) {
        this.supna = supna;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}
