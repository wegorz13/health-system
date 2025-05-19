package pl.agh.databases.health_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.HospitalDTO;
import pl.agh.databases.health_system.service.DoctorService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();

        List<DoctorDTO> doctorDTOs = doctors.stream().map((doctor) -> {
            DoctorDTO doctorDTO = new DoctorDTO();
            doctorDTO.setId(doctor.getId());
            doctorDTO.setFullName(doctor.getFirstName()+" "+doctor.getLastName());
            doctorDTO.setSpecialty(doctor.getSpecialty());
            return doctorDTO;
        } ).toList();

        return new ResponseEntity<>(doctorDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> doctorDetails(@PathVariable("id") Long id) {
        Map<HospitalDTO, List<String>> hospitalsWithHours = doctorService.getDoctorDetails(id);
        Doctor doctor = doctorService.getDoctorById(id);
        DoctorDTO doctorDTO = new DoctorDTO(doctor.getId(), doctor.getFirstName()+" "+doctor.getLastName(), doctor.getSpecialty(), hospitalsWithHours);

        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
    }
}
