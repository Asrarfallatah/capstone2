package org.example.eventplannercompany.Service;

import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.Model.CompanyRepresentative;
import org.example.eventplannercompany.Repository.CompanyRepresentativeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyRepresentativeService {

    // connect to the DataBase
    private final CompanyRepresentativeRepository companyRepresentativeRepository;
    private final AiService aiService;

    // get all representatives information from the DataBase
    public List<CompanyRepresentative> getAllRepresentatives(){
        return companyRepresentativeRepository.findAll();
    }

    // add representative information to the DataBase
    public void addRepresentative(CompanyRepresentative rep){
        companyRepresentativeRepository.save(rep);
    }

    // update representative information in  the DataBase
    public int updateRepresentative(Integer repId , CompanyRepresentative rep){

        CompanyRepresentative old = companyRepresentativeRepository.findCompanyRepresentativeByRepresentativeTeamId(repId);
        if (old == null){
            return 1;
        }

        old.setRepresentativeTeamName(rep.getRepresentativeTeamName());
        old.setRepresentativeTeamPhoneNumber(rep.getRepresentativeTeamPhoneNumber());

        companyRepresentativeRepository.save(old);
        return 2;
    }

    // delete representative information from the DataBase
    public int deleteRepresentative(Integer repId){

        CompanyRepresentative rep =
                companyRepresentativeRepository.findCompanyRepresentativeByRepresentativeTeamId(repId);

        if (rep == null){
            return 1;
        }

        companyRepresentativeRepository.delete(rep);
        return 2;
    }


    /// extra endpoints

    // 1- find representatives by phone
    public CompanyRepresentative getByPhone(String phone){
        return companyRepresentativeRepository.getTeamByPhoneNumber(phone);
    }

    // 2 - count requests handled by representative
    public Integer countEventsHandled(Integer repId){
        return companyRepresentativeRepository.countEventsHandled(repId);
    }

    // 3 - get openAI to evaluate the workload on the representative
    public String evaluateWorkload(Integer repId){

        CompanyRepresentative rep =
                companyRepresentativeRepository.findCompanyRepresentativeByRepresentativeTeamId(repId);

        if (rep == null){
            return "Representative ID not found in the DataBase !";
        }

        Integer count = companyRepresentativeRepository.countEventsHandled(repId);

        String prompt =
                "Evaluate workload and performance for this representative team:\n" +
                        "Team Name: " + rep.getRepresentativeTeamName() + "\n" +
                        "Phone: " + rep.getRepresentativeTeamPhoneNumber() + "\n" +
                        "Events Handled: " + count + "\n" +
                        "Give suggestions to improve their productivity.";

        return aiService.askAI(prompt);
    }
}


