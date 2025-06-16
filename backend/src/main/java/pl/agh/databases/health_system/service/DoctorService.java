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
import pl.agh.databases.health_system.repository.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final WorkDayScheduleRepository workDayScheduleRepository;
    private final VisitRepository visitRepository;
    private final PatientService patientService;
    private final PatientRepository patientRepository;

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
        List<Doctor> doctors = doctorRepository.findAll();
        Map<Long,Doctor> recommendationMap = new HashMap<>();

        doctors.forEach(doctor -> {
            Long sumForDoctor = visitRepository.findByDoctorId(doctor.getId()).stream().filter(Visit::isRecommends).count();
            recommendationMap.put(sumForDoctor, doctor);
        });

        return recommendationMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Doctor>comparingByKey().reversed())
                .map(Map.Entry::getValue)
                .map(DoctorMapper::toDTO)
                .toList();
    }

    public List<DoctorDTO> getRelativeBasedRecommendedDoctors(Long patientId) {
        HashMap<Long,Integer> factorByPatient = new HashMap<>();

        List<Long> relativesFirstDegree = patientService.findNthRelativesIdsByPatientId(patientId,1);

        relativesFirstDegree.forEach(relativeId->factorByPatient.put(relativeId,1));

        List<Long> relativesSecondDegree = patientService.findNthRelativesIdsByPatientId(patientId,2);

        relativesSecondDegree.forEach(relativeId->{
            if(!factorByPatient.containsKey(relativeId))
                factorByPatient.put(relativeId,2);
        });

        List<Long> relativesThirdDegree = patientService.findNthRelativesIdsByPatientId(patientId,3);
        relativesThirdDegree.forEach(relativeId->{
            if(!factorByPatient.containsKey(relativeId))
                factorByPatient.put(relativeId,4);
        });

        List<Doctor> doctors = doctorRepository.findAll();

        Map<Double,Doctor> recommendationMap = new HashMap<>();

        doctors.forEach(doctor -> {
            Double sumForDoctor = patientRepository.findByDoctorId(doctor.getId())
                    .stream()
                    .filter(p -> factorByPatient.containsKey(p.getId()))
                    .flatMap(p -> p.getVisits().stream()
                            .filter(Visit::isRecommends)
                            .map(v -> 1.0 / factorByPatient.get(p.getId()))
                    )
                    .mapToDouble(Double::doubleValue)
                    .sum();

            recommendationMap.put(sumForDoctor, doctor);

        });


        return recommendationMap.entrySet().stream()
                .sorted(Map.Entry.<Double, Doctor>comparingByKey().reversed())
                .map(Map.Entry::getValue)
                .map(DoctorMapper::toDTO)
                .toList();
    }
}
