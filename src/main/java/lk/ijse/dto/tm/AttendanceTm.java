package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.sql.Date;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class AttendanceTm extends EmployeeTm{
    private Date date;
    private String EmpId;
    private String EmpName;
    private Button remove;
    private boolean isPresent;

    public AttendanceTm(String id, String name, javafx.scene.control.Button btn) {
        this.EmpId = id;
        this.EmpName = name;
        this.remove = btn;
    }
    public AttendanceTm(Date date,String EmpId,String EmpName){
        this.date = date;
        this.EmpId = EmpId;
        this.EmpName = EmpName;
    }

}
