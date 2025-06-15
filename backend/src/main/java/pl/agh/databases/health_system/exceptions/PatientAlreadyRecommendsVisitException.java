package pl.agh.databases.health_system.exceptions;

public class PatientAlreadyRecommendsVisitException extends RuntimeException {
    public PatientAlreadyRecommendsVisitException(Long patientId, Long doctorId) {
        super("Patient with id " + patientId + " already recommended doctor with id " + doctorId);
    }
}

