/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.heideltime;

import br.ifpb.simba.ourdata.heideltime.HeidelTimeToken;
import de.unihd.dbs.uima.annotator.heideltime.resources.Language;
import java.io.File;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 */
public class HeidelTimeEngineTest {

    private HeidelTimeEngine heidelTimeEngine;

    public HeidelTimeEngineTest() {
    }

    @Before
    public void setUp() {
        String configPropsPath = System.getProperty("user.dir") + File.separator + "config.props";
        heidelTimeEngine = new HeidelTimeEngine(configPropsPath, Language.ENGLISH);
    }

    /**
     * Test of process method, of class HeidelTimeEngine.
     */
    @org.junit.Test
    public void testProcess() throws Exception {
        System.out.println("process");
        String text = "Yeah yeah yeah. I'll pay (yeah yeah yeah). When tomorrow. "
                + "Tomorrow comes today. Gorillaz - 15 de jan de 2010";
        Date currentData = new Date(System.currentTimeMillis());
        HeidelTimeEngine instance = new HeidelTimeEngine();

        String expResult = "[HeidelTimeToken{id=t1, type=DATE, value=2017-06-14, data=tomorrow}, HeidelTimeToken{id=t2, type=DATE, value=2017-06-14, data=Tomorrow}, HeidelTimeToken{id=t3, type=DATE, value=2017-06-13, data=today}, HeidelTimeToken{id=t4, type=DATE, value=2010, data=2010}]";
        List<HeidelTimeToken> heidelTimeTokens = instance.process(text, currentData);
        String result = heidelTimeTokens.toString();
        assertEquals(expResult, result);
    }

}
