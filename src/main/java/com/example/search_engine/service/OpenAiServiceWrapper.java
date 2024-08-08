//package com.example.search_engine.service;
//
//import com.theokanning.openai.OpenAiService;
//import com.theokanning.openai.completion.CompletionRequest;
//import com.theokanning.openai.completion.CompletionResult;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OpenAiServiceWrapper {
//    private final OpenAiService openAiService;
//
//    public OpenAiServiceWrapper(@Value("${openai.api.key}") String apiKey) {
//        this.openAiService = new OpenAiService(apiKey);
//    }
//
//    public String getBasicDescription(String query) {
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt("Provide a brief description of " + query)
//                .maxTokens(150)
//                .build();
//
//        CompletionResult result = openAiService.createCompletion(completionRequest);
//        return result.getChoices().get(0).getText().trim();
//    }
//}
