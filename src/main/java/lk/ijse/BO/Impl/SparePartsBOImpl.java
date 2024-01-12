/* Created By Sithira Roneth
 * Date :1/2/24
 * Time :00:02
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.Custom.SparePartsBO;
import lk.ijse.DAO.Custom.SparePartsDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.Entity.Employee;
import lk.ijse.Entity.SpareParts;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.SpareDto;
import lk.ijse.dto.tm.SpareCartTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SparePartsBOImpl implements SparePartsBO {
    SparePartsDAO sparePartsDAO = (SparePartsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SPAREPARTS);

    @Override
    public boolean updateSpareParts(List<SpareCartTm> spareCartTmList) {
        return sparePartsDAO.updateSpare(spareCartTmList);
    }

    @Override
    public List<SpareDto> SearchSpareParts(String id) throws SQLException, ClassNotFoundException {
       // return sparePartsDAO.searchSpareparts(id);
        return null;
    }

    @Override
    public boolean saveSpare(SpareDto dto) throws SQLException, ClassNotFoundException {
        return sparePartsDAO.save(new SpareParts(
                dto.getSpareId(),
                dto.getSpareType(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getService_name(),
                dto.getService_id()
        ));
    }

    @Override
    public boolean deleteSpare(String id) throws SQLException, ClassNotFoundException {
        return sparePartsDAO.delete(id);
    }

    @Override
    public List<SpareDto> getAllSpare() throws SQLException, ClassNotFoundException {
        ArrayList<SpareParts>getspareParts = (ArrayList<SpareParts>) sparePartsDAO.getAll();
        ArrayList<SpareDto>spareDtos = new ArrayList<>();

        for (SpareParts spareParts:getspareParts) {
            spareDtos.add(new SpareDto(
                    spareParts.getSpareId(),
                    spareParts.getSpareType(),
                    spareParts.getDescription(),
                    spareParts.getPrice(),
                    spareParts.getService_name(),
                    spareParts.getService_id()
            ));
        }
        return spareDtos;
    }

    @Override
    public boolean updateSpare(SpareDto dto) throws SQLException, ClassNotFoundException {
        return sparePartsDAO.update(new SpareParts());
    }

    @Override
    public SpareDto searchSpare(String id) throws SQLException, ClassNotFoundException {
        SpareParts spareParts = sparePartsDAO.search(id);
        return new SpareDto(spareParts.getSpareId(),spareParts.getSpareType(),
                spareParts.getDescription(),spareParts.getPrice(),
                spareParts.getService_name(),spareParts.getService_id());
    }

    @Override
    public String generateNextSpareId() throws SQLException, ClassNotFoundException {
        return sparePartsDAO.generateNextId();
    }
}
