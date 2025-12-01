package org.example.eventplannercompany.Service;

import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.Model.Client;
import org.example.eventplannercompany.Model.ClientRequest;
import org.example.eventplannercompany.Repository.ClientRepository;
import org.example.eventplannercompany.Repository.ClientRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientRequestService {

    // connect to the DataBase
    private final ClientRequestRepository clientRequestRepository;
    private final ClientRepository clientRepository;
    private final AiService aiService;


    // get all clients requests information from the DataBase
    public List<ClientRequest> getAllClientRequests(){
        return clientRequestRepository.findAll();
    }



    // add a client request information to the DataBase
    public int addClientRequest(ClientRequest request){

        // check if client email is there
        if (request.getClientEmail() == null || request.getClientEmail().isEmpty()){
            return 1;
        }

        // check if client there
        Client client = clientRepository.findClientByClientEmail(request.getClientEmail());
        if (client == null){
            return 2;
        }

        // assign request to pending
        request.setClientId(client.getClientId());
        request.setClientRequestStatus("PENDING");

        // save information
        clientRequestRepository.save(request);
        return 3;
    }

    // update client request information within the DataBase
    public int updateClientRequest(Integer requestId , ClientRequest request){

        // check if request exists
        ClientRequest old = clientRequestRepository.findClientRequestByClientRequestId(requestId);
        if (old == null){
            return 1;
        }

        // update request when status = PENDING
        if (!"PENDING".equalsIgnoreCase(old.getClientRequestStatus())){
            return 2;
        }

        // update information
        old.setClientRequestTheme(request.getClientRequestTheme());
        old.setClientRequestType(request.getClientRequestType());
        old.setClientRequestDate(request.getClientRequestDate());
        old.setClientRequestBudget(request.getClientRequestBudget());
        old.setClientRequestPlace(request.getClientRequestPlace());
        old.setClientRequestEstimatedGuest(request.getClientRequestEstimatedGuest());
        old.setClientRequestDescription(request.getClientRequestDescription());

        // 4- save it
        clientRequestRepository.save(old);
        return 3;
    }


    // delete the client reques t information from the DataBase
    public boolean deleteClientRequest(Integer requestId){

        // find the client
        ClientRequest request = clientRequestRepository.findClientRequestByClientRequestId(requestId);
        if (request == null){
            return false;
        }

        // delete it
        clientRequestRepository.delete(request);
        return true;
    }

    /// extra endpoints

    // 1-  get all requests for specific client
    public List<ClientRequest> getRequestsForClient(Integer clientId){
        return clientRequestRepository.findClientRequestsByClientId(clientId);
    }

    //2- - get all pending requests
    public List<ClientRequest> getPendingRequestsForClient(Integer clientId){
        return clientRequestRepository.findClientRequestsByClientIdAndClientRequestStatus(clientId, "PENDING");
    }

    // 3 -get requests within a budget range
    public List<ClientRequest> getRequestsByBudgetForClient(Integer clientId, Double min, Double max){
        return clientRequestRepository.findClientRequestsByClientIdAndClientRequestBudgetBetween(clientId, min, max);
    }

    // 4- get requests by date range
    public List<ClientRequest> getRequestsByDateRangeForClient(Integer clientId, LocalDateTime start, LocalDateTime end){
        return clientRequestRepository.findRequestsForClientWithinDateRange(clientId, start, end);
    }

    // 5 - get top themes requested
    public List<String> getTopThemes(){
        return clientRequestRepository.getTopRequestedThemes();
    }

    // 6- get help from openAI to give expected cost budget
    public String predictCost(Integer requestId){

        ClientRequest r = clientRequestRepository.findClientRequestByClientRequestId(requestId);

        if (r == null){
            return "Client Request ID not found in the DataBase !";
        }

        String prompt =
                "Predict the ideal event budget for:\n" +
                        "Type: " + r.getClientRequestType() + "\n" +
                        "Theme: " + r.getClientRequestTheme() + "\n" +
                        "Guests: " + r.getClientRequestEstimatedGuest() + "\n" +
                        "Location: " + r.getClientRequestPlace();

        return aiService.askAI(prompt);
    }

    // 7 - get help with openAI to see theme trends events
    public String summarizeTrends(){

        List<String> themes = clientRequestRepository.getTopRequestedThemes();

        if (themes.isEmpty()){
            return "There are no client requests in the DataBase to analyze trends !";
        }

        StringBuilder sb = new StringBuilder("Summarize event planning trends based on these themes:\n\n");
        for (String t : themes){
            sb.append("Theme: ").append(t).append("\n");
        }

        return aiService.askAI(sb.toString());
    }
}
