package pl.agh.databases.health_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.domain.Hospital;
import pl.agh.databases.health_system.domain.Visit;
import pl.agh.databases.health_system.domain.WorkDaySchedule;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.HospitalDTO;
import pl.agh.databases.health_system.dto.request.CreateHospitalRequest;
import pl.agh.databases.health_system.exceptions.ResourceNotFoundException;
import pl.agh.databases.health_system.mapper.HospitalMapper;
import pl.agh.databases.health_system.repository.DoctorRepository;
import pl.agh.databases.health_system.repository.HospitalRepository;
import pl.agh.databases.health_system.repository.VisitRepository;
import pl.agh.databases.health_system.repository.WorkDayScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final DoctorService doctorService;
    private final DoctorRepository doctorRepository;
    private final WorkDayScheduleRepository workDayScheduleRepository;
    private final VisitRepository visitRepository;

    public void createHospital(CreateHospitalRequest request) {
        Hospital hospital = HospitalMapper.toEntity(request);

        hospitalRepository.save(hospital);
    }

    public void deleteHospital(Long hospitalId) {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new ResourceNotFoundException("Hospital with id " + hospitalId + " not found"));

        List<Doctor> doctors = doctorRepository.findDoctorsByHospitalId(hospitalId);

        doctors.forEach(doctor -> {
            List<WorkDaySchedule> schedules = doctor.getSchedules();
            workDayScheduleRepository.deleteAll(schedules);
        });

        List<Visit> visits = visitRepository.findByHospitalId(hospitalId);
        visits.forEach(visit -> {
            if (visit.getDate().isAfter(LocalDateTime.now())){
                visitRepository.delete(visit);
            }
        });

        hospitalRepository.delete(hospital);
    }

    public List<HospitalDTO> getAllHospitalsWithDoctors() {
        return hospitalRepository.findAll().stream()
                .map(HospitalMapper::toDTO)
                .peek(this::enrichWithDoctors)
                .toList();
    }

    public HospitalDTO getHospitalWithDoctorsById(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        HospitalDTO hospitalDTO = HospitalMapper.toDTO(hospital);
        enrichWithDoctors(hospitalDTO);
        return hospitalDTO;
    }

    private void enrichWithDoctors(HospitalDTO hospitalDTO) {
        List<DoctorDTO> doctors = doctorService.getAllDoctorsByHospitalId(hospitalDTO.getId());
        hospitalDTO.setDoctors(doctors);
    }
}

