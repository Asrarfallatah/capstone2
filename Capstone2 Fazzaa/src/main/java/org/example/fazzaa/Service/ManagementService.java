package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Api.ApiException;
import org.example.fazzaa.Model.Management;
import org.example.fazzaa.Repository.ManagementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagementService {

    // connect to the
    private final ManagementRepository managementRepository;

    // get all managements information from the database
    public List<Management> getAllManagements() {

        // check if there are any managements
        if (managementRepository.findAll().isEmpty()) {
            throw new ApiException("no managements found");
        }

        return managementRepository.findAll();
    }

    // add management information to the database
    public void addManagement(Management management) {

        // check null
        if (management.getManagementType() == null ) {
            throw new ApiException("management type cannot be null or empty");
        }else if (management.getManagementType().isEmpty()) {
            throw new ApiException("management type cannot be null or empty");
        }

        // check if management type already exists
        if (managementRepository.findManagementByManagementTypeIgnoreCase(management.getManagementType()) != null) {
            throw new ApiException("management type already exists");
        }
        // check input mismatch
        if (management.getManagementType().matches("^[0-9]+$")) {
            throw new ApiException("management type must be string values only");
        }

        managementRepository.save(management);

    }

    // update management information in the database
    public boolean updateManagement(Integer managementId, Management management) {

        // check null
        if (management.getManagementType() == null ) {
            throw new ApiException("management type cannot be null or empty");
        }else if (management.getManagementType().isEmpty()) {
            throw new ApiException("management type cannot be null or empty");
        }

        // check if management type already exists
        if (managementRepository.findManagementByManagementTypeIgnoreCase(management.getManagementType()) != null) {
            throw new ApiException("management type already exists");
        }
        // check input mismatch
        if (management.getManagementType().matches("^[0-9]+$")) {
            throw new ApiException("management type must be string values only");
        }


        // check if management exists
        Management oldManagement = managementRepository.findManagementByManagementId(managementId);
        if (oldManagement == null) {
            return false;
        }

        oldManagement.setManagementType(management.getManagementType());

        managementRepository.save(oldManagement);
        return true;
    }

    // delete
    public boolean deleteManagement(Integer managementId) {

        // check input mismatch
        if (!(managementId.toString().matches("^[0-9]+$")) ){
            throw new ApiException("management type must be integer values only");
        }


        // check if management exists
        Management management = managementRepository.findManagementByManagementId(managementId);
        if (management == null) {
            return false;
        }

        managementRepository.delete(management);
        return true;
    }

    /// extra endpoint
    // 10 -  get management by type
    public Management getManagementByType(String type) {

        // check if management exists
        if (managementRepository.findManagementByManagementTypeIgnoreCase(type) == null) {
          throw new ApiException("error no management with this type");
        }
        return managementRepository.findManagementByManagementTypeIgnoreCase(type);
    }
}
