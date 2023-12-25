package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.ExpenditureDAO;
import lk.ijse.DB.DbConnection;
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
    public  List<ExpenditureTm> searchExpenditure(int year, String month) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Expenditure WHERE Year = ? AND Month = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, year);
        pstm.setString(2, month);

        ResultSet resultSet = pstm.executeQuery();

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
    public boolean save(ExpenditureDto dto) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Expenditure (Description,Amount,Year,Month,Date) VALUES(?,?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getDesc());
        pstm.setString(2, String.valueOf(dto.getAmount()));
        pstm.setString(3, String.valueOf(dto.getYear()));
        pstm.setString(4, dto.getMonth());
        pstm.setString(5, dto.getDate());

        boolean isSaved = pstm.executeUpdate()>0;
        return isSaved;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<ExpenditureDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(ExpenditureDto expenditureDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ExpenditureDto search(String id) throws SQLException, ClassNotFoundException {
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