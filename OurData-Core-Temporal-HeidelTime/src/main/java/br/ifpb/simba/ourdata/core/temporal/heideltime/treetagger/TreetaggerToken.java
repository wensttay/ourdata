package br.ifpb.simba.ourdata.core.temporal.heideltime.treetagger;

import org.annolab.tt4j.TokenHandler;

/**
 * This class is a example of TokenHandler for
 * {@link br.ifpb.simba.ourdata.core.temporal.heideltime.treetagger.TreetaggerEngine}.
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 28/03/2017, 19:39:05
 */
public class TreetaggerToken implements TokenHandler<String> {

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
        System.out.println("Token -> " + token);
        System.out.println("Pos -> " + pos);
        System.out.println("Lemma -> " + lemma);
        System.out.println("");
    }
}
