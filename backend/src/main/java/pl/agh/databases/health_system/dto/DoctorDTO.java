package pl.agh.databases.health_system.dto;

import lombok.*;

import java.time.DayOfWeek;
import java.util.Map;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private Long id;
    private String fullName;
    private String specialty;
    private Map<DayOfWeek, WorkDayScheduleDTO> workDaySchedule;
}
