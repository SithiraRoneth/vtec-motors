package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.dto.ExpenditureDto;
import lk.ijse.dto.IncomeDto;
import lk.ijse.dto.tm.ExpenditureTm;
import lk.ijse.dto.tm.IncomeTm;
import java.sql.SQLException;
import java.util.List;

public interface IncomeBO extends SuperBO {
    List<IncomeTm> searchIncome(int year, String month) throws SQLException;
    boolean saveIncome(IncomeDto dto) throws SQLException, ClassNotFoundException;
    List<ExpenditureTm> searchExpenditure(int year, String month) throws SQLException;
    boolean saveExpenditure(ExpenditureDto dto) throws SQLException, ClassNotFoundException;
}
