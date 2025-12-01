package org.example.eventplannercompany.Repository;

import org.example.eventplannercompany.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findClientByClientId(Integer clientId);

    ///  JPA

        // find a client by email
        Client findClientByClientEmail(String clientEmail);

        // look for clients who have this name in their name
        List<Client> findClientsByClientNameContainingIgnoreCase(String name);

        // find clients by a specific email
        Client findClientsByClientPhoneNumber(String phone);

    /// JPQL

        //get all clients in alphabetical order A to Z
        @Query("select c from Client c order by c.clientName asc")
        List<Client> getClientsSortedByName();

        //count how many requests a client made
        @Query("select count(r) from ClientRequest r where r.clientId = ?1")
        Integer countRequestsForClient(Integer clientId);

        //see clients who have the same email domain
        @Query("select c from Client c where c.clientEmail like %?1%")
        List<Client> getClientsByEmailDomain(String domain);
}


