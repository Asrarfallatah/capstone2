package org.example.eventplannercompany.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.ApiResponse.ApiResponse;
import org.example.eventplannercompany.Model.CompanyManagement;
import org.example.eventplannercompany.Service.CompanyManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
public class CompanyManagementController {

    private final CompanyManagementService companyManagementService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getManagement(){

        if (companyManagementService.getAllManagement().isEmpty()){
            return ResponseEntity.status(400)
                    .body(new ApiResponse("There is no management decisions yet in the DataBase to show their information !"));
        }

        return ResponseEntity.status(200).body(companyManagementService.getAllManagement());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addManagement(@Valid @RequestBody CompanyManagement mg){

        int result = companyManagementService.addManagement(mg);

        if (result == 1){
            return ResponseEntity.status(400).body(new ApiResponse("No Client Request with that ID is not found in the DataBase !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Management information has been added Successfully with status PENDING !"));
    }

    // update
    @PutMapping("/update/{managementId}")
    public ResponseEntity<?> updateManagement(@PathVariable Integer managementId,
                                              @Valid @RequestBody CompanyManagement mg){

        int result = companyManagementService.updateManagement(managementId, mg);

        if (result == 1){
            return ResponseEntity.status(400)
                    .body(new ApiResponse("No management with this ID in the DataBase is found to update it !"));
        }
        else if (result == 2){
            return ResponseEntity.status(400)
                    .body(new ApiResponse("Only PENDING management decisions can be updated !"));
        }

        return ResponseEntity.status(200)
                .body(new ApiResponse("Management information has been updated Successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{managementId}")
    public ResponseEntity<?> deleteManagement(@PathVariable Integer managementId){

        int result = companyManagementService.deleteManagement(managementId);

        if (result == 1){
            return ResponseEntity.status(400)
                    .body(new ApiResponse("No management with this ID in the DataBase is found to delete it !"));
        }

        return ResponseEntity.status(200)
                .body(new ApiResponse("Management information has been deleted Successfully !"));
    }

    /// EXTRA ENDPOINTS

    // get all management that communicate with this client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getManagementForClient(@PathVariable Integer clientId){

        List<CompanyManagement> list = companyManagementService.getManagementForClient(clientId);

        if (list.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("This client does not have any management decisions yet in the DataBase !"));
        }

        return ResponseEntity.status(200).body(list);
    }


    // Get management by representative team
    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> getByTeam(@PathVariable Integer teamId){

        List<CompanyManagement> list = companyManagementService.getManagementForTeam(teamId);

        if (list.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no management decisions assigned to this team in the DataBase !"));
        }

        return ResponseEntity.status(200).body(list);
    }



    // look for management decision by statements based on a keyword
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchKeyword(@PathVariable String keyword){

        if (companyManagementService.searchStatements(keyword).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no management decision that contains this keyword in the DataBase !"));
        }

        return ResponseEntity.status(200).body(companyManagementService.searchStatements(keyword));
    }

    // AI improve policy
    @GetMapping("/improve-policy")
    public ResponseEntity<?> improvePolicy(){

        String result = companyManagementService.improvePolicy();

        if (result.contains("no management")){
            return ResponseEntity.status(400).body(new ApiResponse(result));
        }

        return ResponseEntity.status(200).body(result);
    }

    // approve client request
    @PutMapping("/approve/{managementId}/{repId}")
    public ResponseEntity<?> approveManagement(@PathVariable Integer managementId, @PathVariable Integer repId){

        int result = companyManagementService.approveClientRequest(managementId, repId);

        if (result == 1){
            return ResponseEntity.status(400).body(new ApiResponse("No management with this ID has been found in the DataBase !"));
        }
        else if (result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("No representative with this ID has been found in the DataBase !"));
        }
        else if (result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Client Request ID linked to this management does not exist in the DataBase !"));
        }
        else if (result == 5){
            return ResponseEntity.status(400).body(new ApiResponse("Only pending management decisions can be approved !"));
        }
        else if (result == 6){
            return ResponseEntity.status(400).body(new ApiResponse("Management statement can not be empty before approval !"));
        }


        return ResponseEntity.status(200).body(new ApiResponse("Client Request has been APPROVED Successfully and representative assigned !"));
    }

    // reject client request
    @PutMapping("/reject/{managementId}")
    public ResponseEntity<?> rejectManagement(@PathVariable Integer managementId){

        int result = companyManagementService.rejectClientRequest(managementId);

        if (result == 1){
            return ResponseEntity.status(400).body(new ApiResponse("No management with this ID has been found in the DataBase !"));
        }
        else if (result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Client Request ID linked to this management does not exist in the DataBase !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Client Request has been REJECTED Successfully !"));
    }


}
