package pl.agh.databases.health_system.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.databases.health_system.models.Doctor;
import pl.agh.databases.health_system.objects.DoctorDTO;
import pl.agh.databases.health_system.objects.HospitalDTO;
import pl.agh.databases.health_system.services.DoctorService;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            doctorDTO.setFullName(doctor.getFullName());
            doctorDTO.setSpecialty(doctor.getSpecialty());
            return doctorDTO;
        } ).toList();

        return new ResponseEntity<>(doctorDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> doctorDetails(@PathVariable("id") Long id) {
        Map<HospitalDTO, List<String>> hospitalsWithHours = doctorService.getDoctorDetails(id);
        Doctor doctor = doctorService.getDoctorById(id);
        DoctorDTO doctorDTO = new DoctorDTO(doctor.getId(), doctor.getFullName(), doctor.getSpecialty(), hospitalsWithHours);

        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
    }
}
