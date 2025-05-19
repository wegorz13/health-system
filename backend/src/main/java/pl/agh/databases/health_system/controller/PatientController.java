package pl.agh.databases.health_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.databases.health_system.dto.response.PatientResponse;
import pl.agh.databases.health_system.dto.request.CreatePatientRequest;
import pl.agh.databases.health_system.service.PatientService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/me")
    public ResponseEntity<String> getLoggedInUser(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }

    @PostMapping("/register")
    public ResponseEntity<PatientResponse> registerPatient(@RequestBody CreatePatientRequest request) {
        PatientResponse patient = patientService.createPatient(request);
        return ResponseEntity.ok(patient);
    }

}
