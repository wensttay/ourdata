package br.ifpb.simba.ourdata.dao;

/**
 * This class is a abstrat GeneriBdDao with the DaoRelation interface
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 *
 * @param <T> Type of First Object ID
 * @param <I> Type of Secound Object ID
 */
public abstract class GenericRelationBdDao<T, I> extends GenericBdDao implements DaoRelation<T, I> {
}
