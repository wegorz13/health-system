package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.agh.databases.health_system.domain.Visit;
import pl.agh.databases.health_system.dto.VisitDTO;

import java.util.List;

public interface VisitRepository extends Neo4jRepository<Visit, Long> {
    @Query("""
    MATCH (p:Patient {id: $patientId})
      <-[:BELONGS_TO_PATIENT]-(v:Visit)
      <-[:CONDUCTED_BY]-(d:Doctor),
          (v)-[:TOOK_PLACE_IN]->(h:Hospital)
    RETURN 
      v.id AS id,
      v.date AS date,
      v.cost AS cost,
      v.prescriptions AS prescriptions,
      v.patientsCondition AS patientsCondition,
      d.firstName + ' ' + d.lastName AS doctorFullName,
      h.address AS address
    """)
    List<VisitDTO> findByPatientId(Long patientId);
}
