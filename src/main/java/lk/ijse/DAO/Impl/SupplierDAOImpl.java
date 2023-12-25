package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.SupplierDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public boolean save(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier VALUES(?,?,?)",dto.getId(),dto.getName(),dto.getContact());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM supplier WHERE Supplier_id = ? ",id);
    }

    @Override
    public List<SupplierDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier");

        ArrayList<SupplierDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new SupplierDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }
        return dtoList;
    }

    @Override
    public boolean update(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplier SET Supplier_name = ? , Supplier_ContactNo = ? WHERE Supplier_id = ? ",
                dto.getName(),
                dto.getContact(),
                dto.getId()
                );
    }

    @Override
    public SupplierDto search(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute( "SELECT * FROM supplier WHERE Supplier_id = ? ",id);
        SupplierDto dto = null;
        if (resultSet.next()){
            String Supplier_id = resultSet.getString(1);
            String Supplier_name  = resultSet.getString(2);
            String Supplier_ContactNo = resultSet.getString(3);

            dto = new SupplierDto(Supplier_id,Supplier_name,Supplier_ContactNo);

        }
        return dto;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT Supplier_id FROM supplier ORDER BY Supplier_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String id) {
        if(id != null) {
            String[] split = id.split("S0");

            int id1 = Integer.parseInt(split[1]); //01
            id1++;
            return "S00" + id1;
        } else {
            return "S001";
        }
    }
}

