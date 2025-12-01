package org.example.eventplannercompany.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.ApiResponse.ApiResponse;
import org.example.eventplannercompany.Model.Company;
import org.example.eventplannercompany.Service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getCompanies(){

        if (companyService.getAllCompanies().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no company information yet in the DataBase to show it !"));
        }

        return ResponseEntity.status(200).body(companyService.getAllCompanies());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addCompany(@Valid @RequestBody Company company, Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        companyService.addCompany(company);

        return ResponseEntity.status(200).body(new ApiResponse("Company information has been added Successfully !"));
    }

    // update
    @PutMapping("/update/{companyId}")
    public ResponseEntity<?> updateCompany(@PathVariable Integer companyId, @Valid @RequestBody Company company, Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean updated = companyService.updateCompany(companyId, company);

        if (!updated){
            return ResponseEntity.status(400).body(new ApiResponse("No company with this ID in the DataBase is found to update it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Company information has been updated Successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable Integer companyId){

        boolean deleted = companyService.deleteCompany(companyId);

        if (!deleted){
            return ResponseEntity.status(400).body(new ApiResponse("No company with this ID in the DataBase is found to delete it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Company information has been deleted Successfully !"));
    }


    /// extra endpoints

    // 1- AI improve service
    @GetMapping("/improve/{companyId}")
    public ResponseEntity<?> improveService(@PathVariable Integer companyId){

        String result = companyService.improveCompanyService(companyId);

        if (result.contains("not found")){
            return ResponseEntity.status(400).body(new ApiResponse("No company with this ID has been found in the DataBase !"));
        }

        return ResponseEntity.status(200).body(result);
    }
}
