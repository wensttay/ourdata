/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity.utils;

import br.ifpb.simba.ourdata.entity.KeyPlace;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wensttay
 */
public class KeyPlaceUtils
{

    /**
     * Return a list with a 'lite Version' of result seach KeyWords's resource.
     * This list has a same KeyWords of result search, but KeyWords with same
     * place are joined in the same KeyPlace
     *
     * @param keyWords List with repeat KeyPlaces
     *
     * @return Filered list, without repeat KeyPlaces
     */
    public static List<KeyPlace> getLiteVersion(List<KeyPlace> keyWords)
    {
        List<KeyPlace> liteVersion = new ArrayList<>();

        if (keyWords != null && !keyWords.isEmpty())
        {
            for (KeyPlace keyWord : keyWords)
            {
                boolean exist = false;
                for (KeyPlace liteVersionAux : liteVersion)
                {
                    if (liteVersionAux.equals(keyWord))
                    {
                        liteVersionAux.setRepeatNumber(keyWord.getRepeatNumber() + liteVersionAux.getRepeatNumber());
                        exist = true;
                        break;
                    }
                }
                if (!exist)
                {
                    liteVersion.add(keyWord);
                }
            }
        }
        return liteVersion;
    }
}
