package pl.agh.databases.health_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.HospitalDTO;
import pl.agh.databases.health_system.dto.HospitalWithHours;
import pl.agh.databases.health_system.repository.DoctorRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;


    public List<Doctor> getAllDoctorsByHospitalId(Long hospitalId) {
        return doctorRepository.findDoctorsByHospitalId(hospitalId);
    }

    public List<DoctorDTO> getAllDoctorsByPatientId(Long patientId) {
        List<Doctor> doctors = doctorRepository.findDoctorsByPatientId(patientId);
        return doctors.stream().map((doc) ->
                DoctorDTO.builder()
                        .id(doc.getId())
                        .fullName(doc.getFirstName()+" "+doc.getLastName())
                        .specialty(doc.getSpecialty()).build())
                .toList();
    }

    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        return doctors.stream().map((doctor) ->
            DoctorDTO.builder().id(doctor.getId()).fullName(doctor.getFirstName()+" "+doctor.getLastName()).specialty(doctor.getSpecialty()).build()
         ).toList();
    }

    public Map<HospitalDTO, List<String>> getDoctorWorkingHours(Long id) {
        List<HospitalWithHours> list = doctorRepository.findDoctorDetails(id);

        return list.stream().collect(Collectors.toMap(
                HospitalWithHours::getHospital, HospitalWithHours::getHours));
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }
}
