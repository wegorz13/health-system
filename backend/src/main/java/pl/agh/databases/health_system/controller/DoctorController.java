package pl.agh.databases.health_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.service.DoctorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("/")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return new ResponseEntity<>( doctorService.getAllDoctors(), HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsForPatient(@PathVariable("id") Long id) {
        return new ResponseEntity<>(doctorService.getAllDoctorsByPatientId(id), HttpStatus.OK);
    }

    @GetMapping("/hospital/{id}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsForHospital(@PathVariable("id") Long id) {
        return new ResponseEntity<>(doctorService.getAllDoctorsByHospitalId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorDetails(@PathVariable("id") Long id) {
        return new ResponseEntity<>(doctorService.getDoctorDetails(id), HttpStatus.OK);
    }
}
