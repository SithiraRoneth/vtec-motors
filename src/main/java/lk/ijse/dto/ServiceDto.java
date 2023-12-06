package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ServiceDto {
    private String id;
    private String name;
    private String description;
    private double amount;
}
