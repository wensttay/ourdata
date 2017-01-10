package br.ifpb.simba.ourdata.dao;

/**
 * This class is a abstrat GeneriBdDao with the Dao interface
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 * 
 * @param <T> Type of Object
 * @param <I> Type of T's ID
 */
public abstract class GenericObjectBdDao<T, I> extends GenericBdDao implements Dao<T, I> {
}
