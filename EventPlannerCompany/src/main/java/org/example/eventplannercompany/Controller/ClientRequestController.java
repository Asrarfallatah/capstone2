package org.example.eventplannercompany.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.ApiResponse.ApiResponse;
import org.example.eventplannercompany.Model.ClientRequest;
import org.example.eventplannercompany.Service.ClientRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class ClientRequestController {

    private final ClientRequestService clientRequestService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getRequests(){

        if (clientRequestService.getAllClientRequests().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no client requests in the DataBase yet to show their information !"));
        }

        return ResponseEntity.status(200).body(clientRequestService.getAllClientRequests());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addRequest(@Valid @RequestBody ClientRequest request, Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = clientRequestService.addClientRequest(request);

        if (result == 1){
            return ResponseEntity.status(400).body(new ApiResponse("Client email can not be empty ! Please enter a valid email !"));
        }
        else if (result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("No client with this email is found in the DataBase to add a request !"));
        }
        else if (result == 3){
            return ResponseEntity.status(200).body(new ApiResponse("Client request has been added Successfully with status PENDING !"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Unknown error while adding client request !"));
    }

    // update
    @PutMapping("/update/{requestId}")
    public ResponseEntity<?> updateRequest(@PathVariable Integer requestId, @Valid @RequestBody ClientRequest request, Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = clientRequestService.updateClientRequest(requestId, request);

        if (result == 1){
            return ResponseEntity.status(400).body(new ApiResponse("No client request with this ID has been found in the DataBase to update it !"));
        }
        else if (result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Only PENDING client requests can be updated !"));
        }
        else if (result == 3){
            return ResponseEntity.status(200).body(new ApiResponse("Client request information has been updated Successfully !"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Unknown error while updating client request information !"));
    }

    // delete
    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer requestId){

        boolean deleted = clientRequestService.deleteClientRequest(requestId);

        if (!deleted){
            return ResponseEntity.status(400).body(new ApiResponse("No client request with this ID has been found in the DataBase to delete it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Client request information has been deleted Successfully !"));
    }

    /// EXTRA ENDPOINTS

    // 1 - get all requests for specific client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getClientRequests(@PathVariable Integer clientId){

        if (clientRequestService.getRequestsForClient(clientId).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("This client has no requests yet in the DataBase to show their information !"));
        }

        return ResponseEntity.status(200).body(clientRequestService.getRequestsForClient(clientId));
    }

    // 2 - get pending requests for a specific client
    @GetMapping("/pending/{clientId}")
    public ResponseEntity<?> getPendingRequestsForClient(@PathVariable Integer clientId){

        if (clientRequestService.getPendingRequestsForClient(clientId).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("This client has no pending requests in the DataBase to show their information !"));
        }

        return ResponseEntity.status(200).body(clientRequestService.getPendingRequestsForClient(clientId));
    }

    // 3 - get requests for a specific client within a budget range
    @GetMapping("/budget-range/{clientId}/{min}/{max}")
    public ResponseEntity<?> getRequestsByBudgetForClient(@PathVariable Integer clientId, @PathVariable Double min, @PathVariable Double max){

        if (clientRequestService.getRequestsByBudgetForClient(clientId, min, max).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("This client has no requests within this budget range to show their information !"));
        }

        return ResponseEntity.status(200).body(clientRequestService.getRequestsByBudgetForClient(clientId, min, max));
    }

    // 4 - get requests for a client between two dates
    @GetMapping("/date-range/{clientId}/{start}/{end}")
    public ResponseEntity<?> getRequestsByDateForClient(@PathVariable Integer clientId, @PathVariable String start, @PathVariable String end){

        LocalDateTime s = LocalDateTime.parse(start);
        LocalDateTime e = LocalDateTime.parse(end);

        if (clientRequestService.getRequestsByDateRangeForClient(clientId, s, e).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("This client has no requests between these two dates to show their information !"));
        }

        return ResponseEntity.status(200).body(clientRequestService.getRequestsByDateRangeForClient(clientId, s, e));
    }

    // 5 - get top themes from openAi
    @GetMapping("/top-themes")
    public ResponseEntity<?> getTopThemes(){

        if (clientRequestService.getTopThemes().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no theme requests yet in the DataBase to analyze them !"));
        }

        return ResponseEntity.status(200).body(clientRequestService.getTopThemes());
    }

    // 6 - get help from  openAI to predict cost of the theme
    @GetMapping("/predict-cost/{requestId}")
    public ResponseEntity<?> predictCost(@PathVariable Integer requestId){

        String answer = clientRequestService.predictCost(requestId);

        if (answer.contains("not found")){
            return ResponseEntity.status(400).body(new ApiResponse("No client request with this ID has been found in the DataBase !"));
        }

        return ResponseEntity.status(200).body(answer);
    }

    // 7 - get help from openAI to summarize trends
    @GetMapping("/trends")
    public ResponseEntity<?> summarizeTrends(){

        String answer = clientRequestService.summarizeTrends();

        if (answer.contains("no client requests")){
            return ResponseEntity.status(400).body(new ApiResponse("There are no client requests in the DataBase to analyze trends !"));
        }

        return ResponseEntity.status(200).body(answer);
    }
}
