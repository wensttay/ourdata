/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

/**
 *
 * @author Wensttay
 */
public interface Reader <T,I> {
    T build (I url);
    void print (I url);
}
