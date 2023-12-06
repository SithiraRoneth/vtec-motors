package lk.ijse.Model;

import com.mysql.cj.util.DnsSrv;
import lk.ijse.DB.DbConnection;
import lk.ijse.dto.SpareDto;
import lk.ijse.dto.SpareOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class AddedSpareModel {
    private static SparePartsModel sparePartsModel = new SparePartsModel();
    private static SupplierModel supplierModel = new SupplierModel();
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

            boolean isSupplierSaved = supplierModel.savedSupplier(supplier_id,supplier_name,contact);
            if (isSupplierSaved){
                boolean isUpdate = sparePartsModel.updateSpare(spareOrderDto.getSpareCartTmList());
                if (isUpdate){
                    boolean isSpareOrderSaved = sparePartsDetailsModel.saveSpearPart(spareOrderDto.getSupplier_id(),spareOrderDto.getSupplier_name(),spareOrderDto.getSpareCartTmList());
                    if (isSpareOrderSaved){
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
