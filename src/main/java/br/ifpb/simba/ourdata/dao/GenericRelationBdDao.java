/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao;

/**
 *
 * @author Wensttay
 */
public abstract class GenericRelationBdDao <T, I> extends GenericBdDao implements DaoRelation<T, I>{

    public GenericRelationBdDao() {
        super(PROPERTIES_PATH_DEFAULT);
    }

}
