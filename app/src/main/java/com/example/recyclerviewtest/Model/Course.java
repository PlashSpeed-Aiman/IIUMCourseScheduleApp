package com.example.recyclerviewtest.Model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;


public class Course implements Serializable {

    private String codeVal = "";
    private String sectionVal = "";
    private String nameVal = "";
    private String creditVal = "";
    private List<String> day_val = Collections.emptyList();
    private List<String> time_val = Collections.emptyList();
    private List<String> venue = Collections.emptyList();
    private List<String> lectures = Collections.emptyList();
    private String htmlTable = "";


    public Course(String code, String sect, String name, String chr, List<String> dayVal, List<String> timeVal, List<String> venue, List<String> lecturers,String Data) {
        this.codeVal = code;
        this.sectionVal = sect;
        this.nameVal = name;
        this.creditVal = chr;
        this.day_val = dayVal;
        this.time_val = timeVal;
        this.venue = venue;
        this.lectures = lecturers;
        this.htmlTable = Data;
    }

    public String getCodeVal() {
        return codeVal;
    }

    public void setCodeVal(String codeVal) {
        this.codeVal = codeVal;
    }

    public String getSectionVal() {
        return sectionVal;
    }

    public void setSectionVal(String sectionVal) {
        this.sectionVal = sectionVal;
    }

    public String getNameVal() {
        return nameVal;
    }

    public void setNameVal(String nameVal) {
        this.nameVal = nameVal;
    }

    public String getCreditVal() {
        return creditVal;
    }

    public void setCreditVal(String creditVal) {
        this.creditVal = creditVal;
    }

    public List<String> getDay_val() {
        return day_val;
    }

    public void setDay_val(List<String> day_val) {
        this.day_val = day_val;
    }

    public List<String> getTime_val() {
        return time_val;
    }

    public void setTime_val(List<String> time_val) {
        this.time_val = time_val;
    }

    public List<String> getVenue() {
        return venue;
    }

    public void setVenue(List<String> venue) {
        this.venue = venue;
    }

    public List<String> getLectures() {
        return lectures;
    }

    public void setLectures(List<String> lectures) {
        this.lectures = lectures;
    }

    public String getHtmlTable() {
        return htmlTable;
    }

    public void setHtmlTable(String htmlTable) {
        this.htmlTable = htmlTable;
    }
}
