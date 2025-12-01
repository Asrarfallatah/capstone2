package org.example.eventplannercompany.Repository;

import org.example.eventplannercompany.Model.CompanyManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyManagementRepository extends JpaRepository<CompanyManagement, Integer> {

    CompanyManagement findCompanyManagementByCompanyManagementId(Integer id);

    /// JPA

        // get all management decisions assigned to a specific representative team
        List<CompanyManagement> findCompanyManagementsByAssignedRepresentativeTeamId(Integer teamId);

         List<CompanyManagement> findByClientRequestIdIn(List<Integer> ids);

    /// JPQL

        // find management statements containing a keyword
        @Query("select m from CompanyManagement m where lower(m.companyManagementStatement) like lower(concat('%', ?1, '%'))")
        List<CompanyManagement> searchManagementStatements(String keyword);
}
