package lk.ijse.dto.tm;

import lombok.*;
import javafx.scene.control.Button;
//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GuardianTm {
    private String Guardian_id;
    private String Guardian_name;
    private String Guardian_contact;
    private String Employee_id;
    private Button remove;

    public GuardianTm(String guardianId, String guardianName, String guardianContact, String employeeId,  javafx.scene.control.Button btn) {
        this.Guardian_id = guardianId;
        this.Guardian_name = guardianName;
        this.Guardian_contact = guardianContact;
        this.Employee_id = employeeId;
        this.remove = btn;
    }

    /*public GuardianTm(String guardian_id,String guardian_name,String guardian_contact,String employee_id,Button remove){
        this.Guardian_id = guardian_id;
        this.Guardian_name = guardian_name;
        this.Guardian_contact = guardian_contact;
        this.Employee_id = employee_id;
        this.remove = remove;
    }*/

}
