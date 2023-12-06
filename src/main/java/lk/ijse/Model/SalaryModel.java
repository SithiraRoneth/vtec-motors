package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.tm.SalaryTm;
import net.sf.jasperreports.engine.analytics.data.StandardAxisLevel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {

    public static boolean saveSalary(SalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO salary (Emp_id,Emp_name,salary_amount,bonus,etf,final_salary,month)VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, String.valueOf(dto.getSalary()));
        pstm.setString(4, String.valueOf(dto.getBonus()));
        pstm.setString(5, String.valueOf(dto.getEtf()));
        pstm.setString(6, String.valueOf(dto.getFinalSalary()));
        pstm.setString(7, dto.getMonth());

        boolean isSaved =  pstm.executeUpdate()>0;
        return isSaved;
    }
    public static List<SalaryTm>searchSalary(String month) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM salary WHERE month = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,month);

        ResultSet resultSet = pstm.executeQuery();

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

}
