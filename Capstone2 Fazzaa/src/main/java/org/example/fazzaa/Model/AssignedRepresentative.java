package org.example.fazzaa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AssignedRepresentative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assignedId;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "request ID can not be empty !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer requestId;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "representative team ID can not be empty !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer repTeamId;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Assigned date can not be null !")

    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate assignedDate ;
}
