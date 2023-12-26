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
    private List<SpareCartTm>spareCartTmList = new ArrayList<>();

    public SpareOrderDto(String id , String name , List<SpareCartTm>sp){
        this.supplier_id = id;
        this.supplier_name = name;
        this.spareCartTmList = sp;
    }
}
