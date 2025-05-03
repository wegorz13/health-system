package pl.agh.databases.health_system.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.databases.health_system.models.Patient;
import pl.agh.databases.health_system.objects.PatientDTO;
import pl.agh.databases.health_system.requests.CreatePatientRequest;
import pl.agh.databases.health_system.services.PatientService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/auth/me")
    public String loggedInUser(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/auth/register")
    public ResponseEntity<PatientDTO> signUp(@RequestBody CreatePatientRequest request){
        Patient patient = patientService.createPatient(request);

        PatientDTO responsePatient = new PatientDTO(patient.getFirstName(), patient.getLastName(), patient.getUsername(), patient.getEmail(), patient.getDateOfBirth(), patient.getGender(), patient.getRoles());

        return new ResponseEntity<>(responsePatient, HttpStatus.CREATED);
    }

    @GetMapping("/relatives/{id}")
    public ResponseEntity<List<PatientDTO>> relatives(@PathVariable("id") Long id){
        List<Patient> relatives = patientService.getRelatives(id);

        List<PatientDTO> relativesDTOS = relatives.stream().map((patient -> {
            PatientDTO relativeDTO = new PatientDTO();
            relativeDTO.setFirstName(patient.getFirstName());
            relativeDTO.setLastName(patient.getLastName());
            relativeDTO.setUsername(patient.getUsername());
            return relativeDTO;
        })).toList();

        return new ResponseEntity<>(relativesDTOS, HttpStatus.OK);
    }
}
