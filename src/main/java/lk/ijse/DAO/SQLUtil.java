/* Created By Sithira Roneth
 * Date :12/22/23
 * Time :23:38
 * Project Name :vtec-motors
 * */
package lk.ijse.DAO;

import lk.ijse.DB.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {
    public static <T> T execute(String sql, Object...ob) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        for (int i =0 ; i < ob.length; i++){
            pstm.setObject((i+1),ob[i]);
        }
        if (sql.startsWith("SELECT")){
            return (T) pstm.executeQuery();
        }else {
            return (T) (Boolean)(pstm.executeUpdate()>0);
        }
    }
}
