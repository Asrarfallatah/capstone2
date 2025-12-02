package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiResponse;
import org.example.fazzaa.Model.Company;
import org.example.fazzaa.Service.CompanyService;
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
    public ResponseEntity<?> getCompanyInfo() {

        return ResponseEntity.status(200).body(companyService.getAllCompanyInfo());
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<?> addCompanyInfo(@Valid @RequestBody Company company ) {

        companyService.addCompanyInfo(company);
        return ResponseEntity.status(200).body(new ApiResponse("company information has been added successfully to the database!"));
    }

    // update
    @PutMapping("/update/{companyId}")
    public ResponseEntity<?> update(@PathVariable Integer companyId, @Valid @RequestBody Company company ) {

        companyService.updateCompanyInfo(companyId, company);
        return ResponseEntity.status(200).body(new ApiResponse("company information has been updated successfully !"));
    }

    //delete
    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<?> delete(@PathVariable Integer companyId) {

        companyService.deleteCompanyInfo(companyId);
        return ResponseEntity.status(200).body(new ApiResponse("company information deleted successfully !"));
    }

    // 23- get company improvement advice from Ai
    @GetMapping("/ai-improvement/{infoId}")
    public ResponseEntity<?> getAiCompanyImprovementAdvice(@PathVariable Integer infoId) {


        return ResponseEntity.status(200).body( new ApiResponse( companyService.getAiCompanyImprovementAdvice(infoId) ));
    }




}
