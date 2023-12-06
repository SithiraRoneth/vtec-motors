package lk.ijse.dto;

import lk.ijse.dto.tm.SpareCartTm;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SpareOrderDto {
    private String supplier_id;
    private String supplier_name;
    private String contact;
   /* private String spare_id;
    private String spare_name;*/
    private List<SpareCartTm>spareCartTmList = new ArrayList<>();
}
