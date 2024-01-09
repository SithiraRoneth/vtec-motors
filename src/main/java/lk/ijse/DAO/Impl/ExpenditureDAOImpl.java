package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.ExpenditureDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.Entity.Expenditure;
import lk.ijse.dto.ExpenditureDto;
import lk.ijse.dto.tm.ExpenditureTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenditureDAOImpl implements ExpenditureDAO {
    @Override
    public  List<ExpenditureTm> searchExpenditure(int year, String month) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Expenditure WHERE Year = ? AND Month = ?",year,month);

        List<ExpenditureTm> expenditureTmList = new ArrayList<>();

        while (resultSet.next()) {
            String desc = resultSet.getString(2);
            double amount = resultSet.getDouble(3);
            String date = String.valueOf(resultSet.getDate(6));

            ExpenditureTm dto = new ExpenditureTm(date, desc, amount);
            expenditureTmList.add(dto);
        }

        return expenditureTmList;
    }

    @Override
    public boolean save(Expenditure entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Expenditure (Description,Amount,Year,Month,Date) VALUES(?,?,?,?,?)",
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
    public List<Expenditure> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Expenditure expenditureDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Expenditure search(String id) throws SQLException, ClassNotFoundException {
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