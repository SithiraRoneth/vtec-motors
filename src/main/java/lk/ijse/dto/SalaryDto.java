package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SalaryDto {
    private String id;
    private String name;
    private double salary;
    private double bonus;
    private double etf;
    private double finalSalary;
    private String month;
}
