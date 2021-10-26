package com.example.recyclerviewtest.Model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Course implements Serializable {


    public Course(String code, String sect, String name, String chr, List<String> dayVal, List<String> timeVal, List<String> venue, List<String> lecturers) {
        this.code_val = code;
        this.section_val = sect;
        this.name_val = name;
        this.credit_val = chr;
        this.day_val = dayVal;
        this.time_val = timeVal;
        this.venue = venue;
        this.lectures = lecturers;
    }

    public String getCode_val() {
        return code_val;
    }

    public void setCode_val(String code_val) {
        this.code_val = code_val;
    }

    public String getSection_val() {
        return section_val;
    }

    public void setSection_val(String section_val) {
        this.section_val = section_val;
    }

    public String getName_val() {
        return name_val;
    }

    public void setName_val(String name_val) {
        this.name_val = name_val;
    }

    public String getCredit_val() {
        return credit_val;
    }

    public void setCredit_val(String credit_val) {
        this.credit_val = credit_val;
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

    private String code_val = "";
    private String section_val = "";
    private String name_val = "";
    private String credit_val = "";
    private List<String> day_val = Collections.emptyList();
    private List<String> time_val = Collections.emptyList();
    private List<String> venue = Collections.emptyList();
    private List<String> lectures = Collections.emptyList();


}
