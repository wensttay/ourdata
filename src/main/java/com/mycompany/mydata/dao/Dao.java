/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;


/**
 *
 * @author kieckegard
 */
public interface Dao<T, I> {

    boolean insert(T obj);
    
//    boolean update(T obj);
//    
//    boolean insertOrUpdate(T obj);
    
}
