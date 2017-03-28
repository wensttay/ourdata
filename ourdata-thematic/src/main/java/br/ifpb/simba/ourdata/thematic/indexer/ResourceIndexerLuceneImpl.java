/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.thematic.indexer;

import br.ifpb.simba.ourdata.reader.CSVReaderException;
import br.ifpb.simba.ourdata.thematic.parser.CkanResourceDocumentParser;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Pedro Arthur
 */
public class ResourceIndexerLuceneImpl implements LuceneResourceIndexer {

    private final Directory directory;
    private final IndexWriterConfig config;
    private Analyzer analyzer;
    private final CkanResourceDocumentParser parser;

    public ResourceIndexerLuceneImpl(Analyzer analyzer, Directory directory) {
        this.analyzer = analyzer;
        this.parser = new CkanResourceDocumentParser();
        this.directory = directory;
        System.out.println("Instantiating IndexWriterConfig");
        this.config = new IndexWriterConfig(Version.LUCENE_42, analyzer);
    }

    @Override
    public void index(Collection<CkanResource> resources) {
        //
        try {
            //
            System.out.println("Creating IndexWriter");
            IndexWriter writer = new IndexWriter(this.directory, config);
            writer.commit();
            //
            System.out.println("-----------------CREATING DOCUMENTS AND INDEXING IT----------------");
            
            for(CkanResource resource : resources) {
                try {
                    Document document = createResourceDocument(resource);
                    System.out.println("Indexing resource document "+resource.getId());
                    writer.addDocument(document);
                    System.out.println("Commiting...");
                    writer.commit();
                } catch (CSVReaderException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println("-------------------------------END----------------------------------");

        } catch (IOException ex) {
            Logger.getLogger(ResourceIndexerLuceneImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Document createResourceDocument(CkanResource resource) throws CSVReaderException {
        
        String path = "C:\\lucene\\ourdata\\documents\\";

        System.out.println("Creating document for " + resource.getId());

        String strDoc = this.parser.parse(resource);

        Log.log(path + resource.getId() + ".txt", strDoc);

        Document document = new Document();
        document.add(new TextField("resourceText", strDoc, Field.Store.YES));
        document.add(new TextField("resourceId", resource.getId(), Field.Store.YES));

        return document;
    }

    @Override
    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    @Override
    public Analyzer getAnalyzer() {
        return this.analyzer;
    }

    @Override
    public Directory getDirectory() {
        return this.directory;
    }

    @Override
    public IndexWriterConfig getIndexWriteConfig() {
        return this.config;
    }

}
