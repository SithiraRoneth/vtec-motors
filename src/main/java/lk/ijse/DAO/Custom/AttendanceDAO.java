package lk.ijse.DAO.Custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Attendance;
import lk.ijse.dto.AttendanceDto;

import java.sql.SQLException;
import java.util.List;

public interface AttendanceDAO extends CrudDAO<Attendance> {
      boolean addAttendanceList(List<Attendance> attendanceDtoList) throws SQLException;
      List<Attendance> getAttendance(String date) throws SQLException, ClassNotFoundException;
}
