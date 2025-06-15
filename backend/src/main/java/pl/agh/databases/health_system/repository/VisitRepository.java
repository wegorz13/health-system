package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.agh.databases.health_system.domain.Visit;


import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends Neo4jRepository<Visit, Long> {
    @Query("""
    MATCH (p:Patient)
    WHERE id(p) = $patientId
    MATCH (p)-[:HAS_VISIT]->(v:Visit)
    MATCH (v)<-[:CONDUCTED_BY]-(d:Doctor)
    OPTIONAL MATCH (v)-[:TOOK_PLACE_IN]->(h:Hospital)
    RETURN v, d, h
""")
    List<Visit> findByPatientId(Long patientId);

    @Query("""
    MATCH (d:Doctor)
    WHERE id(d) = $doctorId
    MATCH (v:Visit)-[:CONDUCTED_BY]->(d)
    OPTIONAL MATCH (v)-[:TOOK_PLACE_IN]->(h:Hospital)
    RETURN v, d, h
    """)
    List<Visit> findByDoctorId(Long doctorId);

    @Query("""
    MATCH (v:Visit)-[:TOOK_PLACE_IN]->(h:Hospital)
    WHERE id(h) = $hospitalId
    OPTIONAL MATCH (v)<-[:CONDUCTED_BY]-(d:Doctor)
    RETURN v, d, h
""")
    List<Visit> findByHospitalId(Long hospitalId);

    @Query("""
    RETURN EXISTS{
    MATCH (d:Doctor)
    WHERE id(d)=$doctorId
    MATCH
      (d)<-[:CONDUCTED_BY]-(v:Visit)
    WHERE date(v.date).year = date($date).year AND
          date(v.date).month = date($date).month AND
          date(v.date).day = date($date).day AND
          abs(duration.between(v.date, $date).minutes) <= $visitDuration}
    """)
    boolean areVisitsColliding(Long doctorId, LocalDateTime date, int visitDuration);
}
