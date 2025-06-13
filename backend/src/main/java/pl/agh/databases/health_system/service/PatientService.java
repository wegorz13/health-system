package pl.agh.databases.health_system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.PatientDTO;
import pl.agh.databases.health_system.dto.VisitDTO;
import pl.agh.databases.health_system.dto.response.PatientResponse;
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

    public List<VisitDTO> getPatientVisits(Long patientId) {
        return visitRepository.findByPatientId(patientId);
    }

    public List<DoctorDTO> getPatientRelativesRecommendedDoctors(Long patientId, int depth) {
        List<Long> relativeIds = patientRepository.findNthRelativesIdsByPatientId(patientId, depth);

        return doctorRepository.findDoctorsRecommendedByPatientIds(relativeIds).stream().map(DoctorMapper::toDTO).toList();
    }
}
