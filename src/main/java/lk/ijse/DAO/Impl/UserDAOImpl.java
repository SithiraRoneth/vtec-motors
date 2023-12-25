package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.UserDAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class UserDAOImpl implements UserDAO {
    @Override
    public List<UserDto> loginUser() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user ");

        List<UserDto> userDtoList = new ArrayList<>();
        while (resultSet.next()) {
            userDtoList.add(
                    new UserDto(
                           resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }

        return userDtoList;
    }

    @Override
    public boolean save(UserDto dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO user VALUES(?,?,?)",
                dto.getUser_name(),
                dto.getEmail(),
                dto.getPassword()
                );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM user WHERE user_name = ? ",id);
    }

    @Override
    public List<UserDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user");
        ArrayList<UserDto>dtoList = new ArrayList<>();
        while (resultSet.next()){
            dtoList.add(
                    new UserDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }
        return dtoList;
    }

    @Override
    public boolean update(UserDto dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE user SET email = ? ,password = ? WHERE user_name = ? ",
                dto.getEmail(),
                dto.getPassword(),
                dto.getUser_name()
                );
    }

    @Override
    public UserDto search(String id) throws SQLException, ClassNotFoundException {
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
