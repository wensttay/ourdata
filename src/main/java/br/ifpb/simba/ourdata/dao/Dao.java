package br.ifpb.simba.ourdata.dao;

import java.sql.ResultSet;
import java.util.List;

/**
 * Interface of methods to CRUD Ckan entities
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public interface Dao<T, I> {

    /**
     * Method to insert something on some secure repository
     *
     * @param obj Object to be inserted
     *
     * @return True = Sucess / False = Fail
     */
    public boolean insert(T obj);

    /**
     * Method to update something on some secure repository
     *
     * @param obj Object to be updated
     *
     * @return True = Sucess / False = Fail
     */
    public boolean update(T obj);

    /**
     * Method to insert or update something on some secure repository
     *
     * @param obj Object to be inserted or updated
     */
    public void insertOrUpdate(T obj);

    /**
     * Method to verify if exist something on some secure repository
     *
     * @param id Id of Object to be verified
     *
     * @return True = Exits / False = No Exists
     */
    public boolean exist(I id);

    /**
     * Method to list all of type T on some secure repository
     *
     * @return A List with all T objects on secure repository
     */
    public List<T> getAll();

    /**
     * Method to fill a T object with a ResultSet pass of param
     *
     * @param rs ResultSet used to fill a T
     */
    public T fill(ResultSet rs);
}
