package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    public static boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM employee WHERE Emp_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public  boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();;

        String sql = "INSERT INTO employee VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getContact());
        pstm.setString(4,dto.getNic());
        pstm.setString(5,dto.getJob());
        pstm.setString(6,dto.getEmail());


        boolean isSaved = pstm.executeUpdate()>0;
        return isSaved;
    }

    public static List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<EmployeeDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new EmployeeDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    )
            );
        }
        return dtoList;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Employee SET Emp_name = ?, Contact_no = ?, NIC = ? ,Job =?,email = ? WHERE Emp_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getName());
        pstm.setString(2,dto.getContact());
        pstm.setString(3,dto.getNic());
        pstm.setString(4,dto.getJob());
        pstm.setString(5,dto.getEmail());
        pstm.setString(6, dto.getId());

        return pstm.executeUpdate() >0;
    }
    public static EmployeeDto  searchEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE Emp_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if (resultSet.next()){
            String emp_id = resultSet.getString(1);
            String emp_name = resultSet.getString(2);
            String contact = resultSet.getString(3);
            String nic = resultSet.getString(4);
            String job = resultSet.getString(5);
            String email = resultSet.getString(6);

            dto = new EmployeeDto(emp_id,emp_name,contact,nic,job,email);
        }
        return dto;
    }


    public String generateNextEmployeeId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT Emp_id FROM employee ORDER BY Emp_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitEmpId(resultSet.getString(1));
        }
        return splitEmpId(null);
    }

    private String splitEmpId(String emp_id) {
        if(emp_id != null) {
            String[] split = emp_id.split("E0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "E00" + id;
        } else {
            return "E001";
        }
    }
}
