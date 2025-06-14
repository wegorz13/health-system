package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.agh.databases.health_system.domain.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends Neo4jRepository<Patient, Long> {
    Optional<Patient> findPatientByUsername(String username);

    @Query("""
        MATCH (:Patient {id: $patientId}) - [:IS_RELATED] - (relatives:Patient) 
        RETURN relatives
    """)
    List<Patient> findRelativesById(Long patientId);

    @Query("""
        MATCH (:Patient {id: $patientId}) - [:IS_RELATED*1..$depth] - (relative:Patient) 
        RETURN DISTINCT relative
    """)
    List<Long> findNthRelativesIdsByPatientId(Long patientId, int depth);

    @Query("RETURN EXISTS { MATCH (:Patient {id: $patientId}) -[:IS_RELATED]- (:Patient {id: $relativeId}) }")
    boolean verifyIsRelative(Long patientId, Long relativeId);

    @Query("""
        MATCH (patient:Patient {id: $patientId}), (relative:Patient {id: $relativeId}) 
        CREATE (patient)-[:IS_RELATED]->(relative)
    """)
    void addRelativeToPatient(Long patientId, Long relativeId);
}
