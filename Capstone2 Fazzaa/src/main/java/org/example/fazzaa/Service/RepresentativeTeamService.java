package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiException;
import org.example.fazzaa.Model.RepresentativeTeam;
import org.example.fazzaa.Repository.RepresentativeTeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepresentativeTeamService {

    //connect to the database
    private final RepresentativeTeamRepository representativeTeamRepository;

    // get all representative teams
    public List<RepresentativeTeam> getAllRepresentativeTeams() {

        // check empty
        if (representativeTeamRepository.findAll().isEmpty()) {
            throw new RuntimeException("error no representative teams found");
        }
        return representativeTeamRepository.findAll();
    }

    // add new representative team in the database
    public void addRepresentativeTeam(RepresentativeTeam team) {

        //check null
        if (team.getTeamName() == null || team.getTeamName().isEmpty()) {
            throw new RuntimeException("error team name cannot be null or empty");
        }
        representativeTeamRepository.save(team);

    }

    // update a representative team within the database
    public void updateRepresentativeTeam(Integer repTeamId, RepresentativeTeam team) {

        // check input missmatch
        if (repTeamId.toString().contains("[a-zA-Z]")) {
            throw new RuntimeException("error representative team id must be a number");
        }

        //check null
        if (team.getTeamName() == null || team.getTeamName().isEmpty()) {
            throw new RuntimeException("error team name cannot be null or empty");
        }


        // check if the team exists
        RepresentativeTeam oldTeam = representativeTeamRepository.findRepresentativeTeamByRepTeamId(repTeamId);
        if (oldTeam == null) {
            throw new RuntimeException("error representative team not found");
        }

        // update
        oldTeam.setTeamName(team.getTeamName());

        // save
        representativeTeamRepository.save(oldTeam);

    }

    // delete a representative team from the database
    public void deleteRepresentativeTeam(Integer repTeamId) {

        // check input missmatch
        if (repTeamId.toString().contains("[a-zA-Z]")) {
            throw new ApiException("error representative team id must be a number");
        }

        // check if the team exists
        RepresentativeTeam team = representativeTeamRepository.findRepresentativeTeamByRepTeamId(repTeamId);
        if (team == null) {
            throw new ApiException("error representative team not found");
        }

        // delete
        representativeTeamRepository.delete(team);

    }

    ///  extra endpoints

    // 1 4- get team by name
    public RepresentativeTeam getTeamByName(String teamName) {

        // check if team exists
        if (representativeTeamRepository.findRepresentativeTeamByTeamNameIgnoreCase(teamName) == null) {
            throw new ApiException("Team not found");
        }

        return representativeTeamRepository.findRepresentativeTeamByTeamNameIgnoreCase(teamName);
    }
}
