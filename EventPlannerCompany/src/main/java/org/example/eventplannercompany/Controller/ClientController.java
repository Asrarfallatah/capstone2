package org.example.eventplannercompany.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.ApiResponse.ApiResponse;
import org.example.eventplannercompany.Model.Client;
import org.example.eventplannercompany.Service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {


    private final ClientService clientService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getClients(){

        if (clientService.getAllClients().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no clients yet in the DataBase to show their information !"));
        }

        return ResponseEntity.status(200).body(clientService.getAllClients());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addClient(@Valid @RequestBody Client client, Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean added = clientService.addClient(client);

        if (!added){
            return ResponseEntity.status(400).body(new ApiResponse("This email is already used in the DataBase ! Please try again with a new email !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Client information has been added Successfully !"));
    }

    // update
    @PutMapping("/update/{clientId}")
    public ResponseEntity<?> updateClient(@PathVariable Integer clientId, @Valid @RequestBody Client client, Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = clientService.updateClient(clientId, client);

        if (result == 1){
            return ResponseEntity.status(400).body(new ApiResponse("No client with this ID has been found in the DataBase to update its information !"));
        }
        else if (result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("This email is already used by another client in the DataBase !"));
        }
        else if (result == 3){
            return ResponseEntity.status(200).body(new ApiResponse("Client information has been updated Successfully !"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Unknown error while updating client information !"));
    }

    // delete
    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable Integer clientId){

        boolean deleted = clientService.deleteClient(clientId);

        if (!deleted){
            return ResponseEntity.status(400).body(new ApiResponse("No client with this ID has been found in the DataBase to delete it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Client information has been deleted Successfully !"));
    }


    /// EXTRA ENDPOINTS

    // 5- search clients by name
    @GetMapping("/search-name/{name}")
    public ResponseEntity<?> searchClientsByName(@PathVariable String name){

        if (clientService.searchClientsByName(name).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no clients with this name in the DataBase to show their information !"));
        }

        return ResponseEntity.status(200).body(clientService.searchClientsByName(name));
    }


    // 6- search clients by phone number
    @GetMapping("/search-phone/{phone}")
    public ResponseEntity<?> searchClientsByPhone(@PathVariable String phone){

        Client c = clientService.searchClientsByPhone(phone);

        if (c == null){
            return ResponseEntity.status(400).body(new ApiResponse("There is no client with this phone number in the DataBase !"));
        }

        return ResponseEntity.status(200).body(c);
    }


    // 7- count number of requests made by client
    @GetMapping("/count-requests/{clientId}")
    public ResponseEntity<?> countClientRequests(@PathVariable Integer clientId){

        Integer count = clientService.getClientRequestCount(clientId);

        if (count == null){
            return ResponseEntity.status(400).body(new ApiResponse("No client with this ID has been found in the DataBase !"));
        }

        return ResponseEntity.status(200).body(count);
    }

    // 8- get clients by email domain
    @GetMapping("/email-domain/{domain}")
    public ResponseEntity<?> getByDomain(@PathVariable String domain){

        if (clientService.getClientsByEmailDomain(domain).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No clients with this email domain are found in the DataBase !"));
        }

        return ResponseEntity.status(200).body(clientService.getClientsByEmailDomain(domain));
    }

    // 9- use OpenAI to classify a client
    @GetMapping("/classify/{clientId}")
    public ResponseEntity<?> classifyClient(@PathVariable Integer clientId){

        String result = clientService.classifyClient(clientId);

        if (result.contains("not found")){
            return ResponseEntity.status(400).body(new ApiResponse("No client with this ID has been found in the DataBase !"));
        }

        return ResponseEntity.status(200).body(result);
    }
}
