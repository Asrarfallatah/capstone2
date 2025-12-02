package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiException;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Model.WithdrawRequest;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.WithdrawRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawRequestService {

    // connnect to the datbase
    private final WithdrawRequestRepository withdrawRequestRepository;
    private final EventRequestRepository eventRequestRepository;

    // get all withdraw requests from the database
    public List<WithdrawRequest> getAllWithdrawRequests() {

        // check if there are withdraw requests or empty
        if (withdrawRequestRepository.findAll().isEmpty()) {
           throw new ApiException("error no withdraw requests found yet in the database");
        }

        return withdrawRequestRepository.findAll();
    }

    // add a withdraw request to the database
    public void addWithdrawRequest(WithdrawRequest withdrawRequest) {

        // check input mismatch
        if (withdrawRequest.getFeePaid().matches("^[a-zA-Z]+$")) {
           throw new ApiException("error invalid fee paid it must be a number");
        }else if (withdrawRequest.getFeePaid().matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
           throw new ApiException("error invalid fee paid it cannot contain special characters");
        }else if (withdrawRequest.getClientId().toString().matches("^[a-zA-Z]+$")) {
           throw new ApiException("error invalid client id it must be a number");
        }else if (withdrawRequest.getRequestId().toString().matches("^[a-zA-Z]+$")) {
           throw new ApiException("error invalid request id it must be a number");
        }else if (withdrawRequest.getCommunicationManagerId().toString().matches("^[a-zA-Z]+$")) {
           throw new ApiException("error invalid communication manager id it must be a number");
        }

        // null check
        if (withdrawRequest.getRequestId() == null ){
              throw new ApiException("error request id cannot be null");
        }else if (withdrawRequest.getClientId() == null) {
              throw new ApiException("error client id cannot be null");
        }else if (withdrawRequest.getReason() == null || withdrawRequest.getReason().isEmpty()) {
              throw new ApiException("error reason cannot be null or empty");
        }else if (withdrawRequest.getFeePaid() == null || withdrawRequest.getFeePaid().isEmpty()) {
              throw new ApiException("error fee paid cannot be null or empty");
        } else if (withdrawRequest.getCommunicationManagerId() == null) {
              throw new ApiException("error additional notes cannot be null or empty");
        }else if (withdrawRequest.getJustification() == null){
                throw new ApiException("error justification cannot be null");
        }



        // check if  if the event request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(withdrawRequest.getRequestId());
        if (request == null) {
          throw new ApiException("error request with that id  does not exist");
        }

        // check if the request is already withdrawn
        if (request.getRequestStatus().equalsIgnoreCase("WITHDRAWN")) {
           throw new ApiException("error request is already withdrawn");
        }

        // check if fee is paid
        if (!withdrawRequest.getFeePaid().equalsIgnoreCase("TRUE") &&
            !withdrawRequest.getFeePaid().equalsIgnoreCase("FALSE")) {
           throw new ApiException("error fee paid status must be true or false only");
        }

        // check if client id matches the request's client id
        if (!withdrawRequest.getClientId().equals(request.getClientId())) {
           throw new ApiException("error client id does not match the request's client id");
        }

        // check if reason length is within limit
        if (withdrawRequest.getReason().length() > 1000) {
           throw new ApiException("error reason length exceeds the limit of 1000 characters");
        }

        // check if justification length is within limit
        if (withdrawRequest.getJustification() != null && withdrawRequest.getJustification().length() > 2000) {
           throw new ApiException("error justification length exceeds the limit of 2000 characters");
        }

        // check if communication manager id is valid
        if (withdrawRequest.getCommunicationManagerId() <= 0) {
           throw new ApiException("error invalid communication manager id");
        }

        // save the withdraw request to the database
        withdrawRequestRepository.save(withdrawRequest);

        // change request status to WITHDRAWN
        request.setRequestStatus("WITHDRAWN");
        eventRequestRepository.save(request);


    }

    // update withdraw request in the database
    public void  updateWithdrawRequest(Integer withdrawId, WithdrawRequest withdrawRequest) {

        // check input mismatch
        if (withdrawRequest.getFeePaid().matches("^[a-zA-Z]+$")) {
            throw new ApiException("error invalid fee paid it must be a number");
        }else if (withdrawRequest.getFeePaid().matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new ApiException("error invalid fee paid it cannot contain special characters");
        }else if (withdrawRequest.getClientId().toString().matches("^[a-zA-Z]+$")) {
            throw new ApiException("error invalid client id it must be a number");
        }else if (withdrawRequest.getRequestId().toString().matches("^[a-zA-Z]+$")) {
            throw new ApiException("error invalid request id it must be a number");
        }else if (withdrawRequest.getCommunicationManagerId().toString().matches("^[a-zA-Z]+$")) {
            throw new ApiException("error invalid communication manager id it must be a number");
        }

        // null check
        if (withdrawRequest.getRequestId() == null ){
            throw new ApiException("error request id cannot be null");
        }else if (withdrawRequest.getClientId() == null) {
            throw new ApiException("error client id cannot be null");
        }else if (withdrawRequest.getReason() == null || withdrawRequest.getReason().isEmpty()) {
            throw new ApiException("error reason cannot be null or empty");
        }else if (withdrawRequest.getFeePaid() == null || withdrawRequest.getFeePaid().isEmpty()) {
            throw new ApiException("error fee paid cannot be null or empty");
        } else if (withdrawRequest.getCommunicationManagerId() == null) {
            throw new ApiException("error additional notes cannot be null or empty");
        }else if (withdrawRequest.getJustification() == null){
            throw new ApiException("error justification cannot be null");
        }

        // check if  if the event request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(withdrawRequest.getRequestId());
        if (request == null) {
            throw new ApiException("error request with that id  does not exist");
        }

        // check if the request is already withdrawn
        if (request.getRequestStatus().equalsIgnoreCase("WITHDRAWN")) {
            throw new ApiException("error request is already withdrawn");
        }

        // check if fee is paid
        if (!withdrawRequest.getFeePaid().equalsIgnoreCase("TRUE") &&
                !withdrawRequest.getFeePaid().equalsIgnoreCase("FALSE")) {
            throw new ApiException("error fee paid status must be true or false only");
        }

        // check if client id matches the request's client id
        if (!withdrawRequest.getClientId().equals(request.getClientId())) {
            throw new ApiException("error client id does not match the request's client id");
        }

        // check if reason length is within limit
        if (withdrawRequest.getReason().length() > 1000) {
            throw new ApiException("error reason length exceeds the limit of 1000 characters");
        }

        // check if justification length is within limit
        if (withdrawRequest.getJustification() != null && withdrawRequest.getJustification().length() > 2000) {
            throw new ApiException("error justification length exceeds the limit of 2000 characters");
        }

        // check if communication manager id is valid
        if (withdrawRequest.getCommunicationManagerId() <= 0) {
            throw new ApiException("error invalid communication manager id");
        }

        // check if withdraw request exists
        WithdrawRequest existingWithdrawRequest = withdrawRequestRepository.findWithdrawRequestByWithdrawId(withdrawId);
        if (existingWithdrawRequest == null) {
            throw new ApiException("error withdraw request with that id does not exist");
        }

        // update
        existingWithdrawRequest.setReason(withdrawRequest.getReason());
        existingWithdrawRequest.setFeePaid(withdrawRequest.getFeePaid());
        existingWithdrawRequest.setJustification(withdrawRequest.getJustification());
        existingWithdrawRequest.setRequestId(withdrawRequest.getRequestId());
        existingWithdrawRequest.setClientId(withdrawRequest.getClientId());

        // save
        withdrawRequestRepository.save(existingWithdrawRequest);


    }


    // delete withdraw request from the database
    public void deleteWithdrawRequest(Integer withdrawId) {

        // input mismatch check
        if (withdrawId.toString().matches("^[a-zA-Z]+$")) {
            throw new ApiException("error invalid withdraw id it must be a number");
        }

        // null check
        if (withdrawId == null) {
            throw new ApiException("error withdraw id cannot be null");
        }

        // check if withdraw request exists
        WithdrawRequest withdrawRequest = withdrawRequestRepository.findWithdrawRequestByWithdrawId(withdrawId);
        if (withdrawRequest == null) {
            throw new ApiException("error withdraw request with that id does not exist");
        }

    }

    ///  extra endpoints

    //17-  get withdraw requests by client
    public List<WithdrawRequest> getWithdrawsByClient(Integer clientId) {

        // input mismatch check
        if (clientId.toString().matches("^[a-zA-Z]+$")) {
            throw new ApiException("error invalid client id it must be a number");
        }

        // null check
        if (clientId == null) {
            throw new ApiException("error client id cannot be null");
        }

        // is it empty
        if (withdrawRequestRepository.findWithdrawRequestsByClientId(clientId).isEmpty()) {
            throw new ApiException("error no withdraw requests found for that client id");
        }

        return withdrawRequestRepository.findWithdrawRequestsByClientId(clientId);
    }

    //18-  get withdraw requests by request
    public List<WithdrawRequest> getWithdrawsByRequest(Integer requestId) {

        // input mismatch check
        if (requestId.toString().matches("^[a-zA-Z]+$")) {
            throw new ApiException("error invalid withdraw requests it must be a number");
        }

        // null check
        if (requestId == null) {
            throw new ApiException("error withdraw requests cannot be null");
        }

        // is it empty
        if (withdrawRequestRepository.findWithdrawRequestsByRequestId(requestId).isEmpty()) {
            throw new ApiException("error no withdraw requests found for that request id");
        }

        return withdrawRequestRepository.findWithdrawRequestsByRequestId(requestId);
    }
}
