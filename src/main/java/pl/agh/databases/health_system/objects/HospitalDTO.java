package pl.agh.databases.health_system.objects;

import pl.agh.databases.health_system.models.Doctor;

import java.util.ArrayList;
import java.util.List;

public class HospitalDTO {
    private String name;
    private String address;
    private List<Doctor> doctors = new ArrayList<Doctor>();
    private String phone;
    private String email;

    public HospitalDTO(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
