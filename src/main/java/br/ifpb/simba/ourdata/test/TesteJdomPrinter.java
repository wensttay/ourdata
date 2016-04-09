/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.reader.JdomReader;

/**
 *
 * @author wensttay
 */

public class TesteJdomPrinter {
    final static String url = "http://api.pgi.gov.br/api/1/serie/614.xml";
    
    public static void main(String[] args) {
        JdomReader jdomReader = new JdomReader();
//        jdomReader.print(url);
        jdomReader.printChildrenNames(url);
    }
    
}
