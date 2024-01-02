/* Created By Sithira Roneth
 * Date :1/2/24
 * Time :00:23
 * Project Name :vtec-motors
 * */
package lk.ijse.dto.Factory;

import lk.ijse.dto.EmployeeDto;

public class DtoFactory {
    private static DtoFactory dtoFactory;
    private DtoFactory(){}
    public DtoFactory getDtoFactory(){
        return dtoFactory == null ? dtoFactory =  new DtoFactory() : dtoFactory;
    }
    public enum DtoType{
        EMPLOYEE,EXPENDITURE,INCOME,GUARDIAN,ORDER,ORDER_SERVICE,SALARY,SPARE,SPARE_ORDER,USER,VEHICLE
    }
    public SuperDTO getDTO(DtoType dtoType){
        switch (dtoType){
            case EMPLOYEE:
                return new EmployeeDto();
            default:
                return null;
        }
    }
}
