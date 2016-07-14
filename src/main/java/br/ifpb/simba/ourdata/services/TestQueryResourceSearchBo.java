/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.services;

import br.ifpb.simba.ourdata.entity.ResourceItemSearch;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class TestQueryResourceSearchBo
{
    public static void main(String[] args)
    {
        String placeName = "Rio Grande do Norte";
        
        QueryResourceItemSearchBo bo = new QueryResourceItemSearchBo();
        
        List<ResourceItemSearch> itensSearch = bo.getResourceItemSearchSortedByRank(placeName, "");
        
        for(ResourceItemSearch item : itensSearch){
            System.out.println(item.getRanking()+" : "+item.getResource().getDescricao()+" | "+item.getResource().getUrl());
        }
    }
}
