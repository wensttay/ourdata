/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity.utils;

import br.ifpb.simba.ourdata.entity.KeyTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class KeyTimeUtils {
    
    /**
     * Return a list with a 'lite Version' of result seach KeyTimes's resource.
     * This list has a same KeyTimes of result search, but KeyTimes with same
     * place are joined in the same KeyTime
     *
     * @param keyTimes List with repeat KeyTimes
     *
     * @return Filered list, without repeat KeyPlacesTimes
     */
    public static List<KeyTime> getLiteVersion(List<KeyTime> keyTimes) {
        List<KeyTime> liteVersion = new ArrayList<>();

        if (keyTimes != null && !keyTimes.isEmpty()) {
            for (KeyTime keyTime : keyTimes) {
                boolean exist = false;
                for (KeyTime liteVersionAux : liteVersion) {
                    if (liteVersionAux.equals(keyTime)) {
                        liteVersionAux.setRepeatNumber(keyTime.getRepeatNumber() + liteVersionAux.getRepeatNumber());
                        liteVersionAux.getPeriod().addAllRows(keyTime.getPeriod().getRows());
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    liteVersion.add(keyTime);
                }
            }
        }
        return liteVersion;
    }
}
