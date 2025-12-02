package org.example.fazzaa.Repository;

import org.example.fazzaa.Model.Management;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagementRepository extends JpaRepository<Management, Integer> {

    Management findManagementByManagementId(Integer managementId);

    Management findManagementByManagementTypeIgnoreCase(String managementType);
}
