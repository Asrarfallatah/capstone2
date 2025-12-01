package org.example.eventplannercompany.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public String askAI(String prompt) {

        try {
            // Escape any problematic quotes to avoid JSON breaking
            String safePrompt = prompt.replace("\"", "\\\"");

            String body = "{\n" +
                    "  \"model\": \"gpt-4o-mini\",\n" +
                    "  \"messages\": [\n" +
                    "    {\"role\": \"user\", \"content\": \"" + safePrompt + "\"}\n" +
                    "  ]\n" +
                    "}";

            String result = webClient.post()
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(result);

            JsonNode choices = root.path("choices");
            if (choices.isMissingNode() || choices.size() == 0) {
                return "AI Error: no response returned";
            }

            return choices.get(0)
                    .path("message")
                    .path("content")
                    .asText()
                    .trim();

        } catch (Exception e) {
            return "AI Error: " + e.getMessage();
        }
    }
}
