package org.example.fazzaa.Repository;

import org.example.fazzaa.Model.WithdrawRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawRequestRepository extends JpaRepository<WithdrawRequest, Integer> {

    WithdrawRequest findWithdrawRequestByWithdrawId(Integer withdrawId);


    List<WithdrawRequest> findWithdrawRequestsByClientId(Integer clientId);


    List<WithdrawRequest> findWithdrawRequestsByRequestId(Integer requestId);
}
