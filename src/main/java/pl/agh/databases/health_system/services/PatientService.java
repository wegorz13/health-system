package pl.agh.databases.health_system.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.models.Patient;
import pl.agh.databases.health_system.repositories.PatientRepository;
import pl.agh.databases.health_system.requests.CreatePatientRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public PatientService(PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Patient createPatient(CreatePatientRequest request) {
        Patient patient = new Patient();
        // TODO: check if username and email is unique
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setGender(request.getGender());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setRoles(request.getRoles());
        patient.setUsername(request.getUsername());

        patientRepository.save(patient);
        return patient;
    }

    public List<Patient> getRelatives(Long id) {
        return patientRepository.findRelativesById(id).orElse(new ArrayList<>());
    }
}
