/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.report.chart;

import java.util.List;

/**
 *
 * @author alirzea
 */
public class PieChart {
    private List<Integer> lstActiveUser;
    private List<String> lstBackgroungColor;
    private List<String> lstHoverBackgroundColor;
    private List<String> lstLable;

    public List<Integer> getLstActiveUser() {
        return lstActiveUser;
    }

    public void setLstActiveUser(List<Integer> lstActiveUser) {
        this.lstActiveUser = lstActiveUser;
    }

    public List<String> getLstBackgroungColor() {
        return lstBackgroungColor;
    }

    public void setLstBackgroungColor(List<String> lstBackgroungColor) {
        this.lstBackgroungColor = lstBackgroungColor;
    }

    public List<String> getLstHoverBackgroundColor() {
        return lstHoverBackgroundColor;
    }

    public void setLstHoverBackgroundColor(List<String> lstHoverBackgroundColor) {
        this.lstHoverBackgroundColor = lstHoverBackgroundColor;
    }

    public List<String> getLstLable() {
        return lstLable;
    }

    public void setLstLable(List<String> lstLable) {
        this.lstLable = lstLable;
    }
    
}
