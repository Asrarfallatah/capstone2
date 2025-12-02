package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiResponse;
import org.example.fazzaa.Model.Management;
import org.example.fazzaa.Service.ManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementService managementService;

    //get
    @GetMapping("/get")
    public ResponseEntity<?> getAllManagements() {
        return ResponseEntity.status(200).body(managementService.getAllManagements());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addManagement(@Valid @RequestBody Management management) {

        managementService.addManagement(management);
        return ResponseEntity.status(200).body(new ApiResponse("management added successfully !"));
    }

    // update
    @PutMapping("/update/{managementId}")
    public ResponseEntity<?> updateManagement(@PathVariable Integer managementId, @Valid @RequestBody Management management, Errors errors) {

        managementService.updateManagement(managementId, management);

        return ResponseEntity.status(200).body(new ApiResponse("management updated successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{managementId}")
    public ResponseEntity<?> deleteManagement(@PathVariable Integer managementId) {

        managementService.deleteManagement(managementId);


        return ResponseEntity.status(200).body(new ApiResponse("management deleted successfully !"));
    }

    /// extra endpoint
    // 10 -  get management by type
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getManagementByType(@PathVariable String type) {


        return ResponseEntity.status(200).body(managementService.getManagementByType(type));
    }
}
