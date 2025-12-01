package org.example.eventplannercompany.Service;

import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.Model.Client;
import org.example.eventplannercompany.Repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    // connect to the DataBase
    private final ClientRepository clientRepository;
    private final AiService aiService;


    // get all clients information from the DataBase
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    // add a client information to the DataBase
    public boolean addClient(Client client){

        // check if email is already used in the DataBase
        if (clientRepository.findClientByClientEmail(client.getClientEmail()) != null) {
            return false;
        }

        clientRepository.save(client);
        return true ;
    }

    // update a client information in the DataBase
    public int updateClient(Integer clientId , Client client){

        // find the client first
        Client old = clientRepository.findClientByClientId(clientId);
        if (old == null){
            return 1;
        }

        // check if email is used in the DataBase
        Client checkEmail = clientRepository.findClientByClientEmail(client.getClientEmail());
        if (checkEmail != null && !checkEmail.getClientId().equals(clientId)){
            return 2;
        }

        // update its information
        old.setClientName(client.getClientName());
        old.setClientPhoneNumber(client.getClientPhoneNumber());
        old.setClientEmail(client.getClientEmail());


        // save it
        clientRepository.save(old);
        return 3;
    }

    // delete a client information from the DataBase
    public boolean deleteClient(Integer clientId){

        // find the client first
        Client client = clientRepository.findClientByClientId(clientId);
        if (client == null){
            return false;
        }

        // delete it
        clientRepository.delete(client);
        return true;
    }

    // extra endpoints :

    // 1- search client by name
    public List<Client> searchClientsByName(String name){
        return clientRepository.findClientsByClientNameContainingIgnoreCase(name);
    }

    // 2 - search client by phone
    public Client searchClientsByPhone(String phone){
        return clientRepository.findClientsByClientPhoneNumber(phone);
    }

    // 3- count the requests made by client
    public Integer getClientRequestCount(Integer clientId){
        return clientRepository.countRequestsForClient(clientId);
    }

    // 4- get client that have the same following email domain
    public List<Client> getClientsByEmailDomain(String domain){
        return clientRepository.getClientsByEmailDomain(domain);
    }

    // 5- use openAI to describe and classify client by id
    public String classifyClient(Integer clientId){

        // find him
        Client client = clientRepository.findClientByClientId(clientId);
        if (client == null){
            return "Client ID not found in the DataBase !";
        }

        // ask openAI
        Integer count = clientRepository.countRequestsForClient(clientId);

        String prompt =
                "Classify the following client based on event history:\n\n" +
                        "Name: " + client.getClientName() + "\n" +
                        "Email: " + client.getClientEmail() + "\n" +
                        "Phone: " + client.getClientPhoneNumber() + "\n" +
                        "Total Requests: " + count + "\n\n" +
                        "Classification Rules:\n" +
                        "- Low-budget\n" +
                        "- Medium-budget\n" +
                        "- Premium\n" +
                        "- Corporate\n\n" +
                        "Explain the classification briefly.";

        // print the answer
        return aiService.askAI(prompt);
    }


}
