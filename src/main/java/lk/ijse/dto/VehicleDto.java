package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleDto {
    private String vehicle_id;
    private String vehicle_type;
    private String guardian_id;
}
