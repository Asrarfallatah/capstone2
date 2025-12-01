package org.example.eventplannercompany.Repository;

import org.example.eventplannercompany.Model.CompanyRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepresentativeRepository extends JpaRepository<CompanyRepresentative, Integer> {


    CompanyRepresentative findCompanyRepresentativeByRepresentativeTeamId(Integer teamId);


    ///  JPQL

        //find a representative team by a specific phone number
        @Query("select r from CompanyRepresentative r where r.representativeTeamPhoneNumber = ?1")
        CompanyRepresentative getTeamByPhoneNumber(String phone);

        // see how many requests were assigned to this team
        @Query("select count(m) from CompanyManagement m where m.assignedRepresentativeTeamId = ?1")
        Integer countEventsHandled(Integer teamId);
}
