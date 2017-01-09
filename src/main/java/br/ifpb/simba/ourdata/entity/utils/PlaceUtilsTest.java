package br.ifpb.simba.ourdata.entity.utils;

import br.ifpb.simba.ourdata.services.QueryResourceItemSearchBo;

/**
 *
 * @author Pedro Arthur
 */
public class PlaceUtilsTest {

    public static void main(String[] args) {
        QueryResourceItemSearchBo bo = new QueryResourceItemSearchBo();
        bo.getResourceItemSearchSortedByRank("cajazeiras", "municipio");

    }
}
