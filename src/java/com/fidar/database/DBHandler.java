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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    
    public ConstantParameters returnPeriorityAccordingToUsername(String username){
        // return periority of user for username and password
        ConstantParameters answer;
        answer = ConstantParameters.USER_UNKNOWN;
        try{
            String query = "select * from `" + config.getDatabaseName() + "`.`tbl_all_user` where `username` = '" + username + "'";
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
    
    public int getServiceCode(String serviceName){
        int serviceCode = 0;
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_services` where `serviceName` = '"+serviceName+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                serviceCode = rst.getInt("serviceCode");
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getServiceName : " + e);
        }
        return serviceCode;
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
    
    public List<Integer> getAllServiceCodeForSimpleUser(String username){
        List<Integer> lst  = new ArrayList<>();
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_simple_user` where `username_simple` = '"+username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            String strServiceName = rst.getString("service_name");
            this.close();
            this.open();
            String query_getServiceCode = "select * from `"+config.getDatabaseName()+"`.`tbl_services` where `serviceName`='"+strServiceName+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_getServiceCode);
            rst.next();
            lst.add(rst.getInt("serviceCode"));
        }catch(SQLException e){
            System.err.println("[*] ERROR : DBHandler/getAllServiceCodeForSimpleUser : " + e);
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

    public void setFileConfirm(String admin_username, int fileId) {
        try{
            String query_admin_permission = "select count(*) from `"+config.getDatabaseName()+"`.`tbl_content_file`"
                    + " where `indx`='"+fileId+"' and `admin_username`='"+admin_username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_admin_permission);
            rst.next();
            if(rst.getInt(1) > 0){
                this.close();
                this.open();
                String query_confirm_file = "update `"+config.getDatabaseName()+"`.`tbl_content_file` set"
                        + " `status`='1', `review_date`='"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "' where `indx`='"+fileId+"'";
                stm = conn.createStatement();
                stm.execute(query_confirm_file);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/setFileConfirm : " + e);
        }
    }

    public void setFileDenied(String admin_username, int fileId) {
        try{
            String query_admin_permission = "select count(*) from `"+config.getDatabaseName()+"`.`tbl_content_file`"
                    + " where `indx`='"+fileId+"' and `admin_username`='"+admin_username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_admin_permission);
            rst.next();
            if(rst.getInt(1) > 0){
                this.close();
                this.open();
                String query_denied_file = "update `"+config.getDatabaseName()+"`.`tbl_content_file` set"
                        + " `status`='0', `review_date`='"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "' where `indx`='"+fileId+"'";
                stm = conn.createStatement();
                stm.execute(query_denied_file);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/setFileConfirm : " + e);
        }
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
    
    /**************************************************************************
     *                                                                        *
     *                              File Handling                             *
     *                                                                        *
     * ************************************************************************/
    
    public void saveFileContent(String username, String fileAddress, int serviceCode, String serviceName){
        try{
            // find admin username for this service
            String queryAdminUsername = "select `admin_username` from `"+config.getDatabaseName()+"`.`tbl_service_tables` where `serviceCode`='"+serviceCode+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(queryAdminUsername);
            rst.next();
            String adminUsername = rst.getString(1);
            this.close();
            this.open();
            String queryFileContent = "INSERT INTO `mobtakerandb`.`tbl_content_file`\n" +
                    "(" +
                    "`username`,\n" +
                    "`file_address`,\n" +
                    "`upload_date`,\n" +
                    "`status`,\n" +
                    "`review_date`,\n" +
                    "`admin_username`,\n" +
                    "`service_name`)\n" +
                    "VALUES\n" +
                    "(" +
                    "'"+username+"',\n" +
                    "'"+fileAddress+"',\n" +
                    "'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+"',\n" +
                    "'-1',\n" +
                    "'',\n" +
                    "'"+adminUsername+"',\n" +
                    "'"+serviceName+"')";
            stm = conn.createStatement();
            stm.execute(queryFileContent);
            
        }catch(Exception e){
            System.err.println("[*] ERROR - DBHandler/saveFileContent : " + e);
        }
    }
    
    /**************************************************************************
     *                                                                        *
     *                      Functions for making report                       *
     *                                                                        *
     **************************************************************************/
    public int getNumberOfSuccessfulPayments(String serviceName, String date){
        int ans = 0;
        try{
            stm = conn.createStatement();
            rst = stm.executeQuery("select sum(successNumber) from "+config.getDatabaseName()+"." + "tbl_report_" + serviceName
                    + " where datePayment like '%" + date + "%'");
            while(rst.next()){
                ans = rst.getInt(1);
            }
        }catch(SQLException e){
            System.err.println("SQL - 00 - Report : DBHandler_Native > " + e.getMessage());
        }
        return ans;
    }
    
    public int getNumberOfFailedPayments(String serviceName, String date){
        int ans = 0;
        try{
            stm = conn.createStatement();
            rst = stm.executeQuery("select sum(failedNumber) from "+config.getDatabaseName()+"." + "tbl_report_" + serviceName
                    + " where datePayment like '%" + date + "%'");
            while(rst.next()){
                ans = rst.getInt(1);
            }
        }catch(SQLException e){
            System.err.println("SQL - 01 - Report : DBHandler_Native > " + e.getMessage());
        }
        return ans;
    }
    
    public int getNumberOfUserForSpecificDate(String serviceName, String date){
        int ans = 0;
        try{
            stm = conn.createStatement();
            rst = stm.executeQuery("select customerNumber from "+config.getDatabaseName()+"." + "tbl_report_" + serviceName
                    + " where datePayment like '%" + date + "%'");
            while(rst.next()){
                ans = rst.getInt(1);
            }
        }catch(SQLException e){
            System.err.println("SQL - 03 - Report : DBHandler_Native > " + e.getMessage());
        }
        return ans;
    }
    
    public String getCashOfSpecificDate(String serviceName, String date){
        String ans = "0";
        try{
            stm = conn.createStatement();
            rst = stm.executeQuery("select sum(cash) from "+config.getDatabaseName()+"." + "tbl_report_" + serviceName
                    + " where datePayment like '%" + date + "%'");
            while(rst.next()){
                ans = rst.getString(1);
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getCashOfSpecificDate : " + e.getMessage());
        }
        return ans;
    }
    
    /**
     * @param customerNumber 
     * @param cash 
     * @param successNumber 
     * @param failedNumber 
     * @param priceList 
     * @param successList 
     * @param failedList 
     * @param datePayment
     * @param startTime 
     * @param stopTime 
     * @param jsonError 
     * @param serviceName
     */
    public void saveReport(String serviceName, int customerNumber, int cash, int successNumber, int failedNumber,
            String priceList, String successList, String failedList, String datePayment,
            String startTime, String stopTime, String jsonError){
        String query = "INSERT INTO `"+config.getDatabaseName()+"`.`" + "tbl_report_" + serviceName + "` " +
                        "(`customerNumber`, " +
                        "`cash`, " +
                        "`successNumber`, " +
                        "`failedNumber`, " +
                        "`priceList`, " +
                        "`successList`, " +
                        "`failedList`, " +
                        "`datePayment`, " +
                        "`processStartedTime`, " +
                        "`processFinishedTime`, " +
                        "`failedErrorList`) " +
                        "VALUES " +
                        "('" + customerNumber + "', " +
                        "'" + cash + "', " +
                        "'" + successNumber + "', " +
                        "'" + failedNumber + "', " +
                        "'" + priceList + "', " +
                        "'" + successList + "', " +
                        "'" + failedList + "', " +
                        "'" + datePayment + "', " +
                        "'" + startTime + "', " +
                        "'" + stopTime + "', " +
                        "'" + jsonError + "')";
        try{
            stm = conn.createStatement();
            stm.execute(query);
        }catch(SQLException e){
            System.err.println("SQL - 05 - Report : DBHandler_Native > " + e.getMessage());
        }
    }
    
    /**
     * @param date
     * @return 
     */
    public String getStartedTimeFromReport(String serviceName, String date){
        String strAns = "";
        try{
            String query = "SELECT * FROM `"+config.getDatabaseName()+"`.`"+ "tbl_report_" + serviceName +"` WHERE `datePayment` = '" + date + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                strAns = rst.getString("processStartedTime");
            }
            return strAns;
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getStartedTimeFromReport : " + e.getMessage());
            return "";
        }
    }
    
    /**
     * @param date
     * @return 
     */
    public String getFinishedTimeFromReport(String serviceName, String date){
        String strAns = "";
        try{
            String query = "SELECT * FROM `"+config.getDatabaseName()+"`.`" + "tbl_report_" + serviceName + "` WHERE `datePayment` = '" + date + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                strAns = rst.getString("processFinishedTime");
            }
            return strAns;
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getFinishedTimeFromReport : " + e.getMessage());
            return "";
        }
    }
    
    /**
     * @param date
     * @param serviceName 
     * @return 
     */
    public Map<String, String> getErrorsFromReport(String serviceName, String date){
        Map<String, String> map = new HashMap<>();
        String strJSON = "";
        try{
            String query = "SELECT * FROM `"+config.getDatabaseName()+"`.`"+ "tbl_report_" + serviceName +"` WHERE `datePayment` = '" + date + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                strJSON = rst.getString("failedErrorList");
            }
            if(strJSON != null && !strJSON.equals("")){
                strJSON = strJSON.replace("{ ", "").replace(" }", "");
                String[] strArray = strJSON.split(",");
                for(String s : strArray){
                    String[] ss = s.split(":");
                    if(ss.length >= 2){
                        map.put(ss[0].replace("\"", ""), ss[1]);
                    }
                }
            }
            return map;
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getErrorsFromReport : " + e.getMessage());
            return map;
        }
    }
    
    public ReportTbl getReportObjectForSpecificDate(String serviceName, String date){
        ReportTbl report = new ReportTbl();
        try{
            String query = "SELECT * FROM `"+config.getDatabaseName()+"`.`" 
                    + "tbl_report_" + serviceName 
                    + "` WHERE `datePayment` like '%" + date + "%'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                int customerNumber = rst.getInt("customerNumber");
                int cash = rst.getInt("cash");
                String strLstPrice = rst.getString("priceList");
                String strLstSuccess = rst.getString("successList");
                String strLstFailed = rst.getString("failedList");
                String datePayment = rst.getString("datePayment");
                report.setCash(cash);
                report.setCustomerNumber(customerNumber);
                report.setDate(datePayment);
                report.setLstFailed(convertStrToList(strLstFailed));
                report.setLstPrice(convertStrToList(strLstPrice));
                report.setLstSuccess(convertStrToList(strLstSuccess));
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getReportObjectForSpecificDate : " + e.getMessage());
        }
        return report;
    }
    
    private List<Integer> convertStrToList(String str){
        List<Integer> lst = new ArrayList<>();
        String strTemp = str.substring(0, str.length()-1);
        String[] strArray = strTemp.split(",");
        for(String s : strArray){
            lst.add(Integer.parseInt(s));
        }
        return lst;
    }
    
    public int getActiveUserNumber(String serviceName) {
        int ans = 0;
        try{
            String query_serviceCode = "select * from " + config.getDatabaseName() +".tbl_services where"
                    + " `serviceName`='" + serviceName + "'";
            // run above query and use its'result ...
            stm = conn.createStatement();
            rst = stm.executeQuery(query_serviceCode);
            rst.next();
            int iServiceCode = rst.getInt("serviceCode");         // reached result (above)
            
            this.close();
            this.open();
            
            String queryFetchData = "select count(*) from " + config.getDatabaseName() + ".tbl_service_users where `status`='1'"
                    + " and serviceCode='" + iServiceCode + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(queryFetchData);
            rst.next();
            ans = rst.getInt(1);
            
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getActiveUserName : " + e.getMessage());
        }        
        return ans;
    }
    
    public int getTotalSubUser(String serviceName){
        int ans = 0;
        int iServiceCode = 0;
        
        // get service code from tbl_services
        try{
            String query_serviceCode = "select * from " + config.getDatabaseName() + ".tbl_services where "
                    + "serviceName='" + serviceName + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_serviceCode);
            rst.next();
            iServiceCode = rst.getInt("serviceCode");
        }catch(SQLException e){
            System.err.println("getTotalSubUser - DBHandler  - 01: " + e);
        }
        
        close();
        open();
        
        String query = "select count(*) from " + config.getDatabaseName() + ".tbl_service_users where serviceCode='" + iServiceCode + "'";
        try{
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            ans += rst.getInt(1);
        }catch(SQLException e){
            System.err.println("getTotalSubUser - DBHandler - 03 : " + e);
        }
        
        // return sum of results from two tables above
        return ans;
    }
    
    /**
     * @param date this parameter must similar whit this 2018-09-25
     * @return Number of user that un-registered in this date.
     */
    public int getNewUnSubUser(String serviceName, String date){
        int ans = 0;
        int iServiceCode = 0;
        
        // get service code from tbl_services
        try{
            String query_serviceCode = "select * from " + config.getDatabaseName() + ".tbl_services where "
                    + "serviceName='" + serviceName + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_serviceCode);
            rst.next();
            iServiceCode = rst.getInt("serviceCode");
        }catch(SQLException e){
            System.err.println("getNewUnSubUser - DBHandler  - 01 : " + e);
        }
        
        close();
        open();
        
        String query = "select count(*) from " + config.getDatabaseName() + ".tbl_service_users where"
                + " serviceCode = '" + iServiceCode + "' and unRegDate like '%" + date + "%'";
        try{
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            ans = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("getNewUnSubUser - DBHandler - 02 : " + e);
        }
        return ans;
    }
    
    /**
     * @param date this parameter must similar whit this 2018-09-25
     * @return Number of user that registered in this date.
     */
    public int getNewSubUser(String serviceName, String date){
        int ans = 0;
        int iServiceCode = 0;
        
        // get service code from tbl_services
        try{
            String query_serviceCode = "select * from " + config.getDatabaseName() + ".tbl_services where "
                    + "serviceName='" + serviceName + "'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query_serviceCode);
            rst.next();
            iServiceCode = rst.getInt("serviceCode");
        }catch(SQLException e){
            System.err.println("getNewSubUser - DBHandler  - 01 : " + e);
        }
        
        close();
        open();
        
        String query = "select count(*) from " + config.getDatabaseName() + ".tbl_service_users "
                + "where serviceCode = '" + iServiceCode + "' and regDate like '%" + date + "%'";
        try{
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            rst.next();
            ans = rst.getInt(1);
        }catch(SQLException e){
            System.err.println("getNewSubUser - DBHandler_Native - 02 : " + e);
        }
        return ans;
    }

    public boolean getUserStause(String username) {
        int status = 0;
        try{
            String query = "select * from `"+config.getDatabaseName()+"`.`tbl_all_user` where `username`='"+username+"'";
            stm = conn.createStatement();
            rst = stm.executeQuery(query);
            while(rst.next()){
                status = rst.getInt("status");
            }
        }catch(SQLException e){
            System.err.println("[*] ERROR - DBHandler/getUserStatus : " + e);
        }
        return status == 1;
    }
    
}
