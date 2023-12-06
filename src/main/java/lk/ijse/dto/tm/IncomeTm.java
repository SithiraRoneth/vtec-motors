package lk.ijse.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class IncomeTm {
    private String date;
    private String desc;
    private double amount;
}