package lk.ijse.dto.tm;

import lombok.*;

import java.awt.*;
import javafx.scene.control.Button;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserTm {
    private String username;
    private String password;
    private String email;
    private Button deleteBtn;
}
