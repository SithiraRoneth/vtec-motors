package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.IncomeDto;
import lk.ijse.dto.tm.IncomeTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncomeModel {
    public static boolean addIncome(IncomeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Income (Description,Amount,Year,Month,Date) VALUES(?,?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getDesc());
        pstm.setString(2, String.valueOf(dto.getAmount()));
        pstm.setString(3, String.valueOf(dto.getYear()));
        pstm.setString(4, dto.getMonth());
        pstm.setString(5, dto.getDate());

        boolean isSaved = pstm.executeUpdate()>0;
        return isSaved;
    }

    public static List<IncomeTm> searchIncome(int year, String month) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Income WHERE Year = ? AND Month = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, year);
        pstm.setString(2, month);

        ResultSet resultSet = pstm.executeQuery();

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
}