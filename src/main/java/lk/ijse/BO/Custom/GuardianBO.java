package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.GuardianDto;

import java.sql.SQLException;
import java.util.List;

public interface GuardianBO extends SuperBO {
    boolean saveGuardian(GuardianDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteGuardian(String id) throws SQLException, ClassNotFoundException;
    List<GuardianDto> getAllGuardian() throws SQLException, ClassNotFoundException;
    boolean updateGuardian(GuardianDto dto) throws SQLException, ClassNotFoundException;
    GuardianDto searchGuardian(String id) throws SQLException, ClassNotFoundException ;
    String generateNextGuardianId() throws SQLException, ClassNotFoundException;
}
