/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity.utils;

import br.ifpb.simba.ourdata.services.QueryResourceItemSearchBo;

/**
 *
 * @author kieckegard
 */
public class PlaceUtilsTest{
    public static void main( String[] args ){
        QueryResourceItemSearchBo bo = new QueryResourceItemSearchBo();
        bo.getResourceItemSearchSortedByRank("cajazeiras", "municipio");

    }
}
