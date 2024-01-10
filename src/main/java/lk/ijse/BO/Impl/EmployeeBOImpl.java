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
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(
                dto.getId(),
                dto.getName(),
                dto.getContact(),
                dto.getNic(),
                dto.getJob(),
                dto.getEmail()
        ));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<Employee>employees = (ArrayList<Employee>) employeeDAO.getAll();
        ArrayList<EmployeeDto>employeeDtos = new ArrayList<>();

        for (Employee employee:employees) {
            employeeDtos.add(new EmployeeDto(
                    employee.getId(),
                    employee.getName(),
                    employee.getContact(),
                    employee.getNic(),
                    employee.getJob(),
                    employee.getEmail()
            ));
        }
        return employeeDtos;
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(
                dto.getName(),
                dto.getContact(),
                dto.getNic(),
                dto.getJob(),
                dto.getEmail(),
                dto.getId()
        ));
    }

    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(id);
        if (employee != null) {
            return new EmployeeDto(
                    employee.getId(),
                    employee.getName(),
                    employee.getContact(),
                    employee.getNic(),
                    employee.getJob(),
                    employee.getEmail()
            );
        } else {
            return null;
        }
    }

    @Override
    public String generateNextEmpId() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNextId();
    }
}
