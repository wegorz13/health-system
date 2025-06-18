package pl.agh.databases.health_system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.domain.Visit;
import pl.agh.databases.health_system.dto.PatientDTO;
import pl.agh.databases.health_system.exceptions.PatientAlreadyRecommendsVisitException;
import pl.agh.databases.health_system.exceptions.PatientAlreadyRelativeException;
import pl.agh.databases.health_system.exceptions.ResourceNotFoundException;
import pl.agh.databases.health_system.mapper.PatientMapper;
import pl.agh.databases.health_system.domain.Patient;
import pl.agh.databases.health_system.repository.PatientRepository;
import pl.agh.databases.health_system.dto.request.CreatePatientRequest;
import pl.agh.databases.health_system.repository.VisitRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final VisitRepository visitRepository;

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(PatientMapper::toResponse).toList();
    }

    @Transactional
    public PatientDTO createPatient(CreatePatientRequest request) {
        Patient patient = PatientMapper.toEntity(request);
        patient.setPassword(passwordEncoder.encode(request.getPassword()));

        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.toResponse(savedPatient);
    }

    public void deletePatient(Long patientId) {
        Patient patient = patientRepository.findWithVisitsById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        visitRepository.deleteAll(patient.getVisits());
        patientRepository.delete(patient);
    }

    public List<PatientDTO> getRelatives(Long id) {
        List<Patient> relatives = patientRepository.findRelativesById(id);

        return relatives.stream().map((patient -> {
            PatientDTO relativeDTO = new PatientDTO();
            relativeDTO.setFirstName(patient.getFirstName());
            relativeDTO.setLastName(patient.getLastName());
            relativeDTO.setUsername(patient.getUsername());
            return relativeDTO;
        })).toList();
    }

    @Transactional
    public void addRelative(Long patientId, Long relativeId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Patient relative = patientRepository.findById(relativeId).orElseThrow(() -> new ResourceNotFoundException("Relative not found"));

        if (patientRepository.verifyIsRelative(patientId, relativeId)){
            throw new PatientAlreadyRelativeException(patientId, relativeId);
        }

        patient.getRelatives().add(relative);
        relative.getRelatives().add(patient);
        patientRepository.save(patient);
        patientRepository.save(relative);
    }

    @Transactional
    public void deleteRelative(Long patientId, Long relativeId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Patient relative = patientRepository.findById(relativeId).orElseThrow(() -> new ResourceNotFoundException("Relative not found"));

        patientRepository.deleteRelationBetweenPatients(patientId, relativeId);

        patient.getRelatives().remove(relative);
        relative.getRelatives().remove(patient);
        patientRepository.save(patient);
        patientRepository.save(relative);

    }

    @Transactional
    public void recommendDoctorVisit(Long patientId, Long visitId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Visit visit = patient.getVisits().stream().filter((v) -> v.getId().equals(visitId)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Visit not found"));

        if (visit.isRecommends()){
            throw new PatientAlreadyRecommendsVisitException(patientId, visitId);
        }
        visit.setRecommends(true);

        visitRepository.save(visit);
        patientRepository.save(patient);
    }
}
