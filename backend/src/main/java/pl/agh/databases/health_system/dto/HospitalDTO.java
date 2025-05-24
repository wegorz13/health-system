package pl.agh.databases.health_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.agh.databases.health_system.domain.Doctor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class HospitalDTO {
    private Long id;
    private String name;
    private String address;
    private List<Doctor> doctors = new ArrayList<>();
    private String phone;
    private String email;

    public HospitalDTO(Long id, String name, String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}
