package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.dto.SpareOrderDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.VehicleDto;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
    boolean saveSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;
    List<SupplierDto> getAllSupplier() throws SQLException, ClassNotFoundException;
    boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException;
    SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException ;
    String generateNextSupId() throws SQLException, ClassNotFoundException;
    boolean addSpare(SpareOrderDto spareOrderDto) throws SQLException, ClassNotFoundException;
}
