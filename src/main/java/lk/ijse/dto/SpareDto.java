package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SpareDto {
    private String spareId;
    private String spareType;
    private String description;
    private double price;
    private String service_name;
    private String service_id;
}
