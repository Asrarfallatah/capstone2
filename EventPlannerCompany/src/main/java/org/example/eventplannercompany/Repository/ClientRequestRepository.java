package org.example.eventplannercompany.Repository;

import org.example.eventplannercompany.Model.ClientRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientRequestRepository extends JpaRepository<ClientRequest, Integer> {

    ClientRequest findClientRequestByClientRequestId(Integer requestId);

    ///JPA

        //get all requests for a specific client
        List<ClientRequest> findClientRequestsByClientId(Integer clientId);

        //get requests that have a specific status for a client
        List<ClientRequest> findClientRequestsByClientIdAndClientRequestStatus(Integer clientId, String status);

        //see requests by budget range
        List<ClientRequest> findClientRequestsByClientIdAndClientRequestBudgetBetween(Integer clientId, Double min, Double max);

    /// JPQL

        //get requests between two specific dates
        @Query("select r from ClientRequest r where r.clientId = ?1 and r.clientRequestDate between ?2 and ?3")
        List<ClientRequest> findRequestsForClientWithinDateRange(Integer clientId, LocalDateTime start, LocalDateTime end);

        //get the most requested themes by our clients
        @Query("select r.clientRequestTheme from ClientRequest r group by r.clientRequestTheme order by count(r) desc")
        List<String> getTopRequestedThemes();
}

