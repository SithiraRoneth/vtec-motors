package lk.ijse.dto;

import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.SpareCartTm;
import lk.ijse.dto.tm.SpareOrderTm;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PlaceOrderDto {
    private String orderId;
    private String date;
    private String Guardian_id;
    //private List<SpareOrderTm>spareOrderTmList = new ArrayList<>();
    private List<CartTm> cartTmList = new ArrayList<>();

}
