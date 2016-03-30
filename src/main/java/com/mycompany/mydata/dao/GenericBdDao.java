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
 * @author wensttay
 * @param <T>
 * @param <I>
 */
public abstract class GenericBdDao<T, J> implements Dao<T, J> {

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

    public void desconectar() throws SQLException, URISyntaxException, IOException, ClassNotFoundException {
        if (getConnection() != null && !getConnection().isClosed()) {
            connection.close();
        }
    }

    public Connection getConnection() throws URISyntaxException, IOException, SQLException, ClassNotFoundException {
        if(connection == null || connection.isClosed())
            conectar();
        return connection;
    }
}
