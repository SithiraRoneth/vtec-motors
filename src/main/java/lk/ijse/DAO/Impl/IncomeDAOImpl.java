package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.IncomeDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.Entity.Income;
import lk.ijse.dto.IncomeDto;
import lk.ijse.dto.tm.IncomeTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class IncomeDAOImpl implements IncomeDAO {
    @Override
    public  List<IncomeTm> searchIncome(int year, String month) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Income WHERE Year = ? AND Month = ?",year,month);

        List<IncomeTm> incomeList = new ArrayList<>();

        while (resultSet.next()) {
            String desc = resultSet.getString(2);
            double amount = resultSet.getDouble(3);
            String date = String.valueOf(resultSet.getDate(6));

            IncomeTm dto = new IncomeTm(date, desc, amount);
            incomeList.add(dto);
        }

        return incomeList;
    }

    @Override
    public HashSet<Income> getIncomeBarChart() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT month, SUM(Amount) FROM income WHERE date >= CURDATE() - INTERVAL 6 MONTH GROUP BY month");

        HashSet<Income>incomes = new HashSet<>();

        while (resultSet.next()) {
            incomes.add(
                    new Income(
                            resultSet.getString(1),
                            resultSet.getDouble(2)
                            )
            );
        }
        return incomes;
    }

    @Override
    public boolean save(Income entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Income (Description,Amount,Year,Month,Date) VALUES(?,?,?,?,?)",
                entity.getDesc(),
                entity.getAmount(),
                entity.getYear(),
                entity.getMonth(),
                entity.getDate()
                );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Income> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Income incomeDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Income search(String id) throws SQLException, ClassNotFoundException {
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