package com.example.search_engine.controller;

import com.example.search_engine.crawler.WebCrawler;
import com.example.search_engine.history.Search_history;
import com.example.search_engine.indexer.Indexer;
import com.example.search_engine.parser.ContentParser;
import com.example.search_engine.repository.SearchHistoryRepository;
import com.example.search_engine.search.Searcher;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
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

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @GetMapping("/history")
    public String viewHistory() {
        StringBuilder htmlResponse = new StringBuilder("<html><head><style>");
        htmlResponse.append("body {")
                .append("background-color: #f4f4f4;")
                .append("color: black;")
                .append("font-family: Arial, sans-serif;")
                .append("}")
                .append("table {")
                .append("width: 100%;")
                .append("border-collapse: collapse;")
                .append("margin: 20px 0;")
                .append("}")
                .append("th, td {")
                .append("padding: 10px;")
                .append("text-align: left;")
                .append("border-bottom: 1px solid #ddd;")
                .append("}")
                .append("</style></head><body>");

        htmlResponse.append("<h1>Search History</h1>");
        htmlResponse.append("<table>");
        htmlResponse.append("<tr><th>ID</th><th>Search Term</th><th>Timestamp</th></tr>");

        List<Search_history> historyList = searchHistoryRepository.findAll();
        for (Search_history history : historyList) {
            htmlResponse.append("<tr>")
                    .append("<td>").append(history.getId()).append("</td>")
                    .append("<td>").append(history.getSearchTerm()).append("</td>")
                    .append("<td>").append(history.getSearchTime()).append("</td>")
                    .append("</tr>");
        }
        htmlResponse.append("</table>");
        htmlResponse.append("</body></html>");
        return htmlResponse.toString();
    }

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
                .append(".history-button {")
                .append("position: absolute;")
                .append("top: 10px;")
                .append("right: 10px;")
                .append("padding: 10px 20px;")
                .append("background-color: #00f;")
                .append("color: white;")
                .append("border: none;")
                .append("border-radius: 5px;")
                .append("cursor: pointer;")
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

        htmlResponse.append("<a href=\"/history\"><button class=\"history-button\">HISTORY</button></a>");


        htmlResponse.append("<h1>SHADOW SEEK</h1>");
        htmlResponse.append("<form action=\"/search\" method=\"get\">")
                .append("<input type=\"text\" name=\"query\" placeholder=\"Enter your search query\" required>")
                .append("<input type=\"submit\" value=\"Search\">")
                .append("</form>");

        htmlResponse.append("<h2>Search Results for '").append(query).append("'</h2>");
        if (query != null && !query.isEmpty()) {

            Search_history searchHistory = new Search_history();
            searchHistory.setSearchTerm(query);
            searchHistory.setSearchTime(LocalDateTime.now());
            searchHistoryRepository.save(searchHistory);

            // Fetch basic information from Wikipedia
            String basicInfo = fetchBasicInfo(query);
            htmlResponse.append("<h2>About '").append(query).append("'</h2>")
                    .append("<p>").append(basicInfo).append("</p>");

            List<String[]> crawledLinks = webCrawler.crawl("https://www.google.com/search?q=" + query);

            for (String[] linkDesc : crawledLinks) {
                indexer.indexDocument(linkDesc[0], linkDesc[1], linkDesc[2]);
            }


            List<String[]> searchResults = searcher.search(query);


           htmlResponse.append("<h2>Related Links</h2>");
            htmlResponse.append("<ul>");
            if (searchResults != null && !searchResults.isEmpty()) {
                for (String[] result : searchResults) {
                    htmlResponse.append("<li><a href=\"").append(result[0]).append("\" target=\"_blank\">")
                            .append(result[0]).append("</a><br>").append(result[1]);
                    if (result.length > 2 && !result[2].isEmpty()) {
                        htmlResponse.append("<br><img src=\"").append(result[2]).append("\" alt=\"Image\">");
                    }
                    htmlResponse.append("</li>");
                }
            } else {
                htmlResponse.append("<li>No results found.</li>");
            }
            htmlResponse.append("</ul>");
            htmlResponse.append("<br><br>");
            htmlResponse.append("copyright@2024");
            htmlResponse.append("<h2> created by ~ Pranav Narawade </h2>");
        }

        htmlResponse.append("</body></html>");
        return htmlResponse.toString();

    }

    @SneakyThrows
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