/*
package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.SpareOrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceModel {
    public boolean saveOrderDetails(String orderId, List<SpareOrderTm>spList, List<CartTm> cartTmList) throws SQLException {
        for (CartTm tm : cartTmList) {
            for (SpareOrderTm sptm : spList) {
                if (!saveOrderDetails(orderId, spList, tm)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean saveOrderDetails(String orderId,List<SpareOrderTm>spList,CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orderservice VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,orderId);
        pstm.setString(2,tm.getService_id());
        pstm.setDouble(3,spList);
        pstm.setDouble(4,tm.getAmount());

        return pstm.executeUpdate() > 0;
    }
}
*/

package lk.ijse.Model;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.SpareOrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceModel {
    public boolean saveOrderDetails(String orderId, List<CartTm> cartTmList) throws SQLException {
        for (CartTm tm : cartTmList) {
                if (!saveOrderDetails(orderId, tm)) {
                    return false;
                }
            }

        return true;
    }

    private boolean saveOrderDetails(String orderId, CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orderservice VALUES (?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, orderId);
        pstm.setString(2, tm.getService_id());
        pstm.setDouble(3, tm.getAmount());

        return pstm.executeUpdate() > 0;
    }
}

