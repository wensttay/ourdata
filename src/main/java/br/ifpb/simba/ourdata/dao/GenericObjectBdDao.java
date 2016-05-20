/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao;

/**
 *
 * @author Wensttay
 * @param <T>
 */
public abstract class GenericObjectBdDao <T, I> extends GenericBdDao implements Dao<T, I>{
    
    public GenericObjectBdDao() {
        super(PROPERTIES_PATH_DEFAULT);
    }
    
}
