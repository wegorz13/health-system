package pl.agh.databases.health_system.mapper;

import org.springframework.stereotype.Component;
import pl.agh.databases.health_system.domain.Hospital;
import pl.agh.databases.health_system.dto.HospitalDTO;

@Component
public class HospitalMapper {
    public HospitalDTO toDTO(Hospital hospital) {
        return new HospitalDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getAddress(),
                hospital.getPhone(),
                hospital.getEmail()
        );
    }
}
