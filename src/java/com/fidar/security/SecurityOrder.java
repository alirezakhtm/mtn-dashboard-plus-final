/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.security;

import com.fidar.database.ConstantParameters;
import com.fidar.database.DBHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alirzea
 */
public class SecurityOrder {
    
    private DBHandler db = new DBHandler();
    
    public ConstantParameters whoIsUser(String username, String password){
        return db.returnPeriorityUser(username, password);
    }
    
    public void logOutUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("username", "none");
        session.setAttribute("password", "none");
    }
    
    public void pushUserInSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("username", request.getParameter("username"));
        session.setAttribute("password", request.getParameter("password"));
    }
    
}
