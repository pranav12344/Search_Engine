package com.example.search_engine.controller;

import com.example.search_engine.crawler.WebCrawler;
import com.example.search_engine.history.Search_history;
import com.example.search_engine.indexer.Indexer;
import com.example.search_engine.model.User;
import com.example.search_engine.parser.ContentParser;
import com.example.search_engine.repository.SearchHistoryRepository;
import com.example.search_engine.repository.UserRepository;
import com.example.search_engine.search.Searcher;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

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

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String signupPage() {
        return "<html>" +
                "<head>" +
                "<style>" +
                "body {" +
                "    display: flex;" +
                "    justify-content: center;" +
                "    align-items: center;" +
                "    height: 100vh;" +
                "    margin: 0;" +
                "    background-color: #f4f4f4;" +
                "    font-family: Arial, sans-serif;" +
                "}" +
                ".form-container {" +
                "    background-color: #fff;" +
                "    padding: 20px;" +
                "    border-radius: 10px;" +
                "    box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);" +
                "    width: 300px;" +
                "    text-align: center;" +
                "}" +
                ".form-container h1 {" +
                "    margin-bottom: 20px;" +
                "    font-size: 24px;" +
                "    color: #333;" +
                "}" +
                ".form-container input[type='text'], .form-container input[type='password'] {" +
                "    width: 100%;" +
                "    padding: 10px;" +
                "    margin: 10px 0;" +
                "    border: 1px solid #ccc;" +
                "    border-radius: 5px;" +
                "}" +
                ".form-container input[type='submit'] {" +
                "    background-color: #007bff;" +
                "    color: #fff;" +
                "    padding: 10px;" +
                "    border: none;" +
                "    border-radius: 5px;" +
                "    cursor: pointer;" +
                "    width: 100%;" +
                "}" +
                ".form-container input[type='submit']:hover {" +
                "    background-color: #0056b3;" +
                "}" +
                ".form-container a {" +
                "    display: block;" +
                "    margin-top: 20px;" +
                "    color: #007bff;" +
                "    text-decoration: none;" +
                "}" +
                ".form-container a:hover {" +
                "    text-decoration: underline;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"form-container\">" +
                "<h1>Sign Up</h1>" +
                "<form action=\"/signup\" method=\"post\">" +
                "Email: <input type=\"text\" name=\"email\"><br>" +
                "Password: <input type=\"password\" name=\"password\"><br>" +
                "<input type=\"submit\" value=\"Sign Up\">" +
                "</form>" +
                "<a href=\"/login\">Login</a>" +
                "</div>" +
                "</body>" +
                "</html>";
    }


    @PostMapping("/signup")
    public String signup(@RequestParam String email, @RequestParam String password) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            return "<html><body>" +
                    "<p>Email already exists.</p>" +
                    "<button onclick=\"history.back()\">Go Back</button>" +
                    "</body></html>";
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        userRepository.save(newUser);

        return "<html><body>" +
                "<p>Signup successful. Redirecting to the main page...</p>" +
                "<script>" +
                "setTimeout(function() {" +
                "  window.location.href = 'http://localhost:8080/search?query=';" +
                "}, 2000);" +
                "</script>" +
                "</body></html>";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "<html>" +
                "<head>" +
                "<style>" +
                "body {" +
                "    display: flex;" +
                "    justify-content: center;" +
                "    align-items: center;" +
                "    height: 100vh;" +
                "    margin: 0;" +
                "    background-color: #f4f4f4;" +
                "    font-family: Arial, sans-serif;" +
                "}" +
                ".form-container {" +
                "    background-color: #fff;" +
                "    padding: 20px;" +
                "    border-radius: 10px;" +
                "    box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);" +
                "    width: 300px;" +
                "    text-align: center;" +
                "}" +
                ".form-container h1 {" +
                "    margin-bottom: 20px;" +
                "    font-size: 24px;" +
                "    color: #333;" +
                "}" +
                ".form-container input[type='text'], .form-container input[type='password'] {" +
                "    width: 100%;" +
                "    padding: 10px;" +
                "    margin: 10px 0;" +
                "    border: 1px solid #ccc;" +
                "    border-radius: 5px;" +
                "}" +
                ".form-container input[type='submit'] {" +
                "    background-color: #007bff;" +
                "    color: #fff;" +
                "    padding: 10px;" +
                "    border: none;" +
                "    border-radius: 5px;" +
                "    cursor: pointer;" +
                "    width: 100%;" +
                "}" +
                ".form-container input[type='submit']:hover {" +
                "    background-color: #0056b3;" +
                "}" +
                ".form-container a {" +
                "    display: block;" +
                "    margin-top: 20px;" +
                "    color: #007bff;" +
                "    text-decoration: none;" +
                "}" +
                ".form-container a:hover {" +
                "    text-decoration: underline;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"form-container\">" +
                "<h1>Login</h1>" +
                "<form action=\"/login\" method=\"post\">" +
                "Email: <input type=\"text\" name=\"email\"><br>" +
                "Password: <input type=\"password\" name=\"password\"><br>" +
                "<input type=\"submit\" value=\"Login\">" +
                "</form>" +
                "<a href=\"/signup\">Sign Up</a>" +
                "</div>" +
                "</body>" +
                "</html>";
    }


    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, WebRequest request) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
            return "<html><body>" +
                    "<p>Login successful. Redirecting to the main page...</p>" +
                    "<script>" +
                    "setTimeout(function() {" +
                    "  window.location.href = 'http://localhost:8080/search?query=';" +
                    "}, 2000);" +
                    "</script>" +
                    "</body></html>";
        }

        return "<html><body>" +
                "<p>Invalid email or password.</p>" +
                "<button onclick=\"history.back()\">Go Back</button>" +
                "</body></html>";
    }



    @GetMapping("/logout")
    public String logout(WebRequest request) {
        request.removeAttribute("user", WebRequest.SCOPE_SESSION);

        return "<html><body>" +
                "<p>Logged out successfully. Redirecting to the main page...</p>" +
                "<script>" +
                "setTimeout(function() {" +
                "  window.location.href = 'http://localhost:8080/search?query=';" +
                "}, 2000);" +
                "</script>" +
                "</body></html>";
    }

    @GetMapping("/history")
    public String viewHistory(WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        if (user == null) {
            return "<html><body>" +
                    "<p>Please log in to view your history. Redirecting to the login page...</p>" +
                    "<script>" +
                    "setTimeout(function() {" +
                    "  window.location.href = 'http://localhost:8080/login';" +
                    "}, 2000);" +
                    "</script>" +
                    "</body></html>";
        }


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

        htmlResponse.append("<a href=\"/search\"><button class=\"history-button\" style=\"margin-right: 10px;\">SEARCH</button></a>");
        htmlResponse.append("<a href=\"/logout\"><button class=\"history-button\">LOGOUT</button></a>");
        htmlResponse.append("<h1>Your Search History</h1>");
        htmlResponse.append("<table>");
        htmlResponse.append("<tr><th>Search Term</th><th>Timestamp</th></tr>");

        List<Search_history> historyList = searchHistoryRepository.findAllByUserId(user.getId());
        for (Search_history history : historyList) {

            htmlResponse.append("<tr>")

//                    .append("<td>").append(history.getId()).append("</td>")
                    .append("<td>").append(history.getSearchTerm()).append("</td>")
                    .append("<td>").append(history.getSearchTime()).append("</td>")
                    .append("</tr>");
        }
        htmlResponse.append("</table>");
        htmlResponse.append("</body></html>");
        return htmlResponse.toString();
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String query, WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
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
        if (user == null) {
            htmlResponse.append("<a href=\"/signup\"><button class=\"history-button\">SIGN UP</button></a>");
            htmlResponse.append("<a href=\"/login\"><button class=\"history-button\">LOGIN</button></a>");
        } else {
            htmlResponse.append("<a href=\"/logout\"><button class=\"history-button\">LOGOUT</button></a>");
            htmlResponse.append("<a href=\"/history\"><button class=\"history-button\">HISTORY</button></a>");
        }



        htmlResponse.append("<h1>SHADOW SEEK</h1>");
        htmlResponse.append("<form action=\"/search\" method=\"get\">")
                .append("<input type=\"text\" name=\"query\" placeholder=\"Enter your search query\" required>")
                .append("<input type=\"submit\" value=\"Search\">")
                .append("</form>");

        htmlResponse.append("<h2>Search Results for '").append(query).append("'</h2>");


        if (query != null && !query.isEmpty()) {
            if (user != null) {
                Search_history searchHistory = new Search_history();
                searchHistory.setSearchTerm(query);
                searchHistory.setSearchTime(LocalDateTime.now());
                searchHistory.setUser(user);
                searchHistoryRepository.save(searchHistory);
            }

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
