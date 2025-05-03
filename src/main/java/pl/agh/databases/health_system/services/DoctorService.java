package pl.agh.databases.health_system.services;

import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.models.Doctor;
import pl.agh.databases.health_system.objects.HospitalDTO;
import pl.agh.databases.health_system.objects.HospitalWithHours;
import pl.agh.databases.health_system.repositories.DoctorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctorsByHospitalId(Long hospitalId) {
        return doctorRepository.findDoctorsByHospitalId(hospitalId).orElse(new ArrayList<Doctor>());
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Map<HospitalDTO, List<String>> getDoctorDetails(Long id) {
        List<HospitalWithHours> list = doctorRepository.findDoctorDetails(id).orElse(new ArrayList<>());

        return list.stream().collect(Collectors.toMap(
                HospitalWithHours::getHospital, HospitalWithHours::getHours));
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }
}
