/* Created By Sithira Roneth
 * Date :12/23/23
 * Time :00:33
 * Project Name :vtec-motors
 * */
package lk.ijse.BO;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}
    public static BOFactory getBoFactory(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOType{}
    public void getBO(BOType boType){
        
    }

}
