package org.example.eventplannercompany.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientId;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error client name can not be empty !")
    @Size(min = 3, message = "error client name must be at least 3 characters !")

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String clientName;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error client phone number can not be empty !")
    @Size(min = 10, max = 10, message = "error client phone number must be 10 digits !")
    @Pattern(regexp = "^05\\d*$", message = "error client phone number must start with 05--------")

    @Column(columnDefinition = "VARCHAR(10) NOT NULL")
    private String clientPhoneNumber;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error client email can not be empty !")
    @Email(message = "error client email must be valid !")

    @Column(columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    private String clientEmail;

}

