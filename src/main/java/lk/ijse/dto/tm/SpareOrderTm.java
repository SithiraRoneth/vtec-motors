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
public class SpareOrderTm {
    //private String spare_id;
    private String service_id;
    private String name;
    private double price;
    private Button btn;

    public SpareOrderTm(String service_id,String name,double price,javafx.scene.control.Button btn){
        this.service_id = service_id;
        this.name = name;
        this.price = price;
        this.btn = btn;
    }
}
