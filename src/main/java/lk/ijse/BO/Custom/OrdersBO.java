package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.dto.PlaceOrderDto;

import java.sql.SQLException;

public interface OrdersBO extends SuperBO {
     boolean placeOrder (PlaceOrderDto placeOrderDto) throws SQLException, ClassNotFoundException;
}
