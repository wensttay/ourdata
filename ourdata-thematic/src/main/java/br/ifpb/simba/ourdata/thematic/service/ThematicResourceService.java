/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.thematic.service;

import br.ifpb.simba.ourdata.shared.domain.Resource;
import br.ifpb.simba.ourdata.shared.domain.ResourceItemSearch;
import br.ifpb.simba.ourdata.persistence.CkanResourceBdDao;
import br.ifpb.simba.ourdata.thematic.indexer.LuceneResourceIndexer;
import br.ifpb.simba.ourdata.thematic.indexer.ResourceIndexerLuceneImpl;
import br.ifpb.simba.ourdata.thematic.search.ThematicSearchEngine;
import br.ifpb.simba.ourdata.thematic.search.ThematicSearchEngineLuceneImpl;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.pt.PortugueseAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Pedro Arthur
 */
public class ThematicResourceService {

    private final CkanResourceBdDao resourceDao;
    private LuceneResourceIndexer indexer;
    private final ThematicSearchEngine searchEngine;

    public ThematicResourceService() {
        this.resourceDao = new CkanResourceBdDao();
        initIndexer();
        this.searchEngine = new ThematicSearchEngineLuceneImpl(indexer);
    }

    private void initIndexer() {
        PortugueseAnalyzer ptAnalyzer = new PortugueseAnalyzer(Version.LUCENE_42);
        try {
            System.out.println("Creating directory");
            Directory dir = FSDirectory.open(new File("lucene\\ourdata-index.lucene"));
            System.out.println("Instantiating ResourceIndexerLuceneImpl");
            this.indexer = new ResourceIndexerLuceneImpl(ptAnalyzer, dir);
        } catch (IOException ex) {
            Logger.getLogger(ThematicResourceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void indexResources(int offset, int limit) {
        
        List<CkanResource> resources = resourceDao.getCSVResourcesBetween(offset, limit);
        indexer.index(resources);
    }
    
    public List<ResourceItemSearch> search(String argument) { 
        //Recebe Map<ResourceId,Score>
        Map<String, Float> resultMap = this.searchEngine.search(argument);
        //Extrai todos os ResourceId em Set
        Set<String> keySet = resultMap.keySet();
        //Recebe cria Lista com todos os ResourceId
        List<String> resourceIds = new ArrayList<>();
        resourceIds.addAll(keySet);
        //Realiza uma busca no banco de dados por todos os CkanResources que batem com os resourceIds
        List<CkanResource> ckanResources = resourceDao.searchByIds(resourceIds);
        //Cria Lista de resultado da consulta
        List<ResourceItemSearch> result = new ArrayList<>();
        /*Itera sobre a lista de CkanResources retornando no banco, 
          pegando a pontuação referente ao id no resultMap 
          e criando e adicionando, pra cada CkanResource, um ResourceItemSearch.
        */
        for(CkanResource resource : ckanResources) {
            Resource resourceAux = new Resource(resource.getId(),resource.getName(), resource.getDescription(), resource.getFormat(),
                    resource.getUrl(),resource.getPackageId());
            
            Float score = resultMap.get(resource.getId());
            result.add(new ResourceItemSearch(resourceAux, score));
        }
        
        return result;
    }

}
