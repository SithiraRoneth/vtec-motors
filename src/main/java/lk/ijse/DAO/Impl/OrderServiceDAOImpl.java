package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.OrderServiceDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.dto.PlaceOrderDto;
import lk.ijse.dto.tm.CartTm;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceDAOImpl implements OrderServiceDAO {
    // Old one

    /*public boolean saveOrderDetails(String orderId, List<CartTm> cartTmList) throws SQLException {
        for (CartTm tm : cartTmList) {
                if (!saveOrderDetails(orderId, tm)) {
                    return false;
                }
            }
        return true;
    }*/
    private boolean saveOrderDetails(String orderId, CartTm tm) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orderservice VALUES (?,?,?)",
                orderId,
                tm.getService_id(),
                tm.getAmount()
        );
    }

    @Override
    public boolean save(PlaceOrderDto placeOrderDto) throws SQLException, ClassNotFoundException {
        for (CartTm tm : placeOrderDto.getCartTmList()) {
            if (!saveOrderDetails(placeOrderDto.getOrderId(), tm)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<PlaceOrderDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(PlaceOrderDto placeOrderDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public PlaceOrderDto search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String splitId(String id) {
        return null;
    }
}

