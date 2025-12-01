package org.example.eventplannercompany.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientRequestId;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error request theme can not be empty !")
    @Size(min = 5, message = "error request theme must be at least 5 characters !")

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String clientRequestTheme;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error request type can not be empty !")
    @Size(min = 5, message = "error request type must be at least 5 characters !")

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String clientRequestType;

    /// //////////////////////////////////////////////////////////

    @NotNull(message = "error request date can not be empty !")

    @Column(columnDefinition = "DATETIME NOT NULL")
    private LocalDateTime clientRequestDate;

    /// //////////////////////////////////////////////////////////

    @NotNull(message = "error request budget can not be empty !")
    @Min(value = 100, message = "error request budget must be at least 100 SAR !")

    @Column(columnDefinition = "DOUBLE NOT NULL")
    private Double clientRequestBudget;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error request place can not be empty !")
    @Size(min = 5, message = "error request place must be at least 5 characters !")

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String clientRequestPlace;

    /// //////////////////////////////////////////////////////////

    @NotNull(message = "error estimated guests can not be empty !")
    @Min(value = 1, message = "error estimated guests must be at least 1 !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer clientRequestEstimatedGuest;

    /// //////////////////////////////////////////////////////////

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String clientRequestStatus;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error request description can not be empty !")
    @Size(min = 5, message = "error request description must be at least 5 characters !")

    @Column(columnDefinition = "VARCHAR(100) NOT NULL")
    private String clientRequestDescription;

    /// //////////////////////////////////////////////////////////

    @Column(columnDefinition = "INT NOT NULL")
    private Integer clientId;

    /// //////////////////////////////////////////////////////////

    @Email(message = "error client email must be a valid email !")
    @NotEmpty(message = "error client email can not be empty !")

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    private String clientEmail;
}
