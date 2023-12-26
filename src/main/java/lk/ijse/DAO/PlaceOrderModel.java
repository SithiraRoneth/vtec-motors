package lk.ijse.DAO;

import lk.ijse.DAO.Custom.OrderDAO;
import lk.ijse.DAO.Custom.OrderServiceDAO;
import lk.ijse.DAO.Custom.ServiceDAO;
import lk.ijse.DAO.Impl.OrderDAOImpl;
import lk.ijse.DAO.Impl.OrderServiceDAOImpl;
import lk.ijse.DAO.Impl.ServiceDAOImpl;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.OrderDto;
import lk.ijse.dto.PlaceOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderModel {
    OrderDAO orderDAO = new OrderDAOImpl();
    ServiceDAO serviceDAO = new ServiceDAOImpl();
    OrderServiceDAO orderServiceDAO = new OrderServiceDAOImpl();
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
                boolean isUpdated = serviceDAO.updateService(placeOrderDto.getCartTmList());
                if (isUpdated) {
                    var place = new PlaceOrderDto(orderId, placeOrderDto.getCartTmList());
                    //boolean isOrderDetailsSaved = orderServiceModel.saveOrderDetails(placeOrderDto.getOrderId(),placeOrderDto.getCartTmList());
                    boolean isOrderDetailsSaved = orderServiceDAO.save(place);
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
