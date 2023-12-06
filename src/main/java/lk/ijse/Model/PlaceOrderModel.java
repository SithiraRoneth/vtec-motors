package lk.ijse.Model;

import javafx.scene.control.Alert;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.OrderDto;
import lk.ijse.dto.OrderServiceDto;
import lk.ijse.dto.PlaceOrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaceOrderModel {
    private OrderModel orderModel = new OrderModel();

    private ServiceModel serviceModel = new ServiceModel();

    private OrderServiceModel orderServiceModel = new OrderServiceModel();

    public boolean placeOrder (PlaceOrderDto placeOrderDto) throws SQLException{
        System.out.println(placeOrderDto);

        String orderId = placeOrderDto.getOrderId();
        String guardianId = placeOrderDto.getGuardian_id();
        String date = placeOrderDto.getDate();


        Connection connection = null;
        try{
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderModel.saveOrder(orderId,guardianId, LocalDate.parse(date));
            if(isOrderSaved) {
                boolean isUpdated = serviceModel.updateService(placeOrderDto.getCartTmList());
                if (isUpdated) {
                    boolean isOrderDetailsSaved = orderServiceModel.saveOrderDetails(placeOrderDto.getOrderId(),placeOrderDto.getCartTmList());
                    if (isOrderDetailsSaved) {
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
