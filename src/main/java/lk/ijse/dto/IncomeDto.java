package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class IncomeDto {
    private String desc;
    private double amount;
    private int year;
    private String month;
    private String date;
}