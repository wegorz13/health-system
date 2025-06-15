package pl.agh.databases.health_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.agh.databases.health_system.domain.Visit;
import pl.agh.databases.health_system.dto.request.CreateVisitRequest;

@Component
@RequiredArgsConstructor
public class VisitMapper {
    public static Visit toEntity(CreateVisitRequest request) {
        Visit visit = new Visit();
        visit.setDate(request.getDate());
        visit.setPatientsCondition(request.getPatientsCondition());

        return visit;
    }
}
