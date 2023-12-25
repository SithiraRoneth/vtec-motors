package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.VehicleDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.VehicleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public  class VehicleDAOImpl implements VehicleDAO{
    @Override
    public boolean save(VehicleDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO vehicles VALUES(?,?,?)",
                dto.getVehicle_id(),dto.getVehicle_type(),dto.getGuardian_id()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM vehicles WHERE Vehicle_id = ? ",id);
    }

    @Override
    public List<VehicleDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM vehicles");
        ArrayList<VehicleDto>getAllVehicle = new ArrayList<>();
        while (resultSet.next()){
            VehicleDto vehicleDto = new VehicleDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
            getAllVehicle.add(vehicleDto);
        }
        return getAllVehicle;
    }

    @Override
    public boolean update(VehicleDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE vehicles SET Vehicle_type = ?, Guardian_id = ? WHERE Vehicle_id = ?",
                dto.getVehicle_type(),
                dto.getGuardian_id(),
                dto.getVehicle_id()
                );
    }

    @Override
    public VehicleDto search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM vehicles WHERE Vehicle_id = ? ");
        resultSet.next();
        return new VehicleDto(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3)
        );
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT Vehicle_id FROM vehicles ORDER BY Vehicle_id DESC LIMIT 1");
        if (resultSet.next()){
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String id) {
        if(id != null) {
            String[] split = id.split("V0");

            int id1 = Integer.parseInt(split[1]); //01
            id1++;
            return "V00" + id1;
        } else {
            return "V001";
        }
    }
}
