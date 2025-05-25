package pl.agh.databases.health_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class VisitDTO {
    private Long id;
    private LocalDate date;
    private double cost;
    private List<String> prescriptions;
    private String patientsCondition;
    private String doctorFullName;
    private String address;
}
