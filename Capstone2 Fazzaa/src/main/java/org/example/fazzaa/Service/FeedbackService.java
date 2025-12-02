package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiException;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Model.Feedback;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    // connect to the database
    private final FeedbackRepository feedbackRepository;
    private final EventRequestRepository eventRequestRepository;
    private final AiService aiService;

    // get all feedbacks from the database
    public List<Feedback> getAllFeedbacks() {

        // check if empty
        if (feedbackRepository.findAll().isEmpty()) {
            throw new ApiException("error no feedback is found yet in the database to show their information !");
        }

        return feedbackRepository.findAll();
    }

    // add feedback to the database
    public void addFeedback(Feedback feedback) {

        // check input mismatch error
        if (feedback.getClientId().toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, client ID should be an integer !");
        } else if (feedback.getRequestId().toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, request ID should be an integer !");
        } else if (feedback.getRating().toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, rating should be an integer !");
        } else if (feedback.getComment() == null) {
            throw new ApiException("error comment cannot be null !");
        }

        // check null
        if (feedback.getClientId()== null ){
            throw new ApiException("error client ID cannot be null !");
        }else if (feedback.getRequestId() == null ){
            throw new ApiException("error request ID cannot be null !");
        }else if (feedback.getRating() == null ){
            throw new ApiException("error rating cannot be null !");
        }else if (feedback.getComment() == null ){
            throw new ApiException("error comment cannot be null !");
        }


        // check if the request id exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(feedback.getRequestId());
        if (request == null) {
          throw new ApiException("no event request found with the that ID in the database!");
        }

        // check if the client is the owner of the request
        if (!request.getClientId().equals(feedback.getClientId())) {
          throw new ApiException("error client is not the owner of the request !");
        }

        // check if the client has already made feedback for this request
        List<Feedback> existingFeedbacks = feedbackRepository.findFeedbacksByRequestId(feedback.getRequestId());
        for (Feedback f : existingFeedbacks) {
            if (f.getClientId().equals(feedback.getClientId())) {
              throw new ApiException("error client has already submitted feedback for this request !");
            }
        }

        feedback.setFeedbackDate(LocalDate.now());

        // add feedback
        feedbackRepository.save(feedback);

    }

    // update feedback within the database
    public void updateFeedback(Integer feedbackId, Feedback feedback) {

        // check for input mismatch error
        if (feedbackId.toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, request ID should be an integer !");
        }

        // check null
        if (feedback.getClientId()== null ){
            throw new ApiException("error client ID cannot be null !");
        }else if (feedback.getRequestId() == null ){
            throw new ApiException("error request ID cannot be null !");
        }else if (feedback.getRating() == null ){
            throw new ApiException("error rating cannot be null !");
        }else if (feedback.getComment() == null ){
            throw new ApiException("error comment cannot be null !");
        }

        // check rating range
        if (feedback.getRating() < 1 || feedback.getRating() > 5) {
            throw new ApiException("error rating should be between 1 and 5 !");
        }

        // check if the request id exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(feedback.getRequestId());
        if (request == null) {
            throw new ApiException("no event request found with the that ID in the database!");
        }

        // check if the client is the owner of the request
        if (!request.getClientId().equals(feedback.getClientId())) {
            throw new ApiException("error client is not the owner of the request !");
        }

        // check if the feedback exists
        Feedback existingFeedback = feedbackRepository.findFeedbackByFeedbackId(feedbackId);
        if (existingFeedback == null) {
            throw new ApiException("no feedback found with the that ID in the database!");
        }

        // update
        existingFeedback.setRating(feedback.getRating());
        existingFeedback.setComment(feedback.getComment());

        // save
        feedbackRepository.save(existingFeedback);

    }


    // delete feedback from the database
    public void deleteFeedback(Integer feedbackId) {

        // check for input mismatch error
        if (feedbackId.toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, request ID should be an integer !");
        }

        // check if the feedback exists
        Feedback feedback = feedbackRepository.findFeedbackByFeedbackId(feedbackId);
        if (feedback == null) {
            throw new ApiException("no feedback found with the that ID in the database!");
        }

        // delete
        feedbackRepository.delete(feedback);

    }

    /// extra endpoints

    // 19 get all feedbacks for a specific request
    public List<Feedback> getFeedbacksByRequest(Integer requestId) {

        // check for input mismatch error
        if (requestId.toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, request ID should be an integer !");
        }

        // check if the request id exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (request == null) {
            throw new ApiException("no event request found with the that ID in the database!");
        }

        // check if there are feedbacks for this request
        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByRequestId(requestId);
        if (feedbacks.isEmpty()) {
            throw new ApiException ("no feedback found for this request !");
        }

        // check if empty
        if (feedbackRepository.findFeedbacksByRequestId(requestId).isEmpty()) {
            throw new ApiException("no feedback found for this request !");
        }

        return feedbackRepository.findFeedbacksByRequestId(requestId);
    }

    // 20- summarize feedbacks for a spesific request with Ai
    public String getAiFeedbackSummary(Integer requestId) {

        // check for input mismatch error
        if (requestId.toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, request ID should be an integer !");
        }


        // check if the request id exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (request == null) {
            throw new ApiException("no event request found with the that ID in the database!");
        }


        // check if there are feedbacks for this request
        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByRequestId(requestId);
        if (feedbacks.isEmpty()) {
            throw new ApiException ("no feedback found for this request !");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Summarize these feedback comments and ratings:\n");
        for (Feedback f : feedbacks) {
            sb.append("Rating: ").append(f.getRating())
                    .append(" Comment: ").append(f.getComment())
                    .append("\n");
        }

       return aiService.generateResponse(sb.toString());
    }


    //22 detect bad feedback and suggest improvements with Ai
    public String getAiNegativeFeedbackAnalysis() {

        // check if there are feedbacks in the system
        List<Feedback> feedbacks = feedbackRepository.findAll();
        if (feedbacks.isEmpty()) {
            throw new ApiException("no feedback in the system yet, nothing to analyze !");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Analyze this feedback and detect negative issues:\n\n");

        for (Feedback f : feedbacks) {
            sb.append("Rating: ").append(f.getRating()).append("\n").append("Comment: ").append(f.getComment()).append("\n\n");
        }

        sb.append("Please detect negative patterns and recommend improvements ").append("for the company management and representative teams.");

         return aiService.generateResponse(sb.toString());
    }

}
