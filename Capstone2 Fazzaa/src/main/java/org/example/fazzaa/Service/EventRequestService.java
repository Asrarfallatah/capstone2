package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiException;
import org.example.fazzaa.Model.Client;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Repository.ClientRepository;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventRequestService {


    // connect to database
    private final EventRequestRepository eventRequestRepository;
    private final ClientRepository clientRepository;
    private final AiService aiService;


    // get all event requests from database
    public List<EventRequest> getAllEventRequests() {

        // check if there is any event request in the database
        if (eventRequestRepository.findAll().isEmpty()) {
            throw new ApiException("error no event requests is found yet in the database !");
        }

        return eventRequestRepository.findAll();
    }

    // add event request to the database
    public void addEventRequest(EventRequest eventRequest) {


        // check input mismatch errors
        if (eventRequest.getEventDate().toString().contains("^[a-zA-Z]+$")){
            throw new ApiException("error event date format can not have characters !");
        }else if (eventRequest.getBudget().toString().matches("^-?\\d+(\\.\\d+)?$")){
            throw new ApiException("error budget must be a number !");
        }else if (eventRequest.getEstimatedGuests().toString().matches("^-?\\d+$")){
            throw new ApiException("error estimated guest must be an integer number !");
        }else if (eventRequest.getClientId().toString().matches("^-?\\d+$")){
            throw new ApiException("error client ID must be an integer number !");
        }

        // set status to pending by default
        eventRequest.setRequestStatus("PENDING");

        // check null fields
        if (eventRequest.getEventType() == null){
            throw new ApiException("error event type can not be empty !");
        }else if (eventRequest.getEventDate() == null){
            throw new ApiException("error event date can not be null !");
        }else if (eventRequest.getTheme() == null){
            throw new ApiException("error event theme can not be empty !");
        }else if (eventRequest.getBudget() == null){
            throw new ApiException("error budget can not be empty !");
        }else if (eventRequest.getPlace() == null){
            throw new ApiException("error place can not be empty !");
        }else if (eventRequest.getEstimatedGuests() == null){
            throw new ApiException("error estimated guest can not be null !");
        }else if (eventRequest.getDescription() == null){
            throw new ApiException("error description can not be empty !");
        }else if (eventRequest.getClientId() == null){
            throw new ApiException("error client ID can not be null !");
        }else if (eventRequest.getRequestStatus()== null){
            throw new ApiException("error request status can not be null !");
        }

        // check if client exists
        Client client = clientRepository.findClientByClientId(eventRequest.getClientId());
        if (client == null) {
           throw new ApiException("error no client found with the that ID in the database !");
        }

        // check if event date is in the past
        if (eventRequest.getEventDate().isBefore(LocalDate.now())) {
            throw new ApiException("error event date can not be in the past !");
        }


        // save
        eventRequestRepository.save(eventRequest);
    }

    // update event request within the database
    public void updateEventRequest(Integer requestId, EventRequest eventRequest) {

        // input mismatch errors
        if (eventRequest.getEventDate().toString().contains("^[a-zA-Z]+$")){
            throw new ApiException("error event date format can not have characters !");
        }else if (eventRequest.getBudget().toString().matches("^-?\\d+(\\.\\d+)?$")){
            throw new ApiException("error budget must be a number !");
        }else if (eventRequest.getEstimatedGuests().toString().matches("^-?\\d+$")){
            throw new ApiException("error estimated guest must be an integer number !");
        }else if (eventRequest.getClientId().toString().matches("^-?\\d+$")){
            throw new ApiException("error client ID must be an integer number !");
        }

        // check null fields
        if (eventRequest.getEventType() == null){
            throw new ApiException("error event type can not be empty !");
        }else if (eventRequest.getEventDate() == null){
            throw new ApiException("error event date can not be null !");
        }else if (eventRequest.getTheme() == null){
            throw new ApiException("error event theme can not be empty !");
        }else if (eventRequest.getBudget() == null){
            throw new ApiException("error budget can not be empty !");
        }else if (eventRequest.getPlace() == null){
            throw new ApiException("error place can not be empty !");
        }else if (eventRequest.getEstimatedGuests() == null){
            throw new ApiException("error estimated guest can not be null !");
        }else if (eventRequest.getDescription() == null){
            throw new ApiException("error description can not be empty !");
        }else if (eventRequest.getClientId() == null){
            throw new ApiException("error client ID can not be null !");
        }else if (eventRequest.getRequestStatus()== null){
            throw new ApiException("error request status can not be null !");
        }


        // check if request exists
        EventRequest oldRequest = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (oldRequest == null) {
            throw new ApiException("error no event request found with the that ID in the database !");
        }

        // check if client exists
        Client client = clientRepository.findClientByClientId(eventRequest.getClientId());
        if (client == null) {
           throw new ApiException("error no client found with the that ID in the database !");
        }

        // update
        oldRequest.setEventType(eventRequest.getEventType());
        oldRequest.setEventDate(eventRequest.getEventDate());
        oldRequest.setTheme(eventRequest.getTheme());
        oldRequest.setBudget(eventRequest.getBudget());
        oldRequest.setPlace(eventRequest.getPlace());
        oldRequest.setEstimatedGuests(eventRequest.getEstimatedGuests());
        oldRequest.setDescription(eventRequest.getDescription());
        oldRequest.setClientId(eventRequest.getClientId());

        // save
        eventRequestRepository.save(oldRequest);

    }

    // delete event request from the database
    public void deleteEventRequest(Integer requestId) {

        // check input mismatch error
        if (requestId.toString().matches("^-?\\d+$")){
            throw new ApiException("error request ID must be an integer number !");
        }

        // check if request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (request == null) {
            throw new ApiException("error no event request with that id is found in the database !");
        }

        // delete
        eventRequestRepository.delete(request);

    }

    ///  Extra endpoint

    //4- get all pending requests
    public List<EventRequest> getPendingRequests() {

        // check if there is any pending requests
        if (eventRequestRepository.findEventRequestsByRequestStatusIgnoreCase("PENDING").isEmpty()){
            throw new ApiException("error no pending event requests is found in the database to show their information !");
        }

        return eventRequestRepository.findEventRequestsByRequestStatusIgnoreCase("PENDING");
    }

    //5- get requests by status (APPROVED / REJECTED / COMPLETED / WITHDRAWN)
    public List<EventRequest> getRequestsByStatus(String status) {

        // check if there is any requests with that status
        if (eventRequestRepository.findEventRequestsByRequestStatusIgnoreCase(status).isEmpty()){
            throw new ApiException("error no event requests with status "+status+" is found in the database to show their information !");
        }

        return eventRequestRepository.findEventRequestsByRequestStatusIgnoreCase(status);
    }

    //6- get requests between two dates
    public List<EventRequest> getRequestsByDateRange(LocalDate startDate, LocalDate endDate) {

        // check if there is any requests between those dates
        if (eventRequestRepository.findEventRequestsByEventDateBetween(startDate, endDate).isEmpty()){
            throw new ApiException("error no event requests between dates "+startDate+" and "+endDate+" is found in the database to show their information !");
        }

        return eventRequestRepository.findEventRequestsByEventDateBetween(startDate, endDate);
    }

    //7- get requests in a budget range
    public List<EventRequest> getRequestsByBudgetRange(Double minBudget, Double maxBudget) {

        if (eventRequestRepository.findEventRequestsByBudgetBetween(minBudget, maxBudget).isEmpty()){
            throw new ApiException("error no event requests in the budget range "+minBudget+" to "+maxBudget+" is found in the database to show their information !");
        }

        return eventRequestRepository.findEventRequestsByBudgetBetween(minBudget, maxBudget);
    }

    //8- change request status
    public void changeRequestStatus(Integer requestId, String newStatus) {

        // check input mismatch error
        if (requestId.toString().matches("^-?\\d+$")){
            throw new ApiException("error request ID must be an integer number !");
        }

        //check if request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (request == null) {
           throw new ApiException("error no event request found with the that ID in the database !");
        }

        request.setRequestStatus(newStatus);
        eventRequestRepository.save(request);
    }

    //9- get suggest event idea or plan with Ai
    public String getAiEventPlanSuggestion(Integer requestId) {

        // check input mismatch error
        if (requestId.toString().matches("^-?\\d+$")){
            throw new ApiException("error request ID must be an integer number !");
        }

        // check if request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (request == null) {
            throw new ApiException("error no event request with that id is found in the database !");
        }

        String prompt =
                "Suggest a creative event plan based on this data:\n" +
                        "Event type: " + request.getEventType() + "\n" +
                        "Theme: " + request.getTheme() + "\n" +
                        "Budget: " + request.getBudget() + "\n" +
                        "Place: " + request.getPlace() + "\n" +
                        "Estimated guests: " + request.getEstimatedGuests() + "\n" +
                        "Description: " + request.getDescription();

        return aiService.generateResponse(prompt);
    }


    // 21  get Event trend Suggestion from Ai
    public String getAiEventTrends() {

        // check if there is any event requests in the database
        List<EventRequest> requests = eventRequestRepository.findAll();
        if (requests.isEmpty()) {
            throw new ApiException ("error no event requests yet, no trends available !");
        }


        Map<String, Integer> countMap = new HashMap<>();
        for (EventRequest r : requests) {
            countMap.put(r.getEventType(), countMap.getOrDefault(r.getEventType(), 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Analyze these event trends:\n");
        for (String type : countMap.keySet()) {
            sb.append("Event: ").append(type).append(" | Count: ").append(countMap.get(type)).append("\n");
        }

        sb.append("\nSuggest creative ways to improve these events, ").append("increase fun, and boost client satisfaction.");

         return aiService.generateResponse(sb.toString());
    }



}
