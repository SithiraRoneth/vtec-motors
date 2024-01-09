/* Created By Sithira Roneth
 * Date :1/9/24
 * Time :22:57
 * Project Name :vtec-motors
 * */
package lk.ijse.Entity;

import lk.ijse.dto.tm.CartTm;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderService {
    private String orderId;
    private String date;
    private String Guardian_id;
    private List<CartTm> cartTmList = new ArrayList<>();

    public OrderService(String orderId, List<CartTm> cartTmList){
        this.orderId = orderId;
        this.cartTmList = cartTmList;
    }
}
