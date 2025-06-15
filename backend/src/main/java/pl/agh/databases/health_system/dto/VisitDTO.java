package pl.agh.databases.health_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class VisitDTO {
    private Long id;
    private LocalDateTime date;
    private String patientsCondition;
    private String doctorFullName;
    private String address;
    private boolean recommends;
}
