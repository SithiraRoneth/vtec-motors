/* Created By Sithira Roneth
 * Date :1/1/24
 * Time :22:32
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.IncomeBO;
import lk.ijse.DAO.Custom.ExpenditureDAO;
import lk.ijse.DAO.Custom.IncomeDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.Entity.Expenditure;
import lk.ijse.Entity.Income;
import lk.ijse.dto.ExpenditureDto;
import lk.ijse.dto.IncomeDto;
import lk.ijse.dto.tm.ExpenditureTm;
import lk.ijse.dto.tm.IncomeTm;

import java.sql.SQLException;
import java.util.List;

public class IncomeBOImpl implements IncomeBO {
    IncomeDAO incomeDAO = (IncomeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INCOME);
    ExpenditureDAO expenditureDAO = (ExpenditureDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EXPENDITURE);

    @Override
    public List<IncomeTm> searchIncome(int year, String month) throws SQLException, ClassNotFoundException {
        return incomeDAO.searchIncome(year,month);
    }

    @Override
    public boolean saveIncome(IncomeDto dto) throws SQLException, ClassNotFoundException {
        return incomeDAO.save(new Income(
                dto.getDesc(),
                dto.getAmount(),
                dto.getYear(),
                dto.getMonth(),
                dto.getDate()
        ));
    }

    @Override
    public List<ExpenditureTm> searchExpenditure(int year, String month) throws SQLException, ClassNotFoundException {
        return expenditureDAO.searchExpenditure(year,month);
    }

    @Override
    public boolean saveExpenditure(ExpenditureDto dto) throws SQLException, ClassNotFoundException {
        return expenditureDAO.save(new Expenditure(dto.getDesc(),
                dto.getAmount(),
                dto.getYear(),
                dto.getMonth(),
                dto.getDate()));
    }
}
