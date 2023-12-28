package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DAO.Custom.EmployeeDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.dto.EmployeeDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeBO extends SuperBO {
     boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
     boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
     List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException;
     boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
     EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException ;
     String generateNextEmpId() throws SQLException, ClassNotFoundException;
}
