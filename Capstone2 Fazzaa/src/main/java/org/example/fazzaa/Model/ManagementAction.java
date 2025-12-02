package org.example.fazzaa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ManagementAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actionId;

    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "Action type can not be empty !")

    @Column(columnDefinition = "VARCHAR(40) NOT NULL")
    private String actionType;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Action date can not be empty !")

    @Column(columnDefinition = "DATETIME NOT NULL")
    private LocalDateTime actionDate ;

    /// ///////////////////////////////////////////////////

    @Size(max = 500, message = "statement can not be larger than 500 characters !")

    @Column(columnDefinition = "TEXT")
    private String statement;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Management ID can not be empty !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer managementId;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Request ID can not be empty !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer requestId;
}

