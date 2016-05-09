/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao;

import br.ifpb.simba.ourdata.geo.KeyWord;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author wensttay
 */
public interface DaoGeometric<T,I> {
    boolean insert(T obj);
    List<T> getAll();
    T preencherObjeto(ResultSet rs);
}
