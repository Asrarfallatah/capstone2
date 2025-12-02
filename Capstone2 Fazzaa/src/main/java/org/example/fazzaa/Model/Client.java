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
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientId;

    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "client name can not be empty !")
    @Size(min = 3, message = "client name must be at least 3 characters !")

    @Column(columnDefinition = "VARCHAR(40) NOT NULL")
    private String clientName;

    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "client email can not be empty !")
    @Email(message = "client email must be a valid email !")

    @Column(columnDefinition = "VARCHAR(60) NOT NULL UNIQUE")
    private String clientEmail;

    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "client phone number can not be empty !")
    @Size(min = 10 , max = 10 ,message = "client phone number must be 10 numbers only ! ")

    @Column(columnDefinition = "VARCHAR(20) NOT NULL")
    private String clientPhone;
}
