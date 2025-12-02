package org.example.fazzaa.Repository;

import org.example.fazzaa.Model.AssignedRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignedRepresentativeRepository extends JpaRepository<AssignedRepresentative, Integer> {

    AssignedRepresentative findAssignedRepresentativeByAssignedId(Integer assignedId);

    AssignedRepresentative findAssignedRepresentativeByRequestIdAndRepTeamId(Integer requestId, Integer repTeamId);

    List<AssignedRepresentative> findAssignedRepresentativesByRequestId(Integer requestId);

    List<AssignedRepresentative> findAssignedRepresentativesByRepTeamId(Integer repTeamId);


}
