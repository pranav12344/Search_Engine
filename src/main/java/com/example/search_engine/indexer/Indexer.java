package com.example.search_engine.indexer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Indexer {
    private Directory index = new RAMDirectory();
    private StandardAnalyzer analyzer = new StandardAnalyzer();

    public void indexDocument(String url, String description, String imageUrl) {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter writer = new IndexWriter(index, config)) {
            Document doc = new Document();
            doc.add(new StringField("url", url, Field.Store.YES));
            doc.add(new TextField("description", description, Field.Store.YES));
            doc.add(new StringField("imageUrl", imageUrl, Field.Store.YES));
            writer.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Directory getIndex() {
        return index;
    }
}
