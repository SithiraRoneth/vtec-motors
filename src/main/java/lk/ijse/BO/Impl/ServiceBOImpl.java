/* Created By Sithira Roneth
 * Date :1/1/24
 * Time :23:07
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.ServiceBO;
import lk.ijse.DAO.Custom.ServiceDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.dto.ServiceDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.List;

public class ServiceBOImpl implements ServiceBO {
    ServiceDAO serviceDAO = (ServiceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SERVICE);
    @Override
    public boolean updateServices(List<CartTm> cartTmList) {
        return serviceDAO.updateService(cartTmList);
    }

    @Override
    public boolean saveService(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return serviceDAO.save(dto);
    }

    @Override
    public boolean deleteService(String id) throws SQLException, ClassNotFoundException {
        return serviceDAO.delete(id);
    }

    @Override
    public List<ServiceDto> getAllService() throws SQLException, ClassNotFoundException {
        return serviceDAO.getAll();
    }

    @Override
    public boolean updateService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException {
        return serviceDAO.update(serviceDto);
    }

    @Override
    public ServiceDto searchService(String id) throws SQLException, ClassNotFoundException {
        return serviceDAO.search(id);
    }

    @Override
    public String generateNextSerId() throws SQLException, ClassNotFoundException {
        return serviceDAO.generateNextId();
    }
}
