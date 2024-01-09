/* Created By Sithira Roneth
 * Date :12/28/23
 * Time :22:05
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.VehicleBO;
import lk.ijse.DAO.Custom.VehicleDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.Entity.Vehicle;
import lk.ijse.dto.VehicleDto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleBOImpl implements VehicleBO {
    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.VEHICLE);

    @Override
    public boolean saveVehicle(VehicleDto dto) throws SQLException, ClassNotFoundException {
        return vehicleDAO.save(new Vehicle(dto.getVehicle_id(),dto.getVehicle_type(),dto.getGuardian_id()));
    }

    @Override
    public boolean deleteVehicle(String id) throws SQLException, ClassNotFoundException {
        return vehicleDAO.delete(id);
    }

    @Override
    public List<VehicleDto> getAllVehicle() throws SQLException, ClassNotFoundException {
        ArrayList<Vehicle>vehicles = (ArrayList<Vehicle>) vehicleDAO.getAll();
        ArrayList<VehicleDto>vehicleDtos = new ArrayList<>();
        for (Vehicle vehicle:vehicles) {
            vehicleDtos.add(new VehicleDto(
                    vehicle.getVehicle_id(),
                    vehicle.getVehicle_type(),
                    vehicle.getGuardian_id()
            ));
        }
        return vehicleDtos;
    }

    @Override
    public boolean updateVehicle(VehicleDto dto) throws SQLException, ClassNotFoundException {
        return vehicleDAO.update(new Vehicle(dto.getVehicle_type(),dto.getGuardian_id(),dto.getVehicle_id()));
    }

    @Override
    public VehicleDto searchVehicle(String id) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = vehicleDAO.search(id);
        if (vehicle != null) {
            return new VehicleDto(
                    vehicle.getVehicle_id(),
                    vehicle.getVehicle_type(),
                    vehicle.getGuardian_id()
            );
        }else {
            return null;
        }
    }

    @Override
    public String generateNextVehicleId() throws SQLException, ClassNotFoundException {
        return vehicleDAO.generateNextId();
    }
}
