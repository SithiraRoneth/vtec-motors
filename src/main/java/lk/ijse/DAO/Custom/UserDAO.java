package lk.ijse.DAO.Custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.User;
import lk.ijse.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CrudDAO<User> {
     List<User> loginUser() throws SQLException, ClassNotFoundException;
}
