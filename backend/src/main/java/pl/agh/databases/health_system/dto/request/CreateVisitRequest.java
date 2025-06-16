package pl.agh.databases.health_system.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVisitRequest {
    private LocalDateTime date;
    private String patientsCondition;
    private Long patientId;
    private Long doctorId;
}