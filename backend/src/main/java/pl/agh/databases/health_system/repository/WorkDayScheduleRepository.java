package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.agh.databases.health_system.domain.WorkDaySchedule;

public interface WorkDayScheduleRepository extends Neo4jRepository<WorkDaySchedule, Long> {
}
