package pl.agh.databases.health_system.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Node
@Getter
@Setter
@NoArgsConstructor
public class Visit {
    @Id @GeneratedValue
    private Long id;
    private LocalDate date;
    private double cost;
    private List<String> prescriptions; //should be separate type
    private String patientsCondition;
}
