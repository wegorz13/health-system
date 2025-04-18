package pl.agh.databases.health_system.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.agh.databases.health_system.models.Patient;

import java.util.Optional;

public interface PatientRepository extends Neo4jRepository<Patient, Long> {

    Optional<Patient> findPatientByUsername(String username);
}
