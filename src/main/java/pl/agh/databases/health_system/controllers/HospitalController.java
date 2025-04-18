package pl.agh.databases.health_system.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.databases.health_system.models.Hospital;
import pl.agh.databases.health_system.objects.HospitalDTO;
import pl.agh.databases.health_system.services.DoctorService;
import pl.agh.databases.health_system.services.HospitalService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/hospitals")
public class HospitalController {
    private HospitalService hospitalService;
    private DoctorService doctorService;

    public HospitalController(HospitalService hospitalService, DoctorService doctorService) {
        this.hospitalService = hospitalService;
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public ResponseEntity<List<HospitalDTO>> hospitalIndex(){
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        List<HospitalDTO> responseHospitals = hospitals.stream().map((hospital) -> {
            HospitalDTO responseHospital = new HospitalDTO(hospital.getName(), hospital.getAddress(), hospital.getPhone(), hospital.getEmail());
            responseHospital.setDoctors(doctorService.getAllDoctorsByHospitalName(hospital.getName()));

            return responseHospital;
        }).toList();

        return new ResponseEntity<>(responseHospitals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalDTO> hospitalDetails(@PathVariable Long id){
        Hospital hospital = hospitalService.getHospitalById(id);

        HospitalDTO responseHospital = new HospitalDTO(hospital.getName(), hospital.getAddress(), hospital.getPhone(), hospital.getEmail());
        responseHospital.setDoctors(doctorService.getAllDoctorsByHospitalName(hospital.getName()));

        return new ResponseEntity<>(responseHospital, HttpStatus.OK);
    }
}
