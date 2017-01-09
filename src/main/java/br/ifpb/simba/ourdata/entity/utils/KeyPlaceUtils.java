package br.ifpb.simba.ourdata.entity.utils;

import br.ifpb.simba.ourdata.entity.KeyPlace;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class KeyPlaceUtils {

    /**
     * Return a list with a 'lite Version' of result seach KeyPlaces's resource.
     * This list has a same KeyPlaces of result search, but KeyPlaces with same
     * place are joined in the same KeyPlace
     *
     * @param keyPlaces List with repeat KeyPlaces
     *
     * @return Filered list, without repeat KeyPlaces
     */
    public static List<KeyPlace> getLiteVersion(List<KeyPlace> keyPlaces) {
        List<KeyPlace> liteVersion = new ArrayList<>();

        if (keyPlaces != null && !keyPlaces.isEmpty()) {
            for (KeyPlace keyPlace : keyPlaces) {
                boolean exist = false;
                for (KeyPlace liteVersionAux : liteVersion) {
                    if (liteVersionAux.equals(keyPlace)) {
                        liteVersionAux.setRepeatNumber(keyPlace.getRepeatNumber() + liteVersionAux.getRepeatNumber());
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    liteVersion.add(keyPlace);
                }
            }
        }
        return liteVersion;
    }
}
