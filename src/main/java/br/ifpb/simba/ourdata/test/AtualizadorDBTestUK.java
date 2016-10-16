/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

/**
 *
 * @author kieckegard
 */
public class AtualizadorDBTestUK{
    public static final String url = "http://data.gov.uk/";

    public static void main( String[] args ){
        (new Thread(new AtualizadorDBThread(url, "banco/bancoEua.properties"))).start();
    }
}
