package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.SparePartsDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.SpareDto;
import lk.ijse.dto.tm.SpareCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SparePartsDAOImpl implements SparePartsDAO {
    @Override
    public boolean updateSpare(List<SpareCartTm> spareCartTmList){
        for (SpareCartTm tm : spareCartTmList){
            System.out.println("spare part " + tm);
        }
        return true;
    }
    @Override
    public  List<SpareDto>searchSpareparts(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM spareparts WHERE (Service_id = ? )",id);

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

    @Override
    public boolean save(SpareDto dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO spareparts VALUES(?,?,?,?,?,?)",
                dto.getSpareId(),
                dto.getSpareType(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getService_name(),
                dto.getSpareId()
                );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM spareparts WHERE Spare_id = ? ",id);
    }

    @Override
    public List<SpareDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM spareparts");

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

    @Override
    public boolean update(SpareDto spareDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public SpareDto search(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM spareparts WHERE Spare_id = ? ",id);
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

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT Spare_id FROM spareparts ORDER BY Spare_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String id) {
        if(id != null) {
            String[] split = id.split("Spare_0");

            int spare_id = Integer.parseInt(split[1]); //01
            spare_id++;
            return "Spare_00" + spare_id;
        } else {
            return "Spare_001";
        }
    }
}
