/* Created By Sithira Roneth
 * Date :1/12/24
 * Time :12:56
 * Project Name :vtec-motors
 * */
package lk.ijse.BO.Impl;

import javafx.scene.chart.XYChart;
import lk.ijse.BO.Custom.HomeBO;
import lk.ijse.DAO.Custom.IncomeDAO;
import lk.ijse.DAO.DAOFactory;
import lk.ijse.DB.DbConnection;
import lk.ijse.Entity.Income;
import lk.ijse.dto.IncomeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class HomeBOImpl implements HomeBO {

    IncomeDAO incomeDAO = (IncomeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INCOME);
    @Override
    public HashSet<IncomeDto> orderChart() throws SQLException, ClassNotFoundException {

        HashSet<Income>set =  incomeDAO.getIncomeBarChart();
        HashSet<IncomeDto>incomeDtos = new HashSet<>();
        for (Income entity:set) {
            incomeDtos.add(new IncomeDto(
                    entity.getMonth(),
                    entity.getAmount()
            ));
        }
        return incomeDtos;
    }
}
