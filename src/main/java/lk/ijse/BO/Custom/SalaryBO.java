package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.tm.SalaryTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SalaryBO extends SuperBO {
     List<SalaryTm> searchSalaryPerMonth(String month) throws SQLException, ClassNotFoundException;
     boolean saveSalary(SalaryDto dto) throws SQLException, ClassNotFoundException;
}
