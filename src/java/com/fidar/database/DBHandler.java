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
import java.util.List;

/**
 *
 * @author alirzea
 */
public class DBHandler {
    
    private Connection conn;
    private Statement stm;
    private ResultSet rst;
    
    private String url = "", username = "", password = "";

    public DBHandler() {
        ConfigHandler config = new ConfigHandler();
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
        return ConstantParameters.USER_ADMIN;
    }
    
    public List<Integer> getAllServiceCode() {
        return new ArrayList<>();
    }
    
    public int getActiveUser(int serviceCode){
        return 0;
    }

    public String getServiceName(int serviceCode) {
        return "";
    }
    
    public String getRevenue(int serviceCode){
        return "";
    }
    
}
