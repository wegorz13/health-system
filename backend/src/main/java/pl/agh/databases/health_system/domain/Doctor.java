package pl.agh.databases.health_system.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
@Getter
@Setter
@NoArgsConstructor
public class Doctor {
    @Id @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String specialty;

    @Relationship(type = "WORKS_AT")
    private List<WorkDaySchedule> schedules;

    @Relationship(type = "IS_CONDUCTED_BY")
    private List<Visit> visits;
}
