
package br.ifpb.simba.ourdata.dao;

/**
 * This class is a abstrat GeneriBdDao with the Dao interface
 *
 * @author Wensttay
 * @param <T> Type of Object
 * @param <I> Type of T's ID
 */
public abstract class GenericObjectBdDao<T, I> extends GenericBdDao implements Dao<T, I>
{
}
