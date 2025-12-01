package org.example.eventplannercompany.Service;

import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.Model.CompanyManagement;
import org.example.eventplannercompany.Model.ClientRequest;
import org.example.eventplannercompany.Model.CompanyRepresentative;
import org.example.eventplannercompany.Repository.CompanyManagementRepository;
import org.example.eventplannercompany.Repository.ClientRequestRepository;
import org.example.eventplannercompany.Repository.CompanyRepresentativeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyManagementService {

    // connect to the Database
    private final CompanyManagementRepository companyManagementRepository;
    private final ClientRequestRepository clientRequestRepository;
    private final CompanyRepresentativeRepository companyRepresentativeRepository;
    private final AiService aiService;


    // get all management decisions from the DataBase
    public List<CompanyManagement> getAllManagement(){
        return companyManagementRepository.findAll();
    }


    // add management decision to the DataBase
    public int addManagement(CompanyManagement management){

        // check if request exists
        ClientRequest request = clientRequestRepository.findClientRequestByClientRequestId(management.getClientRequestId());
        if (request == null){
            return 1;
        }

        // assign
        management.setCompanyManagementApprovalStatus("PENDING");
        management.setAssignedRepresentativeTeamId(null);

        companyManagementRepository.save(management);
        return 2;
    }


    // update management decision in the DataBase
    public int updateManagement(Integer managementId , CompanyManagement management){

        CompanyManagement old = companyManagementRepository.findCompanyManagementByCompanyManagementId(managementId);
        if (old == null){
            return 1;
        }

        // only PENDING can be changed
        if (!"PENDING".equalsIgnoreCase(old.getCompanyManagementApprovalStatus())){
            return 2;
        }

        // update only statement
        old.setCompanyManagementStatement(management.getCompanyManagementStatement());
        companyManagementRepository.save(old);

        return 3;
    }


    // delete management decision from the DataBase
    public int deleteManagement(Integer managementId){

        CompanyManagement mg = companyManagementRepository.findCompanyManagementByCompanyManagementId(managementId);

        if (mg == null){
            return 1;
        }

        companyManagementRepository.delete(mg);
        return 2;
    }


    /// extra endpoints

    // 1- get management decisions for specific request
    public List<CompanyManagement> getManagementForClient(Integer clientId){

        // get all requests for the client
        List<ClientRequest> requests = clientRequestRepository.findClientRequestsByClientId(clientId);

        if (requests.isEmpty()){
            return List.of(); // no requests → no management decisions
        }

        // extract request IDs
        List<Integer> requestIds = requests.stream()
                .map(ClientRequest::getClientRequestId)
                .toList();

        // return all management decisions linked to those request IDs
        return companyManagementRepository.findByClientRequestIdIn(requestIds);
    }


    // 3- get management decisions for specific team
    public List<CompanyManagement> getManagementForTeam(Integer teamId){
        return companyManagementRepository.findCompanyManagementsByAssignedRepresentativeTeamId(teamId);
    }

    // 4 - search statements
    public List<CompanyManagement> searchStatements(String keyword){
        return companyManagementRepository.searchManagementStatements(keyword);
    }

    // 5 - AI improve policies
    public String improvePolicy(){

        List<CompanyManagement> list = companyManagementRepository.findAll();

        if (list.isEmpty()){
            return "There are no management decisions in the DataBase to analyze !";
        }

        StringBuilder sb = new StringBuilder("Analyze these management decisions and suggest improvements for company policy:\n\n");

        for (CompanyManagement mg : list){
            sb.append("Status: ").append(mg.getCompanyManagementApprovalStatus())
                    .append("\nStatement: ").append(mg.getCompanyManagementStatement())
                    .append("\n\n");
        }

        return aiService.askAI(sb.toString());
    }


    // approve client request
    public int approveClientRequest(Integer managementId, Integer repId){


        CompanyManagement mg = companyManagementRepository.findCompanyManagementByCompanyManagementId(managementId);
        if (mg == null){
            return 1;
        }

        if (!"PENDING".equalsIgnoreCase(mg.getCompanyManagementApprovalStatus())) {
            return 5;
        }

        if (mg.getCompanyManagementStatement() == null || mg.getCompanyManagementStatement().isEmpty()) {
            return 6;
        }

        CompanyRepresentative rep = companyRepresentativeRepository.findCompanyRepresentativeByRepresentativeTeamId(repId);
        if (rep == null){
            return 2;
        }

        ClientRequest request = clientRequestRepository.findClientRequestByClientRequestId(mg.getClientRequestId());
        if (request == null){
            return 3;
        }



        // update client request
        request.setClientRequestStatus("APPROVED");
        clientRequestRepository.save(request);

        // update management record
        mg.setCompanyManagementApprovalStatus("APPROVED");
        mg.setAssignedRepresentativeTeamId(repId);
        companyManagementRepository.save(mg);

        return 4;
    }


    // reject client request
    public int rejectClientRequest(Integer managementId){

        CompanyManagement mg = companyManagementRepository.findCompanyManagementByCompanyManagementId(managementId);
        if (mg == null){
            return 1;
        }

        ClientRequest request = clientRequestRepository.findClientRequestByClientRequestId(mg.getClientRequestId());
        if (request == null){
            return 2;
        }

        // update client request
        request.setClientRequestStatus("REJECTED");
        clientRequestRepository.save(request);

        // update management decision
        mg.setCompanyManagementApprovalStatus("REJECTED");
        mg.setAssignedRepresentativeTeamId(null);
        companyManagementRepository.save(mg);

        return 3;
    }


}
