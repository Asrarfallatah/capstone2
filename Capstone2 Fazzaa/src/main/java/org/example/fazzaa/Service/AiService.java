package org.example.fazzaa.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .build();

    public String generateResponse(String prompt) {

        try {
            String response = webClient.post()
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue("""
                                {
                                  "model": "gpt-4o-mini",
                                  "messages": [
                                    {
                                      "role": "user",
                                      "content": "%s"
                                    }
                                  ]
                                }
                            """.formatted(prompt))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return response;

        } catch (Exception e) {
            return "error while calling AI service : " + e.getMessage();
        }
    }
}
