package br.ifpb.simba.ourdata.heideltime;

import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import de.unihd.dbs.uima.annotator.heideltime.resources.Language;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;

/**
 * A simple example of HeidelTime Project.
 *
 * @see HeidelTimeEngine
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 28/03/2017, 18:23:27
 */
public class HeidelTimeSimpleExample {

    public static void main(String[] args) {
        
        // Define a configPropsPath with configs about this project.
        // For this to work is necessary to be changed the attribute "treeTaggerHome"
        // in this config.props to the Treetagger's Path instalation or to define
        // the same into "TREE_TAGGER_HOME" environment variable.
        String configPropsPath = System.getProperty("user.dir") + File.separator + "config.props";
        
        // Define the text to be processed
        String text = "Yeah yeah yeah. I'll pay (yeah yeah yeah). When tomorrow. "
                + "Tomorrow comes today. Gorillaz - 15 de jan de 2010";
//        String text = "Eu ainda sinto o gosto. Da noite passada. Que parecia não ter fim. "
//                + "Espero que hoje. Seja melhor do que ontem. Capital Inicial - 23 de ago de 2014";
        
        // Create a HeidelTimeEngine with the configPropsPath configured to expecif Language process.
        HeidelTimeEngine heidelTimeEngine = new HeidelTimeEngine(configPropsPath, Language.ENGLISH);
//        HeidelTimeEngine heidelTimeEngine = new HeidelTimeEngine(configPropsPath, Language.PORTUGUESE);
        
        List<HeidelTimeToken> results = new ArrayList<>();
        try {
            // Execute the process and add all into the 'results' list.
            results.addAll(heidelTimeEngine.process(text, new Date()));
        } catch (JDOMException | IOException | DocumentCreationTimeMissingException ex) {
            Logger.getLogger(HeidelTimeSimpleExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Show all results.
//        System.out.println(">>> Results:");
//        for (HeidelTimeToken proces : results) {
//            System.out.println(proces.toString());
//        }
        System.out.println(results.toString());
    }
}
