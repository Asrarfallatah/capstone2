package org.example.eventplannercompany.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.ApiResponse.ApiResponse;
import org.example.eventplannercompany.Model.ClientFeedback;
import org.example.eventplannercompany.Service.ClientFeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class ClientFeedbackController {

    private final ClientFeedbackService clientFeedbackService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getFeedbacks(){

        if (clientFeedbackService.getAllFeedbacks().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no feedback yet in the DataBase to show their information !"));
        }

        return ResponseEntity.status(200).body(clientFeedbackService.getAllFeedbacks());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addFeedback(@Valid @RequestBody ClientFeedback feedback, Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = clientFeedbackService.addFeedback(feedback);

        if (result == 1){
            return ResponseEntity.status(400).body(new ApiResponse("No client with this ID has been found in the DataBase to add a feedback !"));
        }
        else if (result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("No client request with this ID has been found in the DataBase !"));
        }
        else if (result == 3){
            return ResponseEntity.status(200).body(new ApiResponse("Feedback information has been added Successfully !"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Unknown error occurred while adding feedback !"));
    }

    // update
    @PutMapping("/update/{feedbackId}")
    public ResponseEntity<?> updateFeedback(@PathVariable Integer feedbackId, @Valid @RequestBody ClientFeedback feedback, Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = clientFeedbackService.updateFeedback(feedbackId, feedback);

        if (result == 1){
            return ResponseEntity.status(400).body(new ApiResponse("No feedback with this ID has been found in the DataBase to update it !"));
        }
        else if (result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("No client with this ID has been found in the DataBase !"));
        }
        else if (result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("No client request with this ID has been found in the DataBase !"));
        }
        else if (result == 4){
            return ResponseEntity.status(200).body(new ApiResponse("Feedback information has been updated Successfully !"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Unknown error occurred while updating feedback !"));
    }

    // delete
    @DeleteMapping("/delete/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Integer feedbackId){

        int deleted = clientFeedbackService.deleteFeedback(feedbackId);

        if (deleted == 1){
            return ResponseEntity.status(400).body(new ApiResponse("No feedback with this ID has been found in the DataBase to delete it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Feedback has been deleted Successfully !"));
    }

    /// EXTRA ENDPOINTS

    // 1 - low rating
    @GetMapping("/low-rating/{threshold}")
    public ResponseEntity<?> getLowRatingFeedbacks(@PathVariable Integer threshold){

        if (clientFeedbackService.getLowRatingFeedbacks(threshold).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no feedback with a rating lower than this threshold !"));
        }

        return ResponseEntity.status(200).body(clientFeedbackService.getLowRatingFeedbacks(threshold));
    }

    // 2- keyword search
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchKeyword(@PathVariable String keyword){

        if (clientFeedbackService.searchFeedbackByKeyword(keyword).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no feedback containing this keyword !"));
        }

        return ResponseEntity.status(200).body(clientFeedbackService.searchFeedbackByKeyword(keyword));
    }

    // 3- avg rating for client
    @GetMapping("/avg-client/{clientId}")
    public ResponseEntity<?> avgRatingClient(@PathVariable Integer clientId){

        Double avg = clientFeedbackService.getAverageRatingForClient(clientId);

        if (avg == null){
            return ResponseEntity.status(400).body(new ApiResponse("This client has no feedback yet in the DataBase !"));
        }

        return ResponseEntity.status(200).body(avg);
    }

    // 4- avg rating by event type
    @GetMapping("/avg-type/{eventType}")
    public ResponseEntity<?> avgRatingType(@PathVariable String eventType){

        Double avg = clientFeedbackService.getAverageRatingByEventType(eventType);

        if (avg == null){
            return ResponseEntity.status(400).body(new ApiResponse("There is no feedback found for this event type !"));
        }

        return ResponseEntity.status(200).body(avg);
    }

    // 5- AI negative patterns
    @GetMapping("/negative-analysis")
    public ResponseEntity<?> analyzeNegative(){

        String result = clientFeedbackService.analyzeNegativeFeedback();

        if (result.contains("no negative feedback")){
            return ResponseEntity.status(400).body(new ApiResponse("There is no negative feedback in the DataBase to analyze !"));
        }

        return ResponseEntity.status(200).body(result);
    }
}
