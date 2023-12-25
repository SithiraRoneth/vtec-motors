package lk.ijse.DAO.Custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CrudDAO<UserDto> {
     List<UserDto> loginUser() throws SQLException, ClassNotFoundException;
}
