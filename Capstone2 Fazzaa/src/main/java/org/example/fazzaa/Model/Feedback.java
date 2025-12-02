package org.example.fazzaa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Rating can not be empty !!")
    @Min(value = 1, message = "Rating can not be less than 1 !")
    @Max(value = 5, message = "Rating can not be more than 5 !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer rating;

    /// ///////////////////////////////////////////////////

    @Size(max = 1000, message = "Comment can not exceed 1000 characters !")

    @Column(columnDefinition = "TEXT")
    private String comment;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Feedback date can not be null !")

    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate feedbackDate;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Request ID  can not be empty !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer requestId;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Client ID  can not be empty  !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer clientId;
}

