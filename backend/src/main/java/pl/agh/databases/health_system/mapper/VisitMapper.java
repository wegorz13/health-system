package pl.agh.databases.health_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.agh.databases.health_system.domain.Visit;
import pl.agh.databases.health_system.dto.VisitDTO;
import pl.agh.databases.health_system.dto.request.CreateVisitRequest;

@Component
@RequiredArgsConstructor
public class VisitMapper {
    public static VisitDTO mapToDTO(Visit visit) {
        return VisitDTO.builder()
                .id(visit.getId())
                .date(visit.getDate())
                .cost(visit.getCost())
                .prescriptions(visit.getPrescriptions())
                .patientsCondition(visit.getPatientsCondition())
                .build();
    }

    public static Visit toEntity(CreateVisitRequest request) {
        Visit visit = new Visit();
        visit.setDate(request.getDate());
        visit.setCost(request.getCost());
        visit.setPrescriptions(request.getPrescriptions());
        visit.setPatientsCondition(request.getPatientsCondition());

        return visit;
    }
}
