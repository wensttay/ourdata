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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wensttay
 * @param <T>
 * @param <I>
 */
public abstract class GenericBdDao<T, I> implements Dao<T, I> {

    Connection connection;

    public void conectar() throws URISyntaxException, IOException, SQLException, ClassNotFoundException {
        if (getConnection() != null && !getConnection().isClosed()) {
            return;
        }
        Properties prop = new Properties();
        prop.load(new FileInputStream(getClass().getResource("/banco/banco.properties").toURI().getPath()));

        String nome = prop.getProperty("nome");
        String user = prop.getProperty("user");
        String url = prop.getProperty("url");
        String password = prop.getProperty("password");
        String driver = prop.getProperty("driver");

        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);

    }

    public void desconectar(){
        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                connection.close();
            }
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() throws URISyntaxException, IOException, SQLException, ClassNotFoundException {
        return connection;
    }
}
