package lk.ijse.DAO.Custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Income;
import lk.ijse.dto.IncomeDto;
import lk.ijse.dto.tm.IncomeTm;

import java.sql.SQLException;
import java.util.List;

public interface IncomeDAO extends CrudDAO<Income> {
    List<IncomeTm> searchIncome(int year, String month) throws SQLException, ClassNotFoundException;
}
