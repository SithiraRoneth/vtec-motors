/* Created By Sithira Roneth
 * Date :1/1/24
 * Time :23:54
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.UserBO;
import lk.ijse.DAO.Custom.UserDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.Entity.User;
import lk.ijse.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public List<UserDto> loginUserDetails() throws SQLException, ClassNotFoundException {
        ArrayList<User>users = (ArrayList<User>) userDAO.loginUser();
        ArrayList<UserDto>userDtos = new ArrayList<>();

        for (User user:users) {
            userDtos.add(new UserDto(user.getUser_name(),user.getEmail(),user.getPassword()));
        }
        return userDtos;
    }

    @Override
    public boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(dto.getUser_name(),dto.getEmail(),dto.getPassword()));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }

    @Override
    public List<UserDto> getAllUser() throws SQLException, ClassNotFoundException {
        ArrayList<User>users = (ArrayList<User>) userDAO.getAll();
        ArrayList<UserDto>userDtos = new ArrayList<>();

        for (User user:users) {
            userDtos.add(new UserDto(user.getUser_name(),user.getEmail(),user.getPassword()));
        }
        return userDtos;
    }

    @Override
    public boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(dto.getEmail(),dto.getPassword(),dto.getUser_name()));
    }
}
