package org.example.eventplannercompany.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error company name can not be empty !")
    @Size(min = 3, message = "error company name must be at least 3 characters !")

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String companyName;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error company phone number can not be empty !")
    @Size(min = 10, max = 10, message = "error company phone number must be 10 digits !")
    @Pattern(regexp = "^05\\d*$", message = "error company phone number must start with 05--------")

    @Column(columnDefinition = "VARCHAR(10) NOT NULL")
    private String companyPhoneNumber;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error company email can not be empty !")
    @Email(message = "error company email must be valid !")

    @Column(columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    private String companyEmail;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error company location can not be empty !")

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String companyLocation;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error about us can not be empty !")
    @Size(min = 10 , message = "error about us can not be less than 10 characters ")

    @Column(columnDefinition = "VARCHAR(500) NOT NULL")
    private String aboutUs ;

}
