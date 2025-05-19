package pl.agh.databases.health_system.objects;

import java.util.List;

public interface HospitalWithHours {
    HospitalDTO getHospital();
    List<String> getHours();
}

