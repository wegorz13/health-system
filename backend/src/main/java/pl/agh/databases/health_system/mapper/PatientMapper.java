package pl.agh.databases.health_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.agh.databases.health_system.dto.PatientDTO;
import pl.agh.databases.health_system.dto.request.CreatePatientRequest;
import pl.agh.databases.health_system.domain.Patient;

@Component
@RequiredArgsConstructor
public class PatientMapper {

    public static Patient toEntity(CreatePatientRequest request) {
        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setUsername(request.getUsername());
        return patient;

    }

    public static PatientDTO toResponse(Patient patient) {
        return PatientDTO.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .username(patient.getUsername())
                .email(patient.getEmail())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .build();
    }
}
