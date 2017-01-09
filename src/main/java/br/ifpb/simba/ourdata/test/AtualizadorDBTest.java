package br.ifpb.simba.ourdata.test;

/**
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class AtualizadorDBTest {

    final static String url = "http://dados.gov.br/";

    public static void main(String[] args) {
        (new Thread(new AtualizadorDBThread(url))).start();
    }
}
