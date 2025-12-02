package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiResponse;
import org.example.fazzaa.Model.Client;
import org.example.fazzaa.Service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {


    private final ClientService clientService;


    @GetMapping("/get")
    public ResponseEntity<?> getAllClients() {

        return ResponseEntity.status(200).body(clientService.getAllClients());

    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addClient(@Valid @RequestBody Client client) {

        clientService.addClient(client);
        return ResponseEntity.status(200).body(new ApiResponse("client added successfully !"));

    }

   // update
    @PutMapping("/update/{clientId}")
    public ResponseEntity<?> updateClient(@PathVariable Integer clientId, @Valid @RequestBody Client client) {

        clientService.updateClient(clientId, client);
        return ResponseEntity.status(200).body(new ApiResponse("client information has been updated successfully in the database !"));
    }

    // delete
    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable Integer clientId) {

        clientService.deleteClient(clientId);
        return ResponseEntity.status(200).body(new ApiResponse("client information has been deleted successfully from the database !"));
    }

    /// extra endpoints

    // 1- get client by specific email
    @GetMapping("/search/email/{email}")
    public ResponseEntity<?> getClientByEmail(@PathVariable String email) {

        return ResponseEntity.status(200).body(clientService.getClientByEmail(email));
    }

    // 2- count how many requests a specific client has made
    @GetMapping("/count/requests/{clientId}")
    public ResponseEntity<?> countClientRequests(@PathVariable Integer clientId) {

        return ResponseEntity.status(200).body( new ApiResponse(" client made "+clientService.countClientRequests(clientId) + " requests !"));

    }

    //3-  count how many feedbacks a specific client has written
    @GetMapping("/count/feedback/{clientId}")
    public ResponseEntity<?> countClientFeedback(@PathVariable Integer clientId) {

        return ResponseEntity.status(200).body(new ApiResponse("client made "+clientService.countClientFeedbacks(clientId) + " feedbacks"));
    }
}
