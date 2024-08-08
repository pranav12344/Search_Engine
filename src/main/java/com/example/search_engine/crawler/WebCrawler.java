//package com.example.search_engine.crawler;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class WebCrawler {
//
//    public List<String[]> crawl(String url) {
//        List<String[]> results = new ArrayList<>();
//        try {
//            Document doc = Jsoup.connect(url).get();
//            Elements links = doc.select("a[href]");
//            for (Element link : links) {
//                String absHref = link.attr("abs:href");
//                String description = link.text();
//                results.add(new String[]{absHref, description});
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return results;
//    }
//}


package com.example.search_engine.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebCrawler {

    public List<String[]> crawl(String url) {
        List<String[]> results = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String absHref = link.attr("abs:href");
                String description = link.text();
                String imageUrl = "";

                // Extract image URL if present
                Element imgElement = link.select("img").first();
                if (imgElement != null) {
                    imageUrl = imgElement.attr("abs:src");
                }

                results.add(new String[]{absHref, description, imageUrl});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
