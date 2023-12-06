package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static boolean deleteSupplier(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM supplier WHERE Supplier_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public static boolean savedSupplier(String id , String name , String contact) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO supplier VALUES(?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        pstm.setString(2,name);
        pstm.setString(3,contact);

        /*boolean isSaved = pstm.executeUpdate()>0;
        return isSaved;*/
        return pstm.executeUpdate()>0;
    }

    public List<SupplierDto> getAllSupplier() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

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

    public boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE supplier SET Supplier_name = ? , Supplier_ContactNo = ? WHERE Supplier_id = ? ";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getContact());
        pstm.setString(3, dto.getId());

        return pstm.executeUpdate()>0;
    }

    public SupplierDto searchSupplier(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier WHERE Supplier_id = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        ResultSet resultSet = pstm.executeQuery();

        SupplierDto dto = null;

        if (resultSet.next()){
            String Supplier_id = resultSet.getString(1);
            String Supplier_name  = resultSet.getString(2);
            String Supplier_ContactNo = resultSet.getString(3);

            dto = new SupplierDto(Supplier_id,Supplier_name,Supplier_ContactNo);

        }
        return dto;
    }

    public String generateNextSupplierId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT Supplier_id FROM supplier ORDER BY Supplier_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitSupId(resultSet.getString(1));
        }
        return splitSupId(null);
    }

    private String splitSupId(String supId) {
        if(supId != null) {
            String[] split = supId.split("S0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "S00" + id;
        } else {
            return "S001";
        }
    }

}

