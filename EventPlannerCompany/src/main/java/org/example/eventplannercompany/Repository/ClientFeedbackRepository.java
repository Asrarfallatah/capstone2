package org.example.eventplannercompany.Repository;

import org.example.eventplannercompany.Model.ClientFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientFeedbackRepository extends JpaRepository<ClientFeedback, Integer> {


    ClientFeedback findClientFeedbackByClientFeedbackId(Integer feedbackId);

    /// JPA

        // get rating feedback lower than a specific number
        List<ClientFeedback> findClientFeedbacksByRatingLessThan(Integer rating);

    /// JPQL

        // see all feedbacks that contain a specific keyword
        @Query("select f from ClientFeedback f where lower(f.feedbackText) like lower(concat('%', ?1, '%'))")
        List<ClientFeedback> searchFeedbackByKeyword(String keyword);

        // get all feedback that their rating is lower than 3
        @Query("select f from ClientFeedback f where f.rating <= 2")
        List<ClientFeedback> getAllNegativeFeedback();

        // see the average rating made for a specific client
        @Query("select avg(f.rating) from ClientFeedback f where f.clientId = ?1")
        Double getAverageRatingForClient(Integer clientId);

        // get average rating for a specific event type
        @Query("select avg(f.rating) from ClientFeedback f, ClientRequest r where f.clientRequestId = r.clientRequestId and r.clientRequestType = ?1")
        Double getAverageRatingByEventType(String eventType);
}
