package br.ifpb.simba.ourdata.services;

import br.ifpb.simba.ourdata.entity.ResourceItemSearch;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public class TestQueryResourceSearchBo {

    public static void main(String[] args) {
        String placeName = "Rio Grande do Norte";

        QueryResourceItemSearchBo bo = new QueryResourceItemSearchBo();

        List<ResourceItemSearch> itensSearch = bo.getResourceItemSearchSortedByRank(placeName, "cidade");

        for (ResourceItemSearch item : itensSearch) {
            System.out.println(item.getRanking() + " : " + item.getResource().getDescricao() + " | " + item.getResource().getUrl());
        }
    }
}
