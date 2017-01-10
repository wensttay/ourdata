package br.ifpb.simba.ourdata.test;

/**
 *
 * @author Pedro Arthur
 */
public class AtualizadorDBTestEUA {

    public static final String url = "http://www.data.gov/";

    public static void main(String[] args) {
        (new Thread(new AtualizadorDBThread(url, "banco/bancoEua.properties"))).start();
    }
}
