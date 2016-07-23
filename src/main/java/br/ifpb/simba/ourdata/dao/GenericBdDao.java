
package br.ifpb.simba.ourdata.dao;

import br.ifpb.simba.ourdata.reader.TextColor;
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
 * @author Wensttay
 */
public abstract class GenericBdDao{
    public static final String PROPERTIES_PATH_DEFAULT = "/banco/banco.properties";

    private String properties_path;
    private String user;
    private String url;
    private String password;
    private String driver;
    private Connection connection;

    /**
     * This constructor create a GenericBdDao using the properties_path passed
     * to JDBC connection
     *
     * @param properties_path The path will be used to JDBC connection
     */
    public GenericBdDao( String properties_path ){
        this.properties_path = properties_path;
    }

    /**
     * This constructor create a GenericBdDao using the default properties_path
     * 'PROPERTIES_PATH_DEFAULT' to JDBC connection
     */
    public GenericBdDao(){
        this.properties_path = PROPERTIES_PATH_DEFAULT;
    }

    /**
     * Create a connection with a JDBC using the properties_path atributes
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void conectar() throws URISyntaxException, IOException, SQLException, ClassNotFoundException{
        if ( getConnection() != null && !getConnection().isClosed() ){
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
        (( org.postgresql.PGConnection ) connection).addDataType("geometry", Class.forName("org.postgis.PGgeometry"));
        (( org.postgresql.PGConnection ) connection).addDataType("box3d", Class.forName("org.postgis.PGbox3d"));
    }

    /**
     * This method close the connection with the JDBC
     * <p>
     */
    public void desconectar(){
        try{
            if ( getConnection() != null && !getConnection().isClosed() ){
                connection.close();
            }
        } catch ( URISyntaxException | IOException | SQLException | ClassNotFoundException ex ){
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        }
    }

    /**
     * Return the Connection class in your atual state
     *
     * @return Return the Connection class in your atual state
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Connection getConnection() throws URISyntaxException, IOException, SQLException, ClassNotFoundException{
        return connection;
    }

    /**
     * Return a properties_path with the atributs to make a conection with JDBC
     * set in this BdDao.
     *
     * @return The String diretory of properties_path
     */
    public String getProperties_path(){
        return properties_path;
    }

    /**
     * Modific the properties_path with the atributs to make a conection with
     * JDBC to the path passed as parameter
     *
     * @param properties_path The new path of properties_path
     */
    public void setProperties_path( String properties_path ){
        this.properties_path = properties_path;
    }
}
