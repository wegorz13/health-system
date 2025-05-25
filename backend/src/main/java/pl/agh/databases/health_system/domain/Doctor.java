package pl.agh.databases.health_system.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
@Setter
@NoArgsConstructor
public class Doctor {
    @Id @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private double salary;
    private String specialty;


}
