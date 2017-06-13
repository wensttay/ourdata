/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.core.temporal.heideltime.treetagger;

import br.ifpb.simba.ourdata.core.temporal.heideltime.path.EnvironmentVariablesUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 */
public class TreetaggerEngineTest {

    private Properties prop;
    private String treetaggerHomePath;
    private String treetageerModelPath;
    private TreetaggerEngine instance;

    public TreetaggerEngineTest(){
         prop = new Properties();
    }

    @Before
    public void setUp() throws FileNotFoundException, IOException {
        prop.load(new FileInputStream(System.getProperty("user.dir") + File.separator + "config.props"));
        treetaggerHomePath = EnvironmentVariablesUtils.source(prop.getProperty("treeTaggerHome"));
        treetageerModelPath = treetaggerHomePath
                + File.separator + "lib"
                + File.separator + "english-utf8.par";
        instance = new TreetaggerEngine(treetaggerHomePath, treetageerModelPath);
    }

    /**
     * Test of process method, of class TreetaggerEngine.
     */
    @Test
    public void testProcess() throws Exception {
        System.out.println("process");
        String text = "I worked there from 2014 to 2017";
        instance.process(text);
    }

}
