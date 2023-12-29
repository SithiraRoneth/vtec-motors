package lk.ijse.DAO;

import lk.ijse.BO.BOFactory;
import lk.ijse.BO.Custom.SupplierBO;
import lk.ijse.DAO.Custom.SparePartsDAO;
import lk.ijse.DAO.Custom.SpareParts_Details_DAO;
import lk.ijse.DAO.Custom.SupplierDAO;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.SpareOrderDto;
import lk.ijse.dto.SupplierDto;
import java.sql.Connection;
import java.sql.SQLException;

public class AddedSpareModel {
    static SparePartsDAO sparePartsDAO = (SparePartsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SPAREPARTS);
    static SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.SUPPLIER);
    static SpareParts_Details_DAO sparePartsDetailsDao = (SpareParts_Details_DAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SPAREPARTS_DETAILS);

    public static boolean addSpare(SpareOrderDto spareOrderDto) throws SQLException {
        System.out.println(spareOrderDto);

        String supplier_id = spareOrderDto.getSupplier_id();
        String supplier_name = spareOrderDto.getSupplier_name();
        String contact = spareOrderDto.getContact();
//        String spare_id = spareOrderDto.getSpare_id();
//        String spare_type = spareOrderDto.getSpare_name();

        Connection connection = null;

        try{
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            var dto = new SupplierDto(supplier_id,supplier_name,contact);
            boolean isSupplierSaved = supplierBO.saveSupplier(dto);
            if (isSupplierSaved){
                boolean isUpdate = sparePartsDAO.updateSpare(spareOrderDto.getSpareCartTmList());
                if (isUpdate){
                    var spare = new SpareOrderDto(spareOrderDto.getSupplier_id(),spareOrderDto.getSupplier_name(),spareOrderDto.getSpareCartTmList());
                   // boolean isSpareOrderSaved = sparePartsDetailsModel.saveSpearPart(spareOrderDto.getSupplier_id(),spareOrderDto.getSupplier_name(),spareOrderDto.getSpareCartTmList());
                    boolean isSpareOrderSaved = sparePartsDetailsDao.save(spare);
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
