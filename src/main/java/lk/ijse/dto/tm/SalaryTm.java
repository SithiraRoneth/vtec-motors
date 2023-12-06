package lk.ijse.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SalaryTm {
    private String id;
    private String name;
    private double salary;
    private double bonus;
    private double etf;
    private double finalSalary;

}
