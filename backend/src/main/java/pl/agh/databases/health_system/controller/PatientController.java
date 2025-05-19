package pl.agh.databases.health_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.databases.health_system.domain.Patient;
import pl.agh.databases.health_system.dto.PatientDTO;
import pl.agh.databases.health_system.dto.request.CreatePatientRequest;
import pl.agh.databases.health_system.dto.response.PatientResponse;
import pl.agh.databases.health_system.service.PatientService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/auth/me")
    public ResponseEntity<String> getLoggedInUser(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<PatientResponse> registerPatient(@RequestBody CreatePatientRequest request) {
        PatientResponse patient = patientService.createPatient(request);
        return ResponseEntity.ok(patient);
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
