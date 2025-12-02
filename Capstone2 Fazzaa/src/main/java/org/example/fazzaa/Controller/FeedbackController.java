package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiResponse;
import org.example.fazzaa.Model.Feedback;
import org.example.fazzaa.Service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getAllFeedback() {


        return ResponseEntity.status(200).body(feedbackService.getAllFeedbacks());
    }

    // add
    @PostMapping("/add/{clientId}")
    public ResponseEntity<?> addFeedback(@PathVariable Integer clientId , @Valid @RequestBody Feedback feedback) {

        feedbackService.addFeedback(feedback);
        return ResponseEntity.status(200).body(new ApiResponse("feedback has been added successfully to the database !"));
    }

    // update
    @PutMapping("/update/{feedbackId}")
    public ResponseEntity<?> updateFeedback(@PathVariable Integer feedbackId , @Valid @RequestBody Feedback feedback) {
      feedbackService.updateFeedback(feedbackId, feedback);

        return ResponseEntity.status(200).body(new ApiResponse("feedback updated successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Integer feedbackId) {

        feedbackService.deleteFeedback(feedbackId);

        return ResponseEntity.status(200).body(new ApiResponse("feedback deleted successfully !"));
    }

    /// extra endpoints

    // 19 get all feedbacks for a specific request
    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getFeedbacksByRequest(@PathVariable Integer requestId) {

        feedbackService.getFeedbacksByRequest(requestId);

        return ResponseEntity.status(200).body(feedbackService.getFeedbacksByRequest(requestId));
    }

    // 20- summarize feedbacks for a spesific request with Ai
    @GetMapping("/ai-summary/{requestId}")
    public ResponseEntity<?> getAiFeedbackSummary(@PathVariable Integer requestId) {

        feedbackService.getAiFeedbackSummary(requestId);
        return ResponseEntity.status(200).body(new ApiResponse( feedbackService.getAiFeedbackSummary(requestId)  ));
    }

    //22 detect bad feedback and suggest improvements with Ai
    @GetMapping("/ai-negative-analysis")
    public ResponseEntity<?> getAiNegativeFeedbackAnalysis() {

        feedbackService.getAllFeedbacks();
        return ResponseEntity.status(200).body(feedbackService.getAiNegativeFeedbackAnalysis());
    }

}
