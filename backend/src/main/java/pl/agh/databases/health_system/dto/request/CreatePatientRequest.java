package pl.agh.databases.health_system.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePatientRequest {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String username;
    private String password;
    private String roles;
    private String email;
    private String gender;

}
