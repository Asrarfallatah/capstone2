package org.example.eventplannercompany.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CompanyRepresentative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer representativeTeamId;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error representative team name can not be empty !")

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String representativeTeamName;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error representative team phone number can not be empty !")
    @Size(min = 10, max = 10, message = "error phone number must be 10 digits !")
    @Pattern(regexp = "^05\\d*$", message = "error phone must start with 05--------")

    @Column(columnDefinition = "VARCHAR(10) NOT NULL")
    private String representativeTeamPhoneNumber;


}
