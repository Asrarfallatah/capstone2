package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiResponse;
import org.example.fazzaa.Model.AssignedRepresentative;
import org.example.fazzaa.Service.AssignedRepresentativeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assigned-rep")
@RequiredArgsConstructor
public class AssignedRepresentativeController {

    private final AssignedRepresentativeService assignedRepresentativeService;

    // gets
    @GetMapping("/get")
    public ResponseEntity<?> getAllAssignRepresentatives() {
        return ResponseEntity.status(200).body(assignedRepresentativeService.getAllAssignRepresentatives());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addAssignment(@Valid @RequestBody AssignedRepresentative assigned) {

        assignedRepresentativeService.addAssignedRepresentative(assigned);
        return ResponseEntity.status(200).body(new ApiResponse("representative assignment added successfully !"));
    }

    // update
    @PutMapping("/update/{assignedId}")
    public ResponseEntity<?> updateAssignment(@PathVariable Integer assignedId, @Valid @RequestBody AssignedRepresentative assignedRepresentative) {

        assignedRepresentativeService.updateAssignedRepresentative(assignedId, assignedRepresentative);
        return ResponseEntity.status(200).body(new ApiResponse("representative assignment information has been updated successfully !"));

    }

    // delete
    @DeleteMapping("/delete/{assignedId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Integer assignedId) {

        assignedRepresentativeService.deleteAssignedRepresentative(assignedId);
        return ResponseEntity.status(200).body(new ApiResponse("representative assignment has been deleted successfully !"));
    }

    ///  extra endpoints

    //15- get all representative assignments for a specific request
    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getAssignmentsByRequest(@PathVariable Integer requestId) {

        return ResponseEntity.status(200).body(assignedRepresentativeService.getAssignmentsByRequest(requestId));
    }

    // 16- get all assignments for a specific representative team
    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> getAssignmentsByTeam(@PathVariable Integer teamId) {

        return ResponseEntity.status(200).body(assignedRepresentativeService.getAssignmentsByTeam(teamId));
    }
}
