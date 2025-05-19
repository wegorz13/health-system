package pl.agh.databases.health_system.dto;

import java.util.List;

public interface HospitalWithHours {
    HospitalDTO getHospital();
    List<String> getHours();
}

