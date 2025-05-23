package pl.agh.databases.health_system.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private Long id;
    private String fullName;
    private String specialty;
    private Map<HospitalDTO, List<String>> workingHours;

}
