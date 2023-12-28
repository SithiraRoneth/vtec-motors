/* Created By Sithira Roneth
 * Date :12/28/23
 * Time :21:54
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.GuardianBO;
import lk.ijse.DAO.Custom.GuardianDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.dto.GuardianDto;
import java.sql.SQLException;
import java.util.List;

public class GuardianBOImpl implements GuardianBO {
    GuardianDAO guardianDAO = (GuardianDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.GUARDIAN);
    @Override
    public boolean saveGuardian(GuardianDto dto) throws SQLException, ClassNotFoundException {
        return guardianDAO.save(dto);
    }

    @Override
    public boolean deleteGuardian(String id) throws SQLException, ClassNotFoundException {
        return guardianDAO.delete(id);
    }

    @Override
    public List<GuardianDto> getAllGuardian() throws SQLException, ClassNotFoundException {
        return guardianDAO.getAll();
    }

    @Override
    public boolean updateGuardian(GuardianDto dto) throws SQLException, ClassNotFoundException {
        return guardianDAO.update(dto);
    }

    @Override
    public GuardianDto searchGuardian(String id) throws SQLException, ClassNotFoundException {
        return guardianDAO.search(id);
    }

    @Override
    public String generateNextGuardianId() throws SQLException, ClassNotFoundException {
        return guardianDAO.generateNextId();
    }
}
