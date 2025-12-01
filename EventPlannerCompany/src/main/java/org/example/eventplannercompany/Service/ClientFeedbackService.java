package org.example.eventplannercompany.Service;

import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.Model.Client;
import org.example.eventplannercompany.Model.ClientFeedback;
import org.example.eventplannercompany.Model.ClientRequest;
import org.example.eventplannercompany.Repository.ClientFeedbackRepository;
import org.example.eventplannercompany.Repository.ClientRepository;
import org.example.eventplannercompany.Repository.ClientRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientFeedbackService {


    // connect to the DataBase
    private final ClientFeedbackRepository clientFeedbackRepository;
    private final ClientRepository clientRepository;
    private final ClientRequestRepository clientRequestRepository;
    private final AiService aiService;


    // get all feedback from the database
    public List<ClientFeedback> getAllFeedbacks(){
        return clientFeedbackRepository.findAll();
    }

    // add feedback to the DataBase
    public int addFeedback(ClientFeedback feedback){

        // check if client exists
        Client client = clientRepository.findClientByClientId(feedback.getClientId());
        if (client == null){
            return 1;
        }

        //check if request is there
        ClientRequest request = clientRequestRepository.findClientRequestByClientRequestId(
                feedback.getClientRequestId());
        if (request == null){
            return 2;
        }

        // save it
        clientFeedbackRepository.save(feedback);
        return 3;
    }

    // update feedback within the DataBase
    public int updateFeedback(Integer feedbackId , ClientFeedback feedback){

        //check if feedback exists
        ClientFeedback old = clientFeedbackRepository.findClientFeedbackByClientFeedbackId(feedbackId);
        if (old == null){
            return 1; // feedback not found
        }

        //check if client exists
        Client client = clientRepository.findClientByClientId(feedback.getClientId());
        if (client == null){
            return 2; // client not found
        }

        //check if request exists
        ClientRequest request = clientRequestRepository.findClientRequestByClientRequestId(
                feedback.getClientRequestId());
        if (request == null){
            return 3;
        }

        // update
        old.setRating(feedback.getRating());
        old.setFeedbackText(feedback.getFeedbackText());


        //save
        clientFeedbackRepository.save(old);
        return 4;
    }

    // delete feedback from DataBase
    public int deleteFeedback(Integer feedbackId){

        // find feedback
        ClientFeedback feedback = clientFeedbackRepository.findClientFeedbackByClientFeedbackId(feedbackId);
        if (feedback == null){
            return 1;
        }

        clientFeedbackRepository.delete(feedback);
        return 2;
    }

    /// extra endpoints

    //1 - get low rating feedback
    public List<ClientFeedback> getLowRatingFeedbacks(Integer threshold){
        return clientFeedbackRepository.findClientFeedbacksByRatingLessThan(threshold);
    }

    // 2- - get feedbacks if it contains a specific keyword
    public List<ClientFeedback> searchFeedbackByKeyword(String keyword){
        return clientFeedbackRepository.searchFeedbackByKeyword(keyword);
    }

    // 3- get average rating for a specific client
    public Double getAverageRatingForClient(Integer clientId){
        return clientFeedbackRepository.getAverageRatingForClient(clientId);
    }

    // 4 - get average rating for event type
    public Double getAverageRatingByEventType(String eventType){
        return clientFeedbackRepository.getAverageRatingByEventType(eventType);
    }

    // 5 - get help from openAI to analyze negative feedback
    public String analyzeNegativeFeedback(){

        List<ClientFeedback> list = clientFeedbackRepository.getAllNegativeFeedback();

        if (list.isEmpty()){
            return "There is no negative feedback in the DataBase !";
        }

        StringBuilder sb = new StringBuilder(
                "Analyze these negative feedback comments and suggest improvements:\n\n");

        for (ClientFeedback fb : list){
            sb.append("Rating: ").append(fb.getRating())
                    .append("\nComment: ").append(fb.getFeedbackText()).append("\n\n");
        }

        return aiService.askAI(sb.toString());
    }
}
