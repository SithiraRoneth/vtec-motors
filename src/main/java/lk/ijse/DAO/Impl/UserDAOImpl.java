package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.UserDAO;
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
    public List<UserDto> loginUser() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user ";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        UserDto userDto = new UserDto();
        ResultSet resultSet = preparedStatement.executeQuery();
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
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO user VALUES(?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getUser_name());
        pstm.setString(2, dto.getEmail());
        pstm.setString(3, dto.getPassword());


        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM user WHERE user_name = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate()>0;
    }

    @Override
    public List<UserDto> getAll() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

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
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE user SET email = ? ,password = ? WHERE user_name = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmail());
        pstm.setString(2, dto.getPassword());
        pstm.setString(3, dto.getUser_name());

        return pstm.executeUpdate()>0;
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
