/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alirzea
 */
public class DBHandler {
    
    private Connection conn;
    private Statement stm;
    private ResultSet rst;
    
    private String url = "", username = "", password = "";

    private ConfigHandler config;
    
    public DBHandler() {
        config = new ConfigHandler();
        url = "jdbc:mysql://localhost:3306/" + config.getDatabaseName() 
                + "?useSSL=false" + "&useUnicode=true&characterEncoding=utf-8";
        username = config.getDatabaseUsername();
        password = config.getDatabasePassword();
    }
    
    public void open(){
       try{
           Class.forName("com.mysql.jdbc.Driver");
           conn = (Connection) DriverManager.getConnection(url, username, password);
       }catch(SQLException | ClassNotFoundException e){
           System.err.println("[*] ERROR : DBHandler/open : " + e);
       }
    }
    
    public void close(){
        try{
            if(!conn.isClosed()) conn.close();
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/close : " + e);
        }
    }
    
    public ConstantParameters returnPeriorityUser(String username, String password){
        // return periority of user for username and password
        ConstantParameters answer;
        answer = ConstantParameters.USER_UNKNOWN;
        try{
            String query = "select * from `" + config.getDatabaseName() + "`.`tbl_all_user` where `username` = '" + username + "' and `password` = '" + password + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            int iPeriority = rst.getInt("periority");
            switch(iPeriority){
                case 0:
                    answer = ConstantParameters.USER_MASTER;
                    break;
                case 1:
                    answer = ConstantParameters.USER_ADMIN;
                    break;
                case 2:
                    answer = ConstantParameters.USER_SIMPLE;
                    break;
                default:
                    answer = ConstantParameters.USER_UNKNOWN;
                    break;
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/returnPeriorityUser : " + e);
        }
        return answer;
    }
    
    public List<Integer> getAllServiceCode() {
        List<Integer> lstAllServiceCode = new ArrayList<>();
        try{
            String query = "select * from `" + config.getDatabaseName() + "`.`tbl_services`";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                lstAllServiceCode.add(rst.getInt("serviceCode"));
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getAllServiceCode : " + e);
        }
        return lstAllServiceCode;
    }
    
    public int getActiveUser(int serviceCode){
        int answer = 0;
        try{
            String query = "select count(*) from `" + config.getDatabaseName() + "`.`tbl_service_users` where `serviceCode` = '" + serviceCode + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                answer = rst.getInt(1);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getActiveUser : " + e);
        }
        return answer;
    }

    public String getServiceName(int serviceCode) {
        String serviceName = "";
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_services` where `serviceCode` = '"+serviceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                serviceName = rst.getString("serviceName");
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getServiceName : " + e);
        }
        return serviceName;
    }
    
    public String getRevenue(int serviceCode){
        String answer = "";
        try{
            String query01 = "select * from `"+config.getDatabaseName()+"`.`tbl_service_tables` where `serviceCode`='"+serviceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query01);
            rst.next();
            String name = rst.getString("tableName");
            this.close();
            this.open();
            String query02 = "select sum(`cash`) from `"+config.getDatabaseName()+"`.`tbl_report_"+name+"`";
            stm = conn.createStatement();
            rst = stm.executeQuery(query02);
            rst.next();
            answer = rst.getString(1);
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getRevenue : " + e);
        }
        return answer;
    }

    public List<Integer> getServices_TopSubscribtion_ServiceCode() {
        List<Integer> lstAnswer = new ArrayList<>();
        List<Integer> lstAllServiceCode = new ArrayList<>();
        Map<Integer, Integer> mapServiceUsers = new HashMap<>();
        try{
            String query = "select `serviceCode` from `"+config.getDatabaseName()+"`.`tbl_services`"; 
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                lstAllServiceCode.add(rst.getInt(1));
            }
            this.close();
            this.open();
            for(int serviceCode : lstAllServiceCode){
                String query02 = "select count(*) from `"+config.getDatabaseName()+"`.`tbl_service_users`"
                        + " where `serviceCode`='"+serviceCode+"'";
                stm = conn.createStatement();
                rst = stm.executeQuery(query02);
                rst.next();
                mapServiceUsers.put(serviceCode, rst.getInt(1));
                this.close();
                this.open();
            }
            mapServiceUsers.
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getServices_TopSubscribtion_ServiceCode : " + e);
        }
        return lstAnswer;
    }

    public String getServiceAdminName(int serviceCode) {
        return "";
    }

    public int getSubNumber(String date) {
        return 0;
    }

    public List<Integer> getAllServiceCodeForAdmin(String username) {
        return null;
    }

    public void addSimpleUser(String username_newUser, String password_newUser, String serviceName, String adminUserName) {
        
    }

    public List<String> getUserList(String admin_username) {
        return null;
    }

    public String getUserPassword(String username) {
        return "";
    }

    public String getUserStatus(String username) {
        // return active or disable
        return "";
    }
    
    public List<ConfirmContentFileObject> getConfirmContentList(String admin_username){
        return null;
    }

    public void disableUser(String admin_username, String user_username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void activeUser(String admin_username, String user_username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
