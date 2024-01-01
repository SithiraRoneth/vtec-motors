package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.dto.SpareDto;
import lk.ijse.dto.tm.SpareCartTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SparePartsBO extends SuperBO {
    boolean updateSpareParts(List<SpareCartTm> spareCartTmList);
    List<SpareDto>SearchSpareParts(String id) throws SQLException, ClassNotFoundException;
    boolean saveSpare(SpareDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteSpare(String id) throws SQLException, ClassNotFoundException;
    List<SpareDto> getAllSpare() throws SQLException, ClassNotFoundException;
    boolean updateSpare(SpareDto spareDto) throws SQLException, ClassNotFoundException;
    SpareDto searchSpare(String id) throws SQLException, ClassNotFoundException;
    String generateNextSpareId() throws SQLException, ClassNotFoundException;
}
