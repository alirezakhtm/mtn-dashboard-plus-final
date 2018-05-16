/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.service;

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
@WebServlet(name = "ContentConfirm", urlPatterns = {"/ContentConfirm"})
public class ContentConfirm extends HttpServlet {

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
        RequestDispatcher dispatcher;
        if(answer.equals(ConstantParameters.USER_UNKNOWN) || answer.equals(ConstantParameters.USER_SIMPLE)){
            securityOrder.logOutUser(request);
            dispatcher = request.getRequestDispatcher("loginpage.jsp");
            dispatcher.forward(request, response);
        }else{
            try{
                int fileId = Integer.parseInt(request.getParameter("fileId"));
                String action = request.getParameter("action");
                switch(action.toLowerCase()){
                    case "confirm":
                        db.open();
                        db.setFileConfirm(username, fileId);
                        db.close();
                        break;
                    case "denied":
                        db.open();
                        db.setFileDenied(username, fileId);
                        db.close();
                        break;
                }
            }catch(Exception e){
                
            }
            dispatcher = request.getRequestDispatcher("contentconfirm.jsp");
            dispatcher.forward(request, response);
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
