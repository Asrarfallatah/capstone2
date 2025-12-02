package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiResponse;
import org.example.fazzaa.Model.WithdrawRequest;
import org.example.fazzaa.Service.WithdrawRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/withdraw")
@RequiredArgsConstructor
public class WithdrawRequestController {

    private final WithdrawRequestService withdrawRequestService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getAllWithdrawRequests() {
        return ResponseEntity.status(200).body(withdrawRequestService.getAllWithdrawRequests());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addWithdrawRequest(@Valid @RequestBody WithdrawRequest withdrawRequest) {


         withdrawRequestService.addWithdrawRequest(withdrawRequest);


        return ResponseEntity.status(200).body(new ApiResponse("withdraw request added has been successfully !"));
    }

    // update
    @PutMapping("/update/{withdrawId}")
    public ResponseEntity<?> updateWithdrawRequest(@PathVariable Integer withdrawId, @Valid @RequestBody WithdrawRequest withdrawRequest) {


       withdrawRequestService.updateWithdrawRequest(withdrawId, withdrawRequest);

        return ResponseEntity.status(200).body(new ApiResponse("withdraw request can not be updated !"));
    }




    // delete
    @DeleteMapping("/delete/{withdrawId}")
    public ResponseEntity<?> deleteWithdrawRequest(@PathVariable Integer withdrawId) {

        withdrawRequestService.deleteWithdrawRequest(withdrawId);
        return ResponseEntity.status(200).body(new ApiResponse("withdraw request can not be deleted !"));
    }

    ///  extra endpoints

    //17-  get withdraw requests by client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getWithdrawsByClient(@PathVariable Integer clientId) {

        withdrawRequestService.getWithdrawsByClient(clientId);
        return ResponseEntity.status(200).body(withdrawRequestService.getWithdrawsByClient(clientId));
    }

    //18-  get withdraw requests by request
    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getWithdrawsByRequest(@PathVariable Integer requestId) {

        withdrawRequestService.getWithdrawsByRequest(requestId);
        return ResponseEntity.status(200).body(withdrawRequestService.getWithdrawsByRequest(requestId));
    }
}
