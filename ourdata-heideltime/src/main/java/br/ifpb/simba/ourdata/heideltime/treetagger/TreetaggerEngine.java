package br.ifpb.simba.ourdata.heideltime.treetagger;

import java.io.IOException;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;

/**
 * Class responsable to manage the Treetagger processes.
 *
 * @see TreetaggerTokenHandler
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 28/03/2017, 18:42:29
 */
public class TreetaggerEngine {

    private String treetaggerHomePath;
    private String treetaggerModelPath;
    private TreetaggerTokenHandler treetaggerTokenHandler;

    public TreetaggerEngine(String treetaggerHome, String treetaggerModel) {
        this.treetaggerHomePath = treetaggerHome;
        this.treetaggerModelPath = treetaggerModel;
        this.treetaggerTokenHandler = new TreetaggerTokenHandler();
    }

    /**
     * Process a text using {@link org.annolab.tt4j.TreeTaggerWrapper}.
     *
     * @param text Text to be processed.
     *
     * @throws IOException if the model can not be found.
     * @throws TreeTaggerException if there is a problem communication with
     * TreeTagger.
     */
    public void process(String text) throws IOException, TreeTaggerException {
        System.setProperty("treetagger.home", getTreetaggerHomePath());
        TreeTaggerWrapper treeTaggerWrapper = new TreeTaggerWrapper();
        treeTaggerWrapper.setModel(getTreetaggerModelPath());
        treeTaggerWrapper.setHandler(getTreetaggerTokenHandler());
        treeTaggerWrapper.process(text.split(" "));
    }

    /**
     * @return the treetaggerHomePath
     */
    public String getTreetaggerHomePath() {
        return treetaggerHomePath;
    }

    /**
     * @param treetaggerHomePath the treetaggerHomePath to set
     */
    public void setTreetaggerHomePath(String treetaggerHomePath) {
        this.treetaggerHomePath = treetaggerHomePath;
    }

    /**
     * @return the treetaggerModelPath
     */
    public String getTreetaggerModelPath() {
        return treetaggerModelPath;
    }

    /**
     * @param treetaggerModelPath the treetaggerModelPath to set
     */
    public void setTreetaggerModelPath(String treetaggerModelPath) {
        this.treetaggerModelPath = treetaggerModelPath;
    }

    /**
     * @return the treetaggerTokenHandler
     */
    public TreetaggerTokenHandler getTreetaggerTokenHandler() {
        return treetaggerTokenHandler;
    }

    /**
     * @param treetaggerTokenHandler the treetaggerTokenHandler to set
     */
    public void setTreetaggerTokenHandler(TreetaggerTokenHandler treetaggerTokenHandler) {
        this.treetaggerTokenHandler = treetaggerTokenHandler;
    }

}
