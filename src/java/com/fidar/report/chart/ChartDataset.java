/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.report.chart;

import com.fidar.database.DBHandler;
import com.fidar.database.ReportTbl;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author alirzea
 */
public class ChartDataset {
    
    DBHandler db = new DBHandler();
    
    public String getDatasetPieChart_Admin(){
        
        db.open();
        List<Integer> allServiceCode = db.getAllServiceCode();
        db.close();
        Random random = new Random();
        List<String> lstLable = new ArrayList<>();
        List<Integer> lstActiveUser = new ArrayList<>();
        List<String> lstBackgroungColor = new ArrayList<>();
        List<String> lstHoverBackgroundColor = new ArrayList<>();
        for(int n : allServiceCode){
            db.open();
            int activeUser = db.getActiveUser(n);
            db.close();
            lstActiveUser.add(activeUser);
            lstBackgroungColor.add("rgba(" + random.nextInt(255) + ", " + random.nextInt(255) + ", " + random.nextInt(255) + "," + random.nextFloat() + ")");
            lstHoverBackgroundColor.add("rgba(" + random.nextInt(255) + ", " + random.nextInt(255) + ", " + random.nextInt(255) + "," + random.nextFloat() + ")");
            db.open();
            String serviceName = db.getServiceName(n);
            db.close();
            lstLable.add(serviceName);
        }
        
        String data = "[ ";
        for(int n : lstActiveUser){
            data += n + ", ";
        }
        data = data.substring(0, data.lastIndexOf(","));
        data += " ]";
        
        String backgroundColor = "[ ";
        for(String s : lstBackgroungColor){
            backgroundColor += "\"" + s + "\", ";
        }
        backgroundColor = backgroundColor.substring(0, backgroundColor.lastIndexOf(","));
        backgroundColor += " ]";
        
        String hoverBackgournColor = "[ ";
        for(String s : lstHoverBackgroundColor){
            hoverBackgournColor += "\"" + s + "\", ";
        }
        hoverBackgournColor = hoverBackgournColor.substring(0, hoverBackgournColor.lastIndexOf(","));
        hoverBackgournColor += " ]";
        
        String lable = "[ ";
        for(String s : lstLable){
            lable += "\"" + s + "\", ";
        }
        lable = lable.substring(0, lable.lastIndexOf(","));
        lable += " ]";
        
        String answer = "data: {\n" +
"			datasets: [ {\n" +
"				data: " + data + ",\n" +
"				backgroundColor: " + backgroundColor + ",\n" +
"				hoverBackgroundColor: " + hoverBackgournColor + "\n" +
"\n" +
"                            } ],\n" +
"			labels: " + lable + "\n" +
"		}";
        
        return answer;
        
        //return "";
    }

    public String getDatasetPieChart_User(){
        return this.getDatasetPieChart_Admin();
    }
    
    public String getDatasetBarChart_Admin(){
        
        db.open();
        List<Integer> allServiceCode = db.getAllServiceCode();
        db.close();
        List<String> lstServiceName = new ArrayList<>();
        List<String> lstRevenue = new ArrayList<>();
        for(int n : allServiceCode){
            String serviceName;
            String revenue;
            db.open();
            serviceName = db.getServiceName(n);
            db.close();
            db.open();
            revenue = db.getRevenue(n);
            db.close();
            lstServiceName.add(serviceName);
            lstRevenue.add(revenue);
        }
        
        String data = "[ ";
        for(String p : lstRevenue){
            data += p + ", ";
        }
        data = data.substring(0, data.lastIndexOf(","));
        data += " ]";
        
        String lable = "[ ";
        for(String s : lstServiceName){
            lable += "\"" + s + "\", ";
        }
        lable = lable.substring(0, lable.lastIndexOf(","));
        lable += " ]";
        
        String answer = "data: {\n" +
"                labels: " + lable + ",\n" +
"                datasets: [\n" +
"                    {\n" +
"                        label: \"Revenue\",\n" +
"                        data: " + data + ",\n" +
"                        borderColor: \"rgba(0, 123, 255, 0.9)\",\n" +
"                        borderWidth: \"0\",\n" +
"                        backgroundColor: \"rgba(0, 123, 255, 0.5)\"\n" +
"                    }\n" +
"                ]\n" +
"            }";
        
        
        return answer;
        
        //return "";
    }
    
    public String getDatasetBarChart_User(){
        return this.getDatasetBarChart_Admin();
    }
    
    public String getDatasetBarChart_RevinueReport(String serviceName, String date){
        
        db.open();
        ReportTbl reportTbl = db.getReportObjectForSpecificDate(serviceName, date);
        db.close();
        List<Integer> lstPrice = reportTbl.getLstPrice();
        List<Integer> lstSuccessPaymet = reportTbl.getLstSuccess();
        List<Integer> lstFailedPayment = reportTbl.getLstFailed();
        // create price list according to javascript syntax for show data
        String strPrice = "";
        if(lstPrice != null){
            for(int p : lstPrice){
                strPrice += "\""+p+"\",";
            }
            strPrice = "[" + strPrice.substring(0, strPrice.lastIndexOf(",")) + "]";
        }
        
        // create success list according to javascript syntax for show data
        String strSuccess = "";
        if(lstSuccessPaymet != null){
            for(Integer s : lstSuccessPaymet){
                strSuccess += s + ", ";
            }
            strSuccess = "[" + strSuccess.substring(0, strSuccess.lastIndexOf(",")) + "]";
        }
        
        // create failed list according to javascript syntax for show data
        String strFailed = "";
        if(lstFailedPayment != null){
            for(Integer f : lstFailedPayment){
                strFailed += f + ", ";
            }
            strFailed = "[" + strFailed.substring(0, strFailed.lastIndexOf(",")) + "]";
        }
        
        
        String answer = 
                "labels: %s,\n" +
                "datasets: [\n" +
                "    {\n" +
                "        label: \"Success\",\n" +
                "        data: %s,\n" +
                "        borderColor: \"rgba(0, 123, 255, 0.9)\",\n" +
                "        borderWidth: \"0\",\n" +
                "        backgroundColor: \"rgba(0, 123, 255, 0.5)\"\n" +
                "    },\n" +
                "    {\n" +
                "        label: \"Fail\",\n" +
                "        data: %s,\n" +
                "        borderColor: \"rgba(0,0,0,0.09)\",\n" +
                "        borderWidth: \"0\",\n" +
                "        backgroundColor: \"rgba(200,50,0,0.5)\"\n" +
                "    }\n" +
                "]";
        answer = String.format(answer, strPrice, strSuccess, strFailed);
        return answer;
    }
}
