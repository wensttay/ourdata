/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import br.ifpb.simba.ourdata.geo.KeyWord;
import br.ifpb.simba.ourdata.geo.Place;
import java.util.List;

/**
 *
 * @author Wensttay
 */
public interface Reader <T,I> {
    T build (I urlString);
    void print (I urlString);
    List<KeyWord> filterKeyWord(String resourceId, I urlString, List<Place> PlaceList);
}
