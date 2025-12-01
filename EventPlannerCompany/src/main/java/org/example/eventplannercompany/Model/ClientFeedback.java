package org.example.eventplannercompany.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientFeedbackId;

    /// //////////////////////////////////////////////////////////

    @NotNull(message = "error feedback rating can not be empty !")
    @Min(value = 1, message = "error rating must be between 1 and 5")
    @Max(value = 5, message = "error rating must be between 1 and 5")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer rating;

    /// //////////////////////////////////////////////////////////

    @NotEmpty(message = "error feedback can not be empty !")
    @Size(min = 5, message = "feedback must be at least 5 characters !")

    @Column(columnDefinition = "VARCHAR(100) NOT NULL")
    private String feedbackText;

    /// //////////////////////////////////////////////////////////

    @NotNull(message = "error client id can not be empty !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer clientId;

    /// //////////////////////////////////////////////////////////

    @NotNull(message = "error client request id can not be empty !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer clientRequestId;
}
