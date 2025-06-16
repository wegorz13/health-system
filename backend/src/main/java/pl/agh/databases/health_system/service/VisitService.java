package pl.agh.databases.health_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.agh.databases.health_system.domain.*;
import pl.agh.databases.health_system.dto.request.CreateVisitRequest;
import pl.agh.databases.health_system.exceptions.ResourceNotFoundException;
import pl.agh.databases.health_system.exceptions.UnavailableVisitDateException;
import pl.agh.databases.health_system.mapper.VisitMapper;
import pl.agh.databases.health_system.repository.DoctorRepository;
import pl.agh.databases.health_system.repository.HospitalRepository;
import pl.agh.databases.health_system.repository.PatientRepository;
import pl.agh.databases.health_system.repository.VisitRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    private final static int VISIT_DURATION = 20;
    private final HospitalRepository hospitalRepository;

    public List<Visit> getVisitsByPatientId(Long patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        return visitRepository.findByPatientId(patientId);
    }

    public List<Visit> getVisitsByDoctorId(Long doctorId) {
        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        return visitRepository.findByDoctorId(doctorId);
    }

    public Visit getVisitDetails(Long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }

    public void createVisit(CreateVisitRequest request) {
        Long patientId = request.getPatientId();
        Long doctorId = request.getDoctorId();
        Long hospitalId = request.getHospitalId();
        LocalDateTime date = request.getDate();

        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new ResourceNotFoundException("Hospital not found"));

        List<WorkDaySchedule> workDaySchedules = doctor.getSchedules();

        WorkDaySchedule workDaySchedule = workDaySchedules
                .stream()
                .filter((schedule) -> schedule.getDayOfWeek().equals(date.getDayOfWeek()))
                .findFirst()
                .orElseThrow(() -> new UnavailableVisitDateException(doctorId, date));

        LocalTime visitStartTime = date.toLocalTime();
        LocalTime visitEndTime = visitStartTime.plusMinutes(20);

        if (visitStartTime.isBefore(workDaySchedule.getStartTime()) ||
                visitEndTime.isAfter(workDaySchedule.getEndTime())) {
            throw new UnavailableVisitDateException(doctorId, date);
        }

        if(visitRepository.areVisitsColliding(doctorId, date, VISIT_DURATION)) {
            throw new UnavailableVisitDateException(doctorId, date);
        }

        Visit visit = VisitMapper.toEntity(request);
        visit.setHospital(hospital);
        patient.getVisits().add(visit);
        doctor.getVisits().add(visit);
        visitRepository.save(visit);
        patientRepository.save(patient);
        doctorRepository.save(doctor);
    }
}
