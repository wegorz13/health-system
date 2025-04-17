package pl.agh.databases.health_system.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.agh.databases.health_system.models.Hospital;

public interface HospitalRepository extends Neo4jRepository<Hospital, Long> {

}
