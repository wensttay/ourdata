/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Pedro Arthur
 */
public class PostgresConnectionProvider {
    
    public static final String PROPERTIES_PATH_DEFAULT = "/banco/banco.properties";
    
    private static String url;
    private static String username;
    private static String password;
    private static String driver;
    
    private static PostgresConnectionProvider provider;
    private BasicDataSource connectionPool;
    
    private PostgresConnectionProvider() {

    }

    public static synchronized PostgresConnectionProvider getInstance() {
        if (provider == null) 
            provider = new PostgresConnectionProvider();
        return provider;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        
        if ( connectionPool == null || connectionPool.isClosed() ){
            try{
                
                Properties prop = new Properties();
                prop.load(new FileInputStream(getClass().getResource(PROPERTIES_PATH_DEFAULT).toURI().getPath()));

                username = prop.getProperty("user");
                url = prop.getProperty("url");
                password = prop.getProperty("password");
                driver = prop.getProperty("driver");
                
                System.out.println(url + " - " + username + " - " + password + " - " + driver);

                connectionPool = new BasicDataSource();
                connectionPool.setUsername(username);
                connectionPool.setPassword(password);
                connectionPool.setDriverClassName(driver);
                connectionPool.setUrl(url);

            } 
            catch (IOException | URISyntaxException io) {
                io.printStackTrace();
            }
            catch ( IndexOutOfBoundsException ex ){
                connectionPool.close();
                return null;
            }

        }

        return connectionPool.getConnection();
    }
}
