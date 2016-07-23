
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.dao.ckan.CkanDataSetBdDao;
import br.ifpb.simba.ourdata.reader.TextColor;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.exceptions.CkanException;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import java.util.List;

/**
 *
 * @author Wensttay
 */
public class AtualizadorDBThread extends Thread{
    public static final long INTERNAL_TIME_REPEAT_DEFAULT = 5000;
    private String url;
    private CkanClient cc;
    private CkanDataSetBdDao cdsbd;
    private List<String> datasetlist;
    private long intervalTimeRepeat;
    private int repeatNumber;

    public AtualizadorDBThread( String url ){
        this.url = url;
        cc = new CkanClient(this.url);
        cdsbd = new CkanDataSetBdDao();
        datasetlist = cc.getDatasetList();
        intervalTimeRepeat = INTERNAL_TIME_REPEAT_DEFAULT;
        repeatNumber = 1;
    }

    public AtualizadorDBThread( String url, String dbProp ){
        this.url = url;
        cc = new CkanClient(this.url);
        cdsbd = new CkanDataSetBdDao(dbProp);
        datasetlist = cc.getDatasetList();
        intervalTimeRepeat = INTERNAL_TIME_REPEAT_DEFAULT;
        repeatNumber = 1;
    }

    public AtualizadorDBThread( String url, Long intervalTime ){
        this.url = url;
        cc = new CkanClient(this.url);
        cdsbd = new CkanDataSetBdDao();
        datasetlist = cc.getDatasetList();
        this.intervalTimeRepeat = intervalTime;
        repeatNumber = 1;
    }

    public AtualizadorDBThread( String url, Long intervalTime, int repeatNumber ){
        this.url = url;
        cc = new CkanClient(this.url);
        cdsbd = new CkanDataSetBdDao();
        datasetlist = cc.getDatasetList();
        this.intervalTimeRepeat = intervalTime;
        this.repeatNumber = repeatNumber;
    }

    @Override
    public void run(){
        for ( int i = 0; i < repeatNumber; i++ ){
            for ( int j = 0; j < datasetlist.size(); j++ ){
                try{
                    System.out.println("Index DataSet: " + j);
                    CkanDataset dataset = cc.getDataset(datasetlist.get(j));
                    cdsbd.insertOrUpdate(dataset);

                } catch ( CkanException ex ){
                    System.out.println("Acesso negado.");
                }
            }
            try{
                Thread.sleep(intervalTimeRepeat);
            } catch ( InterruptedException ex ){
                System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            }
        }
    }
}
