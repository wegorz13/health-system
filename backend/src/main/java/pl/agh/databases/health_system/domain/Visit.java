package pl.agh.databases.health_system.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;

@Node
@Getter
@Setter
@NoArgsConstructor
public class Visit {
    @Id @GeneratedValue
    private Long id;
    private LocalDateTime date;
    private String patientsCondition;
    private boolean recommends;

    @Relationship(type = "CONDUCTED_BY")
    private Doctor doctor;

    @Relationship(type = "TOOK_PLACE_IN")
    private Hospital hospital;
}
