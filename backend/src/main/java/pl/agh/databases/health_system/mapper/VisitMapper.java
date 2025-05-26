package pl.agh.databases.health_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.agh.databases.health_system.domain.Visit;
import pl.agh.databases.health_system.dto.VisitDTO;

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
}
