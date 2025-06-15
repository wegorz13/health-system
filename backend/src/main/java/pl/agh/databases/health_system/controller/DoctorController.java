package pl.agh.databases.health_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.request.CreateDoctorRequest;
import pl.agh.databases.health_system.dto.request.CreateWorkDayScheduleRequest;
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

    @PostMapping("/")
    public ResponseEntity<Void> createDoctor(@RequestBody CreateDoctorRequest request) {
        doctorService.createDoctor(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable("id") Long doctorId) {
        doctorService.deleteDoctor(doctorId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> addDoctorWorkDaySchedule(@PathVariable("id") Long doctorId, @RequestBody CreateWorkDayScheduleRequest request){
        doctorService.addDoctorWorkDaySchedule(request, doctorId);

        return new ResponseEntity<>(HttpStatus.CREATED);
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

    @GetMapping("/recommendations")
    public ResponseEntity<List<DoctorDTO>> getBestRecommendedDoctors() {
        return new ResponseEntity<>(doctorService.getBestRecommendedDoctors(), HttpStatus.OK);
    }
    @GetMapping("/recommendations/{id}")
    public ResponseEntity<List<DoctorDTO>> getRelativeBasedRecommendedDoctors(@PathVariable("id") Long patientId) {
        return new ResponseEntity<>(doctorService.getRelativeBasedRecommendedDoctors(patientId), HttpStatus.OK);
    }
}
