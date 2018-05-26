/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.service;

import com.fidar.database.ConstantParameters;
import com.fidar.database.DBHandler;
import com.fidar.security.SecurityOrder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author alirzea
 */
@WebServlet(name = "UploadContent", urlPatterns = {"/UploadContent"})
@MultipartConfig
public class UploadContent extends HttpServlet {

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
        
        
        try{
            String action = request.getParameter("action");
            String serviceName = request.getParameter("serviceSelecter");
            
            if(action.equals("upload")){
                Part file = request.getPart("file");
                String fileName = Paths.get(file.getSubmittedFileName()).getFileName().toString();
                InputStream input = file.getInputStream();
                // check main directory exist
//                File mainDir = new File("MTNDashbordFileUpload");
//                if(!mainDir.exists()){
//                    mainDir.mkdir();
//                }
                // generate directory for uploading file there
                String randomDir = /*"MTNDashboardFileUpload/" +*/ UUID.randomUUID().toString();
                File ownDir = new File(randomDir);
                boolean result = ownDir.mkdir();
                // create file with same name as uploaded file
                File myFile = new File(randomDir + "/" + fileName);
                OutputStream os = new FileOutputStream(myFile);
                int length = 0;
                byte[] buffer = new byte[1024];
                while((length = input.read(buffer)) > 0){
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                input.close();
                // save result on database
                db.open();
                int serviceCode = db.getServiceCode(serviceName);
                db.close();
                db.open();
                db.saveFileContent(username, randomDir+"/"+fileName, serviceCode, serviceName);
                db.close();
                request.setAttribute("message", "File Successfully uploaded.");
            }else{
                
            }
            
        }catch(Exception e){
            System.err.println("[*] ERROR - UploadContent " + e);
        }
        
        ConstantParameters answer = securityOrder.whoIsUser(username, password);
        RequestDispatcher dispatcher;
        if(answer.equals(ConstantParameters.USER_UNKNOWN)){
            securityOrder.logOutUser(request);
            dispatcher = request.getRequestDispatcher("loginpage.jsp");
            dispatcher.forward(request, response);
        }else{
            dispatcher = request.getRequestDispatcher("uploadcontent.jsp");
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
