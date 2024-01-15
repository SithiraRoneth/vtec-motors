package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.AttendanceDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.Entity.Attendance;
import lk.ijse.dto.AttendanceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {
    public  boolean addAttendanceList(List<Attendance> attendanceDtoList) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Attendance (Date,Emp_id,Emp_name) VALUES (?,?,?);";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            for (Attendance dto : attendanceDtoList) {
                pstm.setString(1, dto.getDate());
                pstm.setString(2, dto.getEmp_id());
                pstm.setString(3, dto.getEmp_name());

                pstm.addBatch();
            }

            int[] result = pstm.executeBatch();
            connection.commit();

            for (int i : result) {
                if (i <= 0) {
                    return false;
                }
            }
            return true;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<Attendance> getAttendance(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM Attendance WHERE Date = ?",date);

        ArrayList<Attendance>getAllAttendance = new ArrayList<>();
        while (resultSet.next()){
            Attendance attendance = new Attendance(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            getAllAttendance.add(attendance);
        }
        return getAllAttendance;
    }

    @Override
    public boolean save(Attendance attendance) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Attendance (Date,Emp_id,Emp_name) VALUES (?,?,?);",
                attendance.getDate(),attendance.getEmp_id(),attendance.getEmp_name());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Attendance> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Attendance attendance) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Attendance search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String splitId(String id) {
        return null;
    }
}
