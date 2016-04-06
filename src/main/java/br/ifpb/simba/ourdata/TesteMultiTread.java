/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wensttay
 */
public class TesteMultiTread {
    public static void main(String[] args) {
        (new Thread(new Main())).start();
        
        int i = 0;
        while(true){
            System.out.println(++i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
