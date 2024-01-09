package lk.ijse.DAO.Custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.SpareParts;
import lk.ijse.dto.SpareDto;
import lk.ijse.dto.tm.SpareCartTm;

import java.sql.SQLException;
import java.util.List;

public interface SparePartsDAO extends CrudDAO<SpareParts> {
    boolean updateSpare(List<SpareCartTm> spareCartTmList);
    List<SpareParts>searchSpareparts(String id) throws SQLException, ClassNotFoundException;
}
