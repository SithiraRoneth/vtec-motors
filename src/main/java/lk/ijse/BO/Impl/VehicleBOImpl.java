/* Created By Sithira Roneth
 * Date :12/28/23
 * Time :22:05
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.VehicleBO;
import lk.ijse.DAO.Custom.VehicleDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.dto.VehicleDto;

import java.sql.SQLException;
import java.util.List;

public class VehicleBOImpl implements VehicleBO {
    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.VEHICLE);

    @Override
    public boolean saveVehicle(VehicleDto dto) throws SQLException, ClassNotFoundException {
        return vehicleDAO.save(dto);
    }

    @Override
    public boolean deleteVehicle(String id) throws SQLException, ClassNotFoundException {
        return vehicleDAO.delete(id);
    }

    @Override
    public List<VehicleDto> getAllVehicle() throws SQLException, ClassNotFoundException {
        return vehicleDAO.getAll();
    }

    @Override
    public boolean updateVehicle(VehicleDto dto) throws SQLException, ClassNotFoundException {
        return vehicleDAO.update(dto);
    }

    @Override
    public VehicleDto searchVehicle(String id) throws SQLException, ClassNotFoundException {
        return vehicleDAO.search(id);
    }

    @Override
    public String generateNextVehicleId() throws SQLException, ClassNotFoundException {
        return vehicleDAO.generateNextId();
    }
}
