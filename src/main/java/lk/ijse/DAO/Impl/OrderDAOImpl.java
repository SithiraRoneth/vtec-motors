package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.OrderDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.Entity.Orders;
import lk.ijse.dto.OrderDto;
import java.sql.*;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orders VALUES (?,?,?)",
                entity.getOrder_id(),entity.getOrder_date(),entity.getGuardian_id()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Orders orderDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Orders search(String id) throws SQLException, ClassNotFoundException {
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
