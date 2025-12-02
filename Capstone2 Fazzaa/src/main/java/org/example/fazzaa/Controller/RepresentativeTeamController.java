package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiResponse;
import org.example.fazzaa.Model.RepresentativeTeam;
import org.example.fazzaa.Service.RepresentativeTeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rep-team")
@RequiredArgsConstructor
public class RepresentativeTeamController {

    private final RepresentativeTeamService representativeTeamService;

    //get
    @GetMapping("/get")
    public ResponseEntity<?> getAllTeams() {

        return ResponseEntity.status(200).body(representativeTeamService.getAllRepresentativeTeams());
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<?> addTeam(@Valid @RequestBody RepresentativeTeam team) {

        representativeTeamService.addRepresentativeTeam(team);
        return ResponseEntity.status(200).body(new ApiResponse("representative team added successfully !"));
    }

    //update
    @PutMapping("/update/{teamId}")
    public ResponseEntity<?> updateTeam(@PathVariable Integer teamId, @Valid @RequestBody RepresentativeTeam team) {

        representativeTeamService.updateRepresentativeTeam(teamId, team);


        return ResponseEntity.status(200).body(new ApiResponse("representative team has been updated successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer teamId) {

      representativeTeamService.deleteRepresentativeTeam(teamId);

        return ResponseEntity.status(200).body(new ApiResponse("representative team has been deleted successfully !"));
    }

    ///  extra endpoints

    // 1 4- get team by name
    @GetMapping("/name/{teamName}")
    public ResponseEntity<?> getTeamByName(@PathVariable String teamName) {

        return ResponseEntity.status(200).body(representativeTeamService.getTeamByName(teamName));
    }
}
