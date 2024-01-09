/* Created By Sithira Roneth
 * Date :1/1/24
 * Time :23:07
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.ServiceBO;
import lk.ijse.DAO.Custom.ServiceDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.Entity.Employee;
import lk.ijse.Entity.Service;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.ServiceDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceBOImpl implements ServiceBO {
    ServiceDAO serviceDAO = (ServiceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SERVICE);
    @Override
    public boolean updateServices(List<CartTm> cartTmList) {
        return serviceDAO.updateService(cartTmList);
    }

    @Override
    public boolean saveService(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return serviceDAO.save(new Service(dto.getId(),dto.getName(),dto.getDescription(),dto.getAmount()));
    }

    @Override
    public boolean deleteService(String id) throws SQLException, ClassNotFoundException {
        return serviceDAO.delete(id);
    }

    @Override
    public List<ServiceDto> getAllService() throws SQLException, ClassNotFoundException {
        ArrayList<Service>services = (ArrayList<Service>) serviceDAO.getAll();
        ArrayList<ServiceDto>serviceDtos = new ArrayList<>();

        for (Service service:services) {
            serviceDtos.add(new ServiceDto(
                    service.getId(),
                    service.getName(),
                    service.getDescription(),
                    service.getAmount()
            ));
        }
        return serviceDtos;
    }

    @Override
    public boolean updateService(ServiceDto dto) throws SQLException, ClassNotFoundException {
        return serviceDAO.update(new Service());
    }

    @Override
    public ServiceDto searchService(String id) throws SQLException, ClassNotFoundException {
        Service service = serviceDAO.search(id);
        if (service != null) {
            return new ServiceDto(
                    service.getId(),
                    service.getName(),
                    service.getDescription(),
                    service.getAmount()
            );
        }else {
            return null;
        }
    }

    @Override
    public String generateNextSerId() throws SQLException, ClassNotFoundException {
        return serviceDAO.generateNextId();
    }
}
