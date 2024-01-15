/* Created By Sithira Roneth
 * Date :1/8/24
 * Time :22:05
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.AttendanceBO;
import lk.ijse.DAO.Custom.AttendanceDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.Entity.Attendance;
import lk.ijse.dto.AttendanceDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBOImpl implements AttendanceBO {
    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);
    @Override
    public boolean addAttendanceList(List<AttendanceDto> attendanceDtoList) throws SQLException, ClassNotFoundException {
        for (AttendanceDto attendanceDto : attendanceDtoList) {
            Attendance attendance = new Attendance();
            attendance.setDate(attendanceDto.getDate());
            attendance.setEmp_id(attendanceDto.getEmp_id());
            attendance.setEmp_name(attendanceDto.getEmp_name());

            if (!attendanceDAO.save(attendance)) {
                return false;
            }
        }
        return true;

    }

    @Override
    public List<AttendanceDto> getAttendance(String date) throws SQLException, ClassNotFoundException {
        ArrayList<Attendance>attendances = (ArrayList<Attendance>) attendanceDAO.getAttendance(date);
        ArrayList<AttendanceDto>attendanceDtos = new ArrayList<>();

        for (Attendance attendance:attendances) {
            attendanceDtos.add(new AttendanceDto(
                    attendance.getDate(),
                    attendance.getEmp_id(),
                    attendance.getEmp_name()
                ));
        }
        return attendanceDtos;
    }
}
