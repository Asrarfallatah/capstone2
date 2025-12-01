package org.example.eventplannercompany.Controller;

import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.Service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/ask")
    public ResponseEntity<?> askAI(@RequestParam String prompt){
        return ResponseEntity.status(200).body(aiService.askAI(prompt));
    }
}
