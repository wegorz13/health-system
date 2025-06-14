package pl.agh.databases.health_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.databases.health_system.dto.VisitDTO;
import pl.agh.databases.health_system.dto.request.CreateVisitRequest;
import pl.agh.databases.health_system.service.VisitService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<VisitDTO>> getVisitsForPatient(@PathVariable("id") Long patientId) {
        return new ResponseEntity<>(visitService.getVisitsByPatientId(patientId), HttpStatus.OK);
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<VisitDTO>> getVisitsForDoctor(@PathVariable("id") Long doctorId) {
        return new ResponseEntity<>(visitService.getVisitsByDoctorId(doctorId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDTO> getVisitDetails(@PathVariable("id") Long id) {
        return new ResponseEntity<>(visitService.getVisitDetails(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<VisitDTO> createVisit(@RequestBody CreateVisitRequest request) {
        return new ResponseEntity<>(visitService.createVisit(request), HttpStatus.CREATED);
    }
}