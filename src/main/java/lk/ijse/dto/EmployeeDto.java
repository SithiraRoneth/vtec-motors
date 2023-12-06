package lk.ijse.dto;

import javafx.scene.Parent;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeDto {
    private String id;
    private String name;
    private String contact;
    private String nic;
    private String job;
    private String email;


}
