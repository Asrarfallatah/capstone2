package org.example.fazzaa.Repository;

import org.example.fazzaa.Model.ManagementAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagementActionRepository extends JpaRepository<ManagementAction, Integer> {

    ManagementAction findManagementActionByActionId(Integer actionId);

    List<ManagementAction> findManagementActionsByRequestId(Integer requestId);

    List<ManagementAction> findManagementActionsByManagementId(Integer managementId);
}
