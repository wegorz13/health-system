package pl.agh.databases.health_system.exceptions;

public class PatientNotSeeingDoctorException extends RuntimeException {
    public PatientNotSeeingDoctorException(Long patientId, Long doctorId) {
        super("Patient " + patientId + " not seeing " + doctorId);
    }
}
