package org.example.fazzaa.Repository;

import org.example.fazzaa.Model.RepresentativeTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentativeTeamRepository extends JpaRepository<RepresentativeTeam, Integer> {

    RepresentativeTeam findRepresentativeTeamByRepTeamId(Integer repTeamId);




    RepresentativeTeam findRepresentativeTeamByTeamNameIgnoreCase(String teamName);
}
