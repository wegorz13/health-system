package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.agh.databases.health_system.domain.Doctor;

import java.util.List;

public interface DoctorRepository extends Neo4jRepository<Doctor, Long> {
    @Query("""
    MATCH (h:Hospital) <-[:WORKS_AT]-(docs:Doctor) 
    WHERE id(h) = $hospitalId
    RETURN docs""")
    List<Doctor> findDoctorsByHospitalId(Long hospitalId);

    @Query("""
            MATCH (p:Patient {id: $patientId}) <- [:BELONGS_TO_PATIENT] - (:Visit) <- [:CONDUCTED_BY] - (docs:Doctor) 
            WHERE id(p) = $patientId
            RETURN docs""")
    List<Doctor> findDoctorsByPatientId(Long patientId);
}
