/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.dao.GenericGeometricBdDao;
import br.ifpb.simba.ourdata.entity.KeyTime;
import br.ifpb.simba.ourdata.reader.TextColor;
import br.ifpb.simba.ourdata.entity.Period;
import br.ifpb.simba.ourdata.entity.PeriodTime;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class KeyTimeBdDao extends GenericGeometricBdDao<KeyTime, Integer> {

    /**
     * This constructor create a KeyTimeBdDao using the default properties_path
     * 'PROPERTIES_PATH_DEFAULT' to JDBC connection.
     */
    public KeyTimeBdDao() {
        super();
    }

    /**
     * This constructor create a KeyTimeBdDao using the properties_path passed
     * to JDBC connection
     *
     * @param properties_path The path will be used to JDBC connection
     */
    public KeyTimeBdDao(String properties_path) {
        super.setProperties_path(properties_path);
    }

    @Override
    public boolean insert(KeyTime obj) {
        PreparedStatement ps = null;
        boolean result;

        try {
            StringBuilder sql = new StringBuilder("INSERT INTO resource_time(START_COLUM_NUMBER, END_COLUM_NUMBER, REPEAT_NUMBER");
            sql.append(", ROWS_NUMBER, METADATA_CREATED, START_DATE, END_DATE, ID_RESOURCE)");
            sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            ps = getConnection().prepareStatement(sql.toString());

            int i = 1;
            ps.setInt(i++, obj.getPeriod().getStartDate().getCol());
            ps.setInt(i++, obj.getPeriod().getEndDate().getCol());
            ps.setInt(i++, obj.getRepeatNumber());
            ps.setInt(i++, obj.getRowsNumber());
            ps.setTimestamp(i++, obj.getMetadataCreated());
            ps.setTimestamp(i++, new Timestamp(obj.getPeriod().getStartDate().getDate().getTime()));
            ps.setTimestamp(i++, new Timestamp(obj.getPeriod().getEndDate().getDate().getTime()));
            ps.setString(i++, obj.getIdResource());

            result = (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
//            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
//            System.out.println(TextColor.ANSI_RED.getCode() + "Já existe no banco");
            ex.printStackTrace();
            result = false;
        }

        try {
            ps.close();
        } catch (Exception ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        }

        return result;
    }

    @Override
    public List<KeyTime> getAll() {
        List<KeyTime> times = new ArrayList<>();

        try {
            conectar();
            String sql = "SELECT * FROM resource_time";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KeyTime p = preencherObjeto(rs);
                if (p != null) {
                    times.add(p);
                }
            }

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        } finally {
            desconectar();
        }
        return times;
    }

    private KeyTime preencherObjeto(ResultSet rs) {
        try {
            KeyTime kt = new KeyTime();
            kt.setRepeatNumber(rs.getInt("REPEAT_NUMBER"));
            kt.setRowsNumber(rs.getInt("ROWS_NUMBER"));
            kt.setMetadataCreated(rs.getTimestamp("METADATA_CREATED"));
            kt.setIdResource(rs.getString("ID_RESOURCE"));

            Period p = new Period();
            p.setStartDate(new PeriodTime(new Date(rs.getTimestamp("START_DATE").getTime()), rs.getInt("START_COLUM_NUMBER")));
            p.setEndDate(new PeriodTime(new Date(rs.getTimestamp("END_DATE").getTime()), rs.getInt("END_COLUM_NUMBER")));
            kt.setPeriod(p);

            return kt;
        } catch (SQLException ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            return null;
        }
    }

    /**
     * This method insert all KeyTimesF passed in a java.util.List; param
     *
     * @param listKeyTimes List with KeyTimes to insert
     *
     * @return
     */
    public boolean insertAll(List<KeyTime> listKeyTimes) {
        long size = listKeyTimes.size();
        long count = 0;
        float porcent = 0;
        float oldPorcent = 0;

        try {
            conectar();
        } catch (Exception ex) {
//            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage
            ex.printStackTrace();
            return false;
        }

        for (KeyTime keyTime : listKeyTimes) {
            if (insert(keyTime)) {
                System.out.println("Inserido Com Sucesso");
            }

            count++;
            porcent = (count * 100) / size;

            if (porcent > oldPorcent + 10) {
                oldPorcent = porcent;
                System.out.println(porcent + " %");
            }
        }

        if (!listKeyTimes.isEmpty()) {
            System.out.println("100.0 %");
        }

        try {
            desconectar();
        } catch (Exception ex) {
//            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        return true;
    }

}
