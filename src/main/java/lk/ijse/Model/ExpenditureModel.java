package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.ExpenditureDto;
import lk.ijse.dto.tm.ExpenditureTm;
import lk.ijse.dto.tm.IncomeTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenditureModel {
    public static boolean addExpenditure(ExpenditureDto dto) throws SQLException {
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

    public static List<ExpenditureTm> searchExpenditure(int year, String month) throws SQLException {
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
}