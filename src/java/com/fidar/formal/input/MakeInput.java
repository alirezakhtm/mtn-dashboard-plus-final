/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.formal.input;

import com.fidar.database.DBHandler;
import java.util.List;

/**
 *
 * @author alirzea
 */
public class MakeInput {
    private DBHandler db = new DBHandler();
    
    public String getSelector_AddSimpleUser(String username){
        String answer = "";
//        db.open();
//        List<Integer> lstServiceCode = db.getAllServiceCodeForAdmin(username);
//        db.close();
//        for(int n : lstServiceCode){
//            db.open();
//            String serviceName = db.getServiceName(n);
//            db.close();
//            answer += "<option>" + serviceName + "</option>\n";
//        }
        return answer;
    }
}
