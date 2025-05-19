package pl.agh.databases.health_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.repository.DoctorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctorsByHospitalName(String hospitalName) {
        return doctorRepository.findDoctorsByHospitalName(hospitalName);
    }
}
