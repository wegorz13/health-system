package pl.agh.databases.health_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.domain.Patient;
import pl.agh.databases.health_system.domain.Visit;
import pl.agh.databases.health_system.dto.VisitDTO;
import pl.agh.databases.health_system.mapper.VisitMapper;
import pl.agh.databases.health_system.repository.DoctorRepository;
import pl.agh.databases.health_system.repository.PatientRepository;
import pl.agh.databases.health_system.repository.VisitRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final VisitMapper visitMapper;

    public List<VisitDTO> getVisitsByPatientId(Long patientId) {
        // Check if patient exists
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        return visitRepository.findByPatientId(patientId);
    }

    public List<VisitDTO> getVisitsByDoctorId(Long doctorId) {
        // Check if doctor exists
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        return visitRepository.findByDoctorId(doctorId);
    }

    public VisitDTO getVisitDetails(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        return visitMapper.mapToDTO(visit);
    }

}
