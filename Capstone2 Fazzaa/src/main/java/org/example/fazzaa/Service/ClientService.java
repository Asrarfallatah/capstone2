package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiException;
import org.example.fazzaa.Model.Client;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Model.Feedback;
import org.example.fazzaa.Repository.ClientRepository;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    // connect to the database
    private final ClientRepository clientRepository;
    private final EventRequestRepository eventRequestRepository;
    private final FeedbackRepository feedbackRepository;

    // get all clients from the database
    public List<Client> getAllClients() {

        // check if there are clients
        if (clientRepository.findAll().isEmpty()) {
            throw new ApiException("no client found in the database yet !");
        }

        return clientRepository.findAll();
    }

    // add a client to the database
    public void addClient(Client client) {

        // check for inputMismatch errors
        if (client.getClientName().contains("^[0-9]+$")) {
            throw new ApiException("error client name must be string only !");
        }else if (client.getClientPhone().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error client phone must contain numbers only !");
        }else if (client.getClientEmail().contains("^[0-9]+$")) {
            throw new ApiException("error client email must contain string values !");
        }

        //check for null validation
        if (client.getClientName() == null ){
            throw new ApiException("error client name cant be empty !");
        } else if (client.getClientEmail() == null) {
            throw new ApiException("error client email cant be empty !");
        } else if (client.getClientPhone() == null) {
            throw new ApiException("error client phone cant be empty !");
        }

        // check name length > 4
        if (client.getClientName().length() < 4) {
            throw new ApiException("error client name cant be less than 4 characters !");
        }

        // check email format
        if (!client.getClientEmail().contains("@") || !client.getClientEmail().contains(".")) {
            throw new ApiException("error client email format is incorrect !");
        }

        // check phone length = 10
        if (client.getClientPhone().length() != 10) {
            throw new ApiException("error client phone must be 10 digits only !");
        }

        // check phone contains only digits
        if (!client.getClientPhone().matches("\\d+")) {
            throw new ApiException("error client phone must contain only numbers !");
        }

        // check if email is not used
        Client existingClientEmail = clientRepository.findClientByClientEmail(client.getClientEmail());
        if (existingClientEmail == null) {
            throw new ApiException("error this email is already used by another client !");
        }

        // add it
        clientRepository.save(client);

    }

    // update client within the database
    public void updateClient(Integer clientId, Client client) {


        // check for inputMismatch errors
        if (client.getClientName().contains("^[0-9]+$")) {
            throw new ApiException("error client name must be string only !");
        }else if (client.getClientPhone().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error client phone must contain numbers only !");
        }else if (client.getClientEmail().contains("^[0-9]+$")) {
            throw new ApiException("error client email must contain string values !");
        }


        // check if client exist
        Client oldClient = clientRepository.findClientByClientId(clientId);
        if (oldClient == null) {
            throw new ApiException("error no client with that id is found in the database !");
        }

        // check for null validation
        if (client.getClientName() == null ){
            throw new ApiException("error client name cant be empty !");
        } else if (client.getClientEmail() == null) {
            throw new ApiException("error client email cant be empty !");
        } else if (client.getClientPhone() == null) {
            throw new ApiException("error client phone cant be empty !");
        }

        // check name length > 4
        if (client.getClientName().length() < 4) {
            throw new ApiException("error client name cant be less than 4 characters !");
        }

        // check email format
        if (!client.getClientEmail().contains("@") || !client.getClientEmail().contains(".")) {
            throw new ApiException("error client email format is incorrect !");
        }

        // check phone length = 10
        if (client.getClientPhone().length() != 10) {
            throw new ApiException("error client phone must be 10 digits only !");
        }

        // check phone contains only digits
        if (!client.getClientPhone().matches("\\d+")) {
            throw new ApiException("error client phone must contain only numbers !");
        }


        // check if email is not used by another client
        Client emailOwner = clientRepository.findClientByClientEmail(client.getClientEmail());
        if (emailOwner != null && !emailOwner.getClientId().equals(clientId)) {
           throw new ApiException("error this email is already used by another client !");
        }



        // update
        oldClient.setClientName(client.getClientName());
        oldClient.setClientEmail(client.getClientEmail());
        oldClient.setClientPhone(client.getClientPhone());

        // save
        clientRepository.save(oldClient);

    }

    // delete client from the database
    public void deleteClient(Integer clientId) {

        // check for inputMismatch errors
        if (clientId.toString().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error client ID must be number only !");
        }

        // check if client exist
        Client client = clientRepository.findClientByClientId(clientId);
        if (client == null) {
          throw new ApiException("error no client with that id is found in the database !");
        }


        // delete
        clientRepository.delete(client);
    }

    /// extra endpoints

    // 1- get client by specific email
    public Client getClientByEmail(String email) {

        // check for inputMismatch errors
        if (email.contains("^[0-9]+$")) {
            throw new ApiException("error client email must contain string values !");
        }

        // check email format
        if (!email.contains("@") || !email.contains(".")) {
            throw new ApiException("error client email format is incorrect !");
        }

        // check if email exists
        Client client = clientRepository.findClientByClientEmail(email);
        if (client == null) {
            throw new ApiException("error no client with that email is found in the database !");
        }

        return client;
    }

    // 2- count how many requests a specific client has made
    public int countClientRequests(Integer clientId) {

        // check for inputMismatch errors
        if (clientId.toString().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error client ID must be number only !");
        }

        // check if client exist
        Client client = clientRepository.findClientByClientId(clientId);
        if (client == null) {
            throw new ApiException("error no client with that id is found in the database !");
        }

        // check if there are requests
        List<EventRequest> requests = eventRequestRepository.findEventRequestsByClientId(clientId);
        if (requests.isEmpty()) {
            throw new ApiException("error this client has no requests in the database !");
        }

        return requests.size();
    }

    //3-  count how many feedbacks a specific client has written
    public int countClientFeedbacks(Integer clientId) {

        // check for inputMismatch errors
        if (clientId.toString().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error client ID must be number only !");
        }

        // check if client exist
        Client client = clientRepository.findClientByClientId(clientId);
        if (client == null) {
            throw new ApiException("error no client with that id is found in the database !");
        }

        // check if there are feedbacks
        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByClientId(clientId);
        if (feedbacks.isEmpty()) {
            throw new ApiException("error this client has no feedbacks in the database !");
        }
        return feedbacks.size();
    }
}
