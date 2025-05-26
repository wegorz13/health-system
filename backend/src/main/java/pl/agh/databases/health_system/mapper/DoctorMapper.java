package pl.agh.databases.health_system.mapper;

import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.HospitalWithHoursDTO;

import java.util.List;

public class DoctorMapper {
    public static DoctorDTO toDTO(Doctor doctor) {
        return DoctorDTO.builder().id(
                doctor.getId()).fullName(
                doctor.getFirstName() + " " + doctor.getLastName()).specialty(
                doctor.getSpecialty()).build();
    }
}
