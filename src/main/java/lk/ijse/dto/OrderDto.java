package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class OrderDto {
    private String Order_id;
    private String Order_date;
    private String Guardian_id;
}
