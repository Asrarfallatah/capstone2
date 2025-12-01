package org.example.eventplannercompany.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.ApiResponse.ApiResponse;
import org.example.eventplannercompany.Model.CompanyRepresentative;
import org.example.eventplannercompany.Service.CompanyRepresentativeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rep")
@RequiredArgsConstructor
public class CompanyRepresentativeController {

    private final CompanyRepresentativeService companyRepresentativeService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getReps(){

        List<CompanyRepresentative> reps = companyRepresentativeService.getAllRepresentatives();

        if (reps.isEmpty()){
            return ResponseEntity.status(400)
                    .body(new ApiResponse("There is no representatives yet in the DataBase to show their information !"));
        }

        return ResponseEntity.status(200).body(reps);
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addRep(@Valid @RequestBody CompanyRepresentative rep){

        companyRepresentativeService.addRepresentative(rep);
        return ResponseEntity.status(200)
                .body(new ApiResponse("Representative information has been added Successfully !"));
    }

    // update
    @PutMapping("/update/{repId}")
    public ResponseEntity<?> updateRep(@PathVariable Integer repId,
                                       @Valid @RequestBody CompanyRepresentative rep){

        int result = companyRepresentativeService.updateRepresentative(repId, rep);

        if (result == 1){
            return ResponseEntity.status(400)
                    .body(new ApiResponse("No representative with this ID has been found in the DataBase to update it !"));
        }

        return ResponseEntity.status(200)
                .body(new ApiResponse("Representative information has been updated Successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{repId}")
    public ResponseEntity<?> deleteRep(@PathVariable Integer repId){

        int result = companyRepresentativeService.deleteRepresentative(repId);

        if (result == 1){
            return ResponseEntity.status(400)
                    .body(new ApiResponse("No representative with this ID has been found in the DataBase to delete it !"));
        }

        return ResponseEntity.status(200)
                .body(new ApiResponse("Representative information has been deleted Successfully !"));
    }

    /// EXTRA ENDPOINTS

    // 3 - find team by phone
    @GetMapping("/search-phone/{phone}")
    public ResponseEntity<?> getByPhone(@PathVariable String phone){

        CompanyRepresentative representative = companyRepresentativeService.getByPhone(phone);

        if (representative == null){
            return ResponseEntity.status(400).body(new ApiResponse("There is no representative team with this phone number in the DataBase !"));
        }

        return ResponseEntity.status(200).body(representative);

    }

    //4- count events handled for spesific team
    @GetMapping("/count/{repId}")
    public ResponseEntity<?> countEvents(@PathVariable Integer repId){

        Integer count = companyRepresentativeService.countEventsHandled(repId);

        if (count == null || count == 0){
            return ResponseEntity.status(400).body(new ApiResponse("This representative has not handled any requests yet !"));
        }

        return ResponseEntity.status(200).body(count);
    }

    //5 - make openAI see and evaluate team workload
    @GetMapping("/workload/{repId}")
    public ResponseEntity<?> workload(@PathVariable Integer repId){
        return ResponseEntity.status(200).body(companyRepresentativeService.evaluateWorkload(repId));
    }
}

