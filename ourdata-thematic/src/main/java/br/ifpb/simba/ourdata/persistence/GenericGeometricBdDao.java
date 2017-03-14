
package br.ifpb.simba.ourdata.persistence;

/**
 * This class is a abstrat GeneriBdDao with the DaoGeometric interface
 *
 * @author Wensttay
 * @param <T> Type of Object
 * @param <I> Type of T's ID
 */
public abstract class GenericGeometricBdDao<T, I> extends GenericBdDao implements DaoGeometric<T, I>{
}
