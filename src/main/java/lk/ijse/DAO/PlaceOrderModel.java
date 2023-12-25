package lk.ijse.DAO;

import lk.ijse.DAO.Custom.OrderDAO;
import lk.ijse.DAO.Impl.OrderDAOImpl;
import lk.ijse.DAO.Impl.ServiceDAOImpl;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.OrderDto;
import lk.ijse.dto.PlaceOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderModel {
    OrderDAO orderDAO = new OrderDAOImpl();

    private ServiceDAOImpl serviceModel = new ServiceDAOImpl();

    private OrderServiceModel orderServiceModel = new OrderServiceModel();

    public boolean placeOrder (PlaceOrderDto placeOrderDto) throws SQLException, ClassNotFoundException {
        System.out.println(placeOrderDto);

        String orderId = placeOrderDto.getOrderId();
        String guardianId = placeOrderDto.getGuardian_id();
        String date = placeOrderDto.getDate();


        Connection connection = null;
        try{
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            var dto = new OrderDto(orderId,guardianId,date);
            boolean isOrderSaved = orderDAO.save(dto);
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
