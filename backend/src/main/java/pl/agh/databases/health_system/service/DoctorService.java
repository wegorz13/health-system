package pl.agh.databases.health_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.domain.*;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.WorkDayScheduleDTO;
import pl.agh.databases.health_system.dto.request.CreateDoctorRequest;
import pl.agh.databases.health_system.dto.request.CreateWorkDayScheduleRequest;
import pl.agh.databases.health_system.exceptions.ResourceNotFoundException;
import pl.agh.databases.health_system.exceptions.ScheduleAlreadyAssignedException;
import pl.agh.databases.health_system.mapper.DoctorMapper;
import pl.agh.databases.health_system.repository.DoctorRepository;
import pl.agh.databases.health_system.repository.HospitalRepository;
import pl.agh.databases.health_system.repository.VisitRepository;
import pl.agh.databases.health_system.repository.WorkDayScheduleRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final WorkDayScheduleRepository workDayScheduleRepository;
    private final VisitRepository visitRepository;
    private final PatientService patientService;

    public void createDoctor(CreateDoctorRequest request) {
        Doctor doctor = DoctorMapper.toEntity(request);
        doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        List<WorkDaySchedule> schedules = doctor.getSchedules();

        List<Visit> visits = visitRepository.findByDoctorId(doctorId);
        visits.forEach(visit -> {
            if (visit.getDate().isAfter(LocalDateTime.now())){
                visitRepository.delete(visit);
            }
        });

        workDayScheduleRepository.deleteAll(schedules);
        doctorRepository.deleteById(doctorId);
    }

    public void addDoctorWorkDaySchedule(CreateWorkDayScheduleRequest request, Long doctorId) {
        Long hospitalId = request.getHospitalId();

        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new ResourceNotFoundException("Hospital not found"));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        List<WorkDaySchedule> schedules = doctor.getSchedules();

        if (schedules.stream().anyMatch((s) -> s.getDayOfWeek().equals(request.getDayOfWeek()))) {
            throw new ScheduleAlreadyAssignedException(doctorId, request.getDayOfWeek());
        }

        WorkDaySchedule schedule = DoctorMapper.toEntity(request);

        schedule.setHospital(hospital);
        schedules.add(schedule);

        doctorRepository.save(doctor);
    }

    public List<DoctorDTO> getAllDoctorsByHospitalId(Long hospitalId) {
        return doctorRepository.findDoctorsByHospitalId(hospitalId).stream()
                .map(DoctorMapper::toDTO)
                .toList();
    }

    public List<DoctorDTO> getAllDoctorsByPatientId(Long patientId) {
        List<Doctor> doctors = doctorRepository.findDoctorsByPatientId(patientId);
        return doctors.stream().map(DoctorMapper::toDTO).toList();
    }

    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        return doctors.stream().map(DoctorMapper::toDTO).toList();
    }


    public DoctorDTO getDoctorDetails(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with id: " + id + " not found"));

        DoctorDTO doctorDTO = DoctorMapper.toDTO(doctor);
        Map<DayOfWeek, WorkDayScheduleDTO> workSchedule = DoctorMapper.doctorScheduleToWorkDayScheduleDTO(doctor.getSchedules());
        doctorDTO.setWorkDaySchedule(workSchedule);

        return doctorDTO;
    }

    public List<DoctorDTO> getBestRecommendedDoctors() {
    }

    public List<DoctorDTO> getRelativeBasedRecommendedDoctors(Long patientId) {

        // For each patient relative give a weight
        HashMap<Long,Integer> factorByPatient = new HashMap<>();

        // Get 1 degree relatives
        List<Long> relativesFirstDegree = patientService.findNthRelativesIdsByPatientId(patientId,1);
        // Assign factors to relatives
        relativesFirstDegree.forEach(relativeId->factorByPatient.put(relativeId,1));

        // Get 2 degree relatives
        List<Long> relativesSecondDegree = patientService.findNthRelativesIdsByPatientId(patientId,2);
        // Assign factors to relatives
        relativesSecondDegree.forEach(relativeId->{
            if(!factorByPatient.containsKey(relativeId))
                factorByPatient.put(relativeId,2);
        });

        // Get 3 degree relatives
        List<Long> relativesThirdDegree = patientService.findNthRelativesIdsByPatientId(patientId,3);
        // Assign factors to relatives
        relativesThirdDegree.forEach(relativeId->{
            if(!factorByPatient.containsKey(relativeId))
                factorByPatient.put(relativeId,4);
        });

        // Get all doctors
        List<Doctor> doctors = doctorRepository.findAll();

        // Calculate for each doctor sum of factors
        // The best recommendation is doctor with smallest one
        Map<Long,Doctor> recommendationMap = new HashMap<>();

        // For each doctor gets patients
        // If patient is relative, adds it's factor
        doctors.forEach(doctor->{
            long sumForDoctor = doctor.getPatients().stream()
                    .filter(patient -> factorByPatient.containsKey(patient.getId()))
                    .mapToLong(patient -> factorByPatient.get(patient.getId()))
                    .sum();

            recommendationMap.put(sumForDoctor, doctor);

        });

        // Converts a map into a list of doctors
        // sorted by the map's keys in ascending order.
        return recommendationMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .map(DoctorMapper::toDTO)
                .toList();


    }
}
