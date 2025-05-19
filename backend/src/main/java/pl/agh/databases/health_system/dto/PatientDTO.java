package pl.agh.databases.health_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class PatientDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String roles;

}
