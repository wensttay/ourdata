package br.ifpb.simba.ourdata.db.postgresql.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * A generic class for JDBC connections with Geometry Values
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public abstract class GenericBdDao {

    public static final String PROPERTIES_PATH_DEFAULT = File.separator + 
            "banco" + File.separator + "banco.properties";

    private String propertiesPath;
    private String user;
    private String url;
    private String password;
    private String driver;
    private Connection connection;

    /**
     * This constructor create a GenericBdDao using the propertiesPath passed
     * to JDBC connection
     *
     * @param propertiesPath The path will be used to JDBC connection
     */
    public GenericBdDao(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    /**
     * This constructor create a GenericBdDao using the default propertiesPath
     * 'PROPERTIES_PATH_DEFAULT' to JDBC connection
     */
    public GenericBdDao() {
        this.propertiesPath = PROPERTIES_PATH_DEFAULT;
    }

    /**
     * Create a connection with a JDBC using the propertiesPath atributes
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void conectar() throws URISyntaxException, IOException, SQLException, ClassNotFoundException {
        
        if (getConnection() != null && !getConnection().isClosed()) {
            return;
        }

        Properties prop = new Properties();
        prop.load(new FileInputStream(getClass().getResource(propertiesPath).toURI().getPath()));

        user = prop.getProperty("user");
        url = prop.getProperty("url");
        password = prop.getProperty("password");
        driver = prop.getProperty("driver");

        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);        
    }

    /**
     * This method close the connection with the JDBC
     * <p>
     */
    public void desconectar() {
        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
//            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        }
    }

    /**
     * Return the Connection class in your atual state
     *
     * @return Return the Connection class in your atual state
     *
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Return a properties_path with the atributs to make a conection with JDBC
     * set in this BdDao.
     *
     * @return The String diretory of propertiesPath
     */
    public String getPropertiesPath() {
        return propertiesPath;
    }

    /**
     * Modific the propertiesPath with the atributs to make a conection with
     * JDBC to the path passed as parameter
     *
     * @param properties_path The new path of propertiesPath
     */
    public void setPropertiesPath(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }
}
