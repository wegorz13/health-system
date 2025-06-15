package pl.agh.databases.health_system.mapper;

import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.domain.WorkDaySchedule;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.WorkDayScheduleDTO;
import pl.agh.databases.health_system.dto.request.CreateDoctorRequest;
import pl.agh.databases.health_system.dto.request.CreateWorkDayScheduleRequest;

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

    public static Doctor toEntity(CreateDoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setSpecialty(request.getSpecialty());

        return doctor;
    }

    public static WorkDaySchedule toEntity(CreateWorkDayScheduleRequest request) {
        WorkDaySchedule workDaySchedule = new WorkDaySchedule();
        workDaySchedule.setDayOfWeek(request.getDayOfWeek());
        workDaySchedule.setStartTime(request.getStartTime());
        workDaySchedule.setEndTime(request.getEndTime());

        return workDaySchedule;
    }
}
