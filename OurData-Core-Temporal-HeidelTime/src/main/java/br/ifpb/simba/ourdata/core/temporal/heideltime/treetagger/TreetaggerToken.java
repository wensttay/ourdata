package br.ifpb.simba.ourdata.core.temporal.heideltime.treetagger;

/**
 * This class is a example of Token for
 * {@link br.ifpb.simba.ourdata.core.temporal.heideltime.treetagger.TreetaggerTokenHandler}
 * and {@link br.ifpb.simba.ourdata.core.temporal.heideltime.treetagger.TreetaggerEngine}.
 * 
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 01:39:30
 */
public class TreetaggerToken {

    private String token;
    private String pos;
    private String lemma;

    public TreetaggerToken(String token, String pos, String lemma) {
        this.token = token;
        this.pos = pos;
        this.lemma = lemma;
    }

    @Override
    public String toString() {
        return "TreetaggerToken{" + "token=" + token + ", pos=" + pos + ", lemma=" + lemma + '}';
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the pos
     */
    public String getPos() {
        return pos;
    }

    /**
     * @param pos the pos to set
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     * @return the lemma
     */
    public String getLemma() {
        return lemma;
    }

    /**
     * @param lemma the lemma to set
     */
    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

}
