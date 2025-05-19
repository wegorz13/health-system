package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.agh.databases.health_system.domain.Doctor;

import java.util.List;

public interface DoctorRepository extends Neo4jRepository<Doctor, Long> {
    @Query("MATCH (:Hospital {name: $name}) <- [:WORKS_AT] - (docs:Doctor) RETURN docs")
    List<Doctor> findDoctorsByHospitalName(String name);
}
