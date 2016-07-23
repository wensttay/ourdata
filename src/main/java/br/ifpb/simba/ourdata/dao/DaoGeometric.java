
package br.ifpb.simba.ourdata.dao;

import java.util.List;

/**
 * Interface of methods to CRUD Geometric entities
 *
 * @author Wensttay
 * @param <T> Type of Object
 * @param <I> Type of T's ID
 */
public interface DaoGeometric<T, I>{
    /**
     * Method to insert something on some secure repository
     *
     * @param obj Object to be inserted
     *
     * @return True = Sucess / False = Fail
     */
    public boolean insert( T obj );

    /**
     * Method to list all of type T on some secure repository
     *
     * @return A List with all T objects on secure repository
     */
    public List<T> getAll();
}
