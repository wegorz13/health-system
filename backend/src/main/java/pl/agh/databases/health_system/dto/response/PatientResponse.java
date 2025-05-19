package pl.agh.databases.health_system.dto.response;

import lombok.Builder;
import lombok.Value;
import java.time.LocalDate;
import java.util.Set;

@Value
@Builder
public class PatientResponse {
    String firstName;
    String lastName;
    String username;
    String email;
    LocalDate dateOfBirth;
    String gender;
    Set<String> roles;
}