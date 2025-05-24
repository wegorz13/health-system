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
import pl.agh.databases.health_system.dto.HospitalWithHoursDTO;
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
        List<DoctorDTO> doctorDTOs = doctorService.getAllDoctors();

        return new ResponseEntity<>(doctorDTOs, HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsForPatient(@PathVariable("id") Long id) {
        List<DoctorDTO> doctorDTOS = doctorService.getAllDoctorsByPatientId(id);

        return new ResponseEntity<>(doctorDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorDetails(@PathVariable("id") Long id) {
        DoctorDTO doctorDTO = doctorService.getDoctorDetails(id);

        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
    }
}
