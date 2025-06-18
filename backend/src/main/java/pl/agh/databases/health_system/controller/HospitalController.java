package pl.agh.databases.health_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.databases.health_system.dto.HospitalDTO;
import pl.agh.databases.health_system.dto.request.CreateHospitalRequest;
import pl.agh.databases.health_system.service.HospitalService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hospitals")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @PostMapping
    public ResponseEntity<HospitalDTO> createHospital(@RequestBody CreateHospitalRequest request) {
        return ResponseEntity.ok( hospitalService.createHospital(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable("id") Long hospitalId) {
        hospitalService.deleteHospital(hospitalId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<HospitalDTO>> hospitalIndex(){
        return ResponseEntity.ok(hospitalService.getAllHospitalsWithDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalDTO> hospitalDetails(@PathVariable Long id){
        return ResponseEntity.ok(hospitalService.getHospitalWithDoctorsById(id));
    }
}
