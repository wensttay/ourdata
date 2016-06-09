
package br.ifpb.simba.ourdata.dao;

/**
 * This class is a abstrat GeneriBdDao with the DaoRelation interface
 *
 * @author Wensttay
 * @param <T> Type of First Object ID
 * @param <I> Type of Secound Object ID
 */
public abstract class GenericRelationBdDao<T, I> extends GenericBdDao implements DaoRelation<T, I>
{
}
