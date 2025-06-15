package pl.agh.databases.health_system.mapper;

import org.springframework.stereotype.Component;
import pl.agh.databases.health_system.domain.Hospital;
import pl.agh.databases.health_system.dto.HospitalDTO;
import pl.agh.databases.health_system.dto.request.CreateHospitalRequest;

@Component
public class HospitalMapper {

    public static HospitalDTO toDTO(Hospital hospital) {
        return new HospitalDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getAddress(),
                hospital.getPhone(),
                hospital.getEmail()
        );
    }

    public static Hospital toEntity(CreateHospitalRequest request) {
        Hospital hospital = new Hospital();
        hospital.setName(request.getName());
        hospital.setAddress(request.getAddress());
        hospital.setPhone(request.getPhone());
        hospital.setEmail(request.getEmail());

        return hospital;
    }

}
