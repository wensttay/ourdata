
package br.ifpb.simba.ourdata.dao;

/**
 * Interface of methods to CRUD Ckan entities
 *
 * @author Wensttay
 */
public interface Dao<T, I>{
    /**
     * Method to insert something on some secure repository
     *
     * @param obj Object to be inserted
     *
     * @return True = Sucess / False = Fail
     */
    public boolean insert( T obj );

    /**
     * Method to update something on some secure repository
     *
     * @param obj Object to be updated
     *
     * @return True = Sucess / False = Fail
     */
    public boolean update( T obj );

    /**
     * Method to insert or update something on some secure repository
     *
     * @param obj Object to be inserted or updated
     */
    public void insertOrUpdate( T obj );

    /**
     * Method to verify if exist something on some secure repository
     *
     * @param id Id of Object to be verified
     *
     * @return True = Exits / False = No Exists
     */
    public boolean exist( I id );
}
