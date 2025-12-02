package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiException;
import org.example.fazzaa.Model.Management;
import org.example.fazzaa.Model.ManagementAction;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.ManagementActionRepository;
import org.example.fazzaa.Repository.ManagementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagementActionService {

    // connect to the database
    private final ManagementActionRepository managementActionRepository;
    private final ManagementRepository managementRepository;
    private final EventRequestRepository eventRequestRepository;

    // get all management actions from database
    public List<ManagementAction> getAllManagementActions() {

        // check empty
        if (managementActionRepository.findAll().isEmpty()) {
            return null;
        }

        return managementActionRepository.findAll();
    }

    // add management action to database
    public void addManagementAction(ManagementAction action) {

        // check input mismatch
        if (action.getManagementId().toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, management ID should be an integer !");
        } else if (action.getRequestId().toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, request ID should be an integer !");
        }  else if (action.getActionDate().toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, action date should be a date time !");
        }

        // check null
        if (action.getManagementId() == null) {
            throw new ApiException("error management ID cannot be null !");
        }else if (action.getRequestId() == null){
            throw new ApiException("error request ID cannot be null !");
        }else if (action.getActionType() == null){
            throw new ApiException("error action type cannot be null !");
        }else if (action.getStatement() == null){
            throw new ApiException("error statement cannot be null !");
        } else if (action.getActionDate() == null) {
            throw new ApiException("error action date cannot be null !");
        }


        // check if management exists
        Management management = managementRepository.findManagementByManagementId(action.getManagementId());
        if (management == null) {
           throw new ApiException("error management not found !");
        }

        // check if request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(action.getRequestId());
        if (request == null) {
            throw new ApiException("error event request not found !");
        }


        action.setActionDate(LocalDateTime.now());

        // add
        managementActionRepository.save(action);

    }

    // update management action in database
    public void updateManagementAction(Integer actionId, ManagementAction action) {

        // check input mismatch
        if (actionId.toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error action ID should be an integer !");
        }


        // check if action exists
        ManagementAction existingAction = managementActionRepository.findManagementActionByActionId(actionId);
        if (existingAction == null) {
            throw new ApiException("error management action not found !");
        }

        // check if management exists
        Management management = managementRepository.findManagementByManagementId(action.getManagementId());
        if (management == null) {
            throw new ApiException("error management not found !");
        }

        // check if request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(action.getRequestId());
        if (request == null) {
            throw new ApiException("error event request not found !");
        }

        // update
        existingAction.setManagementId(action.getManagementId());
        existingAction.setRequestId(action.getRequestId());
        existingAction.setActionType(action.getActionType());
        existingAction.setStatement(action.getStatement());
        existingAction.setActionDate(LocalDateTime.now());

        // save
        managementActionRepository.save(existingAction);

    }



    // delete management action from database
    public void deleteManagementAction(Integer actionId) {

        // check input mismatch
        if (actionId.toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error action ID should be an integer !");
        }


        // check if action exists
        ManagementAction action = managementActionRepository.findManagementActionByActionId(actionId);
        if (action == null) {
           throw new ApiException("error management action not found !");
        }

        managementActionRepository.delete(action);

    }

    /// extra endpoint


    //11- get all actions for a specific request
    public List<ManagementAction> getActionsForRequest(Integer requestId) {

      if (requestId.toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, request ID should be an integer !");
        }

      if (eventRequestRepository.findEventRequestByRequestId(requestId) == null) {
          throw new ApiException("error event request not found !");
      }

        return managementActionRepository.findManagementActionsByRequestId(requestId);
    }

    //12- get all actions done by a specific management
    public List<ManagementAction> getActionsByManagement(Integer managementId) {

        if (managementId.toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, management ID should be an integer !");
        }

        if (managementRepository.findManagementByManagementId(managementId) == null) {
            throw new ApiException("error management not found !");
        }

        return managementActionRepository.findManagementActionsByManagementId(managementId);
    }

    // 13-  APPROVE / REJECT
    public void approveOrRejectRequest(Integer managementId, Integer requestId, String actionType, String statement) {

        if (managementId.toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, management ID should be an integer !");
        } else if (requestId.toString().matches(".*[a-zA-Z]+.*")) {
            throw new ApiException("error invalid input type, request ID should be an integer !");
        }


        // check if management exists
        Management management = managementRepository.findManagementByManagementId(managementId);
        if (management == null) {
            throw new ApiException("error no management is found !");
        }

        //check if request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (request == null) {
           throw new ApiException("error no event request is found !");
        }

        // make action
        ManagementAction action = new ManagementAction();
        action.setManagementId(managementId);
        action.setRequestId(requestId);
        action.setActionType(actionType);
        action.setStatement(statement);
        action.setActionDate(LocalDateTime.now());

        managementActionRepository.save(action);

        // change request status
        if (actionType.equalsIgnoreCase("APPROVE")) {
            request.setRequestStatus("APPROVED");
        } else if (actionType.equalsIgnoreCase("REJECT")) {
            request.setRequestStatus("REJECTED");
        }

        eventRequestRepository.save(request);

    }
}
