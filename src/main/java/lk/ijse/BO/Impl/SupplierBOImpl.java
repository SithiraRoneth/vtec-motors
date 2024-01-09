/* Created By Sithira Roneth
 * Date :12/29/23
 * Time :23:55
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.SparePartsBO;
import lk.ijse.BO.Custom.SupplierBO;
import lk.ijse.DAO.Custom.SparePartsDAO;
import lk.ijse.DAO.Custom.SpareParts_Details_DAO;
import lk.ijse.DAO.Custom.SupplierDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DB.DbConnection;
import lk.ijse.Entity.Employee;
import lk.ijse.Entity.SpareOrder;
import lk.ijse.Entity.Supplier;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.SpareDto;
import lk.ijse.dto.SpareOrderDto;
import lk.ijse.dto.SupplierDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    SparePartsDAO sparePartsDAO = (SparePartsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SPAREPARTS);
    SpareParts_Details_DAO sparePartsDetailsDao = (SpareParts_Details_DAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SPAREPARTS_DETAILS);

    @Override
    public boolean saveSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(
                dto.getId(),
                dto.getName(),
                dto.getContact()
        ));
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public List<SupplierDto> getAllSupplier() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier>suppliers = (ArrayList<Supplier>) supplierDAO.getAll();
        ArrayList<SupplierDto>supplierDtos = new ArrayList<>();

        for (Supplier supplier:suppliers) {
            supplierDtos.add(new SupplierDto(
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getContact()
            ));
        }
        return supplierDtos;
    }

    @Override
    public boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(
                dto.getName(),
                dto.getContact(),
                dto.getId()
        ));
    }

    @Override
    public SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException {
        //return supplierDAO.search(id);
        Supplier supplier = supplierDAO.search(id);
        if (supplier != null) {
            return new SupplierDto(
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getContact()
                );
        }else {
            return null;
        }
    }

    @Override
    public String generateNextSupId() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNextId();
    }

    @Override
    public boolean addSpare(SpareOrderDto spareOrderDto) throws SQLException {
        String supplier_id = spareOrderDto.getSupplier_id();
        String supplier_name = spareOrderDto.getSupplier_name();
        String contact = spareOrderDto.getContact();

        Connection connection = null;

        try{
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            var dto = new SupplierDto(supplier_id,supplier_name,contact);
            boolean isSupplierSaved = supplierDAO.save(new Supplier(dto.getId(),dto.getName(),dto.getContact()));
            if (isSupplierSaved){
                boolean isUpdate = sparePartsDAO.updateSpare(spareOrderDto.getSpareCartTmList());
                if (isUpdate){
                    //var spare = new SpareOrderDto(spareOrderDto.getSupplier_id(),spareOrderDto.getSupplier_name(),spareOrderDto.getSpareCartTmList());
                    // boolean isSpareOrderSaved = sparePartsDetailsModel.saveSpearPart(spareOrderDto.getSupplier_id(),spareOrderDto.getSupplier_name(),spareOrderDto.getSpareCartTmList());
                    boolean isSpareOrderSaved = sparePartsDetailsDao.save(new SpareOrder(
                            spareOrderDto.getSupplier_id(),spareOrderDto.getSupplier_name(),spareOrderDto.getSpareCartTmList()
                    ));
                    if (isSpareOrderSaved){
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
