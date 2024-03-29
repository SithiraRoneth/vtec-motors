package lk.ijse.DAO.Custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Expenditure;
import lk.ijse.dto.ExpenditureDto;
import lk.ijse.dto.tm.ExpenditureTm;

import java.sql.SQLException;
import java.util.List;

public interface ExpenditureDAO extends CrudDAO<Expenditure> {
    List<ExpenditureTm> searchExpenditure(int year, String month) throws SQLException, ClassNotFoundException;
}
