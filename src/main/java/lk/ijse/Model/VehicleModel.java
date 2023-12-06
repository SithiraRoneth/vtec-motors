package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.GuardianDto;
import lk.ijse.dto.VehicleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleModel {
        public boolean addVehicle(VehicleDto dto) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO vehicles VALUES(?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,dto.getVehicle_id());
            pstm.setString(2, dto.getVehicle_type());
            pstm.setString(3, dto.getGuardian_id());

            return pstm.executeUpdate()>0;
        }

    public List<VehicleDto> getAllVehicles() throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM vehicles";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

        ArrayList<VehicleDto> dtoList =  new ArrayList<>();
        while (resultSet.next()){
            dtoList.add(
                    new VehicleDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }
        return dtoList;
    }
    public static boolean deleteVehicle(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM vehicles WHERE Vehicle_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public VehicleDto searchVehicle(String vehicleId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM vehicles WHERE Vehicle_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,vehicleId);

        ResultSet resultSet = pstm.executeQuery();

        VehicleDto dto = null;

        if (resultSet.next()){
            String vehicle_id = resultSet.getString(1);
            String vehicle_type = resultSet.getString(2);
            String guardian_id = resultSet.getString(3);

            dto = new VehicleDto(vehicle_id,vehicle_type,guardian_id);
        }
        return dto;
    }

    public boolean updateVehicle(VehicleDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE vehicles SET Vehicle_type = ?, Guardian_id = ? WHERE Vehicle_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getVehicle_type());
        pstm.setString(2,dto.getGuardian_id());
        pstm.setString(3,dto.getGuardian_id());

        return pstm.executeUpdate() >0;
    }

    public String generateNextVehicleId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT Vehicle_id FROM vehicles ORDER BY Vehicle_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitVehicleId(resultSet.getString(1));
        }
        return splitVehicleId(null);
    }

    private String splitVehicleId(String vehicle_id) {
        if(vehicle_id != null) {
            String[] split = vehicle_id.split("V0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "V00" + id;
        } else {
            return "V001";
        }
    }
}
