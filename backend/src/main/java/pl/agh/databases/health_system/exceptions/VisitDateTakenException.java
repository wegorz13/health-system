package pl.agh.databases.health_system.exceptions;


import java.time.LocalDate;

public class VisitDateTakenException extends RuntimeException {
    public VisitDateTakenException(Long doctorId, LocalDate date) {
        super("Date taken: " + date + " at doctor with id: " + doctorId);
    }
}
