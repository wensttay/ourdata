package br.ifpb.simba.ourdata.core.temporal.heideltime.treetagger;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.annolab.tt4j.TreeTaggerException;

/**
 * A simple example of Treetagger Project.
 *
 * @see TreetaggerEngine
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 28/03/2017, 18:34:44
 */
public class TreetaggerSimpleExample {

    public static void main(String[] args) {

        // Define the treetaggerHomePath, where the Treetagger are installed.
        String treetaggerHomePath = "/home/wensttay/ProgramFiles/tree-tagger-linux-3.2.1";

        // Define the treetageerModelPath, where there is a Treetagger lib.
        String treetageerModelPath = treetaggerHomePath + "/lib/english-utf8.par";

        // Define the text to be processed.
        String text = "I worked there from 2014 to 2017";

        // Create a treetaggetEngine.
        TreetaggerEngine treetaggerEngine = new TreetaggerEngine(treetaggerHomePath, treetageerModelPath);

        // Seting the variables.
        treetaggerEngine.setTreetaggerHomePath(treetaggerHomePath);
        treetaggerEngine.setTreetaggerModelPath(treetageerModelPath);

        try {
            // Processing the text.
            treetaggerEngine.process(text);
        } catch (IOException | TreeTaggerException ex) {
            Logger.getLogger(TreetaggerSimpleExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
