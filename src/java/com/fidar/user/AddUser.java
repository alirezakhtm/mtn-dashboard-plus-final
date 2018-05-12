/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.user;

import com.fidar.database.ConstantParameters;
import com.fidar.database.DBHandler;
import com.fidar.security.SecurityOrder;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alirzea
 */
@WebServlet(name = "AddUser", urlPatterns = {"/AddUser"})
public class AddUser extends HttpServlet {

    private SecurityOrder securityOrder = new SecurityOrder();
    private DBHandler db = new DBHandler();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");
        String password = (String)session.getAttribute("password");
        ConstantParameters answer = securityOrder.whoIsUser(username, password);
        if(!answer.equals(ConstantParameters.USER_UNKNOWN) && !answer.equals(ConstantParameters.USER_SIMPLE)){
            RequestDispatcher dispatcher = request.getRequestDispatcher("adduser.jsp");
            dispatcher.forward(request, response);
        }else{
            securityOrder.logOutUser(request);
            RequestDispatcher dispatcher = request.getRequestDispatcher("loginpage.jsp");
            dispatcher.forward(request, response);
        }
        
        // add account for all type user
        String action = request.getParameter("action");
        switch(action){
            case "add-simple-user":
                String username_newUser = request.getParameter("username");
                String password_newUser = request.getParameter("password");
                String serviceName = request.getParameter("service");
                db.open();
                db.addSimpleUser(username_newUser, password_newUser, serviceName, username);
                db.close();
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
