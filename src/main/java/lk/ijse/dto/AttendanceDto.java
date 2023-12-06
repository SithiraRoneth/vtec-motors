package lk.ijse.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AttendanceDto {
    private String date;
    private String emp_id;
    private String emp_name;
    private boolean isPresent;
}
