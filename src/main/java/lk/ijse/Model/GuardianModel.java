package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.GuardianDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuardianModel {
    public boolean saveGuardian(GuardianDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO guardian VALUES(?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getGuardian_id());
        pstm.setString(2, dto.getGuardian_name());
        pstm.setString(3, dto.getGuardian_contact());
        pstm.setString(4, dto.getEmployee_id());

        boolean isAdded = pstm.executeUpdate()>0;
        return isAdded;
    }

    public List<GuardianDto> GetAllGuardian() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM guardian";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<GuardianDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new GuardianDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public GuardianDto searchGuardian(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM guardian WHERE Guardian_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        GuardianDto dto = null;

        if (resultSet.next()) {
            String G_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);
            String emp_id = resultSet.getString(4);

            dto = new GuardianDto(G_id, name, contact, emp_id);
        }
        return dto;
    }
    public static boolean deleteGuardian(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM guardian WHERE Guardian_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean updateGuardian(GuardianDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE guardian SET Guardian_name = ? , Guardian_ContactNo = ? , Emp_id = ? WHERE Guardian_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getGuardian_name());
        pstm.setString(2, dto.getGuardian_contact());
        pstm.setString(3, dto.getEmployee_id());
        pstm.setString(4,dto.getGuardian_id());

        return pstm.executeUpdate()>0;
    }

    public String generateNextGuardinaId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT Guardian_id FROM Guardian ORDER BY Guardian_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private String splitOrderId(String string) {
        if(string != null) {
            String[] split = string.split("G0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "G00" + id;
        } else {
            return "G001";
        }
    }
}
