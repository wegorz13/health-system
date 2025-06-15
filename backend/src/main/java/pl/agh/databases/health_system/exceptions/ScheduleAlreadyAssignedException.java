package pl.agh.databases.health_system.exceptions;

import java.time.DayOfWeek;

public class ScheduleAlreadyAssignedException extends RuntimeException {
    public ScheduleAlreadyAssignedException(Long doctorId, DayOfWeek dayOfWeek) {
        super("Doctor with id " + doctorId + " already has a schedule for " + dayOfWeek);
    }
}
