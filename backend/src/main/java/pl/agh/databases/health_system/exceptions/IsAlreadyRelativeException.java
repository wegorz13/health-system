package pl.agh.databases.health_system.exceptions;


public class IsAlreadyRelativeException extends RuntimeException {
    public IsAlreadyRelativeException(Long patientId, Long relativeId) {
        super("Patient with id " + patientId + " is already related with patient with id " + relativeId);
    }
}
