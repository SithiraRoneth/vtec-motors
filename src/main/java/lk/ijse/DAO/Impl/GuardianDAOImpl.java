package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.GuardianDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.Entity.Employee;
import lk.ijse.Entity.Guardian;
import lk.ijse.dto.GuardianDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuardianDAOImpl implements GuardianDAO {
    @Override
    public boolean save(Guardian entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO guardian VALUES(?,?,?,?)",
                entity.getGuardian_id(),
                entity.getGuardian_name(),
                entity.getGuardian_contact(),
                entity.getEmployee_id()
                );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM guardian WHERE Guardian_id = ? ",id);
    }

    @Override
    public List<Guardian> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM guardian");

        ArrayList<Guardian> getAllGuardian = new ArrayList<>();
        while (resultSet.next()){
            Guardian guardian = new Guardian(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            getAllGuardian.add(guardian);
        }
        return getAllGuardian;
    }

    @Override
    public boolean update(Guardian entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE guardian SET Guardian_name = ? , Guardian_ContactNo = ? , Emp_id = ? WHERE Guardian_id = ? ",
                entity.getGuardian_name(),
                entity.getGuardian_contact(),
                entity.getEmployee_id(),
                entity.getGuardian_id()
                );
    }

    @Override
    public Guardian search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM guardian WHERE Guardian_id = ?",id);
        if (resultSet.next()) {
            return new Guardian(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        } else {
            return null;
        }
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT Guardian_id FROM Guardian ORDER BY Guardian_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String id) {
        if(id != null) {
            String[] split = id.split("G0");

            int id1 = Integer.parseInt(split[1]); //01
            id1++;
            return "G00" + id1;
        } else {
            return "G001";
        }
    }
}
