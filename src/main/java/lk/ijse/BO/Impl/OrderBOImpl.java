/* Created By Sithira Roneth
 * Date :1/5/24
 * Time :23:56
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.OrdersBO;
import lk.ijse.DAO.Custom.OrderDAO;
import lk.ijse.DAO.Custom.OrderServiceDAO;
import lk.ijse.DAO.Custom.ServiceDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DB.DbConnection;
import lk.ijse.Entity.OrderService;
import lk.ijse.Entity.Orders;
import lk.ijse.dto.OrderDto;
import lk.ijse.dto.PlaceOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderBOImpl implements OrdersBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ServiceDAO serviceDAO = (ServiceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SERVICE);
    OrderServiceDAO orderServiceDAO = (OrderServiceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_SERVICE);
    @Override
    public boolean placeOrder(PlaceOrderDto placeOrderDto) throws SQLException, ClassNotFoundException {

        System.out.println(placeOrderDto);

        String orderId = placeOrderDto.getOrderId();
        String guardianId = placeOrderDto.getGuardian_id();
        String date = placeOrderDto.getDate();


        Connection connection = null;
        try {

            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            var dto = new OrderDto(orderId, guardianId, date);

            if (!orderDAO.save(new Orders(dto.getOrder_id(), dto.getGuardian_id(), dto.getOrder_date()))) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            if (!serviceDAO.updateService(placeOrderDto.getCartTmList())) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            //var place = new PlaceOrderDto(orderId, placeOrderDto.getCartTmList());
            //boolean isOrderDetailsSaved = orderServiceModel.saveOrderDetails(placeOrderDto.getOrderId(),placeOrderDto.getCartTmList());
            if (!orderServiceDAO.save(new OrderService(placeOrderDto.getOrderId(), placeOrderDto.getCartTmList()))) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
