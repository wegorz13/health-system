package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.agh.databases.health_system.domain.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends Neo4jRepository<Patient, Long> {
    Optional<Patient> findPatientByUsername(String username);

    @Query("""
    MATCH (p:Patient)-[:HAS_VISIT]->(v:Visit)
    WHERE id(p) = $patientId
    RETURN p, collect(v)
""")
    Optional<Patient> findWithVisitsById(Long patientId);

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
        MATCH (patient:Patient {id: $patientId}) -[r:IS_RELATED]- (relative:Patient {id: $relativeId}) 
        DELETE r
    """)
    void deleteRelative(Long patientId, Long relativeId);

    @Query("""
        MATCH (patient:Patient {id: $patientId})
        MATCH (relative:Patient {id: $relativeId})
        CREATE (patient)-[:IS_RELATED]->(relative)
    """)
    void addRelativeToPatient(Long patientId, Long relativeId);

    @Query("""
        MATCH (patients:Patient) - [:HAS_VISIT] -> (v:Visit) - [:IS_CONDUCTED_BY] -> (:Doctor {id: $doctorId})
        RETURN patients, v
    """)
    List<Patient> findByDoctorId(Long doctorId);
}
