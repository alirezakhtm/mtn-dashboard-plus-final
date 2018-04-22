/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author alirzea
 */
public class DBHandler {
    
    private Connection conn;
    private Statement stm;
    private ResultSet rst;
    
    public void open(){
       
    }
    
    public void close(){
        
    }
    
    public ConstantParameters returnPeriorityUser(String username, String password){
        // return periority of user for username and password
        return ConstantParameters.USER_MASTER;
    }
    
}
