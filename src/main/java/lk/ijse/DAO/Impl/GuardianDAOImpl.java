package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.GuardianDAO;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.GuardianDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuardianDAOImpl implements GuardianDAO {
    @Override
    public boolean save(GuardianDto dto) throws SQLException, ClassNotFoundException {
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

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM guardian WHERE Guardian_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    @Override
    public List<GuardianDto> getAll() throws SQLException, ClassNotFoundException {
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

    @Override
    public boolean update(GuardianDto dto) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE guardian SET Guardian_name = ? , Guardian_ContactNo = ? , Emp_id = ? WHERE Guardian_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getGuardian_name());
        pstm.setString(2, dto.getGuardian_contact());
        pstm.setString(3, dto.getEmployee_id());
        pstm.setString(4,dto.getGuardian_id());

        return pstm.executeUpdate()>0;
    }

    @Override
    public GuardianDto search(String id) throws SQLException, ClassNotFoundException {
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

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT Guardian_id FROM Guardian ORDER BY Guardian_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String id) {
        if(id != null) {
            String[] split = id.split("G0");

            int id1 = Integer.parseInt(split[1]); //01
            id1++;
            return "G00" + id1;
        } else {
            return "G001";
        }
    }
}
