package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.SalaryDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.tm.SalaryTm;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {
    @Override
    public  List<SalaryTm>searchSalary(String month) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM salary WHERE month = ? ",month);

        List<SalaryTm> salaryTm = new ArrayList<>();
        while (resultSet.next()){
            String id = resultSet.getString(2);
            String name = resultSet.getString(3);
            double salary_amount = resultSet.getDouble(4);
            double bonus = resultSet.getDouble(5);
            double etf = resultSet.getDouble(6);
            double final_salary = resultSet.getDouble(7);

            SalaryTm dto = new SalaryTm(id,name,salary_amount,bonus,etf,final_salary);
            salaryTm.add(dto);

        }
        return salaryTm;
    }

    @Override
    public boolean save(SalaryDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO salary (Emp_id,Emp_name,salary_amount,bonus,etf,final_salary,month)VALUES(?,?,?,?,?,?,?)",
                dto.getId(),
                dto.getName(),
                dto.getSalary(),
                dto.getBonus(),
                dto.getEtf(),
                dto.getFinalSalary(),
                dto.getMonth()

        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<SalaryDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(SalaryDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public SalaryDto search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String splitId(String id) {
        return null;
    }
}
