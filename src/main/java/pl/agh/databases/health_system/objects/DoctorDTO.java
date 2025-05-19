package pl.agh.databases.health_system.objects;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.List;
import java.util.Map;

public class DoctorDTO {
    private Long id;
    private String fullName;
    private String specialty;
    private Map<HospitalDTO, List<String>> workingHours;

    public DoctorDTO() {}

    public DoctorDTO(Long id, String fullName, String specialty, Map<HospitalDTO, List<String>> workingHours) {
        this.id = id;
        this.fullName = fullName;
        this.specialty = specialty;
        this.workingHours = workingHours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Map<HospitalDTO, List<String>> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Map<HospitalDTO, List<String>> workingHours) {
        this.workingHours = workingHours;
    }


}
