package lk.ijse.DAO.Custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Service;
import lk.ijse.dto.ServiceDto;
import lk.ijse.dto.tm.CartTm;

import java.util.List;

public interface ServiceDAO extends CrudDAO<Service> {
     boolean updateService(List<CartTm> cartTmList);
}
