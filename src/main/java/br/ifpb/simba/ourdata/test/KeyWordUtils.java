/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.geo.KeyWord;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class KeyWordUtils {

    /**
     * Return a list with a 'lite Version' of result seach KeyWords's resource.
     * This list has a same KeyWords of result search, but KeyWords with same
     * place are joined in the same KeyWord
     * @param keyWords List with KeyWord
     * @return 
     */
    public static List<KeyWord> getLiteVersion(List<KeyWord> keyWords) {
        List<KeyWord> liteVersion = new ArrayList<>();

        if (keyWords != null && !keyWords.isEmpty()) {
            for (KeyWord keyWord : keyWords) {
                boolean exist = false;
                for (KeyWord liteVersionAux : liteVersion) {
                    if (liteVersionAux.equals(keyWord)) {
                        liteVersionAux.setRepeatNumber(keyWord.getRepeatNumber() + liteVersionAux.getRepeatNumber());
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    liteVersion.add(keyWord);
                }
            }
        }
        return liteVersion;
    }
}
