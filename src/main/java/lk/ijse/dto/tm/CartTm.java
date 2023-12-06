package lk.ijse.dto.tm;

import lombok.*;

import javafx.scene.control.Button;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartTm {
    private String service_id;
    private String service_name;
    private double amount;
    private Button btn;

    public CartTm(String service_id,String service_name,double amount,javafx.scene.control.Button btn){
        this.service_id = service_id;
        this.service_name = service_name;
        this.amount = amount;
        this.btn = btn;
    }
}
