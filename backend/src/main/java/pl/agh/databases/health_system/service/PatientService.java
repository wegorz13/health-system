package pl.agh.databases.health_system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.domain.Visit;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.PatientDTO;
import pl.agh.databases.health_system.dto.response.PatientResponse;
import pl.agh.databases.health_system.exceptions.PatientAlreadyRecommendsVisitException;
import pl.agh.databases.health_system.exceptions.PatientAlreadyRelativeException;
import pl.agh.databases.health_system.exceptions.ResourceNotFoundException;
import pl.agh.databases.health_system.mapper.DoctorMapper;
import pl.agh.databases.health_system.mapper.PatientMapper;
import pl.agh.databases.health_system.domain.Patient;
import pl.agh.databases.health_system.repository.DoctorRepository;
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
    private final DoctorRepository doctorRepository;

    @Transactional
    public PatientResponse createPatient(CreatePatientRequest request) {
        Patient patient = PatientMapper.toEntity(request);
        patient.setPassword(passwordEncoder.encode(request.getPassword()));

        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.toResponse(savedPatient);
    }

    public void deletePatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

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

    public List<Visit> getPatientVisits(Long patientId) {
        return visitRepository.findByPatientId(patientId);
    }

    public List<DoctorDTO> getDoctorsRecommendedByPatientRelatives(Long patientId, int depth) {
        List<Long> relativeIds = patientRepository.findNthRelativesIdsByPatientId(patientId, depth);

        return doctorRepository.findDoctorsRecommendedByPatientIds(relativeIds).stream().map(DoctorMapper::toDTO).toList();
    }

    public void addRelative(Long patientId, Long relativeId) {
        verifyPatientAndRelativeExistOrThrow(patientId, relativeId);

        if (patientRepository.verifyIsRelative(patientId, relativeId)){
            throw new PatientAlreadyRelativeException(patientId, relativeId);
        }

        patientRepository.addRelativeToPatient(patientId, relativeId);
    }

    public void deleteRelative(Long patientId, Long relativeId) {
        verifyPatientAndRelativeExistOrThrow(patientId, relativeId);

        patientRepository.deleteRelative(patientId, relativeId);
    }

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

    private void verifyPatientAndRelativeExistOrThrow(Long patientId, Long relativeId) {
        patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        patientRepository.findById(relativeId).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }
}
