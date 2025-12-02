package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiResponse;
import org.example.fazzaa.Model.ManagementAction;
import org.example.fazzaa.Service.ManagementActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management-action")
@RequiredArgsConstructor
public class ManagementActionController {

    private final ManagementActionService managementActionService;


    // get
    @GetMapping("/get")
    public ResponseEntity<?> getAllActions() {

        return ResponseEntity.status(200).body(managementActionService.getAllManagementActions());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addAction(@Valid @RequestBody ManagementAction action) {


         managementActionService.addManagementAction(action);



        return ResponseEntity.status(200).body(new ApiResponse("management action added successfully !"));
    }

    // update
    @PutMapping("/update/{actionId}")
    public ResponseEntity<?> updateAction(@PathVariable Integer actionId, @Valid @RequestBody ManagementAction action) {

            managementActionService.updateManagementAction(actionId, action);


            return ResponseEntity.status(200).body(new ApiResponse("management action updated successfully !"));
        }

    // delete
    @DeleteMapping("/delete/{actionId}")
    public ResponseEntity<?> deleteAction(@PathVariable Integer actionId) {

         managementActionService.deleteManagementAction(actionId);


        return ResponseEntity.status(200).body(new ApiResponse("management action deleted successfully !"));
    }

    /// extra endpoint


    //11- get all actions for a specific request
    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getActionsForRequest(@PathVariable Integer requestId) {

        return ResponseEntity.status(200).body(managementActionService.getActionsForRequest(requestId));
    }

    //12- get all actions done by a specific management
    @GetMapping("/management/{managementId}")
    public ResponseEntity<?> getActionsByManagement(@PathVariable Integer managementId) {

        return ResponseEntity.status(200).body(managementActionService.getActionsByManagement(managementId));
    }

    // 13-  APPROVE / REJECT
    @PostMapping("/decision/{managementId}/{requestId}")
    public ResponseEntity<?> approveOrReject(@PathVariable Integer managementId, @PathVariable Integer requestId, @RequestParam String actionType, @RequestParam(required = false) String statement) {

         managementActionService.approveOrRejectRequest(managementId, requestId, actionType, statement);


        return ResponseEntity.status(200).body(new ApiResponse("action performed successfully !"));
    }
}
