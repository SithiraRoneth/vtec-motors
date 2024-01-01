package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.dto.ServiceDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ServiceBO extends SuperBO {
     boolean updateServices(List<CartTm> cartTmList);
     boolean saveService(ServiceDto dto) throws SQLException, ClassNotFoundException;
     boolean deleteService(String id) throws SQLException, ClassNotFoundException;
     List<ServiceDto> getAllService() throws SQLException, ClassNotFoundException;
     boolean updateService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException;
     ServiceDto searchService(String id) throws SQLException, ClassNotFoundException;
     String generateNextSerId() throws SQLException, ClassNotFoundException;
}
