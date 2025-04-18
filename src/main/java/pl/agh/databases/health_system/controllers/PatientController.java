package pl.agh.databases.health_system.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.databases.health_system.models.Patient;
import pl.agh.databases.health_system.objects.PatientDTO;
import pl.agh.databases.health_system.requests.CreatePatientRequest;
import pl.agh.databases.health_system.services.PatientService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/me")
    public String loggedInUser(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/register")
    public ResponseEntity<PatientDTO> signUp(@RequestBody CreatePatientRequest request){
        Patient patient = patientService.createPatient(request);

        PatientDTO responsePatient = new PatientDTO(patient.getFirstName(), patient.getLastName(), patient.getUsername(), patient.getEmail(), patient.getDateOfBirth(), patient.getGender(), patient.getRoles());

        return new ResponseEntity<>(responsePatient, HttpStatus.CREATED);
    }
}
