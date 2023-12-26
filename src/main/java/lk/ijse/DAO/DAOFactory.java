/* Created By Sithira Roneth
 * Date :12/26/23
 * Time :23:29
 * Project Name :vtec-motors
 * */
package lk.ijse.DAO;

import lk.ijse.DAO.Custom.SupplierDAO;
import lk.ijse.DAO.Impl.*;

import javax.swing.plaf.PanelUI;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}
    public static DAOFactory getDaoFactory(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }
    public enum DAOTypes{
        ATTENDANCE,EMPLOYEE,EXPENDITURE,GUARDIAN,INCOME,ORDER,ORDER_SERVICE,
        SALARY,SERVICE,SPAREPARTS_DETAILS,SPAREPARTS,SUPPLIER,USER,VEHICLE
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes) {
            case ATTENDANCE:
                //return new AttendanceDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case EXPENDITURE:
                return new ExpenditureDAOImpl();
            case GUARDIAN:
                return new GuardianDAOImpl();
            case INCOME:
                return new IncomeDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_SERVICE:
                return new OrderServiceDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case SERVICE:
                return new ServiceDAOImpl();
            case SPAREPARTS_DETAILS:
                return new SpareParts_Details_DAOImpl();
            case SPAREPARTS:
                return new SparePartsDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case USER:
                return new UserDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            default:
                return null;
        }
    }
}

