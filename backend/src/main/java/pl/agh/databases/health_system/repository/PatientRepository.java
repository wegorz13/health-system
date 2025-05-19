package pl.agh.databases.health_system.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.agh.databases.health_system.domain.Patient;

import java.util.Optional;

public interface PatientRepository extends Neo4jRepository<Patient, Long> {

    Optional<Patient> findPatientByUsername(String username);
}
