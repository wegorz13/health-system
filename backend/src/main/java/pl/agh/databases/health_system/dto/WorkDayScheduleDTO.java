package pl.agh.databases.health_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class WorkDayScheduleDTO {
    private LocalTime startTime;
    private LocalTime endTime;
    private String hospitalName;
}
