package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.SpareDto;
import lk.ijse.dto.tm.SpareCartTm;

import javax.naming.PartialResultException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SparePartsModel {

    public boolean saveSpare(SpareDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO spareparts VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getSpareId());
        pstm.setString(2, dto.getSpareType());
        pstm.setString(3, dto.getDescription());
        pstm.setString(4, String.valueOf(dto.getPrice()));
        pstm.setString(5, dto.getService_name());
        pstm.setString(6, dto.getService_id());

        return pstm.executeUpdate()>0;
    }

    public String generateNextSpareId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT Spare_id FROM spareparts ORDER BY Spare_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitSpare(resultSet.getString(1));
        }
        return splitSpare(null);
    }

    private String splitSpare(String id) {
        if(id != null) {
            String[] split = id.split("Spare_0");

            int spare_id = Integer.parseInt(split[1]); //01
            spare_id++;
            return "Spare_00" + spare_id;
        } else {
            return "Spare_001";
        }
    }

    public List<SpareDto> getAllSpare() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM spareparts";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<SpareDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new SpareDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    )
            );
        }
        return dtoList;
    }
    public static boolean deleteSpare(String spareId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM spareparts WHERE Spare_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,spareId);
        return pstm.executeUpdate()>0;
    }

    public SpareDto searchSpare(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM spareparts WHERE Spare_id = ? ";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();
        SpareDto dto = null;

        if (resultSet.next()){
            dto = new SpareDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return dto;
    }
    public boolean updateSpare(List<SpareCartTm>spareCartTmList){
        for (SpareCartTm tm : spareCartTmList){
            System.out.println("spare part " + tm);
        }
        return true;
    }
    public static List<SpareDto>searchSpareparts(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM spareparts WHERE (Service_id = ? )";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<SpareDto> dto = new ArrayList<>();
        while (resultSet.next()){
            dto.add(
                    new SpareDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                    )
            );
        }
        return dto;
    }
}
