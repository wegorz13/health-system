package pl.agh.databases.health_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.agh.databases.health_system.dto.request.CreatePatientRequest;
import pl.agh.databases.health_system.dto.response.PatientResponse;
import pl.agh.databases.health_system.domain.Patient;

@Component
@RequiredArgsConstructor
public class PatientMapper {

    public Patient toEntity(CreatePatientRequest request) {
        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setUsername(request.getUsername());
        return patient;

    }

    public PatientResponse toResponse(Patient patient) {
        return PatientResponse.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .username(patient.getUsername())
                .email(patient.getEmail())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .build();
    }
}
