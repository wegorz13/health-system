package pl.agh.databases.health_system.exceptions;


import java.time.LocalDateTime;

public class UnavailableVisitDateException extends RuntimeException {
    public UnavailableVisitDateException(Long doctorId, LocalDateTime date) {
        super("Date taken: " + date + " at doctor with id: " + doctorId);
    }
}
