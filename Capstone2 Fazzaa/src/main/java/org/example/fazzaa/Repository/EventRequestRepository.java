package org.example.fazzaa.Repository;

import org.example.fazzaa.Model.EventRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRequestRepository extends JpaRepository<EventRequest, Integer> {

    EventRequest findEventRequestByRequestId(Integer requestId);

    List<EventRequest> findEventRequestsByClientId(Integer clientId);

    List<EventRequest> findEventRequestsByRequestStatusIgnoreCase(String status);

    List<EventRequest> findEventRequestsByEventDateBetween(LocalDate start, LocalDate end);

    List<EventRequest> findEventRequestsByBudgetBetween(Double minBudget, Double maxBudget);


}
