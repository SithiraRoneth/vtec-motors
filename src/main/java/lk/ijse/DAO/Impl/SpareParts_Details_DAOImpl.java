package lk.ijse.DAO.Impl;

import lk.ijse.DAO.Custom.SpareParts_Details_DAO;
import lk.ijse.DAO.SQLUtil;
import lk.ijse.DB.DbConnection;
import lk.ijse.Entity.SpareOrder;
import lk.ijse.dto.SpareOrderDto;
import lk.ijse.dto.tm.SpareCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SpareParts_Details_DAOImpl implements SpareParts_Details_DAO {
    /*public boolean saveSpearPart(String supplierId,String supplierName, List<SpareCartTm>spareCartTmList) throws SQLException {
        for (SpareCartTm tm : spareCartTmList) {
            if(!saveSpearPart(supplierId,supplierName,tm)){
                return false;
            }
        }
        return true;
    }*/

    private boolean saveSpearPart(String supplierId,String supplier_name,SpareCartTm tm) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO spareparts_supplier VALUES(?,?,?,?)",
                supplierId,
                supplier_name,
                tm.getId(),
                tm.getSpare_type()
                );
    }

    @Override
    public boolean save(SpareOrder spareOrderDto) throws SQLException, ClassNotFoundException {
        for (SpareCartTm tm : spareOrderDto.getSpareCartTmList()) {
            if(!saveSpearPart(spareOrderDto.getSupplier_id(),spareOrderDto.getSupplier_name(),tm)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<SpareOrder> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(SpareOrder spareOrderDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public SpareOrder search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String splitId(String id) {
        return null;
    }
}
