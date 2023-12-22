package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.EmployeeDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean save(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "INSERT INTO employee VALUES(?,?,?,?,?,?)",
                dto.getId(),
                dto.getName(),
                dto.getContact(),
                dto.getNic(),
                dto.getJob(),
                dto.getEmail()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE Emp_id = ? ",id);
    }

    @Override
    public List<EmployeeDto> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee");
        ArrayList<EmployeeDto>getAllEmployee = new ArrayList<>();
        while (resultSet.next()){
            EmployeeDto employeeDto = new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            getAllEmployee.add(employeeDto);
        }
        return getAllEmployee;
    }

    @Override
    public boolean update(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Employee SET Emp_name = ?, Contact_no = ?, NIC = ? ,Job =?,email = ? WHERE Emp_id = ?",
                dto.getName(),
                dto.getContact(),
                dto.getNic(),
                dto.getJob(),
                dto.getEmail(),
                dto.getId()
                );
    }
    @Override
    public EmployeeDto search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee WHERE Emp_id = ? ",id);
        resultSet.next();
        return new EmployeeDto(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6)
                );
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT Emp_id FROM employee ORDER BY Emp_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String id) {
        if(id != null) {
            String[] split = id.split("E0");
            int S_id = Integer.parseInt(split[1]); //01
            S_id++;
            return "E00" + id;
        } else {
            return "E001";
        }
    }

}
