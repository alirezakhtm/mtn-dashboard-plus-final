/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author alirzea
 */
public class ConfigHandler {
    private String databaseUsername;
    private String databasePassword;
    private String databaseName;

    public ConfigHandler() {
        try{
            InputStream input = getClass().getResourceAsStream("/com/fidar/database/config.json");
            Reader reader = new InputStreamReader(input, "utf-8");
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            String jsonStr = sb.toString();
            Gson gson = new GsonBuilder().create();
            Config config = gson.fromJson(jsonStr, Config.class);
            this.databaseName = config.getDbname();
            this.databaseUsername = config.getUsername();
            this.databasePassword = config.getPassword();
        }catch(IOException e){
            System.err.println("[*] Error : ConfigHandler/Constructor : " + e);
        }
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
