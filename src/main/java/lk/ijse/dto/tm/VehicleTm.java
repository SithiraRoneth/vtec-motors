package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.awt.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleTm {
    private String vehicle_id;
    private String vehicle_type;
    private String guardian_id;
    private Button remove;
}
