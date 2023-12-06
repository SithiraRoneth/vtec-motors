package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.ServiceDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceModel {
    public static boolean deleteService(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM Service WHERE Service_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean AddService(ServiceDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO service VALUES(?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getDescription());
        pstm.setString(4, String.valueOf(dto.getAmount()));

        return pstm.executeUpdate()>0;
    }

    public List<ServiceDto> loadAllService() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM service";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<ServiceDto> dtoList = new ArrayList<>();
        while (resultSet.next()){
            dtoList.add(
                    new ServiceDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4)
                    )
            );
        }
        return dtoList;
    }

    public ServiceDto searchService(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM service WHERE Service_id = ? ";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        ServiceDto dto = null;

        if (resultSet.next()){
            dto = new ServiceDto(
                     resultSet.getString(1),
                     resultSet.getString(2),
                     resultSet.getString(3),
                     resultSet.getDouble(4)
            );

        }
        return dto;
    }

    public String generateNextServiceId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT Service_id FROM service ORDER BY Service_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitSerId(resultSet.getString(1));
        }
        return splitSerId(null);
    }

    private String splitSerId(String serId) {
        if(serId != null) {
            String[] split = serId.split("Service_0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "Service_00" + id;
        } else {
            return "Service_001";
        }
    }

    public static boolean updateService(List<CartTm> cartTmList) throws SQLException {
        for(CartTm tm : cartTmList) {
            System.out.println("Service: " + tm);

        }
        return true;
    }
}
