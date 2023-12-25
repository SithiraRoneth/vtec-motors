package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.ServiceDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.ServiceDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOImpl implements ServiceDAO {
    @Override
    public  boolean updateService(List<CartTm> cartTmList){
        for(CartTm tm : cartTmList) {
            System.out.println("Service: " + tm);

        }
        return true;
    }

    @Override
    public boolean save(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO service VALUES(?,?,?,?)",
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getAmount()
                );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Service WHERE Service_id = ? ",id);
    }

    @Override
    public List<ServiceDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM service");
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

    @Override
    public boolean update(ServiceDto serviceDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ServiceDto search(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM service WHERE Service_id = ? ",id);
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

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT Service_id FROM service ORDER BY Service_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String id) {
        if(id != null) {
            String[] split = id.split("Service_0");

            int id1 = Integer.parseInt(split[1]); //01
            id1++;
            return "Service_00" + id1;
        } else {
            return "Service_001";
        }
    }
}
