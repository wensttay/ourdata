/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.edu.ifpb.dac.ourdata.shared.domain.ResourceItemSearch;
import br.ifpb.simba.ourdata.thematic.service.ThematicResourceService;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public class SearchExample {
    
    private static ThematicResourceService thematicService = new ThematicResourceService();
    
    
    public static void main(String[] args) {
        
        //Exemplo de indexação
        //Indexe todos os resources de 1 à 1000
        thematicService.indexResources(1, 1000);

        //Exemplo de busca pelo argumento "SOCIAL"
        //List<ResourceItemSearch> searchResult = thematicService.search("SOCIAL"); 
    }
}
