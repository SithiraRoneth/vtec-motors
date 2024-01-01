/* Created By Sithira Roneth
 * Date :1/1/24
 * Time :21:46
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.SalaryBO;
import lk.ijse.DAO.Custom.SalaryDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.tm.SalaryTm;

import java.sql.SQLException;
import java.util.List;

public class SalaryBOImpl implements SalaryBO {
    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);
    @Override
    public List<SalaryTm> searchSalaryPerMonth(String month) throws SQLException, ClassNotFoundException {
        return salaryDAO.searchSalary(month);
    }

    @Override
    public boolean saveSalary(SalaryDto dto) throws SQLException, ClassNotFoundException {
        return salaryDAO.save(dto);
    }
}
