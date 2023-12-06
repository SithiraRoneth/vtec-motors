package lk.ijse.Model;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.OrderDto;

import java.sql.*;
import java.time.LocalDate;

public class OrderModel {
    public String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT Order_id FROM orders ORDER BY Order_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return spiltOrderId(resultSet.getString(1));
        }
        return spiltOrderId(null);
    }

    private String spiltOrderId(String currentOrderId) {
        if (currentOrderId != null ){
            String[] spilt = currentOrderId.split("O0");
            int id = Integer.parseInt(spilt[1]);
            id++;
            return "O00"+id;
        }else {
            return "O001";
        }
    }

    /*public boolean saveOrder(OrderDto orderDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Orders VALUES (?, ?, ?)");
        pstm.setString(1, orderDto.getOrder_id());
        pstm.setString(2, orderDto.getOrder_date());
        pstm.setString(3, orderDto.getGuardian_id());

        return pstm.executeUpdate() > 0;
    }*/

    public boolean saveOrder(String orderId,String guardianId,LocalDate date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES (?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,orderId);
        pstm.setDate(2,Date.valueOf(date));
        pstm.setString(3,guardianId);

        return pstm.executeUpdate() > 0;
    }
}
