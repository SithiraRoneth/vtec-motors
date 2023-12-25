package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.OrderDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.dto.OrderDto;
import java.sql.*;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orders VALUES (?,?,?)",
                orderDto.getOrder_id(),orderDto.getGuardian_id(),orderDto.getOrder_date()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDto search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT Order_id FROM orders ORDER BY Order_id DESC LIMIT 1");
        if (resultSet.next()){
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String id) {
        if (id != null ){
            String[] spilt = id.split("O0");
            int id1 = Integer.parseInt(spilt[1]);
            id1++;
            return "O00"+id1;
        }else {
            return "O001";
        }
    }
}
