/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.thematic.search;

import br.ifpb.simba.ourdata.thematic.indexer.LuceneResourceIndexer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.Version;

/**
 *
 * @author Pedro Arthur
 */
public class ThematicSearchEngineLuceneImpl implements ThematicSearchEngine {

    private final LuceneResourceIndexer indexer;

    public ThematicSearchEngineLuceneImpl(LuceneResourceIndexer indexer) {
        this.indexer = indexer;
    }

    /**
     * Search resources using TF-IDF and Cosine Similarity.
     *
     * @param argument
     * @return List of Resource Ids
     */
    @Override
    public Map<String, Float> search(String argument) {

        try {

            int hitsPerPage = 1220;

            Query q = new QueryParser(Version.LUCENE_42, "resourceText", indexer.getAnalyzer()).parse(argument);

            try {

                IndexReader reader = DirectoryReader.open(indexer.getDirectory());
                //Cria objeto que irá realizar a consulta em cima do Reader
                IndexSearcher searcher = new IndexSearcher(reader);
                //cria o coletor de resultados da consulta através de uma pontuação
                TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);

                //usa o objeto que realiza a consulta usando o obj de consulta e o coletor
                searcher.search(q, collector);
                //recebe os resultados e itera sobre eles
                ScoreDoc[] hits = collector.topDocs().scoreDocs;

                return getResult(hits, searcher);

            } catch (IOException ex) {
                Logger.getLogger(ThematicSearchEngineLuceneImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ParseException ex) {
            throw new IllegalArgumentException("Sounds like you're putting some invalid argument!");
        }

        return null;
    }

    @Override
    public Map<String, Float> getAll() {

        int hitsPerPage = 1220;

        Query q = new MatchAllDocsQuery();

        try {

            IndexReader reader = DirectoryReader.open(indexer.getDirectory());
            //Cria objeto que irá realizar a consulta em cima do Reader
            IndexSearcher searcher = new IndexSearcher(reader);
            //cria o coletor de resultados da consulta através de uma pontuação
            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);

            //usa o objeto que realiza a consulta usando o obj de consulta e o coletor
            searcher.search(q, collector);
            //recebe os resultados e itera sobre eles
            ScoreDoc[] hits = collector.topDocs().scoreDocs;

            return getResult(hits, searcher);

        } catch (IOException ex) {
            Logger.getLogger(ThematicSearchEngineLuceneImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private Map<String, Float> getResult(ScoreDoc[] hits, IndexSearcher searcher) throws IOException {
        //List<String> result = new ArrayList<>();
        Map<String, Float> result = new HashMap<>();
        for (ScoreDoc hit : hits) {
            int docId = hit.doc;
            System.out.println("Doc id: " + docId);
            Document d = searcher.doc(docId);
            String resourceId = d.get("resourceId");
            System.out.println("Resouce id fount: " + resourceId);
            System.out.println("Score found: " + hit.score);
            //result.add(resourceId);
            result.put(resourceId, hit.score);
        }
        System.out.println("fim");
        return result;
    }

}
