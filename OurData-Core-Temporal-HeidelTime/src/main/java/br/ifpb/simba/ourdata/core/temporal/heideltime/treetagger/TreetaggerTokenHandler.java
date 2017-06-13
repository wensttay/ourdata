package br.ifpb.simba.ourdata.core.temporal.heideltime.treetagger;

import java.util.ArrayList;
import java.util.List;
import org.annolab.tt4j.TokenHandler;

/**
 * This class is a example of TokenHandler for
 * {@link br.ifpb.simba.ourdata.core.temporal.heideltime.treetagger.TreetaggerEngine}.
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 28/03/2017, 19:39:05
 */
public class TreetaggerTokenHandler implements TokenHandler<String> {

    private List<TreetaggerToken> treetaggerTokens;

    public TreetaggerTokenHandler() {
        super();
        this.treetaggerTokens = new ArrayList<>();
    }

    /**
     * This method is auto called to print all the tokens values when the method
     * {@link org.annolab.tt4j.TreeTaggerWrapper#process(java.util.Collection)}
     * is invoked.
     *
     * @param token the one of the token objects passed to
     * {@link org.annolab.tt4j.TreeTaggerWrapper#process(java.util.Collection)}.
     * @param pos the Part-of-Speech tag as produced by TreeTagger or null.
     * @param lemma the lemma as produced by TreeTagger or null.
     */
    @Override
    public void token(String token, String pos, String lemma) {
        this.treetaggerTokens.add(new TreetaggerToken(token, pos, lemma));
    }

    /**
     * List with TreetaggerTokens result of process and clear the Handler cache.
     * 
     * @return List with TreetaggerTokens results.
     */
    public List<TreetaggerToken> getTreetaggerTokens() {
        List<TreetaggerToken> aux = new ArrayList<>(treetaggerTokens);
        treetaggerTokens.clear();
        return aux;
    }
}
