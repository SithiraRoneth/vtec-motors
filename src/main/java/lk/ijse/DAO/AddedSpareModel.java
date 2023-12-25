package lk.ijse.DAO;

import lk.ijse.DAO.Custom.SparePartsDAO;
import lk.ijse.DAO.Custom.SupplierDAO;
import lk.ijse.DAO.Impl.SparePartsDAOImpl;
import lk.ijse.DAO.Impl.SupplierDAOImpl;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.SpareOrderDto;
import lk.ijse.dto.SupplierDto;

import java.sql.Connection;
import java.sql.SQLException;

public class AddedSpareModel {
    static SparePartsDAO sparePartsDAO = new SparePartsDAOImpl();
    static SupplierDAO supplierDAO = new SupplierDAOImpl();
    private static SpareParts_Details_Model sparePartsDetailsModel = new SpareParts_Details_Model();

    public static boolean addSpare(SpareOrderDto spareOrderDto) throws SQLException {
        System.out.println(spareOrderDto);

        String supplier_id = spareOrderDto.getSupplier_id();
        String supplier_name = spareOrderDto.getSupplier_name();
        String contact = spareOrderDto.getContact();
       /* String spare_id = spareOrderDto.getSpare_id();
        String spare_type = spareOrderDto.getSpare_name();*/

        Connection connection = null;

        try{
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            var dto = new SupplierDto(supplier_id,supplier_name,contact);
            boolean isSupplierSaved = supplierDAO.save(dto);
            if (isSupplierSaved){
                boolean isUpdate = sparePartsDAO.updateSpare(spareOrderDto.getSpareCartTmList());
                if (isUpdate){
                    boolean isSpareOrderSaved = sparePartsDetailsModel.saveSpearPart(spareOrderDto.getSupplier_id(),spareOrderDto.getSupplier_name(),spareOrderDto.getSpareCartTmList());
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
