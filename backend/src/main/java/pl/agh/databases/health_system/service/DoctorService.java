package pl.agh.databases.health_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.HospitalDTO;
import pl.agh.databases.health_system.dto.HospitalWithHoursDTO;
import pl.agh.databases.health_system.mapper.DoctorMapper;
import pl.agh.databases.health_system.repository.DoctorRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public List<DoctorDTO> getAllDoctorsByHospitalId(Long hospitalId) {
        return doctorRepository.findDoctorsByHospitalId(hospitalId).stream()
                .map(DoctorMapper::toDTO)
                .toList();
    }

    public List<DoctorDTO> getAllDoctorsByPatientId(Long patientId) {
        List<Doctor> doctors = doctorRepository.findDoctorsByPatientId(patientId);
        return doctors.stream().map(DoctorMapper::toDTO).toList();
    }

    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        return doctors.stream().map(DoctorMapper::toDTO).toList();
    }


    public DoctorDTO getDoctorDetails(Long id) {
        List<HospitalWithHoursDTO> workingHours = doctorRepository.findDoctorDetails(id);
        Doctor doctor = doctorRepository.findById(id).orElse(null);

        if (doctor == null){
            throw new RuntimeException("Doctor not found"); //TODO
        }

        DoctorDTO doctorDTO = DoctorMapper.toDTO(doctor);
        doctorDTO.setWorkingHours(workingHours);

        return doctorDTO;
    }
}
