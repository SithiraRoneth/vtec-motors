/* Created By Sithira Roneth
 * Date :12/23/23
 * Time :00:33
 * Project Name :vtec-motors
 * */
package lk.ijse.BO;

import lk.ijse.BO.Impl.EmployeeBOImpl;
import lk.ijse.BO.Impl.GuardianBOImpl;
import lk.ijse.BO.Impl.VehicleBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}
    public static BOFactory getBoFactory(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOType{
        EMPLOYEE,GUARDIAN,VEHICLE
    }
    public SuperBO getBO(BOType boType){
        switch (boType){
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case GUARDIAN:
                return new GuardianBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            default:
                return null;
        }
    }

}
