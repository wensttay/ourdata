/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import java.util.List;

/**
 *
 * @author kieckegard
 */
public interface Dao<T,J>
{
    boolean insert(T obj);
    T get(J obj);
    List<T> getAll();
}
