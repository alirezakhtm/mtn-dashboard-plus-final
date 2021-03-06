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
        db.open();
        ConstantParameters answer = db.returnPeriorityUser(username, password);
        db.close();
        return answer;
    }
    
    public ConstantParameters whoIsUserName(String username){
        db.open();
        ConstantParameters answer = db.returnPeriorityAccordingToUsername(username);
        db.close();
        return answer;
    }
    
    public void logOutUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("username", "none");
        session.setAttribute("password", "none");
    }
    
    public void pushUserInSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        while(session.getAttributeNames().hasMoreElements()){
            String str = session.getAttributeNames().nextElement();
            if(str.equals("username") || str.equals("password")) return;
        }
        session.setAttribute("username", request.getParameter("username"));
        session.setAttribute("password", request.getParameter("password"));
    }

    public boolean UserStatus(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
        boolean felag = false;
        db.open();
        felag = db.getUserStause(username);
        db.close();
        return felag;
    }
    
}
