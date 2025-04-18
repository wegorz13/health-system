package pl.agh.databases.health_system.services;

import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.models.Doctor;
import pl.agh.databases.health_system.repositories.DoctorRepository;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctorsByHospitalName(String hospitalName) {
        return doctorRepository.findDoctorsByHospitalName(hospitalName);
    }
}
