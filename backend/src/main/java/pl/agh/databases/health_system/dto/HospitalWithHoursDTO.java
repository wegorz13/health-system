package pl.agh.databases.health_system.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HospitalWithHoursDTO {
    private HospitalDTO hospital;
    private List<String> hours;

    public HospitalWithHoursDTO(HospitalDTO hospital, List<String> hours) {
        this.hospital = hospital;
        this.hours = hours;
    }
}

