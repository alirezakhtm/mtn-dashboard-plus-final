/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.report.table;

import com.fidar.database.ConfirmContentFileObject;
import com.fidar.database.DBHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author alirzea
 */
public class TableDataset {
    
    private DBHandler db = new DBHandler();
    
    public String getTableTopServices_Subscribtion(){
        String answer = "";
        try{
            db.open();
            List<Integer> lstTopServicesCode = db.getServices_TopSubscribtion_ServiceCode();
            db.close();
            List<String> lstServiceName = new ArrayList<>();
            List<String> lstServiceAdmin = new ArrayList<>();
            List<Integer> lstSubUser = new ArrayList<>();
            int Counter = 0;
            for(Integer m : lstTopServicesCode){
                Counter++;
                if(Counter == 6) break;
                db.open();
                String serviceName = db.getServiceName(m);
                db.close();
                lstServiceName.add(serviceName);
                db.open();
                String serviceAdminName = db.getServiceAdminName(m);
                db.close();
                lstServiceAdmin.add(serviceAdminName);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -1);
                String strDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
                db.open();
                int subNumber = db.getSubNumber(strDate);
                db.close();
                lstSubUser.add(subNumber);
            }
            // create html table row

            Counter = 1;
            for(int n = 0; n < lstServiceName.size(); n++){
                answer += 
                        "<tr>\n" +
                        "   <th scope=\"row\">" + Counter + "</th>\n" +
                        "   <td>" + lstServiceName.get(n) + "</td>\n" +
                        "   <td><span class=\"badge badge-primary\">" + lstServiceAdmin.get(n) + "</span></td>\n" +
                        "   <td>" + lstSubUser.get(n) + "</td>\n" +
                        "</tr>\n";
                Counter++;
            }
        }catch(Exception e){
            
        }
        return answer;
    }
    
    public String getTableUserList(String admin_username){
        String answer = "";
        db.open();
        List<String> lstUsername = db.getUserList(admin_username);
        db.close();
        for(String username : lstUsername){
            answer += "<tr>\n" 
                    + "<th>" + username + "</th>\n";
            db.open();
            String password = db.getUserPassword(username);
            db.close();
            answer += "<th>" + password + "</th>\n";
            db.open();
            String status = db.getUserStatus(username);
            db.close();
            answer += "<th>" + status + "</th>\n";
            switch(status){
                case "active":
                    answer += "<th><a href=\"UserList?admin_username=" + admin_username + "&username=" + username + "&action=disable\" class=\"btn btn-danger\">Disable</a></th>\n</tr>\n";
                    break;
                case "disable":
                    answer += "<th><a href=\"UserList?admin_username=" + admin_username + "&username=" + username + "&action=active\" class=\"btn btn-success\">Active</a></th>\n</tr>\n";
                    break;
            }
        }
        return answer;
    }
    
    public String getTableConfirmFile(String admin_username){
        String answer = "";
        db.open();
        List<ConfirmContentFileObject> lstConfirmContent = db.getConfirmContentList(admin_username);
        db.close();
        int Counter = 1;
        for(ConfirmContentFileObject ccfo : lstConfirmContent){
            answer += "<tr>\n" +
                    "<th scope=\"row\">" + Counter + "</th>\n" +
                        "<td>" + ccfo.getUsername() + "</td>\n" +
                        "<td>" + ccfo.getServiceName() + "</td>\n" +
                        "<td><a href=\"" + ccfo.getFileAddress() + "\" class=\"color-primary\"><span class=\"icon-flag\"></span> File</a></td>\n" +
                        "<td><a href=\"ContentConfirm?action=confirm&fileId=" + ccfo.getFileId() +
                            "\" class=\"btn btn-success\">Confirm</a> <a href=\"ContentConfirm?action=denied&fileId=" +
                            ccfo.getFileId() + "\" class=\"btn btn-danger\">Denied</a></td>\n" +
                    "</tr>\n";
            Counter++;
        }
        return answer;
    }
    
    public String getTableDailyReport(String date, String serviceName){
        String answer = "";
        // ???????
        /*
        <tr>
            <th scope="row">1</th>
            <td>3000</td>
            <td>7356</td>
            <td>15632</td>
        </tr>
        */
        return answer;
    }
    
    public String getReportDetails(String date, String serviceName){
        String answer = "";
        /*
        <p><b>Revenue:</b> 180,000</p>
        <p><b>Total Sub. User:</b> 180,000</p>
        <p><b>Total Active User:</b> 180,000</p>
        <p><b>New Sub. User:</b> 180,000</p>
        <p><b>New UnSub. User:</b> 180,000</p>
        */
        return answer;
    }
}
