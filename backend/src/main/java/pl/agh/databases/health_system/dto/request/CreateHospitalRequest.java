package pl.agh.databases.health_system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHospitalRequest {
    private String name;
    private String address;
    private String email;
    private String phone;
}
