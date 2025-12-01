package org.example.eventplannercompany.Repository;

import org.example.eventplannercompany.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findCompanyByCompanyId(Integer companyId);

}
