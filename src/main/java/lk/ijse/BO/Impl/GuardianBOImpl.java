/* Created By Sithira Roneth
 * Date :12/28/23
 * Time :21:54
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.GuardianBO;
import lk.ijse.DAO.Custom.GuardianDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.Entity.Employee;
import lk.ijse.Entity.Guardian;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.GuardianDto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuardianBOImpl implements GuardianBO {
    GuardianDAO guardianDAO = (GuardianDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.GUARDIAN);
    @Override
    public boolean saveGuardian(GuardianDto dto) throws SQLException, ClassNotFoundException {
        return guardianDAO.save(new Guardian(
                dto.getGuardian_id(),
                dto.getGuardian_name(),
                dto.getGuardian_contact(),
                dto.getEmployee_id()
        ));
    }

    @Override
    public boolean deleteGuardian(String id) throws SQLException, ClassNotFoundException {
        return guardianDAO.delete(id);
    }

    @Override
    public List<GuardianDto> getAllGuardian() throws SQLException, ClassNotFoundException {
        ArrayList<Guardian>guardians = (ArrayList<Guardian>) guardianDAO.getAll();
        ArrayList<GuardianDto>guardianDtos = new ArrayList<>();

        for (Guardian guardian : guardians) {
            guardianDtos.add(new GuardianDto(
                    guardian.getGuardian_id(),
                    guardian.getGuardian_name(),
                    guardian.getGuardian_contact(),
                    guardian.getEmployee_id()
            ));
        }
        return guardianDtos;
    }

    @Override
    public boolean updateGuardian(GuardianDto dto) throws SQLException, ClassNotFoundException {
        return guardianDAO.update(new Guardian(dto.getGuardian_name(),dto.getGuardian_contact(),dto.getEmployee_id(),dto.getGuardian_id()));
    }

    @Override
    public GuardianDto searchGuardian(String id) throws SQLException, ClassNotFoundException {
        Guardian guardian = guardianDAO.search(id);
        if (guardian != null) {
            return new GuardianDto(
                    guardian.getGuardian_id(),
                    guardian.getGuardian_name(),
                    guardian.getGuardian_contact(),
                    guardian.getEmployee_id()
            );
        }else {
            return null;
        }
    }

    @Override
    public String generateNextGuardianId() throws SQLException, ClassNotFoundException {
        return guardianDAO.generateNextId();
    }
}
