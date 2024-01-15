package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.Entity.Attendance;
import lk.ijse.dto.AttendanceDto;

import java.sql.SQLException;
import java.util.List;

public interface AttendanceBO extends SuperBO {
    boolean addAttendanceList(List<AttendanceDto> attendanceDtoList) throws SQLException, ClassNotFoundException;
    List<AttendanceDto> getAttendance(String date) throws SQLException, ClassNotFoundException;
}
