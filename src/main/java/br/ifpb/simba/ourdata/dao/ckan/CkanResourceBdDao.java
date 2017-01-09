package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import br.ifpb.simba.ourdata.reader.TextColor;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that know how CRUD a CkanResource type into a JDBC
 *
 * @version 1.0
 * @author Pedro Arthur, Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class CkanResourceBdDao extends GenericObjectBdDao<CkanResource, String> {

    /**
     * This constructor create a CkanResourceBdDao using the default
     * properties_path to JDBC connection 'PROPERTIES_PATH_DEFAULT'
     */
    public CkanResourceBdDao() {
    }

    /**
     * This constructor create a CkanResourceBdDao using the properties_path
     * passed to JDBC connection
     *
     * @param properties_path The path will be used to JDBC connection
     */
    public CkanResourceBdDao(String properties_path) {
        super.setProperties_path(properties_path);
    }

    /**
     * Method to insert a CkanResource type into a JDBC
     *
     * @param obj CkanResource that need be save into a JDBC
     *
     * @return A boolean that means: true = inserted with sucess, false = not
     * insert with sucess or inserssion is not possible.
     */
    @Override
    public boolean insert(CkanResource obj) {
        try {
            conectar();
            StringBuilder sql = new StringBuilder("INSERT INTO resource(ID, DESCRIPTION, FORMAT, URL,");
            sql.append("NAME ,ID_DATASET) VALUES(?, ?, ?, ?, ?, ?)");
            PreparedStatement ps = getConnection().prepareStatement(sql.toString());

            int i = 1;
            ps.setString(i++, obj.getId());
            ps.setString(i++, obj.getDescription());
            ps.setString(i++, obj.getFormat());
            ps.setString(i++, obj.getUrl());
            ps.setString(i++, obj.getName());
            ps.setString(i++, obj.getPackageId());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        } finally {
            desconectar();
        }
        return false;
    }

    /**
     * Method to update a CkanResource type into the JDBC
     *
     * @param obj CkanResource type that need to be updated to the JDBC
     *
     * @return A boolean that means: true = updated with sucess, false = not
     * uptated with sucess or the update is not possible.
     */
    @Override
    public boolean update(CkanResource obj) {
        try {
            conectar();
            StringBuilder sql = new StringBuilder("UPDATE RESOURCE SET DESCRIPTION = ?, FORMAT = ?, URL = ?,");
            sql.append("NAME = ?, ID_DATASET = ? WHERE ID = ?");
            PreparedStatement ps = getConnection().prepareStatement(sql.toString());

            int i = 1;
            ps.setString(i++, obj.getDescription());
            ps.setString(i++, obj.getFormat());
            ps.setString(i++, obj.getUrl());
            ps.setString(i++, obj.getName());
            ps.setString(i++, obj.getPackageId());
            ps.setString(i++, obj.getId());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
//            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRROUUUUUUUUUUU");
        } finally {
            desconectar();
        }
        return false;
    }

    /**
     * Method to verify if exists a some CkanResource with the same id into JDBC
     *
     * @param id String type that represent a id of the CkanResource as want to
     * be persisted
     *
     * @return A boolean that means: true = exists a CkanResource saved with
     * this ID, false = not exist CkanDataSet saved with this ID
     */
    @Override
    public boolean exist(String id) {
        try {
            conectar();
            String sql = "SELECT * FROM RESOURCE WHERE ID = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, id);

            return (ps.executeQuery().next());
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        } finally {
            desconectar();
        }
        return false;
    }

    /**
     * This method try verify if the CkanResource exists in JDBC, if exists, if
     * exists this methos verify if the CkanResource wants to be updated in
     * answer possitive he do this, case this Ckan not exists method insert the
     * same in JDBC.
     *
     * @param obj The CkanResource objet which wants to be inserted or updated
     * into JDBC
     */
    @Override
    public void insertOrUpdate(CkanResource obj) {
        if (exist(obj.getId())) {
            update(obj);
        } else {
            insert(obj);
        }

    }

    /**
     * This method return a java.util.List; with all the CkanResource inserted
     * in JDBC
     *
     * @return A list of all CkanResource in JDBC
     */
    @Override
    public List<CkanResource> getAll() {
        try {
            conectar();
            String sql = "SELECT * FROM resource";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<CkanResource> resources = new ArrayList<>();

            while (rs.next()) {
                CkanResource resource = fill(rs);
                if (resource != null) {
                    resources.add(resource);
                }
            }
            return resources;

        } catch (SQLException | URISyntaxException | IOException | ClassNotFoundException ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        } finally {
            desconectar();
        }
        return new ArrayList<>();
    }

    /**
     * This method return a java.util.List; with all the CkanResource inserted
     * in JDBC
     *
     * @return A list of all CkanResource in JDBC with Dataset id
     *
     * @param id Id of Dataset father that wants search
     */
    public List<CkanResource> searchByDatasetId(String id) {
        try {
            conectar();
            String sql = "SELECT * FROM resource WHERE id_dataset = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            int i = 1;
            ps.setString(i++, id);

            ResultSet rs = ps.executeQuery();

            List<CkanResource> resources = new ArrayList<>();

            while (rs.next()) {
                CkanResource resource = fill(rs);
                if (resource != null) {
                    resources.add(resource);
                }
            }
            return resources;

        } catch (SQLException | URISyntaxException | IOException | ClassNotFoundException ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        } finally {
            desconectar();
        }
        return new ArrayList<>();
    }

    @Override
    public CkanResource fill(ResultSet rs) {
        try {
            CkanResource resource = new CkanResource();
            resource.setId(rs.getString("id"));
            resource.setDescription(rs.getString("description"));
            resource.setFormat(rs.getString("format"));
            resource.setUrl(rs.getString("url"));
            resource.setPackageId(rs.getString("id_dataset"));
            return resource;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
