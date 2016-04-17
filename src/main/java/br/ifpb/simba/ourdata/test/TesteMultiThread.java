/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.dao.thread.AtualizadorDeBanco;

/**
 *
 * @author Wensttay
 */
public class TesteMultiThread {
    final static String url = "http://dados.gov.br/";
    
    public static void main(String[] args) {
        (new Thread(new AtualizadorDeBanco(url))).start();
        
//        int i = 0;
//        while(true){
//            System.out.println(++i);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//        }
    }
}
