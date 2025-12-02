package org.example.fazzaa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RepresentativeTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer repTeamId;

    /// ///////////////////////////

    @NotEmpty(message = "Team name can not be empty !")

    @Column(columnDefinition = "VARCHAR(40) NOT NULL")
    private String teamName;

}

