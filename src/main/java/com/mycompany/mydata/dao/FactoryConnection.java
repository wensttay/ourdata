/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author kieckegard
 */
public class FactoryConnection
{
    private String url = "";
    private String username;
    private String password;
    private static Connection conn;
    
    public Connection getConnection() throws IOException, URISyntaxException, SQLException{
        Properties prop = new Properties();
        prop.load(new FileInputStream(getClass().getResource("/banco/banco.properties").toURI().getPath()));
        if(conn == null)
            conn = DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("username"),prop.getProperty("password"));
        return conn;
            
    }
}
