package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiException;
import org.example.fazzaa.Model.AssignedRepresentative;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Model.RepresentativeTeam;
import org.example.fazzaa.Repository.AssignedRepresentativeRepository;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.RepresentativeTeamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignedRepresentativeService {


    // connect to the database
    private final AssignedRepresentativeRepository assignedRepresentativeRepository;
    private final EventRequestRepository eventRequestRepository;
    private final RepresentativeTeamRepository representativeTeamRepository;

    // get all assigned representatives
    public List<AssignedRepresentative> getAllAssignRepresentatives() {

        // check if there are assigned representatives
        if (assignedRepresentativeRepository.findAll().isEmpty()) {
            return null;
        }

        return assignedRepresentativeRepository.findAll();
    }

    // add an assigned representative to the database
    public void addAssignedRepresentative(AssignedRepresentative assignedRepresentative) {

        // check input mismatch errors
        if (assignedRepresentative.getRequestId().toString().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error request ID must be numbers only !");
        } else if (assignedRepresentative.getRepTeamId().toString().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error representative team ID must be numbers only !");
        } else if (assignedRepresentative.getAssignedDate().toString().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error assigned date must be in date format !");
        }

        // check for null validation
        if (assignedRepresentative.getRequestId() == null) {
            throw new ApiException("error request ID cant be empty !");
        } else if (assignedRepresentative.getRepTeamId() == null) {
            throw new ApiException("error representative team ID cant be empty !");
        } else if (assignedRepresentative.getAssignedDate() == null) {
            throw new ApiException("error assigned date cant be empty !");
        }

        // check if the assignment already exists
        AssignedRepresentative existingAssignment = assignedRepresentativeRepository.findAssignedRepresentativeByRequestIdAndRepTeamId(assignedRepresentative.getRequestId(), assignedRepresentative.getRepTeamId());
        if (existingAssignment != null) {
            throw new ApiException("this representative team is already assigned to this request !");
        }

        // check if the request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(assignedRepresentative.getRequestId());
        if (request == null) {
            throw new ApiException("eror no event request with that id is found in the database !");
        }

        // check if the team exists
        RepresentativeTeam team = representativeTeamRepository.findRepresentativeTeamByRepTeamId(assignedRepresentative.getRepTeamId());
        if (team == null) {
            throw new ApiException("error no representative team with that id is found in the database !");
        }

        // check that assigned date is not in the past
        if (assignedRepresentative.getAssignedDate().isBefore(LocalDate.now())) {
            throw new ApiException("error assigned date cannot be in the past !");
        }

        // check that assigned date is not before the request date
        if (assignedRepresentative.getAssignedDate().isBefore(request.getEventDate())) {
            throw new ApiException("error assigned date cannot be before the request date !");
        }

        // check that assigned date is not after the event date
        if (assignedRepresentative.getAssignedDate().isAfter(request.getEventDate())) {
            throw new ApiException("error assigned date cannot be after the event date !");
        }

        //check that the representative team is available on the assigned date
        List<AssignedRepresentative> teamAssignments = assignedRepresentativeRepository.findAssignedRepresentativesByRepTeamId(assignedRepresentative.getRepTeamId());
        for (AssignedRepresentative assignment : teamAssignments) {
            if (assignment.getAssignedDate().isEqual(assignedRepresentative.getAssignedDate())) {
                throw new ApiException("error this representative team is already assigned on the selected date !");
            }
        }


        // set the assigned date to today
        assignedRepresentative.setAssignedDate(LocalDate.now());

        //add
        assignedRepresentativeRepository.save(assignedRepresentative);
    }

    // update an assigned representative in the database
    public void updateAssignedRepresentative(Integer assignedId, AssignedRepresentative assignedRepresentative) {

        // check input mismatch errors
        if (assignedRepresentative.getRequestId().toString().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error request ID must be numbers only !");
        } else if (assignedRepresentative.getRepTeamId().toString().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error representative team ID must be numbers only !");
        }

        // check for null validation
        if (assignedRepresentative.getRequestId() == null) {
            throw new ApiException("error request ID cant be empty !");
        } else if (assignedRepresentative.getRepTeamId() == null) {
            throw new ApiException("error representative team ID cant be empty !");
        } else if (assignedRepresentative.getAssignedDate() == null) {
            throw new ApiException("error assigned date cant be empty !");
        }

        // check if the assignment exists
        AssignedRepresentative existingAssignment = assignedRepresentativeRepository.findAssignedRepresentativeByAssignedId(assignedId);
        if (existingAssignment == null) {
            throw new ApiException("error no assigned representative with that ID is found !");
        }

        // check if the request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(assignedRepresentative.getRequestId());
        if (request == null) {
            throw new ApiException("error no event request with that id is found in the database !");
        }

        // check if the team exists
        RepresentativeTeam team = representativeTeamRepository.findRepresentativeTeamByRepTeamId(assignedRepresentative.getRepTeamId());
        if (team == null) {
            throw new ApiException("error no representative team with that id is found in the database !");
        }

        // check that assigned date is not in the past
        if (assignedRepresentative.getAssignedDate().isBefore(LocalDate.now())) {
            throw new ApiException("error assigned date cannot be in the past !");
        }

        // check that assigned date is not before the request date
        if (assignedRepresentative.getAssignedDate().isBefore(request.getEventDate())) {
            throw new ApiException("error assigned date cannot be before the request date !");
        }

        // check that assigned date is not after the event date
        if (assignedRepresentative.getAssignedDate().isAfter(request.getEventDate())) {
            throw new ApiException("error assigned date cannot be after the event date !");
        }

        //check that the representative team is available on the assigned date
        List<AssignedRepresentative> teamAssignments = assignedRepresentativeRepository.findAssignedRepresentativesByRepTeamId(assignedRepresentative.getRepTeamId());
        for (AssignedRepresentative assignment : teamAssignments) {
            if (assignment.getAssignedDate().isEqual(assignedRepresentative.getAssignedDate()) && !assignment.getAssignedId().equals(assignedId)) {
                throw new ApiException("error this representative team is already assigned on the selected date !");
            }
        }


        // update fields
        existingAssignment.setRequestId(assignedRepresentative.getRequestId());
        existingAssignment.setRepTeamId(assignedRepresentative.getRepTeamId());


        // save updated assignment
        assignedRepresentativeRepository.save(existingAssignment);
    }

    // delete an assigned representative from the database
    public void deleteAssignedRepresentative(Integer assignedId) {

        // check input mismatch errors
        if (assignedId.toString().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error assigned ID must be numbers only !");
        }

        //check for null validation
        if (assignedId == null) {
            throw new ApiException("error assigned ID cant be empty !");
        }

        // check if the assignment exists
        AssignedRepresentative assignedRepresentative = assignedRepresentativeRepository.findAssignedRepresentativeByAssignedId(assignedId);
        if (assignedRepresentative == null) {
            throw new ApiException("error no assigned representative with that ID is found !");
        }

        // delete
        assignedRepresentativeRepository.delete(assignedRepresentative);

    }

    ///  extra endpoints

    //15- get all representative assignments for a specific request
    public List<AssignedRepresentative> getAssignmentsByRequest(Integer requestId) {

        // check input mismatch errors
        if (requestId.toString().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error request ID must be numbers only !");
        }

        // check for null validation
        if (requestId == null) {
            throw new ApiException("error request ID cant be empty !");
        }

        //check if the request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (request == null) {
            throw new ApiException("error no event request with that id is found in the database !");
        }

        // check if empty
        if (assignedRepresentativeRepository.findAssignedRepresentativesByRequestId(requestId).isEmpty()) {
            throw new ApiException("error there are no assignments for the specified request id !");
        }

        return assignedRepresentativeRepository.findAssignedRepresentativesByRequestId(requestId);
    }

    // (16- get all assignments for a specific representative team
    public List<AssignedRepresentative> getAssignmentsByTeam(Integer repTeamId) {

        //check input mismatch errors
        if (repTeamId.toString().contains("^[a-zA-Z]+$")) {
            throw new ApiException("error representative team ID must be numbers only !");
        }

        //check for null validation
        if (repTeamId == null) {
            throw new ApiException("error representative team ID cant be empty !");
        }

        // check if empty
        if (assignedRepresentativeRepository.findAssignedRepresentativesByRepTeamId(repTeamId).isEmpty()) {
            throw new ApiException("error there are no assignments for the specified team id !");
        }
            return assignedRepresentativeRepository.findAssignedRepresentativesByRepTeamId(repTeamId);
        }
    }
