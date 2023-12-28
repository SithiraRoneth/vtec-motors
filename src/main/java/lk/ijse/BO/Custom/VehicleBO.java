package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.dto.VehicleDto;
import java.sql.SQLException;
import java.util.List;

public interface VehicleBO extends SuperBO {
    boolean saveVehicle(VehicleDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteVehicle(String id) throws SQLException, ClassNotFoundException;
    List<VehicleDto> getAllVehicle() throws SQLException, ClassNotFoundException;
    boolean updateVehicle(VehicleDto dto) throws SQLException, ClassNotFoundException;
    VehicleDto searchVehicle(String id) throws SQLException, ClassNotFoundException ;
    String generateNextVehicleId() throws SQLException, ClassNotFoundException;
}
