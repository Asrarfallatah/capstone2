package org.example.fazzaa.Service;


import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiException;
import org.example.fazzaa.Model.Company;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Model.Feedback;
import org.example.fazzaa.Model.WithdrawRequest;
import org.example.fazzaa.Repository.CompanyRepository;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.FeedbackRepository;
import org.example.fazzaa.Repository.WithdrawRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    // connect to the database
    private final CompanyRepository companyRepository;
    private final EventRequestRepository eventRequestRepository;
    private final FeedbackRepository feedbackRepository;
    private final WithdrawRequestRepository withdrawRequestRepository;
    private final AiService aiService;


    // get all company info
    public List<Company> getAllCompanyInfo() {

        // check if there is company info is empty
        if (companyRepository.findAll().isEmpty()) {
            throw new ApiException("no company information yet in the database to show its information !");
        }

        return companyRepository.findAll();
    }

    // add company info to the database
    public void addCompanyInfo(Company company) {

        // check for input mismatch errors
        if (company.getCompanyName().matches("^[0-9]+$")) {
            throw new ApiException("error company name must be string only !");
        }else if (company.getCompanyVision().matches("^[0-9]+$")) {
            throw new ApiException("error company vision must be string only !");
        }else if (company.getAboutUs().matches("^[0-9]+$")) {
            throw new ApiException("error about us must be string only !");
        }else if (company.getCompanyPolicy().matches("^[0-9]+$")) {
            throw new ApiException("error company policy must be string only !");
        }else if (company.getCompanyGoals().matches("^[0-9]+$")) {
            throw new ApiException("error company goals must be string only !");
        }else if (company.getCompanyValues().matches("^[0-9]+$")) {
            throw new ApiException("error company values must be string only !");
        }


        // check null input
        if (company.getCompanyName() == null ){
            throw new ApiException("error company name can not be null !");
        }else if (company.getCompanyVision() == null ){
            throw new ApiException("error company vision can not be null !");
        }else if (company.getAboutUs() == null ){
            throw new ApiException("error about us can not be null !");
        }else if (company.getCompanyPolicy() == null ) {
            throw new ApiException("error company policy can not be null !");
        }else if (company.getCompanyGoals() == null ){
            throw new ApiException("error company goals can not be null !");
        }else if (company.getCompanyValues() == null ){
            throw new ApiException("error company values can not be null !");
        }

        // check length
        if (company.getCompanyName().length() < 4) {
            throw new ApiException("error company name must be at least 4 characters long !");
        }

        if (company.getCompanyVision().length() < 10){
            throw new ApiException("error company vision must be at least 10 characters long !");
        }

        if (company.getAboutUs().length() < 10) {
            throw new ApiException("error about us must be at least 10 characters long !");
        }

        if (company.getCompanyPolicy().length() < 10) {
            throw new ApiException("error company policy must be at least 10 characters long !");
        }

        if (company.getCompanyGoals().length() < 10) {
            throw new ApiException("error company goals must be at least 10 characters long !");
        }

        if (company.getCompanyValues().length() < 10) {
            throw new ApiException("error company values must be at least 10 characters long !");
        }


        companyRepository.save(company);
    }

   // update company info
    public void updateCompanyInfo(Integer infoId, Company company) {

        // check for input mismatch errors
        if (company.getCompanyName().matches("^[0-9]+$")) {
            throw new ApiException("error company name must be string only !");
        }else if (company.getCompanyVision().matches("^[0-9]+$")) {
            throw new ApiException("error company vision must be string only !");
        }else if (company.getAboutUs().matches("^[0-9]+$")) {
            throw new ApiException("error about us must be string only !");
        }else if (company.getCompanyPolicy().matches("^[0-9]+$")) {
            throw new ApiException("error company policy must be string only !");
        }else if (company.getCompanyGoals().matches("^[0-9]+$")) {
            throw new ApiException("error company goals must be string only !");
        }else if (company.getCompanyValues().matches("^[0-9]+$")) {
            throw new ApiException("error company values must be string only !");
        }


        // null input check
        if (company.getCompanyName() == null ){
            throw new ApiException("error company name can not be null !");
        }else if (company.getCompanyVision() == null ){
            throw new ApiException("error company vision can not be null !");
        }else if (company.getAboutUs() == null ){
            throw new ApiException("error about us can not be null !");
        }else if (company.getCompanyPolicy() == null ) {
            throw new ApiException("error company policy can not be null !");
        }else if (company.getCompanyGoals() == null ){
            throw new ApiException("error company goals can not be null !");
        }else if (company.getCompanyValues() == null ){
            throw new ApiException("error company values can not be null !");
        }


        // check length
        if (company.getCompanyName().length() < 4) {
            throw new ApiException("error company name must be at least 4 characters long !");
        }

        if (company.getCompanyVision().length() < 10){
            throw new ApiException("error company vision must be at least 10 characters long !");
        }

        if (company.getAboutUs().length() < 10) {
            throw new ApiException("error about us must be at least 10 characters long !");
        }

        if (company.getCompanyPolicy().length() < 10) {
            throw new ApiException("error company policy must be at least 10 characters long !");
        }

        if (company.getCompanyGoals().length() < 10) {
            throw new ApiException("error company goals must be at least 10 characters long !");
        }

        if (company.getCompanyValues().length() < 10) {
            throw new ApiException("error company values must be at least 10 characters long !");
        }


        // check if the company exists
        Company oldcompany = companyRepository.findCompanyByCompanyId(infoId);
        if (oldcompany == null) {
            throw new ApiException("error no company with that id is found to update its information!");
        }

        // update
        oldcompany.setCompanyName(company.getCompanyName());
        oldcompany.setCompanyVision(company.getCompanyVision());
        oldcompany.setAboutUs(company.getAboutUs());
        oldcompany.setCompanyPolicy(company.getCompanyPolicy());
        oldcompany.setCompanyGoals(company.getCompanyGoals());
        oldcompany.setCompanyValues(company.getCompanyValues());

        // save it
        companyRepository.save(oldcompany);

    }

   // delete company info
    public void deleteCompanyInfo(Integer infoId) {

        // check input mismatch
        if (infoId.toString().contains("^[0-9]+$")) {
            throw new ApiException("error company id must be an integer !");
        }


        // check if the company exists
        Company company = companyRepository.findCompanyByCompanyId(infoId);
        if (company == null) {
            throw new ApiException("error no company with that id is found to delete its information!");
        }

        // delete it
        companyRepository.delete(company);
    }

    // 23- get company improvement advice from Ai
    public String getAiCompanyImprovementAdvice(Integer infoId) {

        // check input mismatch
        if (infoId.toString().contains("^[0-9]+$")) {
            throw new ApiException("error company id must be an integer !");
        }

        // check if the company exists
        Company company = companyRepository.findCompanyByCompanyId(infoId);
        if (company == null) {
            throw new ApiException ("error no company with this information is found  in the database to show it !");
        }

        List<EventRequest> requests = eventRequestRepository.findAll();
        List<Feedback> feedbacks = feedbackRepository.findAll();
        List<WithdrawRequest> withdraws = withdrawRequestRepository.findAll();

        StringBuilder sb = new StringBuilder();

        sb.append("Analyze this company information:\n\n")
                .append("Company Name: ").append(company.getCompanyName()).append("\n")
                .append("Vision: ").append(company.getCompanyVision()).append("\n")
                .append("About Us: ").append(company.getAboutUs()).append("\n")
                .append("Policy: ").append(company.getCompanyPolicy()).append("\n")
                .append("Goals: ").append(company.getCompanyGoals()).append("\n")
                .append("Values: ").append(company.getCompanyValues()).append("\n\n");


        sb.append("Here are event request trends:\n");
        for (EventRequest r : requests) {
            sb.append("- ").append(r.getEventType())
                    .append(" | Budget: ").append(r.getBudget())
                    .append("\n");
        }
        sb.append("\n");


        sb.append("Here is feedback from clients:\n");
        for (Feedback f : feedbacks) {
            sb.append("- Rating: ").append(f.getRating())
                    .append(" | Comment: ").append(f.getComment())
                    .append("\n");
        }
        sb.append("\n");


        sb.append("Here are withdrawal reasons:\n");
        for (WithdrawRequest w : withdraws) {
            sb.append("- Reason: ").append(w.getReason())
                    .append(" | Fee Paid: ").append(w.getFeePaid())
                    .append("\n");
        }
        sb.append("\n");


        sb.append("Please provide professional recommendations on:\n")
                .append("- how to improve company policy\n")
                .append("- how to attract more clients\n")
                .append("- how to increase trust and satisfaction\n")
                .append("- how to enhance brand identity\n")
                .append("- how to strengthen workflow\n")
                .append("- how to create fun and unique client experiences\n");

        return aiService.generateResponse(sb.toString());
    }
}
