package pl.agh.databases.health_system.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.databases.health_system.models.Hospital;
import pl.agh.databases.health_system.services.HospitalService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hospitals")
public class HospitalController {
    private HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Hospital>> hospitalIndex(){
        return new ResponseEntity<>(hospitalService.getAllHospitals(), HttpStatus.OK);
    }

    @GetMapping("/c")
    public ResponseEntity<Hospital> hospitalDetails(@PathVariable Long id){
        Hospital hospital = hospitalService.getHospitalById(id);

        return new ResponseEntity<>(hospital, HttpStatus.OK);
    }
}
