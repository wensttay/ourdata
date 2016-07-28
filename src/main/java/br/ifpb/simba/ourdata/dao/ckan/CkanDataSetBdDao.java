
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.DataSetResourcesBdDao;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that know how CRUD a CkanDataSet type into a JDBC
 *
 * @author Wensttay, Pedro Arthur
 */
public class CkanDataSetBdDao extends GenericObjectBdDao<CkanDataset, String>{
    private DataSetResourcesBdDao dataSetResourcesBdDao;
    private CkanResourceBdDao ckanResourceBdDao;

    /**
     * This constructor create a CkanDataSetBdDao using the default
     * properties_path
     * 'PROPERTIES_PATH_DEFAULT' to JDBC connection
     */
    public CkanDataSetBdDao(){
    }

    /**
     * This constructor create a CkanDataSetBdDao using the properties_path
     * passed to JDBC connection
     *
     * @param properties_path The path will be used to JDBC connection
     */
    public CkanDataSetBdDao( String properties_path ){
        super.setProperties_path(properties_path);
    }

    /**
     * Method to insert a CkanDataSet type into a JDBC
     *
     * @param obj CkanDataSet that need be save into a JDBC
     *
     * @return A boolean that means: true = inserted with sucess, false = not
     * insert with sucess or inserssion is not possible.
     */
    @Override
    public boolean insert( CkanDataset obj ){
        try{
            conectar();
            StringBuilder sql = new StringBuilder("INSERT INTO dataset(");
            sql.append("id, author, notes, title, name, metadata_created, metadata_modified) ");
            sql.append("VALUES(?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement ps = getConnection().prepareStatement(sql.toString());

            int i = 1;
            ps.setString(i++, obj.getId());
            ps.setString(i++, obj.getAuthor());
            ps.setString(i++, obj.getNotes());
            ps.setString(i++, obj.getTitle());
            ps.setString(i++, obj.getName());
            ps.setTimestamp(i++, obj.getMetadataCreated());
            ps.setTimestamp(i++, obj.getMetadataModified());

            return ps.executeUpdate() != 0;

        } catch ( URISyntaxException | IOException | SQLException | ClassNotFoundException ex ){
            ex.printStackTrace();
        } finally{
            desconectar();
        }
        return false;

    }

    /**
     * Method to update a CkanDataSet type into the JDBC
     *
     * @param obj CkanDataset type that need to be updated to the JDBC
     *
     * @return A boolean that means: true = updated with sucess, false = not
     * uptated with sucess or the update is not possible.
     */
    @Override
    public boolean update( CkanDataset obj ){
        try{
            conectar();
            StringBuilder sql = new StringBuilder("UPDATE dataset SET author = ?, notes = ?, title = ?,");
            sql.append("name = ?, metadata_created = ?, metadata_modified = ? WHERE id = ?");
            PreparedStatement ps = getConnection().prepareStatement(sql.toString());

            int i = 1;
            ps.setString(i++, obj.getAuthor());
            ps.setString(i++, obj.getNotes());
            ps.setString(i++, obj.getTitle());
            ps.setString(i++, obj.getName());
            ps.setString(i++, obj.getId());
            ps.setTimestamp(i++, obj.getMetadataCreated());
            ps.setTimestamp(i++, obj.getMetadataModified());

            return (ps.executeUpdate() != 0);

        } catch ( URISyntaxException | IOException | SQLException | ClassNotFoundException ex ){
            ex.printStackTrace();
        } finally{
            desconectar();
        }
        return false;
    }

    /**
     * Method to verify if exists a some CkanDataSet with the same id into JDBC
     *
     * @param id String type that represent a id of the CkanDataSet as want to
     * be persisted
     *
     * @return A boolean that means: true = exists a CkanDataSet saved with this
     * ID, false = not exist CkanDataSet saved with this ID
     */
    @Override
    public boolean exist( String id ){
        try{
            conectar();

            String sql = "SELECT * FROM DATASET WHERE ID = ?";

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, id);

            return (ps.executeQuery().next());

        } catch ( URISyntaxException | IOException | SQLException | ClassNotFoundException ex ){
            ex.printStackTrace();
        } finally{
            desconectar();
        }
        return false;
    }

