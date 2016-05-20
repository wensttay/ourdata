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
public abstract class GenericGeometricBdDao<T, I> extends GenericBdDao implements DaoGeometric<T, I>{

    public GenericGeometricBdDao(String properties_path) {
        super(properties_path);
    }
}
