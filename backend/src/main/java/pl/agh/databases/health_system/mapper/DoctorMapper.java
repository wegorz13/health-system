package pl.agh.databases.health_system.mapper;

import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.domain.WorkDaySchedule;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.WorkDayScheduleDTO;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorMapper {
    public static DoctorDTO toDTO(Doctor doctor) {
        return DoctorDTO.builder().id(
                doctor.getId()).fullName(
                doctor.getFirstName() + " " + doctor.getLastName()).specialty(
                doctor.getSpecialty()).build();
    }

    public static Map<DayOfWeek, WorkDayScheduleDTO> doctorScheduleToWorkDayScheduleDTO(List<WorkDaySchedule> schedules){
        Map<DayOfWeek, WorkDayScheduleDTO> map = new HashMap<>();
        for (WorkDaySchedule schedule : schedules) {
            map.put(schedule.getDayOfWeek(), new WorkDayScheduleDTO(schedule.getStartTime(), schedule.getEndTime(), schedule.getHospital().getName()));
        }

        return map;
    }
}
