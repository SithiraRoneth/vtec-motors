package lk.ijse.dto.tm;

import lombok.*;

import java.awt.*;
import java.sql.PreparedStatement;
import javafx.scene.control.Button;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SparePartTm {
    private String spareId;
    private String spareType;
    private String description;
    private String price;
    private Button delete;

    public SparePartTm(String spareId, String spareType, String description, double price, Button btn) {
        this.spareId = spareId;
        this.spareType = spareType;
        this.description = description;
        this.price = String.valueOf(price);
        this.delete = btn;
    }
}
