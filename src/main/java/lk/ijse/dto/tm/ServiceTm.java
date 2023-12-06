package lk.ijse.dto.tm;

import lombok.*;

import java.awt.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ServiceTm {
        private String id;
        private String name;
        private String description;
        private double amount;
        //private Button delete;
}
