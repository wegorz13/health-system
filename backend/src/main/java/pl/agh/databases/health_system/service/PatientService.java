package pl.agh.databases.health_system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.dto.response.PatientResponse;
import pl.agh.databases.health_system.mapper.PatientMapper;
import pl.agh.databases.health_system.domain.Patient;
import pl.agh.databases.health_system.repository.PatientRepository;
import pl.agh.databases.health_system.dto.request.CreatePatientRequest;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientMapper patientMapper;

    @Transactional
    public PatientResponse createPatient(CreatePatientRequest request) {
        Patient patient = patientMapper.toEntity(request);
        patient.setPassword(passwordEncoder.encode(request.getPassword()));

        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toResponse(savedPatient);
    }

}
