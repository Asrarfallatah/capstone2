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
public class EventRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;

    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "Event type can not be empty !")

    @Column(columnDefinition = "VARCHAR(30) NOT NULL")
    private String eventType;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Event date can not be null !")

    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate eventDate;
    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "Event theme can not be empty !")

    @Column(columnDefinition = "VARCHAR(40) NOT NULL")
    private String theme;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Budget can not be empty !")
    @Min(value = 0, message = "Budget can not be negative !")

    @Column(columnDefinition = "DOUBLE NOT NULL")
    private Double budget;
    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "Place can not be empty !")

    @Column(columnDefinition = "VARCHAR(60) NOT NULL")
    private String place;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "estimated guest can not be null !")
    @Min(value = 1, message = "estimated guest must be At least 1 guest  !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer estimatedGuests;

    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "Description can not be empty !")
    @Size(min = 10 , message = "Description must be at least 10 characters !")

    @Column(columnDefinition = "TEXT NOT NULL")
    private String description;

    /// ///////////////////////////////////////////////////

    @NotEmpty(message = "Request status can not be empty !")
    @Pattern(regexp = "^(PENDING|pending)$",
            message = "error request status must be PENDING !")

    @Column(columnDefinition = "VARCHAR(20) NOT NULL")
    private String requestStatus;

    /// ///////////////////////////////////////////////////

    @NotNull(message = "Client ID can not be null !")

    @Column(columnDefinition = "INT NOT NULL")
    private Integer clientId;
}
