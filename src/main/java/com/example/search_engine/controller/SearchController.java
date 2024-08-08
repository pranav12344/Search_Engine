//package com.example.search_engine.controller;
//
//import com.example.search_engine.crawler.WebCrawler;
//import com.example.search_engine.indexer.Indexer;
//import com.example.search_engine.parser.ContentParser;
//import com.example.search_engine.search.Searcher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class SearchController {
//
//    @Autowired
//    private WebCrawler webCrawler;
//
//    @Autowired
//    private ContentParser contentParser;
//
//    @Autowired
//    private Indexer indexer;
//
//    @Autowired
//    private Searcher searcher;
//
//    @GetMapping("/search")
//    public String search(@RequestParam(required = false) String query) {
//        StringBuilder htmlResponse = new StringBuilder("<html><head><style>");
//        htmlResponse.append("body {")
//                .append("background-image: url('https://img.freepik.com/free-vector/white-abstract-wallpaper_23-2148830026.jpg?w=1800&t=st=1723051513~exp=1723052113~hmac=8abc460627c90ff30cb8bde82aaa54b47f15fe1f41d641b74cbae5f999e31bbd');")
//                .append("background-size: cover;")
//                .append("color: black;")
//                .append("font-family: Arial, sans-serif;")
//                .append("}")
//                .append("h1 {")
//                .append("text-align: center;")
//                .append("}")
//                .append("h2 {")
//                .append("text-align: left;")
//                .append("}")
//                .append("ul {")
//                .append("list-style-type: none;")
//                .append("padding: 0;")
//                .append("}")
//                .append("li {")
//                .append("margin: 10px 0;")
//                .append("}")
//                .append("a {")
//                .append("color: #00f;")
//                .append("text-decoration: none;")
//                .append("}")
//                .append("a:hover {")
//                .append("text-decoration: underline;")
//                .append("}")
//                .append("img {")
//                .append("max-width: 200px;")
//                .append("display: block;")
//                .append("margin-top: 10px;")
//                .append("}")
//                .append("form {")
//                .append("text-align: center;")
//                .append("margin-top: 20px;")
//                .append("}")
//                .append("input[type='text'] {")
//                .append("width: 300px;")
//                .append("padding: 10px;")
//                .append("border-radius: 5px;")
//                .append("border: none;")
//                .append("}")
//                .append("input[type='submit'] {")
//                .append("padding: 10px 20px;")
//                .append("border-radius: 5px;")
//                .append("border: none;")
//                .append("background-color: #00f;")
//                .append("color: white;")
//                .append("cursor: pointer;")
//                .append("}")
//                .append("</style></head><body>");
//
//        htmlResponse.append("<br>");
//        htmlResponse.append("<h1>SHADOW SEEK</h1>");
//        htmlResponse.append("<form action=\"/search\" method=\"get\">")
//                .append("<input type=\"text\" name=\"query\" placeholder=\"Enter your search query\" required>")
//                .append("<input type=\"submit\" value=\"Search\">")
//                .append("</form>");
//
//        if (query != null && !query.isEmpty()) {
//            List<String[]> crawledLinks = webCrawler.crawl("https://www.google.com/search?q=" + query);
//
//            for (String[] linkDesc : crawledLinks) {
//                indexer.indexDocument(linkDesc[0], linkDesc[1], linkDesc[2]);
//            }
//
//            List<String[]> searchResults = searcher.search(query);
//
//            htmlResponse.append("<h2>Search Results for '").append(query).append("'</h2>");
//            htmlResponse.append("<br>");
//            htmlResponse.append("<ul>");
//            for (String[] result : searchResults) {
//                htmlResponse.append("<li><a href=\"").append(result[0]).append("\" target=\"_blank\">")
//                        .append(result[0]).append("</a><br>").append(result[1]);
//                if (!result[2].isEmpty()) {
//                    htmlResponse.append("<br><img src=\"").append(result[2]).append("\" alt=\"Image\">");
//                }
//                htmlResponse.append("<br>");
//                htmlResponse.append("</li>");
//            }
//            htmlResponse.append("</ul>");
//            htmlResponse.append("<br><br>");
//            htmlResponse.append("copyright@2024");
//            htmlResponse.append("<h2> created by ~ Pranav Narawade </h2>");
//        }
//
//        htmlResponse.append("</body></html>");
//        return htmlResponse.toString();
//    }
//}
//
package com.example.search_engine.controller;

