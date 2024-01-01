package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserBO extends SuperBO {
    List<UserDto> loginUserDetails() throws SQLException, ClassNotFoundException;
    boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;
    List<UserDto> getAllUser() throws SQLException, ClassNotFoundException;
    boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException;
}
