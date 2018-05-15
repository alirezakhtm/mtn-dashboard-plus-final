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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
            Map<Integer, Integer> mapServiceSorted = this.sortMap(mapServiceUsers);
            for(Integer k : mapServiceSorted.keySet()){
                lstAnswer.add(k);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getServices_TopSubscribtion_ServiceCode : " + e);
        }
        return lstAnswer;
    }

    private Map<Integer, Integer> sortMap(Map<Integer, Integer> mapUnsorted){
        List<Map.Entry<Integer, Integer>> lst = new LinkedList<>(mapUnsorted.entrySet());
        Collections.sort(lst, new Comparator<Map.Entry<Integer, Integer>>(){
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        Map<Integer, Integer> mapAnswer = new LinkedHashMap<Integer, Integer>();
        for(Map.Entry<Integer, Integer> entry : lst){
            mapAnswer.put(entry.getKey(), entry.getValue());
        }
        return mapAnswer;
    }
    
    
    public String getServiceAdminName(int serviceCode) {
        String answer = "";
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_service_tables` where `serviceCode` = '"+serviceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            answer = rst.getString("admin_username");
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getServiceAdminName : " + e);
        }
        return answer;
    }

    public int getSubNumber(int serviceCode, String date) {
        int answer = 0;
        try{
            String query = "select count(*) from `"+config.getDatabaseName()+"`.`tbl_service_users` "
                    + "where `serviceCode` = '"+serviceCode+"' and `regDate` like '%"+date+"%'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            answer = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getSubNumber : " + e);
        }
        return answer;
    }

    public List<Integer> getAllServiceCodeForAdmin(String username) {
        List<Integer> lst  = new ArrayList<>();
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_service_tables` where `admin_username` = '"+username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                int serviceCode = rst.getInt("serviceCode");
                lst.add(serviceCode);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getAllServiceCodeForAdmin : " + e);
        }
        return lst;
    }

    public boolean addSimpleUser(String username_newUser, String password_newUser, String serviceName, String adminUserName) {
        try{
            
            // check that username is exist in table ro not.
            String query_username_exist = "select count(*) from `"+config.getDatabaseName()+"`.`tbl_all_user` where `username`='"+username_newUser+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_username_exist);
            rst.next();
            if(rst.getInt(1) != 0){
                return false;
            }
            this.close();
            this.open();
            String query = "insert into `"+config.getDatabaseName()+"`.`tbl_simple_user`(`username_simple`, `username_admin`, `service_name`) "
                    + "values('"+username_newUser+"', '"+adminUserName+"', '"+serviceName+"')";
            stm = conn.createStatement();
            stm.execute(query);
            this.close();
            this.open();
            query = "INSERT INTO `"+config.getDatabaseName()+"`.`tbl_all_user`\n" +
                    "(" +
                    "`username`,\n" +
                    "`password`,\n" +
                    "`periority`,\n" +
                    "`status`)\n" +
                    "VALUES\n" +
                    "(" +
                    "'"+username_newUser+"',\n" +
                    "'"+password_newUser+"',\n" +
                    "'2',\n" +
                    "'1')";
            stm = conn.createStatement();
            stm.execute(query);
//            this.close();
//            this.open();
//            query = "INSERT INTO `mobtakerandb`.`tbl_simple_user`\n" +
//                    "(" +
//                    "`username_simple`,\n" +
//                    "`username_admin`,\n" +
//                    "`service_name`)\n" +
//                    "VALUES\n" +
//                    "(" +
//                    "'"+username_newUser+"',\n" +
//                    "'"+adminUserName+"',\n" +
//                    "'"+serviceName+"')";
//            stm = conn.createStatement();
//            stm.execute(query);
            return true;
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/addSimpleUser : " + e);
            return false;
        }
    }

    public List<String> getUserList(String admin_username) {
        List<String> lstUsername = new ArrayList<>();
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_simple_user` where `username_admin`='"+admin_username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                lstUsername.add(rst.getString("username_simple"));
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getUserList : " + e);
        }
        return lstUsername;
    }

    public String getUserPassword(String username) {
        String password = "";
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_all_user` where `username`='"+username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            password = rst.getString("password");
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getUserPassword : " + e);
        }
        return password;
    }

    public String getUserStatus(String username) {
        // return active or disable
        String[] userStatus = new String[]{"disable", "active"};
        int iStatus = 0;
        try{
            String query = "select `status` from `"+config.getDatabaseName()+"`.`tbl_all_user` where `username`='"+username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            iStatus = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getUserStatus : " + e);
        }
        return userStatus[iStatus];
    }
    
    public List<ConfirmContentFileObject> getConfirmContentList(String admin_username){
        List<ConfirmContentFileObject> lstAnswer = new ArrayList<>();
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_content_file` where `admin_username` = '"+admin_username+"'"
                    + " and `status` = '-1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ConfirmContentFileObject ccfo = new ConfirmContentFileObject();
                ccfo.setDate(rst.getString("upload_date"));
                ccfo.setFileAddress(rst.getString("file_address"));
                ccfo.setFileId(rst.getInt("indx"));
                ccfo.setServiceName(rst.getString("service_name"));
                ccfo.setUsername(rst.getString("username"));
                ccfo.setSeenDate(rst.getString("review_date"));
                ccfo.setStatus(-1);
                lstAnswer.add(ccfo);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getConfirmContentList : " + e);
        }
        return lstAnswer;
    }

    public void disableUser(String admin_username, String user_username) {
        try{
            String query_admin_check = "select count(*) from `"+config.getDatabaseName()+"`.`tbl_simple_user`"
                    + " where `username_simple`='"+user_username+"' and `username_admin`='"+admin_username+"'";
            String query_disable_user = "UPDATE `"+config.getDatabaseName()+"`.`tbl_all_user`\n" +
                        "SET\n" +
                        "`status` = '0'\n" +
                        "WHERE `username` = '"+user_username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_admin_check);
            rst.next();
            int isExistThisUserAndAdminHavePermission = rst.getInt(1);
            this.close();
            this.open();
            if(isExistThisUserAndAdminHavePermission != 0){
                stm = conn.createStatement();
                stm.execute(query_disable_user);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/disableUser : " + e);
        }
    }

    public void activeUser(String admin_username, String user_username) {
        try{
            String query_admin_check = "select count(*) from `"+config.getDatabaseName()+"`.`tbl_simple_user`"
                    + " where `username_simple`='"+user_username+"' and `username_admin`='"+admin_username+"'";
            String query_disable_user = "UPDATE `"+config.getDatabaseName()+"`.`tbl_all_user`\n" +
                        "SET\n" +
                        "`status` = '1'\n" +
                        "WHERE `username` = '"+user_username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_admin_check);
            rst.next();
            int isExistThisUserAndAdminHavePermission = rst.getInt(1);
            this.close();
            this.open();
            if(isExistThisUserAndAdminHavePermission != 0){
                stm = conn.createStatement();
                stm.execute(query_disable_user);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/activeUser : " + e);
        }
    }

    public String getServiceName(String admin_username) {
        String serviceName = "";
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_service_tables` where `admin_username` = '"+admin_username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                serviceName = rst.getString("tableName");
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getServiceName - 02 : " + e);
        }
        return serviceName;
    }

    public String getServiceName_simpleUser(String username) {
        String serviceName = "";
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_simple_user` where `username_simple` = '"+username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                serviceName = rst.getString("service_name");
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getServiceName - 02 : " + e);
        }
        return serviceName;
    }

    public void setFileConfirm(int fileId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setFileDenied(int fileId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ConfirmContentFileObject> getConfirmContentListHistory(String admin_username) {
        List<ConfirmContentFileObject> lstAnswer = new ArrayList<>();
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_content_file` where `admin_username` = '"+admin_username+"'"
                    + " and `status` <> '-1'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                ConfirmContentFileObject ccfo = new ConfirmContentFileObject();
                ccfo.setDate(rst.getString("upload_date"));
                ccfo.setFileAddress(rst.getString("file_address"));
                ccfo.setFileId(rst.getInt("indx"));
                ccfo.setServiceName(rst.getString("service_name"));
                ccfo.setUsername(rst.getString("username"));
                ccfo.setSeenDate(rst.getString("review_date"));
                ccfo.setStatus(rst.getInt("status"));
                lstAnswer.add(ccfo);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getConfirmContentList : " + e);
        }
        return lstAnswer;
    }
    
}
