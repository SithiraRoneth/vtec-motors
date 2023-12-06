package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeTm {
    private String id;
    private String name;
    private String contact;
    private String nic;
    private String job;
    private String email;
    private Button remove;

}
