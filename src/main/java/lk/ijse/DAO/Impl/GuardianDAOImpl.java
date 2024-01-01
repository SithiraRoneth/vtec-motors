package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.GuardianDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.GuardianDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuardianDAOImpl implements GuardianDAO {
    @Override
    public boolean save(GuardianDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO guardian VALUES(?,?,?,?)",
                dto.getGuardian_id(),
                dto.getGuardian_name(),
                dto.getGuardian_contact(),
                dto.getEmployee_id()
                );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM guardian WHERE Guardian_id = ? ",id);
    }

    @Override
    public List<GuardianDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM guardian");

        ArrayList<GuardianDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new GuardianDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    @Override
    public boolean update(GuardianDto dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE guardian SET Guardian_name = ? , Guardian_ContactNo = ? , Emp_id = ? WHERE Guardian_id = ? ",
                dto.getGuardian_name(),
                dto.getGuardian_contact(),
                dto.getEmployee_id(),
                dto.getGuardian_id()
                );
    }

    @Override
    public GuardianDto search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM guardian WHERE Guardian_id = ?",id);

        GuardianDto dto = null;

        if (resultSet.next()) {
            String G_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);
            String emp_id = resultSet.getString(4);

            dto = new GuardianDto(G_id, name, contact, emp_id);
        }
        return dto;
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
