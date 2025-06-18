package pl.agh.databases.health_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.databases.health_system.dto.PatientDTO;
import pl.agh.databases.health_system.dto.request.CreatePatientRequest;
import pl.agh.databases.health_system.service.PatientService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/auth/me")
    public ResponseEntity<String> getLoggedInUser(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<PatientDTO> registerPatient(@RequestBody CreatePatientRequest request) {
        return ResponseEntity.ok(patientService.createPatient(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long patientId) {
        patientService.deletePatient(patientId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatives/{id}")
    public ResponseEntity<List<PatientDTO>> getRelatives(@PathVariable("id") Long id){
        return new ResponseEntity<>(patientService.getRelatives(id), HttpStatus.OK);
    }

    @PostMapping("/relatives/{id}")
    public ResponseEntity<Void> addRelative(@PathVariable ("id") Long patientId, @RequestParam("relative_id") Long relativeId){
        patientService.addRelative(patientId, relativeId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/relatives/{id}")
    public ResponseEntity<Void> deleteRelative(@PathVariable ("id") Long patientId, @RequestParam("relative_id") Long relativeId){
        patientService.deleteRelative(patientId, relativeId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/recommended/{id}")
    public ResponseEntity<Void> recommendDoctorVisit(@PathVariable("id") Long patientId, @RequestParam("visit_id") Long visitId){
        patientService.recommendDoctorVisit(patientId, visitId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
