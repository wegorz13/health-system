package pl.agh.databases.health_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
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
