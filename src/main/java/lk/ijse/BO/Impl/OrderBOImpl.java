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
