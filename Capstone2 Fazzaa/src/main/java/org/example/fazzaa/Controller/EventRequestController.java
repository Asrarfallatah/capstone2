package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiResponse;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Service.EventRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/event-request")
@RequiredArgsConstructor
public class EventRequestController {


    private final EventRequestService eventRequestService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getAllRequests() {

        if (eventRequestService.getAllEventRequests().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("error no event requests is found yet in the database to show their information !"));
        }

        return ResponseEntity.status(200).body(eventRequestService.getAllEventRequests());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addRequest(@Valid @RequestBody EventRequest request) {

        eventRequestService.addEventRequest(request);
        return ResponseEntity.status(200).body(new ApiResponse("event request added successfully !"));
    }

    // update
    @PutMapping("/update/{requestId}")
    public ResponseEntity<?> updateRequest(@PathVariable Integer requestId, @Valid @RequestBody EventRequest request) {

        eventRequestService.updateEventRequest(requestId, request);
        return ResponseEntity.status(200).body(new ApiResponse("event request has been updated in the database successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer requestId) {

       eventRequestService.deleteEventRequest(requestId);

        return ResponseEntity.status(200).body(new ApiResponse("event request deleted successfully !"));
    }

    ///  Extra endpoint
    ///


    // 4- get pending requests
    @GetMapping("/get/pending")
    public ResponseEntity<?> getPendingRequests() {

        return ResponseEntity.status(200).body(eventRequestService.getPendingRequests());
    }

    // 5- get by status
    @GetMapping("/get/status/{status}")
    public ResponseEntity<?> getRequestsByStatus(@PathVariable String status) {

        return ResponseEntity.status(200).body(eventRequestService.getRequestsByStatus(status));
    }

    //6- get by date range
    @GetMapping("/get/date-range/{start}/{end}")
    public ResponseEntity<?> getByDateRange(@PathVariable String start, @PathVariable String end) {

        return ResponseEntity.status(200).body(eventRequestService.getRequestsByDateRange(LocalDate.parse(start), LocalDate.parse(end)));
    }

    //7- get by budget range
    @GetMapping("/get/budget/{min}/{max}")
    public ResponseEntity<?> getByBudgetRange(@PathVariable Double min, @PathVariable Double max) {

        return ResponseEntity.status(200).body(eventRequestService.getRequestsByBudgetRange(min, max));
    }

    //8- change status
    @PutMapping("/status/{requestId}/{newStatus}")
    public ResponseEntity<?> changeStatus(@PathVariable Integer requestId, @PathVariable String newStatus) {

        eventRequestService.changeRequestStatus(requestId, newStatus);

        return ResponseEntity.status(200).body(new ApiResponse("status changed successfully !"));
    }

    // 9- event plan suggestion from Ai
    @GetMapping("/ai-suggestion/{requestId}")
    public ResponseEntity<?> getAiSuggestion(@PathVariable Integer requestId) {

        eventRequestService.getAiEventPlanSuggestion(requestId);

        return ResponseEntity.status(200).body( new ApiResponse( eventRequestService.getAiEventPlanSuggestion(requestId)  ));
    }

    // // 21- get event trends among clients
    @GetMapping("/ai-trends")
    public ResponseEntity<?> getEventTrends() {

        return ResponseEntity.status(200).body(new ApiResponse( eventRequestService.getAiEventTrends() ));
    }
}
