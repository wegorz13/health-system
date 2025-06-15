package pl.agh.databases.health_system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRequest {
    private String firstName;
    private String lastName;
    private String specialty;
}