package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.agh.databases.health_system.domain.Doctor;

import java.util.List;

public interface DoctorRepository extends Neo4jRepository<Doctor, Long> {
    @Query("MATCH (h:Hospital) WHERE id(h) = $hospitalId MATCH (h) <-[:WORKS_AT]-(docs:Doctor) RETURN docs")
    List<Doctor> findDoctorsByHospitalId(Long hospitalId);

    @Query("MATCH (:Patient {id: $patientId}) <- [:BELONGS_TO_PATIENT] - (:Visit) <- [:CONDUCTED_BY] - (docs:Doctor) RETURN docs")
    List<Doctor> findDoctorsByPatientId(Long patientId);

    @Query("MATCH (d:Doctor) <- [r:VISITS] - (p:Patient) WHERE p IN $patientIds AND r.recommended=true RETURN DISTINCT d ")
    List<Doctor> findDoctorsRecommendedByPatientIds(List<Long> patientIds);


}
