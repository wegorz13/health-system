package pl.agh.databases.health_system.domain;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Node
public class WorkDaySchedule {
    @Id @GeneratedValue
    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    @Relationship(type = "IN_HOSPITAL")
    private Hospital hospital;
}
