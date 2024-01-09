package lk.ijse.DAO.Custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Salary;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.tm.SalaryTm;

import java.sql.SQLException;
import java.util.List;

public interface SalaryDAO extends CrudDAO<Salary> {
    List<SalaryTm> searchSalary(String month) throws SQLException, ClassNotFoundException;
}
