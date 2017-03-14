/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.thematic.parser;

/**
 *
 * @author Pedro Arthur
 */
public interface Parser<T,E> {
    
    E parse(T obj);
}
