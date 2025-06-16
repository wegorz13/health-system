package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.agh.databases.health_system.domain.Patient;

import java.util.List;
import java.util.Map;
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
        MATCH (:Patient {id: $patientId}) - [:IS_RELATED*1] - (relative:Patient) 
        RETURN DISTINCT relative.id
    """)
    List<Long> find1stRelativesIdsByPatientId(Long patientId);

    @Query("""
        MATCH (:Patient {id: $patientId}) - [:IS_RELATED*2] - (relative:Patient) 
        RETURN DISTINCT relative.id
    """)
    List<Long> find2ndRelativesIdsByPatientId(Long patientId);
    @Query("""
        MATCH (:Patient {id: $patientId}) - [:IS_RELATED*3] - (relative:Patient) 
        RETURN DISTINCT relative.id
    """)
    List<Long> find3rdRelativesIdsByPatientId(Long patientId);

    @Query("""
    RETURN EXISTS { 
        MATCH (patient:Patient) WHERE elementId(patient) = $patientId
        MATCH (relative:Patient) WHERE elementId(relative) = $relativeId
        MATCH (patient)-[:IS_RELATED]-(relative)
    }
""")
    boolean verifyIsRelative(Long patientId, Long relativeId);

    @Query("""
    MATCH (p1:Patient {id: $patientId})-[r:IS_RELATED]-(p2:Patient {id: $relativeId})
    DELETE r
""")
    void deleteRelationBetweenPatients(Long patientId, Long relativeId);

    @Query("""
        MATCH (patients:Patient) - [:HAS_VISIT] -> (v:Visit) - [:IS_CONDUCTED_BY] -> (:Doctor {id: $doctorId})
        RETURN patients, v
    """)
    List<Patient> findByDoctorId(Long doctorId);
}
