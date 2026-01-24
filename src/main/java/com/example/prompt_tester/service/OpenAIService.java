package com.example.prompt_tester.service;

import com.example.prompt_tester.dto.PromptRequest;
import com.example.prompt_tester.dto.PromptResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String OPEN_API_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate = new RestTemplate();


    //send a prompt to OpenAI and return a response with stats
    public PromptResult sendPrompt(PromptRequest request){
        //start timer
        long startTime = System.currentTimeMillis();

        //Build the request body, this is what OpenAI expects
        Map<String, Object> requestBody = buildRequestBody(request);

        //set headers(auth + content-type)
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + apiKey);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        //create HTTP entity, combine both headers and body fo0m above
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, httpHeaders);

        //make the api call
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                OPEN_API_URL,
                HttpMethod.POST,
                entity,
                Map.class
        );

        //parse the response
        Map<String, Object> responseBody = responseEntity.getBody();

        //extract data that we need
        String aiResponse = extractAIresponse(responseBody);
        List<Integer> counts = extractTokenCount(responseBody);
        Integer tokenCounts = counts.get(0) + counts.get(1);

        //calculate cost
        Double cost = calculateCost(counts);
        Long responseTime = System.currentTimeMillis() - startTime;


        return new PromptResult(aiResponse, tokenCounts, cost, responseTime);
    }

    //calculates cost efficiently
    private Double calculateCost(List<Integer> counts) {
        if (counts == null || counts.size() < 2) {
            return 0.0;
        }

        Integer promptTokens = counts.get(0);
        Integer completionTokens = counts.get(1);

        double inputRate = 0.15 / 1_000_000.0;
        double outputRate = 0.60 / 1_000_000.0;

        return (promptTokens * inputRate) + (completionTokens * outputRate);
    }


    //extract token count from the openAi response
    private List<Integer> extractTokenCount(Map<String, Object> responseBody) {
        try{
            Map<String, Object> usage = (Map<String, Object>) responseBody.get("usage");

            return List.of(
                    (Integer) usage.getOrDefault("prompt_tokens", 0),
                    (Integer) usage.getOrDefault("completion_tokens", 0)
            );
        }catch(Exception e){
            return List.of(0, 0);
        }
    }

    //extract AI response
    private String extractAIresponse(Map<String, Object> responseBody) {
        try{
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, String> message = (Map<String, String>) firstChoice.get("message");
            return message.get("content");
        }catch (Exception e){
            return "Error parsing response " + e.getMessage();
        }
    }


    //builds request body as expected by the OpenAI
    private Map<String, Object> buildRequestBody(PromptRequest request) {
        Map<String, Object> body = new HashMap<>();

        body.put("model", "gpt-4o-mini");
        body.put("temperature", request.temperature());
        body.put("max_tokens", 500); //Limits response length, **controls cost**


        //message array (OpenAI's format)
        List<Map<String, String>> messages = new ArrayList<>();

        //System Message to set AI behavior
        messages.add(Map.of(
              "role", "system",
                "content", request.systemPrompt()
        ));

        //User message; the actual question being asked
        messages.add(Map.of(
                "role", "user",
                "content", request.userMessage()
        ));

        body.put("messages", messages);
        return body;
    }

}
