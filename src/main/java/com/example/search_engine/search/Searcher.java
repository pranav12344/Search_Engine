package com.example.search_engine.search;

import com.example.search_engine.indexer.Indexer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
    @Service
    public class Searcher {

        private Directory index;
        private StandardAnalyzer analyzer = new StandardAnalyzer();

        @Autowired
        public Searcher(Indexer indexer) {
            this.index = indexer.getIndex();
        }

        public List<String[]> search(String queryStr) {
            List<String[]> results = new ArrayList<>();
            try {
                Query query = new QueryParser("description", analyzer).parse(queryStr);
                IndexReader reader = DirectoryReader.open(index);
                IndexSearcher searcher = new IndexSearcher(reader);
                TopDocs docs = searcher.search(query, 10);

                System.out.println("Number of hits: " + docs.totalHits); // Debug output

                for (ScoreDoc scoreDoc : docs.scoreDocs) {
                    Document doc = searcher.doc(scoreDoc.doc);
                    String url = doc.get("url");
                    String description = doc.get("description");
                    String imageUrl = doc.get("imageUrl");

                    System.out.println("Search Result: URL = " + url + ", Description = " + description + ", Image URL = " + imageUrl); // Debug output
                    results.add(new String[]{url, description, imageUrl});
                }
                reader.close();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            return results;
        }
    }
