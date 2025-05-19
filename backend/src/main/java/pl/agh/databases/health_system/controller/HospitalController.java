package pl.agh.databases.health_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.databases.health_system.dto.HospitalDTO;
import pl.agh.databases.health_system.service.HospitalService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hospitals")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping("/")
    public ResponseEntity<List<HospitalDTO>> hospitalIndex(){
        return ResponseEntity.ok(hospitalService.getAllHospitalsWithDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalDTO> hospitalDetails(@PathVariable Long id){
        return ResponseEntity.ok(hospitalService.getHospitalWithDoctorsById(id));
    }
}
