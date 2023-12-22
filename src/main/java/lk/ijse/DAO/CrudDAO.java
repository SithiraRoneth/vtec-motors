package lk.ijse.DAO;

import lk.ijse.DB.DbConnection;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
      boolean save(T t) throws SQLException, ClassNotFoundException;
      boolean delete(String id) throws SQLException, ClassNotFoundException;
      List<T> getAll() throws SQLException, ClassNotFoundException;
      boolean update(T t) throws SQLException, ClassNotFoundException;
      T  search(String id) throws SQLException, ClassNotFoundException;
      String generateNextId() throws SQLException, ClassNotFoundException;
      String splitId(String id);
}
