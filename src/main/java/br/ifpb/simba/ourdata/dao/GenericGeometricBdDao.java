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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author wensttay
 * @param <T>
 * @param <I>
 */
public abstract class GenericGeometricBdDao<T, I> implements Dao<T, I> {

    private String properties_path;
    private String user;
    private String url;
    private String password;
    private String driver;
    private Connection connection;

    public GenericGeometricBdDao(String properties_path) {
        this.properties_path = properties_path;
    }
    
    public void conectar() throws URISyntaxException, IOException, SQLException, ClassNotFoundException {
        if (getConnection() != null && !getConnection().isClosed()) {
            return;
        }
        Properties prop = new Properties();
        prop.load(new FileInputStream(getClass().getResource(properties_path).toURI().getPath()));

        user = prop.getProperty("user");
        url = prop.getProperty("url");
        password = prop.getProperty("password");
        driver = prop.getProperty("driver");

        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);
        ((org.postgresql.PGConnection)connection).addDataType("geometry",Class.forName("org.postgis.PGgeometry"));
        ((org.postgresql.PGConnection)connection).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));
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
