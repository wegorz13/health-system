package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.agh.databases.health_system.domain.Doctor;
import pl.agh.databases.health_system.dto.HospitalWithHours;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends Neo4jRepository<Doctor, Long> {
    @Query("MATCH (:Hospital {id: $hospitalId}) <- [:WORKS_AT] - (docs:Doctor) RETURN docs")
    Optional<List<Doctor>> findDoctorsByHospitalId(Long hospitalId);

    @Query("MATCH (d:Doctor {id: $doctorId})-[r:WORKS_AT]->(h:Hospital) RETURN h { .id, .name }  AS hospital, collect(r.working_hours) AS hours")
    Optional<List<HospitalWithHours>> findDoctorDetails(Long doctorId);
}
