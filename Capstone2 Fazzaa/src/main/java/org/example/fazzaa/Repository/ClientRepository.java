package org.example.fazzaa.Repository;

import org.example.fazzaa.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findClientByClientId(Integer clientId);




    Client findClientByClientEmail(String clientEmail);
}
