
package br.ifpb.simba.ourdata.dao;

/**
 * Interface of methods to CRUD Ckan relations
 *
 * @author Wensttay
 * @param <T> Type of First Object ID
 * @param <I> Type of Secound Object ID
 */
public interface DaoRelation<T, I>{
    /**
     * Method to insert a relation between a T type and I type on some secure
     * repository
     *
     * @param obj First Object of relation
     * @param id Secound Object of relation
     *
     * @return True = Sucess / False = Fail
     */
    boolean insert( T id, I otherId );
}
