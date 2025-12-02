package org.example.fazzaa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer withdrawId;

    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "Withdraw reason can not be empty !")

    @Column(columnDefinition = "TEXT NOT NULL")
    private String reason;

    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "Fee paid status can not be empty !")
    @Pattern(regexp = "^(TRUE|true|True|False|FALSE|false)$",
             message = "error fee paid status must be true or false only !")

    @Column(columnDefinition = "VARCHAR(30) NOT NULL")
    private String feePaid ;

    /// ///////////////////////////////////////////////////

    @Column(columnDefinition = "TEXT")
    private String justification;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Request ID can not be empty !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer requestId;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Client ID can not be empty !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer clientId;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Communication management ID can not be empty !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer communicationManagerId;

}
