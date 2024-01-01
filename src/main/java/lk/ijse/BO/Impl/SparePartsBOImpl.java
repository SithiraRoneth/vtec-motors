/* Created By Sithira Roneth
 * Date :1/2/24
 * Time :00:02
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.SparePartsBO;
import lk.ijse.DAO.Custom.SparePartsDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.dto.SpareDto;
import lk.ijse.dto.tm.SpareCartTm;

import java.sql.SQLException;
import java.util.List;

public class SparePartsBOImpl implements SparePartsBO {
    SparePartsDAO sparePartsDAO = (SparePartsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SPAREPARTS);

    @Override
    public boolean updateSpareParts(List<SpareCartTm> spareCartTmList) {
        return sparePartsDAO.updateSpare(spareCartTmList);
    }

    @Override
    public List<SpareDto> SearchSpareParts(String id) throws SQLException, ClassNotFoundException {
        return sparePartsDAO.searchSpareparts(id);
    }

    @Override
    public boolean saveSpare(SpareDto dto) throws SQLException, ClassNotFoundException {
        return sparePartsDAO.save(dto);
    }

    @Override
    public boolean deleteSpare(String id) throws SQLException, ClassNotFoundException {
        return sparePartsDAO.delete(id);
    }

    @Override
    public List<SpareDto> getAllSpare() throws SQLException, ClassNotFoundException {
        return sparePartsDAO.getAll();
    }

    @Override
    public boolean updateSpare(SpareDto spareDto) throws SQLException, ClassNotFoundException {
        return sparePartsDAO.update(spareDto);
    }

    @Override
    public SpareDto searchSpare(String id) throws SQLException, ClassNotFoundException {
        return sparePartsDAO.search(id);
    }

    @Override
    public String generateNextSpareId() throws SQLException, ClassNotFoundException {
        return sparePartsDAO.generateNextId();
    }
}
