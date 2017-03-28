/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.thematic.search;

import java.util.Map;

/**
 *
 * @author Pedro Arthur
 */
public interface ThematicSearchEngine {
    
    /**
     * Search resources by an argument
     * @param argument
     * @return - List of ResourceIds
     */
    Map<String, Float> search(String argument);
    Map<String, Float> getAll();
    
}
