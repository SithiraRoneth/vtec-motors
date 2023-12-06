package lk.ijse.dto.tm;

import lombok.*;

import java.awt.*;
import javafx.scene.control.Button;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SupplierTm {
    private String id;
    private String name;
    private String contact;
    private Button btn;

    public SupplierTm(String id, String name, String contact, javafx.scene.control.Button btn) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.btn = btn;
    }
}
