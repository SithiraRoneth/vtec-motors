package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SpareCartTm {
    private String id;
    private String  spare_type;
    private double price;
    private Button btn;

    public SpareCartTm(String id,String spare_type,double price,javafx.scene.control.Button btn){
        this.id = id;
        this.spare_type = spare_type;
        this.price = price;
        this.btn = btn;
    }
}
