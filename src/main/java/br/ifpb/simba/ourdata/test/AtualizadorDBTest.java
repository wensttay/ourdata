/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

/**
 *
 * @author Wensttay
 */
public class AtualizadorDBTest {
    final static String url = "http://dados.gov.br/";
    
    public static void main(String[] args) {
        (new Thread(new AtualizadorDBThread(url))).start();
    }
}
