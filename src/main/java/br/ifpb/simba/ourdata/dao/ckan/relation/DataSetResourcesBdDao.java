
package br.ifpb.simba.ourdata.dao.ckan.relation;

import br.ifpb.simba.ourdata.dao.GenericRelationBdDao;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class that know how CRUD a relation between DataSet and Resource into a JDBC
 *
 * @author Wensttay
 */
public class DataSetResourcesBdDao extends GenericRelationBdDao<String, String>
{
    /**
     * This constructor create a DataSetResourcesBdDao using the default
     * properties_path
     * 'PROPERTIES_PATH_DEFAULT' to JDBC connection
     */
    public DataSetResourcesBdDao()
    {
    }

    /**
     * This constructor create a DataSetResourcesBdDao using the properties_path
     * passed to JDBC connection
     *
     * @param properties_path The path will be used to JDBC connection
     */
    public DataSetResourcesBdDao(String properties_path)
    {
        super.setProperties_path(properties_path);
    }

    /**
     * Method to insert a relation between DataSet and Resource type into a JDBC
     *
     * @param id Dataset ID
     * @param otherId Resourse ID
     *
     * @return A boolean that means: true = inserted with sucess, false = not
     * insert with sucess or inserssion is not possible.
     */
    @Override
    public boolean insert(String id, String otherId)
    {
        try
        {
            conectar();
            String sql = "INSERT INTO DATASET_RESOURCE(id_dataset, id_resource) values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            int i = 1;
            ps.setString(i++, id);
            ps.setString(i++, otherId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        } finally
        {
            desconectar();
        }
        return false;
    }
}
