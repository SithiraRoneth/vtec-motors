/* Created By Sithira Roneth
 * Date :12/28/23
 * Time :21:30
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.EmployeeBO;
import lk.ijse.DAO.Custom.EmployeeDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.Entity.Employee;
import lk.ijse.dto.EmployeeDto;
import java.sql.SQLException;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(dto);
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAll();
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(dto);
    }

    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.search(id);

    }

    @Override
    public String generateNextEmpId() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNextId();
    }
}
