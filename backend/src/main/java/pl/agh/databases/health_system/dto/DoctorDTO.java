package pl.agh.databases.health_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Long id;
    private String fullName;
    private String specialty;
    private Map<HospitalDTO, List<String>> workingHours;

}
