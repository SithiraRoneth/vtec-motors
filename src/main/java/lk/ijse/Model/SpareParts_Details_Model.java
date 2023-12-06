package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.tm.SpareCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SpareParts_Details_Model {
    public boolean saveSpearPart(String supplierId,String supplierName, List<SpareCartTm>spareCartTmList) throws SQLException {
        for (SpareCartTm tm : spareCartTmList) {
            if(!saveSpearPart(supplierId,supplierName,tm)){
                return false;
            }
        }
        return true;
    }

    private boolean saveSpearPart(String supplierId,String supplier_name,SpareCartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO spareparts_supplier VALUES(?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,supplierId);
        pstm.setString(2,supplier_name);
        pstm.setString(3, tm.getId());
        pstm.setString(4,tm.getSpare_type());
        return pstm.executeUpdate()>0;
    }
}
