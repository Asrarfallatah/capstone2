package org.example.eventplannercompany.Service;

import lombok.RequiredArgsConstructor;
import org.example.eventplannercompany.Model.Company;
import org.example.eventplannercompany.Repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    // connect with the DataBase
    private final CompanyRepository companyRepository;
    private final AiService aiService;


    // get company information from database
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    // add the company information in the database
    public void addCompany(Company company){
        companyRepository.save(company);
    }

    // update company information within the database
    public boolean updateCompany(Integer companyId, Company company){

        // check if the company exist
        Company old = companyRepository.findCompanyByCompanyId(companyId);
        if (old == null){
            return false;
        }

        // update
        old.setCompanyName(company.getCompanyName());
        old.setCompanyPhoneNumber(company.getCompanyPhoneNumber());
        old.setCompanyEmail(company.getCompanyEmail());
        old.setCompanyLocation(company.getCompanyLocation());
        old.setAboutUs(company.getAboutUs());

        // save
        companyRepository.save(old);
        return true;
    }

    // delete company info
    public boolean deleteCompany(Integer companyId){

        // see if company exist
        Company company = companyRepository.findCompanyByCompanyId(companyId);
        if (company == null){
            return false;
        }

        // delete
        companyRepository.delete(company);
        return true;
    }

    /// extra endpoints

    // 1- - get help from openAI to improve company service
    public String improveCompanyService(Integer companyId){

        Company company = companyRepository.findCompanyByCompanyId(companyId);
        if (company == null){
            return "Company ID not found in the DataBase !";
        }

        String prompt =
                "Suggest ways to improve the event planning services for this company:\n\n" +
                        "Name: " + company.getCompanyName() + "\n" +
                        "Location: " + company.getCompanyLocation() + "\n" +
                        "Email: " + company.getCompanyEmail() + "\n\n" +
                        "About Us:\n" + company.getAboutUs() + "\n\n" +
                        "Give clear, actionable, and realistic recommendations.";

        return aiService.askAI(prompt);
    }

}
