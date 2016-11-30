/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.headerAnality;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class ContainsAllTest {
    
    public static void main(String[] args) {
        
        List<String> pk = new LinkedList<>();
        
        pk.add("1");
        pk.add("2");
        pk.add("3");
        pk.add("4");
        
        List<String> fk = new LinkedList();
        
        fk.add("1");
        fk.add("1");
        fk.add("1");
        fk.add("1");
        fk.add("1");
        fk.add("2");
        fk.add("2");
        fk.add("2");
        fk.add("2");
        fk.add("3");
        fk.add("3");
        fk.add("3");
        fk.add("3");
        fk.add("3");
        fk.add("5");
        
        System.out.println(pk.containsAll(fk));
    }
}
