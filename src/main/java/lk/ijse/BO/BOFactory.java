/* Created By Sithira Roneth
 * Date :12/23/23
 * Time :00:33
 * Project Name :vtec-motors
 * */
package lk.ijse.BO;

import lk.ijse.BO.Impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}

    public static BOFactory getBoFactory(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType{
        EMPLOYEE,GUARDIAN,VEHICLE,SUPPLIER,SALARY,INCOME,SERVICE,USER,SPARE_PARTS
    }

    public SuperBO getBO(BOType boType){
        switch (boType){
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case GUARDIAN:
                return new GuardianBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case INCOME:
                return new IncomeBOImpl();
            case SERVICE:
                return new ServiceBOImpl();
            case USER:
                return new UserBOImpl();
            case SPARE_PARTS:
                return new SparePartsBOImpl();
            default:
                return null;
        }
    }
}
