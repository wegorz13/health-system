package pl.agh.databases.health_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.domain.Hospital;
import pl.agh.databases.health_system.domain.WorkDaySchedule;
import pl.agh.databases.health_system.dto.DoctorDTO;
import pl.agh.databases.health_system.dto.WorkDayScheduleDTO;
import pl.agh.databases.health_system.dto.request.CreateDoctorRequest;
import pl.agh.databases.health_system.dto.request.CreateWorkDayScheduleRequest;
import pl.agh.databases.health_system.exceptions.ResourceNotFoundException;
import pl.agh.databases.health_system.exceptions.ScheduleAlreadyAssignedException;
import pl.agh.databases.health_system.mapper.DoctorMapper;
import pl.agh.databases.health_system.repository.DoctorRepository;
import pl.agh.databases.health_system.repository.HospitalRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    public void createDoctor(CreateDoctorRequest request) {
        Doctor doctor = DoctorMapper.toEntity(request);
        doctorRepository.save(doctor);
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
}
