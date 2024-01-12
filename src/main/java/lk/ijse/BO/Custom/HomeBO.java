package lk.ijse.BO.Custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.dto.IncomeDto;

import java.sql.SQLException;
import java.util.HashSet;

public interface HomeBO extends SuperBO {
     HashSet<IncomeDto> orderChart() throws SQLException, ClassNotFoundException;
}
