package lk.ijse.Model;

import com.mysql.cj.xdevapi.DbDoc;
import javafx.scene.control.Alert;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public static List<UserDto> getAllUser() throws SQLException {
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

    public static boolean deleteUser(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM user WHERE user_name = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate()>0;
    }
   /* public static boolean checkUserAndPassword(UserDto userDto) throws SQLException {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userDto.getEmail());
            preparedStatement.setString(2, userDto.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            String userName1 = null;
            String email1 = null;
            String password1 = null;
            while (resultSet.next()) {
                userName1 =  resultSet.getString(1);
                email1 = resultSet.getString(2);
                password1 = resultSet.getString(3);
                break;
            }

            if (userName1.equals(userDto.getUser_name()) && (email1.equals(userDto.getEmail())) && password1.equals(userDto.getPassword())) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }*/

    public boolean checkRegister(UserDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO user VALUES(?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getUser_name());
        pstm.setString(2, dto.getEmail());
        pstm.setString(3, dto.getPassword());


        return pstm.executeUpdate()>0;
    }

    public boolean updateUser(UserDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE user SET email = ? ,password = ? WHERE user_name = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmail());
        pstm.setString(2, dto.getPassword());
        pstm.setString(3, dto.getUser_name());

        return pstm.executeUpdate()>0;
    }

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
}
