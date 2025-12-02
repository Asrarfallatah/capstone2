package org.example.fazzaa.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Management {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer managementId;

    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "Management type can not be empty !")
    @Pattern(regexp = "^(ADMIN|admin|Admin|COMMUNICATION|communication|Communication)$",
             message = "error management type must be admin or communication only !")

    @Column(columnDefinition = "VARCHAR(20) NOT NULL")
    private String managementType;


}
