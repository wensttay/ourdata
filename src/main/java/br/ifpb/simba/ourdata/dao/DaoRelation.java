/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao;

/**
 *
 * @author wensttay
 */
public interface DaoRelation<T, I> {

    boolean insert(T obj, I id);

//    boolean update(T obj);
//    
//    boolean insertOrUpdate(T obj);
}
