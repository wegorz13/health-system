package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.agh.databases.health_system.domain.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends Neo4jRepository<Patient, Long> {

    Optional<Patient> findPatientByUsername(String username);

    @Query("MATCH (:Patient {id: $patientId}) <- [:IS_RELATED] - (relatives:Patient) RETURN relatives")
    Optional<List<Patient>> findRelativesById(Long patientId);
}
