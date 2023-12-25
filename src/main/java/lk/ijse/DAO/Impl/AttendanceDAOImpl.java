package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.AttendanceDAO;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.AttendanceDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendanceDAOImpl{
    public List<AttendanceDto> getAllAttendance() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM attendance";

        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<AttendanceDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(
                    new AttendanceDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getBoolean(4)
                    )
            );
        }
        return dtoList;
    }

    public static boolean addAttendanceList(List<AttendanceDto> attendanceDtoList) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Attendance (Date,Emp_id,Emp_name) VALUES (?,?,?);";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            for (AttendanceDto dto : attendanceDtoList) {
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


    public List<AttendanceDto> getAttendanceForDate(Date date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM attendance WHERE Date = ? ";

        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<AttendanceDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(
                    new AttendanceDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getBoolean(4)
                    )
            );
        }
        return dtoList;
    }

    public static List<AttendanceDto> getAttendance(String date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Attendance WHERE Date = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, date); // Set the parameter value

        ResultSet resultSet = pstm.executeQuery();

        List<AttendanceDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(2);
            String name = resultSet.getString(3);
            String day = resultSet.getString(4);

            AttendanceDto dto = new AttendanceDto(id, name, day, false);

            dtoList.add(dto);
        }

        return dtoList;
    }
}