import com.example.search_engine.crawler.WebCrawler;
import com.example.search_engine.indexer.Indexer;
import com.example.search_engine.parser.ContentParser;
import com.example.search_engine.search.Searcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    private WebCrawler webCrawler;

    @Autowired
    private ContentParser contentParser;

    @Autowired
    private Indexer indexer;

    @Autowired
    private Searcher searcher;

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String query) {
        StringBuilder htmlResponse = new StringBuilder("<html><head><style>");
        htmlResponse.append("body {")
                .append("background-image: url('https://img.freepik.com/free-vector/white-abstract-wallpaper_23-2148830026.jpg?w=1800&t=st=1723051513~exp=1723052113~hmac=8abc460627c90ff30cb8bde82aaa54b47f15fe1f41d641b74cbae5f999e31bbd');")
                .append("background-size: cover;")
                .append("color: black;")
                .append("font-family: Arial, sans-serif;")
                .append("}")
                .append("h1 {")
                .append("text-align: center;")
                .append("}")
                .append("h2 {")
                .append("text-align: left;")
                .append("}")
                .append("ul {")
                .append("list-style-type: none;")
                .append("padding: 0;")
                .append("}")
                .append("li {")
                .append("margin: 10px 0;")
                .append("}")
                .append("a {")
                .append("color: #00f;")
                .append("text-decoration: none;")
                .append("}")
                .append("a:hover {")
                .append("text-decoration: underline;")
                .append("}")
                .append("img {")
                .append("max-width: 200px;")
                .append("display: block;")
                .append("margin-top: 10px;")
                .append("}")
                .append("form {")
                .append("text-align: center;")
                .append("margin-top: 20px;")
                .append("}")
                .append("input[type='text'] {")
                .append("width: 300px;")
                .append("padding: 10px;")
                .append("border-radius: 5px;")
                .append("border: none;")
                .append("}")
                .append("input[type='submit'] {")
                .append("padding: 10px 20px;")
                .append("border-radius: 5px;")
                .append("border: none;")
                .append("background-color: #00f;")
                .append("color: white;")
                .append("cursor: pointer;")
                .append("}")
                .append("</style></head><body>");

        htmlResponse.append("<h1>SHADOW SEEK</h1>");
        htmlResponse.append("<form action=\"/search\" method=\"get\">")
                .append("<input type=\"text\" name=\"query\" placeholder=\"Enter your search query\" required>")
                .append("<input type=\"submit\" value=\"Search\">")
                .append("</form>");

        htmlResponse.append("<h2>Search Results for '").append(query).append("'</h2>");
        if (query != null && !query.isEmpty()) {
            // Fetch basic information from Wikipedia
            String basicInfo = fetchBasicInfo(query);
            htmlResponse.append("<h2>About '").append(query).append("'</h2>")
                    .append("<p>").append(basicInfo).append("</p>");

            List<String[]> crawledLinks = webCrawler.crawl("https://www.google.com/search?q=" + query);

            for (String[] linkDesc : crawledLinks) {
                indexer.indexDocument(linkDesc[0], linkDesc[1], linkDesc[2]);
            }

            // Perform the search and return results
            List<String[]> searchResults = searcher.search(query);

            // Format the results as HTML
            htmlResponse.append("<h2>Related Links</h2>");
            htmlResponse.append("<ul>");
            for (String[] result : searchResults) {
                htmlResponse.append("<li><a href=\"").append(result[0]).append("\" target=\"_blank\">")
                        .append(result[0]).append("</a><br>").append(result[1]);
                if (!result[2].isEmpty()) {
                    htmlResponse.append("<br><img src=\"").append(result[2]).append("\" alt=\"Image\">");
                }
                htmlResponse.append("</li>");
            }
            htmlResponse.append("</ul>");
            htmlResponse.append("<br><br>");
            htmlResponse.append("copyright@2024");
            htmlResponse.append("<h2> created by ~ Pranav Narawade </h2>");
        }

        htmlResponse.append("</body></html>");
        return htmlResponse.toString();
    }

    private String fetchBasicInfo(String query) {
        String apiUrl = "https://en.wikipedia.org/api/rest_v1/page/summary/" + query.replace(" ", "_");
        RestTemplate restTemplate = new RestTemplate();
        try {
            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
            if (response != null && response.get("extract") != null) {
                return response.get("extract").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "No basic information available.";
    }
}
//***************************************chat-gpt******************
//package com.example.search_engine.controller;
//
//import com.example.search_engine.crawler.WebCrawler;
//import com.example.search_engine.indexer.Indexer;
//import com.example.search_engine.parser.ContentParser;
//import com.example.search_engine.search.Searcher;
//import com.example.search_engine.service.OpenAiServiceWrapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class SearchController {
//
//    @Autowired
//    private WebCrawler webCrawler;
//
//    @Autowired
//    private ContentParser contentParser;
//
//    @Autowired
//    private Indexer indexer;
//
//    @Autowired
//    private Searcher searcher;
//
//    @Autowired
//    private OpenAiServiceWrapper openAiServiceWrapper;
//
//    @GetMapping("/search")
//    public String search(@RequestParam(required = false) String query) {
//        StringBuilder htmlResponse = new StringBuilder("<html><head><style>");
//        htmlResponse.append("body {")
//                .append("background-image: url('https://example.com/your-background-image.jpg');")
//                .append("background-size: cover;")
//                .append("color: black;")
//                .append("font-family: Arial, sans-serif;")
//                .append("}")
//                .append("h1 {")
//                .append("text-align: center;")
//                .append("}")
//                .append("ul {")
//                .append("list-style-type: none;")
//                .append("padding: 0;")
//                .append("}")
//                .append("li {")
//                .append("margin: 10px 0;")
//                .append("}")
//                .append("a {")
//                .append("color: #00f;")
//                .append("text-decoration: none;")
//                .append("}")
//                .append("a:hover {")
//                .append("text-decoration: underline;")
//                .append("}")
//                .append("img {")
//                .append("max-width: 200px;")
//                .append("display: block;")
//                .append("margin-top: 10px;")
//                .append("}")
//                .append("form {")
//                .append("text-align: center;")
//                .append("margin-top: 20px;")
//                .append("}")
//                .append("input[type='text'] {")
//                .append("width: 300px;")
//                .append("padding: 10px;")
//                .append("border-radius: 5px;")
//                .append("border: none;")
//                .append("}")
//                .append("input[type='submit'] {")
//                .append("padding: 10px 20px;")
//                .append("border-radius: 5px;")
//                .append("border: none;")
//                .append("background-color: #00f;")
//                .append("color: white;")
//                .append("cursor: pointer;")
//                .append("}")
//                .append("</style></head><body>");
//
//        htmlResponse.append("<h1>Search Engine</h1>");
//        htmlResponse.append("<form action=\"/search\" method=\"get\">")
//                .append("<input type=\"text\" name=\"query\" placeholder=\"Enter your search query\" required>")
//                .append("<input type=\"submit\" value=\"Search\">")
//                .append("</form>");
//
//        if (query != null && !query.isEmpty()) {
//            // Fetch basic information from OpenAI
//            String basicInfo = openAiServiceWrapper.getBasicDescription(query);
//            htmlResponse.append("<h2>About '").append(query).append("'</h2>")
//                    .append("<p>").append(basicInfo).append("</p>");
//
//            // Crawl links and descriptions
//            List<String[]> crawledLinks = webCrawler.crawl("https://www.google.com/search?q=" + query);
//
//            // Index the crawled content
//            for (String[] linkDesc : crawledLinks) {
//                indexer.indexDocument(linkDesc[0], linkDesc[1], linkDesc[2]);
//            }
//
//            // Perform the search and return results
//            List<String[]> searchResults = searcher.search(query);
//
//            // Format the results as HTML
//            htmlResponse.append("<h2>Related Links</h2>");
//            htmlResponse.append("<ul>");
//            for (String[] result : searchResults) {
//                htmlResponse.append("<li><a href=\"").append(result[0]).append("\" target=\"_blank\">")
//                        .append(result[0]).append("</a><br>").append(result[1]);
//                if (!result[2].isEmpty()) {
//                    htmlResponse.append("<br><img src=\"").append(result[2]).append("\" alt=\"Image\">");
//                }
//                htmlResponse.append("</li>");
//            }
//            htmlResponse.append("</ul>");
//        }
//
//        htmlResponse.append("</body></html>");
//        return htmlResponse.toString();
//    }
//}
