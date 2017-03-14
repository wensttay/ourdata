/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.thematic.indexer;

import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.Collection;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

/**
 *
 * @author Pedro Arthur
 */
public interface LuceneResourceIndexer {
    
    void index(Collection<CkanResource> resources);
    Analyzer getAnalyzer();
    Directory getDirectory();
    IndexWriterConfig getIndexWriteConfig();
    void setAnalyzer(Analyzer analyzer);
    
}
