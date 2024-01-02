package lk.ijse.dto;

import lk.ijse.dto.Factory.SuperDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeDto implements SuperDTO {
    private String id;
    private String name;
    private String contact;
    private String nic;
    private String job;
    private String email;


}