    /**
     * This method try verify if the CkanDataSet exists in JDBC, if exists, if
     * exists this methos verify if the CkanDataSet wants to be updated in
     * answer possitive he do this, case this Ckan not exists method insert the
     * same in JDBC.
     *
     * @param obj The CkanDataSet objet which wants to be inserted or updated
     * into JDBC
     */
    @Override
    public void insertOrUpdate( CkanDataset obj ){
        if ( exist(obj.getId()) ){
            Timestamp timestampModified = getTimestampModified(obj.getId());
            if ( timestampModified != null && obj.getMetadataModified().after(timestampModified) ){
                System.out.println("Houve modificação no dataset " + obj.getId());
                insertOrUpdateAtributes(obj);
            }
        } else{
            insert(obj);
            insertOrUpdateAtributes(obj);
        }
    }

    /**
     * This method is for insert or update others atributes inside the
     * CkanDataset in JDBC
     *
     * @param obj CkanDataset type with the atributes which want to be inserted
     * or updated in JDBC
     */
    private void insertOrUpdateAtributes( CkanDataset obj ){
        List<CkanResource> auxListResource = obj.getResources();

        if ( auxListResource == null ){
            auxListResource = new ArrayList<>();
        }

        for ( CkanResource ckanResource : auxListResource ){
            getCkanResourceBdDao().insertOrUpdate(ckanResource);
            getDataSetResourcesBdDao().insert(obj.getId(), ckanResource.getId());
        }
    }

    /**
     * This methor return a Timestamp which indicates the last modified date
     *
     * @param id String type that represent a id of the CkanDataSet as want to
     * be persisted
     *
     * @return
     */
    private Timestamp getTimestampModified( String id ){
        try{
            conectar();
            String sql = "SELECT METADATA_MODIFIED FROM DATASET WHERE ID = ?";

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getTimestamp("METADATA_MODIFIED");
        } catch ( URISyntaxException | IOException | SQLException | ClassNotFoundException ex ){
            ex.printStackTrace();
            ex.printStackTrace();
        } finally{
            desconectar();
        }
        return null;
    }

    /**
     * Method responsable to instacing the CkanResourceBdDao
     *
     * @return A CkanResourceBdDao instaced
     */
    private CkanResourceBdDao getCkanResourceBdDao(){
        if ( ckanResourceBdDao == null ){
            ckanResourceBdDao = new CkanResourceBdDao(getProperties_path());
        }
        return this.ckanResourceBdDao;
    }

    /**
     * Method responsable to instacing the DataSetResourcesBdDao
     *
     * @return A DataSetResourcesBdDao instaced
     */
    private DataSetResourcesBdDao getDataSetResourcesBdDao(){
        if ( dataSetResourcesBdDao == null ){
            dataSetResourcesBdDao = new DataSetResourcesBdDao();
        }
        return dataSetResourcesBdDao;
    }

    @Override
    public List<CkanDataset> getAll(){
        List<CkanDataset> ckanDatasets = new ArrayList<>();
        try{
            conectar();
            String sql = "SELECT * FROM dataset";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while ( rs.next() ){                
                CkanDataset dataset = fill(rs);
                if(dataset != null){
                    ckanDatasets.add(dataset);
                }
            }
            return ckanDatasets;
            
        } catch ( URISyntaxException | IOException | SQLException | ClassNotFoundException ex ){
            ex.printStackTrace();
        }finally{
            desconectar();
        }
        
        return new ArrayList<>();
    }

    @Override
    public CkanDataset fill( ResultSet rs ){
        try{
            CkanDataset ckanDataset = new CkanDataset();
            ckanDataset.setId(rs.getString("id"));
            ckanDataset.setAuthor(rs.getString("author"));
            ckanDataset.setNotes(rs.getString("notes"));
            ckanDataset.setName(rs.getString("name"));
            ckanDataset.setMetadataCreated(rs.getTimestamp("metadata_created"));
            ckanDataset.setMetadataModified(rs.getTimestamp("metadata_modified"));
            return ckanDataset;
        } catch ( SQLException ex ){
            ex.printStackTrace();
        }
        return null;
    }
}
