package pl.agh.databases.health_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.domain.Hospital;
import pl.agh.databases.health_system.dto.HospitalDTO;
import pl.agh.databases.health_system.mapper.HospitalMapper;
import pl.agh.databases.health_system.repository.HospitalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final DoctorService doctorService;
    private final HospitalMapper hospitalMapper;

    public List<HospitalDTO> getAllHospitalsWithDoctors() {
        return hospitalRepository.findAll().stream()
                .map(hospitalMapper::toDTO)
                .peek(this::enrichWithDoctors)
                .toList();
    }

    public HospitalDTO getHospitalWithDoctorsById(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        HospitalDTO hospitalDTO = hospitalMapper.toDTO(hospital);
        enrichWithDoctors(hospitalDTO);
        return hospitalDTO;
    }

    private void enrichWithDoctors(HospitalDTO hospitalDTO) {
        List<Doctor> doctors = doctorService.getAllDoctorsByHospitalId(hospitalDTO.getId());
        hospitalDTO.setDoctors(doctors);
    }
}

