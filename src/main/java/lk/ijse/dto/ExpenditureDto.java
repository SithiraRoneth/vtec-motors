package lk.ijse.dto;

import lk.ijse.dto.Factory.SuperDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ExpenditureDto implements SuperDTO {
    private String desc;
    private double amount;
    private int year;
    private String month;
    private String date;
}